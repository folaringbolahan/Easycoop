
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.BranchCriteria;
import com.sift.easycoopfin.models.Branch;
import org.orm.*;

public interface BranchDAO {
	public Branch loadBranchByORMID(int id) throws PersistentException;
	public Branch getBranchByORMID(int id) throws PersistentException;
	public Branch loadBranchByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch getBranchByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch loadBranchByORMID(PersistentSession session, int id) throws PersistentException;
	public Branch getBranchByORMID(PersistentSession session, int id) throws PersistentException;
	public Branch loadBranchByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch getBranchByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch[] listBranchByQuery(String condition, String orderBy) throws PersistentException;
	public Branch[] listBranchByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch[] listBranchByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Branch[] listBranchByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch loadBranchByQuery(String condition, String orderBy) throws PersistentException;
	public Branch loadBranchByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch loadBranchByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Branch loadBranchByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Branch createBranch();
	public boolean save(com.sift.easycoopfin.models.Branch branch) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Branch branch) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Branch branch) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Branch branch) throws PersistentException;
	public Branch loadBranchByCriteria(BranchCriteria branchCriteria);
	public Branch[] listBranchByCriteria(BranchCriteria branchCriteria);
}
