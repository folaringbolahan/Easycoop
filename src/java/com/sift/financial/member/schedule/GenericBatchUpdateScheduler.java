/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.member.BatchUploadFile;
import com.sift.financial.member.BatchUploadReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sift.financial.ReminderInterface;

/**
 *
 * @author baydel200
 */
@Component
@Scope("prototype")
public class GenericBatchUpdateScheduler implements Runnable {
    
    private static final Log log = LogFactory.getLog(GenericBatchUpdateScheduler.class);
    
    //@Autowired
    //private BatchScheduleService currentService;
    
    @Autowired
    private ApplicationContext ctx;
    
    private BatchScheduleInterface currentInterface;
    
    @Autowired
    @Value("${UPLOAD_PATH}")
    private String uploadPath;
    
    @Autowired
    @Value("${refStatus}")
    private String statusShort;
    
    private String currentRefrence;
   
    public void doRun()
    {
        
       // Event event = (Event)currentService.getEventDAO().findByEventShort(currentService.getEventShort()).get(0);
        boolean writeResult = false;
        
        Map<String,String> resultContent = new HashMap<String,String> ();
       
        //fetch relevant Refrence
        log.info("Getting Reference :: " + currentRefrence);
        
        currentInterface = (BatchScheduleInterface)ctx.getBean(currentRefrence.substring(0,7));
        
        log.info(" currentInterface  ::" + currentInterface.getClass());
        
        List references =  currentInterface.getFreshReference(currentRefrence);
        
        log.info("Getting References :: " + references);
        
        if(references!=null)
        {
            log.info("Getting References :: size () ::" + references.size());
            
                Iterator  refIter = references.listIterator(); 
                
                while(refIter.hasNext())
                {
                  BatchUploadReference curBatch = (BatchUploadReference)refIter.next();
                     
                  log.info(" References :: curBatch ::" + curBatch.getBatchUploadReferenceId());
                  
                        int noOfFiles = curBatch.getBatchUploadFiles().size();
                        
                        log.info(" References :: curBatch no of files  ::" + noOfFiles);
                        
                        int countFile = 0;
                      
                        log.info(" About to get Files .......");
                        
                      List theFiles  = currentInterface.getBatchUploadFileDAO().getByReference(curBatch.getBatchUploadReferenceId(), statusShort);
                        
                      log.info(" theFiles  ::" + theFiles.size());
                      
                            for(Object currentFileObj: theFiles)
                            {
                            
                                 BatchUploadFile currentFile = (BatchUploadFile)currentFileObj;
                                 
                                  log.info(" currentFile  ::" + currentFile.getBatchUploadType().getUploadTypeShort());
                                 //configure services as UploadType short
                                 //currentInterface = (BatchScheduleInterface)ctx.getBean(currentFile.getBatchUploadType().getUploadTypeShort());
                                  
                                 List rawRecords = currentInterface.buildObjects(currentFile,uploadPath);
                                 
                                 log.info(" currentInterface buildObjects size::" + rawRecords.size());
 
                                 if(rawRecords!=null)
                                    {
                                        log.info("...........About processing............... ");
                                        List processedRecords = currentInterface.processObjects(rawRecords, currentFile);
                                        
                                        log.info(" currentInterface processObjects size::" + processedRecords.size());
 
                                        if (processedRecords!=null)
                                           {
                                               
                                               log.info(" ............About Writing Result..........");
                                               writeResult =  currentInterface.writeObjects(processedRecords, currentFile);
                                               
                                               log.info(" currentIn writeResult::" + writeResult );
                                               
                                           }
                                           else
                                           {    
                                               resultContent.put(ReminderInterface.PROC_MSG, "No record found due to error:" + currentInterface.getMsg());
                                           }
                                          
                                    }
                                    else
                                    {
                                     resultContent.put(ReminderInterface.PROC_MSG, "No record found due to error:" + currentInterface.getMsg());
                                    }
                                 
                                 
                                 currentInterface.sendNotification(currentFileObj, resultContent , writeResult);
                                 
                               countFile = countFile + 1;
                            }
                            
                            //mark of file rrference here.
                           if(countFile == noOfFiles)
                           {

                           }
                           log.info(" closeFreshReference ::" + curBatch.getBatchUploadReferenceId() );
                           
                           currentInterface.closeFreshReference(curBatch, writeResult);
                           
                           log.info(" closeFreshReference ::" + curBatch.getBatchUploadReferenceId() );
                }
        }
        
    }

    public ApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public BatchScheduleInterface getCurrentInterface() {
        return currentInterface;
    }

    public void setCurrentInterface(BatchScheduleInterface currentInterface) {
        this.currentInterface = currentInterface;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getStatusShort() {
        return statusShort;
    }

    public void setStatusShort(String statusShort) {
        this.statusShort = statusShort;
    }

    @Override
    public void run() {
        
        log.info("Starting Run for  :: " + currentRefrence);
        doRun();
        log.info("Ending Reference :: " + currentRefrence);
    }

    public String getCurrentRefrence() {
        return currentRefrence;
    }

    public void setCurrentRefrence(String currentRefrence) {
        this.currentRefrence = currentRefrence;
    }
    
}
