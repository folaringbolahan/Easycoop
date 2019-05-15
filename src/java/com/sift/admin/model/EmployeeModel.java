/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Gbolahan.Folarin
 */
@Entity
@Table(name="employeetable")
public class EmployeeModel {
   
@Id
@Column(name="ID")
@GeneratedValue(strategy = GenerationType.AUTO)
private int id; 

@Column(name="NAME")
private String name;  

@Column(name="SALARY")
private float salary;  

@Column(name="DESIGNATION")
private String designation;

    public EmployeeModel() {
    }

    public EmployeeModel(int id, String name, float salary, String designation) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.designation = designation;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    
}
