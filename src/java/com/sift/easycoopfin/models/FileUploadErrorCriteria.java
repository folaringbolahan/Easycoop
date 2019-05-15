/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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

/**
 *
 * @author Inove
 */
public class FileUploadErrorCriteria extends AbstractORMCriteria {

    public final IntegerExpression id;
    public final StringExpression referenceNmber;
    public final StringExpression description;
    public final StringExpression fileName;
    public final StringExpression location;
    public final IntegerExpression companyId;
    public final IntegerExpression branchId;
    public final IntegerExpression productId;
    public final StringExpression userId;
    public final DateExpression processedDate;

    public FileUploadErrorCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        referenceNmber = new StringExpression("referenceNmber", this);
        description = new StringExpression("description", this);
        fileName = new StringExpression("fileName", this);
        companyId = new IntegerExpression("companyId", this);
        branchId = new IntegerExpression("branchId", this);
        location = new StringExpression("location", this);
        productId = new IntegerExpression("productId", this);
        userId = new StringExpression("userId", this);
        processedDate = new DateExpression("processedDate", this);
    }

    public FileUploadErrorCriteria(PersistentSession session) {
        this(session.createCriteria(FileUpload.class));
    }

    public FileUploadErrorCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public FileUploadError uniqueFileUpload() {
        return (FileUploadError) super.uniqueResult();
    }

    public FileUploadError[] listFileUploads() {
        java.util.List list = super.list();
        return (FileUploadError[]) list.toArray(new FileUploadError[list.size()]);
    }

    public List<FileUploadError> listAllFiles() {
        java.util.List list = super.list();
        return (List<FileUploadError>) list;
    }
}
