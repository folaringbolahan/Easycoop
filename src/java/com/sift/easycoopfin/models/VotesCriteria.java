
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class VotesCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	public final StringExpression description;
	public final StringExpression title;
	public final DateExpression voteDate;
	public final IntegerExpression agmId;
	public VotesCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		companyId = new IntegerExpression("companyId", this);
		description = new StringExpression("description", this);
		title = new StringExpression("title", this);
		voteDate = new DateExpression("voteDate", this);
                agmId = new IntegerExpression("agmId", this);
	}
	
	public VotesCriteria(PersistentSession session) {
		this(session.createCriteria(Votes.class));
	}
	
	public VotesCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Votes uniqueVotes() {
		return (Votes) super.uniqueResult();
	}
	
	public Votes[] listVotes() {
		java.util.List list = super.list();
		return (Votes[]) list.toArray(new Votes[list.size()]);
	}
         public List<Votes> listAllVotes(){
            java.util.List list = super.list();
            return (List<Votes>) list;
        }
}

