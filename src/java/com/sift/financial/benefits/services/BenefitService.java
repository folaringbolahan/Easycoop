package com.sift.financial.benefits.services;

import java.util.*;

import com.sift.financial.GenericConfigDAO;
import com.sift.financial.member.AddressTypeDAO;
import com.sift.financial.member.CompanyDAO;
import com.sift.financial.member.DividendDAO;
import com.sift.financial.member.MemberDAO;
import com.sift.financial.member.PatronageRefundDAO;

import static com.sift.financial.utility.customutil.*;

public class BenefitService {
	
	
	private CompanyDAO companyDAO;
	private MemberDAO memberDAO;
	private AddressTypeDAO addressTypeDAO;
	private GenericConfigDAO genericConfigDAO;
	
	private DividendDAO dividendDAO;
	private PatronageRefundDAO patronageRefundDAO;
	
	
	public Date getExDivDate (Date recordDate, String timeZone, int noOfDays, String countryId)
	{
            return getNewBusinessDate(recordDate,noOfDays, timeZone);
	}
        
        
       	public List getQualifiedMembersByStock (String companyId, String branchId, Date record)
	{
		
		
		
	return null;
	}
	
	
	
	
	
	public List getQualifiedMembersByContrib (String companyId, String branchId, Date recordDate)
	{
		
		
		
		
		
		
	return null;
	}
        
        
        public List getQualifiedMembersByContrib (String companyId, Date recordDate)
	{
		
		
		
		
		
		
	return null;
	}

    public CompanyDAO getCompanyDAO() {
        return companyDAO;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public MemberDAO getMemberDAO() {
        return memberDAO;
    }

    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public AddressTypeDAO getAddressTypeDAO() {
        return addressTypeDAO;
    }

    public void setAddressTypeDAO(AddressTypeDAO addressTypeDAO) {
        this.addressTypeDAO = addressTypeDAO;
    }

    public GenericConfigDAO getGenericConfigDAO() {
        return genericConfigDAO;
    }

    public void setGenericConfigDAO(GenericConfigDAO genericConfigDAO) {
        this.genericConfigDAO = genericConfigDAO;
    }

    public DividendDAO getDividendDAO() {
        return dividendDAO;
    }

    public void setDividendDAO(DividendDAO dividendDAO) {
        this.dividendDAO = dividendDAO;
    }

    public PatronageRefundDAO getPatronageRefundDAO() {
        return patronageRefundDAO;
    }

    public void setPatronageRefundDAO(PatronageRefundDAO patronageRefundDAO) {
        this.patronageRefundDAO = patronageRefundDAO;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
