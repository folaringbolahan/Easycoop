/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.dao;

import com.sift.gl.model.FileUploadErrors;
import com.sift.gl.model.FileUploadItems;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
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
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author Olakunle Awotunbo
 */
@Repository("fileUploadErrorsDao")
@Transactional
public class FileUploadErrorsDaoImpl implements FileUploadErrorsDao{
    @Autowired
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory(){
        return this.sessionFactory;
    }
    @Override
    public void addFileUploadError(FileUploadErrors item) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(item);
        //System.out.println("File Uplaod Item Error Save With name : " + item.getMemberName());
    }

    @Override
    public void addFileUploadErrors(List<FileUploadErrors> items) {
        if(items!=null && items.size()>0){
		 for(FileUploadErrors item: items){
			 sessionFactory.getCurrentSession().save(item);
		 }
	 }
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileUploadErrors> listFileUploadErrors(int companyId, int branchId, String batchId) {
         Criteria criteria = sessionFactory.getCurrentSession().createCriteria(FileUploadErrors.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
         criteria.add(Restrictions.eq("branchId",branchId));
	 criteria.add(Restrictions.eq("batchId",batchId));
	 
	 return  (List<FileUploadErrors>)criteria.list();
    }

    @Override
    public FileUploadErrors getFileUploadError(int id) {
        return (FileUploadErrors) sessionFactory.getCurrentSession().get(FileUploadErrors.class, id);
    }

    @Override
    public void writeErrorToExcel(List<FileUploadErrors> memAcctList, HttpServletResponse response) {
         //boolean 
        String fileName = "FAILED MEMBER RECORD.xls";
        //int dbranch = user.getCurruser().getBranch();
        //int dcompany = user.getCurruser().getCompanyid();

        //String duser = user.getCurruser().getUserId();

        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            try {
                throw new Exception("invalid file name, should be xls or xlsx");
            } catch (Exception ex) {
                Logger.getLogger(FileUploadErrorsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Sheet sheet = workbook.createSheet("Member Error List");

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

        Iterator<FileUploadErrors> iterator = memAcctList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("MEMBER NAME");
        headercell0.setCellStyle(style);

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("MEMBER NO");
        headercell1.setCellStyle(style);

        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("ERROR(S)");
        headercell2.setCellStyle(style);

       

        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            FileUploadErrors obj = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getMemberName());

            Cell cell1 = row.createCell(columnIndex++);
            cell1.setCellValue(obj.getMemberNo());

            Cell cell2 = row.createCell(columnIndex++);
            cell2.setCellValue(obj.getErrorMessage());

        }

        //lets write the excel data to file now
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this.getReportLocation() + fileName);
            workbook.write(fos);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUploadErrorsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUploadErrorsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FileUploadErrorsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}
