/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.EmployeeModel;
import java.util.List;
import javax.persistence.Id;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gbolahan.Folarin
 */
@Repository("EmployeeDao")
public class EmployeeDaoImpl implements EmployeeDao {
    
 @Autowired
 private SessionFactory sessionFactory;


    public void addEmployeeModel(EmployeeModel employeeModel) {
        sessionFactory.getCurrentSession().saveOrUpdate(employeeModel);
    }

     @SuppressWarnings("unchecked")
    public List<EmployeeModel> listEmployeeModel() {
        return (List<EmployeeModel>) sessionFactory.getCurrentSession().createCriteria(EmployeeModel.class).list();
    }

     @SuppressWarnings("unchecked")
    public List<EmployeeModel> listEmployeeModel(int id, String name) {
         Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EmployeeModel.class);
	 criteria.add(Restrictions.eq("Id", id));
	 criteria.add(Restrictions.eq("Name", name));
             
             return  criteria.list();
    }

    @Override
    public EmployeeModel getEmployeeModel(int id) {
          return (EmployeeModel) sessionFactory.getCurrentSession().get(EmployeeModel.class, id);
    }

    
    public void updateEmployeeModel(EmployeeModel employeeModel) {
         sessionFactory.getCurrentSession().update(employeeModel);
    }

    @Override
    public void deleteEmployeeModel(EmployeeModel employeeModel) {
        sessionFactory.getCurrentSession().createQuery("delete from EmployeeModel where id = "+employeeModel.getId()).executeUpdate();
    }


    
}
