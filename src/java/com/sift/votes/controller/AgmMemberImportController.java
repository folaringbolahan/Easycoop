/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.controller;

import com.sift.easycoopfin.models.FileMeta;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Generictableviewbean;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.savings.utility.HelperUtil;
import com.sift.savings.utility.SavingsTaskth;
import com.sift.votes.bean.VotAgmBean;
import com.sift.votes.model.Activitylog;
import com.sift.votes.service.VotMembersErrorsService;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotMembers;
import com.sift.votes.model.VotMembersErrors;
import com.sift.votes.service.VotAgmService;
import com.sift.votes.service.VotCompanyService;
import com.sift.votes.service.VotFileUploadService;
import com.sift.votes.service.VotMemberFileService;
import com.sift.votes.service.VotMemberService;
import com.sift.votes.service.AuditlogService;
import com.sift.votes.service.VotGenerictableviewService;
import com.sift.votes.utility.AgmHelperUtility;
import com.sift.votes.utility.VotBeanMapperUtility;
import com.sift.votes.utility.VotMemberTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nelson Akpos
 */
@Controller
public class AgmMemberImportController {
     VotBeanMapperUtility votutility = new VotBeanMapperUtility();
     @Autowired
     VotMembersErrorsService votMembersErrorsService;
     @Autowired
    private VotCompanyService votCompanyService;
     
    @Autowired
    private VotAgmService votAgmService;
    
    @Autowired
    private VotMemberService votMemberService;
    
    @Autowired
    private VotMemberFileService votMemberFileService;
    
    @Autowired 
    private VotGenerictableviewService votGenerictableviewService;
    
    @Autowired
    private  AgmHelperUtility agmhelperUTIL;
     
    @Autowired
    private VotFileUploadService votFileUploadService;
    
    
     LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    @Autowired
    private CurrentuserdisplayImpl user;
    
    HelperUtil util = new HelperUtil();
    PersistentSession session;
    String clientIPAddress;
     @Autowired
    private FileUploadItemsService fileUploadItemsService;
   
    @Autowired
    private TaskExecutor taskExecutor;
    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AgmMemberImportController.class);
     @Autowired
    @Value("${DOWNLOAD_PATH}")
    private String downLoadPath;
     
     private Generictableviewbean globalgtbb;
    //for external member upload
       @RequestMapping(value="/externalMemberImport", method = RequestMethod.GET)
      public ModelAndView showUploadForm(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>();
           //model.put("agms", prepareListofVotBean(votAgmService.listExternalAgm()));
           model.put("agms", prepareListofVotBean(votAgmService.listActiveExternalAgm()));
           String ref = "";
           String agid = "";
           boolean haserrors = false;
           ref = req.getParameter("rf");
           agid = req.getParameter("agid");
           model.put("batchref", ref);
           model.put("agi", agid);
           System.out.println("ref " + ref + " agmid " + agid);
           if ((ref!=null) && (ref.isEmpty()==false))  {
               haserrors = votMembersErrorsService.votMembersErrorsexist(Integer.parseInt(agid), ref);
           }
           model.put("haserrors", haserrors);
           
            return new ModelAndView("externalImportUpload", model);
   
   }
        // download agm member template
    @RequestMapping(value = "/downloadagmtmpl", method = RequestMethod.GET)
    public void downloadAgmMemberTempl(HttpServletRequest req, HttpServletResponse response) throws Exception {

        File file = new File(downLoadPath + req.getParameter("downFile").replace("/", "").replace("\\", ""));

        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(SavingsController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        // MIME type of the file
        response.setContentType("application/octet-stream");
        // Response header
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        // Read from the file and write into the response
        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException ex) {
            // Logger.getLogger(MemberBatchController.class.getName()).log(Level.SEVERE, null, ex);
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
     @RequestMapping(value = "/extmembuploads", method = RequestMethod.POST)
    public @ResponseBody
     ModelAndView uploadagm(MultipartHttpServletRequest request, HttpServletResponse response) throws PersistentException {
    //LinkedList<FileMeta> uploadagm(MultipartHttpServletRequest request, HttpServletResponse response) throws PersistentException {
        LinkedList<FileMeta> returnobj = null;
        String referenceNumber;
        //System.out.println("i am now inside the upload agm method");
        System.out.println("called twice?");
         //get Agm id and use to retrieve comapny id
         String agmid=request.getParameter("description");
         int agmid_int= Integer.parseInt(agmid);
         String loggeduser= request.getParameter("createdby");
         //use agmid to get companyid 
         int companyid=agmhelperUTIL.companyid(agmid);
         int agmcompany=companyid;
         int companyrefid=agmhelperUTIL.getCompanyrefid(companyid);
         Integer userbranchid= user.getCurruser().getBranch();
        // System.out.println("logged in user branch id"+ userbranchid);
         //use company id to get company refid from vot_company table
         System.out.println("the company ref id is "+companyrefid);
         System.out.println("AGM ID  "+agmcompany);
      
        ////just added yomi
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        //returnobj = savingService.performBulkSave(request, user.getCurruser());
         System.out.println("the request is " +request.getFile("fileupload") );
            System.out.println("the current user is " + user.getCurruser() );
        referenceNumber = votMemberService.performBulkSave2(request, user.getCurruser());
        System.out.println("the reference number is "+  referenceNumber );
        VotMemberTask performVotMembTask = new  VotMemberTask();
          performVotMembTask.setTaskExecutor(taskExecutor);
        System.out.println("print statement after setting task executor");
        //System.exit(1);
        //performVotMembTask.readExcelasync(dbranch,dcompany, referenceNumber,agmcompany, agmid_int,votFileUploadService,votMemberService,loggeduser,votutility,agmhelperUTIL,votMembersErrorsService);
        performVotMembTask.readExcelasync(dbranch,dcompany, referenceNumber,agmcompany, agmid_int,votFileUploadService,votMemberFileService,loggeduser,votutility,agmhelperUTIL,votMembersErrorsService);
        //send email to second admin on successful member upload
        
        //end mail
        Activitylog activity = new Activitylog();
        activity.setEvent(137);
        activity.setAction("IMP-VOT");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + "easycoop member import" );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(request.getRemoteHost());
        activity.setUsername(request.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(agmid_int);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        //end just added  uncomment next line
        //// return savingService.performBulkSave(request, user.getCurruser());
       /// return returnobj;
        return new ModelAndView("redirect:/externalMemberImport?rf=" + referenceNumber + "&agid=" + agmid_int);
    }
       
        private List<VotAgmBean> prepareListofVotBean(List<VotAgm> votAgms) {
        List<VotAgmBean> beans = null;
        
        if (votAgms != null && !votAgms.isEmpty()) {
            beans = new ArrayList<VotAgmBean>();
           VotAgmBean bean = null;

            for (VotAgm votAgm : votAgms) {
                bean = new VotAgmBean ();
                bean.setId(votAgm.getId());
                bean.setCompanyid(votAgm.getCompanyid());
                bean.setBallotid(votAgm.getBallotid());
                bean.setStartdate(votAgm.getStartdate());
                bean.setEnddate(votAgm.getEnddate());
                bean.setStarttime(votAgm.getStarttime());
                bean.setEndtime(votAgm.getEndtime());
                bean.setBaseurl(votAgm.getBaseurl());
                bean.setActive(votAgm.getActive());
                bean.setAgmyear(votAgm.getAgmyear());
                bean.setClosed(votAgm.getClosed());
                bean.setCreatedate(votAgm.getCreatedate());
                bean.setImportsource(votAgm.getImportsource());
                bean.setDescription(votAgm.getDescription());
                bean.setCompanyName(votCompanyService.getVotCompany(votAgm.getCompanyid()).getCompanyname());
               beans.add(bean);
            }
        }
        return beans;
    }
        
        
    @RequestMapping(value = "/extimpuploaderr", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView extimpuploadError(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Map<String ,Object> model = new HashMap<String, Object>();
        String ref = req.getParameter("rf");
        String agid = req.getParameter("agid");
        int agidnt = 0;
        try 
        {
            agidnt = Integer.parseInt(agid);
        }
        catch(NumberFormatException nuex) 
        {
            agidnt = 0;
        }
        List<String> colhdrs = new ArrayList<String>();
        String pagetitle = "";
        
        pagetitle = "Details of Error records for file reference " + ref  ;
        colhdrs.add(0, "Member id");colhdrs.add(1, "First name");colhdrs.add(2, "Middle name");colhdrs.add(3, "Surname");colhdrs.add(4, "Email");
        colhdrs.add(5, "Phone");colhdrs.add(6, "Vote Units");colhdrs.add(7, "Error(s)");colhdrs.add(8, "Batch Ref");colhdrs.add(9, "AGM Id");  
        List<VotMembersErrors> votMembersErrors = votMembersErrorsService.listAllVotMembersErrorsBybatch(agidnt, ref);
        model.put("votersErrors", votMembersErrors);
        model.put("referenceNumber", ref);
        model.put("agid", agid);
        Generictableviewbean gtbb = new Generictableviewbean();
        gtbb.setBody(votGenerictableviewService.buildtablebody(votMembersErrors));//, pagetitle, colhdrs);
        gtbb.setPagetitle(pagetitle);
        gtbb.setHeader(colhdrs);
        globalgtbb = gtbb;
        return new ModelAndView("extimpuploaderr", model);
    }   
    
    @RequestMapping(value = "/extimpuploaderrold", method = RequestMethod.GET)
    public ModelAndView extimpuploadErrorold(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Map<String ,Object> model = new HashMap<String, Object>();
        String ref = req.getParameter("rf");
        String agid = req.getParameter("agid");
        int agidnt = Integer.parseInt(agid);
        List<String> colhdrs = new ArrayList<String>();
        String pagetitle = "";
        
        pagetitle = "Details of Error records for file reference " + ref  ;
        colhdrs.add(0, "Member id");colhdrs.add(1, "First name");colhdrs.add(2, "Middle name");colhdrs.add(3, "Surname");colhdrs.add(4, "Email");
        colhdrs.add(5, "Phone");colhdrs.add(6, "Vote Units");colhdrs.add(7, "Error(s)");colhdrs.add(8, "Batch Ref");colhdrs.add(9, "AGM Id");  
        List<VotMembersErrors> votMembersErrors = votMembersErrorsService.listAllVotMembersErrorsBybatch(agidnt, ref);
        model.put("votersErrors", votMembersErrors);
        model.put("referenceNumber", ref);
        model.put("agid", agid);
        Generictableviewbean gtbb = new Generictableviewbean();
        gtbb.setBody(votGenerictableviewService.buildtablebody(votMembersErrors));//, pagetitle, colhdrs);
        gtbb.setPagetitle(pagetitle);
        gtbb.setHeader(colhdrs);
        globalgtbb = gtbb;
        return new ModelAndView("extimpuploaderr", model);
    }   
    
    
    @RequestMapping(value = {"voterdownloadrecs/{batchId}/{nm}"}, method = RequestMethod.POST)
    public ModelAndView downloadFailedRecordsToExcel(@PathVariable("batchId") String batchId, @PathVariable("nm") String nm,HttpServletRequest req, HttpServletResponse response) throws Exception  {
        String filename = "vedw" + batchId;
        votGenerictableviewService.writeListToExcel(globalgtbb, response,filename);
        return new ModelAndView("redirect:/extimpuploaderr");
    }
    
    
    @RequestMapping(value = "/externalImporterrchk", method = RequestMethod.GET)
    public ModelAndView externalImporterrchk(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Map<String ,Object> model = new HashMap<String, Object>();
        model.put("agms", prepareListofVotBean(votAgmService.listExternalAgm()));
        return new ModelAndView("externalImporterrchk", model);
    }   
    
    
}
