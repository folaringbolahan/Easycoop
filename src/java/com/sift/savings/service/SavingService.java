/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.service;

import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.FileMeta;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Company;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.Users;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.orm.PersistentException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author logzy
 */
public interface SavingService {
  
    public boolean uploadSavings(List<Savings> saving);
    public boolean withdraw(Savings saving, int period, int year, String timezone, Date postDate);
    public boolean uploadSingleTransaction(Savings saving, int period, int year, String timezone, Date postDate);
    public boolean uploadTransaction(Savings saving,FileUpload fileUpload);
    public boolean saveFileUpload(FileUpload fileUpload);
    public LinkedList<FileMeta> performBulkSave(MultipartHttpServletRequest request, Users user) throws PersistentException;
    public LinkedList<FileMeta> doBulkSave(MultipartHttpServletRequest request, Users user) throws PersistentException ;
    public String createAccount(int customerId, String productCode, int branchId, int companyId, String timezone);
    public String createSavingsAccount(Accnowbs account);
    public List<Member> getMembers(String name);
    public boolean postTransaction(Entrys entrys);
    public List<Custaccountdetails> getAccounts();
   // public void readSavingsUpload()throws PersistentException ;
    public List<SavingsError> loadSavingsError(Users user);
    public List<Savings> loadPendingSavings(Users user);
    public void updateSavingsStatus(String id);
    public void doBulkGlPosting(); 
    public boolean saveTransaction(Savings saving);
    public void testJoin();
    public List<FileUpload> listUnVerifiedUploads(int branchId, int companyId);
    public String verify(String referenceNumber, String userId) ;
    public String authorize(String id, String userId);
    public boolean checkIfAccountByBranchIsValid(String accountNumber, int branchId, int companyId);
    public List<Savings> loadSavingsByReferenceNumber(String referenceNumber);
    public List<SavingsError> loadSavingsErrorByReferenceNumber(String referenceNumber);
    public boolean getProductTypeByAccountNumber(String accountNumber, int branchId, int companyId);
    public boolean checkIfSavingsAccount(String accountNumber, int branchId, int companyId);
     public int getProductIdByAccountNumber(String accountNumber, int branchId);
     public String performBulkSave2(MultipartHttpServletRequest request, Users user) throws PersistentException;
     //
     public List<Savings> loadBatchForApprovalItems(int branchId, int companyId, String referenceNumber);
     public List<FileUpload> listBatchForApprovalItems(int branchId, int companyId, String referenceNumber);
     public List<FileUpload> listBatchForApproval(int branchId, int companyId);
     public boolean updateBatchStatus(int branchId, int companyId, String referenceNumber, String status, String tableName, String currentUser);
     public List<FileUpload> listFailedUploads(int branchId, int companyId);
     public void verifyBatchUpload(String referenceNumber, String userId) ;
}
