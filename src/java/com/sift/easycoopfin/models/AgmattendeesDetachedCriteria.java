
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class AgmattendeesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression memberId;
	public final IntegerExpression agmsId;
	
	public AgmattendeesDetachedCriteria() {
		super(com.sift.easycoopfin.models.Agmattendees.class, com.sift.easycoopfin.models.AgmattendeesCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		memberId = new IntegerExpression("memberId", this.getDetachedCriteria());
		agmsId = new IntegerExpression("agmsId", this.getDetachedCriteria());
	}
	
	public AgmattendeesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.AgmattendeesCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		memberId = new IntegerExpression("memberId", this.getDetachedCriteria());
		agmsId = new IntegerExpression("agmsId", this.getDetachedCriteria());
	}
	
	public Agmattendees uniqueAgmattendees(PersistentSession session) {
		return (Agmattendees) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Agmattendees[] listAgmattendees(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Agmattendees[]) list.toArray(new Agmattendees[list.size()]);
	}
}

