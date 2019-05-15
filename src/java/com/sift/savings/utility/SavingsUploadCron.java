/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.utility;

import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.FileUploadCriteria;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.gl.model.Account;
import com.sift.loan.utility.MailBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.orm.PersistentTransaction;

/**
 *
 * @author logzy
 */
public class SavingsUploadCron {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingsCron.class);
    PersistentTransaction t = null;

    public void readExcelData() throws PersistentException {

        String pattern = "yyyy-MM-dd";
        String trxType = "S";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date trx_date = null;
        int successCount = 0;
        int failureCount = 0;
        float processedSum = 0f;
        int fileCount = 0;
        String accountNumber = "";
        byte status = 0;
        List<SavingsError> savingsError = null;

        String narrative = "";
        Double amount = 0.0;
        String reformattedStr = "";
        String date = "";
        String errorMessage = "";
        try {

            t = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().beginTransaction();
            FileUploadCriteria criteria = new FileUploadCriteria();

            criteria.add(Restrictions.eq("status", status));


            List<FileUpload> uploads = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getFileUploadDAO().listAllFileUploadByCriteria(criteria);

            if (uploads.size() > 0) {
                for (FileUpload f : uploads) {
System.out.println("Processing: "+f.getId());
                    FileInputStream file = new FileInputStream(new File(f.getLocation()));
                    String extension = FilenameUtils.getExtension(f.getFileName());


                    if (extension.equalsIgnoreCase("xls")) {

                        HSSFWorkbook workbook = new HSSFWorkbook(file);


                        HSSFSheet sheet = workbook.getSheetAt(0);


                        Iterator<Row> rowIterator = sheet.iterator();

                        while (rowIterator.hasNext()) {
                            Row row = rowIterator.next();
                            fileCount += 1;

                            if (row.getRowNum() > 0) {

                                if (row.getCell(0) != null) {
                                    accountNumber = row.getCell(0).getStringCellValue();
                                }
                                if (row.getCell(1) != null) {
                                    narrative = row.getCell(1).getStringCellValue();
                                }
                                if (row.getCell(2) != null) {
                                    amount = row.getCell(2).getNumericCellValue();
                                }
                                if (row.getCell(3) != null) {
                                    date = row.getCell(3).getStringCellValue();

                                    reformattedStr = myFormat.format(fromUser.parse(date));
                                    trx_date = myFormat.parse(reformattedStr);
                                }

                                try {
                                    if (!EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy")) {
                                        errorMessage += " Date is Invalid";
                                    }
                                } catch (Exception ex) {
                                    errorMessage += " Date is Invalid";
                                }

                                try {
                                    if (!EasyCoopFinValidator.checkIfAccountExists(accountNumber, f.getBranchId(), f.getCompanyId())) {
                                        errorMessage += " Date is Invalid";
                                    }
                                } catch (Exception ex) {
                                    errorMessage += " Date is Invalid";
                                }
                                
                                //Date postDate = company.getPostDate();
                                if (errorMessage.length()==0) {
                                    byte trxStatus = 1;
                                    successCount += 1;
                                    Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber, f.getBranchId(), f.getCompanyId());
                                    MemberCriteria mcriteria = new MemberCriteria();

                                    mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
                                    Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
                                    Savings saving = new Savings();
                                    saving.setAccountNumber(accountNumber);
                                    saving.setAmount(amount.floatValue());
                                    saving.setBranchId(f.getBranchId());
                                    saving.setCompanyId(f.getCompanyId());
                                    saving.setDescription(narrative);
                                    saving.setMemberId(m.getId());
                                    saving.setReferenceNumber(f.getReferenceNumber());
                                    saving.setTrxDate(trx_date);
                                    saving.setUserId(f.getUserId());
                                    saving.setProductId(f.getProductId());
                                    saving.setUserId(f.getUserId());
                                    //saving.setIsProcessed(trxType);

                                    processedSum += amount.floatValue();

                                    DAOFactory.getDAOFactory().getSavingsDAO().save(saving);

                                } else {
                                    if (savingsError == null) {
                                        savingsError = new ArrayList<SavingsError>();
                                    }
                                      SavingsError sError = new SavingsError();
                                        sError.setAccountNumber(accountNumber);
                                        sError.setAmount(amount.floatValue());
                                        sError.setBranchId(f.getBranchId());
                                        sError.setCompanyId(f.getCompanyId());
                                        sError.setDescription(narrative);
                                        sError.setMemberId(new Integer(0));
                                        sError.setReferenceNumber(f.getReferenceNumber());
                                        sError.setTrxDate(trx_date);
                                        sError.setUserId(f.getUserId());
                                        sError.setProductId(f.getProductId());
                                        sError.setUserId(f.getUserId());
                                        sError.setTrxType(trxType);
                                        sError.setErrorMessage(errorMessage);

                                        savingsError.add(sError);
                                }



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
                            fileCount += 1;
                            //For each row, iterate through each columns
                            Iterator<Cell> cellIterator = row.cellIterator();
                            if (row.getRowNum() > 0) {
                                if (row.getCell(0) != null) {
                                    accountNumber = row.getCell(0).getStringCellValue();
                                }
                                if (row.getCell(1) != null) {
                                    narrative = row.getCell(1).getStringCellValue();
                                }
                                if (row.getCell(2) != null) {
                                    amount = row.getCell(2).getNumericCellValue();
                                }
                                if (row.getCell(3) != null) {
                                    date = row.getCell(3).getStringCellValue();

                                    reformattedStr = myFormat.format(fromUser.parse(date));
                                    trx_date = myFormat.parse(reformattedStr);
                                }

                                  try {
                                    if (!EasyCoopFinValidator.isThisDateValid(date, "dd-MM-yyyy")) {
                                        errorMessage += " Date is Invalid";
                                    }
                                } catch (Exception ex) {
                                    errorMessage += " Date is Invalid";
                                }

                                try {
                                    if (!EasyCoopFinValidator.checkIfAccountExists(accountNumber, f.getBranchId(), f.getCompanyId())) {
                                        errorMessage += " Date is Invalid";
                                    }
                                } catch (Exception ex) {
                                    errorMessage += " Date is Invalid";
                                }
                                
                            //add here
                                //Date postDate = company.getPostDate();
                                if (errorMessage.length()==0) {
                                    byte trxStatus = 1;
                                    successCount += 1;
                                    Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(accountNumber, f.getBranchId(), f.getCompanyId());
                                    MemberCriteria mcriteria = new MemberCriteria();

                                    mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
                                    Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
                                    Savings saving = new Savings();
                                    saving.setAccountNumber(accountNumber);
                                    saving.setAmount(amount.floatValue());
                                    saving.setBranchId(f.getBranchId());
                                    saving.setCompanyId(f.getCompanyId());
                                    saving.setDescription(narrative);
                                    saving.setMemberId(m.getId());
                                    saving.setReferenceNumber(f.getReferenceNumber());
                                    saving.setTrxDate(trx_date);
                                    saving.setUserId(f.getUserId());
                                    saving.setProductId(f.getProductId());
                                    saving.setUserId(f.getUserId());
                                    //saving.setIsProcessed(trxType);

                                    processedSum += amount.floatValue();

                                    DAOFactory.getDAOFactory().getSavingsDAO().save(saving);

                                } else {
                                    if (savingsError == null) {
                                        savingsError = new ArrayList<SavingsError>();
                                    }
                                      SavingsError sError = new SavingsError();
                                        sError.setAccountNumber(accountNumber);
                                        sError.setAmount(amount.floatValue());
                                        sError.setBranchId(f.getBranchId());
                                        sError.setCompanyId(f.getCompanyId());
                                        sError.setDescription(narrative);
                                        sError.setMemberId(new Integer(0));
                                        sError.setReferenceNumber(f.getReferenceNumber());
                                        sError.setTrxDate(trx_date);
                                        sError.setUserId(f.getUserId());
                                        sError.setProductId(f.getProductId());
                                        sError.setUserId(f.getUserId());
                                        sError.setTrxType(trxType);
                                        sError.setErrorMessage(errorMessage);

                                        savingsError.add(sError);
                                        System.out.println("Error: "+errorMessage);
                                }



                            }

                        }
                    }

                    if (savingsError != null) {
                        failureCount = savingsError.size();
                    }

                    //com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().updateFileUpload(f.getId(), successCount, failureCount, processedSum, fileCount);
                    file.close();
                    if (savingsError != null) {
                        failureCount = savingsError.size();

                        System.out.println("Failure Count:" + failureCount + " Success Count " + successCount + " Processed Count: " + processedSum + " File count: " + fileCount);
                        for (SavingsError sE : savingsError) {
                            com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsErrorDAO().save(sE);
                        }

                    }

                    String mailBody = "	<style type=\"text/css\">"
                            + "	<!--"
                            + "	.style2 {"
                            + "		font-family: Verdana, Arial, Helvetica, sans-serif;"
                            + "		font-size: 14px;"
                            + "	}"
                            + "	-->"
                            + "	</style>"
                            + "	 <p class=\"style2\">Hello, </p>"
                            + "	<p class=\"style2\">Your batch upload with reference #" + f.getReferenceNumber() + " has been successfully processed <br>"
                            + "   <strong>Below are the details: </strong></p>"
                            + "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
                            + "	  <tr>"
                            + "	    <td width=\"50%\"><span class=\"style2\">Failure Count:</span></td>"
                            + "	    <td width=\"50%\"><span class=\"style2\">" + failureCount + "</span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Success Count:</span></td>"
                            + "	    <td><span class=\"style2\">" + successCount + " </span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Processed Sum:</span></td>"
                            + "	    <td><span class=\"style2\">" + processedSum + " </span></td>"
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                            + "	</table>"
                            + "	<p class=\"style2\"><br>"
                            + "	  Thanks </p> ";
                    MailBean MB = HelperUtil.createMailBean();
                    MB.setSubject(SavingsDefinitions.BULK_UPLOAD_PROCESSED_SUBJECT);
                    MB.setToemail(HelperUtil.getUserEmail(f.getUserId()));

                    MB.setMailBody(mailBody);

                    //send email
                    // HelperUtil.sendMail(MB);
                }




                t.commit();
            }
        } catch (PersistentException e) {
            t.rollback();
            _logger.error("SavingsCron", e);
        } catch (FileNotFoundException e) {
            t.rollback();
            _logger.error("SavingsCron", e);
        } catch (IOException e) {
            t.rollback();
            _logger.error("SavingsCron", e);
        } catch (ParseException e) {
            //t.rollback();
            _logger.error("SavingsCron", e);
        }
        /**
         * catch (Exception ex) { t.rollback(); _logger.error("SavingsCron",
         * ex); }*
         */
    }
}
