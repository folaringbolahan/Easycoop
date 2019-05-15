
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductsCriteria extends AbstractORMCriteria {
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
	public final IntegerExpression branchId;
	//public final IntegerExpression productTypeId;
	public final StringExpression productTypeCode;
        public final StringExpression loanTypeCode;
        public final ByteExpression isActive;
        
	public ProductsCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		code = new StringExpression("code", this);
		isDeleted = new ByteExpression("isDeleted", this);
		name = new StringExpression("name", this);
		companyId = new IntegerExpression("companyId", this);
		initialAmountMax = new FloatExpression("initialAmountMax", this);
		initialAmountMin = new FloatExpression("initialAmountMin", this);
		interestRateMin = new FloatExpression("interestRateMin", this);
		interestRateMax = new FloatExpression("interestRateMax", this);
		interestRate = new FloatExpression("interestRate", this);
		branchId = new IntegerExpression("branchId", this);
		//productTypeId = new IntegerExpression("productTypeId", this);
                productTypeCode = new StringExpression("productTypeCode", this);
                loanTypeCode = new StringExpression("loanTypeCode", this);
                isActive = new ByteExpression("isActive",this);
	}
	
	public ProductsCriteria(PersistentSession session) {
		this(session.createCriteria(Products.class));
	}
	
	public ProductsCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Products uniqueProducts() {
		return (Products) super.uniqueResult();
	}
	
	public Products[] listProducts() {
		java.util.List list = super.list();
		return (Products[]) list.toArray(new Products[list.size()]);
	}
          public List<Products> listAllProducts(){
            java.util.List list = super.list();
		return (List<Products>) list;
        }
}

