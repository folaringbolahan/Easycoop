/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import org.orm.PersistentException;

/**
 *
 * @author Nelson Akpos
 */
public interface VotFileUploadErrorDao {
   public boolean save(com.sift.votes.model.VotFileUploadError fileUploadError) throws PersistentException;
    
}
