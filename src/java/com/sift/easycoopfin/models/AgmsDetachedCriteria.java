
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class AgmsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	public final DateExpression startDate;
	public final DateExpression endDate;
	public final ByteExpression isDeleted;
	
	public AgmsDetachedCriteria() {
		super(com.sift.easycoopfin.models.Agms.class, com.sift.easycoopfin.models.AgmsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		startDate = new DateExpression("startDate", this.getDetachedCriteria());
		endDate = new DateExpression("endDate", this.getDetachedCriteria());
		isDeleted = new ByteExpression("isDeleted", this.getDetachedCriteria());
	}
	
	public AgmsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.AgmsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		startDate = new DateExpression("startDate", this.getDetachedCriteria());
		endDate = new DateExpression("endDate", this.getDetachedCriteria());
		isDeleted = new ByteExpression("isDeleted", this.getDetachedCriteria());
	}
	
	public Agms uniqueAgms(PersistentSession session) {
		return (Agms) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Agms[] listAgms(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Agms[]) list.toArray(new Agms[list.size()]);
	}
}

