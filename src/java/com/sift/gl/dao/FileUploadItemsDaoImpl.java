/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.gl.bean.FileUploadBean;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.AccountsImp;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Custaccountdetails;
import com.sift.gl.model.FileUploadErrors;
import com.sift.gl.model.FileUploadImp;
import com.sift.gl.model.FileUploadItems;
import com.sift.gl.model.MemberImp;
import com.sift.gl.model.Products;
import com.sift.gl.model.SmsLog;
import com.sift.gl.service.FileUploadErrorsService;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.gl.service.MemberImpFileService;
import com.sift.gl.service.MemberImpService;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.Product;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.utility.MailBean;
import com.sift.savings.utility.HelperUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
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
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author Olakunle Awotunbo
 */
@Repository("fileUploadItemsDao")
//@Configurable
@Transactional
public class FileUploadItemsDaoImpl implements FileUploadItemsDao {

    private static WebResource webResource;
    private static Client client;
    private String theerrormess = "";
    private String DBASE_URI = "";
    private String output;
    private String dpwd;
    private String dname;

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private CurrentuserdisplayImpl user;
    @Autowired
    private BranchService branchService;
    @Autowired
    private MemberImpService memberImpService;
    @Autowired
    //@Qualifier("fileUploadItemsService1")
    private FileUploadItemsService fileUploadItemsService;
    @Autowired
    private FileUploadErrorsService fileUploadErrorsService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MemberImpFileService memberImpFileService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
private Configuration freemarkerConfiguration;
    private TaskExecutor taskExecutor;

    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void addBulkUploadItem(FileUploadItems item) {
        sessionFactory.getCurrentSession().saveOrUpdate(item);
        // System.out.println("File Uplaod Item Save : " + item);
    }

    @Override
    public void addBulkUploadItems(List<FileUploadItems> items) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listSuccessBulkUploadItems(String batchId, int branchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadItems.class);
        int isApproved = 0;
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("batchId", batchId));
        criteria.add(Restrictions.eq("isApproved", isApproved));
        criteria.addOrder(Order.asc("batchId"));

        return criteria.list();

    }

    @Override
    public List<FileUploadItems> listBulkUploadsForAuth(int companyId, int branchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadItems.class);
        int isApproved = 0;
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("isApproved", isApproved));
        //criteria.setProjection(Projections.distinct(Projections.property("batchId")));
        //criteria.setProjection(Projections.distinct("batchId"));
        //criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        //criteria.setMaxResults(10);
        //criteria.setFirstResult(20);

        return (List<FileUploadItems>) criteria.list();
    }

    @Override
    public boolean authorizeBatchUpload(int companyId, int branchId, String batchId, String approvedBy) {
        boolean success = false;
        try {
            this.sessionFactory.getCurrentSession().createQuery("update FileUploadItems set approvedBy='" + approvedBy + "', isApproved = 2 where companyId = '" + companyId + "' and branchId ='" + branchId + "' AND batchId = '" + batchId + "'").executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean authorizeSingleUpload(String id, String approvedBy) {

        boolean success = false;
        try {
            //this.sessionFactory.getCurrentSession().createQuery("update FileUploadItems set approvedBy='"+approvedBy + "', isApproved = 1 where id = '"+id + "'").executeUpdate();
            this.sessionFactory.getCurrentSession().createQuery("update FileUploadItems set approvedBy='" + approvedBy + "', isApproved = 2 where id in (" + id + ")").executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;

    }

    @Override
    public ArrayList<FileUploadBean> listSuccessBulkUploadItems2(String batchId, int branchId) {
        ArrayList<FileUploadBean> list = null;
        FileUploadBean fileUploadBean = null;

        String sql = "SELECT f.PRODUCT_ID as productId, f.BATCH_ID as batchId, f.UPLOADED_BY as uploadedBy ,"
                + "  p.name as productName  FROM "
                + "file_upload_items AS f, products AS p WHERE"
                + " f.PRODUCT_ID = p.id AND f.BATCH_ID =" + batchId + " AND f.BRANCH_ID= " + branchId + "  GROUP BY f.BATCH_ID ORDER BY f.UPLOADED_DATE ASC";
        //System.out.println("Query : " + sql);
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<FileUploadBean>();
            }
            fileUploadBean = new FileUploadBean();

            Object[] row = results.next();

            fileUploadBean.setProductId((Integer) row[i++]);
            fileUploadBean.setBatchId((String) row[i++]);
            fileUploadBean.setUploadedBy((String) row[i++]);
            fileUploadBean.setProductName((String) row[i++]);

            list.add(fileUploadBean);
        }

        return list;

    }

    @Override
    public ArrayList<FileUploadBean> listBulkUploadsForAuth2(int companyId, int branchId) {
        ArrayList<FileUploadBean> list = null;
        FileUploadBean fileUploadBean = null;
        int isApproved = 0;

        String sql = "SELECT f.PRODUCT_ID as productId, f.BATCH_ID as batchId, f.UPLOADED_BY as uploadedBy ,"
                + "  p.name as productName  FROM "
                + "file_upload_items AS f, products AS p WHERE"
                + " f.PRODUCT_ID = p.id AND f.COMPANY_ID =" + companyId + " AND f.BRANCH_ID= " + branchId + " AND f.IS_APPROVED= " + isApproved + " GROUP BY f.BATCH_ID ORDER BY f.UPLOADED_DATE ASC";
        //System.out.println("Query : " + sql);
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<FileUploadBean>();
            }
            fileUploadBean = new FileUploadBean();

            Object[] row = results.next();

            fileUploadBean.setProductId((Integer) row[i++]);
            fileUploadBean.setBatchId((String) row[i++]);
            fileUploadBean.setUploadedBy((String) row[i++]);
            fileUploadBean.setProductName((String) row[i++]);

            list.add(fileUploadBean);
        }

        return list;
    }

    @Override
    public List<FileUploadItems> getMembersNo(int companyId, int branchId, String batchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadItems.class);
        int isApproved = 2; //2 is for approve and 1 is reject 
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("batchId", batchId));
        criteria.add(Restrictions.eq("isApproved", isApproved));

        return (List<FileUploadItems>) criteria.list();
    }

    @Override
    public String getProductCode(int companyId, int branchId, String batchId) {
        String productCode = "";
        String sql = "SELECT DISTINCT "
                + "  p.code   FROM "
                + "file_upload_items AS f, products AS p WHERE"
                + " f.PRODUCT_ID = p.id AND f.COMPANY_ID =" + companyId + " AND f.BRANCH_ID= " + branchId + " AND f.BATCH_ID = '" + batchId + "' ";
        //System.out.println("Query : " + sql);
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        productCode = (String) query.list().get(0);

        return productCode;
    }

    @Override
    public boolean rejectBatchUpload(int companyId, int branchId, String batchId, String approvedBy) {
        boolean success = false;
        try {
            this.sessionFactory.getCurrentSession().createQuery("update FileUploadItems set approvedBy='" + approvedBy + "', isApproved = 1 where companyId = '" + companyId + "' and branchId ='" + branchId + "' AND batchId = '" + batchId + "'").executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean updateAccountNo(int companyId, int branchId, String batchId, String accountNo, String memberNo) {
        boolean success = false;
        try {
            this.sessionFactory.getCurrentSession().createQuery("update FileUploadItems set accountNo='" + accountNo + "' where isApproved = 2  AND companyId = '" + companyId + "' and branchId ='" + branchId + "' AND batchId = '" + batchId + "' AND memberNo = '" + memberNo + "'").executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public void writeAcctNoToExcel(String productName, List<Custaccountdetails> memAcctList, HttpServletResponse response) {

        String fileName = productName + ".xls";

        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            try {
                throw new Exception("invalid file name, should be xls or xlsx");
            } catch (Exception ex) {
                Logger.getLogger(FileUploadItemsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        Iterator<Custaccountdetails> iterator = memAcctList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        Cell headercell01 = headerrow.createCell(columnIndex++);
        headercell01.setCellValue("ACCOUNT NUMBER");
        headercell01.setCellStyle(style);

        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("ACCOUNT NAME");
        headercell0.setCellStyle(style);
        /*
         Cell headercell1 = headerrow.createCell(columnIndex++);
         headercell1.setCellValue("MEMBER NAME");
         headercell1.setCellStyle(style);
         */
        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("NARRATION");
        headercell2.setCellStyle(style);

        Cell headercell3 = headerrow.createCell(columnIndex++);
        headercell3.setCellValue("AMOUNT");
        headercell3.setCellStyle(style);

        Cell headercell4 = headerrow.createCell(columnIndex++);
        headercell4.setCellValue("TXN TYPE");
        headercell4.setCellStyle(style);

        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            Custaccountdetails obj = iterator.next();
            //iterator.next().getTitle();

            Row row = sheet.createRow(rowIndex++);

            Cell cell01 = row.createCell(columnIndex++);
            //cell01.setCellValue(obj.getTitle());
            cell01.setCellValue(obj.getAccountNo().trim());

            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getTitle().split("\\-", 2)[0].trim());  // if you're only interested in the first element.Split into 2

            /*
             Cell cell1 = row.createCell(columnIndex++);
             cell1.setCellValue(obj.getName());
             */
        }

        //lets write the excel data to file now
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this.getReportLocation() + fileName);
            workbook.write(fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUploadItemsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUploadItemsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        File f = new File(this.getReportLocation() + fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment; filename=" + fileName);
        try {
            //let browser to download
            FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(FileUploadItemsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        //return null;
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

    @Override
    public List<FileUploadItems> accountNoForExcel(int companyId, int branchId, int productId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadItems.class);
        int isApproved = 2; //2 is for approve and 1 is reject 
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("isApproved", isApproved));
        criteria.add(Restrictions.eq("productId", productId));

        return (List<FileUploadItems>) criteria.list();
    }

    @Override
    public String getUploadedBy(int companyId, int branchId, String batchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadItems.class);
        criteria.add(Restrictions.eq("companyId", companyId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("batchId", batchId));
        criteria.setProjection(Projections.property("uploadedBy")); // uploadedBy alone
        List result = criteria.list();

        String uploadedBy = "";
        for (Iterator it = result.iterator(); it.hasNext();) {
            //Object[] myResult = (Object[]) it.next();
            uploadedBy = String.valueOf(it.next());
        }
        return uploadedBy;
    }

    @Override
    public Products getProductsById(int branchId, int productId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Products.class);
        int isActive = 1;
        criteria.add(Restrictions.eq("id", productId));
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("isActive", isActive));

        return criteria.list().size() == 0 ? null : (Products) criteria.list().get(0);

    }

    @Override
    public boolean accountExist(int companyId, int branchId, String segmentCode, String memberNo, String branchCode) {

        int row = 0;

        String aseg3 = "01";
        String accountType = "M";
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(accountNo) from accounts where companyId='" + companyId + "'"
                + " and branch ='" + branchId + "' AND aseg1 ='" + segmentCode + "' AND aseg2 ='" + memberNo + "'"
                + " AND aseg3 ='" + aseg3 + "' AND aseg4 ='" + branchCode + "' AND accounttype ='" + accountType + "'");

        row = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
        return row > 0 ? true : false;

    }

    //@Async
    public /*Future<FileUploadBean>*/ FileUploadBean readExcelMemberData(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress,
                    FileUploadItemsService fileUploadItemsService, CompanyService companyService, EazyCoopUtility eazyCoopUTIL,
                    BranchService branchService, MemberImpService memberImpService, FileUploadErrorsService fileUploadErrorsService, CountryService countryService,
                    MemberImpFileService memberImpFileService) {
        System.out.println("Inside the read upload START :: 3");
        List<FileUploadBean> uploadedListAll = new ArrayList<FileUploadBean>();
        List<FileUploadBean> uploadedListSuccess = new ArrayList<FileUploadBean>();
        List<FileUploadBean> uploadedListFailed = new ArrayList<FileUploadBean>();
      
        String productTypeCode = fileUploadItemsService.getProductsById(branchId, productId).getProductTypeCode();
   
        String segmentCode = fileUploadItemsService.getProductsById(branchId, productId).getSegmentCode();

        Company coy = companyService.getCompany(companyId);
        //Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());

        FileUploadBean lrv = new FileUploadBean();
        FileUploadErrors fileUploadErrors = new FileUploadErrors();
        FileUploadImp fileUpload = new FileUploadImp();
        //Date localDate = eazyCoopUTIL.getTimeZoneGivenCountry(coy.getCountryId());
        Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());

        boolean SkipFirstRow = true;
        int rowCount = 0;
        Branch brnch = branchService.getBranch(branchId);

        try {
            //Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream(fileName);

            //create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;

            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            //Get the number of sheets in the xlsx file
            int numberOfSheets = workbook.getNumberOfSheets();
            numberOfSheets = 1;

            System.out.println("About to enter the for loop ");
            //loop through each of the sheets
            for (int i = 0; i < numberOfSheets; i++) {
                System.out.println("i :: " + i);
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);

                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                int COLUMN_INDEX = 0;

                //need to skip first row                
                while (rowIterator.hasNext()) {
                    //System.err.println("rowIterator.hasNext() :: " + rowIterator.hasNext());
                    String memberNoStr = "";
                    String memberName = "";
                    //Date uploadDate = null;

                    //String uploader = uploaderStr;
                    String uploadError = "N";
                    String validationMsg = "";

                    //Get the row object
                    Row row = rowIterator.next();

                    if (row.getRowNum() == 0) {
                        continue;
                        //just skip the rows if row number is 0 
                    }

                    rowCount += 1;

                    //Every row has columns, get the column iterator and iterate over them
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        if (COLUMN_INDEX == 2) {
                            COLUMN_INDEX = 0;
                        }
                        COLUMN_INDEX += 1;
                        //get the cell object
                        Cell cell = cellIterator.next();

                        if (COLUMN_INDEX == 1) {

                            try {
                                memberName = cell.getStringCellValue().trim();
                                //System.out.println("Member Name : " + memberName);
                                if (memberName == null) {
                                    //member does not exist
                                    validationMsg += "; Member Name is Empty";
                                } else {
                                    //System.out.println("memberNoStr:"+ memberNoStr);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                validationMsg += "; Member Name is Empty";
                            }

                        } else if (COLUMN_INDEX == 2) {
                            //2nd column Member Number

                            try {
                                // Column 1 is for member number
                                // memberNoStr = cell.getStringCellValue().trim();

                                memberNoStr = cell.getStringCellValue().trim();
                                //check of member exist
                                //System.out.println("Member No : " + memberNoStr);
                                boolean valid = memberImpService.memberExist(memberNoStr);
                                //System.out.println("Is member No valid:=" + valid);                                
                                if (!valid) {
                                    // Member No does not exist
                                    validationMsg += "; Invalid Member Number";
                                }
                                boolean acctExist = false;
                                // check if product is not of Loan type. So check if account exist for Savings, and contribution

                                if (!"L".equalsIgnoreCase(productTypeCode.trim())) {
                                    // this check is using the wrong parameters to check ; segment code instead of product code - more over its not in the place of file upload 1st stage to verify if account exists - product acc creation webservice does that at approval
                                    // so we comment out the next line
                                   // acctExist = fileUploadItemsService.accountExist(companyId, branchId, segmentCode, memberNoStr, brnch.getBranchCode());

                                }

                                if (acctExist) {
                                    // Member No does not exist
                                    validationMsg += "; Member already has an account for this product";
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                validationMsg += "; Invalid Member Number";
                            }

                        }
                        //may it here to do something

                    }//end of cell iterator
                    //call web service to create product account for member here              

                    if (validationMsg.length() > 0) {
                        uploadError = "Y";
                    }

                    FileUploadBean c = new FileUploadBean(memberNoStr, validationMsg);
                    uploadedListAll.add(c);

                    if ("N".equals(uploadError)) {
                        uploadedListSuccess.add(c);

                        //add to upload item table
                        // BulkFileUploadItems b = new BulkFileUploadItems();
                        FileUploadItems b = new FileUploadItems();
                        b.setMemberNo(memberNoStr);
                        b.setMemberName(memberName);
                        b.setCompanyId(companyId);
                        b.setBranchId(branchId);
                        b.setBatchId(batchId);
                        b.setUploadedBy(uploaderStr);
                        b.setProductId(productId);
                        b.setUploadedDate(localDate);

                        fileUploadItemsService.addBulkUploadItem(b);

                       // System.out.println("uploadedListSuccess : " + uploadedListSuccess.size());
                    } else if ("Y".equals(uploadError)) {
                        uploadedListFailed.add(c);

                        //fileUploadErrorsService.addFileUploadErrors(uploadedListFailed);
                        fileUploadErrors.setMemberNo(memberNoStr);
                        fileUploadErrors.setErrorMessage(validationMsg);
                        fileUploadErrors.setBatchId(batchId);
                        fileUploadErrors.setCompanyId(companyId);
                        fileUploadErrors.setBranchId(branchId);
                        fileUploadErrors.setProductId(productId);
                        fileUploadErrors.setMemberName(memberName);
                        fileUploadErrors.setUploadedDate(localDate);
                        fileUploadErrors.setUploadedBy(uploaderStr);
                        fileUploadErrorsService.addFileUploadError(fileUploadErrors);

                    }

                    uploadError = "N";
                    validationMsg = "";

                }//end of rows iterator          

                System.out.println("Row count : " + rowCount);
                System.out.println("uploadedListAll : " + uploadedListAll.size());
                //TimeUnit.SECONDS.sleep(10);
                //System.out.println("Thread is awake after 10sec");
            }//end of sheets for loop

            lrv.setUploadedListAll(uploadedListAll);
            lrv.setUploadedListSuccess(uploadedListSuccess);
            lrv.setUploadedListFailed(uploadedListFailed);
            //close file input stream
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        lrv.getTotalRecords();
        int totalRecords = lrv.getUploadedListAll() == null ? 0 : lrv.getUploadedListAll().size();
        int successRecords = lrv.getUploadedListSuccess() == null ? 0 : lrv.getUploadedListSuccess().size();
        int failedRecords = lrv.getUploadedListFailed() == null ? 0 : lrv.getUploadedListFailed().size();

        //System.out.println("In Controller");
        //System.out.println("totalRecords : " + totalRecords);
        //System.out.println("successRecords : " + successRecords);
        //System.out.println("failedRecords : " + failedRecords);

        //FileUploadImp fileUpload = new FileUploadImp();
        fileUpload.setTotalRecords(totalRecords);
        fileUpload.setStatus(successRecords);
        fileUpload.setFailedCount(failedRecords);
        //i want to update the record 
        // memberImpFileService.addBulkUpload(fileUpload);
        // send a mail notification for completion of read excel
        //System.out.println("In fileUpload :: " + fileUpload);

        System.out.println("Inside the read upload FINISH  :: 4");
        return lrv;

        //return new AsyncResult<FileUploadBean>(lrv);
    }

    @Override
    public int getLoanAccountsCountByMemberAndProduct(int companyId, int branchId, String segmentCode, String memberNo, String branchCode) {
        int row = 0;
        //String aseg3 = "01";
        String accountType = "M";
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(accountNo) from accounts where companyId='" + companyId + "'"
                + " and branch ='" + branchId + "' AND aseg1 ='" + segmentCode + "' AND aseg2 ='" + memberNo + "'"
                + " AND aseg4 ='" + branchCode + "' AND accounttype ='" + accountType + "'");

        row = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
        return row;

    }

    @Override
    public List<Custaccountdetails> productAccounts(int companyId, int branchId, String productId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Custaccountdetails.class);
        //String accounttype = "M";
        criteria.add(Restrictions.eq("companyid", companyId));
        criteria.add(Restrictions.eq("branchid", branchId));
        criteria.add(Restrictions.eq("product", productId));
        //criteria.setProjection(Projections.property("accountno")); 
        //criteria.setProjection(Projections.property("title")); 
        //criteria.add(Restrictions.eq("aseg4", segmentCode));
        //criteria.add(Restrictions.eq("accounttype", accounttype));

        return (List<Custaccountdetails>) criteria.list();
    }

    @Override
    public boolean addSmsLog(SmsLog smsLog) {
        boolean success = false;
        try {
            this.sessionFactory.getCurrentSession().saveOrUpdate(smsLog);
            //sessionFactory.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public List<SmsLog> listLogByCoop(int compId, int branchId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SmsLog.class);
        criteria.add(Restrictions.eq("branchId", branchId));
        criteria.add(Restrictions.eq("companyId", compId));

        return criteria.list();
    }

    public boolean addSmsLog(int eventId, String description, String action, String username, String mobile, String message, Date actionDate, String actionItem, String actionResult, int companyId, int branchId) {
        SmsLog smsLog = new SmsLog();

        smsLog.setEventId(eventId);
        smsLog.setDescription(description);
        smsLog.setAction(action);
        smsLog.setUsername(username);
        smsLog.setMobile(mobile);
        smsLog.setMessage(message);
        smsLog.setActionDate(actionDate);
        smsLog.setActionItem(actionItem);
        smsLog.setActionResult(actionResult);
        smsLog.setCompanyId(companyId);
        smsLog.setBranchId(branchId);

        boolean success = false;
        try {
            sessionFactory.getCurrentSession().save(smsLog);
            //sessionFactory.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean importLoan(LoanRequest loanRequest, ArrayList<LoanRepaymentSchedule> lrsList) {
        Session session = null;
        boolean success = false;
        try {
            session = sessionFactory.getCurrentSession();
            // save loan request
            session.saveOrUpdate(loanRequest);

            if (lrsList != null && lrsList.size() > 0) {
                for (LoanRepaymentSchedule item : lrsList) {
                    //save loan schedule
                    session.saveOrUpdate(item);
                }
            }

            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
    
    public Company getCompany(int id){  
        return (Company) sessionFactory.getCurrentSession().get(Company.class,id);  
    } 

    // for the task executor 
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @return the taskExec
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    @Override
    public boolean processBatchApproval(MemberImpFileService memberImpFileService, int dcompany, int dbranch, String batchId, String userId, String clientIPAddress, HelperUtility helperUTIL, String timezone, 
            FileUploadItemsService fileUploadItemsService, BranchService branchService, EazyCoopUtility eazyCoopUTIL) {
        boolean success = false;
        boolean mailSent = false;

        String productCode = fileUploadItemsService.getProductCode(dcompany, dbranch, batchId);
        int productId = memberImpFileService.getFileUploadByBatchIdAndBranchId(dbranch, batchId).getProductId();
        String segmentCode = fileUploadItemsService.getProductsById(dbranch, productId).getSegmentCode();
        // update fileupload table and set processed to P
        memberImpFileService.processBatchUpload(dcompany, dbranch, batchId, userId, "P");
        String descrip = "Batch ID : " + batchId + " Upload Approved  ";
        Activitylog activity = new Activitylog();

        //Audit action
        activity.setEvent(101);
        activity.setAction("BULK-ACCT-APPROVAL");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem(batchId);
        activity.setActionResult("Bulk account approval for:: " + batchId);
        activity.setDescription(descrip);
        activity.setIpaddress(clientIPAddress);
        activity.setUsername(userId);
        activity.setTimezone(timezone);
        activity.setToDate("");
        activity.setCompanyid(dcompany);
        activity.setBranchid(dbranch);

        eazyCoopUTIL.LogUserAction(activity);

        List<FileUploadItems> memNoList = fileUploadItemsService.getMembersNo(dcompany, dbranch, batchId);
        Branch brnch = branchService.getBranch(dbranch);
        //System.out.println("memNoList:= " + memNoList);
        int i = 0;
        //loop through memNos to creat account
        if (memNoList != null && memNoList.size() > 0) {
            System.out.println("I have passed it to the Async method for processing");

            for (FileUploadItems item : memNoList) {
                i++;
                System.out.println("Creating account for Record :: " + i);

                String countStr = helperUTIL.getActiveLoanProductsCount(item.getMemberNo(), productCode, Integer.toString(dbranch), Integer.toString(dcompany));
                int countValue = 1 + Integer.parseInt(countStr);
                String counterStr = String.valueOf(countValue);

                if (countStr.length() < 2) {
                    counterStr = "0" + counterStr;
                }

                //System.out.println("Next loan acctSeg3 at bulk account := " + countStr);
                Accnowbs accnowbs = new Accnowbs();

                // Subno will be 01 for savings or contribution and for loan, it will be incremented by 1 based on the last no
                accnowbs.setBranchcode(brnch.getBranchCode());
                accnowbs.setBranchId(dbranch);
                accnowbs.setCompany(dcompany);
                accnowbs.setCompanycode(eazyCoopUTIL.deriveCompanyCode(String.valueOf(dcompany)));
                accnowbs.setProductcode(productCode);
                accnowbs.setSubno(counterStr);
                accnowbs.setCustomerno(item.getMemberNo());
                accnowbs.setTimezone(timezone);

                String accountNo = null;
                boolean created;
                try {
                    accountNo = eazyCoopUTIL.createProductAccount(accnowbs);

                    if (accountNo.indexOf("OK.") != - 1) {
                        created = true;
                        accountNo = accountNo.trim().toUpperCase().replace("OK.", "");

                        if (accountNo != null) {
                            accountNo = accountNo.trim();
                            //System.out.println("Account No @ Chris : " + accountNo);

                        }

                        // Save generated accountNo
                        fileUploadItemsService.updateAccountNo(dcompany, dbranch, batchId, accountNo, item.getMemberNo());
                        // call activitylog
                        String descrip2 = "Account Created : " + accountNo;
                        //Audit action
                        activity.setEvent(103);
                        activity.setAction("PROD-ACCT-CREATION-SUCCESS");
                        activity.setActionDate(new java.util.Date());
                        activity.setActionItem(batchId);
                        activity.setActionResult("Bulk account approval for:: " + batchId);
                        activity.setDescription(descrip2);
                        activity.setIpaddress(clientIPAddress);
                        activity.setUsername(userId);
                        activity.setTimezone(timezone);
                        activity.setToDate("");
                        activity.setCompanyid(dcompany);
                        activity.setBranchid(dbranch);

                        eazyCoopUTIL.LogUserAction(activity);

                    }
                } catch (Exception ex) {
                    String descrip4 = "Prodcut Account creation for member ID : " + item.getMemberNo() + " failed";
                    //Audit action
                    activity.setEvent(104);
                    activity.setAction("PROD-ACCT-CREATION-FAILED");
                    activity.setActionDate(new java.util.Date());
                    activity.setActionItem(batchId);
                    activity.setActionResult("Bulk account approval failed for :: " + batchId);
                    activity.setDescription(descrip4);
                    activity.setIpaddress(clientIPAddress);
                    activity.setUsername(userId);
                    activity.setTimezone(timezone);
                    activity.setToDate("");
                    activity.setCompanyid(dcompany);
                    activity.setBranchid(dbranch);

                    eazyCoopUTIL.LogUserAction(activity);

                    ex.printStackTrace();
                }

            }
            // send mail on completion of bulk account creation 
           System.out.println("I should send mail to the uploader ");
            /*
            try {
                //mail bean setup
                MailBean MB = eazyCoopUTIL.getMailConfig();
                //MB.setSubject(Definitions.MAIL_SUBJECT_USER_PASSWORD_RESET);
                MB.setSubject("Account creation for Batch Id " + batchId + " Completed.");
                MB.setToemail(userId);

                String uri = "";

                try {
                    uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                String mailBody = "";
                String template = "changeLogonPass.ftl";
                Map model = new HashMap();
                //model.put("getUsername", profile.getUsername());
               // model.put("getEmail", user.getEmail());
                model.put("uri", uri);

                try {
                    mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                } catch (TemplateNotFoundException tpex) {
                    System.out.println("Error - missing email template :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (MalformedTemplateNameException tpex) {
                    System.out.println("Error - email template name :" + tpex.getMessage());
                    tpex.printStackTrace();
                } catch (TemplateException tpex) {
                    System.out.println("Error - email template :" + tpex.getMessage());
                    tpex.printStackTrace();

                } catch (IOException tpex) {
                    System.out.println("Error - email template IO :" + tpex.getMessage());
                    tpex.printStackTrace();
                }

                MB.setMailBody(mailBody);

                //send email
                //eazyCoopUTIL.sendMail(MB);
                eazyCoopUTIL.setTaskExecutor(taskExecutor);
                eazyCoopUTIL.sendMailasync(MB);
                mailSent = true;
                success = true;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            // mail sending end 
            */
            success = true;
        }
        return success;
    }

    public class readExcelTask implements Runnable {

        // private MailBean MB;
        private String fileName;
        private String uploaderStr;
        private String batchId;
        private int companyId;
        private int branchId;
        private int productId;
        private String baseName;
        private String serverFileName;
        private String clientIPAddress;
        private FileUploadItemsService fileUploadItemsServicex;
        private CompanyService companyServicex;
        private EazyCoopUtility eazyCoopUTILx;
        private BranchService branchServicex;
        private MemberImpService memberImpServicex;
        private FileUploadErrorsService fileUploadErrorsServicex;
        private CountryService countryServicex;
        private MemberImpFileService memberImpFileServicex;

        public readExcelTask(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress,
                FileUploadItemsService fileUploadItemsService, CompanyService companyService, EazyCoopUtility eazyCoopUTIL,
                BranchService branchService, MemberImpService memberImpService, FileUploadErrorsService fileUploadErrorsService,
                CountryService countryService, MemberImpFileService memberImpFileService) {
            this.fileName = fileName;
            this.uploaderStr = uploaderStr;
            this.batchId = batchId;
            this.companyId = companyId;
            this.branchId = branchId;
            this.productId = productId;
            this.baseName = baseName;
            this.serverFileName = baseName;
            this.clientIPAddress = clientIPAddress;
            this.fileUploadItemsServicex = fileUploadItemsService;
            this.companyServicex = companyService;
            this.eazyCoopUTILx = eazyCoopUTIL;
            this.branchServicex = branchService;
            this.memberImpServicex = memberImpService;
            this.fileUploadErrorsServicex = fileUploadErrorsService;
            this.countryServicex = countryService;
            this.memberImpFileServicex = memberImpFileService;

        }

        public void run() {
            //sendMail(MB);
            readExcelMemberData(fileName, uploaderStr, batchId, companyId, branchId, productId, baseName, serverFileName, clientIPAddress,
                    fileUploadItemsServicex, companyServicex, eazyCoopUTILx, branchServicex, memberImpServicex, fileUploadErrorsServicex,
                    countryServicex, memberImpFileServicex);
        }
    }

    public void readExcel(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress,
            FileUploadItemsService fileUploadItemsServicex, CompanyService companyServicex, EazyCoopUtility eazyCoopUTILx,
            BranchService branchServicex, MemberImpService memberImpServicex, FileUploadErrorsService fileUploadErrorsServicex, CountryService countryServicex,
            MemberImpFileService memberImpFileServicex) {
        taskExecutor.execute(
                new readExcelTask(fileName, uploaderStr, batchId, companyId, branchId, productId, baseName, serverFileName, clientIPAddress,
                        fileUploadItemsServicex, companyServicex, eazyCoopUTILx, branchServicex, memberImpServicex, fileUploadErrorsServicex, countryServicex,
                        memberImpFileServicex));

    }

    // task for approval
    public class acctApprovalTask implements Runnable {

        // private MailBean MB;
        private MemberImpFileService memberImpFileServicex;
        private int dcompanyx;
        private int dbranchx;
        private String batchIdx;
        private String userIdx;
        private String clientIPAddressx;
        private HelperUtility helperUtilx;
        private String timezonex;
        private FileUploadItemsService fileUploadItemsServicex;
        private BranchService branchServicex;
        private EazyCoopUtility eazyCoopUTILx;

        public acctApprovalTask(MemberImpFileService memberImpFileService, int dcompany, int dbranch,
                String batchId, String userId, String clientIPAddress, HelperUtility helperUtil,
                String timezone, FileUploadItemsService fileUploadItemsService, BranchService branchService,
                EazyCoopUtility eazyCoopUTIL) {
            this.memberImpFileServicex = memberImpFileService;
            this.dcompanyx = dcompany;
            this.dbranchx = dbranch;
            this.batchIdx = batchId;
            this.userIdx = userId;
            this.clientIPAddressx = clientIPAddress;
            this.helperUtilx = helperUtil;
            this.timezonex = timezone;
            this.fileUploadItemsServicex = fileUploadItemsService;
            this.branchServicex = branchService;
            this.eazyCoopUTILx = eazyCoopUTIL;

        }

        public void run() {

            processBatchApproval(memberImpFileServicex, dcompanyx, dbranchx, batchIdx, userIdx, clientIPAddressx,
                    helperUtilx, timezonex, fileUploadItemsServicex, branchServicex, eazyCoopUTILx);
        }
    }

    public void processBatchAppr(MemberImpFileService memberImpFileServicex, int dcompanyx, int dbranchx,
            String batchIdx, String userIdx, String clientIPAddressx, HelperUtility helperUtilx,
            String timezonex, FileUploadItemsService fileUploadItemsServicex, BranchService branchServicex,
            EazyCoopUtility eazyCoopUTILx) {

        taskExecutor.execute(new acctApprovalTask(memberImpFileServicex, dcompanyx, dbranchx,
                batchIdx, userIdx, clientIPAddressx, helperUtilx,
                timezonex, fileUploadItemsServicex, branchServicex,
                eazyCoopUTILx));
    }

    // task executor ends
    public String getProductCode(int branchId, int productId) {
        return fileUploadItemsService.getProductsById(branchId, productId).getProductTypeCode();
    }
}
