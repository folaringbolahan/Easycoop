/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.loan.dao;

import com.sift.loan.bean.LoanGuarantorChangeBean;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.loan.model.LoanGuarantorChange;


import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Nelson Akpos
 */
@Repository("loanGuarantorChangeDao")
public class LoanGuarantorChangeDaoImpl implements LoanGuarantorChangeDao {

    @Autowired
    private SessionFactory sessionFactory;
    

    public void addLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange) {
        sessionFactory.getCurrentSession().saveOrUpdate(loanGuarantorChange);
    }

    public void addLoanGuarantorChanged(ArrayList<LoanGuarantorChange> list) {
        org.hibernate.classic.Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.getCurrentSession();
            if (list != null && list.size() > 0) {
                for (LoanGuarantorChange item : list) {
                    session.saveOrUpdate(item);
                }
            }

        } catch (HibernateException e) {
            try {
                if (!tx.wasRolledBack()) {
                    tx.rollback();
                }
            } catch (RuntimeException rbe) {
                rbe.printStackTrace();
            }
            throw e;
        } catch (RuntimeException e) {
            try {
                if (!tx.wasRolledBack()) {
                    tx.rollback();
                }
            } catch (RuntimeException rbe) {
                rbe.printStackTrace();
            }
            throw e;
        } finally {
            tx = null;
        }

    }

    @SuppressWarnings("unchecked")
    public List<LoanGuarantorChange> listLoanGuarantorsChanged() {
        return (List<LoanGuarantorChange>) sessionFactory.getCurrentSession().createCriteria(LoanGuarantorChange.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<LoanGuarantorChange> listLoanGuarantorsChangedByCaseId(String loanCaseId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanGuarantorChange.class);
        criteria.add(Restrictions.eq("loanCaseId", loanCaseId));
        criteria.add(Restrictions.eq("approved", "N"));
       System.out.println("this is the list size "+criteria.list().size());
        return (List<LoanGuarantorChange>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    public List<LoanGuarantorChange> listLoanGuarantorsChangedByBranchId(String branchId) {
        //Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanGuarantorChange.class);
        // criteria.add(Restrictions.eq("branchId", branchId));
         //criteria.add(Restrictions.eq("approved", "N"));
    
  List<LoanGuarantorChange> criteria =
                sessionFactory.getCurrentSession().createCriteria(LoanGuarantorChange.class)
                .add(Restrictions.eq("branchId", branchId))
                .add(Restrictions.eq("approved", "N"))
                .setProjection(Projections.projectionList()
                .add(Projections.groupProperty("loanCaseId"),"loanCaseId")
                .add(Projections.groupProperty("createdBy"),"createdBy"))
                
                // .add(Projections.groupProperty("approved"),"approved"))
               
                //.add(Projections.groupProperty(""),"memberNo"))
                
         
               
                .setResultTransformer(Transformers.aliasToBean(LoanGuarantorChange.class)).list();
           
        
                // System.out.println("the is the size of" + criteria.list().size());
                    return criteria; //(List<LoanGuarantorChange>)criteria.list();
    }
   
    public LoanGuarantorChange getLoanGuarantorChanged(String loanCaseId) {
      return (LoanGuarantorChange) sessionFactory.getCurrentSession().get(LoanGuarantorChange.class, loanCaseId);
        //Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LoanGuarantorChange.class);
       //criteria.add(Restrictions.eq("loanCaseId", loanCaseId));
     
    }

    public void deleteLoanGuarantorChanged(LoanGuarantorChange loanGuarantorChange) {
        sessionFactory.getCurrentSession().createQuery("delete from loanGuarantorChange where id = " + loanGuarantorChange.getId()).executeUpdate();
    }
}
