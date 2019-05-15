/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.utility;

import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.FileUploadError;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.gl.model.Account;
import com.sift.loan.utility.MailBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author logzy
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SavingsTaskth  {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingsTaskth.class);
    PersistentTransaction t = null;
    int noofrecs = 0;
    int noofrecsok = 0;
    int noofrecsfailed = 0;
    private TaskExecutor taskExecutor;
    
    protected void initialize(int branchId,int companyId, String referenceNumber) {
       /// try {
            readExcelasync(branchId,companyId, referenceNumber);
       /// } catch (Exception e) {
       //     _logger.error("SavingsCron", e);
       /// }

    }
    
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
    public TaskExecutor getTaskExecutor() {
       return taskExecutor;
    }

    public class readExcelTask implements Runnable{
       private int branchId;
       private int companyId; 
       private String  referenceNumber;
       public readExcelTask(int branchId,int companyId, String referenceNumber){
        this.branchId = branchId;
        this.companyId = companyId;  
        this.referenceNumber = referenceNumber;
       }
       public void run() {
        try {  
         readExcel(branchId, companyId, referenceNumber);
         ////
         ///GlPostTaskth glposttsk = new GlPostTaskth();
         //glposttsk.initialize();
        } catch (Exception e) {
            _logger.error("SavingsCron", e);
        } 
      }
     }
    
    public void readExcelasync(int branchId,int companyId, String referenceNumber){
        taskExecutor.execute(new readExcelTask(branchId,companyId, referenceNumber));
    } 
    
    public void readExcel(int branchId,int companyId, String referenceNumber) throws PersistentException {
        String pattern = "yyyy-MM-dd";
        String trxType = "S";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date trx_date = null;

        boolean SkipFirstRow = true;
        int rowCount = 0;
        byte status = 0;
        Integer limit = 0;
        byte isBatch = 1;
        //List<SavingsError> errorList = new ArrayList<>();
        //List<Savings> successList = new ArrayList<>();
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();

            limit = (Integer) ctx.lookup("java:comp/env/cron.limit");
           

        } catch (Exception ex) {
            limit = 0;
            _logger.error("Savings cron", ex);
        }


        try {

         //////   t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            FileUploadCriteria criteria = new FileUploadCriteria();
            String fileState= "F";
           criteria.add(Restrictions.eq("status", status));
           criteria.add(Restrictions.le("attemptCount", limit));
           criteria.add(Restrictions.eq("state", fileState));
           criteria.add(Restrictions.eq("companyId", companyId));
           criteria.add(Restrictions.eq("branchId", branchId));
           criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
           //System.out.println("referenceNumber in SavingsTaskth :: " + referenceNumber);

           System.out.println("Attempt Limit: "+limit);
            List<FileUpload> uploads = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);

            
            if (uploads.size() > 0) {
              //System.out.println("inner : "+ uploads.size());
                Workbook workbook = null;
                for (FileUpload f : uploads) {
                    try{
                    System.out.println("File name: "+f.getLocation()+" Count: "+f.getAttemptCount());
                    int successCount = 0;
                    int failureCount = 0;
                    //float processedSum = 0f;
                    //double processedSum = 0;
                    BigDecimal processedSumTemp = new BigDecimal("0.0");
                    BigDecimal processedSum = new BigDecimal("0.0");
                    MathContext mc = new MathContext(4);
                    int fileCount = 0;
                    int errorCount = 0;
                    List<SavingsError> errorList = new ArrayList<>();
                    List<Savings> successList = new ArrayList<>();

                    boolean isAccountValid = true;
                    boolean isDateValid = true;
               
                    boolean isValidAmount=true;
                    
                   /** File uploadedFile = new File(f.getLocation()); 
                    if(!uploadedFile.exists()){
                        System.out.println("File does not exist");
                        FileUploadError fileError = new FileUploadError();
                        fileError.setBranchId(f.getBranchId());
                        fileError.setCompanyId(f.getCompanyId());
                        fileError.setDescription(f.getLocation()+ " missing on ther server");
                        fileError.setFileName(f.getFileName());
                        fileError.setProcessedDate(new Date());
                        fileError.setLocation(f.getLocation());
                        fileError.setProductId(f.getProductId());
                        fileError.setReferenceNumber(f.getReferenceNumber());
                        fileError.setUserId(f.getUserId());
                        
                        com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadErrorDAO().save(fileError);
                        com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadErrorDAO().updateFileUploadStatus(f.getId());
                    }else{**/
                    FileInputStream file = new FileInputStream(f.getLocation());
                    String extension = FilenameUtils.getExtension(f.getFileName());
                    /// System.out.println("got here2: ");
                    if (extension.equalsIgnoreCase("xls")) {
                        workbook = new HSSFWorkbook(file);
                    } else {
                        workbook = new XSSFWorkbook(file);
                    }
                    int numberOfSheets = workbook.getNumberOfSheets();
                    System.out.println("numberOfSheets :: " + numberOfSheets);
                    //loop through each of the sheets
                    for (int i = 0; i < numberOfSheets; i++) {
                        //Get the nth sheet from the workbook
                        Sheet sheet = workbook.getSheetAt(i);

                        //every sheet has rows, iterate over them
                        Iterator<Row> rowIterator = sheet.iterator();
                        int COLUMN_INDEX = 0;

                        //need to skip first row                
                        while (rowIterator.hasNext()) {
                            String accountNumber = "";
                            String accountName = "";
                            String narrative = "";
                            //Double amount = 0.0;
                            BigDecimal amount = new BigDecimal("0.0");
                            String reformattedStr = "";
                            String date = "";
                            //String uploader=uploaderStr;
                            String uploadError = "N";
                            String validationMsg = "";
                            String amountMsg="";
                             String dateMsg="";
                             String acctNameMsg="";
                              String acctNumberMsg="";
                             String narrationMsg="";
                             String strAmount = "";
                            int scheduleIdInt = -1;

                            //Get the row object
                            Row row = rowIterator.next();

                            if (row.getRowNum() == 0) {
                                continue;
                                //just skip the rows if row number is 0 
                            }

                            rowCount += 1;

                            //Every row has columns, get the column iterator and iterate over them
                            Iterator<Cell> cellIterator = row.cellIterator();

                            while (cellIterator.hasNext()) {
                                //if (COLUMN_INDEX == 5) {
                                //    COLUMN_INDEX = 0;
                                //}
                                //COLUMN_INDEX += 1;
                                //get the cell object
                                Cell cell = cellIterator.next();

                                //if(loanCaseIdStr.equalsIgnoreCase("")){
                              //  if (COLUMN_INDEX == 1) {
                                    try {
                                        accountNumber = row.getCell(0).getStringCellValue().trim();
                                        boolean valid = EasyCoopFinValidator.checkIfAccountExists(accountNumber, f.getBranchId(), f.getCompanyId());
                                        System.out.println("rowCount :=" + rowCount + " valid:="+valid + " accountNumber :: " + accountNumber);

                                        if (!valid) {
                                            acctNumberMsg=";Invalid Account Number";
                                            isAccountValid = false;
                                        }

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                         //amountMsg = "; Invalid Account Number";
                                        //isAccountValid = false;
                                    }
                                    //}else if(memberNoStr.equalsIgnoreCase("")){
                                //} else if (COLUMN_INDEX == 2) {
                                    //2nd column MEMER NO
                                    try {
                                        accountName = row.getCell(1).getStringCellValue().trim();

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        acctNameMsg = "; Invalid Account Name";
                                    }
                                    //}else if(amountStr.equalsIgnoreCase("")){
                               // } else if (COLUMN_INDEX == 3) {
                                    //3rd column  REPAYMENT AMOUNT 
                                    try {
                                        narrative = row.getCell(2).getStringCellValue().trim();
                                    } catch (Exception ex) {
                                        narrationMsg = "; Invalid Narration";
                                    }

                                    //}else if(uploadDateStr.equalsIgnoreCase("")){
                              //  } else if (COLUMN_INDEX == 4) {
                                    
                                    
                                        //amount = cell.getNumericCellValue();
                                        
                                        try {
                                         //   String.valueOf(cell.getNumericCellValue());
                                        Double da = Double.parseDouble(String.valueOf(row.getCell(3).getNumericCellValue()));
                                        amount = BigDecimal.valueOf(da.doubleValue()); //  da.doubleValue();
                                        if(amount.doubleValue()< 0){
                                            amountMsg=";Amount cannot be negative";
                                           // strAmount="; Negative Amount Value";
                                           }
                                        //amount = da.doubleValue();
                                        //totamt = totamt + amt;
                                     
                                        
                                    } catch (NumberFormatException ex) {
                                        strAmount= "; Invalid Upload Amount";
                                    } catch (Exception ex) {
                                       strAmount= "; Invalid Upload Amount";
                                    }

                               // } else if (COLUMN_INDEX == 5) {
                                    //4th Column  REPAYMENT DATE
                                    try {
                                        date = row.getCell(4).getStringCellValue();
                                        if (EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy")) {
                                           
                                            reformattedStr = myFormat.format(fromUser.parse(date));
                                            trx_date = myFormat.parse(reformattedStr);
                                            isDateValid = true;
                                        } else {
                                            isDateValid = false;
                                            dateMsg = "; Invalid Date";
                                        }
                                    } catch (Exception ex) {
                                        dateMsg = "; Invalid Date";
                                        isDateValid = false;
                                    }

                                //}
                            }//end of cell iterator
                            validationMsg= acctNameMsg + amountMsg + dateMsg + acctNumberMsg;
                            //  if(isAccountValid==false){
                            //  validationMsg+=";Invalid account number";
                             // }
                             
                              
                            if (validationMsg.length() > 0) {
                                uploadError = "Y";
                            }

                            System.out.println("Row: " + rowCount + " Account: " + accountNumber + " narrative: " + narrative + " amount:" + amount.doubleValue() + " date: " + date + " file branch: " + f.getBranchId() + " file company : " + f.getCompanyId() + " error: " + validationMsg);

                            if ("N".equals(uploadError)) {
                                if (accountNumber != "") {
                                    Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber, f.getBranchId(), f.getCompanyId());
                                    MemberCriteria mcriteria = new MemberCriteria();

                                    mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
                                    //Just added 
                                    mcriteria.add(Restrictions.eq("companyId", f.getCompanyId()));
                                    mcriteria.add(Restrictions.eq("branchId", f.getBranchId()));
                                    Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
                                    Savings saving = new Savings();
                                    saving.setAccountNumber(accountNumber);
                                    
                                    //saving.setAmount(amount.floatValue());    
                                    System.out.println("m.getId() ::" + m.getId() + " Company Id : " + m.getCompanyId() + " Branch id :: "  + m.getBranchId());
                                    System.out.println("m.getId() ::" + m.getId() + " f.Company Id : " + f.getCompanyId() + " f.Branch id :: "  + f.getBranchId());
                                    //System.out.println("amount.doubleValue()  for Savings() :: " + amount.doubleValue());
                                    saving.setAmount(amount.doubleValue());
                                    saving.setBranchId(f.getBranchId());
                                    saving.setCompanyId(f.getCompanyId());
                                    saving.setDescription(narrative);
                                    saving.setMemberId(m.getId());
                                    saving.setReferenceNumber(f.getReferenceNumber());
                                    saving.setTrxDate(trx_date);
                                    saving.setUserId(f.getUserId());
                                    saving.setProductId(f.getProductId());
                                    saving.setUserId(f.getUserId());
                                    //saving.setIsProcessed(trxType);
                                    // to indicate this saving record is a batch
                                    saving.setIsBatch(isBatch);

                                    //processedSum += amount.floatValue();
                                   // processedSum += amount.doubleValue();
                                   // System.out.println("amount :: " + amount);
                                    //processedSum.add(amount);
                                    //processedSum.add(amount, mc);
                                    //processedSum = processedSum.add(amount, mc);
                                    processedSum = processedSum.add(amount);
                                    //System.out.println(" rowCount :: " + rowCount + " processedSum += :: " + processedSum);
                                    successList.add(saving);
                                }
                            } else if ("Y".equals(uploadError)) {
                               // errorCount += 1;
                                String error = "";
                               // if (!isAccountValid) {
                                //    error += "Invalid Account Number";
                               // }
                               // if (!isDateValid) {
                               //     error += "Invalid Date";
                               // }
                               // if (!isAccountNameValid) {
                               //     error += "Invalid Account Name";
                              //  }
                               //  if (!isValidAmount) {
                                //    error += "Amount cannot be negative";
                               // }
                               /// System.out.println("Error found" + error);
                                Member m = null;
                                int memberId = 0;
                                try {
                                    Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber, f.getBranchId(), f.getCompanyId());
                                    MemberCriteria mcriteria = new MemberCriteria();

                                    mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
                                    //Just added 
                                    mcriteria.add(Restrictions.eq("companyId", f.getCompanyId()));
                                    mcriteria.add(Restrictions.eq("branchId", f.getBranchId()));
                                    m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
                                    memberId = m.getId();
                                } catch (NullPointerException ex) {
                                    memberId = 0;
                                } catch (Exception ex) {
                                    memberId = 0;
                                }

                                SavingsError sError = new SavingsError();
                                sError.setAccountNumber(accountNumber);
                                //sError.setAmount(amount.floatValue());
                                sError.setAmount(amount.doubleValue());
                                sError.setBranchId(f.getBranchId());
                                sError.setCompanyId(f.getCompanyId());
                                sError.setDescription(narrative);
                                sError.setMemberId(memberId);
                                sError.setReferenceNumber(f.getReferenceNumber());
                                sError.setTrxDate(trx_date);
                                sError.setUserId(f.getUserId());
                                sError.setProductId(f.getProductId());
                                sError.setUserId(f.getUserId());
                                sError.setTrxType(trxType);
                                   if (validationMsg.length() > 255) {
                                        sError.setErrorMessage(validationMsg.substring(0, 254));
                                      
                                    }
                                    else if (validationMsg.length() <= 255) 
                                    {
                                       sError.setErrorMessage(validationMsg);
                                    }   
                                    
                               
                                
                                // to indicate this saving record is a batch
                                sError.setIsBatch(isBatch);

                                errorList.add(sError);
                                //uploadedListFailed.add(c);
                            }

                            int totalCount = successList.size() + errorList.size();

                            uploadError = "N";
                            validationMsg = "";

                        }//end of rows iterator                
                     ////   System.out.println("End of sheet" + i);
                    }//end of sheets for loop            
                    // file.close();
                    System.out.println("Success Count: " + successList.size() + " Failed Count: " + errorList.size() + " updating" + f.getId());
                    System.out.println("End of file " + f.getReferenceNumber());
                    if (successList.size() > 0) {
                      ////  System.out.println("Found " + successList.size() + " successful");
                        for (Savings sav : successList) {
                            com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(sav);
                        }
                    }
                    if (errorList.size() > 0) {
                     /////   System.out.println("Found " + errorList.size() + " failed");
                        for (SavingsError sE : errorList) {
                            com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsErrorDAO().save(sE);
                        }
                    }
                    //BigDecimal bgdnewintprocessedSum = new BigDecimal(processedSum);
                    //System.out.println("bgdnewintprocessedSum :: " + bgdnewintprocessedSum);
                   //bgdnewintprocessedSum = bgdnewintprocessedSum.setScale(2, BigDecimal.ROUND_HALF_UP );
                    // processedSum = bgdnewintprocessedSum.doubleValue();
                    //System.out.println("processedSum :: " + processedSum);
                    com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().updateFileUpload(f.getId(), successList.size(), errorList.size(), processedSum, fileCount);
                  ///  System.out.println("Success Count: " + successList.size() + " Failed Count: " + errorList.size() + " updating" + f.getId());
                    String mailBody = "	<style type=\"text/css\">"
                            + "	<!--"
                            + "	.style2 {"
                            + "		font-family: Verdana, Arial, Helvetica, sans-serif;"
                            + "		font-size: 14px;"
                            + "	}"
                            + "	-->"
                            + "	</style>"
                            + "	 <p class=\"style2\">Hello, </p>"
                            + "	<p class=\"style2\">Your batch upload with reference #" + f.getReferenceNumber() + " has been successfully processed <br>"
                            + "   <strong>Below are the details: </strong></p>"
                            + "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
                            + "	  <tr>"
                            + "	    <td width=\"50%\"><span class=\"style2\">Failure Count:</span></td>"
                            + "	    <td width=\"50%\"><span class=\"style2\">" + errorList.size() + "</span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Success Count:</span></td>"
                            + "	    <td><span class=\"style2\">" + successList.size() + " </span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Processed Sum:</span></td>"
                            + "	    <td><span class=\"style2\">" + processedSum + " </span></td>"
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                            + "	</table>"
                            + "	<p class=\"style2\"><br>"
                            + "	  Thanks </p> ";

                   // MailBean MB = HelperUtil.createMailBean();
                    MailBean MB = HelperUtil.getMailConfig();
                    MB.setSubject(SavingsDefinitions.BULK_UPLOAD_PROCESSED_SUBJECT);
                    MB.setToemail(HelperUtil.getUserEmail(f.getUserId()));

                    MB.setMailBody(mailBody);

                    try {
                        //send email
                        HelperUtil.sendMail(MB);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                 // }
                    } catch (FileNotFoundException e) {
                         e.printStackTrace();
                         System.out.println("File does not exist");
                        FileUploadError fileError = new FileUploadError();
                        fileError.setBranchId(f.getBranchId());
                        fileError.setCompanyId(f.getCompanyId());
                        fileError.setDescription(f.getLocation()+ " missing on ther server");
                        fileError.setFileName(f.getFileName());
                        fileError.setProcessedDate(new Date());
                        fileError.setLocation(f.getLocation());
                        fileError.setProductId(f.getProductId());
                        fileError.setReferenceNumber(f.getReferenceNumber());
                        fileError.setUserId(f.getUserId());
                        
                        com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadErrorDAO().save(fileError);
                        com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadErrorDAO().updateFileUploadStatus(f.getId());
                       _logger.error("SavingsCron", e);
                  } 
                }//end  of for loop
         
            }
         ///////   t.commit();
        } catch (PersistentException e) {
           // FileUploadError fError = new FileUploadError();
            
            e.printStackTrace();
         //   t.rollback();
            _logger.error("SavingsCron", e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
          //  t.rollback();
            _logger.error("SavingsCron", e);
        } catch (IOException e) {
            e.printStackTrace();
          //  t.rollback();
            _logger.error("SavingsCron", e);
        }

    }

}
