
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class BranchDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	
	public BranchDetachedCriteria() {
		super(com.sift.easycoopfin.models.Branch.class, com.sift.easycoopfin.models.BranchCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
	}
	
	public BranchDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.BranchCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
	}
	
	public Branch uniqueBranch(PersistentSession session) {
		return (Branch) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Branch[] listBranch(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Branch[]) list.toArray(new Branch[list.size()]);
	}
}

