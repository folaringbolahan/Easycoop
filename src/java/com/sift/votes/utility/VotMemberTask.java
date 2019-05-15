/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.utility;

import com.fasterxml.jackson.databind.util.Setter;
import com.sift.admin.bean.UserBean;

import com.sift.votes.dao.VotMembersDaoImpl;
import com.sift.votes.model.VotFileUpload;
import com.sift.votes.model.VotFileUploadCriteria;
import com.sift.votes.model.VotFileUploadError;
import com.sift.votes.model.VotMembers;
import com.sift.votes.model.VotMemberFile;
import com.sift.votes.model.VotMembersErrors;
import com.sift.votes.service.VotFileUploadService;
import com.sift.votes.service.VotMemberFileService;
import com.sift.votes.service.VotMemberService;
import com.sift.votes.service.VotMemberServiceImpl;
import com.sift.votes.service.VotMembersErrorsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

import java.util.ArrayList;
import java.util.Date;

import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Restrictions;

import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.PersistentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author Nelson Akpos
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotMemberTask {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VotMemberTask.class);
    PersistentTransaction t = null;
    int noofrecs = 0;
    int noofrecsok = 0;
    int noofrecsfailed = 0;
    private TaskExecutor taskExecutor;
    private Object companyId;
    AgmHelperUtility membObj = new AgmHelperUtility();

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public class readExcelTask implements Runnable {

        private int branchId;
        private int companyId;
        private String referenceNumber;
        private int companyrefid;
        private VotMemberFileService mvotMemberfileService;
        private VotMemberService mvotMemberService;
        private VotFileUploadService mvotFileUploadService;
        private int agmid;
        private String loggeduser;
        private VotBeanMapperUtility mvotutility;
        private AgmHelperUtility magmhelperUTIL;
        private VotMembersErrorsService mvotMembersErrorsService;

        //public readExcelTask(int branchId, int companyId, int companyrefid, int agmid, String referenceNumber, VotFileUploadService votFileUploadService, VotMemberService votMemberService, String loggeduser, VotBeanMapperUtility votutility, AgmHelperUtility agmhelperUTIL,VotMembersErrorsService votMembersErrorsService) {
       public readExcelTask(int branchId, int companyId, int companyrefid, int agmid, String referenceNumber, VotFileUploadService votFileUploadService, VotMemberFileService votMemberFileService, String loggeduser, VotBeanMapperUtility votutility, AgmHelperUtility agmhelperUTIL,VotMembersErrorsService votMembersErrorsService) {
        
            this.branchId = branchId;
            this.companyId = companyId;
            this.referenceNumber = referenceNumber;
            this.mvotMemberfileService = votMemberFileService;
            //this.mvotMemberService = votMemberService;
            this.mvotFileUploadService = votFileUploadService;
            this.companyrefid = companyrefid;
            this.agmid = agmid;
            this.loggeduser = loggeduser;
            this.magmhelperUTIL = agmhelperUTIL;
            this.mvotutility = votutility;
            this.mvotMembersErrorsService= votMembersErrorsService;
        }

        // public void setvotMemberService(VotMemberService votMemberService) {
        //     mvotMemberService = votMemberService;
        // }
        public void run() {
            try {

                readExcel(branchId, companyId, referenceNumber, companyrefid, agmid, mvotFileUploadService, mvotMemberfileService, loggeduser, mvotutility, magmhelperUTIL,mvotMembersErrorsService);
                ////
                ///GlPostTaskth glposttsk = new GlPostTaskth();
                //glposttsk.initialize();
            } catch (Exception e) {
                e.printStackTrace();
               
            }
        }
    }

    public void readExcelasync(int branchId, int companyId, String referenceNumber, int companyrefid, int agmid, VotFileUploadService votFileUploadService, VotMemberFileService votMemberFileService, String loggeduser, VotBeanMapperUtility mvotutility, AgmHelperUtility magmhelperUTIL,VotMembersErrorsService mvotMembersErrorsService) {

        taskExecutor.execute(new readExcelTask(branchId, companyId, companyrefid, agmid, referenceNumber, votFileUploadService, votMemberFileService, loggeduser, mvotutility, magmhelperUTIL,mvotMembersErrorsService));
    }

    public void readExcel(int branchId, int companyId, String referenceNumber, int companyrefid, int agmid, VotFileUploadService mvotFileUploadService, VotMemberFileService mvotMemberfileService, String loggeduser, VotBeanMapperUtility mvotutility, AgmHelperUtility magmhelperUTIL, VotMembersErrorsService mvotMembersErrorsService) throws PersistentException {

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

            // t = com.sift.votes.model.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();

            //List<VotFileUpload> uploads = votFileUploadService.listAllVotFileUploadByCriteria(companyId,referenceNumber);
            VotFileUploadCriteria criteria = new VotFileUploadCriteria();
            String fileState = "F";
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.le("attemptCount", limit));
            criteria.add(Restrictions.eq("state", fileState));
            criteria.add(Restrictions.eq("companyId", companyId));
            // criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
            //System.out.println("referenceNumber in SavingsTaskth :: " + referenceNumber);

            System.out.println("Attempt Limit: " + limit);
            System.out.println("the company id is " + companyId);
            System.out.println("the referance number for read excel task is " + referenceNumber);
            System.out.println("the branch id  is " + branchId);
            System.out.println("the status is    " + status);
            //  System.out.println("the criteria id  is "+ criteria.toString());

            //List<VotFileUpload> uploads = new ArrayList<>();
           /*otFileUpload upload_model = new VotFileUpload();
             upload_model.setCompanyId(companyId);
             upload_model.setBranchId(branchId);
             upload_model.setReferenceNumber("1521903049278");
             upload_model.setFileName("Copy of Agm_Members_Templ.xls");
             upload_model.setLocation("C:/easycoopfinuploads/uploadreports/tmpfiles/Copy of Agm_Members_Templ_1521903049278.xls");
             uploads.add(upload_model);*/
           // List<VotFileUpload> uploads = mvotFileUploadService.listAllVotFileUploadByCriteria(companyId, referenceNumber);
            List<VotFileUpload> uploads = mvotFileUploadService.listAllVotFileUploadByAgmCriteria(agmid, referenceNumber);

            //List<VotFileUpload> uploads = votFileUploadService.listAllVotFileUploadByCriteria(companyId, referenceNumber);
            // List<VotFileUpload> uploads = votFileUploadService.listAllVotFileUploadByCriteria(companyId,referenceNumber);

            System.out.println("the upload size is " + uploads.size());
            if (uploads.size() > 0) {
                //System.out.println("inner : "+ uploads.size());
                Workbook workbook;
                for (VotFileUpload f : uploads) {
                    try {
                        // System.out.println("File name: "+f.getLocation()+" Count: "+f.getAttemptCount());
                        int successCount = 0;
                        int failureCount = 0;

                        BigDecimal processedSum = new BigDecimal("0.0");
                        MathContext mc = new MathContext(4);
                        int fileCount = 0;
                        int errorCount = 0;
                        List<VotMembersErrors> errorList = new ArrayList<>();
                        List<VotMemberFile> successList = new ArrayList<>();

                        boolean isMemberrefidValid = false;
                        boolean isDateValid = false;
                        boolean isAccountNameValid = true;


                        FileInputStream file = new FileInputStream(f.getLocation());
                        String extension = FilenameUtils.getExtension(f.getFileName());
                        /// System.out.println("got here2: ");
                        if (file != null) {
                            System.out.println("file not null");
                        } else {
                            System.out.println("the file is null");
                        }

                        System.out.println("the extension is " + extension);
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

                                int memberrefid = 0;
                                //String userid = "";
                                String firstname = "";
                                String middlename = "";
                                String surname = "";
                                String email = "";
                                String Phone = "";
                                int voteunits = 0;
                                
                                String memberrefidMsg = "";
                                //String firstnameMsg = "";
                                //String middlenameMsg = "";
                                //String surnameMsg = "";
                                String emailMsg = "";
                                //String PhoneMsg = "";
                                String voteunitsMsg = "";

                                //Double amount = 0.0;
                                // BigDecimal amount = new BigDecimal("0.0");
                                //String reformattedStr = "";
                                //String date = "";
                                //String uploader=uploaderStr;
                                String uploadError = "N";
                                String validationMsg = "";
                                int scheduleIdInt = -1;
                                boolean validname = false;
                                //Get the row object
                                Row row = rowIterator.next();
                                System.out.println("print the row number " + row.getRowNum());
                                if (row.getRowNum() == 0) {
                                    continue;
                                    //just skip the rows if row number is 0 
                                }

                                rowCount += 1;

                                //Every row has columns, get the column iterator and iterate over them
                                Iterator<Cell> cellIterator = row.cellIterator();
                                    boolean validfirstname = true;
                                    boolean validsurname = true;
                                    boolean emailexist;
                                while (cellIterator.hasNext()) {
                                    validname = false;
                                    validfirstname = true;
                                    validsurname = true;
                                    
                                   // if (COLUMN_INDEX == 7) {
                                   //     COLUMN_INDEX = 0;
                                   // }
                                   // COLUMN_INDEX += 1;
                                    //get the cell object
                                    Cell cell = cellIterator.next();

                                    //if(loanCaseIdStr.equalsIgnoreCase("")){
                                  //  if (COLUMN_INDEX == 1 ) {
                                        try {
                                            memberrefid = (int) row.getCell(0).getNumericCellValue();
                                          
                                            //System.out.println("member ref id :: "+  memberrefid);
                                             //boolean valid = mvotMemberService.getMemberrefidValidity(memberrefid,agmid);
                                           // System.out.println("rowCount :=" + rowCount + " valid:="+valid + " Memberrefid :: " +  memberrefid);

                                           // if (valid==false) {
                                           //  validationMsg += "; memberrefid already exist for this agm";
                                           // isMemberrefidValid = false;
                                           // }

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            memberrefidMsg = ";invalid value for member ref id column";
                                           //isMemberrefidValid  = false;
                                        }
                                        //}else if(memberNoStr.equalsIgnoreCase("")){
                                  //  }   if (COLUMN_INDEX == 2 ) {
                                       
                                        //2nd column  for firstname
                                        try {
                                            firstname = row.getCell(1).getStringCellValue().trim();
                                            if("".equals(firstname)){
                                             validfirstname = false;
                                            }
                                        } catch (NullPointerException ex) {
                                            ex.printStackTrace();
                                            validfirstname = false;
                                            //validationMsg += "; Invalid surname";
                                              
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            //validationMsg += "; invalid value for first name";
                                        }

                                        //}else if(uploadDateStr.equalsIgnoreCase("")){
                                   // } else if (COLUMN_INDEX == 3) {
                                       
                                        //3rd column for middlename

                                        //amount = cell.getNumericCellValue();

                                        try {
                                            //   String.valueOf(cell.getNumericCellValue());
                                          
		                  
                                            middlename = row.getCell(2).getStringCellValue().trim();

                                        } catch (Exception ex ) {
                                            ex.printStackTrace();
                                            //validationMsg += "; Invalid middlename";
                                        }
                                     

                                  //  } else if (COLUMN_INDEX == 4 ) {
                                      
                                        //4th Column  for surname
                                        try {
                                            surname = row.getCell(3).getStringCellValue().trim();
                                               if("".equals(surname)){
                                             validsurname = false;
                                            }
                                         } catch (NullPointerException ex) {
                                            ex.printStackTrace();
                                            validsurname = false;
                                            //validationMsg += "; Invalid surname";
                                              
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            //validationMsg += "; Invalid surname";
                                        }

                                //    } else if (COLUMN_INDEX == 5 ) {
                                        //5th Column  for Email
                                        try {
                                            email = row.getCell(4).getStringCellValue().trim();
                                             System.out.println("agmid  the while loop "+ agmid);
                                          System.out.println("email in the while loop "+ email);
                                          boolean email_format=magmhelperUTIL.isValidEmailAddress(email);
                                          if(email_format==false){
                                            emailMsg= "; invalid email format";
                                            System.out.println("this email format is invalid ");
                                         }
                                          emailexist=magmhelperUTIL.membersEmailValidity(email, agmid);
                                           System.out.println("VALIDITY VALUE "+ emailexist);
                                          if(emailexist==true){
                                           
                                            emailMsg= "; email already exist";
                                             System.out.println("this email already axist ");
                                          }
                                          if("".equals(email)){
                                             emailMsg= "; Invalid email";
                                          }
                                         
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            emailMsg= "; Invalid email";
                                        }

                                 //   } else if (COLUMN_INDEX == 6 ) {
                                        // 6th Column  Phone NUMBER
                                        try {
                                            Phone = row.getCell(5).getStringCellValue().trim();

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            //validationMsg += "; Invalid  phone number";
                                        }

                                  //  } else if (COLUMN_INDEX == 7 ) {
                                        //7th Column  votes
                                        try {
                                            voteunits = (int) row.getCell(6).getNumericCellValue();
                                            if (voteunits<=0) {
                                               voteunitsMsg= "; Invalid votes: must be >0"; 
                                            }

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            voteunitsMsg= "; Invalid votes";
                                        }
                                        
                                    }
                                
                                    validationMsg = memberrefidMsg + emailMsg + voteunitsMsg;
                                
                                
                                    if((validfirstname== false) && (validsurname==false )){
                                          validationMsg+=";invalid firstname or surname";
                                    }
                                
                                         
                                //}//end of cell iterator

                                if (validationMsg.length() > 0) {
                                    System.out.println("the validation message is greater than zero ");
                                    uploadError = "Y";
                                }
                                else if (validationMsg.length() == 0) 
                                {
                                    uploadError = "N";
                                }    
                                 
                                System.out.println("Row: " + rowCount + " memberrefid: " + memberrefid + " firstname:" + firstname + " middlename: " + middlename + " surname: " + surname + " email : " + email + "Phone: " + Phone + "votes :" + voteunits);
                               // boolean validity= mvotMemberService.getMemberrefidValidity(memberrefid, agmid);
                               // System.out.println("value of validity "+ validity);
                                if ("N".equals(uploadError) ) {
                                    if ( email != "" ) {
                                       
                                        System.out.println("agmid --" + agmid + "companyid " + companyrefid);
                                       /* VotMembers votmembers = new VotMembers();
                                        votmembers.setUserid(email);
                                        votmembers.setMemberrefid(memberrefid);
                                        votmembers.setFirstname(firstname);
                                        votmembers.setSurname(surname);
                                        votmembers.setMiddlename(middlename);
                                        votmembers.setEmail(email);
                                        votmembers.setPhone(Phone);
                                        votmembers.setAgmid(agmid);
                                        votmembers.setBranchid(0);
                                        votmembers.setCompanyid(companyrefid);
                                        votmembers.setVoteunits(voteunits);
                                        votmembers.setConcluded("N");

                                        successList.add(votmembers);
                                        */
                                        VotMemberFile votmemberfile = new VotMemberFile();
                                        votmemberfile.setUserid(email);
                                        votmemberfile.setMemberrefid(memberrefid);
                                        votmemberfile.setFirstname(firstname);
                                        votmemberfile.setSurname(surname);
                                        votmemberfile.setMiddlename(middlename);
                                        votmemberfile.setEmail(email);
                                        votmemberfile.setPhone(Phone);
                                        votmemberfile.setAgmid(agmid);
                                        votmemberfile.setBranchid(0);
                                        votmemberfile.setCompanyid(companyrefid);
                                        votmemberfile.setVoteunits(voteunits);
                                        votmemberfile.setConcluded("N");
                                        votmemberfile.setFile_ref(referenceNumber);
                                        successList.add(votmemberfile);
                                    }
                                    
                                    
                                } else if ("Y".equals(uploadError) ) {
                                    
                                    errorCount += 1;
                                    String error = "";
                        System.out.println(" member ref id in the error block "+ memberrefid );
                        System.out.println(" agm id in the error block "+ agmid );
                                    VotMembersErrors vError = new VotMembersErrors();
                                    vError.setMemberrefid(memberrefid);
                                    vError.setBranchid(0);
                                    vError.setCompanyid(companyrefid);
                                    vError.setConcluded("N");
                                    vError.setEmail(email);
                                    vError.setFirstname(firstname);
                                    vError.setMiddlename(middlename);
                                    vError.setPhone(Phone);
                                    vError.setAgmid(agmid);
                                    vError.setSurname(surname);
                                    vError.setVoteunits(voteunits);
                                    vError.setUserid(email);
                                    vError.setReferenceid(referenceNumber);
                                    if (validationMsg.length() > 255) {
                                       vError.setErrormessage(validationMsg.substring(0, 254));
                                    }
                                    else if (validationMsg.length() <= 255) 
                                    {
                                       vError.setErrormessage(validationMsg);
                                    }   
                                    
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
                        System.out.println("Success Count: " + successList.size() + " Failed Count: " + errorList.size() + " record id" + f.getId());
                        System.out.println("End of file " + f.getReferenceNumber());
                        int rec_id=f.getId();
                        int success= successList.size();
                        int failed= errorList.size();
                        String succesref_file=f.getReferenceNumber();
                        //update Vot_Fileupload record
                        magmhelperUTIL.updateVotFileuploadRec(rec_id,failed,success);
                        
                        if (successList.size() > 0) {
                            System.out.println("Size if succcess list  " + successList.size());
                            for (VotMemberFile votmemb : successList) {

                                //com.sift.votes.model.VotDAOFactory.getVotDAOFactory().getVotMembersDAO().save(votmemb);
                                System.out.println("the first name is " + votmemb.getFirstname() + "vote units " + votmemb.getVoteunits());
                                try {
                                    mvotMemberfileService.saveMemberFile(votmemb);
                                    // String loggeduser=req.getParameter("createdby");
                                   // System.out.println("loggedin user  " + loggeduser);
                                   // List<UserBean> mails = magmhelperUTIL.getAdminMails();
                                  //  String admin1 = mails.get(0).getEmail().toString();
                                  //  String admin2 = mails.get(1).getEmail().toString();
                                 //  System.out.println("the admin mail1 are " + mails.get(0).getEmail().toString());
                                   
                                 //   System.out.println("email is sent ");

                                } catch (Exception se) {
                                    se.printStackTrace();
                                }

                            }
                        }
                        //if the record count for failed is 0 move all records from vot_members_file to vot_members table

                        if(failed==0){  
                           magmhelperUTIL.moveSuccessfulVotMembers(succesref_file);
                        }
                        if (errorList.size() > 0) {
                            /////   System.out.println("Found " + errorList.size() + " failed");
                            for (VotMembersErrors vE : errorList) {
                            mvotMembersErrorsService.save(vE);
                            }
                        }
                        //BigDecimal bgdnewintprocessedSum = new BigDecimal(processedSum);
                        //System.out.println("bgdnewintprocessedSum :: " + bgdnewintprocessedSum);
                        //bgdnewintprocessedSum = bgdnewintprocessedSum.setScale(2, BigDecimal.ROUND_HALF_UP );
                        // processedSum = bgdnewintprocessedSum.doubleValue();
                        //System.out.println("processedSum :: " + processedSum);
                        // com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().updateFileUpload(f.getId(), successList.size(), errorList.size(), processedSum, fileCount);
                        ///  System.out.println("Success Count: " + successList.size() + " Failed Count: " + errorList.size() + " updating" + f.getId());



                        // }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("File does not exist");
                     //update Vot_Fileupload records
                        
                        
                        
                        
                    }
                }//end  of for loop
                //send admin mail here
                try {
            
                    System.out.println("loggedin user  " + loggeduser);
                    List<UserBean> mails = magmhelperUTIL.getAdminMails();
                    String admin1 = mails.get(0).getEmail().toString();
                    String admin2 = mails.get(1).getEmail().toString();
                    System.out.println("the admin mail1 are " + mails.get(0).getEmail().toString());
                    if (loggeduser.equals(admin1)) {
                        System.out.println("i am in this block");
                        String mailBody = "NEW AGM SETTUP AWAITING APPROVAL";
                        VotMailBean MB = mvotutility.getMailConfig();
                        System.out.println("SMTP HOST " + MB.getMailsmtphost());
                        //System.out.println("SMTP PASSWORD " + MB.getPassword());
                        //System.out.println("SMTP HOST " + MB.getMailsmtphost());
                        System.out.println("SSLORTLS " + MB.getSslortls());
                        MB.setSubject("AGM SETUP NOTIFICATION");
                        MB.setToemail(admin2);
                        MB.setMailBody(mailBody);
                        mvotutility.sendMail(MB);
                    } else if (loggeduser.equals(admin2)) {
                        System.out.println("the second if block");
                        String mailBody = "NEW AGM SETTUP AWAITING APPROVAL";
                        VotMailBean MB = mvotutility.getMailConfig();
                        MB.setSubject("AGM SETUP NOTIFICATION");
                        MB.setToemail(admin1);
                        MB.setMailBody(mailBody);
                        mvotutility.sendMail(MB);

                    }
                } catch (Exception ex) {
                    System.out.println("Error occured:::" + ex.getMessage());
                }

            }
            // t.commit();
        } catch (PersistentException e) {
            // FileUploadError fError = new FileUploadError();

            e.printStackTrace();
           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            
        } catch (IOException e) {
            e.printStackTrace();
            //  t.rollback();
           
        }

    }
    
    
    
    
}
