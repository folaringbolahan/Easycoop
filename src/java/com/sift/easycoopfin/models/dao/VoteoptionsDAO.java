
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.VoteoptionsCriteria;
import com.sift.easycoopfin.models.Voteoptions;
import java.util.List;
import org.orm.*;

public interface VoteoptionsDAO {
	public Voteoptions loadVoteoptionsByORMID(int id) throws PersistentException;
	public Voteoptions getVoteoptionsByORMID(int id) throws PersistentException;
	public Voteoptions loadVoteoptionsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions getVoteoptionsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions loadVoteoptionsByORMID(PersistentSession session, int id) throws PersistentException;
	public Voteoptions getVoteoptionsByORMID(PersistentSession session, int id) throws PersistentException;
	public Voteoptions loadVoteoptionsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions getVoteoptionsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions[] listVoteoptionsByQuery(String condition, String orderBy) throws PersistentException;
	public Voteoptions[] listVoteoptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions[] listVoteoptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Voteoptions[] listVoteoptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions loadVoteoptionsByQuery(String condition, String orderBy) throws PersistentException;
	public Voteoptions loadVoteoptionsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions loadVoteoptionsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Voteoptions loadVoteoptionsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteoptions createVoteoptions();
	public boolean save(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Voteoptions voteoptions) throws PersistentException;
	public Voteoptions loadVoteoptionsByCriteria(VoteoptionsCriteria voteoptionsCriteria);
	public Voteoptions[] listVoteoptionsByCriteria(VoteoptionsCriteria voteoptionsCriteria);
        public List<Voteoptions> listAllVoteoptionsByCriteria(VoteoptionsCriteria voteoptionsCriteria);
}
