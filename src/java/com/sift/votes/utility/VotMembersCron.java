/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.utility;


import com.sift.votes.model.VotFileUpload;
import com.sift.votes.model.VotFileUploadCriteria;
import com.sift.votes.model.VotFileUploadError;
import com.sift.votes.model.VotMembers;
import com.sift.votes.model.VotMembersErrors;
import com.sift.votes.service.VotFileUploadService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
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
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson Akpos
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotMembersCron extends  QuartzJobBean{
      @Autowired
     private VotFileUploadService votFileUploadService;
      
     private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotMembersCron.class);
    PersistentTransaction t = null;
    int noofrecs = 0;
    int noofrecsok = 0;
    int noofrecsfailed = 0;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            readExcel();
        } catch (Exception e) {
            _logger.error("VotMembersCron", e);
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
        byte isBatch = 1;
        //List<SavingsError> errorList = new ArrayList<>();
        //List<Savings> successList = new ArrayList<>();
       try {
            javax.naming.Context ctx = new javax.naming.InitialContext();

           limit = (Integer) ctx.lookup("java:comp/env/cron.limit");
           

       } catch (Exception ex) {
           limit = 0;
            _logger.error("VotMember cron", ex);
      }


        try {

            t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            VotFileUploadCriteria criteria = new VotFileUploadCriteria();
           // String fileState= "F";
           criteria.add(Restrictions.eq("status", status));
           criteria.add(Restrictions.le("attemptCount", limit));
           //criteria.add(Restrictions.eq("state", fileState));
          // criteria.add(Restrictions.eq("companyId", companyId));
           //criteria.add(Restrictions.eq("branchId", branchId));
           //criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
           //System.out.println("referenceNumber in SavingsTaskth :: " + referenceNumber);

           System.out.println("Attempt Limit: "+limit);
           //List<VotFileUpload> uploads = com.sift.votes.model.VotDAOFactory.getVotDAOFactory().getVotFileUploadDAO().listAllVotFileUploadByCriteria(criteria);
     List<VotFileUpload> uploads = votFileUploadService.listAllVotFileUploadByStatus(status);
            
            if (uploads.size() > 0) {
              //System.out.println("inner : "+ uploads.size());
                Workbook workbook = null;
                for (VotFileUpload f : uploads) {
                    try{
                   // System.out.println("File name: "+f.getLocation()+" Count: "+f.getAttemptCount());
                    int successCount = 0;
                    int failureCount = 0;
                    //float processedSum = 0f;
                    //double processedSum = 0;
                    BigDecimal processedSumTemp = new BigDecimal("0.0");
                    BigDecimal processedSum = new BigDecimal("0.0");
                    MathContext mc = new MathContext(4);
                    int fileCount = 0;
                    int errorCount = 0;
                    List<VotMembersErrors> errorList = new ArrayList<>();
                    List<VotMembers> successList = new ArrayList<>();

                    boolean isAccountValid = false;
                    boolean isDateValid = false;
                    boolean isAccountNameValid = true;
                    
                 
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
                            //String accountNumber = "";
                           
                            int memberrefid=0;
                            String userid = "";
                            String firstname = "";
                            String middlename="";
                            String surname="";
                            String email="";
                            String Phone="";
                            int votes=0;
                           
                            //Double amount = 0.0;
                           // BigDecimal amount = new BigDecimal("0.0");
                            //String reformattedStr = "";
                            //String date = "";
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
                                if (COLUMN_INDEX == 8) {
                                    COLUMN_INDEX = 0;
                                }
                                COLUMN_INDEX += 1;
                                //get the cell object
                                Cell cell = cellIterator.next();

                                //if(loanCaseIdStr.equalsIgnoreCase("")){
                                if (COLUMN_INDEX == 1) {
                                    try {
                                        memberrefid = Integer.parseInt(cell.getStringCellValue().trim());
                                       // boolean valid = EasyCoopFinValidator.checkIfAccountExists(accountNumber, f.getBranchId(), f.getCompanyId());
                                        //System.out.println("rowCount :=" + rowCount + " valid:="+valid + " accountNumber :: " + accountNumber);

                                       // if (!valid) {
                                           // validationMsg += "; Invalid Account Number";
                                           // isAccountValid = false;
                                        //}

                                   } catch (Exception ex) {
                                     ex.printStackTrace();
                                      validationMsg += "; Invalid memberrefid";
                                        //isAccountValid = false;
                                   }
                                    //}else if(memberNoStr.equalsIgnoreCase("")){
                                }  else if (COLUMN_INDEX == 2) {
                                    //2nd column  for userid
                                    try {
                                        userid = cell.getStringCellValue().trim();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        validationMsg += "; Invalid userid";
                                    }

                                    //}else if(uploadDateStr.equalsIgnoreCase("")){
                                }
                                 else if (COLUMN_INDEX == 3) {
                                    //2nd column  for firstname
                                    try {
                                        firstname = cell.getStringCellValue().trim();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        validationMsg += "; Invalid Firstname";
                                    }

                                    //}else if(uploadDateStr.equalsIgnoreCase("")){
                                } else if (COLUMN_INDEX == 4) {
                                       //3rd column for middlename
                                   
                                        //amount = cell.getNumericCellValue();
                                        
                                        try {
                                         //   String.valueOf(cell.getNumericCellValue());
                                   
                                        middlename= cell.getStringCellValue().trim();
                                       
                                 } catch (NumberFormatException ex) {
                                    ex.printStackTrace();
                                     validationMsg += "; Invalid middlename";
                                 }
                                        
                                        
                                } else if (COLUMN_INDEX == 5) {
                                    //4th Column  for surname
                                    try {
                                        surname = cell.getStringCellValue().trim();
                                     
                                    } catch (Exception ex) {
                                      ex.printStackTrace();
                                      validationMsg += "; Invalid surname";
                                    }

                                }
                                 else if (COLUMN_INDEX == 6) {
                                    //5th Column  for Email
                                    try {
                                        email = cell.getStringCellValue().trim();
                                     
                                    } catch (Exception ex) {
                                      ex.printStackTrace();
                                       validationMsg += "; Invalid email";
                                    }

                                }
                                 
                                      else if (COLUMN_INDEX == 7) {
                                    // 6th Column  Phone NUMBER
                                    try {
                                        Phone = cell.getStringCellValue().trim();
                                     
                                    } catch (Exception ex) {
                                      ex.printStackTrace();
                                       validationMsg += "; Invalid  phone number";
                                    }

                                }
                                     else if (COLUMN_INDEX == 8) {
                                    //7th Column  votes
                                    try {
                                        votes = Integer.parseInt(cell.getStringCellValue());
                                     
                                    } catch (Exception ex) {
                                      ex.printStackTrace();
                                       validationMsg += "; Invalid  votes";
                                    }

                                }
                            }//end of cell iterator

                            if (validationMsg.length() > 0) {
                                uploadError = "Y";
                            }

                            System.out.println("Row: " + rowCount +" userid: " + userid + " firstname:" + firstname + " middlename: " + middlename + " surname: " + surname + " email : " + email + "Phone: "+ Phone );

                            if ("N".equals(uploadError)) {
                                if (userid != "") {
                                   // //Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber, f.getBranchId(), f.getCompanyId());
                                   // MemberCriteria mcriteria = new MemberCriteria();

                                   // mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
                                    //Just added 
                                    //mcriteria.add(Restrictions.eq("companyId", f.getCompanyId()));
                                   // mcriteria.add(Restrictions.eq("branchId", f.getBranchId()));
                                   // Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
                                    VotMembers votmembers = new VotMembers();
                                     votmembers.setUserid(userid);
                                     votmembers.setMemberrefid(memberrefid);
                                     votmembers.setFirstname(firstname);
                                     votmembers.setSurname(surname);
                                     votmembers.setMiddlename(middlename);
                                     votmembers.setEmail(email);
                                     votmembers.setPhone(Phone);
                                     votmembers.setBranchid(0);
                                     votmembers.setCompanyid(f.getCompanyId());
                                     votmembers.setVoteunits(votes);
                                     votmembers.setConcluded("N");
                                     
                                    successList.add(votmembers);
                                }
                            } else if ("Y".equals(uploadError)) {
                                errorCount += 1;
                                String error = "";
                              
                                VotMembersErrors vError = new VotMembersErrors();
                                vError.setBranchid(0);
                                 vError.setCompanyid(f.getCompanyId());
                                  vError.setConcluded("N");
                                   vError.setEmail(email);
                                    vError.setFirstname(firstname);
                                     vError.setMemberrefid(memberrefid);
                                      vError.setMiddlename(middlename);
                                       vError.setPhone(Phone);
                                        vError.setSurname(surname);
                                         vError.setVoteunits(votes);
                                          vError.setUserid(userid);
                       
                                
                               

                                errorList.add(vError);
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
                        for (VotMembers votmemb : successList) {
                          //  com.sift.votes.model.VotDAOFactory.getVotDAOFactory().getVotMembersDAO().save(votmemb);
                        }
                    }
                    if (errorList.size() > 0) {
                     /////   System.out.println("Found " + errorList.size() + " failed");
                        for (VotMembersErrors vE : errorList) {
                            //com.sift.votes.model.VotDAOFactory.getVotDAOFactory().getVotMembersErrorDAO().save(vE);
                        }
                    }
                    //BigDecimal bgdnewintprocessedSum = new BigDecimal(processedSum);
                    //System.out.println("bgdnewintprocessedSum :: " + bgdnewintprocessedSum);
                   //bgdnewintprocessedSum = bgdnewintprocessedSum.setScale(2, BigDecimal.ROUND_HALF_UP );
                    // processedSum = bgdnewintprocessedSum.doubleValue();
                    //System.out.println("processedSum :: " + processedSum);
                    com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().updateFileUpload(f.getId(), successList.size(), errorList.size(), processedSum, fileCount);
                  ///  System.out.println("Success Count: " + successList.size() + " Failed Count: " + errorList.size() + " updating" + f.getId());
                   

                
                 // }
                    } catch (FileNotFoundException e) {
                         e.printStackTrace();
                         System.out.println("File does not exist");
                        VotFileUploadError fileError = new VotFileUploadError();
                        fileError.setBranchId(f.getBranchId());
                        fileError.setCompanyId(f.getCompanyId());
                        fileError.setDescription(f.getLocation()+ " missing on ther server");
                        fileError.setFileName(f.getFileName());
                        fileError.setProcessedDate(new Date());
                        fileError.setLocation(f.getLocation());
                        //fileError.setProductId(f.getProductId());
                        fileError.setReferenceNumber(f.getReferenceNumber());
                        fileError.setUserId(f.getUserId());
                        
                       // com.sift.votes.model.VotDAOFactory.getVotDAOFactory().getFileUploadErrorDAO().save(fileError);
                       // com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadErrorDAO().updateFileUploadStatus(f.getId());
                       _logger.error("VotMembersCron", e);
                  } 
                }//end  of for loop
         
            }
            t.commit();
        } catch (PersistentException e) {
           // FileUploadError fError = new FileUploadError();
            
            e.printStackTrace();
         //   t.rollback();
            _logger.error("VotMembersCron", e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
          //  t.rollback();
            _logger.error("VotmembersCron", e);
        } catch (IOException e) {
            e.printStackTrace();
          //  t.rollback();
            _logger.error("VotMembersCron", e);
        }

    }
    
}
