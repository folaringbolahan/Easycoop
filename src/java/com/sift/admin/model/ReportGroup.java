package com.sift.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reportgroups")
public class ReportGroup {
   @Id
   @Column(name="ID")
   @GeneratedValue
   private String id;	
	
   @Column(name="code")
   private String code;
   
   @Column(name="description")
   private String description;
   
   @Column(name="formatclass")
   private String formatclass;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getFormatclass() {
	return formatclass;
}

public void setFormatclass(String formatclass) {
	this.formatclass = formatclass;
}  
   
}
