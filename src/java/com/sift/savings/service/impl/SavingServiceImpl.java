/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.service.impl;

import com.sift.easycoopfin.models.Branch;
import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.CustaccountdetailsCriteria;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileMeta;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsCriteria;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.easycoopfin.models.SavingsErrorCriteria;
import com.sift.easycoopfin.models.TxnCode;
import com.sift.easycoopfin.models.TxnCodeCriteria;
import com.sift.easycoopfin.models.impl.SavingsDAOImpl;
import com.sift.gl.GendataService;
import com.sift.gl.NewSerialno;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Account;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.Txnsheader;
import com.sift.gl.model.Users;
import com.sift.loan.utility.MailBean;
import com.sift.savings.service.SavingService;
import com.sift.savings.utility.EasyCoopFinValidator;
import com.sift.savings.utility.HelperUtil;
import com.sift.savings.utility.SavingsDefinitions;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
//import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.apache.commons.io.FilenameUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.orm.*;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author logzy
 */
@Service
public class SavingServiceImpl implements SavingService {

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingServiceImpl.class);
    private DAOFactory daoFactory;
    private static WebResource webResource;
    private static Client client;
    private String theerrormess = "";
    private String DBASE_URI = "";
    private String dpwd;
    private String dname;
    private String uploadUri;
    PersistentTransaction t = null;
    HelperUtil util = new HelperUtil();

    @Override
    public boolean uploadSavings(List<Savings> saving) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean uploadSingleTransaction(Savings savings, int period, int year, String timezone, Date postDate) {
        boolean status = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String referenceNumber = String.valueOf(System.currentTimeMillis());
        String controlAccount = null;
        savings.setReferenceNumber(referenceNumber);
        savings.setTrxType("S");
        Date trx_date = null;
        String serial = "";
        Date entrydate = null;
        String accountCode = "SRC";
        //controlEntry.
        try {
            Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(savings.getProductId());
            entrydate = new Date();
            ProductAccountCriteria accountCriteria = new ProductAccountCriteria();
            accountCriteria.add(Restrictions.eq("productId", savings.getProductId()));
            accountCriteria.add(Restrictions.eq("productAccountTypeCode", accountCode));

            TxnCodeCriteria txnCriteria = new TxnCodeCriteria();
            txnCriteria.add(Restrictions.eq("transactionCode", "CAD"));

            TxnCode txnCode = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getTxnCodeDAO().loadTxnCodeByCriteria(txnCriteria);
            ProductAccount account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().loadProductAccountByCriteria(accountCriteria);
            String narrative = String.format(txnCode.getNarrative(), savings.getAccountNumber(), period, year);
            if (account == null) {
                System.out.println("This account does not have an account attached to it");
            } else {
                String reformattedStr = myFormat.format(fromUser.parse(savings.getStringDate()));
                trx_date = myFormat.parse(reformattedStr);
                savings.setTrxDate(trx_date);
                LinkedList<Entry> entryList;
                entryList = new LinkedList<Entry>();
                NewSerialno nvSerial = new NewSerialno();
                serial = nvSerial.returnSerialnostr("SAVREF", savings.getBranchId(), savings.getCompanyId());
                Entry controlEntry = new Entry();
                Entry accountEntry = new Entry();
                controlEntry.setTxnType("CAD");
                controlEntry.setHeaderdocref(referenceNumber);
                controlEntry.setDocref("REF" + referenceNumber);
                controlEntry.setAmount((savings.getAmount()) * -1);
                controlEntry.setBranchId(savings.getBranchId());
                controlEntry.setCompany(savings.getCompanyId());
                controlEntry.setDebit(savings.getAmount());
                controlEntry.setNarrative(narrative);
                controlEntry.setUserId(savings.getUserId());
                controlEntry.setRate(new Double(1));
                controlEntry.setTxnSerial(serial);
                controlEntry.setTxncode("CAD");
                controlEntry.setAccountNo(account.getGlAccountNumber());
                controlEntry.setPeriod(period);
                controlEntry.setYear(year);

                accountEntry.setTxnType("CAD");
                accountEntry.setHeaderdocref(referenceNumber);
                accountEntry.setDocref("REF" + referenceNumber);
                accountEntry.setAmount(savings.getAmount());
                accountEntry.setBranchId(savings.getBranchId());
                accountEntry.setCompany(savings.getCompanyId());
                accountEntry.setCredit(savings.getAmount());
                accountEntry.setNarrative(narrative);
                accountEntry.setUserId(savings.getUserId());
                accountEntry.setRate(new Double(1));
                accountEntry.setTxnSerial(nvSerial.returnSerialnostr("SAVREF", savings.getBranchId(), savings.getCompanyId()));
                accountEntry.setTxncode("CAD");
                accountEntry.setAccountNo(savings.getAccountNumber());
                accountEntry.setPeriod(period);
                accountEntry.setYear(year);


                entryList.add(controlEntry);
                entryList.add(accountEntry);

                Txnsheader txnhdr = new Txnsheader(serial, "CAD", year, period, savings.getTrxDate(), postDate, entrydate, referenceNumber, txnCode.getDescription(), "SA", savings.getUserId(), savings.getBranchId(), savings.getCompanyId(), timezone);
                Entrys entry = new Entrys();
                entry.setTxnsheader(txnhdr);
                entry.setEntrys(entryList);
                if (postTransaction(entry)) {
                    byte trxStatus = 1;
                    savings.setStatus(trxStatus);

                    if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().add(savings)) {
                        status = true;
                        System.out.print("Saved");
                    } else {
                        System.out.print("Couldn't save");
                    }
                }
                System.out.println("GL Account " + account.getGlAccountNumber());
            }



        } catch (Exception ex) {

            _logger.error("editProduct error ", ex);
        }
        return status;
    }

    public boolean uploadTransaction(Savings saving, FileUpload fileUpload) {
        boolean status = false;
        try {

            if (DAOFactory.getDAOFactory().getSavingsDAO().save(saving)) {
                status = true;
            } else {
                status = false;
            }
        } catch (Exception e) {
            _logger.error("SavingServiceImpl.save(Savings savings)", e);

        }
        return status;
    }

    @Override
    public boolean saveFileUpload(FileUpload fileUpload) {

        boolean status = false;
        try {
            if (DAOFactory.getDAOFactory().getFileUploadDAO().save(fileUpload)) {
                status = true;
            } else {
                status = false;
            }

        } catch (Exception e) {
            _logger.error("SavingServiceImpl.saveFileUpload(FileUpload fileUpload)", e);

        }
        return status;
    }

    
    
    public LinkedList<FileMeta> performBulkSave(MultipartHttpServletRequest request, Users user) throws PersistentException {
        PersistentTransaction t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
        String pattern = "yyyy-MM-dd";
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uploadUri = (String) ctx.lookup("java:comp/env/uploadpath");
        } catch (NamingException ex) {
            _logger.error("upload directory error", ex);
        }
        //String rootPath = System.getProperty("catalina.home");
        String rootPath = uploadUri;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date trx_date = null;
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        String productId = request.getParameter("productId");
        int noOfRecords = Integer.valueOf(request.getParameter("noOfRecords"));
        float totalSum = Float.valueOf(request.getParameter("totalSum"));

        String referenceNumber = String.valueOf(System.currentTimeMillis());
        FilenameUtils fileUTIL = new FilenameUtils();

        String ext = null;
        String baseName = null;
        String serverFileName = null;

        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = dir.getAbsolutePath() + File.separator;


        while (itr.hasNext()) {


            mpf = request.getFile(itr.next());
            ext = fileUTIL.getExtension(mpf.getOriginalFilename());
            baseName = fileUTIL.getBaseName(mpf.getOriginalFilename());

            serverFileName = rootPath + "tmpfiles" + File.separator + baseName + "_" + referenceNumber + "." + ext;


            //create the file on server

            FileUpload fileUpload = new FileUpload();
            fileUpload.setBranchId(user.getBranch());
            fileUpload.setCompanyId(user.getCompanyid());
            fileUpload.setFileName(mpf.getOriginalFilename());
            fileUpload.setLocation(serverFileName);
            fileUpload.setReferenceNumber(referenceNumber);
            fileUpload.setProductId(Integer.valueOf(productId));
            fileUpload.setUserId(user.getUserId());
            fileUpload.setProcessedDate(new Date());
            fileUpload.setTotalRecords(noOfRecords);
            //fileUpload.setFileSum(totalSum);
            DAOFactory.getDAOFactory().getFileUploadDAO().save(fileUpload);


            System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());




            try {
                if (files.size() >= 10) {
                    files.pop();
                }


                fileMeta = new FileMeta();
                fileMeta.setFileName(mpf.getOriginalFilename());
                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());
                fileMeta.setBytes(mpf.getBytes());
                // to set the refrenceNumber
                fileMeta.setReferenceNumber(referenceNumber);

                File serverFile = new File(serverFileName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(mpf.getBytes());
                stream.close();

                t.commit();
            } catch (FileNotFoundException e) {
                t.rollback();
                e.printStackTrace();
            } catch (IOException e) {
                t.rollback();
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception ex) {
                t.rollback();
                ex.printStackTrace();
            }
            //2.4 add to files
            files.add(fileMeta);

        }


        return files;
    }

    
    
    
    public String performBulkSave2(MultipartHttpServletRequest request, Users user) throws PersistentException {
        PersistentTransaction t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
        String pattern = "yyyy-MM-dd";
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uploadUri = (String) ctx.lookup("java:comp/env/uploadpath");
        } catch (NamingException ex) {
            _logger.error("upload directory error", ex);
        }
        //String rootPath = System.getProperty("catalina.home");
        String rootPath = uploadUri;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date trx_date = null;
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        String productId = request.getParameter("productId");
        int noOfRecords = Integer.valueOf(request.getParameter("noOfRecords"));
        //float totalSum = Float.valueOf(request.getParameter("totalSum"));
        BigDecimal totalSum = new BigDecimal(request.getParameter("totalSum"));
        BigDecimal processedSum = new BigDecimal("0.0");
       // double totalSum = Double.parseDouble(request.getParameter("totalSum"));
       
        //System.out.println("totalSum :: " + totalSum);
        //System.out.println("request.getParameter(\"totalSum\") :: " + request.getParameter("totalSum"));
        String referenceNumber = String.valueOf(System.currentTimeMillis());
        FilenameUtils fileUTIL = new FilenameUtils();

        String ext = null;
        String baseName = null;
        String serverFileName = null;

        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = dir.getAbsolutePath() + File.separator;


        while (itr.hasNext()) {


            mpf = request.getFile(itr.next());
            ext = fileUTIL.getExtension(mpf.getOriginalFilename());
            baseName = fileUTIL.getBaseName(mpf.getOriginalFilename());

            serverFileName = rootPath + "tmpfiles" + File.separator + baseName + "_" + referenceNumber + "." + ext;


            //create the file on server

            FileUpload fileUpload = new FileUpload();
            fileUpload.setBranchId(user.getBranch());
            fileUpload.setCompanyId(user.getCompanyid());
            fileUpload.setFileName(mpf.getOriginalFilename());
            fileUpload.setLocation(serverFileName);
            fileUpload.setReferenceNumber(referenceNumber);
            fileUpload.setProductId(Integer.valueOf(productId));
            fileUpload.setUserId(user.getUserId());
            fileUpload.setProcessedDate(new Date());
            fileUpload.setTotalRecords(noOfRecords);
            //fileUpload.setFileSum(totalSum);
            System.out.println("totalSum in SavongsServiceImpl :: " +  totalSum);
            
            fileUpload.setFileSum(totalSum);
            fileUpload.setProcessedSum(processedSum);
            DAOFactory.getDAOFactory().getFileUploadDAO().save(fileUpload);


            System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());




            try {
                if (files.size() >= 10) {
                    files.pop();
                }


                fileMeta = new FileMeta();
                fileMeta.setFileName(mpf.getOriginalFilename());
                fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
                fileMeta.setFileType(mpf.getContentType());
                fileMeta.setBytes(mpf.getBytes());
                // to set the refrenceNumber
                fileMeta.setReferenceNumber(referenceNumber);

                File serverFile = new File(serverFileName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(mpf.getBytes());
                stream.close();

                t.commit();
            } catch (FileNotFoundException e) {
                t.rollback();
                e.printStackTrace();
            } catch (IOException e) {
                t.rollback();
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception ex) {
                t.rollback();
                ex.printStackTrace();
            }
            //2.4 add to files
            files.add(fileMeta);

        }


       // return files;
        return referenceNumber;
    }

    
    
    /**
     * public void readSavingsUpload() throws PersistentException {
     * PersistentTransaction t =
     * com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
     * String pattern = "yyyy-MM-dd"; String trxType = "S"; SimpleDateFormat
     * dateFormat = new SimpleDateFormat("yyyy-MM-dd"); SimpleDateFormat
     * fromUser = new SimpleDateFormat("dd-MM-yyyy"); SimpleDateFormat myFormat
     * = new SimpleDateFormat("yyyy-MM-dd"); Date trx_date = null;
     *
     * byte status = 0; List<SavingsError> savingsError = null;
     *
     * try {
     *
     * FileUploadCriteria criteria = new FileUploadCriteria();
     *
     * criteria.add(Restrictions.eq("status", status));
     *
     * List<FileUpload> uploads =
     * com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);
     * for (FileUpload f : uploads) { FileInputStream file = new
     * FileInputStream(new File(f.getLocation())); String extension =
     * FilenameUtils.getExtension(f.getFileName()); if
     * (extension.equalsIgnoreCase("xls")) {
     *
     *
     *
     * HSSFWorkbook workbook = new HSSFWorkbook(file);
     *
     *
     * HSSFSheet sheet = workbook.getSheetAt(0);
     *
     *
     * Iterator<Row> rowIterator = sheet.iterator();
     *
     * while (rowIterator.hasNext()) { Row row = rowIterator.next();
     *
     * //For each row, iterate through each columns Iterator<Cell> cellIterator
     * = row.cellIterator(); if (row.getRowNum() > 0) { if (savingsError ==
     * null) { savingsError = new ArrayList<SavingsError>(); } String
     * accountNumber = row.getCell(0).getStringCellValue(); String narrative =
     * row.getCell(1).getStringCellValue();
     *
     * Double amount = row.getCell(2).getNumericCellValue(); String date =
     * row.getCell(3).getStringCellValue();
     *
     * String reformattedStr = myFormat.format(fromUser.parse(date)); trx_date =
     * myFormat.parse(reformattedStr);
     *
     * //Date postDate = company.getPostDate(); if
     * ((EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy") ||
     * EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy")) &&
     * EasyCoopFinValidator.checkIfAccountExists(accountNumber)) {
     *
     * Account account =
     * com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber);
     * MemberCriteria mcriteria = new MemberCriteria();
     *
     * mcriteria.add(Restrictions.eq("memberNo", account.getAsegc())); Member m
     * =
     * com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
     * Savings saving = new Savings(); saving.setAccountNumber(accountNumber);
     * saving.setAmount(amount.floatValue());
     * saving.setBranchId(f.getBranchId());
     * saving.setCompanyId(f.getCompanyId()); saving.setDescription(narrative);
     * saving.setMemberId(m.getId());
     * saving.setReferenceNumber(f.getReferenceNumber());
     * saving.setTrxDate(trx_date); saving.setUserId(f.getUserId());
     * saving.setProductId(f.getProductId()); saving.setUserId(f.getUserId());
     * saving.setTrxType(trxType);
     *
     *
     * DAOFactory.getDAOFactory().getSavingsDAO().save(saving);
     *
     * } else { if ((EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy") ==
     * false || EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy") ==
     * false) && !EasyCoopFinValidator.checkIfAccountExists(accountNumber)) {
     * SavingsError sError = new SavingsError();
     * sError.setAccountNumber(accountNumber);
     * sError.setAmount(amount.floatValue());
     * sError.setBranchId(f.getBranchId());
     * sError.setCompanyId(f.getCompanyId()); sError.setDescription(narrative);
     * sError.setMemberId(new Integer(0));
     * sError.setReferenceNumber(f.getReferenceNumber());
     * sError.setTrxDate(trx_date); sError.setUserId(f.getUserId());
     * sError.setProductId(f.getProductId()); sError.setUserId(f.getUserId());
     * sError.setTrxType(trxType); sError.setErrorMessage("Invaid date and
     * account number");
     *
     * savingsError.add(sError); } else if
     * ((EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy") == false ||
     * EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy") == false) &&
     * EasyCoopFinValidator.checkIfAccountExists(accountNumber)) { SavingsError
     * sError = new SavingsError(); sError.setAccountNumber(accountNumber);
     * sError.setAmount(amount.floatValue());
     * sError.setBranchId(f.getBranchId());
     * sError.setCompanyId(f.getCompanyId()); sError.setDescription(narrative);
     * sError.setMemberId(new Integer(0));
     * sError.setReferenceNumber(f.getReferenceNumber());
     * sError.setTrxDate(trx_date); sError.setUserId(f.getUserId());
     * sError.setProductId(f.getProductId()); sError.setUserId(f.getUserId());
     * sError.setTrxType(trxType); sError.setErrorMessage("Invaid date");
     * savingsError.add(sError); } else if
     * ((EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy") ||
     * EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy")) &&
     * EasyCoopFinValidator.checkIfAccountExists(accountNumber) == false) {
     * SavingsError sError = new SavingsError();
     * sError.setAccountNumber(accountNumber);
     * sError.setAmount(amount.floatValue());
     * sError.setBranchId(f.getBranchId());
     * sError.setCompanyId(f.getCompanyId()); sError.setDescription(narrative);
     * sError.setMemberId(new Integer(0));
     * sError.setReferenceNumber(f.getReferenceNumber());
     * sError.setTrxDate(trx_date); sError.setUserId(f.getUserId());
     * sError.setProductId(f.getProductId()); sError.setUserId(f.getUserId());
     * sError.setTrxType(trxType); sError.setErrorMessage("Invaid account
     * number"); savingsError.add(sError); } }
     *
     *
     *
     * }
     *
     * }
     *
     *
     * } else { XSSFWorkbook workbook = new XSSFWorkbook(file);
     *
     * //Get first sheet from the workbook XSSFSheet sheet =
     * workbook.getSheetAt(0); //Get iterator to all the rows in current sheet
     * Iterator<Row> rowIterator = sheet.iterator();
     *
     * while (rowIterator.hasNext()) { Row row = rowIterator.next();
     *
     * //For each row, iterate through each columns Iterator<Cell> cellIterator
     * = row.cellIterator(); if (row.getRowNum() > 0) { String accountNumber =
     * row.getCell(0).getStringCellValue(); String narrative =
     * row.getCell(1).getStringCellValue(); Double amount =
     * row.getCell(2).getNumericCellValue(); String date =
     * row.getCell(3).getStringCellValue();
     *
     * String reformattedStr = myFormat.format(fromUser.parse(date)); trx_date =
     * myFormat.parse(reformattedStr);
     *
     * // Date postDate = company.getPostDate(); if
     * ((EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy") ||
     * EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy")) &&
     * EasyCoopFinValidator.checkIfAccountExists(accountNumber)) {
     *
     * Account account =
     * com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber);
     * MemberCriteria mcriteria = new MemberCriteria();
     *
     * mcriteria.add(Restrictions.eq("memberNo", account.getAsegc())); Member m
     * =
     * com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
     *
     * Savings saving = new Savings(); saving.setAccountNumber(accountNumber);
     * saving.setAmount(amount.floatValue());
     * saving.setBranchId(f.getBranchId());
     * saving.setCompanyId(f.getCompanyId()); saving.setDescription(narrative);
     * saving.setMemberId(m.getId());
     * saving.setReferenceNumber(f.getReferenceNumber());
     * saving.setTrxDate(trx_date); saving.setUserId(f.getUserId());
     * saving.setProductId(f.getProductId()); saving.setTrxType(trxType);
     * DAOFactory.getDAOFactory().getSavingsDAO().save(saving);
     *
     * } else { if ((!EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy")
     * || !EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy")) &&
     * !EasyCoopFinValidator.checkIfAccountExists(accountNumber)) { SavingsError
     * sError = new SavingsError(); sError.setAccountNumber(accountNumber);
     * sError.setAmount(amount.floatValue());
     * sError.setBranchId(f.getBranchId());
     * sError.setCompanyId(f.getCompanyId()); sError.setDescription(narrative);
     * sError.setMemberId(new Integer(0));
     * sError.setReferenceNumber(f.getReferenceNumber());
     * sError.setTrxDate(trx_date); sError.setUserId(f.getUserId());
     * sError.setProductId(f.getProductId()); sError.setUserId(f.getUserId());
     * sError.setTrxType(trxType); sError.setErrorMessage("Invaid date and
     * account number");
     *
     * savingsError.add(sError); } else if
     * ((!EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy") ||
     * !EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy")) &&
     * EasyCoopFinValidator.checkIfAccountExists(accountNumber)) { SavingsError
     * sError = new SavingsError(); sError.setAccountNumber(accountNumber);
     * sError.setAmount(amount.floatValue());
     * sError.setBranchId(f.getBranchId());
     * sError.setCompanyId(f.getCompanyId()); sError.setDescription(narrative);
     * sError.setMemberId(new Integer(0));
     * sError.setReferenceNumber(f.getReferenceNumber());
     * sError.setTrxDate(trx_date); sError.setUserId(f.getUserId());
     * sError.setProductId(f.getProductId()); sError.setUserId(f.getUserId());
     * sError.setTrxType(trxType); sError.setErrorMessage("Invaid date");
     * savingsError.add(sError); } else if
     * ((EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy") ||
     * EasyCoopFinValidator.isThisDateValid(date, "dd/MM/yyyy")) &&
     * !EasyCoopFinValidator.checkIfAccountExists(accountNumber)) { SavingsError
     * sError = new SavingsError(); sError.setAccountNumber(accountNumber);
     * sError.setAmount(amount.floatValue());
     * sError.setBranchId(f.getBranchId());
     * sError.setCompanyId(f.getCompanyId()); sError.setDescription(narrative);
     * sError.setMemberId(new Integer(0));
     * sError.setReferenceNumber(f.getReferenceNumber());
     * sError.setTrxDate(trx_date); sError.setUserId(f.getUserId());
     * sError.setProductId(f.getProductId()); sError.setUserId(f.getUserId());
     * sError.setTrxType(trxType); sError.setErrorMessage("Invaid account
     * number"); savingsError.add(sError); } }
     *
     * }
     *
     * }
     * }
     * com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().updateFileUpload(f.getId());
     * file.close();
     *
     *
     * }
     * if (savingsError.size() > 0) { for (SavingsError sE : savingsError) {
     * com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsErrorDAO().save(sE);
     * } } t.commit(); } catch (FileNotFoundException e) { t.rollback();
     * _logger.error("readSavingsUpload(Users user)", e); } catch (IOException
     * e) { t.rollback(); _logger.error("readSavingsUpload(Users user)", e); }
     * catch (ParseException e) { t.rollback();
     * _logger.error("readSavingsUpload(Users user)", e); } catch (Exception ex)
     * { t.rollback(); _logger.error("readSavingsUpload(Users user)", ex); } }
     *
     */
    @Override
    public LinkedList<FileMeta> doBulkSave(MultipartHttpServletRequest request, Users user) throws PersistentException {
        PersistentTransaction t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
        String pattern = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date trx_date = null;
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        String productId = request.getParameter("productId");

        String referenceNumber = String.valueOf(System.currentTimeMillis());

        String extension = null;



        while (itr.hasNext()) {


            mpf = request.getFile(itr.next());
            FileUpload fileUpload = new FileUpload();
            fileUpload.setBranchId(user.getBranch());
            fileUpload.setCompanyId(user.getCompanyid());
            fileUpload.setFileName(mpf.getOriginalFilename());
            fileUpload.setLocation("C:/temp/files/" + mpf.getOriginalFilename());
            fileUpload.setReferenceNumber(referenceNumber);
            fileUpload.setProductId(Integer.valueOf(productId));
            fileUpload.setUserId(user.getUserId());
            DAOFactory.getDAOFactory().getFileUploadDAO().save(fileUpload);


            System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());
            extension = FilenameUtils.getExtension(mpf.getOriginalFilename());


            if (files.size() >= 10) {
                files.pop();
            }


            fileMeta = new FileMeta();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
            fileMeta.setFileType(mpf.getContentType());

            try {
                fileMeta.setBytes(mpf.getBytes());


                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("C:/temp/files/" + mpf.getOriginalFilename()));

                FileInputStream file = new FileInputStream(new File("C:/temp/files/" + mpf.getOriginalFilename()));


                if (extension.equalsIgnoreCase("xls")) {



                    HSSFWorkbook workbook = new HSSFWorkbook(file);


                    HSSFSheet sheet = workbook.getSheetAt(0);


                    Iterator<Row> rowIterator = sheet.iterator();

                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();

                        //For each row, iterate through each columns
                        Iterator<Cell> cellIterator = row.cellIterator();
                        if (row.getRowNum() > 0) {

                            String accountNumber = row.getCell(0).getStringCellValue();
                            String narrative = row.getCell(1).getStringCellValue();
                            Double amount = row.getCell(2).getNumericCellValue();
                            String date = row.getCell(3).getStringCellValue();

                            String reformattedStr = myFormat.format(fromUser.parse(date));
                            trx_date = myFormat.parse(reformattedStr);

                            Savings saving = new Savings();
                            saving.setAccountNumber(accountNumber);
                            saving.setAmount(amount.floatValue());
                            saving.setBranchId(user.getBranch());
                            saving.setCompanyId(user.getCompanyid());
                            saving.setDescription(narrative);
                            saving.setMemberId(1);
                            saving.setReferenceNumber(referenceNumber);
                            saving.setTrxDate(trx_date);
                            saving.setUserId(user.getUserId());
                            saving.setProductId(Integer.valueOf(productId));
                            saving.setUserId(user.getUserId());
                            // savingService.uploadSingleTransaction(saving);
                            DAOFactory.getDAOFactory().getSavingsDAO().save(saving);


                        }

                    }


                } else {
                    XSSFWorkbook workbook = new XSSFWorkbook(file);

//Get first sheet from the workbook
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    //Get iterator to all the rows in current sheet
                    Iterator<Row> rowIterator = sheet.iterator();

                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();

                        //For each row, iterate through each columns
                        Iterator<Cell> cellIterator = row.cellIterator();
                        if (row.getRowNum() > 0) {
                            String accountNumber = row.getCell(0).getStringCellValue();
                            String narrative = row.getCell(1).getStringCellValue();
                            Double amount = row.getCell(2).getNumericCellValue();
                            String date = row.getCell(3).getStringCellValue();

                            String reformattedStr = myFormat.format(fromUser.parse(date));
                            trx_date = myFormat.parse(reformattedStr);

                            Savings saving = new Savings();
                            saving.setAccountNumber(accountNumber);
                            saving.setAmount(amount.floatValue());
                            saving.setBranchId(user.getBranch());
                            saving.setCompanyId(user.getCompanyid());
                            saving.setDescription(narrative);
                            saving.setMemberId(1);
                            saving.setReferenceNumber(referenceNumber);
                            saving.setTrxDate(trx_date);
                            saving.setUserId(user.getUserId());
                            saving.setProductId(Integer.valueOf(productId));

                            DAOFactory.getDAOFactory().getSavingsDAO().save(saving);



                        }

                    }
                }

                file.close();

                t.commit();
            } catch (FileNotFoundException e) {
                t.rollback();
                e.printStackTrace();
            } catch (IOException e) {
                t.rollback();
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                t.rollback();
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception ex) {
                t.rollback();
                ex.printStackTrace();
            }
            //2.4 add to files
            files.add(fileMeta);

        }


        return files;
    }

    public String createAccount(int customerId, String productCode, int branchId, int companyId, String timezone) {
        String output = "";
        String returnVal = "";
        int resp = 0;
        theerrormess = "";
        Branch branch = null;
        Company company = null;
        Member member = null;
        System.out.println("Create Acount Reached");
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
            DBASE_URI = uri;
            System.out.println("URL: " + uri);
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        try {


            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(DBASE_URI).path("glwsprdacno");

            branch = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getBranchDAO().getBranchByORMID(branchId);
            company = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().getCompanyByORMID(companyId);

            member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().getMemberByORMID(customerId);
            System.out.println("\nCustomer No: " + member.getMemberNo());
            Accnowbs account = new Accnowbs();
            account.setBranchId(branchId);
            account.setBranchcode(branch.getBranchCode());
            account.setCompany(companyId);
            account.setCompanycode(company.getCode());
            account.setProductcode(productCode);
            account.setSubno("01");
            account.setCustomerno(member.getMemberNo());
            account.setTimezone(timezone);


            Client client = Client.create();
            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, account);
            System.out.println("Server response : \n" + response.getStatus());
            resp = response.getStatus();
            if (response.getStatus() != 201) {
                theerrormess = "Failed : HTTP error code : " + response.getStatus() + ". Operation failed";
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus() + ". Operation failed");
            }

            output = response.getEntity(String.class);
            if (!output.isEmpty() || output != null) {
                returnVal = output;
            }
            System.out.println("Server response : \n");
            System.out.println("Output from service: " + output);
        } catch (PersistentException ex) {
            _logger.error("SavingsController.saveAccount()", ex);

        } catch (NullPointerException e) {
            _logger.error("SavingsController.saveAccount()", e);
        } catch (Exception e) {
            _logger.error("SavingsController.saveAccount()", e);
        }
        return returnVal;

    }

    public String createSavingsAccount(Accnowbs account) {
        String output = "";

        /**
         * WebClient client =
         * WebClient.create("http://localhost:8095/Easycoopfin1");
         * client.path("webserv/glwsprdacno");
         * client.type("text/xml").accept("text/xml"); Response r =
         * client.post(account); return "Status: " + r.getStatus();*
         */
        return output;
    }

    @Override
    public List<Member> getMembers(String name) {
        List<Member> members = null;
        try {
            int statusId = 2;
            MemberCriteria criteria = new MemberCriteria();
            Criterion firstName = Restrictions.ilike("firstName", name + "%");
            Criterion lastName = Restrictions.ilike("surname", name + "%");
            LogicalExpression orExp = Restrictions.or(firstName, lastName);
            criteria.add(orExp);
            criteria.add(Restrictions.eq("statusId", statusId));
            members = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().listAllMembers(criteria);

        } catch (PersistentException ex) {
            _logger.error("getMembers(String name)", ex);
        }
        return members;
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean postTransaction(Entrys entrys) {

        boolean success = false;

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
            DBASE_URI = uri;
            System.out.println("URL: " + uri);
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(DBASE_URI).path("glws");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, entrys);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
            } else {
                success = true;
            }

            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public List<Custaccountdetails> getAccounts() {
        List<Custaccountdetails> accounts = null;
        try {
            accounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCustaccountdetailsDAO().listAllAccountsByQuery(null, null);
        } catch (PersistentException ex) {
            _logger.error("List<Custaccountdetails> getAccounts() ", ex);
        }
        return accounts;
    }

    public boolean withdraw(Savings savings, int period, int year, String timezone, Date postDate) {
        boolean status = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String referenceNumber = String.valueOf(System.currentTimeMillis());
        String controlAccount = null;
        savings.setReferenceNumber(referenceNumber);
        savings.setTrxType("W");
        Date trx_date = null;
        String serial = "";
        Date entrydate = null;
        String accountCode = "SRC";

        //controlEntry.
        try {
            Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(savings.getProductId());
            entrydate = new Date();


            ProductAccountCriteria accountCriteria = new ProductAccountCriteria();
            accountCriteria.add(Restrictions.eq("productId", savings.getProductId()));
            accountCriteria.add(Restrictions.eq("productAccountTypeCode", accountCode));

            TxnCodeCriteria txnCriteria = new TxnCodeCriteria();
            txnCriteria.add(Restrictions.eq("transactionCode", "CAW"));
            TxnCode txnCode = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getTxnCodeDAO().loadTxnCodeByCriteria(txnCriteria);
            String narrative = String.format(txnCode.getNarrative(), savings.getAccountNumber(), period, year);

            ProductAccount account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().loadProductAccountByCriteria(accountCriteria);
            if (account == null) {
                System.out.println("This account does not have an account attached to it");
            } else {
                TimeZone timeZone = TimeZone.getTimeZone(timezone);

                System.out.println("Account Code: " + account.getProductAccountTypeCode());
                String reformattedStr = myFormat.format(fromUser.parse(savings.getStringDate()));
                trx_date = myFormat.parse(reformattedStr);
                savings.setTrxDate(trx_date);
                LinkedList<Entry> entryList;
                entryList = new LinkedList<Entry>();
                NewSerialno nvSerial = new NewSerialno();
                serial = nvSerial.returnSerialnostr("SAVREF", savings.getBranchId(), savings.getCompanyId());
                Entry controlEntry = new Entry();
                Entry accountEntry = new Entry();
                controlEntry.setTxnType("CAW");//TransactionCode
                controlEntry.setHeaderdocref(referenceNumber);
                controlEntry.setDocref("REF" + referenceNumber);
                controlEntry.setAmount(savings.getAmount());
                controlEntry.setCcyAmount(savings.getAmount());
                controlEntry.setBranchId(savings.getBranchId());
                controlEntry.setCompany(savings.getCompanyId());
                controlEntry.setCredit(savings.getAmount());
                controlEntry.setNarrative(narrative);
                controlEntry.setUserId(savings.getUserId());
                controlEntry.setRate(new Double(1));
                controlEntry.setTxnSerial(serial);
                controlEntry.setTxncode("CAW");
                controlEntry.setAccountNo(account.getGlAccountNumber());
                controlEntry.setPeriod(period);
                controlEntry.setYear(year);

                accountEntry.setTxnType("CAW");//TransactionCode
                accountEntry.setHeaderdocref(referenceNumber);
                accountEntry.setDocref("REF" + referenceNumber);
                accountEntry.setAmount((savings.getAmount()) * -1);
                accountEntry.setCcyAmount((savings.getAmount()) * -1);
                accountEntry.setBranchId(savings.getBranchId());
                accountEntry.setCompany(savings.getCompanyId());
                accountEntry.setDebit(savings.getAmount());
                accountEntry.setNarrative(narrative);
                accountEntry.setUserId(savings.getUserId());
                accountEntry.setRate(new Double(1));
                accountEntry.setTxnSerial(serial);
                accountEntry.setTxncode("CAW");
                accountEntry.setAccountNo(savings.getAccountNumber());
                accountEntry.setPeriod(period);
                accountEntry.setYear(year);


                entryList.add(controlEntry);
                entryList.add(accountEntry);

                //SA-TransactionCode ,Journal Posting: - Description from txncodes
                Txnsheader txnhdr = new Txnsheader(serial, "CAW", year, period, savings.getTrxDate(), postDate, entrydate, referenceNumber, txnCode.getDescription(), "SA", savings.getUserId(), savings.getBranchId(), savings.getCompanyId(), timezone);
                Entrys entry = new Entrys();
                entry.setTxnsheader(txnhdr);
                entry.setEntrys(entryList);
                if (postTransaction(entry)) {
                    byte trxStatus = 1;
                    savings.setStatus(trxStatus);
                    if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(savings)) {
                        status = true;
                        System.out.print("Saved");
                    } else {
                        System.out.print("Couldn't save");
                    }
                }
                System.out.println("GL Account " + account.getGlAccountNumber());
            }



        } catch (Exception ex) {

            _logger.error("withdraw error ", ex);
        }
        return status;
    }

    public void getRoleparameters() {
        String name = "";
        String password = "";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        String mySQLString;
        mySQLString = "select * FROM tblwebserv a where a.app = 'internal'";
        ResultSet agRecSet;
        try {
            agRecSet = dbobj.retrieveRecset(mySQLString);
            while (agRecSet.next()) {
                name = agRecSet.getString("user");
                password = agRecSet.getString("pwd");
            }
            dname = name;
            dpwd = password;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (dbobj != null) {
                dbobj.closecon();
            }
        }
    }

    @Override
    public List<SavingsError> loadSavingsError(Users user) {
        List<SavingsError> errors = null;
        try {
            SavingsErrorCriteria criteria = new SavingsErrorCriteria();
            criteria.add(Restrictions.eq("companyId", user.getCompanyid()));
            criteria.add(Restrictions.eq("branchId", user.getBranch()));
            String condition = " SavingsError.branchId=" + user.getBranch();
            String order = " SavingsError.trxDate desc";
            errors = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsErrorDAO().listAllSavingByQuery(condition, order);
            for (SavingsError e : errors) {
                //System.out.println("Description:" + e.getDescription());
            }
        } catch (PersistentException ex) {
            Logger.getLogger(SavingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return errors;
    }

    public List<Savings> loadPendingSavings(Users user) {
        List<Savings> savings = null;
        byte status = 0;
        byte isProcessed = 1;
        byte isBatch = 0;
        try {
            int dbranch = user.getBranch();
            int dcompany = user.getCompanyid();
            String condition = " Savings.branchId=" + dbranch;
            condition += " AND Savings.status=" + status;
            condition += " AND Savings.isProcessed=" + isProcessed;
            condition += " AND Savings.isApproved=" + status;
            condition += " AND Savings.isBatch=" + isBatch;
            // condition += " AND Savings.userId <> '"+user.getUserId()+"'";
            //  condition += " AND (Savings.verifierId is NULL OR  Savings.verifierId <> '"+user.getUserId()+"' )";
            String order = " Savings.trxDate desc";
            savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingByQuery(condition, order);
            for (Savings e : savings) {
                //System.out.println("Description:" + e.getDescription());
            }
        } catch (Exception ex) {

            _logger.error("loadPendingSavings(users)", ex);
        }
        return savings;
    }

    public String authorize(String id, String userId) {
        String returnValue = "";
        PersistentSession session;
        String status = "";
        try {

            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            // t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            String sql = "select * from savings id in (" + id + ") and user_id='" + userId + "' limit 1";
            Query query = session.createSQLQuery(sql);
            // t.commit();
            if (query == null || query.list().isEmpty()) {
                System.out.println("User is null");


                SavingsCriteria criteria = new SavingsCriteria();
                criteria.add(Restrictions.eq("referenceNumber", id));
                byte isProcessed = 1;
                List<Savings> savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);
                for (Savings s : savings) {
                    s.setIsProcessed(isProcessed);
                    s.setVerifierId(userId);
                    com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(s);
                    System.out.println("Reference Number " + s.getReferenceNumber());
                }
                FileUploadCriteria fCriteria = new FileUploadCriteria();
                fCriteria.add(Restrictions.eq("referenceNumber", id));
                FileUpload fUpload = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().loadFileUploadsByCriteria(fCriteria);
                System.out.println("Loaction" + fUpload.getLocation());
                fUpload.setIsVerified(isProcessed);
                fUpload.setVerifierId(userId);

                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().save(fUpload);
                status = "ok";
            } else {
                status = "You cannot verify this transaction";
                System.out.println("User is exists: " + query.list().get(0).toString() + " User:" + userId);
            }



        } catch (PersistentException ex) {
            // t.rollback();
            _logger.error("verify(String referenceNumber, String userId)", ex);
        }

        return returnValue;
    }

    public void updateSavingsStatus(String id) {
        com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().updateSavingStatus(id);
    }

    public void doBulkGlPosting() {
        PersistentSession session;
        SavingsCriteria criteria;
        byte isProcessed = 1;
        byte productStatus = 0;
        List<Savings> processedSavings = null;
        boolean status = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String referenceNumber = String.valueOf(System.currentTimeMillis());
        String controlAccount = null;

        int period;
        int year;
        String timezone;
        Date trx_date = null;
        String serial = "";
        Date entrydate = null;
        String accountCode = "SRC";
        Date postDate;
        try {
            criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("isProcessed", isProcessed));
            criteria.add(Restrictions.eq("status", productStatus));

            processedSavings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);
            for (Savings savings : processedSavings) {

                Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(savings.getProductId());
                session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
                SQLQuery query = session.createSQLQuery("SELECT c.*, b.*,ct.* FROM company c join countries as ct on ct.id=c.country_id join branch as b on b.company_id=c.id where c.id = '" + savings.getCompanyId() + "' and b.id='" + savings.getBranchId() + "'");
                List<Object[]> rows = query.list();
                Object[] row = rows.get(0);
                period = Integer.valueOf(row[36].toString());
                year = Integer.valueOf(row[37].toString());
                timezone = row[56].toString();
                entrydate = HelperUtil.getCurrentDateByTimezone(timezone);

                String reformattedStr = myFormat.format(fromUser.parse(row[33].toString()));
                postDate = myFormat.parse(reformattedStr);
                ProductAccountCriteria accountCriteria = new ProductAccountCriteria();
                accountCriteria.add(Restrictions.eq("productId", savings.getProductId()));
                accountCriteria.add(Restrictions.eq("productAccountTypeCode", accountCode));

                TxnCodeCriteria txnCriteria = new TxnCodeCriteria();
                txnCriteria.add(Restrictions.eq("transactionCode", "CAD"));

                TxnCode txnCode = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getTxnCodeDAO().loadTxnCodeByCriteria(txnCriteria);
                ProductAccount account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().loadProductAccountByCriteria(accountCriteria);
                String narrative = String.format(txnCode.getNarrative(), savings.getAccountNumber(), period, year);
                if (account == null) {
                    System.out.println("This account does not have an account attached to it");
                } else {

                    LinkedList<Entry> entryList;
                    entryList = new LinkedList<Entry>();
                    NewSerialno nvSerial = new NewSerialno();
                    serial = nvSerial.returnSerialnostr("SAVREF", savings.getBranchId(), savings.getCompanyId());
                    Entry controlEntry = new Entry();
                    Entry accountEntry = new Entry();
                    controlEntry.setTxnType("CAD");
                    controlEntry.setHeaderdocref(referenceNumber);
                    controlEntry.setDocref("REF" + referenceNumber);
                    controlEntry.setAmount((savings.getAmount()) * -1);
                    controlEntry.setBranchId(savings.getBranchId());
                    controlEntry.setCompany(savings.getCompanyId());
                    controlEntry.setDebit(savings.getAmount());
                    controlEntry.setNarrative(narrative);
                    controlEntry.setUserId(savings.getUserId());
                    controlEntry.setRate(new Double(1));
                    controlEntry.setTxnSerial(serial);
                    controlEntry.setTxncode("CAD");
                    controlEntry.setAccountNo(account.getGlAccountNumber());
                    controlEntry.setPeriod(period);
                    controlEntry.setYear(year);

                    accountEntry.setTxnType("CAD");
                    accountEntry.setHeaderdocref(referenceNumber);
                    accountEntry.setDocref("REF" + referenceNumber);
                    accountEntry.setAmount(savings.getAmount());
                    accountEntry.setBranchId(savings.getBranchId());
                    accountEntry.setCompany(savings.getCompanyId());
                    accountEntry.setCredit(savings.getAmount());
                    accountEntry.setNarrative(narrative);
                    accountEntry.setUserId(savings.getUserId());
                    accountEntry.setRate(new Double(1));
                    accountEntry.setTxnSerial(nvSerial.returnSerialnostr("SAVREF", savings.getBranchId(), savings.getCompanyId()));
                    accountEntry.setTxncode("CAD");
                    accountEntry.setAccountNo(savings.getAccountNumber());
                    accountEntry.setPeriod(period);
                    accountEntry.setYear(year);


                    entryList.add(controlEntry);
                    entryList.add(accountEntry);

                    Txnsheader txnhdr = new Txnsheader(serial, "CAD", year, period, savings.getTrxDate(), postDate, entrydate, referenceNumber, txnCode.getDescription(), "SA", savings.getUserId(), savings.getBranchId(), savings.getCompanyId(), timezone);
                    Entrys entry = new Entrys();
                    entry.setTxnsheader(txnhdr);
                    entry.setEntrys(entryList);
                    System.out.println("Posting for savings with id: " + savings.getId());
                    if (postTransaction(entry)) {
                        byte trxStatus = 1;
                        savings.setStatus(trxStatus);

                        if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(savings)) {

                            System.out.println("Saved: " + savings.getId());
                        } else {
                            System.out.println("Couldn't save: " + savings.getId());
                        }
                    } else {
                    }

                }

            }

        } catch (PersistentException ex) {
            _logger.error("doBulkGlPosting", ex);
        } /**
         * catch (ParseException ex) { _logger.error("doBulkGlPosting", ex); } *
         */
        catch (Exception ex) {
            _logger.error("doBulkGlPosting", ex);
        }

    }

    @Override
    public boolean saveTransaction(Savings saving) {
        byte isProcessed = 1;
        boolean status = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String trxTye = "S";
        Date trx_date = null;
        try {
            String referenceNumber = String.valueOf(System.currentTimeMillis());
            String reformattedStr = myFormat.format(fromUser.parse(saving.getStringDate()));
            trx_date = myFormat.parse(reformattedStr);
            saving.setTrxDate(trx_date);
            saving.setReferenceNumber(referenceNumber);
            saving.setIsProcessed(isProcessed);
            saving.setTrxType(trxTye);
            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(saving)) {
                status = true;
                System.out.print("Saved");
            } else {
                status = false;
                System.out.print("Couldn't save");
            }
        } catch (PersistentException ex) {
            _logger.error("saveTransaction(Savings saving)", ex);
        } catch (Exception ex) {

            _logger.error("editProduct error ", ex);
        }
        return status;
    }

    @Override
    public void testJoin() {
        PersistentSession session;
        String productCode = null;
        int companyId = 4;
        try {
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            SQLQuery query = session.createSQLQuery("SELECT c.*, b.*,ct.* FROM company c join countries as ct on ct.id=c.country_id join branch as b on b.company_id=c.id where c.id = '" + companyId + "' and b.id='3'");
            //query.addEntity("c",com.sift.admin.model.Company.class);
            //query.addJoin("ct", "c.");

            List<Object[]> rows = query.list();
            Object[] row = rows.get(0);

            System.out.println("Company Id: " + row[0].toString());
            System.out.println("Country Id: " + row[2].toString());
            System.out.println("Compnay Name: " + row[10].toString());
            System.out.println("Branch Id: " + row[17].toString());
            System.out.println("Branch Name: " + row[20].toString());
            System.out.println("Post date: " + row[33].toString());
            System.out.println("Entry date: " + row[34].toString());
            System.out.println("Current Period: " + row[36].toString());
            System.out.println("Current Year: " + row[37].toString());
            System.out.println("Country Name: " + row[49].toString());
            System.out.println("Time Zone: " + row[56].toString());
            System.out.println("14 row : " + row[14].toString());
            System.out.println("15 row : " + row[15].toString());
            System.out.println("16 row : " + row[16].toString());
            System.out.println("17 row : " + row[17].toString());
            System.out.println("18 row : " + row[18].toString());
            System.out.println("Object : " + row);

        } catch (PersistentException ex) {
            Logger.getLogger(SavingsDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @Override
    public List<FileUpload> listUnVerifiedUploads(int branchId, int companyId) {
        List<FileUpload> fileUloads = null;
        try {
            byte isVerified = 0;
            byte status = 1;
            byte isApproved = 0;
            //System.out.println("status : " + status);
            FileUploadCriteria criteria = new FileUploadCriteria();
            criteria.add(Restrictions.eq("isVerified", isVerified));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.eq("isApproved", isApproved));
            //criteria.add(Restrictions.ne("isApproved", isApproved));

            fileUloads = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);

        } catch (PersistentException ex) {
            _logger.error("listUnVerifiedUploads", ex);
        }
        return fileUloads;
    }

    public String verify(String referenceNumber, String userId) {
        PersistentSession session;
        String status = "";
        try {

            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            // t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            String sql = "select user_id from savings  where reference_number='" + referenceNumber + "' and user_id='" + userId + "' limit 1";
            Query query = session.createSQLQuery(sql);
            // t.commit();
            if (query == null || query.list().isEmpty()) {
                //System.out.println("User is null");

                SavingsCriteria criteria = new SavingsCriteria();
                criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
                byte isProcessed = 1;
                List<Savings> savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);
                for (Savings s : savings) {
                    s.setIsProcessed(isProcessed);
                    s.setVerifierId(userId);
                    com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(s);
                    //System.out.println("Reference Number " + s.getReferenceNumber());
                }
                FileUploadCriteria fCriteria = new FileUploadCriteria();
                fCriteria.add(Restrictions.eq("referenceNumber", referenceNumber));
                FileUpload fUpload = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().loadFileUploadsByCriteria(fCriteria);
                System.out.println("Loaction :: " + fUpload.getLocation());
                fUpload.setIsVerified(isProcessed);
                fUpload.setVerifierId(userId);

                 String mailBody = "	<style type=\"text/css\">"
                            + "	<!--"
                            + "	.style2 {"
                            + "		font-family: Verdana, Arial, Helvetica, sans-serif;"
                            + "		font-size: 14px;"
                            + "	}"
                            + "	-->"
                            + "	</style>"
                            + "	 <p class=\"style2\">Hello, </p>"
                            + "	<p class=\"style2\">The transactin with reference #" + referenceNumber + " is awaiting approval <br>"
                         
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                         
                            + "	</table>"
                            + "	<p class=\"style2\"><br>"
                            + "	  Thanks </p> ";
                 
                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().save(fUpload);
                //MailBean MB = HelperUtil.createMailBean();
                //MB.setSubject(SavingsDefinitions.VERIFICATION_SUBJECT);
                //MB.setToemail("logzyy@gmail.com");

               // MB.setMailBody(mailBody);
               // HelperUtil.sendMail(MB);
                System.out.println("mailBody :: " + mailBody );
                util.sendMailByUserGroup("SA5", fUpload.getBranchId(), fUpload.getCompanyId(), userId, SavingsDefinitions.VERIFICATION_SUBJECT, mailBody);
                status = "ok";
            } else {
                status = "You cannot verify this transaction";
                // System.out.println("User is exists: " + query.list().get(0).toString() + " User:" + userId);
            }



        } catch (PersistentException ex) {
            // t.rollback();
            _logger.error("verify(String referenceNumber, String userId)", ex);
        }
        return status;
    }

    public void updateRecords(String referenceNumber, String userId) {
        try {
            SavingsCriteria criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
            byte isProcessed = 1;
            List<Savings> savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);
            for (Savings s : savings) {
                s.setIsProcessed(isProcessed);
                s.setVerifierId(userId);
                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(s);
                System.out.println("Reference Number " + s.getReferenceNumber());
            }
            FileUploadCriteria fCriteria = new FileUploadCriteria();
            fCriteria.add(Restrictions.eq("referenceNumber", referenceNumber));
            FileUpload fUpload = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().loadFileUploadsByCriteria(fCriteria);
            System.out.println("Loaction" + fUpload.getLocation());
            fUpload.setIsVerified(isProcessed);
            fUpload.setVerifierId(userId);
        } catch (PersistentException ex) {
        }
    }

    @Override
    public boolean checkIfAccountByBranchIsValid(String accountNumber, int branchId, int companyId) {
        boolean valid = false;
        try {
            if (EasyCoopFinValidator.checkIfAccountExists(accountNumber, branchId, companyId) && EasyCoopFinValidator.isValidBranchAccount(accountNumber, branchId, companyId)) {
                valid = true;
            } else {
                valid = false;
            }
        } catch (PersistentException ex) {
            _logger.error("checkIfAccountByBranchIsValid(String accountNumber, int branchId, int companyId)", ex);
        }
        return valid;
    }

    public List<Savings> loadSavingsByReferenceNumber(String referenceNumber) {
        List<Savings> savings = null;
        try {
            SavingsCriteria criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
            savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);
        } catch (PersistentException ex) {
            savings = null;
            _logger.error("loadSavingsByReferenceNumber(String referenceNumber)", ex);
        }
        return savings;
    }

    public List<SavingsError> loadSavingsErrorByReferenceNumber(String referenceNumber) {
        List<SavingsError> error = null;
        try {
            SavingsErrorCriteria criteria = new SavingsErrorCriteria();
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
            error = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsErrorDAO().listAllSavingsErrorByCriteria(criteria);
        } catch (PersistentException ex) {
            error = null;
            _logger.error("loadSavingsErrorByReferenceNumber(String referenceNumber)", ex);
        }
        return error;
    }

    @Override
    public boolean getProductTypeByAccountNumber(String accountNumber, int branchId, int companyId) {
        Custaccountdetails account = DAOFactory.getDAOFactory().getAccountDAO().getCustomerAccountByAccountNumber(accountNumber, branchId, companyId);
        String productCode = account.getProduct();
        ProductsCriteria criteria;
        Products product;
        boolean status = false;
        try {
            criteria = new ProductsCriteria();
            criteria.add(Restrictions.eq("code", productCode));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("companyId", companyId));

            product = DAOFactory.getDAOFactory().getProductsDAO().loadProductsByCriteria(criteria);
            System.out.println("Checking Product type: " + product.getProductTypeCode() + " Product Name: " + product.getName());
            if (product.getProductTypeCode().equalsIgnoreCase("C")) {

                status = true;
            } else {
                status = false;
            }
        } catch (PersistentException ex) {
            Logger.getLogger(SavingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;

    }

    @Override
    public boolean checkIfSavingsAccount(String accountNumber, int branchId, int companyId) {
        CustaccountdetailsCriteria criteria;
        boolean status = false;
        try {
            criteria = new CustaccountdetailsCriteria();
            criteria.add(Restrictions.eq("id", accountNumber));
            Custaccountdetails account = DAOFactory.getDAOFactory().getCustaccountdetailsDAO().loadAccountByCriteria(criteria);
            String productCode = account.getProduct();
            ProductsCriteria pCriteria = new ProductsCriteria();
            pCriteria.add(Restrictions.eq("code", productCode));
            pCriteria.add(Restrictions.eq("branchId", branchId));
            Products product = DAOFactory.getDAOFactory().getProductsDAO().loadProductsByCriteria(pCriteria);
            System.out.println("Product Code: " + productCode);
            System.out.println("Product Type Code: " + product.getProductTypeCode());
            if (product.getProductTypeCode().equalsIgnoreCase("S") || product.getProductTypeCode().equalsIgnoreCase("C")) {
                status = true;
            } else {
                status = false;
            }

        } catch (PersistentException ex) {
            status = false;
            Logger.getLogger(SavingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    @Override
    public int getProductIdByAccountNumber(String accountNumber, int branchId) {
        Custaccountdetails account = null;
        int productId = 0;
        try {
            CustaccountdetailsCriteria criteria = new CustaccountdetailsCriteria();
            criteria.add(Restrictions.eq("id", accountNumber));

            account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCustaccountdetailsDAO().loadAccountByCriteria(criteria);
            String productCode = account.getProduct();
            ProductsCriteria pCriteria = new ProductsCriteria();
            pCriteria.add(Restrictions.eq("code", productCode));
            pCriteria.add(Restrictions.eq("branchId", branchId));
            Products product = DAOFactory.getDAOFactory().getProductsDAO().loadProductsByCriteria(pCriteria);
            productId = product.getId();
        } catch (PersistentException ex) {
            _logger.error("getProductIdByAccountNumber(String accountNumber, int branchId, int companyId)", ex);
        }
        return productId;
    }

   

    @Override
    public List<FileUpload> listBatchForApprovalItems(int branchId, int companyId, String referenceNumber) {
        List<FileUpload> fileUloads = null;
        try {
            //byte isVerified = 1;
            byte status = 0;
            byte isProcessed = 1;
            int isApproved = 0;
            //System.out.println("status : " + status);
            FileUploadCriteria criteria = new FileUploadCriteria();
            //criteria.add(Restrictions.eq("isVerified", isVerified));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.eq("isApproved", isApproved));
            criteria.add(Restrictions.eq("isProcessed", isProcessed));
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));

            fileUloads = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);

        } catch (PersistentException ex) {
            _logger.error("listBatchForApproval", ex);
        }
        return fileUloads;
    }

    @Override
    public List<FileUpload> listBatchForApproval(int branchId, int companyId) {
        List<FileUpload> fileUloads = null;
        try {
            byte isVerified = 1;
            byte status = 1;
            byte isApproved = 0;
            //System.out.println("status : " + status);
            FileUploadCriteria criteria = new FileUploadCriteria();
            criteria.add(Restrictions.eq("isVerified", isVerified));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.eq("isApproved", isApproved));

            fileUloads = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);

        } catch (PersistentException ex) {
            _logger.error("listBatchForApproval", ex);
        }
        return fileUloads;
    }

    @Override
    public List<Savings> loadBatchForApprovalItems(int branchId, int companyId, String referenceNumber) {
        List<Savings> savings = null;
         byte isProcessed = 1;
          byte status = 0;           
         byte isApproved = 0;
        try {
            SavingsCriteria criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("isProcessed", isProcessed));
            criteria.add(Restrictions.eq("isApproved", isApproved));
            criteria.add(Restrictions.eq("status", status));
            savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);
        } catch (PersistentException ex) {
            savings = null;
            _logger.error("loadBatchForApprovalItems", ex);
        }
        return savings;
    }

    @Override
    public boolean updateBatchStatus(int branchId, int companyId, String referenceNumber, String status, String tableName, String currentUser) {
        PersistentSession session;
        boolean success = false;
        try {
            t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();

            // sessionFactory.getCurrentSession().createSQLQuery("update branch_serial set serial=serial+1 where company_id="+coyId).executeUpdate(); 
           /*
            String sqlQuery = "update " + tableName + " set is_approved = " + status + " where reference_number = "+ referenceNumber +""
                    + " and branch_id  = " + branchId +" and company_id = " + companyId +"";
            */
     
            String sqlQuery = "update " + tableName + " set is_approved = '" + status + "' , approved_by = '" + currentUser + "' where reference_number = '"+ referenceNumber +"'"
                    + " and branch_id  = '" + branchId +"' and company_id = '" + companyId +"'";
            // , approved_by = "+ currentUser + "
             _logger.info("sqlQuery TO UPDATE  :: " + sqlQuery);
            System.out.println("sqlQuery :: " + sqlQuery);
          
            session.createSQLQuery(sqlQuery).executeUpdate();
            //System.out.println(":: Query executed :: " );
            // session.createQuery("update Savings set status= 1 where id ="+Integer.valueOf(id)).executeUpdate();
            t.commit();
        } catch (PersistentException ex) {
            try {
                t.rollback();
            } catch (PersistentException ex1) {
                Logger.getLogger(SavingServiceImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            _logger.error("updateApproveStatus )", ex);
        }
        
        return success;
    }

    @Override
    public List<FileUpload> listFailedUploads(int branchId, int companyId) {
        List<FileUpload> fileUloads = null;
        try {
            byte isVerified = 0;
            byte status = 0;
            byte isApproved = 0;
            //System.out.println("status : " + status);
            FileUploadCriteria criteria = new FileUploadCriteria();
            criteria.add(Restrictions.eq("isVerified", isVerified));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.eq("isApproved", isApproved));

            fileUloads = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);

        } catch (PersistentException ex) {
            _logger.error("listFailedUploads", ex);
        }
        return fileUloads;
    }

    @Override
    @Async
    public void verifyBatchUpload(String referenceNumber, String userId) {
       PersistentSession session;
        String status = "";
        try {

            session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            // t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            String sql = "select user_id from savings  where reference_number='" + referenceNumber + "' and user_id='" + userId + "' limit 1";
            Query query = session.createSQLQuery(sql);
            // t.commit();
            if (query == null || query.list().isEmpty()) {
                //System.out.println("User is null");

                SavingsCriteria criteria = new SavingsCriteria();
                criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
                byte isProcessed = 1;
                byte isApproved = 0;
                String  approvedBy = null;
                List<Savings> savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);
                for (Savings s : savings) {
                    s.setIsProcessed(isProcessed);
                    s.setVerifierId(userId);
                    com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(s);
                    //System.out.println("Reference Number " + s.getReferenceNumber());
                }
                FileUploadCriteria fCriteria = new FileUploadCriteria();
                fCriteria.add(Restrictions.eq("referenceNumber", referenceNumber));
                FileUpload fUpload = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().loadFileUploadsByCriteria(fCriteria);
                System.out.println("Loaction :: " + fUpload.getLocation());
                fUpload.setIsVerified(isProcessed);
                fUpload.setVerifierId(userId);
                //
                fUpload.setApprovedBy(approvedBy);
                fUpload.setIsApproved(isApproved);

                 String mailBody = "	<style type=\"text/css\">"
                            + "	<!--"
                            + "	.style2 {"
                            + "		font-family: Verdana, Arial, Helvetica, sans-serif;"
                            + "		font-size: 14px;"
                            + "	}"
                            + "	-->"
                            + "	</style>"
                            + "	 <p class=\"style2\">Hello, </p>"
                            + "	<p class=\"style2\">The transactin with reference #" + referenceNumber + " is awaiting approval <br>"
                         
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                         
                            + "	</table>"
                            + "	<p class=\"style2\"><br>"
                            + "	  Thanks </p> ";
                 
                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().save(fUpload);
                //MailBean MB = HelperUtil.createMailBean();
                //MB.setSubject(SavingsDefinitions.VERIFICATION_SUBJECT);
                //MB.setToemail("logzyy@gmail.com");

               // MB.setMailBody(mailBody);
               // HelperUtil.sendMail(MB);
               // System.out.println("mailBody :: " + mailBody );   
                    //MailBean MB = HelperUtil.createMailBean();
                    MailBean MB = HelperUtil.getMailConfig();
                    MB.setSubject(SavingsDefinitions.VERIFICATION_SUBJECT);
                    MB.setToemail(userId);

                    MB.setMailBody(mailBody);

                    try {
                        //send email
                        HelperUtil.sendMail(MB);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
               // util.sendMailByUserGroup("SA5", fUpload.getBranchId(), fUpload.getCompanyId(), userId, SavingsDefinitions.VERIFICATION_SUBJECT, mailBody);
                status = "ok";
            } else {
                status = "You cannot verify this transaction";
                // System.out.println("User is exists: " + query.list().get(0).toString() + " User:" + userId);
            }



        } catch (PersistentException ex) {
            // t.rollback();
            _logger.error("verify(String referenceNumber, String userId)", ex);
        }
        System.out.println("I have finished the batch verification for reference number :: " + referenceNumber);
    }
}
