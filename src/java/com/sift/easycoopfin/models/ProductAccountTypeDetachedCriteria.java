
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductAccountTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final StringExpression code;
	public final StringExpression description;
	
	public ProductAccountTypeDetachedCriteria() {
		super(com.sift.easycoopfin.models.ProductAccountType.class, com.sift.easycoopfin.models.ProductAccountTypeCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		code = new StringExpression("code", this.getDetachedCriteria());
		description = new StringExpression("description", this.getDetachedCriteria());
	}
	
	public ProductAccountTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.ProductAccountTypeCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		code = new StringExpression("code", this.getDetachedCriteria());
		description = new StringExpression("description", this.getDetachedCriteria());
	}
	
	public ProductAccountType uniqueProductAccountType(PersistentSession session) {
		return (ProductAccountType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public ProductAccountType[] listProductAccountType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (ProductAccountType[]) list.toArray(new ProductAccountType[list.size()]);
	}
}

