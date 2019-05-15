package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * Module entity. @author MyEclipse Persistence Tools
 */

public class Module implements java.io.Serializable {

	// Fields

	private Integer moduleId;
	private String moduleName;
	private String moduleShort;
	private String delFlg;
	private Set events = new HashSet(0);

	// Constructors

	/** default constructor */
	public Module() {
	}

	/** minimal constructor */
	public Module(String moduleName, String moduleShort, String delFlg) {
		this.moduleName = moduleName;
		this.moduleShort = moduleShort;
		this.delFlg = delFlg;
	}

	/** full constructor */
	public Module(String moduleName, String moduleShort, String delFlg,
			Set events) {
		this.moduleName = moduleName;
		this.moduleShort = moduleShort;
		this.delFlg = delFlg;
		this.events = events;
	}

	// Property accessors

	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleShort() {
		return this.moduleShort;
	}

	public void setModuleShort(String moduleShort) {
		this.moduleShort = moduleShort;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public Set getEvents() {
		return this.events;
	}

	public void setEvents(Set events) {
		this.events = events;
	}

}