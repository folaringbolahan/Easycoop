/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.dao;

import com.sift.admin.model.EmployeeModel;
import java.util.List;

/**
 *
 * @author Gbolahan.Folarin
 */
public interface EmployeeDao {
                              public void addEmployeeModel(EmployeeModel employeeModel);
	 public List<EmployeeModel> listEmployeeModel();
	 public List<EmployeeModel> listEmployeeModel(int keyId, String name);
	 public EmployeeModel getEmployeeModel(int id);
	 public void updateEmployeeModel(EmployeeModel employeeModel);
	 public void deleteEmployeeModel(EmployeeModel employeeModel);
    
}
