/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import org.orm.criteria.AbstractORMCriteria;


import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class FileUploadCriteria extends AbstractORMCriteria {

    public final IntegerExpression id;
    public final StringExpression referenceNmber;
    public final ByteExpression status;
    public final StringExpression fileName;
    public final StringExpression location;
    public final IntegerExpression companyId;
    public final IntegerExpression branchId;
    public final IntegerExpression productId;
    public final ByteExpression isVerified;
    public final IntegerExpression attemptCount;
    public final StringExpression state;
    
    public FileUploadCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        referenceNmber = new StringExpression("referenceNmber", this);
        status = new ByteExpression("status", this);
        fileName = new StringExpression("fileName", this);
        companyId = new IntegerExpression("companyId", this);
        branchId = new IntegerExpression("branchId", this);
        location = new StringExpression("location", this);
        productId = new IntegerExpression("productId", this);
        isVerified = new ByteExpression("isVerified", this);
        attemptCount = new IntegerExpression("attemptCount", this);
        state = new StringExpression("state", this);
    }

    public FileUploadCriteria(PersistentSession session) {
        this(session.createCriteria(FileUpload.class));
    }

    public FileUploadCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public FileUpload uniqueFileUpload() {
        return (FileUpload) super.uniqueResult();
    }
public FileUpload[] listFileUploads() {
		java.util.List list = super.list();
		return (FileUpload[]) list.toArray(new FileUpload[list.size()]);
	}
    public List<FileUpload> listAllFiles() {
        java.util.List list = super.list();
        return (List<FileUpload>) list;
    }
}
