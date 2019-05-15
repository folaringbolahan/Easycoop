
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.CompanyCriteria;
import com.sift.easycoopfin.models.Company;
import java.util.List;
import org.orm.*;

public interface CompanyDAO {
	public Company loadCompanyByORMID(int id) throws PersistentException;
	public Company getCompanyByORMID(int id) throws PersistentException;
	public Company loadCompanyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company getCompanyByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company loadCompanyByORMID(PersistentSession session, int id) throws PersistentException;
	public Company getCompanyByORMID(PersistentSession session, int id) throws PersistentException;
	public Company loadCompanyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company getCompanyByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company[] listCompanyByQuery(String condition, String orderBy) throws PersistentException;
        public List<Company> listAllCompanyByQuery(String condition, String orderBy) throws PersistentException;
	public Company[] listCompanyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company[] listCompanyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Company[] listCompanyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company loadCompanyByQuery(String condition, String orderBy) throws PersistentException;
	public Company loadCompanyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company loadCompanyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Company loadCompanyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Company createCompany();
	public boolean save(com.sift.easycoopfin.models.Company company) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Company company) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Company company) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Company company) throws PersistentException;
	public Company loadCompanyByCriteria(CompanyCriteria companyCriteria);
	public Company[] listCompanyByCriteria(CompanyCriteria companyCriteria);
        public List<Company> listCompaniesByCriteria(CompanyCriteria companyCriteria);
}
