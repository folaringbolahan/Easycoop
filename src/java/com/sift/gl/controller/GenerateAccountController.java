/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;

import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.UserService;
import com.sift.gl.bean.FileUploadBean;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.dao.FileUploadItemsDaoImpl;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.AccountsImp;
import com.sift.gl.model.Custaccountdetails;
import com.sift.gl.model.FileUploadErrors;
import com.sift.gl.model.FileUploadImp;
import com.sift.gl.model.FileUploadItems;
import com.sift.gl.model.MemberImp;
import com.sift.gl.model.Products;
import com.sift.gl.service.FileUploadErrorsService;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.gl.service.FileUploadItemsServiceImpl;
import com.sift.gl.service.MemberImpFileService;
import com.sift.gl.service.MemberImpService;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.savings.service.SavingService;
import com.sift.savings.utility.HelperUtil;
import com.sift.webservice.service.LoanGuarantorWSService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Olakunle Awotunbo
 */
@Controller
@RequestMapping("gl/")
public class GenerateAccountController {

    @Autowired
    private CurrentuserdisplayImpl user;
    @Autowired
    private MemberImpService memberImpService;
    @Autowired
    private MemberImpFileService memberImpFileService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private SavingService savingService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
    private FileUploadItemsService fileUploadItemsService;
    @Autowired
    private FileUploadErrorsService fileUploadErrorsService;
    @Autowired
    public LoanGuarantorWSService loanGuarantorService;
    @Autowired
    ApplicationContext appContext;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    @Value("${DOWNLOAD_PATH}")
    private String downLoadPath;
    //@Autowired
    //FileUploadItemsDaoImpl forTask;

    String clientIPAddress;
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    private static final Logger logger = LoggerFactory.getLogger(GenerateAccountController.class);

    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser());
    }

    @RequestMapping(value = {"gl/gl_pendingacctlist", "/gl_pendingacctlist"}, method = RequestMethod.GET)
    public ModelAndView getViewMembersRecord(HttpServletRequest req, ModelMap map) {

        logger.info("In Approve Members Record Table");
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();

        List<FileUploadImp> penVeriList = memberImpFileService.listPendingMemberAcctList(dcompany, dbranch);
        //map.addAttribute("fileUpload", new FileUploadBean());
        map.addAttribute("penVeriList", penVeriList);

        logger.info("No. of Active Products: " + penVeriList.size());

        return new ModelAndView("gl/gl_pendingacctlist", map);
    }
    
    @RequestMapping(value = {"gl/gl_listBulkUploadsForAuth", "/gl_listBulkUploadsForAuth"}, method = RequestMethod.GET)
    public ModelAndView listBulkUploadsForAuth() {
        Map<String, Object> model = new HashMap<String, Object>();
       
        model.put("bulkuploads", fileUploadItemsService.listBulkUploadsForAuth2(user.getCurruser().getCompanyid(), user.getCurruser().getBranch()));

        model.put("logonUser", user.getCurruser().getUserId());

        return new ModelAndView("gl/gl_bulkUploadListForAuth", model);
    }

    @RequestMapping(value = "/gl_viewfailedrecords/{batchId}", method = RequestMethod.GET)
    public ModelAndView viewFailedRecords(@PathVariable("batchId") String batchId, ModelMap map) {

        List<FileUploadErrors> errorList = fileUploadErrorsService.listFileUploadErrors(user.getCurruser().getCompanyid(), user.getCurruser().getBranch(), batchId);
        map.addAttribute("errorList", errorList);
        map.addAttribute("logonUser", user.getCurruser().getUserId());
        map.addAttribute("dBatchId", batchId);
        return new ModelAndView("gl/gl_viewfailedrecords", map);
    }

    @RequestMapping(value = {"gl/gl_downloadFailedRecords/{batchId}", "/gl_downloadFailedRecords/{batchId}"}, method = RequestMethod.POST)
    public ModelAndView downloadFailedRecordsToExcel(@PathVariable("batchId") String batchId, HttpServletRequest req, HttpServletResponse response) {

        //System.out.println("Batch ID : " + batchId);
        //String batchId = req.getParameter("batchId") == null ? "0" : req.getParameter("batchId");
        logger.info("Downloading Error List for BatchID : " + batchId);
        //System.out.println("ProductID : " + productId);
        List<FileUploadErrors> errorList = fileUploadErrorsService.listFileUploadErrors(user.getCurruser().getCompanyid(), user.getCurruser().getBranch(), batchId);
        fileUploadErrorsService.writeErrorToExcel(errorList, response);
        return new ModelAndView("redirect:/gl/gl_listBulkUploadsForAuth");
        
    }

    
    @RequestMapping(value = {"gl/gl_approveBulkAcct/{batchId}", "/gl_approveBulkAcct/{batchId}"}, method = RequestMethod.GET)
    public ModelAndView approveBulkAcct(@PathVariable("batchId") String batchId, @ModelAttribute("approveAcct") FileUploadItems approveAcct, ModelMap map) {

        List<FileUploadItems> singapprv = fileUploadItemsService.listSuccessBulkUploadItems(batchId, user.getCurruser().getBranch());
        String uploadedBy = fileUploadItemsService.getUploadedBy(user.getCurruser().getCompanyid(), user.getCurruser().getBranch(), batchId);

        map.addAttribute("singapprv", singapprv);
        map.addAttribute("logonUser", user.getCurruser().getUserId());
        map.addAttribute("dBatchId", batchId);
        map.addAttribute("uploadedBy", uploadedBy);

        return new ModelAndView("gl/gl_listAcctForApproval", map);
    }

    @RequestMapping(value = {"gl/gl_downloadTemplate", "/gl_downloadTemplate"}, method = RequestMethod.GET)
    public ModelAndView downloadAccountNo(ModelMap map) {
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();

        List<Products> products = memberImpService.listActiveProductsByBranch(dcompany, dbranch);
        map.addAttribute("fileUpload", new FileUploadBean());
        map.addAttribute("products", products);

        logger.info("No. of Active Products: " + products.size());

        return new ModelAndView("gl/gl_downloadtemplate", map);
    }

    @RequestMapping(value = "/gl_downloadexcelTemplate", method = RequestMethod.POST)
    public ModelAndView downloadAccountNoForExcel(ModelMap map, HttpServletRequest req, HttpServletResponse response) {
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        String productId = req.getParameter("productId") == null ? "0" : req.getParameter("productId");
        logger.info("Downloading Excel Template for Product : " + productId);
        //System.out.println("ProductID : " + productId);        
        String productCode = fileUploadItemsService.getProductsById(dbranch, Integer.parseInt(productId)).getCode();
        String productName = fileUploadItemsService.getProductsById(dbranch, Integer.parseInt(productId)).getName();
        //System.out.println("productCode : " + productCode);
        List<Custaccountdetails> listMembersAcct = fileUploadItemsService.productAccounts(dcompany, dbranch, productCode);
        //System.out.println("listMembersAcct : " + listMembersAcct);
        fileUploadItemsService.writeAcctNoToExcel(productName, listMembersAcct, response);
        return new ModelAndView("redirect:/gl/gl_downloadTemplate");
    }

    @RequestMapping(value = {"gl/gl_approveByBatch/{batchId}", "/gl_approveByBatch/{batchId}"}, method = RequestMethod.POST)
    public ModelAndView approveByBatch(@PathVariable("batchId") String batchId, ModelMap map, HttpServletRequest request, HttpServletResponse response) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        String userId = user.getCurruser().getUserId();
        String timezone = user.getCurrusercompany().getTimezone();
        int customerId;

        String productCode = fileUploadItemsService.getProductCode(dcompany, dbranch, batchId);
        int productId = memberImpFileService.getFileUploadByBatchIdAndBranchId(dbranch, batchId).getProductId();
        String segmentCode = fileUploadItemsService.getProductsById(dbranch, productId).getSegmentCode();

        clientIPAddress = request.getRemoteAddr();

        boolean isSuccess = false;
        isSuccess = fileUploadItemsService.authorizeBatchUpload(dcompany, dbranch, batchId, userId);

        if (isSuccess) {
            
             System.out.println("About to call the read upload  :: 1");
              
               FileUploadItemsDaoImpl forTask = new FileUploadItemsDaoImpl();
                forTask.setTaskExecutor(taskExecutor);
              
                forTask.processBatchAppr(memberImpFileService, dcompany, dbranch, batchId, userId, clientIPAddress, helperUTIL, timezone,
                        fileUploadItemsService, branchService, eazyCoopUTIL);
                        
        } else {
            // Authorization not successful
        }

        //return new ModelAndView("redirect:/gl/gl_listBulkUploadsForAuth");
      //  return new ModelAndView("redirect:/gl/gl_pendingacctlist");
       return new ModelAndView("redirect:/doFeedback.htm?message=Batch:: (" + batchId + ") Approval in progress&redirectURI=gl/gl_pendingacctlist.htm");
   }

    @RequestMapping(value = {"gl/gl_rejectByBatch/{batchId}", "/gl_rejectByBatch/{batchId}"}, method = RequestMethod.POST)
    public ModelAndView rejectByBatch(@PathVariable("batchId") String batchId, ModelMap map, HttpServletRequest req) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        String userId = user.getCurruser().getUserId();
        clientIPAddress = req.getRemoteAddr();

        boolean isSuccess = false;
        isSuccess = fileUploadItemsService.rejectBatchUpload(dcompany, dbranch, batchId, userId);
        if (isSuccess) {
            // update fileupload table and set processed to P
            memberImpFileService.processBatchUpload(dcompany, dbranch, batchId, userId, "P");
            String descrip = "Batch ID : " + batchId + " Upload Rejected  ";
            HelperUtil.logEvent(102, "BULK-ACCT-REJECT", clientIPAddress, userId, user.getCurrusercompany().getTimezone(), batchId, descrip, dbranch, dcompany);

        }
        //return new ModelAndView("redirect:/gl/gl_listBulkUploadsForAuth");
        //return new ModelAndView("redirect:/gl/gl_pendingacctlist");
        return new ModelAndView("redirect:/doFeedback.htm?message=Batch " + batchId + " rejection in progress&redirectURI=gl/gl_pendingacctlist.htm");
    }
    
    @RequestMapping(value = {"gl/gl_uploadmem", "/gl_uploadmem"}, method = RequestMethod.GET)
    public ModelAndView getUploadForm(HttpServletRequest req, ModelMap map) {
        logger.info("In UploadForm");
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();

        List<Products> products = memberImpService.listActiveProductsByBranch(dcompany, dbranch);
        map.addAttribute("fileUpload", new FileUploadBean());
        map.addAttribute("products", products);

        logger.info("No. of Active Products: " + products.size());

        return new ModelAndView("gl/gl_impmemcrtacct", map);
    }

    @RequestMapping(value = "/gl_uploadForAcctCrt", method = RequestMethod.POST)
    public ModelAndView accountsuploadfile(HttpServletRequest req, @RequestParam("fileData") MultipartFile file,
            @ModelAttribute(value = "fileUpload") FileUploadImp fileUpload) {

        String batchid = null;
        clientIPAddress = req.getRemoteAddr();

        if (!file.isEmpty()) {
            //batchid = "OT-" + String.valueOf(System.currentTimeMillis());
            batchid = String.valueOf(System.currentTimeMillis());
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
                if ((ext.equalsIgnoreCase("xlsx") == false) && (ext.equalsIgnoreCase("xls") == false)) {
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

                int isVeri = 0;
                //FileUploadImp fileUpload = new FileUploadImp();
                int dbranch = user.getCurruser().getBranch();
                int dcompany = user.getCurruser().getCompanyid();
                String duser = user.getCurruser().getUserId();
                Company coy = companyService.getCompany(dcompany);
                Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());

                // save uploaded file here
                fileUpload.setFileName(baseName);

                fileUpload.setProcessedDate(localDate);
                //fileUpload.setUploadStatus("E");
                fileUpload.setBranchId(dbranch);
                fileUpload.setCompanyId(dcompany);
                fileUpload.setProductId(fileUpload.getProductId());
                fileUpload.setReferenceNumber(batchid);
                fileUpload.setUserId(duser);
                fileUpload.setLocation(serverFileName);
                 //fileUpload.setFileSum(fileUpload.getFileSum());
                //fileUpload.setTotalRecords(totalRecords);
                //fileUpload.setFailedCount(failedRecords);
                //fileUpload.setSuccessCount(successRecords);
                fileUpload.setUserUploadcount(fileUpload.getUserUploadcount());
                //fileUpload.setIsVerified(0);
                fileUpload.setToCreateAcct(1);
                fileUpload.setProcessingStatus("E");


                boolean good = memberImpFileService.addBulkUpload(fileUpload);
                System.out.println(" Is Good :: " + good);
                if (good) {
                    System.out.println(" Since its Good, update log:: " + good);
                    HelperUtil.logEvent(100, "BULK-ACCT-UPLOLAD", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), batchid, "Upload Successful", dbranch, dcompany);
                    //System.out.println("Audit Log inserted success");
                } else {
                    System.out.println(" Since its Not good, update log failed :: " + good);
                    HelperUtil.logEvent(100, "BULK-ACCT-UPLOLAD", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), batchid, "Upload Failed", dbranch, dcompany);

                }

                //read excel file 
                //FileUploadBean valObj = readExcelMemberData(serverFileName, duser, batchid, dcompany, dbranch, fileUpload.getProductId());
                System.out.println("About to call the read upload  :: 1");
              
               FileUploadItemsDaoImpl forTask = new FileUploadItemsDaoImpl();
                forTask.setTaskExecutor(taskExecutor);
              
                forTask.readExcel(serverFileName, duser, batchid, dcompany, dbranch, fileUpload.getProductId(), baseName, serverFileName, clientIPAddress,
                        fileUploadItemsService,companyService,eazyCoopUTIL,branchService,memberImpService,fileUploadErrorsService, countryService,
                        memberImpFileService);
                System.out.println("After call the read upload  :: 2");

                //return new ModelAndView("redirect:/gl/gl_uploadmem.htm?message=Upload awaiting validation. The assigned Upload Batch Id is : " + batchid + "&bid=" + batchid);
                //return new ModelAndView("redirect:/doFeedback.htm?message=Total Records specified by user : " + fileUpload.getUserUploadcount()/*fileUpload.getTotalRecords()*/ + " &Total record by System :" + totalRecords + " .&redirect:gl/gl_uploadmem.htm");
                return new ModelAndView("redirect:/doFeedback.htm?message=Batch :: " + batchid + " Upload awaiting validation&redirectURI=gl/gl_uploadmem.htm");
            } catch (Exception e) {
                e.printStackTrace();
                return new ModelAndView("redirect:/error.htm?er=No Valid file was specified");
            }
        } else {
            return new ModelAndView("redirect:/error.htm?er=Attempting to upload empty file. Select valid file");
        }
    }
    

    // download member list 
    @RequestMapping(value = {"gl/gl_downloadMember2", "/gl_downloadMember2"}, method = RequestMethod.GET)
    //@ResponseBody
    public Object downloadMember2(HttpServletRequest req, HttpServletResponse response) throws Exception {
        String fileName = "MEMBER LIST.xls";
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();

        String duser = user.getCurruser().getUserId();

        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Member List");

        sheet.setDefaultColumnWidth(30);
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        //List<MemberImp> errList = memberImpService.listMembersByBranch(Integer.parseInt(currentUser.getCompanyId()), 
        //       Integer.parseInt(currentUser.getBranchId()));
        List<MemberImp> memList = memberImpService.listMembersByBranch(dcompany,
                dbranch);

        Iterator<MemberImp> iterator = memList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        /*
        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("MEMBER NAME");
        headercell0.setCellStyle(style);

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("MEMBER NO");
        headercell1.setCellStyle(style);
        
        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("MEMBER NO");
        headercell2.setCellStyle(style);
        */
        
        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("OLD MEMBER NO");
        headercell0.setCellStyle(style);

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("MEMBER NAME");
        headercell1.setCellStyle(style);
        
        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("NEW MEMBER NO");
        headercell2.setCellStyle(style);
        
        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            MemberImp obj = iterator.next();
            Row row = sheet.createRow(rowIndex++);
            
            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getMemberCompId());
            
            Cell cell1 = row.createCell(columnIndex++);
            cell1.setCellValue(obj.getSurname() + " " + obj.getFirstName() + " " + obj.getMiddleName());

            Cell cell2 = row.createCell(columnIndex++);           
            cell2.setCellValue(obj.getMemberNo());
        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(this.getReportLocation() + fileName);
        workbook.write(fos);

        File f = new File(this.getReportLocation() + fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment; filename=" + fileName);
        //let browser to download
        FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
        fos.close();

        return null;
    }

    public void writeAllMembersListToFile(String fileName, int companyId, int branchId) throws Exception {
        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Member List");

        sheet.setDefaultColumnWidth(30);
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        List<MemberImp> errList = memberImpService.listMembersByBranch(companyId, branchId);

        Iterator<MemberImp> iterator = errList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("MEMBER NAME");
        headercell0.setCellStyle(style);

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("MEMBER NO");
        headercell1.setCellStyle(style);

        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            MemberImp obj = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getSurname() + " " + obj.getFirstName() + " " + obj.getMiddleName());

            Cell cell1 = row.createCell(columnIndex++);
            cell1.setCellValue(obj.getMemberNo());

        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(this.getReportLocation() + fileName);
        workbook.write(fos);
        fos.close();
        //System.out.println(fileName + " written successfully");
    }

    public String getReportLocation() {
        String reportlocation = "";

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            reportlocation = (String) ctx.lookup("java:comp/env/uploadreports");
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        return reportlocation;
    }
    
    
    @RequestMapping(value = "/downloadgltempl", method = RequestMethod.GET)    
    public void downloadgltempl(HttpServletRequest req, HttpServletResponse response) throws Exception {
       
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
