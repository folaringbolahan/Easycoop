
package com.sift.easycoopfin.models;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class BranchCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	
	public BranchCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		companyId = new IntegerExpression("companyId", this);
	}
	
	public BranchCriteria(PersistentSession session) {
		this(session.createCriteria(Branch.class));
	}
	
	public BranchCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Branch uniqueBranch() {
		return (Branch) super.uniqueResult();
	}
	
	public Branch[] listBranch() {
		java.util.List list = super.list();
		return (Branch[]) list.toArray(new Branch[list.size()]);
	}
}

