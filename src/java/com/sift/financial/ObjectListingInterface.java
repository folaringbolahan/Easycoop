package com.sift.financial;

import java.util.List;

public interface ObjectListingInterface {
	
	public List<Object[]> getObjectByStatus(String objectStatus, String user, String companyId);
	public List<Object[]> getTheFlow(String objectId);

}
