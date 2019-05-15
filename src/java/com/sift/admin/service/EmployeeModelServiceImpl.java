/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;

import com.sift.admin.dao.EmployeeDao;
import com.sift.admin.model.EmployeeModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gbolahan.Folarin
 */

@Service("employeeModelService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EmployeeModelServiceImpl implements EmployeeModelService {
    
     @Autowired
      private EmployeeDao employeeDao;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addEmployeeModel(EmployeeModel employeeModel) {
        employeeDao.addEmployeeModel(employeeModel);
    }

    public List<EmployeeModel> listEmployeeModel() {
        return employeeDao.listEmployeeModel();
    }


    public List<EmployeeModel> listEmployeeModel(int id, String name) {
          return employeeDao.listEmployeeModel(id, name);
    }

    public EmployeeModel getEmployeeModel(int id) {
        return employeeDao.getEmployeeModel(id);

    }
        

    public void updateEmployeeModel(EmployeeModel employeeModel) {
        employeeDao.updateEmployeeModel(employeeModel);
    }


    public void deleteEmployeeModel(EmployeeModel employeeModel) {
        employeeDao.deleteEmployeeModel(employeeModel);
    }
    
}
