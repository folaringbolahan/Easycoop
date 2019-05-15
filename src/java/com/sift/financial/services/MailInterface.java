/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.services;

import java.util.Map;

/**
 *
 * @author baydel200
 */
public interface MailInterface {
    
    public Map<String, Object>  getMailRecipient(Object obj, String action);
    
}
