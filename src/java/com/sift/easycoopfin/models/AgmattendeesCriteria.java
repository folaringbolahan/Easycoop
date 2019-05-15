
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class AgmattendeesCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression memberId;
	public final IntegerExpression agmsId;
	public final StringExpression participantName;
        
	public AgmattendeesCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		memberId = new IntegerExpression("memberId", this);
		agmsId = new IntegerExpression("agmsId", this);
                participantName = new StringExpression("participantName", this);
	}
	
	public AgmattendeesCriteria(PersistentSession session) {
		this(session.createCriteria(Agmattendees.class));
	}
	
	public AgmattendeesCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Agmattendees uniqueAgmattendees() {
		return (Agmattendees) super.uniqueResult();
	}
	
	public Agmattendees[] listAgmattendees() {
		java.util.List list = super.list();
		return (Agmattendees[]) list.toArray(new Agmattendees[list.size()]);
	}
        public List<Agmattendees> listParticipants() {
		java.util.List list = super.list();
		return (List<Agmattendees>) list;
	}
}

