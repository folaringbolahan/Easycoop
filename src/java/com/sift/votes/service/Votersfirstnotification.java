/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import java.util.Map;

/**
 *
 * @author kola
 */
public interface Votersfirstnotification {
     // first method
    // run thru the cred table database and pick all members whose agmid tally with the one passed as parameter and sent flag is 'N' and do 
    // mail sending
    public boolean firenotification(VoterNotificationService ntc,String mailtemplate,Map model);
    public boolean firenotification(VoterNotificationService ntc,String username,String agmdescription ,String uri,String email,String clearpass,String  startdate,String starttime,String enddate,String endtime  );
}
