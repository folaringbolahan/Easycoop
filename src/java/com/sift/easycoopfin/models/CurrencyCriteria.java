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
import org.orm.criteria.ByteExpression;
import org.orm.criteria.IntegerExpression;
import org.orm.criteria.StringExpression;

/**
 *
 * @author logzy
 */
public class CurrencyCriteria extends AbstractORMCriteria  {
    public final IntegerExpression id;
    public final StringExpression name;
    public final StringExpression code;
    
    public CurrencyCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        name = new StringExpression("name", this);
        code = new StringExpression("code", this);
    }

    public CurrencyCriteria(PersistentSession session) {
        this(session.createCriteria(Currency.class));
    }

    public CurrencyCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public Currency uniqueCurrency() {
        return (Currency) super.uniqueResult();
    }

    public Currency[] listCurrency() {
        java.util.List list = super.list();
        return (Currency[]) list.toArray(new Currency[list.size()]);
    }

    public List<Currency> listAllCurrency() {
        java.util.List list = super.list();
        return (List<Currency>) list;
    }
}
