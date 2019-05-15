/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.dao;

import com.sift.admin.model.Company;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.gl.bean.FileUploadBean;
import com.sift.gl.model.AccountsImp;
import com.sift.gl.model.Custaccountdetails;
import com.sift.gl.model.FileUploadItems;
import com.sift.gl.model.Products;
import com.sift.gl.model.SmsLog;
import com.sift.gl.service.FileUploadErrorsService;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.gl.service.MemberImpFileService;
import com.sift.gl.service.MemberImpService;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface FileUploadItemsDao {
        public void addBulkUploadItem(FileUploadItems item);
	public void addBulkUploadItems(List<FileUploadItems> items);
	public List<FileUploadItems> listSuccessBulkUploadItems(String batchId,int branchId);        	
        public ArrayList<FileUploadBean> listSuccessBulkUploadItems2(String batchId,int branchId);
        public List<FileUploadItems> listBulkUploadsForAuth(int companyId, int branchId);
        public ArrayList<FileUploadBean> listBulkUploadsForAuth2(int companyId, int branchId);
        public boolean authorizeBatchUpload(int companyId, int branchId, String batchId, String approvedBy);       
        public boolean rejectBatchUpload(int companyId, int branchId, String batchId, String approvedBy);
        public boolean authorizeSingleUpload(String id, String approvedBy);	
        public List<FileUploadItems> getMembersNo(int companyId, int branchId, String batchId);
        public String getProductCode(int companyId, int branchId, String batchId);
        public boolean updateAccountNo(int companyId, int branchId, String batchId, String accountNo, String memberNo);
        public void writeAcctNoToExcel(String productName, List<Custaccountdetails> memAcctList ,  HttpServletResponse response);
        public List<FileUploadItems> accountNoForExcel(int companyId, int branchId, int productId);
        public String getUploadedBy(int companyId, int branchId, String batchId);
        public Products getProductsById(int branchId, int productId);
        public boolean accountExist(int companyId, int branchId, String segmentCode, String memberNo, String branchCode);
        //public Future<FileUploadBean> readExcelMemberData(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress);
        public FileUploadBean readExcelMemberData(String fileName, String uploaderStr, String batchId, int companyId, int branchId, int productId, String baseName, String serverFileName, String clientIPAddress,
                FileUploadItemsService fileUploadItemsService, CompanyService companyService, EazyCoopUtility eazyCoopUTIL,
            BranchService branchService, MemberImpService memberImpService, FileUploadErrorsService fileUploadErrorsService,
             CountryService  countryService, MemberImpFileService memberImpFileService);
        public int getLoanAccountsCountByMemberAndProduct(int companyId, int branchId, String segmentCode, String memberNo, String branchCode);
        public List<Custaccountdetails> productAccounts(int companyId, int branchId, String productId);
        /*public void createBatchAccount(List<FileUploadItems> memNoList, String productCode, int dbranch, int dcompany, String timezone,
            String batchId, String clientIPAddress, String userId);
        */
        public boolean processBatchApproval(MemberImpFileService memberImpFileService, int dcompany, int dbranch, String batchId, String userId, String clientIPAddress,
                HelperUtility helperUtil, String timezone, FileUploadItemsService fileUploadItemsService, BranchService branchService, EazyCoopUtility eazyCoopUTIL);
        public boolean addSmsLog(SmsLog smsLog);  
        public boolean addSmsLog(int eventId, String description, String action, String username, String mobile, String message, Date actionDate ,
                String actionItem, String actionResult, int companyId, int branchId);  
        public List<SmsLog> listLogByCoop(int compId, int branchId);
        //public int countSMSByCoopAndBranch(int compId, int brancId);
       
        // for importing old loan
        public boolean importLoan(LoanRequest loanRequest, ArrayList<LoanRepaymentSchedule> lrsList);
        public Company getCompany(int id); 
}
