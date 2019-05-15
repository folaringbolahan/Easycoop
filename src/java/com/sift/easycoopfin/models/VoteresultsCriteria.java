
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class VoteresultsCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression voteId;
	public final IntegerExpression memberId;
	public final IntegerExpression voteOptionId;
	
	public VoteresultsCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		voteId = new IntegerExpression("voteId", this);
		memberId = new IntegerExpression("memberId", this);
		voteOptionId = new IntegerExpression("voteOptionId", this);
	}
	
	public VoteresultsCriteria(PersistentSession session) {
		this(session.createCriteria(Voteresults.class));
	}
	
	public VoteresultsCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Voteresults uniqueVoteresults() {
		return (Voteresults) super.uniqueResult();
	}
	
	public Voteresults[] listVoteresults() {
		java.util.List list = super.list();
		return (Voteresults[]) list.toArray(new Voteresults[list.size()]);
	}
        public List<Voteresults> listAllVoteresults() {
		java.util.List list = super.list();
		return (List<Voteresults>) list;
	}
}

