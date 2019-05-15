
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductAccountDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression productId;
	public final IntegerExpression productAccountTypeId;
	public final StringExpression glAccountNumber;
	
	public ProductAccountDetachedCriteria() {
		super(com.sift.easycoopfin.models.ProductAccount.class, com.sift.easycoopfin.models.ProductAccountCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		productId = new IntegerExpression("productId", this.getDetachedCriteria());
		productAccountTypeId = new IntegerExpression("productAccountTypeId", this.getDetachedCriteria());
		glAccountNumber = new StringExpression("glAccountNumber", this.getDetachedCriteria());
	}
	
	public ProductAccountDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.ProductAccountCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		productId = new IntegerExpression("productId", this.getDetachedCriteria());
		productAccountTypeId = new IntegerExpression("productAccountTypeId", this.getDetachedCriteria());
		glAccountNumber = new StringExpression("glAccountNumber", this.getDetachedCriteria());
	}
	
	public ProductAccount uniqueProductAccount(PersistentSession session) {
		return (ProductAccount) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public ProductAccount[] listProductAccount(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (ProductAccount[]) list.toArray(new ProductAccount[list.size()]);
	}
}

