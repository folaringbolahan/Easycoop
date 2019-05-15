/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.controller;

import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.BatchUploadType;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.sift.financial.member.services.*;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import com.sift.financial.utility.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.servlet.HandlerMapping;

/**
 *
 * @author baydel200
 */
@Controller
public class MemberBatchController {
    
      private static final Log log = LogFactory.getLog(MemberBatchController.class);

@Autowired
private BatchManagementImpl memberService;

//@Autowired
private ConversionService conversionService;

@Autowired
private CurrentuserdisplayImpl user;
        
@Resource (name="batchEvents")
private Map<String, String> batchEvents;

@Autowired
@Value("${startPosBat}")
private String startPosBat; 

@Autowired
@Value("${UPLOAD_PATH}")
private String uploadPath;

 @Autowired
 @Value("${DOWNLOAD_PATH}")
 private String downLoadPath;

@Autowired
@Value("${statusProcessed}")
private String statusProcessed;

@Autowired
@Value("${statusValidated}")
private String statusValidated;

@Autowired
@Value("${processedMember}")
private String processedMember;

@Autowired
@Value("${failedMember}")
private String failedMember;

@Autowired
@Value("${memberValidated}")
private String valdMember;

@Resource (name="viewData")
private Map<String, Map<String, String>> viewData;
    
   
@RequestMapping(value="/membatch/singleUpload")
public ModelAndView singleUpload(@ModelAttribute("batchUploadFile") BatchUploadFile batchUploadFile, BindingResult result){
    
    Date today = new Date();
    batchUploadFile.setCreatedBy(user.getCurruser().getUserId());
    batchUploadFile.setCreatedDate(new Timestamp(today.getTime()));
    
return new ModelAndView("/members/singleUpload", "batchUploadFile", batchUploadFile);
    //return "/members/singleUpload";
}

@RequestMapping(value="/membatch/multipleUpload")
public ModelAndView multiUpload(@ModelAttribute("batchUploadFile") BatchUploadFile batchUploadFile, BindingResult result){
    
     Date today = new Date();
    batchUploadFile.setCreatedBy(user.getCurruser().getUserId());
    batchUploadFile.setCreatedDate(new Timestamp(today.getTime()));
    
return new ModelAndView("/members/multipleUpload", "batchUploadFile", batchUploadFile);
    //return "/member/multipleUpload";
}
    

@RequestMapping(value="/membatch/singleSave", method=RequestMethod.POST )
    public ModelAndView singleSave(@ModelAttribute("batchUploadFile") BatchUploadFile uploadFile, @RequestParam("file") MultipartFile file, HttpServletRequest req){
    	//System.out.println("File Description:"+desc);
    	String fileName = null;
         Date today = new Date();
        FileUtil fileUtility = new FileUtil();
    	if (!file.isEmpty()) {
            try {
                  log.info(req.getContextPath());

                String curEventShort = getBatchEvents().get(uploadFile.getAction());
                
                  log.info("curEventShort :: " + curEventShort);
                memberService.setUser(user);  
                Event curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);

                Status initStatus = memberService.getStatusDAO().getStatusFromShort(startPosBat) ;

                StatusFlow statusFlow = memberService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, initStatus);
           
                
                FileSystem fileSystem = FileSystems.getDefault();
                BatchUploadType uploadType = memberService.getBatchUploadTypeDAO().findById(uploadFile.getBatchUploadType().getBatchUploadTypeId());
                fileName = fileUtility.getServerFileName(uploadType.getUploadTypeShort(),user.getCurruser().getCompanyid().toString(),user.getCurruser().getBranch().toString(), file.getOriginalFilename());
                
                 log.info(fileName);
                byte[] bytes = file.getBytes();
                
                
                uploadFile.setBatchUploadFileName(fileName);
                uploadFile.setCreatedBy(fileName);
                uploadFile.setCreatedDate(new Timestamp(today.getTime()));
                uploadFile.setBatchUploadType(uploadType);
                uploadFile.setBranchId(user.getCurruser().getBranch());
                uploadFile.setCompanyId(user.getCurruser().getCompanyid());
                uploadFile.setCreatedBy(user.getCurruser().getUserId());
                uploadFile.setOriginalFileName(file.getOriginalFilename());
                
                             
                String targetDirectory = fileSystem.getSeparator()+ "etc" +  fileSystem.getSeparator() + "upload";
                log.info(targetDirectory);
                
                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath + fileName)));
                
                buffStream.write(bytes);
                
                buffStream.close();
                
                     memberService.addDetail(uploadFile, statusFlow, req);
                
                log.info("God have mercy....");
                return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + memberService.getMsg());
                
                
            } catch (Exception e) {
                e.printStackTrace();
                  log.info("here in error");
                return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + e.getMessage());
            }
        } else {
            return new ModelAndView("redirect:/finResult/result.htm?textMsg=Unable to upload. File is empty.");
        }
    }
    
    
    @RequestMapping(value="/membatch/multipleSave", method=RequestMethod.POST )
    public @ResponseBody String multipleSave(@RequestParam("file") MultipartFile[] files, HttpServletRequest req){
    	String fileName = null;
    	String msg = "";
    	if (files != null && files.length >0) {
    		for(int i =0 ;i< files.length; i++){
	            try {
	                fileName = files[i].getOriginalFilename();
	                byte[] bytes = files[i].getBytes();
	                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File("F:/cp/" + fileName)));
	                buffStream.write(bytes);
	                buffStream.close();
	                msg += "You have successfully uploaded " + fileName +"<br/>";
	            } catch (Exception e) {
	                return "You failed to upload " + fileName + ": " + e.getMessage() +"<br/>";
	            }
    		}
    		return msg;
        } else {
            return "Unable to upload. File is empty.";
        }
    }
   
    @RequestMapping(value = {"/membatch/pendingBatchValidation","/membatch/pendingBatchApproval"}, method = RequestMethod.GET)
    public ModelAndView pendingRequests(HttpServletRequest req, HttpServletResponse res)
    {
    Map<String, Object> model = new HashMap<String, Object>();
    
    String page= customutil.getActualPage((String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
    String currStatus="";
    String vwTitle= "";
    String vwBreadCrumb="";
    
    if(page.equals("pendingBatchValidation"))
    {
       currStatus = statusProcessed; 
       vwTitle = "Upload Files Pending Validation";
       vwBreadCrumb ="Batch Validation";
    }
    else  if(page.equals("pendingBatchApproval"))
    {
         currStatus = statusValidated;
         vwTitle = "Upload Files Pending Approval";
         vwBreadCrumb ="Batch Approval";
    }
    String vwName = "/members/" + page;
    memberService.setUser(user);
    
    List requests=memberService.listReferences(currStatus);
    
    //List updatedReq = null;
    for(Object obj : requests)
    {
        BatchUploadFile newObj= (BatchUploadFile)obj;
        
        log.info("newObj    :::: " +  newObj.getBatchUploadType().getViewValLink());
    }
    model.put("listFiles", requests); 
    model.put("listTitle", vwTitle);    
    model.put("listBreadCrumb", vwBreadCrumb);  
    
    return new ModelAndView(vwName, model);

    }
    
    
    @RequestMapping(value = {"/membatch/batMemberList", "/membatch/falMemberList","/membatch/valMemberList","/membatch/batStockList", "/membatch/falStockList","/membatch/valStockList","/membatch/batAddrList", "/membatch/falAddrList","/membatch/valAddrList","/membatch/batExtrafldList", "/membatch/falExtrafldList","/membatch/valExtrafldsList"}, method = RequestMethod.GET)
    public ModelAndView pendingBatchMembers(HttpServletRequest req, HttpServletResponse res)
    {
        
        String batchStat = null;
        
        Map<String, Object> model = new HashMap<String, Object>();
        
        String vwName /*= "/members/batchMemberList"*/;
        String vwTitle = "";
        String vwType = "";

        /**String pattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        int pos1 = pattern.lastIndexOf("/");
        int pos2 = pattern.indexOf(".");

        String viewPage = pattern.substring(pos1+1, pos2);
        * **/
        
        String viewPage= customutil.getActualPage((String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
        
        Map<String, String> curData = viewData.get(viewPage);
        
         batchStat = curData.get("status");
         vwTitle  = curData.get("title");
         vwType  =  curData.get("type");
         vwName  = curData.get("page");
         
         System.out.println("vwName vwType vwTitle batchStat " + vwName + "  " + vwType + " " + vwTitle + " " + batchStat );

        /**if(viewPage.equalsIgnoreCase("batchMemberList"))
        {
            batchStat = processedMember;
            vwTitle  = "Processed Member List";
        }
        else if(viewPage.equalsIgnoreCase("faliedMemberList"))
        {
            batchStat = failedMember;
            vwTitle  = "Member List With Errors";
        }
        else if(viewPage.equalsIgnoreCase("valMemberList"))
        {
            batchStat = valdMember;
            vwTitle  = "Validated Member List";
        }
        **/
         
        memberService.setUser(user);
        memberService.setViewType(vwType);
        
        List requests=memberService.listDetail(Integer.parseInt(req.getParameter("id")), batchStat);

        model.put("listMembers", requests);  
        model.put("title", vwTitle);
        if(memberService.getFailFile()!=null && !memberService.getFailFile().equals(""))
        {
          model.put("failFile", memberService.getFailFile());
        }

        return new ModelAndView(vwName, model);
    }
    
    @RequestMapping(value = {"/membatch/valMembers","/membatch/refMembers","/membatch/valStocks","/membatch/refStocks","/membatch/valAddress","/membatch/refAddress","/membatch/valExtraflds","/membatch/refExtraflds"}, method = RequestMethod.GET)
    public ModelAndView validateMembers(HttpServletRequest req, HttpServletResponse res)
    {
        //String act = "VALID-BATCH";
        //String curEventShort = getBatchEvents().get(act);
        memberService.setUser(user);  
        
        String page= customutil.getActualPage((String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
        String pageStart = page.substring(0, 3);
       
        log.info("page ::: " + page);
        log.info("pageStart ::: " + pageStart);
        
        String curEventShort = getBatchEvents().get(page);
        log.info("curEventShort ::: " + curEventShort);
        //memberService.setPassRecord(page.equals("valMembers")?true:false);
        
        memberService.setPassRecord(pageStart.equals("val")?true:false);
       
        log.info("memberService.passRecord ::: " + memberService.isPassRecord());
        
        Event curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);
        
        BatchUploadFile theFile = (BatchUploadFile)memberService.getBatchUploadFileDAO().getFileByIdComp(Integer.parseInt(req.getParameter("id")), user.getCurruser().getCompanyid(),user.getCurruser().getBranch());
  
        StatusFlow statusFlow = memberService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, theFile.getStatus());
          
        memberService.validateDetail(theFile, statusFlow, req);
        
        return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + memberService.getMsg());
    }
    
    
    @RequestMapping(value = {"/membatch/apprMembers","/membatch/rejMembers","/membatch/apprMembersWithout","/membatch/apprStocks","/membatch/rejStocks","/membatch/apprStocksWithout","/membatch/apprAddress","/membatch/rejAddress","/membatch/apprAddressWithout","/membatch/apprlExtraflds","/membatch/rejExtraflds","/membatch/apprExtrafldsWithout"}, method = RequestMethod.GET)
    public ModelAndView approveMembers(HttpServletRequest req, HttpServletResponse res)
    {
        //String act = "APPR-BATCH";
        //String curEventShort = getBatchEvents().get(act);
        memberService.setUser(user); 
        
        BatchUploadFile theFile = null;
        StatusFlow statusFlow = null;
        Event curEvent =  null;
        
        theFile =  (BatchUploadFile)memberService.getBatchUploadFileDAO().getFileByIdComp(Integer.parseInt(req.getParameter("id")), user.getCurruser().getCompanyid(),user.getCurruser().getBranch());
        
        if(theFile!=null)
        {
            String page= customutil.getActualPage((String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));
            log.info("page ::: " + page);
           // String pageStart = page.substring(0, 3);
           // log.info("pageStart ::: " + pageStart);
            
            String curEventShort = getBatchEvents().get(page);
            
         
           //f(page.equalsIgnoreCase("apprMembers"))
           // {
           //   memberService.setPassRecord(true);
           //   theFile.setPostInfo("Y");
           // }
          //  else  if(page.equalsIgnoreCase("apprMembersWithout"))
           // {
            //     memberService.setPassRecord(true);
          //       theFile.setPostInfo("N");
           // }
          //  else  if(page.equalsIgnoreCase("rejMembers"))
          //  {
           //      memberService.setPassRecord(false);
          //  }
            
            if(page.startsWith("appr"))
            {
              memberService.setPassRecord(true);
            }
            else 
            {
             memberService.setPassRecord(false);
            }
           
            if(page.endsWith("Without"))
            {
                theFile.setPostInfo("N");
            }
            else
            {
                 theFile.setPostInfo("Y");
            }
                
        //memberService.setPassRecord(page.equals("apprMembers")?true:false);
        log.info("memberService.passRecord ::: " + memberService.isPassRecord());
        log.info("theFile.setPostInfo ::: " + theFile.getPostInfo()==null?"Not Set":theFile.getPostInfo());

            try
            {
                    curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);
                    statusFlow=     memberService.getStatusFlowDAO().getFlowFromEventStatus(curEvent, theFile.getStatus());
                    memberService.apprDetail(theFile, statusFlow, req);
            }
            catch(Exception ex)
            {
                 memberService.setMsg("Problems retrieving record " + ex.getMessage());
            }
        }
        else
        {
         memberService.setMsg("You have not accessed this page correctly. Please log out and retry");
        }
         
        return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + memberService.getMsg());
    }
    
    
    @ModelAttribute("referenceList")
	public Map<String, Object> populateList(HttpServletRequest request,HttpServletResponse response) 
	{
		//Data referencing for web framework checkboxes
		String companyRef = null;
		String countryRef = null;
		String branchRef = null;
	
		try {
			companyRef = user.getCurruser().getCompanyid().toString();
			countryRef = user.getCurrusercompany().getCountryId();
			branchRef = user.getCurruser().getBranch().toString();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		Map<String,Object> referenceList = new HashMap<String, Object>();
		
		log.info("The company : " + companyRef);
		//List<Map<String,Object>>  theKeys = getGenericConfigDAO().getProductsByType(member.getCompany().getId().toString(), member.getBranch().getId().toString(), prodType);
		referenceList.put("typeList", memberService.getBatchUploadTypeDAO().findByProperty("delFlg", "N"));
		             
		return referenceList;
	}
        
       
    @RequestMapping(value="/membatch/showTemplates")
    public ModelAndView showTemplates(@ModelAttribute("batchUploadFile") BatchUploadFile batchUploadFile, HttpServletRequest request)
    {
        
        Date today = new Date();
        batchUploadFile.setCreatedBy(user.getCurruser().getUserId());
        batchUploadFile.setCreatedDate(new Timestamp(today.getTime()));
        
        memberService.setUser(user);
        
        List<String[]> theFiles = memberService.showTemplates(batchUploadFile, request);
        
        log.info(memberService.getMsg());
        
        log.info("List Size " + theFiles.size() );
        
        //log.info(theFiles.get(0)[0]);
        //log.info(theFiles.get(1)[0]);
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("theFiles", theFiles);
        
        return new ModelAndView("/members/templates", model);
        
    }
    
    
    @RequestMapping(value="/membatch/getTemplates")
    public void getTemplates(@ModelAttribute("batchUploadFile") BatchUploadFile batchUploadFile, BindingResult result, HttpServletRequest req, HttpServletResponse response)
    {
       
          try {
              
              //Call service class
              
              Date today = new Date();
              batchUploadFile.setCreatedBy(user.getCurruser().getUserId());
              batchUploadFile.setCreatedDate(new Timestamp(today.getTime()));
              
              File file = new File(downLoadPath + req.getParameter("downFile").replace("/", "").replace("\\", ""));
              
              InputStream is =null;
              try {
                  is = new FileInputStream(file);
              } catch (FileNotFoundException ex) {
                  Logger.getLogger(MemberBatchController.class.getName()).log(Level.SEVERE, null, ex);
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
                  Logger.getLogger(MemberBatchController.class.getName()).log(Level.SEVERE, null, ex);
              }
              byte[] buffer = new byte[1024];
              int len;
              while ((len = is.read(buffer)) != -1) {
                  os.write(buffer, 0, len);
              }
              os.flush();
              os.close();
              is.close();
              
              // return new ModelAndView("/members/templates", "batchUploadFile", batchUploadFile);
              
          } catch (Throwable ex) {
              Logger.getLogger(MemberBatchController.class.getName()).log(Level.SEVERE, null, ex);
          }
        
    }

     public CurrentuserdisplayImpl getUser() {
		return user;
	}

    public void setUser(CurrentuserdisplayImpl user) {

             this.user.setdCurruser(user.getCurruser());
    }

    public Map<String, String> getBatchEvents() {
        return batchEvents;
    }

    public void setBatchEvents(Map<String, String> batchEvents) {
        this.batchEvents = batchEvents;
    }

    public String getStartPosBat() {
        return startPosBat;
    }

    public void setStartPosBat(String startPosBat) {
        this.startPosBat = startPosBat;
    }

    public BatchManagementImpl getMemberService() {
        return memberService;
    }

    public void setMemberService(BatchManagementImpl memberService) {
        this.memberService = memberService;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
        
        
        
    
}
