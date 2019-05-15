package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * SiftEntity entity. @author MyEclipse Persistence Tools
 */

public class SiftEntity implements java.io.Serializable {

	// Fields

	private Integer siftEntityId;
	private String name;
	private String desc;
	//private Set entityEmails = new HashSet(0);
	//private Set entityPhones = new HashSet(0);

	// Constructors

	/** default constructor */
	public SiftEntity() {
	}

	/** minimal constructor */
	public SiftEntity(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	/** full constructor
	public SiftEntity(String name, String desc) {
		this.name = name;
		this.desc = desc;
		//this.entityEmails = entityEmails;
		//this.entityPhones = entityPhones;
	}
 */
	// Property accessors

	public Integer getSiftEntityId() {
		return this.siftEntityId;
	}

	public void setSiftEntityId(Integer siftEntityId) {
		this.siftEntityId = siftEntityId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/*public Set getEntityEmails() {
		return this.entityEmails;
	}

	public void setEntityEmails(Set entityEmails) {
		this.entityEmails = entityEmails;
	}

	public Set getEntityPhones() {
		return this.entityPhones;
	}

	public void setEntityPhones(Set entityPhones) {
		this.entityPhones = entityPhones;
	}*/

}