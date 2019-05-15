
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductAccountTypeCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final StringExpression code;
	public final StringExpression description;
	
	public ProductAccountTypeCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		code = new StringExpression("code", this);
		description = new StringExpression("description", this);
	}
	
	public ProductAccountTypeCriteria(PersistentSession session) {
		this(session.createCriteria(ProductAccountType.class));
	}
	
	public ProductAccountTypeCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public ProductAccountType uniqueProductAccountType() {
		return (ProductAccountType) super.uniqueResult();
	}
	
	public ProductAccountType[] listProductAccountType() {
		java.util.List list = super.list();
		return (ProductAccountType[]) list.toArray(new ProductAccountType[list.size()]);
	}
          public List<ProductAccountType> listAllProductAccountType(){
            java.util.List list = super.list();
		return (List<ProductAccountType>) list;
        }
}

