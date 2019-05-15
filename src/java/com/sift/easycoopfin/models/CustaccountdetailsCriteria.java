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
public class CustaccountdetailsCriteria extends AbstractORMCriteria {

    public final StringExpression id;
    public final IntegerExpression companyId;
    public final StringExpression Product;
    public final StringExpression Title;
    public final IntegerExpression branchId;

    public CustaccountdetailsCriteria(Criteria criteria) {
        super(criteria);
        id = new StringExpression("id", this);
        companyId = new IntegerExpression("companyId", this);
        Product = new StringExpression("Product", this);
        Title = new StringExpression("Title", this);
        branchId = new IntegerExpression("branchId", this);
    }

    public CustaccountdetailsCriteria(PersistentSession session) {
        this(session.createCriteria(Custaccountdetails.class));
    }

    public CustaccountdetailsCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public Custaccountdetails uniqueCustaccountdetails() {
        return (Custaccountdetails) super.uniqueResult();
    }

    public Custaccountdetails[] listCustaccountdetails() {
        java.util.List list = super.list();
        return (Custaccountdetails[]) list.toArray(new Custaccountdetails[list.size()]);
    }

    public List<Custaccountdetails> listAllCustaccountdetails() {
        java.util.List list = super.list();
        return (List<Custaccountdetails>) list;
    }
}
