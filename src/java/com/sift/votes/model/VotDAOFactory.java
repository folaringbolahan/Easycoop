/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import com.sift.votes.dao.VotFileUploadDao;
import com.sift.votes.dao.VotFileUploadErrorDao;
import com.sift.votes.dao.VotMembersDao;
import com.sift.votes.dao.VotMembersErrorsDao;

/**
 *
 * @author Nelson Akpos
 */
public abstract class VotDAOFactory {
    private static VotDAOFactory _factory = new VotDAOFactoryImpl();
	
	public static VotDAOFactory getVotDAOFactory() {
		return _factory;
	}
        public abstract VotFileUploadDao getVotFileUploadDAO();
       public abstract VotMembersDao getVotMembersDAO();
       public abstract VotMembersErrorsDao getVotMembersErrorDAO();
      public abstract VotFileUploadErrorDao getFileUploadErrorDAO();
   
         
   
       
}
