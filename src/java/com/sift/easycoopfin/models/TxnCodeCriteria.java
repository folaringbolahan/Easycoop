/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.AbstractORMCriteria;
import org.orm.criteria.IntegerExpression;
import org.orm.criteria.StringExpression;

/**
 *
 * @author logzy
 */
public class TxnCodeCriteria extends AbstractORMCriteria {
    public final IntegerExpression id;
	public final StringExpression transactionCode;
	public final StringExpression narrative;
	public final StringExpression description;
        
	public TxnCodeCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		transactionCode = new StringExpression("transactionCode", this);
		narrative = new StringExpression("narrative", this);
                description = new StringExpression("description", this);
	}
	
	public TxnCodeCriteria(PersistentSession session) {
		this(session.createCriteria(TxnCode.class));
	}
	
	public TxnCodeCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public TxnCode uniqueTxnCode() {
		return (TxnCode) super.uniqueResult();
	}
	
	public TxnCode[] listTxnCode() {
		java.util.List list = super.list();
		return (TxnCode[]) list.toArray(new TxnCode[list.size()]);
	}
        public List<TxnCode> listAllTxnCode() {
		java.util.List list = super.list();
		return (List<TxnCode>) list;
	}
}
