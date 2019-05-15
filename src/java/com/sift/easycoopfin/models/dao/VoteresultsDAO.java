
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.Voteresults;
import com.sift.easycoopfin.models.VoteresultsCriteria;
import java.util.List;
import org.orm.*;

public interface VoteresultsDAO {
	public Voteresults loadVoteresultsByORMID(int id) throws PersistentException;
	public Voteresults getVoteresultsByORMID(int id) throws PersistentException;
	public Voteresults loadVoteresultsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults getVoteresultsByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults loadVoteresultsByORMID(PersistentSession session, int id) throws PersistentException;
	public Voteresults getVoteresultsByORMID(PersistentSession session, int id) throws PersistentException;
	public Voteresults loadVoteresultsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults getVoteresultsByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults[] listVoteresultsByQuery(String condition, String orderBy) throws PersistentException;
	public Voteresults[] listVoteresultsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults[] listVoteresultsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Voteresults[] listVoteresultsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults loadVoteresultsByQuery(String condition, String orderBy) throws PersistentException;
	public Voteresults loadVoteresultsByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults loadVoteresultsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Voteresults loadVoteresultsByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Voteresults createVoteresults();
	public boolean save(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Voteresults voteresults) throws PersistentException;
	public Voteresults loadVoteresultsByCriteria(VoteresultsCriteria voteresultsCriteria);
	public Voteresults[] listVoteresultsByCriteria(VoteresultsCriteria voteresultsCriteria);
        public List<Voteresults> listAllVoteresultsByCriteria(VoteresultsCriteria voteresultsCriteria);
}
