/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.utility;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
public class FileReader {
    
    private final Log log = LogFactory.getLog(getClass());
    
    private int startRow;
    private int columnCount;
    private List<String> keyColumns = new ArrayList<String>();
    private boolean multipleSheet = false;
    
    public FileReader(){}
    
    
    public List<String> ReadTxtFile(String filePath){
		
		List<String> content = new ArrayList<String>();
		
		File file= null;
		java.io.FileReader reader= null;
		String str=null;
		LineNumberReader lnreader=null;
		
		try{
			
			file= new File(filePath);
			
			reader= new java.io.FileReader(file);
                        
			lnreader= new LineNumberReader(reader);
			
			while((str=lnreader.readLine())!=null ) {
				content.add(str);
			}
			
		}catch(Exception exp){
			exp.printStackTrace();
			content=null;
		}finally{
			
            if(reader!=null){
            	try {
					reader.close();
				} catch (IOException e) {
				
					e.printStackTrace();
				}
            	
			}
            if(lnreader!=null){
            	try {
            		lnreader.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
            
		}
		
		return content;
	}
    
    
    public List<String[]> readXlsxFile(String filePath) throws Exception {

        FileInputStream file = new FileInputStream(new File(filePath));
        
		//Workbook workbook = new Workbook();			
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		List<String[]> fileRecords =  new ArrayList<String[]>();
		try{	
                   
                XSSFSheet sheet = workbook.getSheetAt(0);
	        String[] currentRecord=null;
	        XSSFCell  cell=null;
	        String temp=null;
	        int row =0;
	        read:  
	  	    for(row =startRow;;row++) {		
	  	    	 
                        XSSFRow row1 = sheet.getRow(row);
                        
	  	    	  currentRecord= new String[columnCount+1];
	  	    	  
				  for(int col=0;col<columnCount;col++) {	
						
						//cell=sheet.getCell(row,col);
                                                cell=row1.getCell(col);
						
						temp=cell.getStringCellValue();
						
						if(temp==null)temp="";	
						System.out.println("temp["+row+","+col+"] :: "+temp);
						currentRecord[col]=temp.trim();
						
					}
				    
					for(int m=0; m < keyColumns.size() ; m++){
						if(currentRecord[Integer.parseInt(keyColumns.get(m))]==null || currentRecord[Integer.parseInt(keyColumns.get(m))].trim().equals("")){
							break read;
						}
					}
					
					currentRecord[columnCount]=row+"";
					
					fileRecords.add(currentRecord);		
					
	  	    }
           
	        
	     }catch(Exception exp){
	    	 exp.printStackTrace();
	     }finally{
	    	 workbook=null;
	     }
	     
         return fileRecords;
    }
    
    
     public List<String[]> readXlsFile(String filePath) throws Exception {

        FileInputStream file = new FileInputStream(new File(filePath));
        
		//Workbook workbook = new Workbook();			
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		List<String[]> fileRecords =  new ArrayList<String[]>();
		try{	
                   
                HSSFSheet sheet = workbook.getSheetAt(0);
	        String[] currentRecord=null;
	        HSSFCell  cell=null;
	        String temp=null;
	        int row =0;
	        read:  
	  	    for(row =startRow;;row++) {		
	  	    	 
                        HSSFRow row1 = sheet.getRow(row);
                        
	  	    	  currentRecord= new String[columnCount+1];
	  	    	  
				  for(int col=0;col<columnCount;col++) {	
						
						//cell=sheet.getCell(row,col);
                                                cell=row1.getCell(col);
						
						temp=cell.getStringCellValue();
						
						if(temp==null)temp="";	
						System.out.println("temp["+row+","+col+"] :: "+temp);
						currentRecord[col]=temp.trim();
						
					}
				    
					for(int m=0; m < keyColumns.size() ; m++){
						if(currentRecord[Integer.parseInt(keyColumns.get(m))]==null || currentRecord[Integer.parseInt(keyColumns.get(m))].trim().equals("")){
							break read;
						}
					}
					
					currentRecord[columnCount]=row+"";
					
					fileRecords.add(currentRecord);		
					
	  	    }
           
	        
	     }catch(Exception exp){
	    	 exp.printStackTrace();
	     }finally{
	    	 workbook=null;
	     }
	     
         return fileRecords;
    }
    
     
     public List<String[]> readExcelFile(String filePath) throws Exception {

        FileInputStream file = new FileInputStream(new File(filePath));
        
		Workbook workbook = null;	
                 if(filePath.toLowerCase().endsWith("xlsx")){
                workbook = new XSSFWorkbook(file);
            }else if(filePath.toLowerCase().endsWith("xls")){
                workbook = new HSSFWorkbook(file);
            }
		//HSSFWorkbook workbook = new HSSFWorkbook(file);
		List<String[]> fileRecords =  new ArrayList<String[]>();
		try{	
                   
                Sheet sheet = workbook.getSheetAt(0);
	        String[] currentRecord=null;
	        Cell  cell=null;
	        Object temp=null;
	        int row =0;
	        Iterator rows = sheet.rowIterator();
                rows.next();
	  	 //   for(row =startRow;;row++) 
                  while(rows.hasNext())
                    {		
                         
                        Row row1 = (Row)rows.next();
                                //sheet.getRow(row);
                        
	  	    	  //currentRecord= new String[columnCount+1];
                          currentRecord= new String[columnCount];
	  	    	  
				  for(int col=0;col<columnCount;col++) {	
						
						//cell=sheet.getCell(row,col);
                                               cell=row1.getCell(col);
                                           
                                            try
                                            {
						int type = cell.getCellType();
                                                
                                                if(type == Cell.CELL_TYPE_NUMERIC)
                                                {
                                                    temp=cell.getNumericCellValue();
                                                }
                                                else if(type == Cell.CELL_TYPE_STRING)
                                                {
                                                    temp=cell.getStringCellValue();
                                                }
                                                else if(type == Cell.CELL_TYPE_BOOLEAN)
                                                {
                                                    temp=cell.getBooleanCellValue();
                                                }
                                                else if(type == Cell.CELL_TYPE_BLANK)
                                                {
                                                    temp=null;
                                                }
                                                
                                            }
                                            catch(Exception ex)
                                            {
                                                log.info(" Cell is null");
                                            temp=null;
                                            }
						
                                        if(temp==null)temp="";	
                                        System.out.println("temp["+row+","+col+"] :: "+temp);
                                        currentRecord[col]=temp.toString();
						
					}
					//currentRecord[columnCount]=row+"";
					
					fileRecords.add(currentRecord);		
			 row =row +1;
	  	    }
           
	        
	     }catch(Exception exp){
	    	 exp.printStackTrace();
	     }finally{
	    	 workbook=null;
	     }
	     System.out.println("fileRecords :: "+fileRecords.size());
         return fileRecords;
    }
     
     
     public List<String[]> readExcelFile(String filePath, Map<String, String> sheetNames) throws Exception {

        FileInputStream file = new FileInputStream(new File(filePath));
            // System.out.println(" filePath " + filePath);
            // System.out.println(" sheetNames " + sheetNames.isEmpty());
		Workbook workbook = null;	
                 if(filePath.toLowerCase().endsWith("xlsx")){
                workbook = new XSSFWorkbook(file);
            }else if(filePath.toLowerCase().endsWith("xls")){
                workbook = new HSSFWorkbook(file);
            }
		//HSSFWorkbook workbook = new HSSFWorkbook(file);
		List<String[]> fileRecords =  new ArrayList<String[]>();
		try{
                  
                 Set<String> sheetNameKeys = sheetNames.keySet();
                 
                for(String sheetName : sheetNameKeys)
                {
                    
                Sheet sheet = workbook.getSheet(sheetNames.get(sheetName));
                
                String[] currentRecord=null;
                Cell  cell=null;
                Object temp=null;
	        int row =0;
	        Iterator rows = sheet.rowIterator();
                rows.next();
	  	 //   for(row =startRow;;row++) 
                  while(rows.hasNext())
                    {		
                        Row row1 = (Row)rows.next();
                           //sheet.getRow(row);
                        
	  	    	  currentRecord= new String[columnCount+1];
	  	    	  
				  for(int col=0;col<columnCount;col++) {	
						
						//cell=sheet.getCell(row,col);
                                                cell=row1.getCell(col);
						int type = cell.getCellType();
                                                
                                                if(type == Cell.CELL_TYPE_NUMERIC)
                                                {
                                                    temp=cell.getNumericCellValue();
                                                }
                                                else if(type == Cell.CELL_TYPE_STRING)
                                                {
                                                    temp=cell.getStringCellValue();
                                                }
                                                else if(type == Cell.CELL_TYPE_BOOLEAN)
                                                {
                                                    temp=cell.getBooleanCellValue();
                                                }
                                                else if(type == Cell.CELL_TYPE_BLANK)
                                                {
                                                    temp=null;
                                                }
						
						if(temp==null)temp="";	
						log.info("temp["+row+","+col+"] :: "+temp);
						currentRecord[col]=temp.toString();
						
					}
					//currentRecord[columnCount]=row+"";
                                        //currentRecord[columnCount+1]=sheetNames.get(sheetName);
                                         currentRecord[columnCount]=sheetNames.get(sheetName);
					
					fileRecords.add(currentRecord);		
			 row =row +1;
	  	    }
                  
                  }
           
	        
	     }catch(Exception exp){
	    	 exp.printStackTrace();
	     }finally{
	    	 workbook=null;
	     }
	     System.out.println("fileRecords :: "+fileRecords.size());
         return fileRecords;
    }
    
    /**
	 * @return the columnCount
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * @param columnCount the columnCount to set
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}


	
	/**
	 * @return the keyColumns
	 */
	public List<String> getKeyColumns() {
		return keyColumns;
	}

	/**
	 * @param keyColumns the keyColumns to set
	 */
	public void setKeyColumns(List<String> keyColumns) {
		this.keyColumns = keyColumns;
	}

        public boolean isMultipleSheet() {
            return multipleSheet;
        }

        public void setMultipleSheet(boolean multipleSheet) {
            this.multipleSheet = multipleSheet;
        }

}
