
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductAccountCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression productId;
	public final StringExpression productAccountTypeCode;
	public final StringExpression glAccountNumber;
        public final StringExpression branchId;
	
	public ProductAccountCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		productId = new IntegerExpression("productId", this);
		productAccountTypeCode = new StringExpression("productAccountTypeCode", this);
		glAccountNumber = new StringExpression("glAccountNumber", this);
                branchId = new StringExpression("branchId", this);
	}
	
	public ProductAccountCriteria(PersistentSession session) {
		this(session.createCriteria(ProductAccount.class));
	}
	
	public ProductAccountCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public ProductAccount uniqueProductAccount() {
		return (ProductAccount) super.uniqueResult();
	}
	
	public ProductAccount[] listProductAccount() {
		java.util.List list = super.list();
		return (ProductAccount[]) list.toArray(new ProductAccount[list.size()]);
	}
        public List<ProductAccount> listAllProductAccount(){
            java.util.List list = super.list();
		return (List<ProductAccount>) list;
        }
}

