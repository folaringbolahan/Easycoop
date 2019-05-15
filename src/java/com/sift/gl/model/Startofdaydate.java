package com.sift.gl.model;

import java.util.Date;

public class Startofdaydate{   
    private Date periodStart;
    private String opmessage;
    private String startdatestr;
    private Integer postdateyr;
    private Integer postdatemth;
    private Integer postdateday;
    
	public Date getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}
	 
        public String getStartdatestr() {
		return startdatestr;
	}
        public void setStartdatestr(String startdatestr) {
		this.startdatestr = startdatestr;
	}  
        public String getOpmessage() {
		return opmessage;
	}
        public void setOpmessage(String opmessage) {
		this.opmessage = opmessage;
	}
        public Integer getPostdateyr() {
		return postdateyr;
	}
        public void setPostdateyr(Integer postdateyr) {
		this.postdateyr = postdateyr;
	}
        public Integer getPostdatemth() {
		return postdatemth;
	}
        public void setPostdatemth(Integer postdatemth) {
		this.postdatemth = postdatemth;
	} 
        public Integer getPostdateday() {
		return postdateday;
	}
        public void setPostdateday(Integer postdateday) {
		this.postdateday = postdateday;
	} 
}
