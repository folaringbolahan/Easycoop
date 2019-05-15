package com.sift.loan.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.model.Company;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.MemberViewService;
import com.sift.admin.service.UserService;
import com.sift.loan.service.BulkUploadItemService;
import com.sift.loan.service.BulkUploadService;
import com.sift.loan.service.LoanPayOffUploadItemService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.Product;
import com.sift.loan.bean.FileUploadBean;
import com.sift.loan.bean.ProductBean;
import com.sift.loan.service.BulkUploadErrorService;
import com.sift.loan.service.LoanRepaymentScheduleService;
import com.sift.loan.service.LoanRequestService;
import freemarker.template.Configuration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;

/**
 * @author XTOFFEL CONSULT
 */
@Controller
public class BulkUploadController {

    private static final Logger logger = LoggerFactory
            .getLogger(BulkUploadController.class);
    @Autowired
    private CountryService countryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
    private LoanRequestService loanRequestService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BulkUploadService bulkUploadService;
    @Autowired
    private BulkUploadItemService bulkUploadItemService;
    @Autowired
    private BulkUploadErrorService bulkUploadErrorService;
    @Autowired
    private Configuration freemarkerConfiguration;
    @Autowired
    private LoanPayOffUploadItemService loanPayOffUploadItemService;
    @Autowired
    private LoanRepaymentScheduleService loanRepaymentScheduleService;
    @Autowired
    private UserService userService;
    @Autowired
    private MemberViewService memberService;
    @Autowired
    private BranchService branchService;   
    BeanMapperUtility beanMapper = new BeanMapperUtility();
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    @Value("${DOWNLOAD_PATH}")
    private String downLoadPath;

    @RequestMapping(value = "/saveBulkUpload", method = RequestMethod.POST)
    public ModelAndView saveBulkUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        bulkUploadService.addBulkUpload(fileUpload);
        return new ModelAndView("redirect:/newBulkUpload.htm");
    }

    @RequestMapping(value = "/processBulkRepayment", method = RequestMethod.POST)
    public ModelAndView processBulkRepayment(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        bulkUploadService.markBatchForPayment(fileUpload);
        return new ModelAndView("redirect:/doFeedback.htm?message=The specified batch has been scheduled for payment&redirectURI=listBulkUploads.htm");
    }

    @RequestMapping(value = "/enterBulkPay", method = RequestMethod.POST)
    public ModelAndView enterBulkPay(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        bulkUploadService.enterBatchUpload(fileUpload);
        return new ModelAndView("redirect:/doFeedback.htm?message=The specified batch has been moved to ENTER Status. Now awaiting Approval.&redirectURI=listBulkUploads.htm");
    }

    @RequestMapping(value = "/verifyBulkPay", method = RequestMethod.POST)
    public ModelAndView verifyBulkPay(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        fileUpload.setVerifiedBy(req.getRemoteUser());

        bulkUploadService.enterBatchUpload(fileUpload);
        return new ModelAndView("redirect:/doFeedback.htm?message=The specified batch has been moved to ENTER Status. Now awaiting Approval.&redirectURI=listBulkUploads.htm");
    }

    @RequestMapping(value = "/cancelBulkPay", method = RequestMethod.POST)
    public ModelAndView cancelBulkPay(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        fileUpload.setApprovedBy(req.getRemoteUser());

        bulkUploadService.cancelBatchUpload(fileUpload);
        return new ModelAndView("redirect:/doFeedback.htm?message=The specified batch was successfully cancelled.&redirectURI=listBulkUploads.htm");
    }

    @RequestMapping(value = "/authorizeBulkPay", method = RequestMethod.POST)
    public ModelAndView authorizeBulkPay(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        fileUpload.setApprovedBy(req.getRemoteUser());

        bulkUploadService.authorizeBatchUpload(fileUpload);

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);
        eazyCoopUTIL.setTaskExecutor(taskExecutor);
        //eazyCoopUTIL.doBulkRepaymentasync(currentUser.getCompanyId(), currentUser.getBranchId());
        eazyCoopUTIL.doBulkRepaymentasync(currentUser.getCompanyId(), currentUser.getBranchId(),
                bulkUploadService, helperUTIL, loanRequestService, 
                    productService, loanRepaymentScheduleService, bulkUploadItemService, loanPayOffUploadItemService,
                    companyService, branchService);
        return new ModelAndView("redirect:/doFeedback.htm?message=The specified batch was successfully authorized for payment.&redirectURI=listBulkUploads.htm");
    }

    @RequestMapping(value = "/rejectBulkPay", method = RequestMethod.POST)
    public ModelAndView rejectBulkPay(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        bulkUploadService.rejectBatchUpload(fileUpload);
        return new ModelAndView("redirect:/doFeedback.htm?message=The specified batch was successfully cancelled.&redirectURI=listBulkUploads.htm");
    }

    @RequestMapping(value = "/processBulkUploadRepayment", method = RequestMethod.GET)
    public ModelAndView processBulkUploadRepayment(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result, HttpServletRequest req) {
        FileUpload fileUpload = prepareModel(fileUploadBean);
        bulkUploadService.markBatchForPayment(fileUpload);
        return new ModelAndView("redirect:/doFeedback.htm?message=The specified batch has been scheduled for payment&redirectURI=listBulkUploads.htm");
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFileHandler(HttpServletRequest req, @RequestParam("uploadType") String uploadType, @RequestParam("uploadedBy") String uploadedBy,
            @RequestParam("userUploadSum") Double userUploadSum, @RequestParam("userUploadcount") Integer userUploadcount,
            @RequestParam("userUploadFine") Double userUploadFine, @RequestParam("fileData") MultipartFile file) {

        String batchid = null;

        if (!file.isEmpty()) {
            batchid = String.valueOf(System.currentTimeMillis());

            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FilenameUtils fileUTIL = new FilenameUtils();

                String ext = fileUTIL.getExtension(file.getOriginalFilename());
                String baseName = fileUTIL.getBaseName(file.getOriginalFilename());
                String serverFileName = req.getRealPath("/uploads") + File.separator + baseName + "_" + batchid + "." + ext;
                System.out.println("serverFileName:" + serverFileName);

                //create the file on server
                File serverFile = new File(serverFileName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
                String logonUser = eazyCoopUTIL.getLoggedonUser(req);

                User currentUser = userService.getUserByEmail(logonUser);

                Company coy = companyService.getCompany(Integer.parseInt(currentUser.getCompanyId()));
                Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());

                FileUpload fileUpload = new FileUpload();

                fileUpload.setUploadFilename(serverFileName);
                fileUpload.setUploadDate(localDate);
                fileUpload.setUploadStatus("U");
                fileUpload.setBranchId(currentUser.getBranchId());
                fileUpload.setCompanyId(currentUser.getCompanyId());
                fileUpload.setBatchId(batchid);
                fileUpload.setUploadedBy(req.getRemoteUser());
                fileUpload.setPaymentStatus("N");
                fileUpload.setProcessingStatus("N");
                fileUpload.setUploadType(uploadType);

                fileUpload.setUserUploadSum(userUploadSum);
                fileUpload.setUserUploadcount(userUploadcount);
                fileUpload.setUserUploadFine(userUploadFine);

                fileUpload.setTotalRecords(0);
                fileUpload.setFailedCount(0);
                fileUpload.setSuccessCount(0);

                fileUpload.setUploadSum(0.0);
                fileUpload.setSuccessSum(0.0);
                fileUpload.setFailedSum(0.0);

                fileUpload.setPenaltySum(0.0);

                //not currently used
                fileUpload.setInterestSum(0.0);
                fileUpload.setPrincipalSum(0.0);

                bulkUploadService.addBulkUpload(fileUpload);

                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.readExcelDataasync(currentUser.getCompanyId(), currentUser.getBranchId(), bulkUploadService, helperUTIL,
                        memberService, loanRequestService, productService, loanRepaymentScheduleService, bulkUploadItemService,
                        bulkUploadErrorService, freemarkerConfiguration);

                return new ModelAndView("redirect:/doFeedback.htm?message=Loan PayOff/Repayment Schedule has been uploaded and is awaiting processing. The assigned Upload Batch Id is : <b>" + batchid + "  </b>&redirectURI=newBulkRepaymentUpload.htm");
            } catch (Exception e) {
                e.printStackTrace();
                return new ModelAndView("redirect:/doError.htm?message=No Valid file was specified&redirectURI=newBulkRepaymentUpload.htm");
            }
        } else {
            return new ModelAndView("redirect:/doError.htm?message=Loan Repayment Schedule has been uploaded and is awaiting processing. The assigned Upload Batch Idis : <b>" + batchid + "  </b>&redirectURI=newBulkRepaymentUpload.htm");
        }
        //return new ModelAndView("redirect:/newBulkRepaymentUpload.htm");
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ModelAndView download(@RequestParam("filename") String fname) {
        ModelAndView mav = new ModelAndView("download");
        mav.addObject("filename", fname);

        return mav;
    }

    @RequestMapping(value = "/listBulkUploads", method = RequestMethod.GET)
    public ModelAndView listBulkUploads(HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("reportFileLocation", eazyCoopUTIL.getReportLocation());
        model.put("bulkuploads", prepareListofBean(bulkUploadService.listBulkUploads(currentUser.getCompanyId(), currentUser.getBranchId())));
        return new ModelAndView("bulkUploadList", model);
    }

    @RequestMapping(value = "/listBulkUploadsForAuth", method = RequestMethod.GET)
    public ModelAndView listBulkUploadsForAuth(HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("bulkuploads", prepareListofBean(bulkUploadService.listBulkUploadsForAuth(currentUser.getCompanyId(), currentUser.getBranchId(), "E")));
        model.put("logonUser", logonUser);

        return new ModelAndView("bulkUploadListForAuth", model);
    }

    @RequestMapping(value = "/listBulkUploadsForVerify", method = RequestMethod.GET)
    public ModelAndView listBulkUploadsForVerify(HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("bulkuploads", prepareListofBean(bulkUploadService.listBulkUploadsForAuth(currentUser.getCompanyId(), currentUser.getBranchId(), "U")));
        model.put("logonUser", logonUser);

        return new ModelAndView("bulkUploadListForVerify", model);
    }

    @RequestMapping(value = "/newBulkRepaymentUpload", method = RequestMethod.GET)
    public ModelAndView addBulkUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        return new ModelAndView("newBulkRepaymentUpload", model);
    }

    @RequestMapping(value = "/newLoanPayOffUpload", method = RequestMethod.GET)
    public ModelAndView addLoanPayOffUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        return new ModelAndView("loanPayOffUpload", model);
    }

    @RequestMapping(value = "/deleteBulkUpload", method = RequestMethod.GET)
    public ModelAndView deleteBulkUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result) {
        bulkUploadService.deleteBulkUpload(prepareModel(fileUploadBean));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("reportFileLocation", eazyCoopUTIL.getReportLocation());
        model.put("bulkupload", null);
        model.put("bulkuploads", prepareListofBean(bulkUploadService.listBulkUploads()));
        return new ModelAndView("addBulkUpload", model);
    }

    @RequestMapping(value = "/editBulkUpload", method = RequestMethod.GET)
    public ModelAndView editBulkUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("reportFileLocation", eazyCoopUTIL.getReportLocation());
        model.put("bulkupload", prepareBulkUploadBean(bulkUploadService.getBulkUpload(fileUploadBean.getId())));
        model.put("bulkuploads", prepareListofBean(bulkUploadService.listBulkUploads()));
        return new ModelAndView("editBulkUpload", model);
    }

    @RequestMapping(value = "/viewBulkUpload", method = RequestMethod.GET)
    public ModelAndView viewBulkUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("bulkupload", prepareBulkUploadBean(bulkUploadService.getBulkUpload(fileUploadBean.getId())));
        model.put("bulkuploads", prepareListofBean(bulkUploadService.listBulkUploads()));
        model.put("reportFileLocation", eazyCoopUTIL.getReportLocation());

        return new ModelAndView("viewBulkUpload", model);
    }

    @RequestMapping(value = "/verifyBulkUpload", method = RequestMethod.GET)
    public ModelAndView verifyBulkUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("reportFileLocation", eazyCoopUTIL.getReportLocation());
        model.put("bulkupload", prepareBulkUploadBean(bulkUploadService.getBulkUpload(fileUploadBean.getId())));
        model.put("bulkuploads", prepareListofBean(bulkUploadService.listBulkUploads()));
        return new ModelAndView("verifyBulkUpload", model);
    }

    @RequestMapping(value = "/approveBulkUpload", method = RequestMethod.GET)
    public ModelAndView approveBulkUpload(@ModelAttribute("bulkupload") FileUploadBean fileUploadBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        FileUpload up = bulkUploadService.getBulkUpload(fileUploadBean.getId());
        model.put("reportFileLocation", eazyCoopUTIL.getReportLocation());
        model.put("bulkupload", prepareBulkUploadBean(up));

        if ("LP".equalsIgnoreCase(up.getUploadType())) {
            model.put("bulkuploaditems", bulkUploadItemService.listSuccessBulkUploadItems(up.getBatchId()));
        } else if ("LR".equalsIgnoreCase(up.getUploadType())) {
            model.put("bulkuploaditems", loanPayOffUploadItemService.listSuccessLoanPayOffUploadItems(up.getBatchId()));
        }

        return new ModelAndView("approveBulkUpload", model);
    }
    
    // download saving/contribution template
    @RequestMapping(value = "/loanrpytuploadteml", method = RequestMethod.GET)    
    public void loanRpytUploadTeml(HttpServletRequest req, HttpServletResponse response) throws Exception {
       
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private FileUpload prepareModel(FileUploadBean fileUploadBean) {
        FileUpload fileUplload = new FileUpload();

        fileUplload.setCompanyId(fileUploadBean.getCompanyId());
        fileUplload.setId(fileUploadBean.getId());
        fileUplload.setBranchId(fileUploadBean.getBranchId());
        fileUplload.setProcessedDate(fileUploadBean.getProcessedDate());
        fileUplload.setUploadDate(fileUploadBean.getUploadDate());
        fileUplload.setUploadedBy(fileUploadBean.getUploadedBy());
        fileUplload.setUploadType(fileUploadBean.getUploadType());
        fileUplload.setUploadFilename(fileUploadBean.getUploadFilename());
        fileUplload.setUploadStatus(fileUploadBean.getUploadStatus());
        fileUplload.setBatchId(fileUploadBean.getBatchId());
        fileUplload.setPaymentStatus(fileUploadBean.getPaymentStatus());
        fileUplload.setProcessingStatus(fileUploadBean.getProcessingStatus());

        fileUplload.setTotalRecords(fileUploadBean.getTotalRecords());
        fileUplload.setSuccessCount(fileUploadBean.getSuccessCount());
        fileUplload.setFailedCount(fileUploadBean.getFailedCount());

        fileUplload.setUploadSum(fileUploadBean.getUploadSum());
        fileUplload.setFailedSum(fileUploadBean.getFailedSum());
        fileUplload.setSuccessSum(fileUploadBean.getSuccessSum());

        fileUplload.setUserUploadcount(fileUploadBean.getUserUploadcount());
        fileUplload.setUserUploadFine(fileUploadBean.getUserUploadFine());
        fileUplload.setUserUploadSum(fileUploadBean.getUserUploadSum());

        return fileUplload;
    }

    private List<FileUploadBean> prepareListofBean(List<FileUpload> fileUploads) {
        List<FileUploadBean> beans = null;

        if (fileUploads != null && !fileUploads.isEmpty()) {
            beans = new ArrayList<FileUploadBean>();
            FileUploadBean bean = null;

            for (FileUpload item : fileUploads) {
                bean = new FileUploadBean();

                bean.setCompanyId(item.getCompanyId());
                bean.setId(item.getId());
                bean.setBranchId(item.getBranchId());
                bean.setProcessedDate(item.getProcessedDate());
                bean.setUploadDate(item.getUploadDate());
                bean.setUploadedBy(item.getUploadedBy());
                bean.setUploadType(item.getUploadType());
                bean.setUploadFilename(item.getUploadFilename());
                bean.setUploadStatus(item.getUploadStatus());
                bean.setBatchId(item.getBatchId());
                bean.setPaymentStatus(item.getPaymentStatus());
                bean.setProcessingStatus(item.getProcessingStatus());

                bean.setTotalRecords(item.getTotalRecords());
                bean.setSuccessCount(item.getSuccessCount());
                bean.setFailedCount(item.getFailedCount());

                bean.setUploadSum(item.getUploadSum());
                bean.setFailedSum(item.getFailedSum());
                bean.setSuccessSum(item.getSuccessSum());

                bean.setUserUploadcount(item.getUserUploadcount());
                bean.setUserUploadFine(item.getUserUploadFine());
                bean.setUserUploadSum(item.getUserUploadSum());

                bean.setUploadFileShortName(eazyCoopUTIL.getFileNameFromPath(item.getUploadFilename()));
                String uploadStatusDesc = "";

                if ("U".equals(item.getUploadStatus())) {
                    uploadStatusDesc = "UPLOADED";
                } else if ("V".equals(item.getUploadStatus())) {
                    uploadStatusDesc = "VERIFIED";
                } else if ("A".equals(item.getUploadStatus())) {
                    uploadStatusDesc = "APPROVED";
                } else if ("R".equals(item.getUploadStatus())) {
                    uploadStatusDesc = "REJECTED";
                }

                bean.setUploadStatusDesc(uploadStatusDesc);

                String paymentStatusDesc = "";

                if ("P".equals(item.getPaymentStatus())) {
                    paymentStatusDesc = "PAID";
                } else if ("N".equals(item.getPaymentStatus())) {
                    paymentStatusDesc = "PENDING";
                }

                bean.setPaymentStatusDesc(paymentStatusDesc);


                String processingStatusDesc = "";

                if ("E".equals(item.getProcessingStatus())) {
                    processingStatusDesc = "ENTERED";
                } else if ("C".equals(item.getProcessingStatus())) {
                    processingStatusDesc = "CANCELLED";
                } else if ("N".equals(item.getProcessingStatus())) {
                    processingStatusDesc = "PENDING";
                } else if ("P".equals(item.getProcessingStatus())) {
                    processingStatusDesc = "PAID";
                }

                bean.setProcessingStatusDesc(processingStatusDesc);

                beans.add(bean);
            }
        }

        return beans;
    }

    private FileUploadBean prepareBulkUploadBean(FileUpload item) {
        FileUploadBean bean = new FileUploadBean();
        bean = new FileUploadBean();

        bean.setCompanyId(item.getCompanyId());
        bean.setId(item.getId());
        bean.setBranchId(item.getBranchId());
        bean.setProcessedDate(item.getProcessedDate());
        bean.setUploadDate(item.getUploadDate());
        bean.setUploadedBy(item.getUploadedBy());
        bean.setUploadType(item.getUploadType());
        bean.setUploadFilename(item.getUploadFilename());
        bean.setUploadStatus(item.getUploadStatus());
        bean.setBatchId(item.getBatchId());
        bean.setPaymentStatus(item.getPaymentStatus());
        bean.setProcessingStatus(item.getProcessingStatus());

        bean.setTotalRecords(item.getTotalRecords());
        bean.setSuccessCount(item.getSuccessCount());
        bean.setFailedCount(item.getFailedCount());

        bean.setUploadSum(item.getUploadSum());
        bean.setFailedSum(item.getFailedSum());
        bean.setSuccessSum(item.getSuccessSum());

        bean.setUserUploadcount(item.getUserUploadcount());
        bean.setUserUploadFine(item.getUserUploadFine());
        bean.setUserUploadSum(item.getUserUploadSum());

        bean.setUploadFileShortName(eazyCoopUTIL.getFileNameFromPath(item.getUploadFilename()));
        String uploadStatusDesc = "";

        if ("U".equals(item.getUploadStatus())) {
            uploadStatusDesc = "UPLOADED";
        } else if ("V".equals(item.getUploadStatus())) {
            uploadStatusDesc = "VERIFIED";
        } else if ("A".equals(item.getUploadStatus())) {
            uploadStatusDesc = "APPROVED";
        } else if ("R".equals(item.getUploadStatus())) {
            uploadStatusDesc = "REJECTED";
        }

        bean.setUploadStatusDesc(uploadStatusDesc);

        String paymentStatusDesc = "";

        if ("P".equals(item.getPaymentStatus())) {
            paymentStatusDesc = "PAID";
        } else if ("N".equals(item.getPaymentStatus())) {
            paymentStatusDesc = "PENDING";
        }

        bean.setPaymentStatusDesc(paymentStatusDesc);


        String processingStatusDesc = "";

        if ("E".equals(item.getProcessingStatus())) {
            processingStatusDesc = "ENTERED";
        } else if ("C".equals(item.getProcessingStatus())) {
            processingStatusDesc = "CANCELLED";
        } else if ("N".equals(item.getProcessingStatus())) {
            processingStatusDesc = "PENDING";
        } else if ("P".equals(item.getProcessingStatus())) {
            processingStatusDesc = "PAID";
        }

        bean.setProcessingStatusDesc(processingStatusDesc);

        return bean;
    }
    /**
     * ***
     *
     * -- File Upload (Upload User) -- File Count Validation -1 (Record Count &
     * Sum) / BatchId generation -- Processing - Error List & Success List
     * (status P) -- Records Validation (Validation User) - success list only.
     * for validated status becomes - E/Rejected status becomes R --
     * Authorization
     *
     ****
     */
}