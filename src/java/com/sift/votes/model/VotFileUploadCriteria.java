/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import com.sift.easycoopfin.models.FileUpload;
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
 * @author Nelson Akpos
 */
public class VotFileUploadCriteria extends AbstractORMCriteria{
   public final IntegerExpression id;
    public final StringExpression referenceNmber;
    public final ByteExpression status;
    public final StringExpression fileName;
    public final StringExpression location;
    public final IntegerExpression companyId;
    public final IntegerExpression branchId;
    public final ByteExpression isVerified;
    public final IntegerExpression attemptCount;
    public final StringExpression state;   
    
        public VotFileUploadCriteria(Criteria criteria) {
        super(criteria);
        id = new IntegerExpression("id", this);
        referenceNmber = new StringExpression("referenceNmber", this);
        status = new ByteExpression("status", this);
        fileName = new StringExpression("fileName", this);
        companyId = new IntegerExpression("companyId", this);
        branchId = new IntegerExpression("branchId", this);
        location = new StringExpression("location", this);
        isVerified = new ByteExpression("isVerified", this);
        attemptCount = new IntegerExpression("attemptCount", this);
        state = new StringExpression("state", this);
    }
      
        public VotFileUploadCriteria(PersistentSession session) {
        this(session.createCriteria(VotFileUpload.class));
    }

    public VotFileUploadCriteria() throws PersistentException {
        this(com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession());
    }

    public VotFileUpload uniqueFileUpload() {
        return (VotFileUpload) super.uniqueResult();
    }
public VotFileUpload[] listFileUploads() {
		java.util.List list = super.list();
		return (VotFileUpload[]) list.toArray(new VotFileUpload[list.size()]);
	}
    public List<VotFileUpload> listAllFiles() {
        java.util.List list = super.list();
        return (List<VotFileUpload>) list;
    }
}
