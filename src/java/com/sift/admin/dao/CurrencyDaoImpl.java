package com.sift.admin.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sift.admin.model.Currency;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Repository("currencyDao")
public class CurrencyDaoImpl implements CurrencyDao{

 @Autowired
 private SessionFactory sessionFactory;

 public void addCurrency(Currency currency) {
   sessionFactory.getCurrentSession().saveOrUpdate(currency);
 }

 @SuppressWarnings("unchecked")
 public List<Currency> listCurrency(){
  return (List<Currency>) sessionFactory.getCurrentSession().createCriteria(Currency.class).list();
 }

 public Currency getCurrency(int id){
  return (Currency) sessionFactory.getCurrentSession().get(Currency.class, id);
 }

 public void deleteCurrency(Currency currency) {
	 sessionFactory.getCurrentSession().createQuery("DELETE FROM CURRENCY WHERE id = "+currency.getId()).executeUpdate();
 }
}