/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.utility;

import com.sift.gl.model.Account;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.FileUploadError;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.loan.utility.MailBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.orm.PersistentTransaction;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author logzy
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SavingsCron extends QuartzJobBean {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingsCron.class);
    PersistentTransaction t = null;
    int noofrecs = 0;
    int noofrecsok = 0;
    int noofrecsfailed = 0;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            readExcel();
        } catch (Exception e) {
            _logger.error("SavingsCron", e);
        }

    }

    public void readExcel() throws PersistentException {
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

            t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            FileUploadCriteria criteria = new FileUploadCriteria();
            String fileState= "F";
           criteria.add(Restrictions.eq("status", status));
           criteria.add(Restrictions.le("attemptCount", limit));
           criteria.add(Restrictions.eq("state", fileState));

           System.out.println("Attempt Limit: "+limit);
            List<FileUpload> uploads = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);

            
            if (uploads.size() > 0) {

                Workbook workbook = null;
                for (FileUpload f : uploads) {
                    try{
                    System.out.println("File name: "+f.getLocation()+" Count: "+f.getAttemptCount());
                    int successCount = 0;
                    int failureCount = 0;
                    float processedSum = 0f;
                    int fileCount = 0;
                    int errorCount = 0;
                    List<SavingsError> errorList = new ArrayList<>();
                    List<Savings> successList = new ArrayList<>();

                    boolean isAccountValid = false;
                    boolean isDateValid = false;
                    boolean isAccountNameValid = true;
                    
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

                    if (extension.equalsIgnoreCase("xls")) {
                        workbook = new HSSFWorkbook(file);
                    } else {
                        workbook = new XSSFWorkbook(file);
                    }
                    int numberOfSheets = workbook.getNumberOfSheets();

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
                            Double amount = 0.0;
                            String reformattedStr = "";
                            String date = "";
                            //String uploader=uploaderStr;
                            String uploadError = "N";
                            String validationMsg = "";
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
                                if (COLUMN_INDEX == 5) {
                                    COLUMN_INDEX = 0;
                                }
                                COLUMN_INDEX += 1;
                                //get the cell object
                                Cell cell = cellIterator.next();

                                //if(loanCaseIdStr.equalsIgnoreCase("")){
                                if (COLUMN_INDEX == 1) {
                                    try {
                                        accountNumber = cell.getStringCellValue().trim();
                                        boolean valid = EasyCoopFinValidator.checkIfAccountExists(accountNumber, f.getBranchId(), f.getCompanyId());
                                        //System.out.println("valid:="+valid);

                                        if (!valid) {
                                            validationMsg += "; Invalid Account Number";
                                            isAccountValid = false;
                                        }

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        validationMsg += "; Invalid Account Number";
                                        isAccountValid = false;
                                    }
                                    //}else if(memberNoStr.equalsIgnoreCase("")){
                                } else if (COLUMN_INDEX == 2) {
                                    //2nd column MEMER NO
                                    try {
                                        accountName = cell.getStringCellValue().trim();

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        validationMsg += "; Invalid Account Name";
                                    }
                                    //}else if(amountStr.equalsIgnoreCase("")){
                                } else if (COLUMN_INDEX == 3) {
                                    //3rd column  REPAYMENT AMOUNT 
                                    try {
                                        narrative = cell.getStringCellValue().trim();
                                    } catch (Exception ex) {
                                        validationMsg += "; Invalid Narration";
                                    }

                                    //}else if(uploadDateStr.equalsIgnoreCase("")){
                                } else if (COLUMN_INDEX == 4) {
                                    String strAmount = "";
                                    try {
                                        amount = cell.getNumericCellValue();
                                    } catch (NumberFormatException ex) {
                                        validationMsg += "; Invalid Upload Amount";
                                    } catch (Exception ex) {
                                        validationMsg += "; Invalid Upload Amount";
                                    }

                                } else if (COLUMN_INDEX == 5) {
                                    //4th Column  REPAYMENT DATE
                                    try {
                                        date = row.getCell(4).getStringCellValue();
                                        if (EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy")) {

                                            reformattedStr = myFormat.format(fromUser.parse(date));
                                            trx_date = myFormat.parse(reformattedStr);
                                            isDateValid = true;
                                        } else {
                                            isDateValid = false;
                                            validationMsg += "; Invalid Date";
                                        }
                                    } catch (Exception ex) {
                                        validationMsg += "; Invalid Date";
                                        isDateValid = false;
                                    }

                                }
                            }//end of cell iterator

                            if (validationMsg.length() > 0) {
                                uploadError = "Y";
                            }

                            System.out.println("Row: " + rowCount + " Account: " + accountNumber + " narrative: " + narrative + " amount:" + amount + " date: " + date + " file branch" + f.getBranchId() + " file company" + f.getCompanyId() + " error:" + validationMsg);

                            if ("N".equals(uploadError)) {
                                if (accountNumber != "") {
                                    Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber, f.getBranchId(), f.getCompanyId());
                                    MemberCriteria mcriteria = new MemberCriteria();

                                    mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
                                    Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
                                    Savings saving = new Savings();
                                    saving.setAccountNumber(accountNumber);
                                    saving.setAmount(amount.floatValue());
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

                                    processedSum += amount.floatValue();
                                    successList.add(saving);
                                }
                            } else if ("Y".equals(uploadError)) {
                                errorCount += 1;
                                String error = "";
                                if (!isAccountValid) {
                                    error += "Invalid Account Number";
                                }
                                if (!isDateValid) {
                                    error += "Invalid Date";
                                }
                                if (!isAccountNameValid) {
                                    error += "Invalid Account Name";
                                }
                                System.out.println("Error found" + error);
                                Member m = null;
                                int memberId = 0;
                                try {
                                    Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber, f.getBranchId(), f.getCompanyId());
                                    MemberCriteria mcriteria = new MemberCriteria();

                                    mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
                                    m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
                                    memberId = m.getId();
                                } catch (NullPointerException ex) {
                                    memberId = 0;
                                } catch (Exception ex) {
                                    memberId = 0;
                                }

                                SavingsError sError = new SavingsError();
                                sError.setAccountNumber(accountNumber);
                                sError.setAmount(amount.floatValue());
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
                                sError.setErrorMessage(error);

                                errorList.add(sError);
                                //uploadedListFailed.add(c);
                            }

                            int totalCount = successList.size() + errorList.size();

                            uploadError = "N";
                            validationMsg = "";

                        }//end of rows iterator                
                        System.out.println("End of sheet" + i);
                    }//end of sheets for loop            
                    // file.close();
                    System.out.println("Success Count: " + successList.size() + " Failed Count: " + errorList.size() + " updating" + f.getId());
                    System.out.println("End of file " + f.getReferenceNumber());
                    if (successList.size() > 0) {
                        System.out.println("Found " + successList.size() + " successful");
                        for (Savings sav : successList) {
                            com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(sav);
                        }
                    }
                    if (errorList.size() > 0) {
                        System.out.println("Found " + errorList.size() + " failed");
                        for (SavingsError sE : errorList) {
                            com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsErrorDAO().save(sE);
                        }
                    }
                 //   com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().updateFileUpload(f.getId(), successList.size(), errorList.size(), processedSum, fileCount);
                    System.out.println("Success Count: " + successList.size() + " Failed Count: " + errorList.size() + " updating" + f.getId());
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

                    MailBean MB = HelperUtil.createMailBean();
                    MB.setSubject(SavingsDefinitions.BULK_UPLOAD_PROCESSED_SUBJECT);
                    MB.setToemail(HelperUtil.getUserEmail(f.getUserId()));

                    MB.setMailBody(mailBody);

                    try {
                        //send email
                        HelperUtil.sendMail(MB);
                    } catch (Exception ex) {

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
            t.commit();
        } catch (PersistentException e) {
           // FileUploadError fError = new FileUploadError();
            
            e.printStackTrace();
           // t.rollback();
            _logger.error("SavingsCron", e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
           // t.rollback();
            _logger.error("SavingsCron", e);
        } catch (IOException e) {
            e.printStackTrace();
            //t.rollback();
            _logger.error("SavingsCron", e);
        }

    }

}
