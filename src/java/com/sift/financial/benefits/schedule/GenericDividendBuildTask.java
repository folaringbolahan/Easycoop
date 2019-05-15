/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.benefits.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.Dividend;
import com.sift.financial.member.DividendSchedule;
import com.sift.financial.member.MemberHoldingsDAO;
import com.sift.financial.member.schedule.ValidateScheduleInterface;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author baydel200
 */
@Component
@Scope("prototype")

public class GenericDividendBuildTask implements Runnable{
    
    private static final Log log = LogFactory.getLog(GenericDividendBuildTask.class);
    
    @Autowired
    private ApplicationContext ctx;
    private Dividend divInfo;
    private BuildDivPayableScheduleInterface divScheduleService;
    private String taskToRun;
    private String eventShort;

    public String getEventShort() {
        return eventShort;
    }

    public void setEventShort(String eventShort) {
        this.eventShort = eventShort;
    }
    
    
    @Override
    public void run() {
        
         boolean returnVal = false;
          List<DividendSchedule> buildDivs = null;
           Map<String, Object> dataBundle = new HashMap<String, Object>();
        //build batchuploadfile
        divScheduleService = (BuildDivPayableScheduleInterface)ctx.getBean(taskToRun);  
        divScheduleService.setEvenShort(getEventShort());
        
        log.info("Starting schedule with task " + taskToRun);
        
        log.info("getting members for  task :: " + taskToRun);
        List memberList = divScheduleService.getMemberByRecordDate(divInfo);
        
        log.info("members obtained for task is size :: " + memberList);
        //get the members per stock
        if(memberList!=null && memberList.size()> 0)
        {
            log.info("Inside memeber available channel: ");
            
            if(divInfo.getFormula().equals("N"))
            {
                log.info("When Formula id N process Objects ");
               buildDivs =  divScheduleService.processObjects(memberList,divInfo,null);
               
               log.info("When Formula id N process Objects size ::  " + buildDivs.size());
            }
            else
            {
                log.info("When Formula id Y process Objects ");
                
                 buildDivs =   divScheduleService.processObjects(memberList,divInfo, null, true);
                   
                 log.info("When Formula id Y process Objects size ::  " + buildDivs.size());
            }
            
            if(buildDivs!=null && buildDivs.size()> 0)
            {
                 
                returnVal =  divScheduleService.writeObjects(buildDivs, divInfo);
                
                log.info("After writeObjects result is ::  " + returnVal);
            }
            else
            {
              log.info("Not going into writeObjects result is ::  " + returnVal);
            }

        }
        else
        {
         returnVal = false;
       
        }
        if(divScheduleService.getMsg()!=null)
        {
            dataBundle.put(ReminderInterface.PROC_MSG, divScheduleService.getMsg());
        }
        else
        {
            dataBundle.put(ReminderInterface.PROC_MSG, "No error or message retrieved");
        }
       //Send Mail to whoever
  
       divScheduleService.sendNotification(dataBundle, returnVal, divInfo);
        
    }

    
    public Dividend getDivInfo() {
        return divInfo;
    }

    public void setDivInfo(Dividend divInfo) {
        this.divInfo = divInfo;
    }

    public String getTaskToRun() {
        return taskToRun;
    }

    public void setTaskToRun(String taskToRun) {
        this.taskToRun = taskToRun;
    }

    
    
    
    
    
}
