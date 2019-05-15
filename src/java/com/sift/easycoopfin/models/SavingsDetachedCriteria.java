
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class SavingsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	public final IntegerExpression branchId;
	public final StringExpression accountNumber;
	public final IntegerExpression memberId;
	public final FloatExpression amount;
	public final IntegerExpression description;
	public final IntegerExpression userId;
	public final StringExpression referenceNumber;
	public final DateExpression trxDate;
	
	public SavingsDetachedCriteria() {
		super(com.sift.easycoopfin.models.Savings.class, com.sift.easycoopfin.models.SavingsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		branchId = new IntegerExpression("branchId", this.getDetachedCriteria());
		accountNumber = new StringExpression("accountNumber", this.getDetachedCriteria());
		memberId = new IntegerExpression("memberId", this.getDetachedCriteria());
		amount = new FloatExpression("amount", this.getDetachedCriteria());
		description = new IntegerExpression("description", this.getDetachedCriteria());
		userId = new IntegerExpression("userId", this.getDetachedCriteria());
		referenceNumber = new StringExpression("referenceNumber", this.getDetachedCriteria());
		trxDate = new DateExpression("trxDate", this.getDetachedCriteria());
	}
	
	public SavingsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.SavingsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		branchId = new IntegerExpression("branchId", this.getDetachedCriteria());
		accountNumber = new StringExpression("accountNumber", this.getDetachedCriteria());
		memberId = new IntegerExpression("memberId", this.getDetachedCriteria());
		amount = new FloatExpression("amount", this.getDetachedCriteria());
		description = new IntegerExpression("description", this.getDetachedCriteria());
		userId = new IntegerExpression("userId", this.getDetachedCriteria());
		referenceNumber = new StringExpression("referenceNumber", this.getDetachedCriteria());
		trxDate = new DateExpression("trxDate", this.getDetachedCriteria());
	}
	
	public Savings uniqueSavings(PersistentSession session) {
		return (Savings) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Savings[] listSavings(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Savings[]) list.toArray(new Savings[list.size()]);
	}
}

