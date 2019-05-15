
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class AgmsCriteria extends AbstractORMCriteria {
	public final IntegerExpression id;
	public final IntegerExpression companyId;
	public final DateExpression startDate;
	public final DateExpression endDate;
	public final ByteExpression isDeleted;
        
	
	public AgmsCriteria(Criteria criteria) {
		super(criteria);
		id = new IntegerExpression("id", this);
		companyId = new IntegerExpression("companyId", this);
		startDate = new DateExpression("startDate", this);
		endDate = new DateExpression("endDate", this);
		isDeleted = new ByteExpression("isDeleted", this);
                
	}
	
	public AgmsCriteria(PersistentSession session) {
		this(session.createCriteria(Agms.class));
	}
	
	public AgmsCriteria() throws PersistentException {
		this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
	}
	
	public Agms uniqueAgms() {
		return (Agms) super.uniqueResult();
	}
	
	public Agms[] listAgms() {
		java.util.List list = super.list();
		return (Agms[]) list.toArray(new Agms[list.size()]);
	}
        public List<Agms> listAllAgms(){
            java.util.List list = super.list();
            return (List<Agms>) list;
        }
}

