/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.GendataService;
import com.sift.gl.GenericsiftException;
import com.sift.gl.model.Generictableviewbean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
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
import org.springframework.util.FileCopyUtils;


/**
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
public class Generictableviewdao implements Generictableviewinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    String headercols = "";
    String title = "";
    List<String> colhdrs = new ArrayList<String>();
    List<String> vcoltblnames = new ArrayList<String>();
    public Generictableviewdao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
    public void addTitle(String description) {
     title = description; 
    }
    public void setHeader(List<String> colhdrs) {
     this.colhdrs = colhdrs;
    }
    public void setTableflds(List<String> vcoltblnames) {
     this.vcoltblnames = vcoltblnames;
    }
    
    @Override 
    public List<List<String>> buildtablebody(String vSqlStmt) throws GenericsiftException {
        List allrecDetails = new ArrayList();
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs =  null;
        try {
         rs =  dbobj.retrieveRecset(vSqlStmt);  
         while (rs.next()) {
              List allrowrecDetails = new ArrayList<String>();
              for (int i = 0; i < this.colhdrs.size();i++) {
                allrowrecDetails.add(rs.getString(vcoltblnames.get(i).toString()));
                //System.out.println("debug dynamic table count : " + i + " " + rs.getString(vcoltblnames.get(i).toString()));
              } 
          allrecDetails.add(allrowrecDetails);
          }  
        } catch (SQLException ex) {
          throw new GenericsiftException(ex.getMessage());
        } 
        finally {
             if (rs != null) {
              try {
                rs.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
        //dbobj = null; 
        return allrecDetails;
    }

   @Override
    //public void writeListToExcel(List<FileUploadErrors> memAcctList Generictableviewbean, HttpServletResponse response) {
    public void writeListToExcel(Generictableviewbean dlistbean, HttpServletResponse response,String filename)  throws GenericsiftException{
       //boolean 
       filename = filename + ".xls";
       Workbook workbook = null;
        workbook = new HSSFWorkbook();
       

        Sheet sheet = workbook.createSheet("Records");

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

        Iterator<String> hditerator = dlistbean.getHeader().iterator();
        Iterator<List<String>> bditerator = dlistbean.getBody().iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

         while (hditerator.hasNext()) {
            String obj = hditerator.next();
            Cell headercell0 = headerrow.createCell(columnIndex++);
            headercell0.setCellValue(obj);
            headercell0.setCellStyle(style);
         }
        
        rowIndex = 1;

        while (bditerator.hasNext()) {
            Row row = sheet.createRow(rowIndex++);
            
            Iterator<String> innbditerator = bditerator.next().iterator();
            columnIndex = 0;
            while (innbditerator.hasNext()) {
              String objinn = innbditerator.next();
              Cell bdcell0 = row.createCell(columnIndex++);
              bdcell0.setCellValue(objinn);
            }
         }

        
        //lets write the excel data to file now
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this.getReportLocation() + filename);
            workbook.write(fos);
        } catch (FileNotFoundException ex) {
            throw new GenericsiftException(ex.getMessage());
        } catch (IOException ex) {
            throw new GenericsiftException(ex.getMessage());
        }

        File f = new File(this.getReportLocation() + filename);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",
                "attachment; filename=" + filename);
        try {
            //let browser to download
            FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
            fos.close();
        } catch (IOException ex) {
            throw new GenericsiftException(ex.getMessage());
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
