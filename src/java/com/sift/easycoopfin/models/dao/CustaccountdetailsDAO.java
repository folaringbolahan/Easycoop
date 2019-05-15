/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.CustaccountdetailsCriteria;
import java.util.List;
import org.orm.PersistentException;
import org.orm.PersistentSession;

/**
 *
 * @author logzy
 */
public interface CustaccountdetailsDAO {
    public Custaccountdetails createCustaccountdetails();
    public List<Custaccountdetails> listAllAccountsByCriteria(CustaccountdetailsCriteria custaccountdetailsCriteria);
    public List<Custaccountdetails> listAllAccountsByQuery(String condition, String orderBy) throws PersistentException;
    public List<Custaccountdetails> listAllAccountsByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException;
    public Custaccountdetails loadAccountByCriteria(CustaccountdetailsCriteria custaccountdetailsCriteria);
    public Custaccountdetails[] listAccountByCriteria(CustaccountdetailsCriteria custaccountdetailsCriteria);
}
