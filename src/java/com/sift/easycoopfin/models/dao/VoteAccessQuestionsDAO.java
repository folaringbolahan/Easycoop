/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.VoteAccessQuestions;
import com.sift.easycoopfin.models.VoteAccessQuestionsCriteria;
import java.util.List;
import org.orm.*;

/**
 *
 * @author logzy
 */
public interface VoteAccessQuestionsDAO {

    public VoteAccessQuestions[] listVoteAccessQuestionsByCriteria(VoteAccessQuestionsCriteria voteAccessQuestionsCriteria);

    public VoteAccessQuestions getVoteAccessQuestionsByORMID(PersistentSession session, int id) throws PersistentException;

    public VoteAccessQuestions loadVoteAccessQuestionsByORMID(int id) throws PersistentException;

    public VoteAccessQuestions loadVoteAccessQuestionsByORMID(PersistentSession session, int id) throws PersistentException;

    public VoteAccessQuestions getVoteAccessQuestionsByORMID(int id) throws PersistentException;

    public VoteAccessQuestions loadVoteAccessQuestionsByQuery(String condition, String orderBy) throws PersistentException;

    public VoteAccessQuestions loadVoteAccessQuestionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;

    public VoteAccessQuestions createVoteAccessQuestions();

    public VoteAccessQuestions loadVoteAccessQuestionsByCriteria(VoteAccessQuestionsCriteria voteAccessCriteria);

    public List<VoteAccessQuestions> listAllVoteAccessQuestionsByCriteria(VoteAccessQuestionsCriteria voteAccessCriteria);

    public List<VoteAccessQuestions> listAllVoteAccessQuestionsByQuery(String condition, String orderBy) throws PersistentException;

    public List<VoteAccessQuestions> listAllVoteAccessQuestionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
}
