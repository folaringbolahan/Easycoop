/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.benefits.schedule;

import com.sift.financial.member.Dividend;
import java.util.List;
import java.util.Map;

/**
 *
 * @author baydel200
 */
public abstract class PayDividendInterface extends BenefitScheduleService {
    
    private String evenShort;
    private  boolean payOk;
    
     //get div due for payment and approved
    public abstract List getQualifiedDiv(String date);
   
     //do  processing
    public  abstract Map<String,Object> makePayment(Object obj,Object fileInfo, boolean passOk);

    /*  
       doNotification
    */
    public  abstract boolean sendNotification(Map<String, Object> processInfo, boolean mailToSend, Object obj);
    

    public String getEvenShort() {
    return evenShort;
    }

    public void setEvenShort(String evenShort) {
    this.evenShort = evenShort;
    }

    public boolean isPayOk() {
        return payOk;
    }

    public void setPayOk(boolean payOk) {
        this.payOk = payOk;
    }
    
    
}
