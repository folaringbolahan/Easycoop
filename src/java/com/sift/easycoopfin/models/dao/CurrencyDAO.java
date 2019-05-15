/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models.dao;

import com.sift.easycoopfin.models.Currency;
import java.util.List;
import org.orm.PersistentException;

/**
 *
 * @author logzy
 */
public interface CurrencyDAO {
    public List<Currency> listCurrencyByQuery(String condition, String orderBy) throws PersistentException;
}
