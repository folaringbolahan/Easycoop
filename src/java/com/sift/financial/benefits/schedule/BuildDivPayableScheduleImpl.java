/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.benefits.schedule;

import com.sift.financial.ReminderInterface;
import com.sift.financial.member.CompStockType;
import com.sift.financial.member.CompStockTypeDetail;
import com.sift.financial.member.Dividend;
import com.sift.financial.services.MailNotificationImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author baydel200
 */
public class BuildDivPayableScheduleImpl extends BuildDivPayableScheduleInterface{
    

    @Autowired
    private MailNotificationImpl emailDispatcher;
    

    @Override
    public boolean sendNotification(Map<String, Object> processInfo, boolean mailToSend, Object obj) {
       
        Dividend fileObject = (Dividend)obj;
        
        Map<String, Object> mailInfo = new HashMap<String, Object>();
        
        if(processInfo !=null)
        {
            if(processInfo.get(ReminderInterface.PROC_MSG)!=null)
            {
                mailInfo.put(ReminderInterface.PROC_MSG, processInfo.get(ReminderInterface.PROC_MSG));
            }
        }
        
        mailInfo.put(ReminderInterface.PROC_REF, fileObject.getDivNumber());
        mailInfo.put(ReminderInterface.PROC_USER, fileObject.getCreatedBy());
        
        //Add Recipients 
        Map<String, Object> recipients = new HashMap<String, Object>();
        recipients.put(ReminderInterface.CREATOR, fileObject.getCreatedBy());
        //recipients.put(ReminderInterface.NEXTLEVEL, super.getNextValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId(), keyColumn));
        recipients.put(ReminderInterface.COMPANYALL, super.getAllValidUserEmail(fileObject.getBranchId(), fileObject.getCompanyId()));
        
        emailDispatcher.sendMail(recipients, mailInfo, super.getEvenShort(), String.valueOf(mailToSend));
          	   
        return emailDispatcher.isMailOk();
    }

 
    
    
    
    

      
}
