/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Nelson Akpos
 */
@Entity
@Table(name="MEMBER_EXTRAFLD_GROUP")
public class MemberExtraFieldGrp {
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
    
    @ManyToOne()
    @JoinColumn(name="GROUPID")
     private MemberExtraField  grpid;
    

    @Column(name="FIELD_OPTION")
    private String fieldOption;
   
    
   
   
 
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

   

    /**
     * @return the fieldOption
     */
    public String getFieldOption() {
        return fieldOption;
    }

    /**
     * @param fieldOption the fieldOption to set
     */
    public void setFieldOption(String fieldOption) {
        this.fieldOption = fieldOption;
    }

    

    /**
     * @return the grpid
     */
    public MemberExtraField getGrpid() {
        return grpid;
    }

    /**
     * @param grpid the grpid to set
     */
    public void setGrpid(MemberExtraField grpid) {
       this.grpid = grpid;
    }

   

  

    
   
 
  
}
