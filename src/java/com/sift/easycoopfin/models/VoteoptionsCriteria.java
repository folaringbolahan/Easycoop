
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class VoteoptionsCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression voteId;
	public final StringExpression voteOption;
	
	public VoteoptionsCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		voteId = new IntegerExpression("voteId", this);
		voteOption = new StringExpression("voteOption", this);
	}
	
	public VoteoptionsCriteria(PersistentSession session) {
		this(session.createCriteria(Voteoptions.class));
	}
	
	public VoteoptionsCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Voteoptions uniqueVoteoptions() {
		return (Voteoptions) super.uniqueResult();
	}
	
	public Voteoptions[] listVoteoptions() {
		java.util.List list = super.list();
		return (Voteoptions[]) list.toArray(new Voteoptions[list.size()]);
	}
        public List<Voteoptions> listAllVoteoptions() {
		java.util.List list = super.list();
		return (List<Voteoptions>) list;
	}
}

