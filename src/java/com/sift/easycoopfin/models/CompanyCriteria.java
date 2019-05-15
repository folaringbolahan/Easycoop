
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class CompanyCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
        public final StringExpression phoneNumber;
	 public final StringExpression emailAddress;
	public CompanyCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		companyId = new IntegerExpression("companyId", this);
                phoneNumber = new StringExpression("phoneNumber",this);
                emailAddress= new StringExpression("emailAddress",this);
	}
	
	public CompanyCriteria(PersistentSession session) {
		this(session.createCriteria(Company.class));
	}
	
	public CompanyCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Company uniqueCompany() {
		return (Company) super.uniqueResult();
	}
	
	public Company[] listCompany() {
		java.util.List list = super.list();
		return (Company[]) list.toArray(new Company[list.size()]);
	}
        public List<Company> listCompanyies() {
		java.util.List list = super.list();
		return (List<Company>) list;
	}
}

