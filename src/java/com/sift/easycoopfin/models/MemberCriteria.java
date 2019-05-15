
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class MemberCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
        public final IntegerExpression branchId;
        public final IntegerExpression statusId;
        public final StringExpression firstName;
        public final StringExpression middleName;
	public final StringExpression surname;
        public final StringExpression gender;
        public final StringExpression memberNo;
	public MemberCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		companyId = new IntegerExpression("companyId", this);
                branchId = new IntegerExpression("branchId", this);
                statusId = new IntegerExpression("statusId", this);;
                firstName = new StringExpression("firstName",this);
                middleName = new StringExpression("middleName",this);
                surname = new StringExpression("surname",this);
                gender = new StringExpression("gender",this);
                memberNo = new StringExpression("memberNo",this);
	}
	
	public MemberCriteria(PersistentSession session) {
		this(session.createCriteria(Member.class));
	}
	
	public MemberCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Member uniqueMember() {
		return (Member) super.uniqueResult();
	}
	
	public Member[] listMember() {
		java.util.List list = super.list();
		return (Member[]) list.toArray(new Member[list.size()]);
	}
        public List<Member> listAllMember() {
		java.util.List list = super.list();
		return (List<Member>) list;
	}
}

