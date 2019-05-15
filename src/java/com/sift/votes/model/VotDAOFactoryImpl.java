/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import com.sift.votes.dao.VotFileUploadDao;
import com.sift.votes.dao.VotFileUploadDaoImpl;
import com.sift.votes.dao.VotFileUploadErrorDao;
import com.sift.votes.dao.VotFileUploadErrorDaoImpl;
import com.sift.votes.dao.VotMembersDao;
import com.sift.votes.dao.VotMembersDaoImpl;
import com.sift.votes.dao.VotMembersErrorsDao;
import com.sift.votes.dao.VotMembersErrorsDaoImpl;

/**
 *
 * @author Nelson Akpos
 */
public class VotDAOFactoryImpl extends VotDAOFactory{
     private VotFileUploadDao _fileUploadDAO = new VotFileUploadDaoImpl();

    public VotFileUploadDao getVotFileUploadDAO() {
        return _fileUploadDAO;
    }
     private VotMembersErrorsDao _votmembersErrorDAO = new VotMembersErrorsDaoImpl();
     public VotMembersErrorsDao getVotMembersErrorDAO() {
       return _votmembersErrorDAO;
    }
    private VotMembersDao _votmembersDAO = new VotMembersDaoImpl();
    public VotMembersDao getVotMembersDAO() {
        return _votmembersDAO;
    }
     private VotFileUploadErrorDao _fileUploadErrorDAO = new VotFileUploadErrorDaoImpl();

    public VotFileUploadErrorDao getFileUploadErrorDAO() {
        return _fileUploadErrorDAO;
    }
}
