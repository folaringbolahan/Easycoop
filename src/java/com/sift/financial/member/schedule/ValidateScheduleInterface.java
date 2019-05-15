/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.member.BatchUploadFile;
import java.util.List;
import java.util.Map;

/**
 *
 * @author baydel200
 */
public interface ValidateScheduleInterface {
    

    // do processing  and mark errors on or pass entries
    public List getRecords(BatchUploadFile  object, boolean passOk);
 
    // do processing by reading uploaded files
    public List buildRecords(List theRecords,Object fileInfo, boolean passOk);

    // do update on  main object
    public  List processObjects(List dataList,Object fileInfo, boolean passOk);

    //write records to destination object with appropriate status
    public  boolean writeObjects(List dataList,Object fileInfo, boolean passOk);
   
    /* 
       doNotification
    */
    public  boolean sendNotification(Object fileBundle, Map<String, Object> processInfo, boolean mailToSend);
    
}
