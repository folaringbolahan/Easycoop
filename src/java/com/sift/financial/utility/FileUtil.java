/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.utility;

import com.sift.financial.GenericConfigDAO;
import com.sift.financial.member.BatchUploadFile;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author baydel200
 */
public class FileUtil {
    
        
   private static final Log log = LogFactory.getLog(FileUtil.class);
    
   private String memberFileCol ;
   private GenericConfigDAO genericConfigDAO;
   private String uploadPath;
   private String keyColumn;
   private Map<String, String> memAddrType;
   
    public String getServerFileName(String uploadTypeShort, String company, String branch, String origName)
    {
        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyMMddhhmmss");
        Date today = new Date();
        formatter.format(today);
        
        int pos = origName.indexOf(".");
        String ext = origName.substring(pos);
        
        String newname = uploadTypeShort+formatter.format(today)+company+branch + ext;
    
        return newname;
    }
    
    public String getServerDownloadFileName(String uploadTypeShort, String company, String branch, String ext)
    {
        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyMMddhhmmss");
        Date today = new Date();
        formatter.format(today);
        //int pos = origName.indexOf(".");
        //String ext = origName.substring(pos);
        
        String newname = uploadTypeShort+formatter.format(today)+company+branch + "." + ext;
    
        return newname;
    }
    
    public String getServerReference(String uploadTypeShort, String company, String branch)
    {
        SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
        Date today = new Date();
        //formatter.format(today);
        String reference = uploadTypeShort+formatter.format(today)+company+branch;
    
        return reference;
    }
    
    public boolean moveFile(File f1,String newpath)
	{
		boolean fileRenamed=false;
		
		try{
			f1.renameTo(new File(newpath, f1.getName()));		
			fileRenamed=true;		
		}
		catch(Exception e){
			fileRenamed=false;
			e.printStackTrace();
		}		
		
		return fileRenamed;
	}
	
	public  boolean deleteFile(String filePathAndName) throws Exception
	{
		boolean fileDeleted=false;
		System.out.println("File to be deleteed:   " + filePathAndName);
		
		try
		{
			//moving files to log
			fileDeleted=new File(filePathAndName).delete();
		}
		catch(Exception e){
			 e.printStackTrace();
		}
		return fileDeleted;		
	}
	
	public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
        
        
       public List<String[]> readFile(BatchUploadFile obj) throws Exception
    {
                    //boolean returnType = false;         
                    List<String[]> finalList = null;
                    
                    List<String> columnList = new ArrayList<String>();
                    
                    FileReader fileReader = new FileReader();
                    
                    Map<String, String> sheetNames = null;

                    if(obj.getBatchUploadType().getUploadTypeShort().equals("MEMTYPE"))
                    {
                        log.info("memberFileCol ::" + memberFileCol);
                        
                        String[] colList=  memberFileCol.split("::");
                        log.info("colList ::" + colList.length);
                        for(int i = 0; i < colList.length; i++)
                        {
                          log.info("colList[i] ::" + i + "  " + colList[i]);
                          columnList.add(colList[i]);
                        }
                    fileReader.setColumnCount(colList.length);
                    fileReader.setMultipleSheet(false);
                    }
                    else if(obj.getBatchUploadType().getUploadTypeShort().equals("ADDTYPE"))
                    {
                        
                        //need to get sheetnames as a map
                        sheetNames = null;
                        sheetNames = getMemAddrType();
                        Map<String,String> countryObj = getGenericConfigDAO().getCountryInfoByCompany(obj.getCompanyId().toString());
                                 
                        columnList.add(keyColumn);
                        List<Map<String,Object>> objList = getGenericConfigDAO().getAddressColumns(String.valueOf(countryObj.get("id")));
                     
                        for(int i = 0; i < objList.size(); i++)
                        {
                            Map<String,Object> mapObj = objList.get(i);
                           
                            log.info("(String)mapObj.get(\"descr\") ::" + (String)mapObj.get("descr"));
                            
                            columnList.add((String)mapObj.get("descr"));
                        }
                        
                         fileReader.setColumnCount(columnList.size());
                         fileReader.setMultipleSheet(true);
                    }
                    else   if(obj.getBatchUploadType().getUploadTypeShort().equals("STKTYPE"))
                    {
                    
                        columnList.add(keyColumn);
                        List<Map<String,Object>> objList = getGenericConfigDAO().getStockColumns(obj.getCompanyId().toString());
                       
                        log.info("No of columns read from stock list :: "  +  objList.size());
                        
                        for(int i = 0; i < objList.size(); i++)
                        {
                            Map<String,Object> mapObj = objList.get(i);
                             log.info("The columns read from stock list :: "  +  (String)mapObj.get("code"));
                            columnList.add((String)mapObj.get("code"));
                        }
                        log.info("No of columns here in stocks :: "  +  columnList.size());
                        
                        fileReader.setColumnCount(columnList.size());
                        
                        fileReader.setMultipleSheet(false);
                    }
                    else   if(obj.getBatchUploadType().getUploadTypeShort().equals("EXTTYPE"))
                    {
                    
                        columnList.add(keyColumn);
                        List<Map<String,Object>> objList = getGenericConfigDAO().getExtrafldColumns(obj.getCompanyId().toString());
                       
                        log.info("No of columns read from extra field list :: "  +  objList.size());
                        
                        for(int i = 0; i < objList.size(); i++)
                        {
                            Map<String,Object> mapObj = objList.get(i);
                             log.info("The columns read from extra field list :: "  +  (String)mapObj.get("code"));
                            columnList.add((String)mapObj.get("code"));
                        }
                        log.info("No of columns here in extra field :: "  +  columnList.size());
                        
                        fileReader.setColumnCount(columnList.size());
                        
                        fileReader.setMultipleSheet(false);
                    }
                    
                    log.info("columnList :: " + columnList);
                    fileReader.setKeyColumns(columnList);
                    fileReader.setStartRow(1);
                    
                    log.info("Starting Read with parameter :: " + columnList.size() );
                    //if(obj.getBatchUploadType().getUploadTypeShort().equals("ADDTYPE"))
                    if(fileReader.isMultipleSheet())
                    {
                        finalList =  fileReader.readExcelFile(uploadPath + obj.getBatchUploadFileName(), sheetNames);
                    }
                    else
                    {
                        finalList =  fileReader.readExcelFile(uploadPath + obj.getBatchUploadFileName());
                    }
                    
                    System.out.println("ending Read with parameter state:: " + finalList.size());
                     
       return finalList;
    }

    public String getMemberFileCol() {
        return memberFileCol;
    }

    public void setMemberFileCol(String memberFileCol) {
        this.memberFileCol = memberFileCol;
    }

    public GenericConfigDAO getGenericConfigDAO() {
        return genericConfigDAO;
    }

    public void setGenericConfigDAO(GenericConfigDAO genericConfigDAO) {
        this.genericConfigDAO = genericConfigDAO;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }
    public Map<String, String> getMemAddrType() {
        return memAddrType;
    }

    public void setMemAddrType(Map<String, String> memAddrType) {
        this.memAddrType = memAddrType;
    }
}
