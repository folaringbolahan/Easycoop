package com.sift.financial.member;

public interface ActivityInterface {

	//GENERAL
	public final static String ADDCOMMENT = "ADD-COMMENT";
	public final static String AUDITSEARCH = "AUDIT-QUERY-RUN";
	public final static String AUDITITEM= "AUDIT-QUERY-RUN";
	public final static String DATAUPDATE= "DATA-UPDATE";
	public final static String DATADELETION= "DATA-DELETION";
	public final static String REPORTGENERATION = "REPORT-GENERATION";
	public final static String OPSENQUIRY ="OPS-ENQUIRY";

	
	//activity result
	public final static String ACTIVITYOKRESULT = "SUCCESSFUL";
	public final static String ACTIVITYFAILERESULT = "FAILED";
	
	public final static String AFFIRMDELFLAG = "Y";
	public final static String UNDELFLAG = "N";
	
	//ACTION
	public final static String ACTIONADD = "ADD";
	public final static String ACTIONEDIT = "EDIT";
	public final static String ACTIONAPPROVE = "APPROVE";
	public final static String ACTIONREJECT = "REJECT";
	public final static String ACTIONDELETE = "DELETE";
        
        public final static String ACTIONBUILD = "REBUILD";
	
	//Service Client Type
	public final static String SERVTYPEACCOUNT = "ACCOUNT";
	public final static String SERVTYPEPOSTING = "POSTING";
	public final static String SERVTYPEPRODUCT = "PRODUCT";
	
        
        //REq Info
	public final static String REQINFOID = "ID";
	public final static String REQINFOUSER = "USER";
	public final static String REQINFOCOMP = "COMP";
        public final static String REQINFOBRAN = "BRAN";
        public final static String REQINFOSUPR= "SUPR";
        public final static String REQINFOCOUN= "COUN";
        public final static String REQINFOCURR = "CURR";
	
	
	public final static String UNSUCCESSFULLNEWITEMID= "NEW-00001";
        
        
        public final static String RECORDEXIST = "U";
	public final static String RECORDEMPTY = "I";
        
        //IDENTIFIER FOR TYPE
        public final static String SINGLEMEMBER = "SINGLEMEMBER";
	public final static String BATCHMEMBER = "BATCHMEMBER";
	public final static String ADRESSUPDATE = "ADRESSUPDATE";
        
        public final static String MEMBERLIST = "MEMBERLIST";
        public final static String COOPCONNECTED = "COOPCONNECTED";
	
	
	
}
