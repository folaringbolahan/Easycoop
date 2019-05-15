
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final StringExpression code;
	public final ByteExpression isDeleted;
	public final StringExpression name;
	public final IntegerExpression companyId;
	public final FloatExpression initialAmountMax;
	public final FloatExpression initialAmountMin;
	public final FloatExpression interestRateMin;
	public final FloatExpression interestRateMax;
	public final FloatExpression interestRate;
	public final IntegerExpression currencyId;
	public final IntegerExpression productTypeId;
	
	public ProductsDetachedCriteria() {
		super(com.sift.easycoopfin.models.Products.class, com.sift.easycoopfin.models.ProductsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		code = new StringExpression("code", this.getDetachedCriteria());
		isDeleted = new ByteExpression("isDeleted", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		initialAmountMax = new FloatExpression("initialAmountMax", this.getDetachedCriteria());
		initialAmountMin = new FloatExpression("initialAmountMin", this.getDetachedCriteria());
		interestRateMin = new FloatExpression("interestRateMin", this.getDetachedCriteria());
		interestRateMax = new FloatExpression("interestRateMax", this.getDetachedCriteria());
		interestRate = new FloatExpression("interestRate", this.getDetachedCriteria());
		currencyId = new IntegerExpression("currencyId", this.getDetachedCriteria());
		productTypeId = new IntegerExpression("productTypeId", this.getDetachedCriteria());
	}
	
	public ProductsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.ProductsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		code = new StringExpression("code", this.getDetachedCriteria());
		isDeleted = new ByteExpression("isDeleted", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		initialAmountMax = new FloatExpression("initialAmountMax", this.getDetachedCriteria());
		initialAmountMin = new FloatExpression("initialAmountMin", this.getDetachedCriteria());
		interestRateMin = new FloatExpression("interestRateMin", this.getDetachedCriteria());
		interestRateMax = new FloatExpression("interestRateMax", this.getDetachedCriteria());
		interestRate = new FloatExpression("interestRate", this.getDetachedCriteria());
		currencyId = new IntegerExpression("currencyId", this.getDetachedCriteria());
		productTypeId = new IntegerExpression("productTypeId", this.getDetachedCriteria());
	}
	
	public Products uniqueProducts(PersistentSession session) {
		return (Products) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Products[] listProducts(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Products[]) list.toArray(new Products[list.size()]);
	}
}

