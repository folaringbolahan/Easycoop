
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class MemberDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	
	public MemberDetachedCriteria() {
		super(com.sift.easycoopfin.models.Member.class, com.sift.easycoopfin.models.MemberCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
	}
	
	public MemberDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.MemberCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
	}
	
	public Member uniqueMember(PersistentSession session) {
		return (Member) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Member[] listMember(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Member[]) list.toArray(new Member[list.size()]);
	}
}

