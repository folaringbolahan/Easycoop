/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import java.util.List;
import java.util.Map;

/**
 *
 * @author baydel200
 */
public abstract class BatchScheduleInterface extends BatchScheduleService {
    
    private String msg;
    
    // do processing  and mark errors on or pass entries
    public abstract Object getFiles(String  dataType);
 
    // do processing by reading uploaded files
    public abstract List buildObjects(Object fileInfo, String Path);

    // do update on  main object
    public abstract List processObjects(List dataList,Object fileInfo);

    //write records to destination object with appropriate status
    public abstract boolean writeObjects(List dataList,Object fileInfo);
   
    /* 
       doNotification
    */
    public abstract boolean sendNotification(Object fileBundle, Map<String, String> processInfo, boolean processState);

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    
    
}
