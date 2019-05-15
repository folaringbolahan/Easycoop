/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.Basic;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="settings_coop")
/**
 *
 * @author Nelson Akpos
 */
public class Settingcoop {
   @Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;  
    @Column(name="setting")
    private String setting;
    @Column(name="value")
    private String value;
    @Column(name="companyid")
    private Integer companyid;
    @Column(name="display")
    private String display;

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
     * @return the setting
     */
    public String getSetting() {
        return setting;
    }

    /**
     * @param setting the setting to set
     */
    public void setSetting(String setting) {
        this.setting = setting;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the companyid
     */
    public Integer getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid the companyid to set
     */
    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    /**
     * @return the display
     */
    public String getDisplay() {
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
        this.display = display;
    }
}
