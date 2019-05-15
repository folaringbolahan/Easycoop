/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.validator;
import com.sift.gl.FileuploadException;
import com.sift.gl.GendataService;
import com.sift.gl.GetSetting;
import com.sift.gl.NotificationService;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import com.sift.gl.validator.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class HptxnuploadValidator implements Validator {
    private Integer companyid;
    private String wkmode="N";
  public boolean supports(Class clazz) {
    return FileUpload.class.isAssignableFrom(clazz);
  }  
 
  public void validate(Object target, Errors errors) {
     ValidationUtils.rejectIfEmpty(errors, "filesum","required.filesum", "Total sum of Records is required.");
     ValidationUtils.rejectIfEmpty(errors, "totalRecords","required.totalRecords", "Total number of Records is required.");
     
     FileUpload tofile = (FileUpload) target;
     int recno = 0;
     //double recsum = 0.0;
     BigDecimal  recsum = new BigDecimal("0.0");
     Date validdate = null;
     try {
      recno = tofile.getTotalRecords();
      recsum = tofile.getFilesum();
     }
     catch (NullPointerException npex) {
         recno = 0;
     }
     
    
     if ((recno == 0) ) {
         errors.rejectValue("totalRecords","rpymttxnbulkupload.totalRecords" ,"Invalid number of records;Enter a number as records.");
      }
     if ((recno != 0) ) {
        // System.out.println("hrere " + tofile.getUploadFilename());
       String retmess = validateexcel(tofile.getLocation(), 2,recno,recsum);
       if (retmess.isEmpty()==false) {
         errors.rejectValue("totalRecords","rpymttxnbulkupload.totalRecords" ,retmess);
       }
     } 
             
     
     //if ((validdate == null) ) {
     //    errors.rejectValue("txnDate","acctxnbulkupload.txnDate" ,"Invalid Transaction Date.");
     // }
  }
  
  
    public String validateexcel(String filename, int sumcol,int suppcount, BigDecimal suppamt)  {
        String messg = "";
        int noofrecs = 0;
        BigDecimal totamt = new BigDecimal("0.0"); 
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
            
            //loop through each of the sheets
            for(int i=0; i < numberOfSheets; i++){                 
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);
                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                int rowid = 0;
                
                while(rowIterator.hasNext()){
                        String amtstr = "";
                        int haserror = 0;
                        BigDecimal amt = new BigDecimal("0.0");
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
                                amtstr = String.valueOf(row.getCell(sumcol).getNumericCellValue());
                               } catch (NullPointerException npex) { dnullmes= " Missing Repayment Amount:<br>";} 
                               
                            
                    }//end of cell iterator
                        try {
                            Double da = Double.parseDouble(amtstr);
                            //amt = da.doubleValue();
                            amt = BigDecimal.valueOf(da.doubleValue()); //  da.doubleValue();
                            //totamt = totamt + amt;
                             totamt = totamt.add(amt) ;
                        } catch (NumberFormatException ex) {
                            
                        }
                      }
                   
                 }//end of rows iterator                
               noofrecs = rowid;                  
            }//end of sheets for loop
         //System.out.println("hrere2 " + noofrecs);   
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
       
        }catch(Exception e) {
            e.printStackTrace();
        }
       if (noofrecs!=suppcount) {
           messg = "Number of records does not match supplied value; ";
       }
       //System.out.println("hrere2 " + totamt);  
       if (totamt!=suppamt) {
           messg = messg + "Sum of amount of records does not match supplied value";
       }
       return messg;
    } 
}
