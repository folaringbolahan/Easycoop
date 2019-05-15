/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.AuditlogService;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.AccountgroupImpl;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.FileuploadImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.model.Users;
import com.sift.gl.validator.AccountsetupValidator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.util.*;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class AccountuploadController{
   //@Autowired 
   private FileuploadImpl fileuploadService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private AccountsetupValidator accountsetupValidator;
   private Account accountdet;
   private String accountseg;
   private String clientIpAddress;
   List<Accountgrpclassdetails> accountclasses;
   List<Accountgrprepdetails> accreportgroups;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          accountsetupValidator = new AccountsetupValidator();
          accountdet = new Account();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setFileuploadService(FileuploadImpl fileuploadService) {
      this.fileuploadService = fileuploadService;
     // accountdet = new Account();
   }
  
   @ModelAttribute("clientIpAddress")
   public String populateClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
   }
   
   @RequestMapping(value = {"gl/gl_acctuploadfile","/gl_acctuploadfile"},method=RequestMethod.POST)
   public ModelAndView accountsuploadfile( HttpServletRequest request,@RequestParam("fileData") MultipartFile file)  {
     String batchid=null;

       if (!file.isEmpty()) {
           batchid = "gl" + String.valueOf(System.currentTimeMillis());
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
               
               fileuploadService.addFile(fileUpload);
               auditlogcall(18,"GLAUP",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),batchid,"File Batch Upload",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
               return new ModelAndView("redirect:gl_acctupvalid.htm?message=Upload awaiting verification. The assigned Upload Batch Id is : " + batchid + "&bid=" + batchid);
           } catch (Exception e) {
               //e.printStackTrace();
               return new ModelAndView("redirect:/error.htm?er=No Valid file was specified");
           }
       } else {
           return new ModelAndView("redirect:/error.htm?er=Attempting to upload empty file. Select valid file");
       }		     
     }
    
   @RequestMapping(value = {"/gl_accountsupload"}, method = {RequestMethod.GET})
   public ModelAndView accountupload(@ModelAttribute("accbulkupload")FileUploadBean fileUploadBean, BindingResult result)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
       Map<String, Object> model = new HashMap<String, Object>();
       //clientIpAddress = request.getRemoteAddr();
     return new ModelAndView("gl/gl_accountsupload", model);
   }
   @RequestMapping(value = {"/gl_acctupvalid","gl/gl_acctupvalid"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView accountuploadval(@RequestParam("bid") String batchid)  {
      Map dmodel = new HashMap();
      FileUpload batchfile2 = new FileUpload();
      batchfile2.setBatchId(batchid);
      dmodel.put("batchfile",batchfile2);
      return new ModelAndView("gl/gl_acctupvalid", dmodel);
   }
   @RequestMapping(value = {"/gl_accountuplitmadd","gl/gl_accountuplitmadd"}, method = {RequestMethod.POST})
   public ModelAndView accountuplitmadd(@ModelAttribute("batchfile")FileUpload flup2,HttpServletRequest request)  {
       clientIpAddress = request.getRemoteAddr();
       TimeZone timeZone;
       timeZone = TimeZone.getTimeZone(user.getCurrusercompany().getTimezone());
       Calendar rightNow = Calendar.getInstance(timeZone);
       fileuploadService.addFilecontents(flup2.getBatchId(),user.getCurruser().getCompanyid(),user.getCurruser().getBranch(),user.getCurrusercompany().getBaseCurrency(), rightNow.getTime(),clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
       String sumrep = fileuploadService.getSummaryreport();
       String detrep = fileuploadService.getDetailederrreport(flup2.getBatchId(),user.getCurruser().getCompanyid(),user.getCurruser().getBranch());
       Map dmodel = new HashMap();
      FileUpload batchfile2 = new FileUpload();
      batchfile2.setBatchId(flup2.getBatchId());
      batchfile2.setUploadStatus(sumrep);
      batchfile2.setUploadFilename(detrep);
      dmodel.put("batchfilereport",batchfile2);
      return new ModelAndView("gl/gl_accountuplitmaddre", dmodel);
   }
   
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
