/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import org.orm.criteria.AbstractORMCriteria;
import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;
/**
 *
 * @author logzy
 */
public class ProductAccountValCriteria extends AbstractORMCriteria{
    public final IntegerExpression id;
	public final StringExpression productType;
	public final StringExpression productAccountTypeCode;
	public ProductAccountValCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		productAccountTypeCode = new StringExpression("productAccountTypeCode", this);
		productType = new StringExpression("productType", this);
	}
	
	public ProductAccountValCriteria(PersistentSession session) {
		this(session.createCriteria(ProductAccountVal.class));
	}
	
	public ProductAccountValCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public ProductAccountVal uniqueProductAccountVal() {
		return (ProductAccountVal) super.uniqueResult();
	}
	
	public ProductAccountVal[] listProductAccountVal() {
		java.util.List list = super.list();
		return (ProductAccountVal[]) list.toArray(new ProductAccountVal[list.size()]);
	}
        public List<ProductAccountVal> listAllProductAccountVal(){
            java.util.List list = super.list();
		return (List<ProductAccountVal>) list;
        }
}
