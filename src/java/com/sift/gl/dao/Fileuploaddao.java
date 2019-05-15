/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.dao;

import com.sift.gl.AuditlogService;
import com.sift.gl.FileuploadException;
import com.sift.gl.GendataService;
import com.sift.gl.GetSetting;
import com.sift.gl.NotificationService;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.sql.SQLException;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** dao class for persistence and data operations on file upload
 *
 * @author ABAYOMI AWOSUSI
 */

//@LocalBean
@Component
public class Fileuploaddao implements Fileuploadinter {
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
    public Fileuploaddao () {
      //GendataService dbobj = new GendataService();
      //dbobj.inimkcon();
    }
   /// public void setNotificationService(NotificationService notificationService) {
  ///    this.notificationService = notificationService;
  ///  }
    /**
     *
     * @param fileUpload
     * @throws FileuploadException
     */
    @Override
    public void addFile(FileUpload fileUpload) throws FileuploadException {
    
    try {
         InsertFile(fileUpload);
      } catch (Exception ex) {
           throw new FileuploadException("File Upload Transaction failed: " + ex.getMessage());
      }
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
    
   private void InsertFile(FileUpload fileupload) throws Exception {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();   
     /* String sqlStatement = "insert into fileupload (company_id,branch_id,file_name,location,status,reference_number,user_id,is_verified,product_id) values (" +
            fileupload.getCompanyId()  + "," + fileupload.getBranchId() + ",'" + fileupload.getUploadFilename() + "','" + fileupload.getLocation() + "',0,'" + fileupload.getBatchId() + "','" + fileupload.getUploadedBy() + "',0,0" + ")";
      dbobj.updateTablebatch(sqlStatement);
      */
      int coy = Integer.parseInt(fileupload.getCompanyId());
      int br = Integer.parseInt(fileupload.getBranchId());
      PreparedStatement psmt = null;
      try {
      psmt = dbobj.con.prepareStatement("insert into fileuploadgl (company_id,branch_id,file_name,location,status,reference_number,user_id,is_verified,product_id,processed_date) values (?,?,?,?,?,?,?,?,?,?)"); 
      psmt.setInt(1, coy);
      psmt.setInt(2, br);
      psmt.setString(3, fileupload.getUploadFilename());
      psmt.setString(4, fileupload.getLocation());
      psmt.setInt(5, 0);
      psmt.setString(6, fileupload.getBatchId());
      psmt.setString(7, fileupload.getUploadedBy());
      psmt.setInt(8, 0);
      psmt.setInt(9, 0);
      //java.sql.Date sqlDate = new java.sql.Date(fileupload.getUploadDate().getTime());
      //psmt.setDate(10, sqlDate);
      java.sql.Timestamp sqlTime = new java.sql.Timestamp(fileupload.getUploadDate().getTime());
      psmt.setTimestamp(10, sqlTime);
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
     * @param batchid
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
    public void addFilecontents(String batchid,int companyid,int branchid,String currency, java.util.Date dateopened,String ipaddr, String username,String timezone) throws FileuploadException {
    
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String loc = "";
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM fileuploadgl where reference_number = '" + batchid + "' and company_id = " + companyid + " and branch_id = " + branchid);
         while (rs.next()) {  
             loc = rs.getString("location"); 
             importfilefrmloc(loc,batchid,companyid,branchid, currency,dateopened,ipaddr,username,timezone);
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
     * @throws FileuploadException
     */
    public void importfilefrmloc(String filename,String ref,int companyid,int branchid,String currency, java.util.Date dateopened,String ipaddr, String username,String timezone) throws FileuploadException {
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
            //loop through each of the sheets
            for(int i=0; i < numberOfSheets; i++){                 
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);
                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                int rowid = 0;
                while(rowIterator.hasNext()){
                        
                	String acno="";
                	String acname="";
                	int acgrp=0;
                        String acgrpstr ="";
                	String acstr="";
                	int cntrlac=99;
                        String cntrlacstr="";
                        String cntrlacnostr="";
                	String baltype="";
                	String uploadError="N";
                	String validationMsg="";
                        int haserror = 0;
                    
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
                                acno = row.getCell(0).getStringCellValue();
                              } catch (NullPointerException npex) { dnullmes= " Missing Ac no:<br>";} 
                               catch (IllegalStateException npex) { dnullmes= " Ac no column must be formatted as text:<br>";} 
                              try {
                                acname = row.getCell(1).getStringCellValue();
                              } catch (NullPointerException npex) { dnullmes= " Missing Ac name:<br>";}
                               catch (IllegalStateException npex) { dnullmes= " Ac name column must be formatted as text:<br>";} 
                              try {  
                                acgrpstr = String.valueOf(row.getCell(2).getNumericCellValue());
                               } catch (NullPointerException npex) { dnullmes= " Missing Ac group:<br>";} 
                               catch (IllegalStateException npex) { dnullmes= " Ac group column must be numeric:<br>";} 
                              try {  
                                acstr = row.getCell(3).getStringCellValue();
                               } catch (NullPointerException npex) { dnullmes= " Missing Ac structure:<br>";} 
                               catch (IllegalStateException npex) { dnullmes= " Ac structure column must be formatted as text:<br>";} 
                               try {  
                                cntrlacstr = String.valueOf(row.getCell(4).getNumericCellValue());
                               } catch (NullPointerException npex) { dnullmes= " Missing Control Account flag:<br>";} 
                                catch (IllegalStateException npex) { dnullmes= " Control Account column must be numeric(1 or 0):<br>";} 
                              try {  
                                baltype = row.getCell(5).getStringCellValue();
                               } catch (NullPointerException npex) { dnullmes= " Missing Balance type:<br>";} 
                              catch (IllegalStateException npex) { dnullmes= " Balance type column must be formatted as text:<br>";} 
                              try {  
                                cntrlacnostr = row.getCell(6).getStringCellValue();
                               } catch (NullPointerException npex) { } 
                                
                              
                            
                    }//end of cell iterator
                        validationMsg += dnullmes;
                        if ((acno.isEmpty() == true)) {
                            validationMsg += " Missing Ac no:<br>";
                        }
                        ///check account no does not exists
                        if ((acname.isEmpty() == true)) {
                            validationMsg += " Missing Ac name:<br>";
                        }
                        try {
                            Double da = Double.parseDouble(acgrpstr);
                            acgrp = da.intValue();
                            acgrpstr = String.valueOf(acgrp);
                        } catch (NumberFormatException ex) {
                            validationMsg += " Invalid Account group:should be Numeric<br>";
                        }
                        ///check account group exists

                        if ((acstr.isEmpty() == true)) {
                            validationMsg += " Missing Ac structure:<br>";
                        }
                        ///check account struct exists
                        try {
                            //cntrlac = Integer.parseInt(cntrlacstr);
                            Double dd = Double.parseDouble(cntrlacstr);
                            cntrlac = dd.intValue();
                            cntrlacstr = String.valueOf(cntrlac);
                        } catch (NumberFormatException ex) {
                            validationMsg += " Invalid Control Account flag:should be numeric<br>";
                        }
                        if (cntrlac != 1 && cntrlac != 0) {
                            validationMsg += " Invalid Control Account flag:should be 1 or 0<br>";
                        }
                        if ((baltype.equalsIgnoreCase("D") != false) && (baltype.equalsIgnoreCase("C") != false)) {
                            validationMsg += " Invalid Balance type:should be D or C<br>";
                        }
                                                        
                    
                    //
                         
                        
                    if ((acno.isEmpty()==false)&&(acstr.isEmpty()==false)&&(acgrp!=0)) {
                                    String msg=validateacrow(acno,acstr,acgrp,companyid,branchid);
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
                    Insertaccountsimportpending(dbobj,acno,acname,acgrpstr,acstr,baltype,cntrlacstr,cntrlacnostr,branchid,companyid,ref,rowid,haserror,validationMsg);
                    ////// if row ok insert into accounts table
                    
                    if (haserror==0) {
                        boolean contracc=false;
                        if (cntrlac==1) { contracc=true;}
                        InsertAccountDetails(acno,acname,acgrp, acstr,currency,dateopened,baltype,contracc,cntrlacnostr,branchid,companyid,ipaddr,username,timezone);
                        noofrecsok = noofrecsok + 1; 
                    }
                    
                    }
                    uploadError="N";
                    validationMsg="";
                 }//end of rows iterator                
               noofrecs = rowid;                  
            }//end of sheets for loop
            
            //close file input stream
            fis.close();  
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
         String msql ="SELECT * FROM accounts where accountno = '" + acno + "' and companyid = " + companyid + " and branch = " + branchid + " and active = 1 and ControlAccount = 0";
         rs =  dbobj.retrieveRecset(msql);
         if (dbobj.retrieveRecset(msql).first() == false)
         {
           dmess = " Account no does not exist, is inactive or is a control account<br>";
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
    
    private void Insertaccountsimportpending(GendataService dbobj,String AccountNo,String Name,String AcGroup,String AcStruct,String Balancetype,String ControlAccount,String cntrlacnostr,int Branchid,int Companyid,String  Reference,int Rownum,int Haserrors,String Errormessage) throws Exception {
     //GendataService dbobj = new GendataService();
     //dbobj.inimkcon();   
      PreparedStatement psmt = null;
      try{
      psmt = dbobj.con.prepareStatement("insert into accountsimportpending (AccountNo, Name, AcGroup, AcStruct, Balancetype, ControlAccount, Branchid, Companyid, Reference, Rownum, Haserrors, Errormessage,controlaccountno) values (?,?,?,?,?,?,?,?,?,?,?,?,?)"); 
      psmt.setString(1, AccountNo);
      psmt.setString(2, Name);
      psmt.setString(3, AcGroup);
      psmt.setString(4, AcStruct);
      psmt.setString(5, Balancetype);
      psmt.setString(6, ControlAccount);
      psmt.setInt(7, Branchid);
      psmt.setInt(8, Companyid);
      psmt.setString(9, Reference);
      psmt.setInt(10, Rownum);
      psmt.setInt(11, Haserrors);
      psmt.setString(12, Errormessage);
      psmt.setString(13,cntrlacnostr);
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
           ///  if (dbobj != null) {
           ///   try {
           ///     dbobj.closecon();
           ///   } catch (Exception ignore) { }
           ///  }
          ///   dbobj = null; 
        }
     /// dbobj = null;  
    }
    
    private void InsertAccountDetails(String acno,String name,Integer acgrp, String acstruct,String currency, java.util.Date dateopened, String baltype,boolean cntrlac,String cntrlacnostr, int branchid,int companyid,String ipaddr, String username,String timezone) throws FileuploadException {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();
     boolean active = true;
     boolean closed = false;
     boolean blocked = false;
     boolean opsuccessfull = false;
     String opsuccessfullmsg = "OK";
     SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
     String dateopenedx = null;
      try {
          dateopenedx = formatter1.format(dateopened);

        } catch( NullPointerException nullEx ) {   }
      java.util.List myAcSeg = getAcSeg(acno, acstruct,companyid);
           String acseg1 = "";
           String acseg2 = "";
           String acseg3 = "";
           String acseg4 = "";
           String acseg5 = "";
           String acseg6 = "";
           String acseg7 = "";
           String acseg8 = "";
           String acseg9 = "";
           String acseg10 = "";
           if (myAcSeg.size()>=1){
               acseg1 = (String) myAcSeg.get(0);
           }
           if (myAcSeg.size()>=2){
               acseg2 = (String) myAcSeg.get(1);
           }
           if (myAcSeg.size()>=3){
               acseg3 = (String) myAcSeg.get(2);
           }
           if (myAcSeg.size()>=4){
               acseg4 = (String) myAcSeg.get(3);
           }
           if (myAcSeg.size()>=5){
               acseg5 = (String) myAcSeg.get(4);
           }
           if (myAcSeg.size()>=6){
               acseg6 = (String) myAcSeg.get(5);
           }
           if (myAcSeg.size()>=7){
               acseg7 = (String) myAcSeg.get(6);
           }
           if (myAcSeg.size()>=8){
               acseg8 = (String) myAcSeg.get(7);
           }
           if (myAcSeg.size()>=9){
               acseg9 = (String) myAcSeg.get(8);
           }
           if (myAcSeg.size()==10){
               acseg10 = (String) myAcSeg.get(9);
           }

      String sqlStatement = "insert into accounts (AccountNo,Name,AcGroup,AcStruct,Currency,ControlAccount,controlaccountno,BalanceType,CCyBalance,CCyClearedBalance,Balance,ClearedBalance,Active,Closed,Blocked,branch,companyid,aseg1,aseg2,aseg3,aseg4,aseg5,aseg6,aseg7,aseg8,aseg9,aseg10,dateopened,accounttype)" +
                   " values('" + acno + "','" + name + "'," + acgrp + ",'" + acstruct + "','" + currency + "'," + cntrlac + ",'" + cntrlacnostr + "','" + baltype + "',0,0,0,0," + active + "," + closed + "," + blocked + "," + branchid + "," + companyid + ",'" +
                     acseg1 + "','" + acseg2 + "','" + acseg3 + "','" + acseg4 + "','" + acseg5 + "','" + acseg6 + "','" + acseg7 + "','" + acseg8 + "','" + acseg9 + "','" + acseg10 + "',{d '" + dateopenedx + "'},'G')";
      try {
      opsuccessfull = dbobj.updateTablebatch(sqlStatement);
      
      
      }
      catch (SQLException ex) {
          throw new FileuploadException(ex.getMessage());
      } 
      finally {
             if (dbobj != null) {
              try {
                dbobj.closecon();
              } catch (Exception ignore) { }
             }
             dbobj = null; 
             if (opsuccessfull==false) {opsuccessfullmsg="Account creation Failed";}
          //testmemaccall();
          auditlogcall(1,"GLGAC",ipaddr,username,timezone,acno,opsuccessfullmsg,branchid,companyid);
        }
      
      dbobj = null;  
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
    
     /**
     *
     * @param fileUpload
     * @param companyid
     * @param branchid
     * @param currency
     * @param dateopened
     * @param ipaddr
     * @param username
     * @param timezone
     * @param acctno
     * @param txndate
     * @throws FileuploadException
     */
    @Override
    public void addtxnFile(FileUpload fileUpload,int companyid,int branchid,String currency, java.util.Date dateopened,String ipaddr, String username,String timezone,String acctno, java.util.Date txndate ) throws FileuploadException {
    
    try {
         ///InsertFile(fileUpload);
         InsertaddFile(fileUpload,acctno, txndate,companyid, branchid);
         //check txn sum and no of records against value
         //importfilefrmloc(fileUpload.getLocation(),fileUpload.getBatchId(),companyid,branchid, currency,dateopened,ipaddr,username,timezone);
          
      } catch (Exception ex) {
           throw new FileuploadException("File Upload Transaction failed: " + ex.getMessage());
      }
   }
     
     
     private void InsertaddFile(FileUpload fileupload,String acctno,java.util.Date txndate,int companyid,int branchid) throws Exception {
     GendataService dbobj = new GendataService();
     dbobj.inimkcon();   
     int coy = Integer.parseInt(fileupload.getCompanyId());
      int br = Integer.parseInt(fileupload.getBranchId());
      PreparedStatement psmt = null;
      try {
      psmt = dbobj.con.prepareStatement("insert into fileuploadacttxn (company_id,branch_id,file_name,location,status,reference_number,user_id,is_verified,product_id,processed_date,accountno,txn_date,total_records,file_sum) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); 
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
      psmt.setString(11, acctno);
      sqlDate = new java.sql.Date(txndate.getTime());
      psmt.setDate(12, sqlDate);
      psmt.setInt(13, fileupload.getTotalRecords());
      //System.out.println("fileupload.getFilesum() " + fileupload.getFilesum());
      //psmt.setDouble(14,fileupload.getFilesum());
      psmt.setBigDecimal(14,fileupload.getFilesum());
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
        ResultSet rs = null;
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
            //loop through each of the sheets
            for(int i=0; i < numberOfSheets; i++){                 
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);
                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                int rowid = 0;
                while(rowIterator.hasNext()){
                        
                	String acno="";
                	String acname="";
                	int acgrp=0;
                        String amtstr ="";
                	String narr="";
                	int cntrlac=99;
                        String cntrlacstr="";
                        String cntrlacnostr="";
                	String baltype="";
                	String uploadError="N";
                	String validationMsg="";
                        int haserror = 0;
                        Double amt = 0.0;
                        String entrybatchid="";
                	String entrydocref="";
                	String ddate ="";
                        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
                    
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
                                entrybatchid = row.getCell(0).getStringCellValue();
                              } catch (NullPointerException npex) { dnullmes= " Missing Batch Id:<br>";} 
                              try {
                                acno = row.getCell(1).getStringCellValue();
                              } catch (NullPointerException npex) { dnullmes= " Missing Ac no:<br>";} 
                             // try {
                             //   acname = row.getCell(1).getStringCellValue();
                             // } catch (NullPointerException npex) { dnullmes= " Missing Ac name:<br>";} 
                              try {  
                                narr = row.getCell(3).getStringCellValue();
                               } catch (NullPointerException npex) { dnullmes= " Missing Narration:<br>";} 
                              try {  
                                amtstr = String.valueOf(row.getCell(4).getNumericCellValue());
                               } catch (NullPointerException npex) { dnullmes= " Missing Amount:<br>";} 
                              try {  
                                baltype = row.getCell(5).getStringCellValue();
                               } catch (NullPointerException npex) { dnullmes= " Missing Transaction type(D/C):<br>";} 
                              try {  
                                ddate = row.getCell(6).getStringCellValue();
                               } catch (NullPointerException npex) { dnullmes= " Missing Transaction Date(TxnDate-dd-mm-YYYY):<br>";} 
                              try {  
                                entrydocref = row.getCell(7).getStringCellValue();
                               } catch (NullPointerException npex) { //dnullmes= " Missing Doc Ref:<br>";
                                   
                               } 
                            
                    }//end of cell iterator
                        validationMsg += dnullmes;
                        if ((entrybatchid.isEmpty() == true)) {
                            validationMsg += " Missing Batch Id:<br>";
                        }
                        if ((acno.isEmpty() == true)) {
                            validationMsg += " Missing Ac no:<br>";
                        }
                        ///check account no does not exists
                      ///  if ((acname.isEmpty() == true)) {
                         //   validationMsg += " Missing Ac name:<br>";
                       /// }
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
                        ///check account group exists

                        if ((narr.isEmpty() == true)) {
                            validationMsg += " Missing Narration:<br>";
                        }
                        ///check account struct exists
                        if ((baltype.equalsIgnoreCase("D") != false) && (baltype.equalsIgnoreCase("C") != false)) {
                            validationMsg += " Invalid Balance type:should be D or C<br>";
                        }
                                                        
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
                    //
                         
                        
                    if ((acno.isEmpty()==false)) {
                       String msg=validateacrow(acno,companyid,branchid);
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
                    Insertaccountstxnimportpending(dbobj,acno,acname,narr,amt,baltype,branchid,companyid,ref,rowid,haserror,validationMsg,entrybatchid,entrydocref,ddate);
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
            
            ////check that txn date is in current period
            //// check that txndate for all items in batch is same
            
            ////check balances of each batch - must be zero / and update rows with balance not zero
            
             
             int currentrow = 0;
             String batchno = "";
             try {
                 rs = dbobj.retrieveRecset("SELECT entrybatchno,sum(if(txntype='D',-1*Amount,Amount)) as totalamt FROM accountstxnimportpending where reference = '" + ref + "' and companyid = " + companyid + " and branchid = " + branchid + "  group by entrybatchno ");
                 while (rs.next()) {
                     //currentrow = 0;
                     batchno = rs.getString("entrybatchno");
                     if (rs.getDouble("totalamt")!=0.0) {
                     //BigDecimal zero = new BigDecimal("0.0");
                    // if (rs.getBigDecimal("totalamt").compareTo(zero) != 0) {
                         System.out.println("rs.getDouble(\"totalamt\") :: " + rs.getDouble("totalamt"));
                         //System.out.println("total amount Bigdecimal :: " + rs.getBigDecimal("totalamt") + " is it = 0?");
                         //update reference 
                         String sqlStatement = "update accountstxnimportpending set haserrors = 1,errormessage = CONCAT (errormessage , 'Entries not balanced for batchid " + batchno +  "- <strong>Row ',CAST(Rownum AS CHAR(10)), '</strong><br>')" + " where reference = '" +
                                              ref + "' and entrybatchno = '" + batchno + "' and companyid = " + companyid + " and branchid = " + branchid ;
     
                         dbobj.updateTable(sqlStatement);
                         errorsexist = true;
                     }
                 }
                 ///select count number of rows with error and ok
                 rs = dbobj.retrieveRecset("SELECT count(id) as noofrecs FROM accountstxnimportpending where reference = '" + ref + "' and companyid = " + companyid + " and branchid = " + branchid + " and haserrors = 1");
                 while (rs.next()) {
                     noofrecsfailed = rs.getInt("noofrecs");
                 }
                 rs = dbobj.retrieveRecset("SELECT count(id) as noofrecs FROM accountstxnimportpending where reference = '" + ref + "' and companyid = " + companyid + " and branchid = " + branchid + " and haserrors = 0");
                 while (rs.next()) {
                     noofrecsok = rs.getInt("noofrecs");
                 }

             } catch (SQLException ex) {
                 throw new FileuploadException(ex.getMessage());
             }
           
            
            
            updatefileuploadtbl(ref,branchid,companyid,noofrecsfailed,noofrecsok,amtrecsok);
            if (noofrecsok>0) {
                GetSetting sendnoticeset = new GetSetting();
                String sendnotice = sendnoticeset.GetSettingvalue("ENABLEGLEMAILNOTIF", branchid, companyid);
                if (sendnotice.equalsIgnoreCase("ON") == true) {
                    //NotificationService ntc = new NotificationService();
                    Map model = new HashMap();
                    model.put("referenceid", ref);
                    notificationService.createflowemailnotice("GL15", mailsubject, "glacctxnsuptoapprvmail.ftl", branchid, companyid, model);
                ///ntc.createflowemailnotice("GL15", mailsubject, "glacctxnsuptoapprvmail.ftl", branchid, companyid, model);
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
       /// } catch (SQLException ex) {
       ///   throw new FileuploadException(ex.getMessage());
        
        }catch(Exception e) {
            e.printStackTrace();
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
     
      private void Insertaccountstxnimportpending(GendataService dbobj,String AccountNo,String Name,String Narration,Double Amount,String Txntype,int Branchid,int Companyid,String  Reference,int Rownum,int Haserrors,String Errormessage,String entrybatchid,String entrydocref,String ddate) throws Exception {
     //GendataService dbobj = new GendataService();
     
     PreparedStatement psmt = null;
    try{
      psmt = dbobj.con.prepareStatement("insert into accountstxnimportpending (AccountNo, Name, Narration, Amount, Txntype, Branchid, Companyid, Reference, Rownum, Haserrors, Errormessage,entrybatchno,entryrefno,txndate) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); 
      psmt.setString(1, AccountNo);
      psmt.setString(2, Name);
      psmt.setString(3, Narration);
      psmt.setDouble(4, Amount);
      psmt.setString(5, Txntype);
      psmt.setInt(6, Branchid);
      psmt.setInt(7, Companyid);
      psmt.setString(8, Reference);
      psmt.setInt(9, Rownum);
      psmt.setInt(10, Haserrors);
      psmt.setString(11, Errormessage);
      psmt.setString(12, entrybatchid);
      psmt.setString(13, entrydocref);
      psmt.setString(14, ddate);
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
       /* System.out.println("Inside thread Step 4");
             System.out.println("start thread");
              try {  
              Thread.sleep(25000);
              } catch (InterruptedException e) {  
               e.printStackTrace();  
             }*/
        try {
         rs =  dbobj.retrieveRecset("SELECT * FROM fileuploadacttxn where reference_number = '" + batchid + "' and company_id = " + companyid + " and branch_id = " + branchid);
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
          //   System.out.println("End thread Step 5");
        }
   }
    
    private void updatefileuploadtbl(String ref,int branchid,int companyid,int noofrecsfailed,int noofrecsok,Double amtrecsok)  throws FileuploadException  {
     String sqlStatement = "update fileuploadacttxn set status = -1,failed_count = " + noofrecsfailed + " , success_count = " + noofrecsok + " ,processed_sum = " + amtrecsok + " where reference_number = '" +
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
         rs =  dbobj.retrieveRecset("SELECT * FROM fileuploadacttxn where company_id = " + companyid + " and branch_id = " + branchid + addsql + " and (is_verified = 0 and is_rejected != 1) order by processed_date desc");
         while (rs.next()) {  
             FileUploadBean vaccounttxnsDetails = new FileUploadBean();
             i = i + 1;
             vaccounttxnsDetails.setId(i);
             vaccounttxnsDetails.setBatchId(rs.getString("reference_number")); 
             vaccounttxnsDetails.setAccountno(rs.getString("accountno"));
             vaccounttxnsDetails.setUploadFilename(rs.getString("file_name"));
             vaccounttxnsDetails.setUploadedBy(rs.getString("user_id"));
             //vaccounttxnsDetails.setFilesum(rs.getDouble("file_sum"));
             vaccounttxnsDetails.setFilesum(rs.getBigDecimal("file_sum"));
             vaccounttxnsDetails.setProcessedsum(rs.getDouble("processed_sum"));
             vaccounttxnsDetails.setFailedCount(rs.getInt("failed_count"));
             vaccounttxnsDetails.setSuccessCount(rs.getInt("success_count"));
             vaccounttxnsDetails.setTotalRecords(rs.getInt("total_records"));
             txn_date=formatter.format(rs.getDate("txn_date"));
             vaccounttxnsDetails.setTxnDatestr(txn_date);
             vaccounttxnsDetails.setUploadDate(rs.getDate("processed_date"));
             vaccounttxnsDetails.setTxnDate(rs.getDate("txn_date"));
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
    
    
     @Override
    public List<FileUploadBean> getJournaltxns4apprv(int companyid,int branchid,String userid) throws FileuploadException {
    List allvaccounttxnsDetails = new ArrayList();
    GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        ResultSet rs=null;
        String addsql = "";
        String txn_date = "";
        int i=0;
        GetSetting usercanapprov = new GetSetting();
        String canapprove = usercanapprov.GetSettingvalue("INITIALIZERCANCOMPTXN",branchid,companyid);
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
         rs =  dbobj.retrieveRecset("select a.*,b.debit,b.credit,b.reccount from txnsheaderua a inner join (SELECT count(txnid) as reccount,sum(debit) as debit,sum(credit) as credit,txntype,txnserial,branchid,companyid FROM txnsua group by txntype,txnserial,branchid,companyid) b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.branchid = b.branchid and a.companyid = b.companyid where a.companyid = " + companyid + " and a.branchid = " + branchid + addsql + " and (approvedstatus = 0 ) order by postdate desc");
         while (rs.next()) {  
             FileUploadBean vaccounttxnsDetails = new FileUploadBean();
             i = i + 1;
             vaccounttxnsDetails.setId(i);
             vaccounttxnsDetails.setBatchId (Integer.toString(rs.getInt("headerid"))); 
             vaccounttxnsDetails.setAccountno(rs.getString("txntype")+"-"+rs.getString("txnserial"));
             //vaccounttxnsDetails.setUploadFilename(rs.getString("file_name"));
             if (canapprove.equalsIgnoreCase("YES")==false) {
               //addsql = " and userid != '" + userid + "'"; 
                 if (rs.getString("userid").equalsIgnoreCase(userid)==true) {
                    vaccounttxnsDetails.setUploadFilename("donotshow");
                 }
             }
             vaccounttxnsDetails.setUploadedBy(rs.getString("userid"));
             //vaccounttxnsDetails.setFilesum(rs.getDouble("file_sum"));
             vaccounttxnsDetails.setFilesum(rs.getBigDecimal("debit"));
             vaccounttxnsDetails.setProcessedsum(rs.getDouble("credit"));
             vaccounttxnsDetails.setFailedCount(0);
             vaccounttxnsDetails.setSuccessCount(rs.getInt("reccount"));
             vaccounttxnsDetails.setTotalRecords(rs.getInt("reccount"));
             txn_date=formatter.format(rs.getDate("postdate"));
             vaccounttxnsDetails.setTxnDatestr(txn_date);
             vaccounttxnsDetails.setUploadDate(rs.getDate("entrydate"));
             vaccounttxnsDetails.setTxnDate(rs.getDate("postdate"));
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
         rs =  dbobj.retrieveRecset("SELECT * FROM fileuploadacttxn where company_id = " + companyid + " and branch_id = " + branchid + addsql + " and (is_verified = 0 and is_rejected != 1) order by processed_date desc");
         while (rs.next()) {  
             FileUploadBean vaccounttxnsDetails = new FileUploadBean();
             i = i + 1;
             vaccounttxnsDetails.setId(i);
             vaccounttxnsDetails.setBatchId(rs.getString("reference_number")); 
             vaccounttxnsDetails.setAccountno(rs.getString("accountno"));
             vaccounttxnsDetails.setUploadFilename(rs.getString("file_name"));
             vaccounttxnsDetails.setUploadedBy(rs.getString("user_id"));
             //vaccounttxnsDetails.setFilesum(rs.getDouble("file_sum"));
             vaccounttxnsDetails.setFilesum(rs.getBigDecimal("file_sum"));
             vaccounttxnsDetails.setProcessedsum(rs.getDouble("processed_sum"));
             vaccounttxnsDetails.setFailedCount(rs.getInt("failed_count"));
             vaccounttxnsDetails.setSuccessCount(rs.getInt("success_count"));
             vaccounttxnsDetails.setTotalRecords(rs.getInt("total_records"));
             txn_date=formatter.format(rs.getDate("txn_date"));
             vaccounttxnsDetails.setTxnDatestr(txn_date);
             vaccounttxnsDetails.setUploadDate(rs.getDate("processed_date"));
             vaccounttxnsDetails.setTxnDate(rs.getDate("txn_date"));
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
     String sqlStatement = "update fileuploadacttxn set is_rejected = 1 where reference_number = '" +
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
    
    public void rejectPostingjrnl(String batchid,int companyid,int branchid,String userid, String timeZonestr) throws FileuploadException {
      String tempDate = null;
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      TimeZone timeZone = TimeZone.getTimeZone(timeZonestr);
      Calendar rightNow = Calendar.getInstance(timeZone);
        try {
         tempDate = formatter.format(rightNow.getTime());
        } catch( NullPointerException nullEx ) {   }
        String sqlStatement = "update txnsheaderua set approvedstatus = 2, approveruserid = '" + userid + "', approvaldate = {d '" + tempDate + "'} where headerid = " +
            batchid + " and companyid = " + companyid + " and branchid = " + branchid ;
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
    
}
