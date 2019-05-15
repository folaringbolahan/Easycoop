
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class UsersDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	
	public UsersDetachedCriteria() {
		super(com.sift.easycoopfin.models.Users.class, com.sift.easycoopfin.models.UsersCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
	}
	
	public UsersDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.UsersCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
	}
	
	public Users uniqueUsers(PersistentSession session) {
		return (Users) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Users[] listUsers(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Users[]) list.toArray(new Users[list.size()]);
	}
}

