
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Member;
import java.util.List;
import org.orm.*;

public interface MemberDAO {
	public Member loadMemberByORMID(int id) throws PersistentException;
	public Member getMemberByORMID(int id) throws PersistentException;
	public Member loadMemberByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member getMemberByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member loadMemberByORMID(PersistentSession session, int id) throws PersistentException;
	public Member getMemberByORMID(PersistentSession session, int id) throws PersistentException;
	public Member loadMemberByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member getMemberByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member[] listMemberByQuery(String condition, String orderBy) throws PersistentException;
        public List<Member> listMembersByQuery(String condition, String orderBy) throws PersistentException;
	public Member[] listMemberByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member[] listMemberByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
        public List<Member> listMembersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Member[] listMemberByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member loadMemberByQuery(String condition, String orderBy) throws PersistentException;
	public Member loadMemberByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member loadMemberByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
	public Member loadMemberByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException;
	public Member createMember();
	public boolean save(com.sift.easycoopfin.models.Member member) throws PersistentException;
	public boolean delete(com.sift.easycoopfin.models.Member member) throws PersistentException;
	public boolean refresh(com.sift.easycoopfin.models.Member member) throws PersistentException;
	public boolean evict(com.sift.easycoopfin.models.Member member) throws PersistentException;
	public Member loadMemberByCriteria(MemberCriteria memberCriteria);
	public Member[] listMemberByCriteria(MemberCriteria memberCriteria);
        public List<Member> listAllMembers(MemberCriteria memberCriteria);
}
