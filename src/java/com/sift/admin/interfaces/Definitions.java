package com.sift.admin.interfaces;

public interface Definitions{
    public String ADDRESSING_TYPE_COMPANY="1";
    public String ADDRESSING_TYPE_BRANCH="2";
    public int 	  PASS_LEN_MIN=10;
    public int    PASS_LEN_MAX=12;
    
    public String LOAN_STATUS_PENDING="P";
    public String LOAN_STATUS_APPROVED="A";    
    public String LOAN_STATUS_REJECTED="R";
    public String LOAN_STATUS_DISBURSED="D";
    public String LOAN_STATUS_ONGOING="O";
    public String LOAN_STATUS_COMPLETED="C";
    public String LOAN_STATUS_CANCELLED="X";
    
    public String LOAN_INTEREST_RATE_FIXED_FLAT="FFL";
    public String LOAN_INTEREST_RATE_REDUCING_EQUAL_INSTALLMENTS="REQ";
    public String LOAN_INTEREST_RATE_REDUCING="RED";
    
    public String FORMULA_TOTAL_SAVINGS="SYM001";
    public String OPERAND_MULTIPLY="OP001";
    public String OPERAND_DIVIDE="OP002";
    
    public String COMPANY_ADMIN_CODE="CAdmin";
    public String BRANCH_ADMIN_CODE="BAdmin";
    
    public String LOAN_PRODUCT_CODE="LON";
    public String LOAN_REPAYMENT_TRAN_CODE="LOR";
    public String LOAN_DISBURSEMENT_TRAN_CODE="LDD";
    public String LOAN_INTEREST_TRAN_CODE="LIA";
    public String LOAN_PENALTY_TRAN_CODE="CHG";
    
    public String MAIL_SUBJECT_NEW_USER_SETUP="User Account Setup Mail Alert!!!";
    public String MAIL_SUBJECT_USER_PASSWORD_RESET="User Account Password Reset Mail Alert!!!";
    public String MAIL_SUBJECT_REPAYMENT_UPLOAD="Bulk Repayment Upload Processing Report!!!";
    public String MAIL_SUBJECT_LOAN_APPROVAL_SUCCESS="Your Loan Request has been approved!!!";
    public String MAIL_SUBJECT_PAYOFF_UPLOAD="Loan PayOff Upload Processing Report!!!";
    public String MAIL_SUBJECT_USER_DEACTIVATION="User Account Deactivation Mail Alert!!!";
    public String MAIL_SUBJECT_USER_ACTIVATION="User Account Activation Mail Alert!!!";

    //public String MAIL_FROM_ADDRESS="easycoopadmin@africaprudentialregistrars.com";
    //public String DEFAULT_MAIL_SENDER="easycoopadmin@africaprudentialregistrars.com";
    
    public String MAIL_FROM_ADDRESS="easycoopadmin@africaprudential.com";
    public String DEFAULT_MAIL_SENDER="easycoopadmin@africaprudential.com";
    
    
    public String DEFAULT_MAX_CON_LOAN="2";
    public String DEFAULT_MAX_GUA_NUM="2";
    public String DEFAULT_MAX_LOAN_SUM="200000";
    public String DEFAULT_MAX_LEN_STAY="180";    
    
    public String SRC_IMAGE_PATH="/resources/images/photos/biz-logo.jpg";
    public String SRC_PATH="/resources/images/photos/";
    
    public String UPLOAD_TYPE_LOAN_REPAYMENT="LR";
    public String UPLOAD_TYPE_LOAN_PAYOFF="LP";
    
    
    //SMS Message Definitions
    public String    SMS_MESSAGE_LOAN_REPAYMENT_SUCCESS="Your current Loan Balance is %s";
    public String    SMS_DEFAULT_MESSAGE_TYPE="0";
    public String    SMS_DEFAULT_SENDER="EASYCOOPFIN";

    //User Profile Mgt Events Ids
    public int    EVENT_USER_PROFILE_CREATION=50;
    public int    EVENT_USER_ACTIVATION=51;
    public int    EVENT_USER_PROFILE_UPDATE=52;
    
    //Branch Mgt Events Ids
    public int    EVENT_BRANCH_SETUP=53;
    public int    EVENT_BRANCH_UPDATE=54; 
    public int    EVENT_BRANCH_ACTIVATION=55;   
    
    //Company Mgt Events Ids
    public int    EVENT_COMPANY_SETUP=56;
    public int    EVENT_COMPANY_UPDATE=57;
    
    //Loan Events Ids
    public int    EVENT_LOAN_INITIATION=58;
    public int    EVENT_LOAN_APPROVAL=59;    
    public int    EVENT_LOAN_DISBURSEMENT=60;
    public int    EVENT_LOAN_REPAYMENT=61; 
    public int    EVENT_LOAN_UPLOAD=62;
    public int    EVENT_LOAN_PAYOFF=63;
    public int    EVENT_BULK_LOAN_REPAYMENT=64;
    public int    EVENT_LOAN_DISBURSEMENT_CANCEL=129;
    
    public int    EVENT_FISCAL_PERIOD_CREATION=65;
    public int    EVENT_FISCAL_PERIOD_UPDATE=66;
    
    public int    EVENT_USER_GROUP_CREATION=67;
    public int    EVENT_USER_GROUP_UPDATE=68;
    
    public int    EVENT_TAX_GROUP_CREATION=69;
    public int    EVENT_TAX_GROUP_UPDATE=70;
    
    public int    EVENT_TAX_DETAILS_CREATION=71;
    public int    EVENT_TAX_DETAILS_UPDATE=72;
    
    public int    EVENT_BULK_UPLD_INITIATION=73;
    public int    EVENT_BULK_UPLD_AUTHORIZATION=74;
    public int    EVENT_LOAN_CLOSE_OUT=75;
        
    //SMS CONFIG
    public String SMS_URI="http://www.smslive247.com/http/index.aspx";
    public String SMS_PORT="1020";
    public String SMS_USER="APRPLC";
    public String SMS_PASS="registrars";     
    public String SMS_SENDER="EASYCOOPFIN";
    public String SMS_OWNEREMAIL="itcare@africaprudentialregistrars.com";
    
    //USER ADMIN GROUP
    public String EASYCORP_SUPER_ADM="RegSuperAdmin";
    
}