
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class CompanyDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	
	public CompanyDetachedCriteria() {
		super(com.sift.easycoopfin.models.Company.class, com.sift.easycoopfin.models.CompanyCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
	}
	
	public CompanyDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.CompanyCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
	}
	
	public Company uniqueCompany(PersistentSession session) {
		return (Company) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Company[] listCompany(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Company[]) list.toArray(new Company[list.size()]);
	}
}

