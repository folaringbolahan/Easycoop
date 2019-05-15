/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kola
 */
@Service("votersnotificationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VotersnotificationServiceImpl implements VotersnotificationService{
     
    public boolean firenotification(VoterNotificationService ntc,String mailtemplate,Map model,String emailaddress, String title)
    {
        boolean result = false;
       // System.out.println("inside voting notification reminder 2...");
        result = ntc.preparemailandsend(emailaddress, title, mailtemplate, model);
        //System.out.println("inside voting email sende..." + emailaddress);
        return result;
    }
}
