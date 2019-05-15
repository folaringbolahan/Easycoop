/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.BatchUploadFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author baydel200
 */
@Component
@Scope("prototype")
public class GenericValidationTask implements Runnable {
    
    //@Autowired
    //private ValidateScheduleService currentService;
    
    @Autowired
    private ApplicationContext ctx;
    
    private ValidateScheduleInterface currentInterface;
    
    private BatchUploadFile file;
    
    private boolean recordPassed;
    
    private String servPrefix;
    
    
    @Override
    public void run() {
        
        //build batchuploadfile
        
      //Map<String,String> resultContent = new HashMap<String,String> ();
        
       // String beaName = "val" + file.getBatchUploadType().getUploadTypeShort() + "Impl";
        String beaName = getServPrefix() + file.getBatchUploadType().getUploadTypeShort() + "Impl";
        Map<String, Object> processInfo = new HashMap<String, Object>();
        
        processInfo.put("file", file);
        
        boolean returnVal = false;
        currentInterface = (ValidateScheduleInterface)ctx.getBean(beaName);
        
        
        List batchRecord = currentInterface.getRecords(file, recordPassed);
        
        if(batchRecord!=null)
        {
           //retrieve all Validated Batcmemenber 
                List builtRecords  = currentInterface.buildRecords(batchRecord, file,recordPassed);
                
                if (builtRecords!=null)
                {
                    List procRecords =  currentInterface.processObjects(builtRecords, file, recordPassed);
                    
                    if (procRecords!=null)
                    {
                          boolean writeOk =  currentInterface.writeObjects(procRecords, file, recordPassed);
                          
                            returnVal = writeOk;
                    }
                    else
                    {
                        //todo when Processing returns null
                        processInfo.put(ReminderInterface.PROC_MSG, "Failed due to no process records" );
                    }
                }
                else
                {
                 //todo when built retruns null;
                   processInfo.put(ReminderInterface.PROC_MSG, "Failed due to no build-up records");
                }
        }
        else
        {
        
            //todo when initialBatch retruns null;
             processInfo.put(ReminderInterface.PROC_MSG, "Failed due to no batch record or batch record is null");
        
        }
        
       currentInterface.sendNotification(file, processInfo, returnVal);
    }

    public ApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public ValidateScheduleInterface getCurrentInterface() {
        return currentInterface;
    }

    public void setCurrentInterface(ValidateScheduleInterface currentInterface) {
        this.currentInterface = currentInterface;
    }

    public BatchUploadFile getFile() {
        return file;
    }

    public void setFile(BatchUploadFile file) {
        this.file = file;
    }

    public boolean isRecordPassed() {
        return recordPassed;
    }

    public void setRecordPassed(boolean recordPassed) {
        this.recordPassed = recordPassed;
    }

    public String getServPrefix() {
        return servPrefix;
    }

    public void setServPrefix(String servPrefix) {
        this.servPrefix = servPrefix;
    }
    
    
    
    
    
    
}
