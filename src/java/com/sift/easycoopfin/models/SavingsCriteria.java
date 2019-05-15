package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class SavingsCriteria extends AbstractORMCriteria {

    public final IntegerExpression id;
    public final IntegerExpression companyId;
    public final IntegerExpression branchId;
    public final StringExpression accountNumber;
    public final IntegerExpression memberId;
    public final FloatExpression amount;
    public final IntegerExpression description;
    public final StringExpression userId;
    public final StringExpression referenceNumber;
    public final DateExpression trxDate;
    public final ByteExpression status;
    public final ByteExpression isProcessed;
    public final ByteExpression isApproved;

    public SavingsCriteria(Criteria criteria) {
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
        trxDate = new DateExpression("trxDate", this);
        status = new ByteExpression("status", this);
        isProcessed = new ByteExpression("isProcessed", this);
        isApproved = new ByteExpression("isApproved", this);
    }

    public SavingsCriteria(PersistentSession session) {
        this(session.createCriteria(Savings.class));
    }

    public SavingsCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public Savings uniqueSavings() {
        return (Savings) super.uniqueResult();
    }

    public Savings[] listSavings() {
        java.util.List list = super.list();
        return (Savings[]) list.toArray(new Savings[list.size()]);
    }

    public List<Savings> listAllSavings() {
        java.util.List list = super.list();
        return (List<Savings>) list;
    }
}
