/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.validator;
import com.sift.gl.model.FileUpload;
import com.sift.gl.model.FileUploadBean;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**  validator for account transactions upload and persistence
 *
 * @author ABAYOMI AWOSUSI
 */
public class AccounttxnsetupValidator implements Validator {
    private Integer companyid;
    private String wkmode="N";
    private String filepath = "";
    /**
     *
     * @param clazz
     * @return
     */
    public boolean supports(Class clazz) {
    return FileUploadBean.class.isAssignableFrom(clazz);
  }  
 
    /**
     *
     * @param target
     * @param errors
     */
    public void validate(Object target, Errors errors) {
     //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountno","required.accountno", "Account no is required.");
     //ValidationUtils.rejectIfEmpty(errors, "txnDate","required.txnDate", "Tranaction Date is required.");
     ValidationUtils.rejectIfEmpty(errors, "totalRecords","required.totalRecords", "Total number of Records is required.");
     FileUploadBean tofile = (FileUploadBean) target;
     int recno = 0;
     //double recsum = 0.0;
     BigDecimal recsum = new BigDecimal("0.0");
     Date validdate = null;
     try {
      recno = tofile.getTotalRecords();
      recsum = tofile.getFilesum();
     // System.out.println("1" + recsum);
     }
     catch (NullPointerException npex) {
         recno = 0;
     }
     /*
     SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
     String dateop = tofile.getTxnDatestr();
       try {
         formatter1.parse(dateop);
        } catch(ParseException pEx ) 
        {   
          errors.rejectValue("txnDatestr","acctxnbulkupload.txnDatestr" ,"Invalid Transaction Date.");
        } catch( NullPointerException nullEx ) 
        {   
          errors.rejectValue("txnDatestr","acctxnbulkupload.txnDatestr" ,"Invalid Transaction Date.");
        }
   */
     if ((recno == 0) ) {
         errors.rejectValue("totalRecords","acctxnbulkupload.totalRecords" ,"Invalid number of records;Enter a number as records.");
      }
     if ((recno != 0) ) {
        // System.out.println("hrere " + tofile.getUploadFilename());
        // System.out.println("2" + recsum);
       String retmess = validateexcel(filepath, 4,recno,recsum);
       if (retmess.isEmpty()==false) {
         errors.rejectValue("filesum","acctxnbulkupload.filesum" ,retmess);
         if (retmess.equalsIgnoreCase("Number of records does not match supplied value")) {
            errors.rejectValue("totalRecords","acctxnbulkupload.totalRecords" ,retmess); 
         }
       }
     } 
  }
  
    /**
     *
     * @param filepath
     */
    public void setFileLocation(String filepath){
      this.filepath = filepath;
  }
  
    /** checks amount and number of records in excel agrees with what is supplied
     *
     * @param filename
     * @param sumcol
     * @param suppcount
     * @param suppamt
     * @return
     */
    public String validateexcel(String filename, int sumcol,int suppcount,/*double suppamt*/ BigDecimal suppamt)  {
        String messg = "";
        int noofrecs = 0;
        //double totamt = 0.0;
        BigDecimal totamt = new BigDecimal("0.0");
        BigDecimal processedSum = new BigDecimal("0.0");
        processedSum = processedSum.setScale(2, BigDecimal.ROUND_HALF_UP );
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
                        BigDecimal amount = new BigDecimal("0.0");
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
                                amtstr = String.valueOf(row.getCell(sumcol).getNumericCellValue());
                               } catch (NullPointerException npex) { dnullmes= " Missing Amount:<br>";} 
                               
                            
                    }//end of cell iterator
                        try {
                            Double da = Double.parseDouble(amtstr);
                            //amt = da.doubleValue();
                            //totamt = totamt + amt;
                            amount = BigDecimal.valueOf(da.doubleValue());
                            processedSum = processedSum.add(amount);
                            
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
       BigDecimal bgdnewtotvalue = new BigDecimal(processedSum.doubleValue());
       bgdnewtotvalue = bgdnewtotvalue.setScale(2, BigDecimal.ROUND_HALF_UP );
       //totamt=bgdnewtotvalue.doubleValue();
       totamt=bgdnewtotvalue; 
       
       //totamt!=suppamt
       if (totamt.compareTo(suppamt) != 0) {
           System.out.println("totamt ::" + totamt);
           System.out.println("suppamt ::" + suppamt);
           messg = messg + "Sum of amount of records does not match supplied value";
       }
       //System.out.println("4" + noofrecs + " supp " + suppcount );
       //System.out.println("3" + totamt + " supp " + suppamt );
       return messg;
    } 
}
