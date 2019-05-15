package com.sift.easycoopfin.models.impl;

import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Member;
import org.orm.*;
import org.hibernate.Query;
import java.util.List;

public class MemberDAOImpl implements com.sift.easycoopfin.models.dao.MemberDAO {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(MemberDAOImpl.class);

    public Member loadMemberByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadMemberByORMID(session, id);
        } catch (Exception e) {
            _logger.error("loadMemberByORMID(int id)", e);
            throw new PersistentException(e);
        }/**finally{
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().close();
        }**/
    }

    public Member getMemberByORMID(int id) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getMemberByORMID(session, id);
        } catch (Exception e) {
            _logger.error("getMemberByORMID(int id)", e);
            throw new PersistentException(e);
        }
    }

    public Member loadMemberByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadMemberByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("loadMemberByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Member getMemberByORMID(int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return getMemberByORMID(session, id, lockMode);
        } catch (Exception e) {
            _logger.error("getMemberByORMID(int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Member loadMemberByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (Member) session.load(com.sift.easycoopfin.models.Member.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("loadMemberByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public Member getMemberByORMID(PersistentSession session, int id) throws PersistentException {
        try {
            return (Member) session.get(com.sift.easycoopfin.models.Member.class, new Integer(id));
        } catch (Exception e) {
            _logger.error("getMemberByORMID(PersistentSession session, int id)", e);
            throw new PersistentException(e);
        }
    }

    public Member loadMemberByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (Member) session.load(com.sift.easycoopfin.models.Member.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("loadMemberByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Member getMemberByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            return (Member) session.get(com.sift.easycoopfin.models.Member.class, new Integer(id), lockMode);
        } catch (Exception e) {
            _logger.error("getMemberByORMID(PersistentSession session, int id, org.hibernate.LockMode lockMode)", e);
            throw new PersistentException(e);
        }
    }

    public Member[] listMemberByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listMemberByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listMemberByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Member[] listMemberByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listMemberByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("listMemberByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Member[] listMemberByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Member as Member");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (Member[]) list.toArray(new Member[list.size()]);
        } catch (Exception e) {
            _logger.error("listMemberByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Member[] listMemberByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Member as Member");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            query.setLockMode("this", lockMode);
            List list = query.list();
            return (Member[]) list.toArray(new Member[list.size()]);
        } catch (Exception e) {
            _logger.error("listMemberByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Member loadMemberByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadMemberByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("loadMemberByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Member loadMemberByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return loadMemberByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("loadMemberByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Member loadMemberByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        Member[] members = listMemberByQuery(session, condition, orderBy);
        if (members != null && members.length > 0) {
            return members[0];
        } else {
            return null;
        }
    }

    public Member loadMemberByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        Member[] members = listMemberByQuery(session, condition, orderBy, lockMode);
        if (members != null && members.length > 0) {
            return members[0];
        } else {
            return null;
        }
    }

    public static java.util.Iterator iterateMemberByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateMemberByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("iterateMemberByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateMemberByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return iterateMemberByQuery(session, condition, orderBy, lockMode);
        } catch (Exception e) {
            _logger.error("iterateMemberByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateMemberByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Member as Member");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            return query.iterate();
        } catch (Exception e) {
            _logger.error("iterateMemberByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public static java.util.Iterator iterateMemberByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.models.Member as Member");
        if (condition != null) {
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            query.setLockMode("this", lockMode);
            return query.iterate();
        } catch (Exception e) {
            _logger.error("iterateMemberByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    public Member createMember() {
        return new com.sift.easycoopfin.models.Member();
    }

    public boolean save(com.sift.easycoopfin.models.Member member) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().saveObject(member);
            return true;
        } catch (Exception e) {
            _logger.error("save(com.sift.models.Member member)", e);
            throw new PersistentException(e);
        }
    }

    public boolean delete(com.sift.easycoopfin.models.Member member) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().deleteObject(member);
            return true;
        } catch (Exception e) {
            _logger.error("delete(com.sift.models.Member member)", e);
            throw new PersistentException(e);
        }
    }

    public boolean refresh(com.sift.easycoopfin.models.Member member) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().refresh(member);
            return true;
        } catch (Exception e) {
            _logger.error("refresh(com.sift.models.Member member)", e);
            throw new PersistentException(e);
        }
    }

    public boolean evict(com.sift.easycoopfin.models.Member member) throws PersistentException {
        try {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().evict(member);
            return true;
        } catch (Exception e) {
            _logger.error("evict(com.sift.models.Member member)", e);
            throw new PersistentException(e);
        }
    }

    public Member loadMemberByCriteria(MemberCriteria memberCriteria) {
        Member[] members = listMemberByCriteria(memberCriteria);
        if (members == null || members.length == 0) {
            return null;
        }
        return members[0];
    }

    public Member[] listMemberByCriteria(MemberCriteria memberCriteria) {
        return memberCriteria.listMember();
    }

    @Override
    public List<Member> listAllMembers(MemberCriteria memberCriteria) {
        return memberCriteria.listAllMember();
    }

    @Override
    public List<Member> listMembersByQuery(String condition, String orderBy) throws PersistentException {
        try {
            PersistentSession session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
            return listMembersByQuery(session, condition, orderBy);
        } catch (Exception e) {
            _logger.error("listMemberByQuery(String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Member> listMembersByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
        StringBuffer sb = new StringBuffer("From com.sift.easycoopfin.models.Member as m");
        if (condition != null) {            
            sb.append(" Where ").append(condition);
        }
        if (orderBy != null) {
            sb.append(" Order By ").append(orderBy);
        }
        try {
            Query query = session.createQuery(sb.toString());
            List list = query.list();
            return (List<Member>) list;
        } catch (Exception e) {
            _logger.error("listMembersByQuery(PersistentSession session, String condition, String orderBy)", e);
            throw new PersistentException(e);
        }
        
    }
}
