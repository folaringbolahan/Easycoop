
package com.sift.easycoopfin.models;

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class UsersCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	
	public UsersCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
	}
	
	public UsersCriteria(PersistentSession session) {
		this(session.createCriteria(Users.class));
	}
	
	public UsersCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Users uniqueUsers() {
		return (Users) super.uniqueResult();
	}
	
	public Users[] listUsers() {
		java.util.List list = super.list();
		return (Users[]) list.toArray(new Users[list.size()]);
	}
}

