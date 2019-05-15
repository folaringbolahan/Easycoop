package com.sift.financial.utility;

import com.sift.financial.GenericConfigDAO;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.util.zip.ZipEntry;
	import java.util.zip.ZipException;
	import java.util.zip.ZipOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
	
	public class CreateZipMultipleFiles
	{ 	
	 
            private static final Log log = LogFactory.getLog(CreateZipMultipleFiles.class);
            
            
		private String uploadPath;
		
		public String getZipFiles (String sourceFiles[], String targetName)
		{	
		  String zipFile = getUploadPath() + targetName + ".zip";	
		  try	
			{		
      		//create byte buffer	
			byte[] buffer = new byte[1024]; 	
			/*		
			 * 	 * To create a zip file, use	
			 * 		 * 		
			 * 	 * ZipOutputStream(OutputStream out)
			 * 
			 * 			 * constructor of ZipOutputStream class.	
			 * 		*/ 		
			//create object of FileOutputStream
			FileOutputStream fout = new FileOutputStream(zipFile); 
			//create object of ZipOutputStream from FileOutputStream
			
			ZipOutputStream zout = new ZipOutputStream(fout); 		
			for(int i=0; i < sourceFiles.length; i++)		
			{ 			 
			log.info("Adding " + sourceFiles[i]);	
			//create object of FileInputStream for source file
			FileInputStream fin = new FileInputStream(sourceFiles[i]); 		
			/*				
			 *  * To begin writing ZipEntry in the zip file, use	
			 *  		
			 *   * void putNextEntry(ZipEntry entry)	
			 *   
			 *   * method of ZipOutputStream class.		
			 *   		 * 			
			 *   	 * This method begins writing a new Zip entry to 
			 * the zip file and positions the stream to the start 		
			 * 		 * of the entry data.				 */ 				
			zout.putNextEntry(new ZipEntry(sourceFiles[i])); 
			/*				 
			 * * After creating entry in the zip file, actually 
			 * 				 * write the file.			
			 * 	 */				
			int length; 				
			while((length = fin.read(buffer)) > 0)			
			{				
				zout.write(buffer, 0, length);		
			} 			
			/*				
			 *  * After writing the file to ZipOutputStream, use
			 *  				 * 		
			 *  		 *
			 *   void closeEntry() method of ZipOutputStream class to 				
			 *    * close the current entry and position the stream to 
			 *    
			 * 
			 *  * write the next entry.				 */ 
			zout.closeEntry(); 				
			//close the InputStream				
			
			fin.close(); 			
			}  			 
			//close the ZipOutputStream			
			zout.close(); 			 
			log.info("Zip file has been created!"); 	
			}
				catch(IOException ioe)		
				{	
					zipFile = null;
					log.info("IOException :" + ioe);	
				} 
			return zipFile;
			}

		/**
		 * @return the uploadPath
		 */
		public String getUploadPath() {
			return uploadPath;
		}

		/**
		 * @param uploadPath the uploadPath to set
		 */
		public void setUploadPath(String uploadPath) {
			this.uploadPath = uploadPath;
		}
		}



