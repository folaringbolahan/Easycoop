/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.service;

import com.sift.admin.model.Company;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.gl.bean.FileUploadBean;
import com.sift.gl.dao.FileUploadItemsDao;
import com.sift.gl.model.AccountsImp;
import com.sift.gl.model.Custaccountdetails;
import com.sift.gl.model.FileUploadItems;
import com.sift.gl.model.Products;
import com.sift.gl.model.SmsLog;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.Product;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("fileUploadItemsService")
//@Component
@Transactional
public class FileUploadItemsServiceImpl implements FileUploadItemsService {

    @Autowired
    private FileUploadItemsDao fileUploadItemsDao;
    private TaskExecutor taskExecutor;

    @Override
    public void addBulkUploadItem(FileUploadItems item) {
        fileUploadItemsDao.addBulkUploadItem(item);
    }

    @Override
    public void addBulkUploadItems(List<FileUploadItems> items) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listBulkUploadItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listBulkUploadItems(String companyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listPendingBulkUploadItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listPendingBulkUploadItems(String batchId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listFailedBulkUploadItems(String batchId, int branchId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listSuccessBulkUploadItems(String batchId, int branchId) {
        return fileUploadItemsDao.listSuccessBulkUploadItems(batchId, branchId);
    }

    @Override
    public List<FileUploadItems> listFailedBulkUploadItems(String batchId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listSuccessBulkUploadItems(String batchId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileUploadItems getBulkUploadItem(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBulkUploadItem(FileUploadItems item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<FileUploadItems> listBulkUploadsForAuth(int companyId, int branchId) {
        return fileUploadItemsDao.listBulkUploadsForAuth(companyId, branchId);
    }

    @Override
    public boolean authorizeBatchUpload(int companyId, int branchId, String batchId, String approvedBy) {
        return fileUploadItemsDao.authorizeBatchUpload(companyId, branchId, batchId, approvedBy);
    }

    @Override
    public boolean authorizeSingleUpload(String id, String approvedBy) {
        return fileUploadItemsDao.authorizeSingleUpload(id, approvedBy);
    }

    @Override
    public ArrayList<FileUploadBean> listSuccessBulkUploadItems2(String batchId, int branchId) {
        return fileUploadItemsDao.listSuccessBulkUploadItems2(batchId, branchId);
    }

    @Override
    public ArrayList<FileUploadBean> listBulkUploadsForAuth2(int companyId, int branchId) {
        return fileUploadItemsDao.listBulkUploadsForAuth2(companyId, branchId);
    }

    @Override
    public List<FileUploadItems> getMembersNo(int companyId, int branchId, String batchId) {
        return fileUploadItemsDao.getMembersNo(companyId, branchId, batchId);
    }

    @Override
    public String getProductCode(int companyId, int branchId, String batchId) {
        return fileUploadItemsDao.getProductCode(companyId, branchId, batchId);
    }

    @Override
    public boolean rejectBatchUpload(int companyId, int branchId, String batchId, String approvedBy) {
        return fileUploadItemsDao.rejectBatchUpload(companyId, branchId, batchId, approvedBy);
    }

    @Override
    public boolean updateAccountNo(int companyId, int branchId, String batchId, String accountNo, String memberNo) {
        return fileUploadItemsDao.updateAccountNo(companyId, branchId, batchId, accountNo, memberNo);
    }

    @Override
    public void writeAcctNoToExcel(String productName, List<Custaccountdetails> memAcctList, HttpServletResponse response) {
        fileUploadItemsDao.writeAcctNoToExcel(productName, memAcctList, response);
    }

    @Override
    public List<FileUploadItems> accountNoForExcel(int companyId, int branchId, int productId) {
        return fileUploadItemsDao.accountNoForExcel(companyId, branchId, productId);
    }

    @Override
    public String getUploadedBy(int companyId, int branchId, String batchId) {
        return fileUploadItemsDao.getUploadedBy(companyId, branchId, batchId);
    }

    @Override
    public Products getProductsById(int branchId, int productId) {
        return fileUploadItemsDao.getProductsById(branchId, productId);
    }

    @Override
    public boolean accountExist(int companyId, int branchId, String segmentCode, String memberNo, String branchCode) {
        return fileUploadItemsDao.accountExist(companyId, branchId, segmentCode, memberNo, branchCode);
    }

    @Override

    public FileUploadBean readExcelMemberData(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress,
            FileUploadItemsService fileUploadItemsService, CompanyService companyService, EazyCoopUtility eazyCoopUTIL,
            BranchService branchService, MemberImpService memberImpService, FileUploadErrorsService fileUploadErrorsService,
            CountryService countryService, MemberImpFileService memberImpFileService) {
        return fileUploadItemsDao.readExcelMemberData(fileName, uploaderStr, batchId, companyId, branchId, productId, baseName, serverFileName, clientIPAddress,
                fileUploadItemsService, companyService, eazyCoopUTIL, branchService, memberImpService, fileUploadErrorsService,
                countryService, memberImpFileService);
    }

    @Override
    public int getLoanAccountsCountByMemberAndProduct(int companyId, int branchId, String segmentCode, String memberNo, String branchCode) {
        return fileUploadItemsDao.getLoanAccountsCountByMemberAndProduct(companyId, branchId, segmentCode, memberNo, branchCode);
    }

    @Override
    public List<Custaccountdetails> productAccounts(int companyId, int branchId, String productId) {
        return fileUploadItemsDao.productAccounts(companyId, branchId, productId);
    }

    /*
     @Override    
     //@Async
     public void createBatchAccount(List<FileUploadItems> memNoList, String productCode, int dbranch, int dcompany, String timezone, String batchId, String clientIPAddress, String userId) {
     //Thread.sleep(1000L);
     fileUploadItemsDao.createBatchAccount(memNoList, productCode, dbranch, dcompany, timezone, batchId, clientIPAddress, userId);
     }
     */
    @Override
    public boolean addSmsLog(SmsLog smsLog) {
        return fileUploadItemsDao.addSmsLog(smsLog);
    }

    @Override
    public List<SmsLog> listLogByCoop(int compId, int branchId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addSmsLog(int eventId, String description, String action, String username, String mobile, String message, Date actionDate, String actionItem, String actionResult, int companyId, int branchId) {
        return fileUploadItemsDao.addSmsLog(eventId, description, action, username, mobile, message, actionDate, actionItem, actionResult, companyId, branchId);
    }

    @Override
    public boolean importLoan(LoanRequest loanRequest, ArrayList<LoanRepaymentSchedule> lrsList) {
        return fileUploadItemsDao.importLoan(loanRequest, lrsList);
    }
    
    public Company getCompany(int id){
	  return fileUploadItemsDao.getCompany(id);
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
    public boolean processBatchApproval(MemberImpFileService memberImpFileService, int dcompany, int dbranch, String batchId, String userId, String clientIPAddress, HelperUtility helperUtil, String timezone, FileUploadItemsService fileUploadItemsService, BranchService branchService, EazyCoopUtility eazyCoopUTIL) {
        return fileUploadItemsDao.processBatchApproval(memberImpFileService, dcompany, dbranch, batchId, userId, clientIPAddress, helperUtil, timezone, fileUploadItemsService, branchService, eazyCoopUTIL);
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

        public readExcelTask(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress) {
            this.fileName = fileName;
            this.uploaderStr = uploaderStr;
            this.batchId = batchId;
            this.companyId = companyId;
            this.branchId = branchId;
            this.productId = productId;
            this.baseName = baseName;
            this.serverFileName = baseName;
            this.clientIPAddress = clientIPAddress;
        }

  

        // for account creation approval end
        public void run() {
            //sendMail(MB);
            //    readExcelMemberData(fileName,uploaderStr, batchId,  companyId, branchId,  productId, baseName, serverFileName,  clientIPAddress);
        }
    }

    public void readExcel(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress) {
        taskExecutor.execute(
                new readExcelTask(fileName, uploaderStr, batchId, companyId, branchId, productId, baseName, serverFileName, clientIPAddress));
    }
    
   
}
