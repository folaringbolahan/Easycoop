/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.utility;

import com.sift.financial.GenericConfigDAO;
import java.io.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author baydel200
 */
public class FileWriter {
    
    private static final Log log = LogFactory.getLog(FileWriter.class);
    
    private boolean firstRowHeader = false;
    private String fileType;
    private String partFileName;
    private String companyId;
    private String branchId;
    private String downLoadPath;
    

    public boolean isFirstRowHeader() {
        return firstRowHeader;
    }

    public void setFirstRowHeader(boolean firstRowHeader) {
        this.firstRowHeader = firstRowHeader;
    }
    
    public String createExcelFile (Map<String,String> sheetNames, Map<String, Map<String,Object[]>> data) throws Exception
    {
          Workbook workbook = null;	
          FileOutputStream out = null;
          String theFileName = null;
          
          try 
          {
                 log.info("Here inside  :::: filewriter" );
                 
                    if(fileType.toLowerCase().equals("xlsx"))
                    {
                          workbook = new XSSFWorkbook();
                    }
                    else if(fileType.toLowerCase().equals("xls"))
                    {
                          workbook = new HSSFWorkbook();
                    }
                    
                       log.info("workbook :::: " + workbook.getClass());

                    Sheet sheet = null;

                    log.info("sheetNames.size() :::: " + sheetNames.size());
                    log.info("data.size() :::: " + data.size());
                    
                    theFileName = getFileName();
                    boolean makeallfieldstext = false;
                    CellStyle cstyle=null;
                    if (theFileName.startsWith("EXTRAFLDTYPE")) {
                        DataFormat exformat = workbook.createDataFormat();
                        cstyle = workbook.createCellStyle();
                        cstyle.setDataFormat(exformat.getFormat("@"));
                        makeallfieldstext = true;
                    }
                    
                    if(sheetNames.size()==data.size())
                    {
                                  Set<String> theSheets = sheetNames.keySet();
                                  //Set<String> theData = data.keySet(); 
                                  for(String currentSheet : theSheets)
                                  {
                                 log.info("currentSheet :::: " + currentSheet);
                                 sheet=  workbook.createSheet(sheetNames.get(currentSheet));

                                                 Map<String, Object[]> currentData = data.get(currentSheet);
                                                 
                                                 // set all cols to text - can it be up to 50?
                                                 
                                                  

                                               //  data.put("1", new Object[] {"Emp No.", "Name", "Salary"});
                                              //   data.put("2", new Object[] {1d, "John", 1500000d});
                                               //  data.put("3", new Object[] {2d, "Sam", 800000d});
                                               //  data.put("4", new Object[] {3d, "Dean", 700000d});

                                                 Set<String> keyset = currentData.keySet();
                                                 int rownum = 0;
                                                 for (String key : keyset) 
                                                 {
                                                    log.info("key :::: " + key);
                                                     Row row = sheet.createRow(rownum++);
                                                     Object [] objArr = currentData.get(key);
                                                     int cellnum = 0;
                                                     
                                                     if (makeallfieldstext==true) 
                                                     {
                                                      for(int i = 0; i < objArr.length; i++)
                                                      {
                                                          log.info("Setting as text the column :::: " + i);
                                                          sheet.setDefaultColumnStyle(i, cstyle);
                                                      }
                                                     }   
                                                     
                                                     for (Object obj : objArr) 
                                                     {
                                                         Cell cell = row.createCell(cellnum++);
                                                         if(obj instanceof Date) 
                                                             cell.setCellValue((Date)obj);
                                                         else if(obj instanceof Boolean)
                                                             cell.setCellValue((Boolean)obj);
                                                         else if(obj instanceof String)
                                                             cell.setCellValue((String)obj);
                                                         else if(obj instanceof Double)
                                                             cell.setCellValue((Double)obj);
                                                     }
                                                }
                                  }
                    }
                    else
                    {
                        throw new Exception("Problem with data:: number of sheets and data set not the same");
                    }

            /* theFileName = getFileName();
             
             if (theFileName.startsWith("EXTRAFLDTYPE")) {
                 
             }
            */ 
             if(workbook != null && theFileName!=null)
             {            
                 log.info("downLoadPath :::: " + downLoadPath +  theFileName);
                out = new FileOutputStream(new File(downLoadPath +  theFileName));
                workbook.write(out);
                out.close();
             }
             else
             {
              throw new Exception("Problem with workbook and filename creation ");
             }
             
            log.info("Excel written successfully..");

          } 
          catch (FileNotFoundException e) 
          { 
             throw new Exception(e.getMessage());
                        
          } catch (IOException e) 
          {
             throw new Exception(e.getMessage());
                        
          }
    
     return theFileName;
    }
    
    
 
    private String getFileName()
    {
       FileUtil fileUtility = new FileUtil(); 
       
    return fileUtility.getServerDownloadFileName(partFileName,companyId, branchId, fileType);
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getPartFileName() {
        return partFileName;
    }

    public void setPartFileName(String partFileName) {
        this.partFileName = partFileName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBarnchId(String branchId) {
        this.branchId = branchId;
    }

    public String getDownLoadPath() {
        return downLoadPath;
    }

    public void setDownLoadPath(String downLoadPath) {
        this.downLoadPath = downLoadPath;
    }
    
    
}
