/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.gl.model.Userattempts;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface VoterUserattemptdetailsinter {

   // public void updateFailAttempts(String userid);
    //public void resetFailAttempts(String userid);
    public Userattempts getUserAttempts(String userid);
}
