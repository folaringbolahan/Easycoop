/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.dao;

import com.sift.gl.dao.*;
import com.sift.gl.AuditlogService;
import com.sift.gl.FileuploadException;
import com.sift.gl.GendataService;
import com.sift.gl.GenericsiftException;
import com.sift.gl.GetSetting;
import com.sift.gl.NotificationService;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.model.Generictableviewbean;
import com.sift.hp.model.Hprepymtschddetails;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.sql.SQLException;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

/** dao class for persistence and data operations on HP repayment file uploads
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
@Component
public class HPFileuploaddao implements HPFileuploadinter {
    //Connection con;
    //GendataService dbobj = new GendataService();
    ///@Autowired
    //private NotificationService notificationService;
    int noofrecs = 0;
    int noofrecsok = 0;
    int noofrecsfailed = 0;
    Double amtrecsok = 0.0;
    
    /**
     *
     */
    public HPFileuploaddao () {
      
    }
   
    /**
     *
     * @param fileUpload
     * @throws FileuploadException
     */
    public void removeFile(FileUpload fileUpload) throws FileuploadException {
     String sqlStatement = "delete from fileuploadgl where reference_number = '" +
            fileUpload.getBatchId() + "' and company_id = " + fileUpload.getCompanyId() + " and branch_id = " + fileUpload.getBranchId() ;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      } catch (Exception ex) {
           throw new FileuploadException("Transaction failed: " + ex.getMessage());
      }
      finally {
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
    }
    
  
    /**
     *
     * @param acno
     * @param acstr
     * @param acgrp
     * @param companyid
     * @param branchid
     * @return
     * @throws FileuploadException
     */
    public String validateacrow(String acno,String acstr,int acgrp,int companyid,int branchid) throws FileuploadException {
    String dmess ="";
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM accounts where accountno = '" + acno + "' and companyid = " + companyid + " and branch = " + branchid);
         while (rs.next()) {  
             dmess = " Account no already exist<br>"; 
          } 
         String msql ="SELECT * FROM accountgroups where Acgroupid = " + acgrp + " and companyid = " + companyid;
         rs =  dbobj.retrieveRecset(msql);
         if (dbobj.retrieveRecset(msql).first() == false)
         {
           dmess = " Account group does not exist<br>";
         }
         
         msql = "SELECT * FROM accountstructures where structurecode = '" + acstr + "' and companyid = " + companyid ;
         rs =  dbobj.retrieveRecset(msql);
         if (dbobj.retrieveRecset(msql).first() == false)
         {
           dmess = " Account structure does not exist<br>";
         }
         else
         {
           while (rs.next()) {  
             if (acno.trim().length() != getAcSeglen(acno,acstr,companyid)) 
             {    
              dmess = " Account no does not match Account structure<br>";
             } 
          }
         }
         
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
        return dmess;
   }
   
    /**
     *
     * @param acno
     * @param companyid
     * @param branchid
     * @return
     * @throws FileuploadException
     */
    public String validateacrow(String acno,int companyid,int branchid) throws FileuploadException {
    String dmess ="";
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        try {
         String msql ="SELECT * FROM accounts where accountno = '" + acno + "' and companyid = " + companyid + " and branch = " + branchid + " and active = 1";
         rs =  dbobj.retrieveRecset(msql);
         if (dbobj.retrieveRecset(msql).first() == false)
         {
           dmess = " Account no does not exist or is inactive<br>";
         }
         
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
        return dmess;
   }
    
    /**
     *
     * @param vAcno
     * @param vAcStructcode
     * @param companyid
     * @return
     */
    public int getAcSeglen(String vAcno,String vAcStructcode,int companyid) {
      String mySQLString;
      mySQLString = "select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg1 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg2 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg3 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg4 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg5 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg6 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg7 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg8 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg9 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg10 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid   ;
      ResultSet agRecSet;
      GendataService dbobj = new GendataService();
      dbobj.inimkcon();
      System.out.println(mySQLString);
      agRecSet = dbobj.retrieveRecset(mySQLString);
      String vAcSeg = "";
      int asum=0;
      int i = vAcno.trim().length();
      int j= 0;
      int indseg;
      int delimlen = 0;
      java.util.List AcsegL =  new ArrayList();
      try {
       while (agRecSet.next()) {
        asum= agRecSet.getInt("length");
        delimlen = 0;
        if ((agRecSet.getString("delimiter")!=null)&& (agRecSet.getString("delimiter").isEmpty()==false)) {
           delimlen = agRecSet.getString("delimiter").trim().length();
           asum=asum+delimlen;
        }
        //AcsegL.add(vAcno.substring(j, j+(asum)));
        j=j+asum;
       }
       j = j - delimlen;
       } catch (SQLException ex) {
          System.out.println(ex.getMessage());
      } 
        catch (NullPointerException nex) {
          System.out.println(nex.getMessage());
      }  
       finally {
          try{  
           if (agRecSet!=null)
           {agRecSet.close();}
          } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          if (dbobj != null) {
           dbobj.closecon();
          }
          dbobj = null;
        } 
       // System.out.println("the acno len " + j);
        return j;
    }
   
   
    /**
     *
     * @param vAcno
     * @param vAcStructcode
     * @param companyid
     * @return
     * @throws FileuploadException
     */
    public java.util.List getAcSeg(String vAcno,String vAcStructcode,int companyid) throws FileuploadException{
      String mySQLString;
      mySQLString = "select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg1 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg2 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg3 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg4 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg5 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg6 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg7 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg8 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg9 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid + 
                    " union select a.structurecode,a.delimiter,b.segmentid,b.length,b.name from accountstructures a inner join accountsegments b on a.companyid = b.companyid and a.seg10 = b.segmentid where structurecode = '" + vAcStructcode + "' and a.companyid = " + companyid   ;
      ResultSet agRecSet;
      GendataService dbobj = new GendataService();
      dbobj.inimkcon();
      agRecSet = dbobj.retrieveRecset(mySQLString);
      String vAcSeg = "";
      int asum=0;
      int i = vAcno.trim().length();
      int j= 0;
      int indseg;
      int delimlen = 0;
      java.util.List AcsegL =  new ArrayList();
      try {
       while (agRecSet.next()) {
        asum= agRecSet.getInt("length");
        delimlen = 0;
        if ((agRecSet.getString("delimiter")!=null)&& (agRecSet.getString("delimiter").isEmpty()==false)) {
           delimlen = agRecSet.getString("delimiter").trim().length();
           asum=asum+delimlen;
        }
        AcsegL.add(vAcno.substring(j, j+(asum)));
        j=j+asum;
       }
       j = j - delimlen;
       } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
      } 
        
       finally {
          try{  
           if (agRecSet!=null)
           {agRecSet.close();}
          } catch (SQLException ex) {
                ex.printStackTrace();
          } 
          if (dbobj != null) {
           dbobj.closecon();
          }
        } 
        return AcsegL;
    }
    
     /**
     *
     * @param eventid
     * @param eventactioncode
     * @param ipaddr
     * @param username
     * @param timezone
     * @param actionitem
     * @param result
     * @param branch
     * @param company
     */
    public void auditlogcall( int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
       Activitylog ent;
       ent = new Activitylog();
       String theerrmess;        
       ent.setBranchid(branch); 
       //ent.setEvent(1);
       //ent.setAction("GLGAC");
       ent.setEvent(eventid);
       ent.setAction(eventactioncode);
       ent.setCompanyid(company); 
       ent.setUsername(username);
       ent.setTimezone(timezone); 
       ent.setDescription(""); 
       ent.setIpaddress(ipaddr);
       ent.setActionItem(actionitem);
       ent.setActionResult(result); 
        AuditlogService cliserv = new AuditlogService();
       String respo = cliserv.create(ent);
       System.out.println(respo);
       //theerrmess= cliserv.gettheerrmess();
       //System.out.println(theerrmess);
    }
    
     /**
     *
     * @return
     */
    public String getSummaryreport()  {
        String dmess ="";
        dmess = "Number of Imported Records: " + noofrecs + ". No. of Records that are valid: " + noofrecsok + ". No. of records that failed validation: " + noofrecsfailed; 
        return dmess;
   }
     
    /**
     *
     * @param batchid
     * @param companyid
     * @param branchid
     * @return
     * @throws FileuploadException
     */
    public String getDetailederrreport(String batchid,int companyid,int branchid) throws FileuploadException {
    String dmess ="";
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        try {
         rs =  dbobj.retrieveRecset("SELECT rownum,errormessage FROM accountsimportpending where Haserrors = 1 and reference = '" + batchid + "' and companyid = " + companyid + " and branchid = " + branchid);
         while (rs.next()) {  
             dmess = dmess + "<strong>Row " + rs.getInt("rownum") + "</strong> : " + rs.getString("errormessage"); 
         }
         dmess = dmess.trim();
         if (dmess.length()>0) {
             dmess = "Errors; " + dmess;
         }
         
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
        return dmess;
   }  
   // 00
     /** add new HP upload file
     *
     * @param fileUpload
     * @param companyid
     * @param branchid
     * @param currency
     * @param dateopened
     * @param ipaddr
     * @param username
     * @param timezone
     * @throws FileuploadException
     */
    @Override
    public void addtxnFile(FileUpload fileUpload,int companyid,int branchid,String currency, java.util.Date dateopened,String ipaddr, String username,String timezone ) throws FileuploadException {
    
    try {
         ///InsertFile(fileUpload);
         InsertaddFile(fileUpload,companyid, branchid);
         //check txn sum and no of records against value
         //importfilefrmloc(fileUpload.getLocation(),fileUpload.getBatchId(),companyid,branchid, currency,dateopened,ipaddr,username,timezone);
          
      } catch (Exception ex) {
           throw new FileuploadException("File Upload Transaction failed: " + ex.getMessage());
      }
   }
     
     
     private void InsertaddFile(FileUpload fileupload,int companyid,int branchid) throws Exception {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();   
     int coy = Integer.parseInt(fileupload.getCompanyId());
      int br = Integer.parseInt(fileupload.getBranchId());
      PreparedStatement psmt = null;
      try {
      psmt = dbobj.con.prepareStatement("insert into fileuploadhprpy (company_id,branch_id,file_name,location,status,reference_number,user_id,is_verified,product_id,processed_date,total_records,file_sum) values (?,?,?,?,?,?,?,?,?,?,?,?)"); 
      psmt.setInt(1, coy);
      psmt.setInt(2, br);
      psmt.setString(3, fileupload.getUploadFilename());
      psmt.setString(4, fileupload.getLocation());
      psmt.setInt(5, 0);
      psmt.setString(6, fileupload.getBatchId());
      psmt.setString(7, fileupload.getUploadedBy());
      psmt.setInt(8, 0);
      psmt.setInt(9, 0);
      java.sql.Date sqlDate = new java.sql.Date(fileupload.getUploadDate().getTime());
      java.sql.Timestamp sqlTime = new java.sql.Timestamp(fileupload.getUploadDate().getTime());
      psmt.setTimestamp(10, sqlTime);
      psmt.setInt(11, fileupload.getTotalRecords());
      psmt.setBigDecimal(12,fileupload.getFilesum());
      psmt.executeUpdate();
     
      } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
      } 
      finally {
             if (psmt != null) {
              try {
                psmt.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
      dbobj = null;  
    }
     
     /**
     *
     * @param filename
     * @param ref
     * @param companyid
     * @param branchid
     * @param currency
     * @param dateopened
     * @param ipaddr
     * @param username
     * @param timezone
     * @param mailsubject
     * @param notificationService
     * @throws FileuploadException
     */
    public void importtxnfilefrmloc(String filename,String ref,int companyid,int branchid,String currency, java.util.Date dateopened,String ipaddr, String username,String timezone, String mailsubject,NotificationService notificationService) throws FileuploadException {
        GendataService dbobj = new GendataService();
        dbobj.inimkcon(); 
         try{
            //Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream(filename);
            //create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;
            if(filename.toLowerCase().endsWith("xlsx")){
                workbook = new XSSFWorkbook(fis);
            }else if(filename.toLowerCase().endsWith("xls")){
                workbook = new HSSFWorkbook(fis);
            }
             //Get the number of sheets in the xlsx file
            int numberOfSheets = workbook.getNumberOfSheets();
            numberOfSheets=1;
            boolean  errorsexist = false;
            noofrecs = 0;
            noofrecsok = 0;
            noofrecsfailed = 0;
            amtrecsok = 0.0;
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            //loop through each of the sheets
            for(int i=0; i < numberOfSheets; i++){                 
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);
                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                int rowid = 0;
                while(rowIterator.hasNext()){
                        
                	String acno="";
                	String refid="";
                	int acgrp=0;
                        String amtstr ="";
                        String penamtstr ="";
                	String ddate="";
                	int cntrlac=99;
                        String cntrlacstr="";
                        String cntrlacnostr="";
                	String baltype="";
                	String uploadError="N";
                	String validationMsg="";
                        int haserror = 0;
                        Double amt = 0.0;
                        Double penamt = 0.0;
                    //Get the row object
                    Row row = rowIterator.next();
                    //Every row has columns, get the column iterator and iterate over them
                    if (row.getRowNum() > 0) {
                        rowid = rowid + 1;
                    Iterator<Cell> cellIterator = row.cellIterator();
                    String dnullmes = "";
                    while(cellIterator.hasNext()){
                            //Get the Cell object
                           dnullmes = "";
                            Cell cell = cellIterator.next();
                             
                             try {
                                refid = row.getCell(0).getStringCellValue();
                              } catch (NullPointerException npex) { dnullmes= " Missing Reference Id:<br>";} 
                              try {
                                acno = row.getCell(1).getStringCellValue();
                              } catch (NullPointerException npex) { dnullmes= " Missing Ac no:<br>";} 
                              try {  
                                amtstr = String.valueOf(row.getCell(2).getNumericCellValue());
                               } catch (NullPointerException npex) { dnullmes= " Missing Repayment Amount:<br>";} 
                              try {  
                                ddate = row.getCell(3).getStringCellValue();
                               } catch (NullPointerException npex) { dnullmes= " Missing Date:<br>";} 
                           //   try {  
                           //     penamtstr = String.valueOf(row.getCell(4).getNumericCellValue());
                            //   } catch (NullPointerException npex) { dnullmes= " Missing Penalty Amount:<br>";} 
                              
                              
                            
                    }//end of cell iterator
                        validationMsg += dnullmes;
                        if ((acno.isEmpty() == true)) {
                            validationMsg += " Missing Ac no:<br>";
                        }
                        ///check account no does not exists
                       if ((refid.isEmpty() == true)) {
                            validationMsg += " Missing Reference:<br>";
                        }
                        try {
                            Double da = Double.parseDouble(amtstr);
                            amt = da.doubleValue();
                            BigDecimal bgdnewtotvalue = new BigDecimal(amt);
                            bgdnewtotvalue = bgdnewtotvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
                            amt=bgdnewtotvalue.doubleValue();
                            amtstr = String.valueOf(amt);
                        } catch (NumberFormatException ex) {
                            validationMsg += " Invalid Amount:should be Numeric<br>";
                        }
                     /*   try {
                            Double da = Double.parseDouble(penamtstr);
                            penamt = da.doubleValue();
                            penamtstr = String.valueOf(amt);
                        } catch (NumberFormatException ex) {
                            validationMsg += " Invalid Penalty Amount:should be Numeric<br>";
                        } */
                        ///check account group exists

                        if ((ddate.isEmpty() == true)) {
                            validationMsg += " Missing Date:<br>";
                        }
                        if ((ddate.isEmpty() == false)) {
                          try {
                             formatter1.parse(ddate);
                          } catch(ParseException pEx ) 
                          {   
                              validationMsg += " Wrong Date format(expected- dd-MM-yyyy):<br>";
                          } catch( NullPointerException nullEx ) {
                              validationMsg += " Wrong Date format(expected- dd-MM-yyyy):<br>";
                          }
                       }
                        //check that refid and accountno tally with schedule pending and amount is >=same as schedule                            
                        //use date field to determine repayment date
                    //
                        //check if penalty should apply
                    
                    if ((acno.isEmpty()==false)) {
                       String msg=validateacrow(acno,companyid,branchid);
                        if (msg.isEmpty()==false) {
                          validationMsg+=msg;
                        }
                     } 
                    
                    if ((ddate.isEmpty()==false)&&((refid.isEmpty() == false))&&((acno.isEmpty()==false))) {
                       String msg=validateloandetails(refid,acno,ddate,amt,companyid,branchid);
                        if (msg.isEmpty()==false) {
                          validationMsg+=msg;
                        }
                     }   
                    
                    if(validationMsg.trim().length()>0){
                        validationMsg = validationMsg + " - <strong>Row " + rowid + "</strong><br>";
                       uploadError="Y";
                       haserror = 1;
                       errorsexist = true;
                       noofrecsfailed = noofrecsfailed + 1;
                    }
                    ////// insert into database table accountsimportpending the row
                    Insertaccountstxnimportpending(dbobj,acno,refid,ddate,amt,penamt,branchid,companyid,ref,rowid,haserror,validationMsg);
                    ////// if row ok insert into accounts table
                    
                    if (haserror==0) {
                        noofrecsok = noofrecsok + 1; 
                        amtrecsok = amtrecsok + amt;
                    }
                    
                    }
                    uploadError="N";
                    validationMsg="";
                 }//end of rows iterator                
               noofrecs = rowid;                  
            }//end of sheets for loop
            //////update table fileuploadacttxn - failed_count,	success_count,processed_sum,status=-1,when verified = 1
            //close file input stream
            fis.close(); 
            updatefileuploadtbl(ref,branchid,companyid,noofrecsfailed,noofrecsok,amtrecsok);
            if (noofrecsok>0) {
                GetSetting sendnoticeset = new GetSetting();
                String sendnotice = sendnoticeset.GetSettingvalue("ENABLEGLEMAILNOTIF", branchid, companyid);
                if (sendnotice.equalsIgnoreCase("ON") == true) {
                    //NotificationService ntc = new NotificationService();
                    Map model = new HashMap();
                    model.put("referenceid", ref);
                    notificationService.createflowemailnotice("HP5", mailsubject, "hpreptxnsuptoapprvmail.ftl", branchid, companyid, model);
                ///ntc.createflowemailnotice("GL15", mailsubject, "glacctxnsuptoapprvmail.ftl", branchid, companyid, model);
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
        
        }catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
    }
     
     
     /**
     *
     * @param refid
     * @param acno
     * @param ddate
     * @param amt
     * @param companyid
     * @param branchid
     * @return
     * @throws FileuploadException
     */
    public String validateloandetails(String refid,String acno,String ddate,Double amt,int companyid,int branchid) throws FileuploadException {
    String dmess ="";
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        SimpleDateFormat formatter0 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateobj = null;
        String datesql = null;
        try {
             dateobj = formatter0.parse(ddate);
         } catch (ParseException pEx) {
             datesql = "1950-01-01";
         } catch (NullPointerException nullEx) {
             datesql = "1950-01-01";
         }
        try {
          datesql = formatter1.format(dateobj);

        } catch( NullPointerException nullEx ) {   }
        
        try {
         String msql ="SELECT * FROM qrynexthprepaymtscdl where refid = '" + refid + "' and accountno = '" + acno + "' and rpymtdate = {d '" + datesql + "'} and instalment = " + amt + " and companyid = " + companyid + " and branchid = " + branchid ;
         rs =  dbobj.retrieveRecset(msql);
         if (dbobj.retrieveRecset(msql).first() == false)
         {
           dmess = " Account no does tally with reference or invalid repayment date/amount<br>";
         }
         
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
        return dmess;
   }
     
      private void Insertaccountstxnimportpending(GendataService dbobj,String AccountNo,String hprefid,String ddate,Double Amount,Double penamt,int Branchid,int Companyid,String  Reference,int Rownum,int Haserrors,String Errormessage) throws Exception {
     //GendataService dbobj = new GendataService();
     
     PreparedStatement psmt = null;
    try{
      psmt = dbobj.con.prepareStatement("insert into hpreptxnimportpending (AccountNo, hprefid, repaymentdate, Amount, Penalty, Branchid, Companyid, Reference, Rownum, Haserrors, Errormessage) values (?,?,?,?,?,?,?,?,?,?,?)"); 
      psmt.setString(1, AccountNo);
      psmt.setString(2, hprefid);
      psmt.setString(3, ddate);
      psmt.setDouble(4, Amount);
      psmt.setDouble(5, penamt);
      psmt.setInt(6, Branchid);
      psmt.setInt(7, Companyid);
      psmt.setString(8, Reference);
      psmt.setInt(9, Rownum);
      psmt.setInt(10, Haserrors);
      psmt.setString(11, Errormessage);
      psmt.executeUpdate();
     
      } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
      } 
      finally {
             if (psmt != null) {
              try {
                psmt.close();
              } catch (Exception ignore) { }
             }
            
        }
      /*finally {
             if (psmt != null) {
              try {
                psmt.close();
              } catch (Exception ignore) { }
             }
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
        }
      */
      
      /*
      if (dbobj != null) {
           dbobj.closecon();
      }
      */
      
      
      
    ///  dbobj = null;  
    }
      
    /**
     *
     * @param batchid
     * @param companyid
     * @param branchid
     * @param currency
     * @param dateopened
     * @param ipaddr
     * @param username
     * @param timezone
     * @param mailsubject
     * @param notificationService
     * @throws FileuploadException
     */
    @Override
    public void addtxnFilecontents(String batchid,int companyid,int branchid,String currency, java.util.Date dateopened,String ipaddr, String username,String timezone,String mailsubject,NotificationService notificationService) throws FileuploadException {
    
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM fileuploadhprpy where reference_number = '" + batchid + "' and company_id = " + companyid + " and branch_id = " + branchid);
         while (rs.next()) {  
             loc = rs.getString("location"); 
             importtxnfilefrmloc(loc,batchid,companyid,branchid, currency,dateopened,ipaddr,username,timezone,mailsubject,notificationService);
          }  
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
   }
    
    private void updatefileuploadtbl(String ref,int branchid,int companyid,int noofrecsfailed,int noofrecsok,Double amtrecsok)  throws FileuploadException  {
     String sqlStatement = "update fileuploadhprpy set status = -1,failed_count = " + noofrecsfailed + " , success_count = " + noofrecsok + " ,processed_sum = " + amtrecsok + " where reference_number = '" +
            ref + "' and company_id = " + companyid + " and branch_id = " + branchid ;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new FileuploadException("Transaction failed: " + ex.getMessage());
      }
      finally {
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
      }
    }
    
    /**
     *
     * @param companyid
     * @param branchid
     * @param userid
     * @return
     * @throws FileuploadException
     */
    @Override
    public List<FileUploadBean> getAccttxns4apprv(int companyid,int branchid,String userid) throws FileuploadException {
    List allvaccounttxnsDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        if (canapprove.equalsIgnoreCase("YES")==false) {
           addsql = " and user_id != '" + userid + "'"; 
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM fileuploadhprpy where company_id = " + companyid + " and branch_id = " + branchid + addsql + " and (is_verified = 0 and is_rejected != 1) order by processed_date desc");
         while (rs.next()) {  
             FileUploadBean vaccounttxnsDetails = new FileUploadBean();
             i = i + 1;
             vaccounttxnsDetails.setId(i);
             vaccounttxnsDetails.setBatchId(rs.getString("reference_number")); 
             //vaccounttxnsDetails.setAccountno(rs.getString("accountno"));
             vaccounttxnsDetails.setUploadFilename(rs.getString("file_name"));
             vaccounttxnsDetails.setUploadedBy(rs.getString("user_id"));
             //vaccounttxnsDetails.setFilesum(rs.getDouble("file_sum"));
             vaccounttxnsDetails.setFilesum(rs.getBigDecimal("file_sum"));
             vaccounttxnsDetails.setProcessedsum(rs.getDouble("processed_sum"));
             vaccounttxnsDetails.setFailedCount(rs.getInt("failed_count"));
             vaccounttxnsDetails.setSuccessCount(rs.getInt("success_count"));
             vaccounttxnsDetails.setTotalRecords(rs.getInt("total_records"));
             //txn_date=formatter.format(rs.getDate("txn_date"));
             //vaccounttxnsDetails.setTxnDatestr(txn_date);
             vaccounttxnsDetails.setUploadDate(rs.getDate("processed_date"));
             //vaccounttxnsDetails.setTxnDate(rs.getDate("txn_date"));
             allvaccounttxnsDetails.add(vaccounttxnsDetails);
          }  
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
        
       return allvaccounttxnsDetails;
    }
    
    /**
     *
     * @param companyid
     * @param branchid
     * @param userid
     * @return
     * @throws FileuploadException
     */
    @Override
    public List<FileUploadBean> getAccttxns4view(int companyid,int branchid,String userid) throws FileuploadException {
    List allvaccounttxnsDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM fileuploadhprpy where company_id = " + companyid + " and branch_id = " + branchid + addsql + " and (is_verified = 0 and is_rejected != 1) order by processed_date desc");
         while (rs.next()) {  
             FileUploadBean vaccounttxnsDetails = new FileUploadBean();
             i = i + 1;
             vaccounttxnsDetails.setId(i);
             vaccounttxnsDetails.setBatchId(rs.getString("reference_number")); 
             //vaccounttxnsDetails.setAccountno(rs.getString("accountno"));
             vaccounttxnsDetails.setUploadFilename(rs.getString("file_name"));
             vaccounttxnsDetails.setUploadedBy(rs.getString("user_id"));
             vaccounttxnsDetails.setFilesum(rs.getBigDecimal("file_sum"));
             vaccounttxnsDetails.setProcessedsum(rs.getDouble("processed_sum"));
             vaccounttxnsDetails.setFailedCount(rs.getInt("failed_count"));
             vaccounttxnsDetails.setSuccessCount(rs.getInt("success_count"));
             vaccounttxnsDetails.setTotalRecords(rs.getInt("total_records"));
             //txn_date=formatter.format(rs.getDate("txn_date"));
             //vaccounttxnsDetails.setTxnDatestr(txn_date);
             vaccounttxnsDetails.setUploadDate(rs.getDate("processed_date"));
             //vaccounttxnsDetails.setTxnDate(rs.getDate("txn_date"));
             allvaccounttxnsDetails.add(vaccounttxnsDetails);
          }  
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
        
       return allvaccounttxnsDetails;
    }
    
    /**
     *
     * @param batchid
     * @param companyid
     * @param branchid
     * @throws FileuploadException
     */
    public void rejectFile(String batchid,int companyid,int branchid) throws FileuploadException {
     String sqlStatement = "update fileuploadhprpy set is_rejected = 1 where reference_number = '" +
            batchid + "' and company_id = " + companyid + " and branch_id = " + branchid ;
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     try {
     dbobj.updateTable(sqlStatement);
      if (dbobj != null) {
           dbobj.closecon();
      }
      dbobj = null;  
      } catch (Exception ex) {
           throw new FileuploadException("Transaction failed: " + ex.getMessage());
      }
      finally {
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
       }
    }
    
    /**
     *
     * @param companyid
     * @param branchid
     * @param userid
     * @return
     * @throws FileuploadException
     */
    @Override
    public List<Hprepymtschddetails> getAccttxns4dwn(int companyid,int branchid,String userid) throws FileuploadException {
    List allvaccounttxnsDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("SELECT refid,accountno,0,rpymtdate from qrynexthprepaymtscdl where companyid = " + companyid + " and branchid = " + branchid );
         while (rs.next()) {  
             Hprepymtschddetails vaccounttxnsDetails = new Hprepymtschddetails();
             i = i + 1;
             vaccounttxnsDetails.setId(i);
             vaccounttxnsDetails.setRefid(rs.getString("refid")); 
             vaccounttxnsDetails.setAccountNo(rs.getString("accountno"));
             vaccounttxnsDetails.setInstalment(0.0);
             vaccounttxnsDetails.setRpymtdate(rs.getDate("rpymtdate"));
             
             allvaccounttxnsDetails.add(vaccounttxnsDetails);
          }  
        } catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
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
        
       return allvaccounttxnsDetails;
    }
    
    
    /**
     *
     * @param dlistbean
     * @param response
     * @param filename
     * @throws GenericsiftException
     */
    public void writeListToExcel(List<Hprepymtschddetails> dlistbean, HttpServletResponse response,String filename)  throws GenericsiftException{
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

       // Iterator<String> hditerator = dlistbean.getHeader().iterator();
        Iterator<Hprepymtschddetails> bditerator = dlistbean.iterator();

        int rowIndex = 0;
        int columnIndex = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Row headerrow = sheet.createRow(rowIndex);

            Cell headercell0 = headerrow.createCell(columnIndex++);
            headercell0.setCellValue("Reference");
            headercell0.setCellStyle(style);
            Cell headercell1 = headerrow.createCell(columnIndex++);
            headercell1.setCellValue("Account No");
            headercell1.setCellStyle(style);
            Cell headercell2 = headerrow.createCell(columnIndex++);
            headercell2.setCellValue("Amount");
            headercell2.setCellStyle(style);
            Cell headercell3 = headerrow.createCell(columnIndex++);
            headercell3.setCellValue("Repayment Date");
            headercell3.setCellStyle(style);
            
        rowIndex = 1;

        while (bditerator.hasNext()) {
            Row row = sheet.createRow(rowIndex++);
            
            Hprepymtschddetails dobj = bditerator.next();
            columnIndex = 0;
            String objinn = dobj.getRefid();
            Cell bdcell0 = row.createCell(columnIndex++);
            bdcell0.setCellValue(objinn);
            String objinn1 = dobj.getAccountNo();
            Cell bdcell1 = row.createCell(columnIndex++);
            bdcell1.setCellValue(objinn1);
            double objinn2 = dobj.getInstalment();
            Cell bdcell2 = row.createCell(columnIndex++);
            bdcell2.setCellValue(objinn2);
            String objinn3 = formatter.format(dobj.getRpymtdate());
            Cell bdcell3 = row.createCell(columnIndex++);
            bdcell3.setCellValue(objinn3);
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
    
    
    /**
     *
     * @return
     */
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
