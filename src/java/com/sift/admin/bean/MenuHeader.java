package com.sift.admin.bean;

import java.util.*;

public class MenuHeader{	
	private String headerId;
	private String headerName;
	private String headerDisplay;
	private String headerUrl;
	private String delFlg;
	private String headerSeq;
	private List<BaseBean>  menuContent;
	
	public String getHeaderId() {
		return headerId;
	}
	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	public String getHeaderDisplay() {
		return headerDisplay;
	}
	public void setHeaderDisplay(String headerDisplay) {
		this.headerDisplay = headerDisplay;
	}
	public String getHeaderUrl() {
		return headerUrl;
	}
	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getHeaderSeq() {
		return headerSeq;
	}
	public void setHeaderSeq(String headerSeq) {
		this.headerSeq = headerSeq;
	}
	public List<BaseBean> getMenuContent() {
		return menuContent;
	}
	public void setMenuContent(List<BaseBean> menuContent) {
		this.menuContent = menuContent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((headerName == null) ? 0 : headerName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MenuHeader other = (MenuHeader) obj;
		if (headerName == null) {
			if (other.headerName != null)
				return false;
		} else if (!headerName.equals(other.headerName))
			return false;
		return true;
	}

}
