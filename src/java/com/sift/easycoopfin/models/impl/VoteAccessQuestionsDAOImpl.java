/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.VoteAccessQuestions;
import com.sift.easycoopfin.models.VoteAccessQuestionsCriteria;
import com.sift.easycoopfin.models.dao.VoteAccessQuestionsDAO;
import java.util.List;
import org.hibernate.Query;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public class VoteAccessQuestionsDAOImpl implements VoteAccessQuestionsDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VoteAccessQuestionsDAOImpl.class);

    @Override
    public VoteAccessQuestions loadVoteAccessQuestionsByQuery(String condition, String orderBy) throws PersistentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VoteAccessQuestions loadVoteAccessQuestionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VoteAccessQuestions createVoteAccessQuestions() {
        return new com.sift.easycoopfin.models.VoteAccessQuestions();
    }

    @Override
    public VoteAccessQuestions loadVoteAccessQuestionsByCriteria(VoteAccessQuestionsCriteria voteAccessCriteria) {
        VoteAccessQuestions[] voteaccess = listVoteAccessQuestionsByCriteria(voteAccessCriteria);
        if (voteaccess == null || voteaccess.length == 0) {
            return null;
        }
        return voteaccess[0];
    }

     @Override
    public VoteAccessQuestions[] listVoteAccessQuestionsByCriteria(VoteAccessQuestionsCriteria voteaccessCriteria) {
        return voteaccessCriteria.listVoteAccessQuestions();
    }

    @Override
    public List<VoteAccessQuestions> listAllVoteAccessQuestionsByCriteria(VoteAccessQuestionsCriteria voteAccessCriteria) {
        return voteAccessCriteria.listAllVoteAccessQuestions();
    }

    @Override
    public VoteAccessQuestions loadVoteAccessQuestionsByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (VoteAccessQuestions) session.load(com.sift.easycoopfin.models.VoteAccessQuestions.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadVoteAccessQuestionsByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public VoteAccessQuestions loadVoteAccessQuestionsByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadVoteAccessQuestionsByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadVoteoptionsByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public VoteAccessQuestions getVoteAccessQuestionsByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getVoteAccessQuestionsByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getVoteAccessQuestionsByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

     @Override
    public VoteAccessQuestions getVoteAccessQuestionsByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (VoteAccessQuestions) session.get(com.sift.easycoopfin.models.VoteAccessQuestions.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getVoteAccessQuestionsByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public List<VoteAccessQuestions> listAllVoteAccessQuestionsByQuery(String condition, String orderBy) throws PersistentException {
       try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listAllVoteAccessQuestionsByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listAllVoteAccessQuestionsByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public List<VoteAccessQuestions> listAllVoteAccessQuestionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.VoteAccessQuestions as VoteAccessQuestions");
        if (condition != null) {            
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (List<VoteAccessQuestions>) list;
        } catch (Exception e) {
            _logger.error("listAllVoteAccessQuestionsByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }
}
