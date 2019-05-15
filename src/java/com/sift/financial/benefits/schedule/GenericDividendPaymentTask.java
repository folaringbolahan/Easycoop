/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.benefits.schedule;

import com.sift.financial.member.Dividend;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author baydel200
 */

@Component
@Scope("prototype")
public class GenericDividendPaymentTask {
    
     private static final Log log = LogFactory.getLog(GenericDividendPaymentTask.class);
     
    @Autowired
    private ApplicationContext ctx;
    
    private PayDividendInterface payDividendService;

    public PayDividendInterface getPayDividendService() {
        return payDividendService;
    }

    public void setPayDividendService(PayDividendInterface payDividendService) {
        this.payDividendService = payDividendService;
    }
    
    /**private String taskToRun;

    public String getTaskToRun() {
        return taskToRun;
    }

    public void setTaskToRun(String taskToRun) {
        this.taskToRun = taskToRun;
    }
    * **/
   @Scheduled(cron="${PAYDIVCRON}")
    public void doRun() {
        log.info("Starting Dividing Payment Schedule for today " + new java.util.Date());
        boolean returnVal = false;
        //get Impl Class
       // payDividendService = (PayDividendInterface)ctx.getBean(taskToRun);
        
        Map<String, Object> theList = null;
       
        //get the stocks for the company
         log.info("Getting Dividend Payment List Schedule now");
        List divList = payDividendService.getQualifiedDiv(null);
       
        //get the Div List
        if(divList!=null && divList.size()> 0)
        {
             log.info("Total  Dividend List due today :" + divList.size());
             
               for (Object obj: divList)
               {
                // evaluate each member stock
                theList = payDividendService.makePayment(obj, null,  false);

                 //Send Mail to whoever
                payDividendService.sendNotification(theList, payDividendService.isPayOk(), obj);
                
               } 
        }
        else
        {
        
          log.info("Total  Dividend List due today is 0");
            
        }
        
        
    }
    
}
