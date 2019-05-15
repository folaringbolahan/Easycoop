
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class VotesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	public final StringExpression description;
	public final StringExpression comments;
	public final DateExpression voteDate;
	
	public VotesDetachedCriteria() {
		super(com.sift.easycoopfin.models.Votes.class, com.sift.easycoopfin.models.VotesCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		description = new StringExpression("description", this.getDetachedCriteria());
		comments = new StringExpression("comments", this.getDetachedCriteria());
		voteDate = new DateExpression("voteDate", this.getDetachedCriteria());
	}
	
	public VotesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, com.sift.easycoopfin.models.VotesCriteria.class);
		id = new IntegerExpression("id", this.getDetachedCriteria());
		companyId = new IntegerExpression("companyId", this.getDetachedCriteria());
		description = new StringExpression("description", this.getDetachedCriteria());
		comments = new StringExpression("comments", this.getDetachedCriteria());
		voteDate = new DateExpression("voteDate", this.getDetachedCriteria());
	}
	
	public Votes uniqueVotes(PersistentSession session) {
		return (Votes) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Votes[] listVotes(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Votes[]) list.toArray(new Votes[list.size()]);
	}
}

