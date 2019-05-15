package com.sift.easycoopfin.models;

import java.util.List;
import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class ProductTypeCriteria extends AbstractORMCriteria {

    public final IntegerExpression id;
    public final StringExpression name;
    public final ByteExpression attractsInterest;
    public final StringExpression code;
    public ProductTypeCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        name = new StringExpression("name", this);
        code = new StringExpression("code", this);
        attractsInterest = new ByteExpression("attractsInterest", this);
    }

    public ProductTypeCriteria(PersistentSession session) {
        this(session.createCriteria(ProductType.class));
    }

    public ProductTypeCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public ProductType uniqueProductType() {
        return (ProductType) super.uniqueResult();
    }

    public ProductType[] listProductType() {
        java.util.List list = super.list();
        return (ProductType[]) list.toArray(new ProductType[list.size()]);
    }

    public List<ProductType> listAllProductType() {
        java.util.List list = super.list();
        return (List<ProductType>) list;
    }
}
