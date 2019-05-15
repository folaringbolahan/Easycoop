/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.gl.AccounttxnsException;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.AccountgroupImpl;
import com.sift.gl.dao.AccountsetupImpl;
import com.sift.gl.dao.AccounttxnsImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.FileuploadImpl;
import com.sift.gl.dao.GenerictableviewImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Accounttxnsdetail;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.model.Generictableviewbean;
import com.sift.gl.model.Users;
import com.sift.gl.validator.AccountsetupValidator;
import com.sift.gl.validator.AccounttxnsetupValidator;
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

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class GenerictableviewController{
   //@Autowired 
   private GenerictableviewImpl generictableviewService;
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
   private Generictableviewbean globalgtbb;
   
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
         // accounttxnsetupValidator = new AccounttxnsetupValidator();
         // accountdet = new Account();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setGenerictableviewService(GenerictableviewImpl generictableviewService) {
      this.generictableviewService = generictableviewService;
     // accountdet = new Account();
   }
  
   @ModelAttribute("clientIpAddress")
   public String populateClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
   }
   
   
   /* 
   @RequestMapping(value = {"/gl_gentblview","gl/gl_gentblview"}, method = {RequestMethod.GET,RequestMethod.POST})
   public ModelAndView loadval(@RequestParam("id") String batchid,@RequestParam("nm") String reportcode)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch(); 
      String sql = "";
      List<String> colhdrs = new ArrayList<String>();
      List<String> colflds = new ArrayList<String>();
      String pagetitle = "";
      if (reportcode.equals("1")) {
          sql = "select * from accountstxnimportpending where reference = '" + batchid + "' and companyid = " + dcompany + " and branchid = " + dbranch + " and haserrors = 0";
          pagetitle = "Details of Transaction records ok for file reference " + batchid  ;
          colhdrs.add(0, "Row");colhdrs.add(1, "Account #");colhdrs.add(2, "Narration");colhdrs.add(3, "Amount");colhdrs.add(4, "Transaction Type");
          colflds.add(0, "Rownum");colflds.add(1, "Accountno");colflds.add(2, "Narration");colflds.add(3, "Amount");colflds.add(4, "Txntype");
      }
      
      Generictableviewbean gtbb = generictableviewService.buildtablebody(sql, pagetitle, colhdrs, colflds);
              
      Map dmodel = new HashMap();
      dmodel.put("gentableview",gtbb);
      return new ModelAndView("gl/gl_gentblview", dmodel);
   }
   */
   
   @RequestMapping(value = {"/gl_gentblview","gl/gl_gentblview"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String loadval(ModelMap model,HttpServletRequest request,@RequestParam("id") String batchid,@RequestParam("nm") String reportcode)  {
      int dcompany = user.getCurruser().getCompanyid();
      int dbranch = user.getCurruser().getBranch(); 
      String sql = "";
      List<String> colhdrs = new ArrayList<String>();
      List<String> colflds = new ArrayList<String>();
      String pagetitle = "";
      globalgtbb = null;
      if (reportcode.equals("1")) {
          sql = "select * from accountstxnimportpending where reference = '" + batchid + "' and companyid = " + dcompany + " and branchid = " + dbranch + " and haserrors = 0";
          pagetitle = "Details of Transaction records ok for file reference " + batchid  ;
          colhdrs.add(0, "Row");colhdrs.add(1, "Account");colhdrs.add(2, "Narration");colhdrs.add(3, "Amount");colhdrs.add(4, "Transaction Type");
          colflds.add(0, "Rownum");colflds.add(1, "Accountno");colflds.add(2, "Narration");colflds.add(3, "Amount");colflds.add(4, "Txntype");
      }
      
      if (reportcode.equals("2")) {
          sql = "select * from accountstxnimportpending where reference = '" + batchid + "' and companyid = " + dcompany + " and branchid = " + dbranch + " and haserrors = 1";
          pagetitle = "Details of failed Transaction records for file reference " + batchid  ;
          colhdrs.add(0, "Row");colhdrs.add(1, "Account");colhdrs.add(2, "Narration");colhdrs.add(3, "Amount");colhdrs.add(4, "Transaction Type");colhdrs.add(5, "Error");
          colflds.add(0, "Rownum");colflds.add(1, "Accountno");colflds.add(2, "Narration");colflds.add(3, "Amount");colflds.add(4, "Txntype");colflds.add(5, "Errormessage");
      }
      
      if (reportcode.equals("4")) {
          sql = "select (@row_number:=@row_number + 1) as rownum,a.accountno,a.narrative,abs(a.amount) as amount,a.docref, if(a.amount<0,'D','C') as transtype , b.postdate,c.name from txnsua a inner join txnsheaderua b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.companyid = b.companyid and a.branchid = b.branchid inner join accounts c on a.accountno = c.accountno and a.companyid = c.companyid and a.branchid = c.branch,(SELECT @row_number:=0) AS t where headerid = " + batchid + " and b.companyid = " + dcompany + " and b.branchid = " + dbranch + "";
          pagetitle = "Details of Journal Postings records with reference " + batchid  ;
          colhdrs.add(0, "Row");colhdrs.add(1, "Account");colhdrs.add(2, "Title");colhdrs.add(3, "Narration");colhdrs.add(4, "Amount");colhdrs.add(5, "Txn Type");colhdrs.add(6, "Doc. Ref");colhdrs.add(7, "Date");
          colflds.add(0, "Rownum");colflds.add(1, "Accountno");colflds.add(2, "Name");colflds.add(3, "Narrative");colflds.add(4, "Amount");colflds.add(5, "Transtype");colflds.add(6, "Docref");colflds.add(7, "Postdate");
      }
     /* if (reportcode.equals("3")) {
          sql = "select * from qrynexthprepaymtscdl where companyid = " + dcompany + " and branchid = " + dbranch + "";
          pagetitle = "Repayment Schedule Templates";
          colhdrs.add(0, "Reference");colhdrs.add(1, "Account");colhdrs.add(2, "Repayment Amount");colhdrs.add(3, "Date");
          colflds.add(0, "Refid");colflds.add(1, "Accountno");colflds.add(2, "0");colflds.add(3, "RpymtDate");
      }*/
      Generictableviewbean gtbb = generictableviewService.buildtablebody(sql, pagetitle, colhdrs, colflds);
      globalgtbb = gtbb;
      //System.out.println("check it " + gtbb.getHeader().size());
      model.addAttribute("pagetitle", gtbb.getPagetitle()); 
      model.addAttribute("headere", gtbb.getHeader());
      model.addAttribute("body", gtbb.getBody()); 
      model.addAttribute("dBatchId", batchid);
      model.addAttribute("nm", reportcode);
     return "gl/gl_gentblview";
   }
   
   
   @RequestMapping(value = {"gl/gl_downloadrecs/{batchId}/{nm}", "/gl_downloadrecs/{batchId}/{nm}"}, method = RequestMethod.POST)
    public ModelAndView downloadFailedRecordsToExcel(@PathVariable("batchId") String batchId, @PathVariable("nm") String nm,HttpServletRequest req, HttpServletResponse response) {
        String filename = "dw" + user.getCurruser().getCompanyid() + String.valueOf(System.currentTimeMillis());
        generictableviewService.writeListToExcel(globalgtbb, response,filename);
        return new ModelAndView("redirect:/gl/gl_gentblview");
    }
}
