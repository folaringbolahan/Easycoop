
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class VoteresultsDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression voteId;
	public final IntegerExpression memberId;
	public final IntegerExpression voteOptionId;
	
	public VoteresultsDetachedCriteria() {
		super(com.sift.easycoopfin.models.Voteresults.class, com.sift.easycoopfin.models.VoteresultsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		voteId = new IntegerExpression("voteId", this.getDetachedCriteria());
		memberId = new IntegerExpression("memberId", this.getDetachedCriteria());
		voteOptionId = new IntegerExpression("voteOptionId", this.getDetachedCriteria());
	}
	
	public VoteresultsDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.VoteresultsCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		voteId = new IntegerExpression("voteId", this.getDetachedCriteria());
		memberId = new IntegerExpression("memberId", this.getDetachedCriteria());
		voteOptionId = new IntegerExpression("voteOptionId", this.getDetachedCriteria());
	}
	
	public Voteresults uniqueVoteresults(PersistentSession session) {
		return (Voteresults) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Voteresults[] listVoteresults(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Voteresults[]) list.toArray(new Voteresults[list.size()]);
	}
}

