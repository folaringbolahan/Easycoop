
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.VotesCriteria;
import com.sift.easycoopfin.models.Votes;
import java.util.List;
import org.orm.*;

public interface VotesDAO {
	public Votes loadVotesByORMID(int id) throws PersistentException;
	public Votes getVotesByORMID(int id) throws PersistentException;
	public Votes loadVotesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes getVotesByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes loadVotesByORMID(PersistentSession session, int id) throws PersistentException;
	public Votes getVotesByORMID(PersistentSession session, int id) throws PersistentException;
	public Votes loadVotesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes getVotesByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes[] listVotesByQuery(String condition, String orderBy) throws PersistentException;
	public Votes[] listVotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes[] listVotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Votes[] listVotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes loadVotesByQuery(String condition, String orderBy) throws PersistentException;
	public Votes loadVotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes loadVotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Votes loadVotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Votes createVotes();
	public boolean save(com.sift.easycoopfin.models.Votes votes) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Votes votes) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Votes votes) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Votes votes) throws PersistentException;
	public Votes loadVotesByCriteria(VotesCriteria votesCriteria);
	public Votes[] listVotesByCriteria(VotesCriteria votesCriteria);
        public List<Votes> listAllVotesByCriteria(VotesCriteria votesCriteria) ;
}
