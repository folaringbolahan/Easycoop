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
public class SavingsErrorCriteria extends AbstractORMCriteria {

    public final IntegerExpression id;
    public final IntegerExpression companyId;
    public final IntegerExpression branchId;
    public final StringExpression accountNumber;
    public final IntegerExpression memberId;
    public final FloatExpression amount;
    public final IntegerExpression description;
    public final StringExpression userId;
    public final StringExpression referenceNumber;
    public final StringExpression errorMessage;
    public final DateExpression trxDate;
    public final ByteExpression status;

    public SavingsErrorCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        companyId = new IntegerExpression("companyId", this);
        branchId = new IntegerExpression("branchId", this);
        accountNumber = new StringExpression("accountNumber", this);
        memberId = new IntegerExpression("memberId", this);
        amount = new FloatExpression("amount", this);
        description = new IntegerExpression("description", this);
        userId = new StringExpression("userId", this);
        referenceNumber = new StringExpression("referenceNumber", this);
        errorMessage = new StringExpression("errorMessage", this);
        trxDate = new DateExpression("trxDate", this);
        status = new ByteExpression("status", this);
    }

    public SavingsErrorCriteria(PersistentSession session) {
        this(session.createCriteria(SavingsError.class));
    }

    public SavingsErrorCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public SavingsError uniqueSavingsError() {
        return (SavingsError) super.uniqueResult();
    }

    public SavingsError[] listSavingsError() {
        java.util.List list = super.list();
        return (SavingsError[]) list.toArray(new SavingsError[list.size()]);
    }

    public List<SavingsError> listAllSavingsError() {
        java.util.List list = super.list();
        return (List<SavingsError>) list;
    }
}
