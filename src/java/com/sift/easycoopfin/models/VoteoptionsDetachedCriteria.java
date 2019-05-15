
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class VoteoptionsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression voteId;
	public final StringExpression voteOption;
	
	public VoteoptionsDetachedCriteria() {
		super(com.sift.easycoopfin.models.Voteoptions.class, com.sift.easycoopfin.models.VoteoptionsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		voteId = new IntegerExpression("voteId", this.getDetachedCriteria());
		voteOption = new StringExpression("voteOption", this.getDetachedCriteria());
	}
	
	public VoteoptionsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.VoteoptionsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		voteId = new IntegerExpression("voteId", this.getDetachedCriteria());
		voteOption = new StringExpression("voteOption", this.getDetachedCriteria());
	}
	
	public Voteoptions uniqueVoteoptions(PersistentSession session) {
		return (Voteoptions) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Voteoptions[] listVoteoptions(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Voteoptions[]) list.toArray(new Voteoptions[list.size()]);
	}
}

