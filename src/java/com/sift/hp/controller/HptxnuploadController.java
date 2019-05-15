/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.controller;
import com.sift.gl.controller.*;
import com.sift.gl.AuditlogService;
import com.sift.gl.NotificationService;

import com.sift.gl.dao.AccounttxnsImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.hp.dao.HPFileuploadImpl;
import com.sift.gl.dao.PosttxnsImpl;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.model.Users;
import com.sift.hp.validator.HptxnuploadValidator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/** controller handles batch upload of HP files
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("hp/")
public class HptxnuploadController{
   //@Autowired 
   private HPFileuploadImpl fileuploadService;
   private PosttxnsImpl posttxnsService;
   @Autowired
    private NotificationService notificationService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private AccounttxnsImpl accounttxnsService;
   private HptxnuploadValidator hptxnuploadValidator;
   private String clientIpAddress;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @Value("${emailsubject.approvehprpmt}")
   private String emailsubjectapprovehprpmt;
   
    /**
     *
     * @param binder
     */
    @InitBinder
   public void initBinder(WebDataBinder binder) {
          hptxnuploadValidator = new HptxnuploadValidator();
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
    public void setFileuploadService(HPFileuploadImpl fileuploadService) {
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
   
    /** method handles upload of repayments of HP transactions
     *
     * @param fileUploadBean
     * @param request
     * @param file
     * @param result
     * @return
     */
    @RequestMapping(value = {"hp/hp_rpymttxnuploadfile","/hp_rpymttxnuploadfile"},method=RequestMethod.POST)
   public ModelAndView rpymttxnuploadfile(@ModelAttribute("rpymttxnbulkupload")FileUploadBean fileUploadBean, HttpServletRequest request,@RequestParam("fileData") MultipartFile file, BindingResult result)  {
     String batchid=null;
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String startdateinstring = "";
     Date txndate = new Date();
     
       if (!file.isEmpty()) {
           batchid = "hprpt" + String.valueOf(System.currentTimeMillis());
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
               fileUpload.setFilesum(fileUploadBean.getFilesum());
               
               //validate file here
               hptxnuploadValidator.validate(fileUpload, result);
               String redurlpath;
               if (result.hasErrors() == true) {
                   List<FieldError> lerr = result.getFieldErrors();
                   for (FieldError err : lerr) {
                       System.out.println(err);
                   }
                   return new ModelAndView("hp/hp_repaymttxnupload", "rpymttxnbulkupload", fileUploadBean);
               }
               
               fileuploadService.addtxnFile(fileUpload,user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurrusercompany().getBaseCurrency(), user.getCurrusercompany().getCurrentsystemDate(),clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
               
               Future<String> domsg=null;
               String mailsubject = emailsubjectapprovehprpmt;
               //System.out.println("mailsubject " + mailsubject);
               Map model = new HashMap();
               domsg=fileuploadService.runcheck(batchid, user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurrusercompany().getBaseCurrency(), user.getCurrusercompany().getCurrentsystemDate(),clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),mailsubject,notificationService);
              // domsg.isDone();  //if done send mail and set 
              //// while (!domsg.isDone()) {
              ////  System.out.println("Still processing upload...notify by mail when done");
              //// }
               auditlogcall(121,"HPRTU",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),batchid,"File Batch Upload",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
               return new ModelAndView("redirect:hp_repaytxnupresp.htm?message=Upload awaiting approval. The assigned Upload Batch Id is : " + batchid + "&bid=" + batchid);
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
    @RequestMapping(value = {"/hp_repaymttxnupload"}, method = {RequestMethod.GET})
   public ModelAndView repaytxnupload(@ModelAttribute("rpymttxnbulkupload")FileUploadBean fileUploadBean, BindingResult result) throws Exception  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       //Map<String, Object> model = new HashMap<String, Object>();
       Map model = new HashMap();
     return new ModelAndView("hp/hp_repaymttxnupload", model);
   }
   
   
   
    /**
     *
     * @param batchid
     * @return
     */
    @RequestMapping(value = {"/hp_repaytxnupresp","hp/hp_repaytxnupresp"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView repaytxnuploadval(@RequestParam("bid") String batchid)  {
      Map dmodel = new HashMap();
      FileUpload batchfile2 = new FileUpload();
      batchfile2.setBatchId(batchid);
      dmodel.put("batchfile",batchfile2);
      return new ModelAndView("hp/hp_repaytxnupresp", dmodel);
   }
   
   
    /** method handles display of HP repayments awaiting approval
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hp_batchpayapprv","hp/hp_batchpayapprv"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String hptxn4apprv(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("hpreptxns4apprv", fileuploadService.getAccttxns4apprv(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "hp/hp_batchpayapprv";
   }
   
    /** method handles display of HP repayments awaiting approval as view-only
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hp_batchpayview","hp/hp_batchpayview"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String hptxn4view(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("hpreptxns4apprv", fileuploadService.getAccttxns4view(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "hp/hp_batchpayview";
   }
   
    /**
     *
     * @param batchid
     * @return
     */
    @RequestMapping(value = {"/hp_cfpfreptxn4apprv","hp/hp_cfpfreptxn4apprv"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView cftepuptxnapp(@RequestParam("id") String batchid)  {
      Map dmodel = new HashMap();
      FileUpload batchfile2 = new FileUpload();
      batchfile2.setBatchId(batchid);
      dmodel.put("batchfile",batchfile2);
      return new ModelAndView("hp/hp_cfpfreptxn4apprv", dmodel);
   }
   
    /** method handles rejection action of repayment transactions uploaded awaiting approval
     *
     * @param batchid
     * @return
     */
    @RequestMapping(value = {"hp_cfpfreptxn4apprvrj","hp/hp_cfpfreptxn4apprvrj"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView cfpfrepuptxnapprj(@RequestParam("id") String batchid)  {
      Map dmodel = new HashMap();
      fileuploadService.rejectFile(batchid,user.getCurruser().getCompanyid(),user.getCurruser().getBranch());
      auditlogcall(20,"GLATR",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),batchid,"File Batch Rejection of Upload",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
      dmodel.put("hpreptxns4apprv", fileuploadService.getAccttxns4apprv(user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurruser().getUserId()));
     return new ModelAndView("hp/hp_batchpayapprv", dmodel);
   }
   
    /** method posts approved HP transactions of repayments
     *
     * @param flup2
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hp_pfreptxn4apprv","hp/hp_pfreptxn4apprv"},method=RequestMethod.POST)
   public ModelAndView repuptxnapp(@ModelAttribute("batchfile")FileUpload flup2,HttpServletRequest request)  {
     //String dcode = accountgroupdet2.getCode();//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dacgrp = 0;
     int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
     
     clientIpAddress = request.getRemoteAddr();
     posttxnsService.updatefileuploadtblhp(flup2.getBatchId(), dbranch, dcompany,user.getCurruser().getUserId(), -1);
     posttxnsService.posthp(flup2.getBatchId(), dcompany, dbranch, 1, user.getCurruser().getUserId(), user.getCurrusercompany().getCurrentYear(), user.getCurrusercompany().getCurrentPeriod(), user.getCurrusercompany().getTimezone(), user.getCurrusercompany().getPostDate(),clientIpAddress);
      //do audit log
     Map dmodel = new HashMap();
     dmodel.put("hpreptxns4apprv", fileuploadService.getAccttxns4apprv(dcompany,dbranch,user.getCurruser().getUserId()));
     return new ModelAndView("redirect:hp_batchpayapprv", dmodel);
   }
   
    /** method handles display page of template download of transaction file for repayments
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hp_scdltempdwn","hp/hp_scdltempdwn"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String hptxn4dwn(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
      model.addAttribute("hpreptxns4apprv", fileuploadService.getAccttxns4dwn(dcompany,dbranch,user.getCurruser().getUserId()));
     //clientIpAddress = request.getRemoteAddr();
     return "hp/hp_scdltempdwn";
   }
   
    /** method handles template download of transaction file for repayments
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = {"hp_downldsdtemp", "hp/hp_downldsdtemp"}, method = RequestMethod.POST)
    public ModelAndView downloadFailedRecordsToExcelhp(HttpServletRequest req, HttpServletResponse response) {
        int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch();
       String filename = "sdl" + user.getCurruser().getCompanyid() + String.valueOf(System.currentTimeMillis());
        fileuploadService.writeListToExcel(fileuploadService.getAccttxns4dwn(dcompany,dbranch,user.getCurruser().getUserId()), response,filename);
        return new ModelAndView("redirect:hp/hp_scdltempdwn");
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
}
