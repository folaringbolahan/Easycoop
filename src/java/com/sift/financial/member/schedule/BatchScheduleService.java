/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.schedule;

import com.sift.financial.member.ActivityLog;
import com.sift.financial.member.BatchUploadReference;
import com.sift.financial.member.Event;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.services.ManagementTempl;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author baydel200
 */
public  class BatchScheduleService extends ManagementTempl{
    
    private static final Log log = LogFactory.getLog(BatchScheduleService.class);
       
    @Autowired
    @Value("${refStatus}")
    private String freshRefSatus;
    
    @Autowired
    @Value("${procMemBatEvent}")
    private String eventShort;
    
    //get qualified records from fresh refrence
    public List getFreshReference()
    {
       return getBatchUploadReferenceDAO().getByStatus(freshRefSatus);
    }
    
     //get qualified records from fresh refrence
    public List getFreshReference(String ref)
    {
       return getBatchUploadReferenceDAO().getByStatus(freshRefSatus, ref);
    }
    

    //write log in activity for everyrecord
    public void logObjects(ActivityLog obj)
     {
      
     }
    

    public String getFreshRefSatus() {
        return freshRefSatus;
    }

    public void setFreshRefSatus(String freshRefSatus) {
        this.freshRefSatus = freshRefSatus;
    }

    public String getEventShort() {
        return eventShort;
    }

    public void setEventShort(String eventShort) {
        this.eventShort = eventShort;
    }

    public boolean closeFreshReference(BatchUploadReference curBatch, boolean close)
    {
       boolean returnVal = false;
        Session sess = null;
        
        Transaction tx = null;
        try {
            Event event = (Event)getEventDAO().findByEventShort(eventShort).get(0);
            StatusFlow statusFlow = getStatusFlowDAO().getFlowFromEventStatus(event, curBatch.getStatus());
            Status successStatus = getStatusDAO().findById(statusFlow.getStatusSuccessId());
            log.info("successStatus  ::: " + successStatus.getStatusShort());
            Status failStatus = getStatusDAO().findById(statusFlow.getStatusFailId());
            log.info("failStatus  ::: " + failStatus.getStatusShort());
            sess = getBatchUploadReferenceDAO().getSessionFactory().openSession();
            tx = sess.beginTransaction();
            
            log.info("close  ::: " + close);
            
                if(close)
                {
                    log.info("executing when close  is ::: " + close);
                        curBatch.setStatus(successStatus);
                }
                else
                {
                      log.info("executing when close  should be False ::: " + close);
                      curBatch.setStatus(failStatus);
                }
                
            getBatchUploadReferenceDAO().merge(curBatch, sess);
            
            tx.commit();
            returnVal = true;
        }
        catch(Exception ex)
        {
         tx.rollback();
         returnVal = false;
        }
        finally
        {
         sess.close();
        }
        return returnVal;
    }

}
