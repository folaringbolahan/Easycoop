/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.AbstractORMCriteria;
import org.orm.criteria.IntegerExpression;
import org.orm.criteria.StringExpression;

/**
 *
 * @author logzy
 */
public class VoteAccessQuestionsCriteria extends AbstractORMCriteria {

    public final IntegerExpression id;
    public final StringExpression question;
    public final StringExpression code;
    public final StringExpression fieldType;

    public VoteAccessQuestionsCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        question = new StringExpression("question", this);
        code = new StringExpression("code", this);
        fieldType = new StringExpression("fieldType", this);
    }
    public VoteAccessQuestionsCriteria(PersistentSession session) {
		this(session.createCriteria(Voteoptions.class));
	}
	
	public VoteAccessQuestionsCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public VoteAccessQuestions uniqueVoteAccessQuestions() {
		return (VoteAccessQuestions) super.uniqueResult();
	}
	
	public VoteAccessQuestions[] listVoteAccessQuestions() {
		java.util.List list = super.list();
		return (VoteAccessQuestions[]) list.toArray(new VoteAccessQuestions[list.size()]);
	}
        public List<VoteAccessQuestions> listAllVoteAccessQuestions() {
		java.util.List list = super.list();
		return (List<VoteAccessQuestions>) list;
	}
}
