/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.AccounttxnsException;
import com.sift.gl.AuditlogService;
import com.sift.gl.NotificationService;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.AccountgroupImpl;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.dao.AccounttxnsImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.FileuploadImpl;
import com.sift.gl.dao.PosttxnsImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Accounttxnsdetail;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.model.Users;
import com.sift.gl.validator.AccountsetupValidator;
import com.sift.gl.validator.AccounttxnsetupValidator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.util.*;
import java.util.concurrent.Future;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/** controller for uploading bulk postings against a GL Account
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class AccounttxnuploadController{
   //@Autowired 
   private FileuploadImpl fileuploadService;
   private PosttxnsImpl posttxnsService;
   @Autowired
    private NotificationService notificationService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private AccounttxnsImpl accounttxnsService;
   private AccounttxnsetupValidator accounttxnsetupValidator;
   private Account accountdet;
   private String accountseg;
   private String clientIpAddress;
   List<Accountgrpclassdetails> accountclasses;
   List<Accountgrprepdetails> accreportgroups;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @Value("${emailsubject.approveaccttxn}")
   private String emailsubjectapproveaccttxn;
    @Value("${DOWNLOAD_PATH}")
    private String downLoadPath;
   
    /**
     *
     * @param binder
     */
    @InitBinder
   public void initBinder(WebDataBinder binder) {
          accounttxnsetupValidator = new AccounttxnsetupValidator();
          accountdet = new Account();
   }
   //@Autowired
    /**
     *
     * @param user
     */
    public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
    /**
     *
     * @param fileuploadService
     */
    public void setFileuploadService(FileuploadImpl fileuploadService) {
      this.fileuploadService = fileuploadService;
     // accountdet = new Account();
   }
  
    /**
     *
     * @param posttxnsService
     */
    public void setPosttxnsService(PosttxnsImpl posttxnsService) {
      this.posttxnsService = posttxnsService;
   }
   
    /**
     *
     * @param request
     * @return
     */
    @ModelAttribute("clientIpAddress")
   public String populateClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
   }
   
    /** method to upload file to server and verify structure of excel file using asynchronous execution
     *
     * @param fileUploadBean
     * @param request
     * @param file
     * @param result
     * @return
     */
    @RequestMapping(value = {"gl/gl_accttxnuploadfile","/gl_accttxnuploadfile"},method=RequestMethod.POST)
   public ModelAndView accountstxnuploadfile(@ModelAttribute("acctxnbulkupload")FileUploadBean fileUploadBean, HttpServletRequest request,@RequestParam("fileData") MultipartFile file, BindingResult result)  {
     String batchid=null;
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String startdateinstring = "";
     Date txndate = new Date();
     txndate = user.getCurrusercompany().getPostDate();
     //System.out.println("date check 1 " + txndate.toString());
     /*
     if (fileUploadBean.getTxnDatestr()!=null) {
        startdateinstring = fileUploadBean.getTxnDatestr();
        //System.out.println("date check 2 " + startdateinstring);
     }
     try {
        txndate = formatter.parse(startdateinstring);
     } catch (ParseException e) {
		//e.printStackTrace();
         // System.out.println("date check 3 " + txndate.toString());
     }
     */ 
     // System.out.println("date check 4 " + txndate.toString());
    
     
    
     
       if (!file.isEmpty()) {
           batchid = "actxn" + String.valueOf(System.currentTimeMillis());
           String uri = "";
           try {
               javax.naming.Context ctx = new javax.naming.InitialContext();
               uri = (String) ctx.lookup("java:comp/env/uploadpath");
           } catch (NamingException nx) {
               System.out.println("Error upload naming exception" + nx.getMessage().toString());
           }
           try {
               byte[] bytes = file.getBytes();
               String rootPath = uri;
               File dir = new File(rootPath + File.separator + "tmpfiles");
               if (!dir.exists()) {
                   dir.mkdirs();
               }
               FilenameUtils fileUTIL = new FilenameUtils();

               String ext = fileUTIL.getExtension(file.getOriginalFilename());
               String baseName = fileUTIL.getBaseName(file.getOriginalFilename());
               if ((ext.equalsIgnoreCase("xlsx")==false) && (ext.equalsIgnoreCase("xls")==false)) {
                   return new ModelAndView("redirect:/error.htm?er=Invalid file type selected; It must be an Excel file");
               }
               //String serverFileName = request.getServletContext().getRealPath("/") + File.separator + baseName + "_" + batchid + "." + ext;
               String serverFileName = rootPath + "tmpfiles" + File.separator + baseName + "_" + batchid + "." + ext;
               //System.out.println("serverFileName:" + serverFileName);

               //create the file on server
               File serverFile = new File(serverFileName);
               BufferedOutputStream stream = new BufferedOutputStream(
                       new FileOutputStream(serverFile));
               stream.write(bytes);
               stream.close();

               FileUpload fileUpload = new FileUpload();

               fileUpload.setUploadFilename(baseName);
               //fileUpload.setUploadDate(user.getCurrusercompany().getCurrentsystemDate());
               TimeZone timeZone = TimeZone.getTimeZone(user.getCurrusercompany().getTimezone());
               Calendar rightNow3 = Calendar.getInstance(timeZone);
               fileUpload.setUploadDate(rightNow3.getTime());
               fileUpload.setUploadStatus("E");
               fileUpload.setBranchId(user.getCurruser().getBranch().toString());
               fileUpload.setCompanyId(user.getCurruser().getCompanyid().toString());
               fileUpload.setBatchId(batchid);
               fileUpload.setUploadedBy(user.getCurruser().getUserId());
               fileUpload.setLocation(serverFileName);
               //
               clientIpAddress = request.getRemoteAddr();
               //fileUploadBean.getAccountno();
               //fileUploadBean.getTxnDate();
               fileUploadBean.setTxnDate(txndate);
               fileUpload.setTotalRecords(fileUploadBean.getTotalRecords());
               System.out.println("supplied filesum :: " + fileUploadBean.getFilesum());
               //System.out.println("supplied getFilesum2() :: " + fileUploadBean.getFilesum2());
               // toString();
               // a =  (fileUploadBean.getFilesum());
               
               fileUpload.setFilesum(fileUploadBean.getFilesum());
               //System.out.println("supplied filesum2" + fileUpload.getFilesum());
               accounttxnsetupValidator.setFileLocation(serverFileName);
               accounttxnsetupValidator.validate(fileUploadBean, result);
               String redurlpath;
               if (result.hasErrors() == true) {
                   List<FieldError> lerr = result.getFieldErrors();
                   for (FieldError err : lerr) {
                       System.out.println(err);
                   }
                   return new ModelAndView("gl/gl_accountstxnupload", "acctxnbulkupload", fileUploadBean);
               }
               
               fileuploadService.addtxnFile(fileUpload,user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurrusercompany().getBaseCurrency(), user.getCurrusercompany().getCurrentsystemDate(),clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),fileUploadBean.getAccountno(),fileUploadBean.getTxnDate());
               
               Future<String> domsg=null;
               String mailsubject = emailsubjectapproveaccttxn;
               //System.out.println("mailsubject " + mailsubject);
               Map model = new HashMap();
                    model.put("referenceid", "trialref");
                    //notificationService.createflowemailnotice("GL15", mailsubject, "glacctxnsuptoapprvmail.ftl", user.getCurruser().getBranch(), user.getCurruser().getCompanyid(), model);
               //System.out.println("Before thread spin Step 1");
                    domsg=fileuploadService.runcheck(batchid, user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurrusercompany().getBaseCurrency(), user.getCurrusercompany().getCurrentsystemDate(),clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),mailsubject,notificationService);
              //System.out.println("After thread spin Step 2");
                    // domsg.isDone();  //if done send mail and set 
              //// while (!domsg.isDone()) {
              ////  System.out.println("Still processing upload...notify by mail when done");
              //// }
               auditlogcall(19,"GLATU",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),batchid,"File Batch Upload",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
               return new ModelAndView("redirect:gl_accttxnupresp.htm?message=Upload awaiting approval. The assigned Upload Batch Id is : " + batchid + "&bid=" + batchid);
           
           } catch (Exception e) {
               //e.printStackTrace();
               return new ModelAndView("redirect:/error.htm?er=No Valid file was specified");
           }
       } else {
           return new ModelAndView("redirect:/error.htm?er=Attempting to upload empty file. Select valid file");
       }		     
     }
    
    /**
     *
     * @param fileUploadBean
     * @param result
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/gl_accountstxnupload"}, method = {RequestMethod.GET})
   public ModelAndView accounttxnupload(@ModelAttribute("acctxnbulkupload")FileUploadBean fileUploadBean, BindingResult result) throws Exception  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       //Map<String, Object> model = new HashMap<String, Object>();
       Map model = new HashMap();
      model.put("accs", accounttxnsService.getAccounts(dbranch,dcompany));
     return new ModelAndView("gl/gl_accountstxnupload", model);
   }
   
    /**
     *
     * @param accounttxnsService
     */
    public void setAccounttxnsService(AccounttxnsImpl accounttxnsService) {
      this.accounttxnsService = accounttxnsService;
   }
   
    /**
     *
     * @param batchid
     * @return
     */
    @RequestMapping(value = {"/gl_accttxnupresp","gl/gl_accttxnupresp"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView accountuploadval(@RequestParam("bid") String batchid)  {
      Map dmodel = new HashMap();
      FileUpload batchfile2 = new FileUpload();
      batchfile2.setBatchId(batchid);
      dmodel.put("batchfile",batchfile2);
      return new ModelAndView("gl/gl_accttxnupresp", dmodel);
   }
   
    /**
     *
     * @return
     * @throws AccounttxnsException
     */
    @ModelAttribute("accs")
   public List<Account> getAllAccounts() throws AccounttxnsException {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return accounttxnsService.getAccounts(dbranch,dcompany);
   }
   
   
    /** method lists batches uploaded pending approval
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/gl_accttxnupldapprv","gl/gl_accttxnupldapprv"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String accounttxn4apprv(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("accttxns4apprv", fileuploadService.getAccttxns4apprv(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "gl/gl_accttxnupldapprv";
   }
    
    @RequestMapping(value = {"/gl_jrnltxnapprv","gl/gl_jrnltxnapprv"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String journaltxn4apprv(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("accttxns4apprv", fileuploadService.getJournaltxns4apprv(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "gl/gl_jrnltxnapprv";
   }
   
    /** method lists batches awaiting approval but without capability to approve - view only
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/gl_accttxnupldview","gl/gl_accttxnupldview"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String accounttxn4view(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("accttxns4apprv", fileuploadService.getAccttxns4view(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "gl/gl_accttxnupldview";
   }
   
    /**
     *
     * @param batchid
     * @return
     */
    @RequestMapping(value = {"/gl_cfpfccttxn4apprv","gl/gl_cfpfccttxn4apprv"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView cfaccountuptxnapp(@RequestParam("id") String batchid)  {
      Map dmodel = new HashMap();
      FileUpload batchfile2 = new FileUpload();
      batchfile2.setBatchId(batchid);
      dmodel.put("batchfile",batchfile2);
      return new ModelAndView("gl/gl_cfpfccttxn4apprv", dmodel);
   }
    
    @RequestMapping(value = {"/gl_cfpfjrnltxn4apprv","gl/gl_cfpfjrnltxn4apprv"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView cfpfjrnltxn4apprv(@RequestParam("id") String batchid)  {
      Map dmodel = new HashMap();
      FileUpload batchfile2 = new FileUpload();
      batchfile2.setBatchId(batchid);
      dmodel.put("batchfile",batchfile2);
      return new ModelAndView("gl/gl_cfpfjrnltxn4apprv", dmodel);
   }
   
    /** method to handle rejection of batch file
     *
     * @param batchid
     * @return
     */
    @RequestMapping(value = {"/gl_cfpfaccttxn4apprvrj","gl/gl_cfpfaccttxn4apprvrj"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView cfaccountuptxnapprj(@RequestParam("id") String batchid)  {
      Map dmodel = new HashMap();
      fileuploadService.rejectFile(batchid,user.getCurruser().getCompanyid(),user.getCurruser().getBranch());
      auditlogcall(116,"GLATR",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),batchid,"File Batch Rejection of Upload",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
      dmodel.put("accttxns4apprv", fileuploadService.getAccttxns4apprv(user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurruser().getUserId()));
     return new ModelAndView("gl/gl_accttxnupldapprv", dmodel);
   }
   
   @RequestMapping(value = {"/gl_cfpfjrnltxn4apprvrj","gl/gl_cfpfjrnltxn4apprvrj"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView cfpfjrnltxn4apprvrj(@RequestParam("id") String batchid)  {
      Map dmodel = new HashMap();
      fileuploadService.rejectPostingjrnl(batchid,user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
      auditlogcall(116,"GLATR",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),batchid,"Posting Journal Rejection ",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
      dmodel.put("accttxns4apprv", fileuploadService.getJournaltxns4apprv(user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurruser().getUserId()));
     return new ModelAndView("gl/gl_jrnltxnapprv", dmodel);
   } 
    
    /** method posts journals at approval
     *
     * @param flup2
     * @param request
     * @return
     */
    @RequestMapping(value = {"gl/gl_pfccttxn4apprv","/gl_pfccttxn4apprv"},method=RequestMethod.POST)
   public ModelAndView accountuptxnapp(@ModelAttribute("batchfile")FileUpload flup2,HttpServletRequest request)  {
     //String dcode = accountgroupdet2.getCode();//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
     
     clientIpAddress = request.getRemoteAddr();
     posttxnsService.updatefileuploadtbl(flup2.getBatchId(), dbranch, dcompany,user.getCurruser().getUserId(), -1);
     posttxnsService.post(flup2.getBatchId(), dcompany, dbranch, 1, user.getCurruser().getUserId(), user.getCurrusercompany().getCurrentYear(), user.getCurrusercompany().getCurrentPeriod(), user.getCurrusercompany().getTimezone(), user.getCurrusercompany().getPostDate(),clientIpAddress);
      //do audit log
     Map dmodel = new HashMap();
     dmodel.put("accttxns4apprv", fileuploadService.getAccttxns4apprv(dcompany,dbranch,user.getCurruser().getUserId()));
     //return new ModelAndView("gl/gl_accttxnupldapprv", dmodel);
     return new ModelAndView("redirect:gl_accttxnupldapprv.htm?message=Approval Successful. A mail will be sent to you on completion. Batch Id is : " + flup2.getBatchId() + "&bid=" + flup2.getBatchId());

   }
   
    
    @RequestMapping(value = {"gl/gl_pfjrnltxnapprv","/gl_pfjrnltxnapprv"},method=RequestMethod.POST)
   public ModelAndView jrnltxnapp(@ModelAttribute("batchfile")FileUpload flup2,HttpServletRequest request)  {
     //String dcode = accountgroupdet2.getCode();//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
     
     clientIpAddress = request.getRemoteAddr();
     
     String operresult1 = posttxnsService.postapprvjrnl(flup2.getBatchId(), dcompany, dbranch, 1, user.getCurruser().getUserId(), user.getCurrusercompany().getCurrentYear(), user.getCurrusercompany().getCurrentPeriod(), user.getCurrusercompany().getTimezone(), user.getCurrusercompany().getPostDate(),clientIpAddress);
     System.out.println("operresult1 "+ operresult1);
     String[] arrresult = operresult1.split(":::");
     boolean operresult2 = false;
     if (arrresult[1].equalsIgnoreCase("0")==false) {
      operresult2 = posttxnsService.updatependingentrydtbl(flup2.getBatchId(), dbranch, dcompany,user.getCurruser().getUserId(), 1,arrresult[1],arrresult[0],user.getCurrusercompany().getTimezone());
     }
      //do audit log, check for approval stqatus from 2 methods above - else display error message
     Map dmodel = new HashMap();
     dmodel.put("accttxns4apprv", fileuploadService.getJournaltxns4apprv(dcompany,dbranch,user.getCurruser().getUserId()));
     String retmess =  "";
     if (operresult2==true)
     {
       retmess = "Approval Successful. A mail will be sent to you on completion";
     }
     else
     {
       retmess = "Approval Failed";  
     }    
     return new ModelAndView("redirect:gl_jrnltxnapprv.htm?message= " + retmess + ". Batch Id is : " + flup2.getBatchId() + "&bid=" + flup2.getBatchId());

   }
    
    /**
     *
     * @param eventid
     * @param eventactioncode
     * @param ipaddr
     * @param username
     * @param timezone
     * @param actionitem
     * @param result
     * @param branch
     * @param company
     */
    public void auditlogcall(int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
       Activitylog ent;
       ent = new Activitylog();
       String theerrmess;        
       ent.setBranchid(branch); 
       //ent.setEvent(1);
       //ent.setAction("GLGAC");
       ent.setEvent(eventid);
       ent.setAction(eventactioncode);
       ent.setCompanyid(company); 
       ent.setUsername(username);
       ent.setTimezone(timezone); 
       ent.setDescription(""); 
       ent.setIpaddress(ipaddr);
       ent.setActionItem(actionitem);
       ent.setActionResult(result); 
       AuditlogService cliserv = new AuditlogService();
       String respo = cliserv.create(ent);
       System.out.println(respo);
       //theerrmess= cliserv.gettheerrmess();
       //System.out.println(theerrmess);
    }
    
    // download bulk transaction upload template
    @RequestMapping(value = "/accountUploadTempl", method = RequestMethod.GET)    
    public void hpuploadteml(HttpServletRequest req, HttpServletResponse response) throws Exception {
       
              File file = new File(downLoadPath + req.getParameter("downFile").replace("/", "").replace("\\", ""));
              
              InputStream is =null;
              try {
                  is = new FileInputStream(file);
              } catch (FileNotFoundException ex) {
                 // Logger.getLogger(SavingsController.class.getName()).log(Level.SEVERE, null, ex);
                  ex.printStackTrace();
              }
              
              // MIME type of the file
              response.setContentType("application/octet-stream");
              // Response header
              response.setHeader("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");
              // Read from the file and write into the response
              OutputStream os = null;
              try 
              {
                  os = response.getOutputStream();
              } catch (IOException ex) {
               
                  ex.printStackTrace();
              }
              byte[] buffer = new byte[1024];
              int len; 
              while ((len = is.read(buffer)) != -1) {
                  os.write(buffer, 0, len);
              }
              os.flush();
              os.close();
              is.close();             
       
    }
}
