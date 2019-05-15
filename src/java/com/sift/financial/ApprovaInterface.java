/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial;

/**
 *
 * @author baydel200
 */
public interface ApprovaInterface {
    
    public boolean approveFirstLevel(String initiator, String modifier, String approval);
    
    public boolean approveSecondLevel(String initiator, String modifier, String approval,String nextApproval);
    
}
