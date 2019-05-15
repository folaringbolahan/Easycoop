/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.services;

import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.ActivityLog;
import com.sift.financial.member.BatchErrors;
import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.BatchUploadReference;
import com.sift.financial.member.Member;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.schedule.GenericBatchUpdateScheduler;
import com.sift.financial.member.schedule.GenericValidationTask;
import com.sift.financial.services.MailNotificationImpl;
import com.sift.financial.services.BatchManageService;
import com.sift.financial.services.ReqManageService;
import com.sift.financial.utility.CreateZipMultipleFiles;
import com.sift.financial.utility.FileReader;
import com.sift.financial.utility.FileUtil;
import com.sift.financial.utility.FileWriter;
import com.sift.financial.utility.SiftFinancialUtil;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/*
 * @author baydel200
 */
public class BatchManagementImpl extends ReqManageService{
    
   private static final Log log = LogFactory.getLog(BatchManagementImpl.class);
    
   private CurrentuserdisplayImpl user;
   
   private String msg;
   private boolean passRecord;
   
   private String failFile;
   
   private String viewType;
   
   @Autowired
   @Value("${KEYCOLUMN}")
   private String keyColumn; 
   
   @Autowired
   @Value("${DOWNLOAD_PATH}")
   private String downLoadPath;
   
    @Autowired
   @Value("${UPLOAD_PATH}")
   private String uploadPath;
   
   @Autowired
   @Value("${MEMBER_FILE}")
   private String memberFileTempl;
   
   @Autowired
   @Value("${MEMBER_FILE_COL}")
   private String memberFileCol;
   
   @Resource (name="memAddrType")
   private Map<String, String> memberAddType;
     
   @Autowired
   private TaskExecutor taskExec;
   
   @Autowired
   private GenericValidationTask valTask;
   
   @Autowired
   private GenericBatchUpdateScheduler processTask;
   
    @Autowired
   @Value("${noFailState}")
   private String noFailState;
   

    @Override
    public Map<String, List> getReferenceData(String[] param) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addDetail(Object eventObject, StatusFlow flow, HttpServletRequest req)
    {
                Session sess = null;
		BatchUploadFile batchUploadFile = (BatchUploadFile)eventObject;
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		batchUploadFile.setStatus(successStatus);
		
		ActivityLog actLog = null;
		Integer actionItem ;
                FileUtil fileUtility = new FileUtil();
		
		Date today = new Date();
                
                try {
			today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
                        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

            boolean success= false;
		
		try
                {
                    //do validation writting
                    sess = getBatchUploadFileDAO().getSessionFactory().openSession();
		    sess.beginTransaction();
                    //get refernce
                    String ref = fileUtility.getServerReference(batchUploadFile.getBatchUploadType().getUploadTypeShort(), user.getCurruser().getCompanyid().toString(), user.getCurruser().getBranch().toString());
                    actLog = getLogObject(batchUploadFile,flow,req);

                    actLog.setActionItem(ref);
                    actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
                    actLog.setDescription(flow.getEvent().getEventName());

                    System.out.println("entrering validator..");
                    
                        if(!validateFile(batchUploadFile))
                        {
                            throw new Exception(getMsg());
                        }
                    
                    System.out.println("exiting validator..");

                    //build reference object
                    BatchUploadReference  batchUploadReference= new BatchUploadReference();
                    batchUploadReference.setBatchUploadReferenceId(ref);
                    batchUploadReference.setCreatedBy(user.getCurruser().getUserId());
                    batchUploadReference.setCreatedDate(new Timestamp(today.getTime()));
                    batchUploadReference.setStatus(successStatus);
                    
                    getBatchUploadReferenceDAO().save(batchUploadReference, sess);
 
                    //complete UploafIle object
                    batchUploadFile.setBatchUploadReference(batchUploadReference);
                    
                    getBatchUploadFileDAO().save(batchUploadFile, sess);
                    
                    sess.getTransaction().commit();
                    
                    log.info("Here inside addDetail.................spurning task with ref info :::: " + ref);
                    
                    processTask.setCurrentRefrence(ref);
                    taskExec.execute(processTask);
                    
                    log.info("Here inside addDetail.................succesfully spurned task ");
                    
		    success=true;
                    
                    setMsg("Upload successfully created with refrence: " + ref);
                }
                catch(Throwable ex)
                {
                 
			ex.printStackTrace();
			try
                        {actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
                        }
                        catch(Exception excatch)
                        {
                            excatch.printStackTrace();
                        }
			sess.getTransaction().rollback();;
			success=false;
			setMsg("Upload Failed  :: " + ex.getMessage());
                
                }
                finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
    
                
    return success;
    }
    
   
    public boolean addBulkDetail(Object eventObject, StatusFlow flow, HttpServletRequest req)
    {
                
                FileUtil fileUtility = new FileUtil();
                String ref = fileUtility.getServerReference("BULK", user.getCurruser().getCompanyid().toString(), user.getCurruser().getBranch().toString());
                Session sess = null;
		//BatchUploadFile[] batchUploadFiles = (BatchUploadFile[])eventObject;
                BatchUploadFile batchUploadFile = (BatchUploadFile)eventObject;
		Status successStatus = getStatusDAO().findById(flow.getStatusSuccessId());
		batchUploadFile.setStatus(successStatus);
		
		ActivityLog actLog = null;
		Integer actionItem ;
               
		
		Date today = new Date();
                
                try {
			today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
                        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

            boolean success= false;
		
		try
                {
                    sess = getBatchUploadFileDAO().getSessionFactory().openSession();
		    sess.beginTransaction();
                    //get refernce
                   
                    //build reference object
                    BatchUploadReference  batchUploadReference= new BatchUploadReference();
                    batchUploadReference.setBatchUploadReferenceId(ref);
                    batchUploadReference.setCreatedBy(ref);
                    batchUploadReference.setCreatedDate(new Timestamp(today.getTime()));
                    batchUploadReference.setStatus(successStatus);
                    
                    getBatchUploadReferenceDAO().save(batchUploadReference, sess);
                    
                        actLog = getLogObject(batchUploadFile,flow,req);
                        
			actLog.setActionItem(ref);
			actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
			actLog.setDescription(flow.getEvent().getEventName());
                            
                    //complete UploafIle object
                    batchUploadFile.setBatchUploadReference(batchUploadReference);
                    
                    getBatchUploadFileDAO().save(batchUploadFile, sess);
                    
                    sess.getTransaction().commit();
		    success=true;
                    setMsg("Upload successfully created with refrence: " + ref);
                }
                catch(Exception ex)
                {
                    
			ex.printStackTrace();
			try
                        {actLog.setActionItem(ActivityInterface.UNSUCCESSFULLNEWITEMID);
			actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
			actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
                        }
                        catch(Exception excatch)
                        {
                            excatch.printStackTrace();
                        }
			sess.getTransaction().rollback();;
			success=false;
			setMsg("Member creation failed due to :: " + ex.getMessage());
                
                }
                finally
		{
			getActivityLogDAO().save(actLog);
			sess.close();
		}
    
    return success;
    }
    
    
    

    @Override
    public boolean editDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object readDetail(Object eventObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> listDetail(Object eventObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> listDetail(Object eventObject, String status) {
        
         List<Object> theList = null;
         
         if (viewType.equals("MEMTYPE"))
         {
            theList =  getBatchMemberDAO().getBatchMemberByStatus(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch());
            
             prepDataDownload(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch(),getBatchMemberDAO());
         }
         else if (viewType.equals("STKTYPE"))
         {
            theList =  getBatchStockDAO().getBatchStockByStatus(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch());
            
            prepDataDownload(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch(),getBatchStockDAO());
         }
         else if (viewType.equals("ADDTYPE"))
         {
             theList =  getBatchAddressEntriesDAO().getBatchAddressByStatus(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch());
             
              prepDataDownload(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch(),getBatchAddressEntriesDAO());
         }
         else if (viewType.equals("EXTTYPE"))
         {
             theList =  getBatchExtrafldEntriesDAO().getBatchExtrafldByStatus(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch());
             
              prepDataDownload(status, (Integer)eventObject, user.getCurruser().getCompanyid(), user.getCurruser().getBranch(),getBatchExtrafldEntriesDAO());
         }
         
        
          return  theList;
    }
    
    public List listReferences(String  statusShort) {
        
        return getBatchUploadFileDAO().getFilesByStatus(statusShort, user.getCurruser().getCompanyid(), user.getCurruser().getBranch());
    
    }

    @Override
    public boolean apprDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {

        //build batchuploadfile
           log.info("Here inside apprDetail....................");
        boolean returnVal = false;
        ActivityLog actLog = null;
        BatchUploadFile batchUploadFile = null;
        Date today = new Date();

               try {
                       today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");

               } catch (ParseException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
               }
        try
        {
        batchUploadFile = (BatchUploadFile)eventObject;
        log.info("Here inside apprDetail................... for bacthId " + batchUploadFile.getBatchUploadFileId());
        
        Map<String, String> result = canApproveRequest(batchUploadFile.getCreatedBy(),batchUploadFile.getModifiedBy(),user.getCurruser().getUserId());
        
        actLog = getLogObject(batchUploadFile,flow,req);
        actLog.setActionItem(batchUploadFile.getBatchUploadReference().getBatchUploadReferenceId());
        actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
        actLog.setDescription(flow.getEvent().getEventName());
         
        if(!Boolean.parseBoolean(result.get("STATE")))
        {
            throw new Exception(result.get("MSG"));
        }
        
        batchUploadFile.setApprovedBy(user.getCurruser().getUserId());
        batchUploadFile.setApprovedDate(new Timestamp(today.getTime()));

            valTask.setServPrefix("appr");
            valTask.setFile(batchUploadFile);
            valTask.setRecordPassed(passRecord);
            log.info("Here inside apprDetail.................spurning task with info ID|PREFIX|RecordPassed|PostInfo :::: " + batchUploadFile.getBatchUploadFileId() + "|"+ valTask.getServPrefix() + "|" + valTask.isRecordPassed()+ "|"+ batchUploadFile.getPostInfo());
            taskExec.execute(valTask);
        
            log.info("Here inside apprDetail.................spurning task successful");
  
            
            String type ="";
            if(passRecord)
            { type="Pass";}
            else
            {type="Fail";}
            
             setMsg(type + " Approval process has been initiated. You will be notified on completion");
            
            returnVal = true;
        }
        catch(Exception ex)
        {
            try
            {actLog.setActionItem(batchUploadFile.getBatchUploadReference().getBatchUploadReferenceId());
            actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
            actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
            }
            catch(Exception excatch)
            {
                excatch.printStackTrace();
            }
            finally
            {
                getActivityLogDAO().save(actLog);
            }
             setMsg("Approval Failed due to :: " +  ex.getMessage());
             returnVal = false;
        }
  
    return returnVal;
    }
   
    
    public boolean validateDetail(Object eventObject, StatusFlow flow, HttpServletRequest req)
    {
        //build batchuploadfile
        boolean returnVal = false;
        ActivityLog actLog = null;
         BatchUploadFile batchUploadFile = null;
        Date today = new Date();

               try {
                       today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");

               } catch (ParseException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
               }
        try
        {
            
            
        batchUploadFile = (BatchUploadFile)eventObject;
        
        Map<String, String> result = canApproveRequest(batchUploadFile.getCreatedBy(),null,user.getCurruser().getUserId());
         
         actLog = getLogObject(batchUploadFile,flow,req);
         actLog.setActionItem(batchUploadFile.getBatchUploadReference().getBatchUploadReferenceId());
         actLog.setActionResult(ActivityInterface.ACTIVITYOKRESULT);
         actLog.setDescription(flow.getEvent().getEventName());
         
        if(!Boolean.parseBoolean(result.get("STATE")))
        {
            throw new Exception(result.get("MSG"));
        }
        batchUploadFile.setModifiedBy(user.getCurruser().getUserId());
        batchUploadFile.setModifiedDate(new Timestamp(today.getTime()));
        


            valTask.setServPrefix("val");
            valTask.setFile(batchUploadFile);
            valTask.setRecordPassed(passRecord);
            taskExec.execute(valTask);
            
            String type ="";
            if(passRecord)
            { type="Pass";}
            else
            {type="Fail";}
            
             setMsg(type + " Validation process has been initiated. You will be notified on completion");
            
            returnVal = true;
        }
        catch(Exception ex)
        {
            try
            {actLog.setActionItem(batchUploadFile.getBatchUploadReference().getBatchUploadReferenceId());
            actLog.setActionResult(ActivityInterface.ACTIVITYFAILERESULT);
            actLog.setDescription(flow.getEvent().getEventName() + " failed due to :: " +  ex.getMessage());
            }
            catch(Exception excatch)
            {
                excatch.printStackTrace();
            }
            finally
            {
                getActivityLogDAO().save(actLog);
            }
             setMsg("Validation Failed due to :: " +  ex.getMessage());
             returnVal = false;
        }
  
    return returnVal;
    }

    @Override
    public boolean apprBulkDetail(Object eventObject, StatusFlow flow, HttpServletRequest req) {

        return false;
    }
    
    
    public boolean getTemplates(Object eventObject, StatusFlow flow, HttpServletRequest req) {
        
        //go generate Excel Files
            //get member file
        
  
            // generate address file based on address definition
            // generate stock file if company does stock
       
        // do Zipping of generated files
        
        //return zip file path to controller
        
        // do activity log
     
      return false;
    }
    
    
    public List<String[]> showTemplates(Object eventObject, HttpServletRequest req) {
        
        List<String[]> theFiles = new ArrayList<String[]>();
        String[] allFiles = new String[4];
        
        
        
        //get member file
        String[] memberFile = new String[2];
        memberFile[0]= memberFileTempl;
        memberFile[1]= "Member Basic Info File";
        allFiles[0]= downLoadPath + memberFileTempl;
                
        theFiles.add(memberFile);
        
         //go generate Excel Files
            // generate address file based on address definition
            String userCountryId = user.getCurrusercompany().getCountryId();
          
            String[] addressArray = new String[2];
            String addrFile = null;
            FileWriter fileWriter = null;
            
            List<Map<String,Object>> addresFields = getGenericConfigDAO().getAddressColumns(userCountryId);
            
            log.info("addresFields :::: " + addresFields.size());
            
             Map<String, Map<String,Object[]>> finalData = new  HashMap<String, Map<String,Object[]>>();
            
           
            //write excel File 4 Address
            if(addresFields!=null)
            {
                log.info("memberAddType :::: " + memberAddType.size());
                
                addrFile  = prepDataDownload(addresFields,memberAddType,"xls","ADDTYPE");
                
               /** for(String key : keys)
                {
                    
                    Map<String,Object[]> record = new HashMap<String,Object[]>();
                
                        Object[]  data = new Object[addresFields.size() +1];

                        data[0] = keyColumn;
                        int n= 1;

                        for(Map<String,Object> mapObj : addresFields)
                        {
                             data[n] = mapObj.get("descr");
                             n = n+1;
                            log.info("mapObj :::: " + mapObj.get("descr"));
                        }
               
                        record.put("1", data);
                 
                 
                    finalData.put(key, record);
                   }
                  log.info("finalData :::: " + finalData.size());
                  
              
                fileWriter = new FileWriter();
                
                fileWriter.setBarnchId(user.getCurruser().getBranch().toString());
                fileWriter.setCompanyId(user.getCurruser().getCompanyid().toString());
                fileWriter.setFileType("xls");
                fileWriter.setFirstRowHeader(true);
                fileWriter.setPartFileName("ADDTYPE");
                fileWriter.setDownLoadPath(downLoadPath);
                
                try
                {
                    log.info("finalData :::: " + finalData.size());
                    
                    addrFile =  fileWriter.createExcelFile(memberAddType, finalData);
                    
                    log.info("addrFile :::: " + addrFile);
                    theFiles.add(addrFile);
                }
                catch(Exception ex)
                {
                        setMsg("problem creating address template ::" + ex.getMessage());
                }
                **/
             
                if(addrFile !=null)
                {
                    log.info("addrFile :::: " + addrFile);
                    addressArray[0]=addrFile;
                    addressArray[1] = "Member Address File";
                    theFiles.add(addressArray);
                     allFiles[1]= downLoadPath +  addrFile;
                }
            }
            else
            {
             setMsg("No address entries defined for country");
            }
           
          

             // generate stock file if company does stock
             List<Map<String,Object>> stocksList = getGenericConfigDAO().getStockColumns(user.getCurruser().getCompanyid().toString());
       
             String stockFile = null;
             String[] stockArray = new String[2];
                
            if(stocksList!=null)
            {
                Map<String, String> stockSheets = new  HashMap<String, String>();
                
                stockSheets.put("STOCKS", "STOCKS");
                
                stockFile  = prepDataDownload(stocksList,stockSheets,"xls","STKTYPE");
                
                if(stockFile !=null)
                {
                    log.info("stockFile :::: " + stockFile);
                    stockArray[0]=stockFile;
                    stockArray[1]="Member Stock File";
                    theFiles.add(stockArray);
                    allFiles[2]= downLoadPath + stockFile;
                }
            }
            else
            {
             setMsg(" No address entries defined for country ");
            
            }
             
            //extra field
            String[] extrafldArray = new String[2];
            String extrafldFile = null;
            List<Map<String,Object>> extrafldFields = getGenericConfigDAO().getExtrafldColumns(user.getCurruser().getCompanyid().toString());
            
            //log.info("extrafldFields :::: " + extrafldFields.size());
            
            //write excel File 4 Address
            if(extrafldFields!=null)
            {
                Map<String, String> extrafldSheets = new  HashMap<String, String>();
                extrafldSheets.put("ADDITIONAL FIELDS", "ADDITIONAL FIELDS");
                extrafldFile  = prepDataDownload(extrafldFields,extrafldSheets,"xls","EXTRAFLDTYPE");
                          
                if(extrafldFile !=null)
                {
                    log.info("extrafldFile :::: " + extrafldFile);
                    extrafldArray[0]=extrafldFile;
                    extrafldArray[1] = "Member Additional field File";
                    theFiles.add(extrafldArray);
                     allFiles[3]= downLoadPath +  extrafldFile;
                }
            }
            else
            {
             setMsg("No additional field entries defined for company");
            }
           
            
            
            
            
             String [] fileArray = allFiles;
             
         // do Zipping of generated files
         String[] zipArray = new String[2];
         CreateZipMultipleFiles zipFiles = new CreateZipMultipleFiles();

         zipFiles.setUploadPath(downLoadPath);
         String zipTargetName = memberFileTempl.substring(0, memberFileTempl.indexOf(".")-1) + "-" + user.getCurruser().getCompanyid() + user.getCurruser().getBranch();
         String zippedFile = null;
                 
         zippedFile = zipFiles.getZipFiles(fileArray, zipTargetName);
        
         if(zippedFile!=null)
         {
             zipArray[0]=zippedFile;
             zipArray[1]="All Files Bundled as Zip";
             //theFiles.add(zipArray);
         }
         //return zip file path to controller
        
        // do activity log
     
      return theFiles;
    }
    
    

    @Override
    public boolean sendSuccessNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendFailureNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendSuccessAuthNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean sendFailureAuthNotification(String comment, Object eventObject, String mailTempl, MailNotificationImpl mailNotificationService) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ActivityLog getLogObject(Object eventObject, StatusFlow flow, HttpServletRequest req) {
        
            BatchUploadFile stk = (BatchUploadFile)eventObject;
            SiftFinancialUtil siftUtil  =  new SiftFinancialUtil();
            ActivityLog log = new ActivityLog();
            
            System.out.println("here Inside Logging");
            Date today  = new Date();
           
            try
            {
                today = getCountryDate(today, user.getCurrusercompany().getCountryId(),"");
                System.out.println("here Inside Logging b4 date");
               // today = getCountryDate(today,stk.getCompany().getCountries().getId().toString(), null);
                System.out.println("here Inside Logging after date");
                
                System.out.println("stk.getAction() ::: " + stk.getAction());
                
		log.setAction(stk.getAction());
                
		log.setActionDate(new  java.sql.Timestamp(today.getTime()));
		log.setEvent(flow.getEvent());
		log.setBranchId(user.getCurruser().getBranch());
		log.setCompanyId(user.getCurruser().getCompanyid());
		log.setIpaddress(siftUtil.getUserAddress(req));
                log.setUsername(user.getCurruser().getUserId());
                
             }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
		
		return log;
    }

    public CurrentuserdisplayImpl getUser() {
        return user;
    }

    public void setUser(CurrentuserdisplayImpl user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getDownLoadPath() {
        return downLoadPath;
    }

    public void setDownLoadPath(String downLoadPath) {
        this.downLoadPath = downLoadPath;
    }

    public String getMemberFileTempl() {
        return memberFileTempl;
    }

    public void setMemberFileTempl(String memberFileTempl) {
        this.memberFileTempl = memberFileTempl;
    }

    public String getMemberFileCol() {
        return memberFileCol;
    }

    public void setMemberFileCol(String memberFileCol) {
        this.memberFileCol = memberFileCol;
    }
    
   
    private boolean validateFile(BatchUploadFile  obj) throws Exception
    {
                    boolean returnType = false;         
                    List<String[]> finalList = null;
                    
                    List<String> columnList = new ArrayList<String>();
                    
                    FileReader fileReader = new FileReader();

                    if(obj.getBatchUploadType().getUploadTypeShort().equals("MEMTYPE"))
                    {
                        log.info("memberFileCol ::" + memberFileCol);
                        
                        String[] colList=  memberFileCol.split("::");
                        log.info("colList ::" + colList.length);
                        for(int i = 0; i < colList.length; i++)
                        {
                          log.info("colList[i] ::" + i + "  " + colList[i]);
                          columnList.add(colList[i]);
                        }
                    fileReader.setColumnCount(colList.length);
                    }
                    
                    else if(obj.getBatchUploadType().getUploadTypeShort().equals("ADDTYPE"))
                    {
                        
                     columnList.add(keyColumn);
                     List<Map<String,Object>> objList = getGenericConfigDAO().getAddressColumns(user.getCurrusercompany().getCountryId());
                     
                        for(int i = 0; i < objList.size(); i++)
                        {
                            Map<String,Object> mapObj = objList.get(i);
                            
                            columnList.add((String)mapObj.get("code"));
                        }
                        
                         fileReader.setColumnCount(objList.size()+1);
                         fileReader.setMultipleSheet(true);

                    }
                    else   if(obj.getBatchUploadType().getUploadTypeShort().equals("STKTYPE"))
                    {
                    
                        columnList.add(keyColumn);
                        List<Map<String,Object>> objList = getGenericConfigDAO().getStockColumns(user.getCurruser().getCompanyid().toString());
                     
                        for(int i = 0; i < objList.size(); i++)
                        {
                            Map<String,Object> mapObj = objList.get(i);
                            columnList.add((String)mapObj.get("code"));
                        }
                        fileReader.setColumnCount(objList.size()+1);
                    }
                    
                    log.info("columnList :: " + columnList);
                    fileReader.setKeyColumns(columnList);
                    fileReader.setStartRow(1);
                    
                       log.info("Starting Read with parameter :: " + columnList.size() );
                       finalList =  fileReader.readExcelFile(uploadPath + obj.getBatchUploadFileName());
                       log.info("ending Read with parameter state:: " + finalList.size());
                    
                    /**
                     * if(obj.getBatchUploadFileName().toLowerCase().endsWith("xlsx"))
                    {
                         finalList = fileReader.readXlsxFile(uploadPath + obj.getBatchUploadFileName());
                    }
                    else if(obj.getBatchUploadFileName().toLowerCase().endsWith("xls"))
                    {
                       finalList =  fileReader.readXlsFile(uploadPath + obj.getBatchUploadFileName());
                    }
                    **/
                     if(finalList.size() != obj.getBatchRecordCount())
                     {
                        returnType = false;
                        
                        setMsg("Number of Records does not match the expected");
                     }
                     else
                     {
                         
                         returnType = true;
                     }
                     
                        
       return returnType;
    }

    public boolean isPassRecord() {
        return passRecord;
    }

    public void setPassRecord(boolean passRecord) {
        this.passRecord = passRecord;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public TaskExecutor getTaskExec() {
        return taskExec;
    }

    public void setTaskExec(TaskExecutor taskExec) {
        this.taskExec = taskExec;
    }

    public GenericValidationTask getValTask() {
        return valTask;
    }

    public void setValTask(GenericValidationTask valTask) {
        this.valTask = valTask;
    }

    public GenericBatchUpdateScheduler getProcessTask() {
        return processTask;
    }

    public void setProcessTask(GenericBatchUpdateScheduler processTask) {
        this.processTask = processTask;
    }

    
    private String prepDataDownload(List<Map<String,Object>> initData, Map<String,String> theSheets, String fileExt, String partFileName)
    {
    
       Map<String, Map<String,Object[]>> finalData = new  HashMap<String, Map<String,Object[]>>();
       
        String addrFile = null;
        FileWriter fileWriter = null;
       
            if(initData!=null && theSheets!=null)
            {
                Set<String> keys = theSheets.keySet();    
                log.info("theSheets :::: " + theSheets.size());
                
                for(String key : keys)
                {
                    Map<String,Object[]> record = new HashMap<String,Object[]>();
                
                        Object[]  data = new Object[initData.size() +1];

                        data[0] = keyColumn;
                        int n= 1;

                        for(Map<String,Object> mapObj : initData)
                        {
                             data[n] = mapObj.get("descr");
                             n = n+1;
                            log.info("mapObj :::: " + mapObj.get("descr"));
                        }
               
                        record.put("1", data);
                 
                    finalData.put(key, record);
                   }
                  log.info("finalData :::: " + finalData.size());
                  
                fileWriter = new FileWriter();
                
                fileWriter.setBarnchId(user.getCurruser().getBranch().toString());
                fileWriter.setCompanyId(user.getCurruser().getCompanyid().toString());
                fileWriter.setFileType(fileExt);
                fileWriter.setFirstRowHeader(true);
                fileWriter.setPartFileName(partFileName);
                fileWriter.setDownLoadPath(downLoadPath);
                
                try
                {
                    log.info("finalData :::: " + finalData.size());
                    
                    log.info("theSheets :::: " + theSheets.size());
                    
                    addrFile =  fileWriter.createExcelFile(theSheets, finalData);
                    //log.info("addrFile :::: " + addrFile);
                    //theFiles.add(addrFile);
                }
                catch(Exception ex)
                {
                        setMsg("Problem creating " + fileWriter.getPartFileName() +" template ::" + ex.getMessage());
                }
                
            }
            else
            {
            
            finalData = null;
            }
           
return addrFile;
}
    
private void prepDataDownload(String status, Integer fileUploadId, Integer companyId, Integer branchId, BatchErrors dao)
{
        
       Map<String, Map<String,Object[]>> finalData = new  HashMap<String, Map<String,Object[]>>();
        
       Status curStatus = getStatusDAO().getStatusFromShort(status);
        
       List<Object[]> initData =null;
        
       Map<String,String> theSheets = new HashMap<String,String>();
       
       String addrFile = null;
     
        if(!curStatus.getFailState().equalsIgnoreCase(noFailState))
        {
            initData = dao.getErrorList(status, fileUploadId, companyId, branchId);
            
            FileWriter fileWriter = null;
       
            if(initData!=null )
            {         
                    Map<String,Object[]> record = new HashMap<String,Object[]>();
                                              
                        Integer n= 0;

                        for(Object[] mapObj : initData)
                        {
                             record.put(n.toString(), mapObj);
                             //data[n] = mapObj.get("descr");
                             n = n+1;
                            log.info("mapObj :::: " + mapObj);
                        }
                        
                    finalData.put(status, record);
                    theSheets.put(status, status);
                  
                  log.info("finalData :::: " + finalData.size());
                  
                fileWriter = new FileWriter();
                
                fileWriter.setBarnchId(user.getCurruser().getBranch().toString());
                fileWriter.setCompanyId(user.getCurruser().getCompanyid().toString());
                fileWriter.setFileType("xls");
                fileWriter.setFirstRowHeader(false);
                fileWriter.setPartFileName(status);
                fileWriter.setDownLoadPath(downLoadPath);
                
                try
                {
                    addrFile =  fileWriter.createExcelFile(theSheets, finalData);
                    setFailFile(addrFile);
                }
                catch(Exception ex)
                {
                        setMsg("Problem creating " + fileWriter.getPartFileName() +" template ::" + ex.getMessage());
                }
            }
            else
            {
            
            finalData = null;
            }
        
        }

}

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getFailFile() {
        return failFile;
    }

    public void setFailFile(String failFile) {
        this.failFile = failFile;
    }

      

}