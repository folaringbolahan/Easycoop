/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member;

import com.sift.admin.bean.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Nelson Akpos
 */
public class MemberExtrafldoption {
    
    private Integer id;
    private String fieldOption;
    private Integer groupid; 
    private Set memberextraflds = new HashSet(0);
    private MemberExtrafld memberextrafields ;
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
     * @return the groupid
     */
    public int getGroupid() {
        return groupid;
    }

    /**
     * @param groupid the groupid to set
     */
    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
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
    
   public Set getMemberextraflds() {
        return this.memberextraflds;
    }
    
    public void setMemberextraflds(Set memberextraflds) {
        this.memberextraflds = memberextraflds;
    }
     public MemberExtrafld getMemberextrafields() {
        return this.memberextrafields;
    }
    
    public void setMemberextrafields(MemberExtrafld memberextrafields) {
        this.memberextrafields = memberextrafields;
    }
}
