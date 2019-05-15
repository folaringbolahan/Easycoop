
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final StringExpression name;
	public final ByteExpression attractsInterest;
	
	public ProductTypeDetachedCriteria() {
		super(com.sift.easycoopfin.models.ProductType.class, com.sift.easycoopfin.models.ProductTypeCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		attractsInterest = new ByteExpression("attractsInterest", this.getDetachedCriteria());
	}
	
	public ProductTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.ProductTypeCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		attractsInterest = new ByteExpression("attractsInterest", this.getDetachedCriteria());
	}
	
	public ProductType uniqueProductType(PersistentSession session) {
		return (ProductType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public ProductType[] listProductType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (ProductType[]) list.toArray(new ProductType[list.size()]);
	}
}

