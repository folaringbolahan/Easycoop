/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

/**
 *
 * @author logzy
 */
public class AgmProxyCriteria extends AbstractORMCriteria {

    public final IntegerExpression id;
    public final IntegerExpression companyId;
    public final IntegerExpression memberId;
    public final IntegerExpression agmId;
    public final StringExpression firstName;
    public final StringExpression middleName;
    public final StringExpression surname;
    public final StringExpression gender;
    public final StringExpression address;
    public final StringExpression phoneNumber;
    public final StringExpression emailAddress;

    public AgmProxyCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        companyId = new IntegerExpression("companyId", this);
        memberId = new IntegerExpression("memberId", this);
        agmId = new IntegerExpression("agmId", this);
        firstName = new StringExpression("firstName", this);
        middleName = new StringExpression("middleName", this);
        surname = new StringExpression("surname", this);
        gender = new StringExpression("gender", this);
        address = new StringExpression("address", this);
        phoneNumber = new StringExpression("phoneNumber", this);
        emailAddress = new StringExpression("emailAddress", this);
    }

    public AgmProxyCriteria(PersistentSession session) {
        this(session.createCriteria(Member.class));
    }

    public AgmProxyCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public AgmProxy uniqueAgmProxy() {
        return (AgmProxy) super.uniqueResult();
    }

    public AgmProxy[] listAgmProxy() {
        java.util.List list = super.list();
        return (AgmProxy[]) list.toArray(new AgmProxy[list.size()]);
    }

    public List<AgmProxy> listAllAgmProxy() {
        java.util.List list = super.list();
        return (List<AgmProxy>) list;
    }
}
