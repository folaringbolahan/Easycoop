package com.sift.loan.utility;
import java.util.ArrayList;
import java.util.List;
import com.sift.admin.model.*;
import com.sift.loan.model.*;
import com.sift.loan.bean.*;
import com.sift.admin.bean.*;

public class BeanMapperUtility {
	public CountryAddressFilter prepareCountryAddressFilterModel(CountryAddressFilterBean countryAddressFilterBean){
	    CountryAddressFilter countryAddressFilter = new CountryAddressFilter();
	 
	    countryAddressFilter.setCountryId(countryAddressFilterBean.getCountryId());
	    countryAddressFilter.setAddrFieldName(countryAddressFilterBean.getAddrFieldName());
	    countryAddressFilter.setAddrFieldIndx(countryAddressFilterBean.getAddrFieldIndx());	    
	    /*countryAddressFilter.setCreatedBy(countryAddressFilterBean.getCreatedBy());
	    countryAddressFilter.setCreationDate(countryAddressFilterBean.getCreationDate());
	    countryAddressFilter.setLastModifiedBy(countryAddressFilterBean.getLastModifiedBy());
	    countryAddressFilter.setLastModificationDate(countryAddressFilterBean.getLastModificationDate());*/
	    countryAddressFilter.setId(countryAddressFilterBean.getId());
	    //countryAddressFilterBean.setId(null);
     
	    return countryAddressFilter;
    }
 
	public List<CountryAddressFilterBean> prepareCountryAddressFilterListofBean(List<CountryAddressFilter> countryAddressFilters){
        List<CountryAddressFilterBean> beans = null;
       
        if(countryAddressFilters != null && !countryAddressFilters.isEmpty()){
        	beans = new ArrayList<CountryAddressFilterBean>();
        	CountryAddressFilterBean countryAddressFilter = null;
		    
        	for(CountryAddressFilter countryAddressFilterBean : countryAddressFilters){
        		countryAddressFilter = new CountryAddressFilterBean();
			    
        		countryAddressFilter.setId(countryAddressFilterBean.getId());
        	    countryAddressFilter.setCountryId(countryAddressFilterBean.getCountryId());
        	    countryAddressFilter.setAddrFieldName(countryAddressFilterBean.getAddrFieldName());
        	    countryAddressFilter.setAddrFieldIndx(countryAddressFilterBean.getAddrFieldIndx());	    
    
			    /*countryAddressFilter.setCreatedBy(countryAddressFilterBean.getCreatedBy());
			    countryAddressFilter.setCreationDate(countryAddressFilterBean.getCreationDate());
			    countryAddressFilter.setLastModifiedBy(countryAddressFilterBean.getLastModifiedBy());
			    countryAddressFilter.setLastModificationDate(countryAddressFilterBean.getLastModificationDate());*/
        	    
			    beans.add(countryAddressFilter);
		   }
	    }
        
        System.out.println(beans==null?"0":beans.size());
  
        return beans;
     }
 
     public CountryAddressFilterBean prepareCountryAddressFilterBean(CountryAddressFilter countryAddressFilter){
		  CountryAddressFilterBean 	bean = new CountryAddressFilterBean();
		  
		  bean.setId(countryAddressFilter.getId());
		  bean.setCountryId(countryAddressFilter.getCountryId());
		  bean.setAddrFieldName(countryAddressFilter.getAddrFieldName());
		  bean.setAddrFieldIndx(countryAddressFilter.getAddrFieldIndx());	 	    
		  /*bean.setCreatedBy(countryAddressFilter.getCreatedBy());
		  bean.setCreationDate(countryAddressFilter.getCreationDate());
		  bean.setLastModifiedBy(countryAddressFilter.getLastModifiedBy());
		  bean.setLastModificationDate(countryAddressFilter.getLastModificationDate());*/
		
		  return bean;
 }
     
     //company field mapping
     public Company prepareCompanyModel(CompanyBean companyBean){
 	    Company company = new Company();
 	 
 	    company.setName(companyBean.getName());
 	    company.setCode(companyBean.getCode());
 	    company.setEmail(companyBean.getEmail());
 	    company.setCountryId(companyBean.getCountryId());
 	    company.setFax(companyBean.getFax());
 	    company.setRegNo(companyBean.getRegNo());
 	    company.setPhone1(companyBean.getPhone1());
 	    company.setPhone2(companyBean.getPhone2());
 	    company.setWebsite(companyBean.getWebsite());
	    
 	    company.setActive(companyBean.getActive());
 	    company.setDeleted(companyBean.getDeleted());
 	    company.setCreatedBy(companyBean.getCreatedBy());
 	    company.setCreationDate(companyBean.getCreationDate());
 	    company.setLastModifiedBy(companyBean.getLastModifiedBy());
 	    company.setLastModificationDate(companyBean.getLastModificationDate());
 	    company.setId(companyBean.getId());
 	    //companyBean.setId(null);
      
 	    return company;
  }
  
  public List<CompanyBean> prepareListofCompanyBean(List<Company> companys){
         List<CompanyBean> beans = null;
        
         if(companys != null && !companys.isEmpty()){
         	beans = new ArrayList<CompanyBean>();
         	CompanyBean bean = null;
 		    
         	for(Company company : companys){
 			    bean = new CompanyBean();
 			    
 			    bean.setName(company.getName());
 			    bean.setCode(company.getCode());
 			    bean.setEmail(company.getEmail());
 			    bean.setCountryId(company.getCountryId());
 			    bean.setFax(company.getFax());
 			    bean.setRegNo(company.getRegNo());
 			    bean.setPhone1(company.getPhone1());
 			    bean.setPhone2(company.getPhone2());
 			    bean.setWebsite(company.getWebsite());
 			    
 			    bean.setId(company.getId());
 			    bean.setActive(company.getActive());
 			    bean.setDeleted(company.getDeleted());
 			    bean.setCreatedBy(company.getCreatedBy());
 			    bean.setCreationDate(company.getCreationDate());
 			    bean.setLastModifiedBy(company.getLastModifiedBy());
 			    bean.setLastModificationDate(company.getLastModificationDate());
 		
 			    beans.add(bean);
 		   }
 	    }
   
         return beans;
  }
  
  public CompanyBean prepareCompanyBean(Company company){
 		  CompanyBean 	bean = new CompanyBean();
 		  
 		  bean.setName(company.getName());
 		  bean.setCode(company.getCode());
 		  bean.setEmail(company.getEmail());
 	      bean.setCountryId(company.getCountryId());
 		  bean.setFax(company.getFax());
 		  bean.setRegNo(company.getRegNo());
 		  bean.setPhone1(company.getPhone1());
 		  bean.setPhone2(company.getPhone2());
 		  bean.setWebsite(company.getWebsite());
	      
	      bean.setId(company.getId());
 		  bean.setActive(company.getActive());
 		  bean.setDeleted(company.getDeleted());
 		  bean.setCreatedBy(company.getCreatedBy());
 		  bean.setCreationDate(company.getCreationDate());
 		  bean.setLastModifiedBy(company.getLastModifiedBy());
 		  bean.setLastModificationDate(company.getLastModificationDate());
 		
 		  return bean;
  }
  
  public Country prepareCountryModel(CountryBean countryBean){
	    Country country = new Country();
	 
	    country.setActive(countryBean.getActive());
	    country.setDeleted(countryBean.getDeleted());
	    country.setCountryName(countryBean.getCountryName());	  
	    country.setCountryCode(countryBean.getCountryCode());	    
	    country.setCreatedBy(countryBean.getCreatedBy());
	    country.setCreationDate(countryBean.getCreationDate());
	    country.setLastModifiedBy(countryBean.getLastModifiedBy());
	    country.setLastModificationDate(countryBean.getLastModificationDate());
	    country.setId(countryBean.getId());
	    country.setTimeZone(countryBean.getTimeZone());
   
	    return country;
  }

  public List<CountryBean> prepareListofCountryBean(List<Country> countrys){
      List<CountryBean> beans = null;
     
      if(countrys != null && !countrys.isEmpty()){
      	beans = new ArrayList<CountryBean>();
      	CountryBean country = null;
		    
      	for(Country item : countrys){
      		    country = new CountryBean();
			    
      		    country.setId(item.getId());
			    country.setActive(item.getActive());
			    country.setDeleted(item.getDeleted());
			    country.setCountryName(item.getCountryName());	  
			    country.setCountryCode(item.getCountryCode());
			    country.setCreatedBy(item.getCreatedBy());
			    country.setCreationDate(item.getCreationDate());
			    country.setLastModifiedBy(item.getLastModifiedBy());
			    country.setLastModificationDate(item.getLastModificationDate());
			    country.setTimeZone(item.getTimeZone());
		
			    beans.add(country);
		   }
	    }

      return beans;
  }

  public CountryBean prepareCountryBean(Country country){
		  CountryBean 	bean = new CountryBean();
		  
		  bean.setId(country.getId());
		  bean.setActive(country.getActive());
		  bean.setDeleted(country.getDeleted());
		  bean.setCountryName(country.getCountryName());	  
		  bean.setCountryCode(country.getCountryCode());
	      bean.setCreatedBy(country.getCreatedBy());
		  bean.setCreationDate(country.getCreationDate());
		  bean.setLastModifiedBy(country.getLastModifiedBy());
		  bean.setLastModificationDate(country.getLastModificationDate());
		  bean.setTimeZone(country.getTimeZone());
		  
		  return bean;
  }  
  
  public Branch prepareBranchModel(BranchBean branchBean){
	    Branch branch = new Branch();
	 
	    branch.setBranchCode(branchBean.getBranchCode());
	    branch.setBranchName(branchBean.getBranchName());	    
	    branch.setCompanyId(branchBean.getCompanyId());
	    branch.setCountryId(branchBean.getCountryId());
	    branch.setPhone1(branchBean.getPhone1());
	    branch.setPhone2(branchBean.getPhone2());
	    branch.setEmail(branchBean.getEmail());
	    branch.setActive(branchBean.getActive());
	    branch.setDeleted(branchBean.getDeleted());
	    		 
	    branch.setPostDate(branchBean.getPostDate());
	    branch.setCurrentYear(branchBean.getCurrentYear());
		branch.setCurrentPeriod(branchBean.getCurrentPeriod());	
		branch.setBaseCurrency(branchBean.getBaseCurrency());
		branch.setSetupDate(branchBean.getSetupDate());	
		
	    branch.setCreatedBy(branchBean.getCreatedBy());
	    branch.setCreationDate(branchBean.getCreationDate());
	    branch.setLastModifiedBy(branchBean.getLastModifiedBy());
	    branch.setLastModificationDate(branchBean.getLastModificationDate());
	    branch.setId(branchBean.getId());
 
	    return branch;
  }

  public List<BranchBean> prepareListofBranchBean(List<Branch> branchs){
      List<BranchBean> beans = null;
     
      if(branchs != null && !branchs.isEmpty()){
      	beans = new ArrayList<BranchBean>();
      	BranchBean bean = null;
		    
      	for(Branch branch : branchs){
			    bean = new BranchBean();

			    bean.setBranchCode(branch.getBranchCode());
			    bean.setBranchName(branch.getBranchName());	    
			    bean.setCompanyId(branch.getCompanyId());
			    bean.setCountryId(branch.getCountryId());
			    bean.setPhone1(branch.getPhone1());
			    bean.setPhone2(branch.getPhone2());
			    bean.setEmail(branch.getEmail());
			    bean.setId(branch.getId());
			    bean.setActive(branch.getActive());
			    bean.setDeleted(branch.getDeleted());
				 
			    bean.setPostDate(branch.getPostDate());
				bean.setCurrentYear(branch.getCurrentYear());
				bean.setCurrentPeriod(branch.getCurrentPeriod());	
				bean.setBaseCurrency(branch.getBaseCurrency());
				bean.setSetupDate(branch.getSetupDate());	

				bean.setCreatedBy(branch.getCreatedBy());
			    bean.setCreationDate(branch.getCreationDate());
			    bean.setLastModifiedBy(branch.getLastModifiedBy());
			    bean.setLastModificationDate(branch.getLastModificationDate());
		
			    beans.add(bean);
		   }
	    }

      return beans;
}

public BranchBean prepareBranchBean(Branch branch){
		  BranchBean 	bean = new BranchBean();
		  
		  bean.setBranchCode(branch.getBranchCode());
		  bean.setBranchName(branch.getBranchName());	    
          bean.setCompanyId(branch.getCompanyId());
		  bean.setCountryId(branch.getCountryId());
		  bean.setPhone1(branch.getPhone1());
		  bean.setPhone2(branch.getPhone2());
		  bean.setEmail(branch.getEmail());
		  bean.setId(branch.getId());
		  bean.setActive(branch.getActive());
		  bean.setDeleted(branch.getDeleted());
		    
		  bean.setPostDate(branch.getPostDate());
		  bean.setCurrentYear(branch.getCurrentYear());
		  bean.setCurrentPeriod(branch.getCurrentPeriod());	
		  bean.setBaseCurrency(branch.getBaseCurrency());
		  bean.setSetupDate(branch.getSetupDate());	
		  
		  bean.setCreatedBy(branch.getCreatedBy());
		  bean.setCreationDate(branch.getCreationDate());
		  bean.setLastModifiedBy(branch.getLastModifiedBy());
		  bean.setLastModificationDate(branch.getLastModificationDate());
		
		  return bean;
}

public UserGroup prepareUserGroupModel(UserGroupBean userGroupBean){
    UserGroup userGroup = new UserGroup();

    userGroup.setId(userGroupBean.getId());
    userGroup.setActive(userGroupBean.getActive());
    userGroup.setDeleted(userGroupBean.getDeleted());
    userGroup.setDescription(userGroupBean.getDescription());
    userGroup.setCode(userGroupBean.getCode());
    userGroup.setCompanyId(userGroupBean.getCompanyId());
    userGroup.setBranchId(userGroupBean.getBranchId());
    userGroup.setAccessId(userGroupBean.getAccessId());
    /*userGroup.setCreatedBy(userGroupBean.getCreatedBy());
    userGroup.setCreationDate(userGroupBean.getCreationDate());
    userGroup.setLastModifiedBy(userGroupBean.getLastModifiedBy());
    userGroup.setLastModificationDate(userGroupBean.getLastModificationDate());*/

    //userGroupBean.setId(null);

    return userGroup;
}

public List<UserGroupBean> prepareListofUserGroupBean(List<UserGroup> userGroups){
    List<UserGroupBean> beans = null;

    if(userGroups != null && !userGroups.isEmpty()){
    	beans = new ArrayList<UserGroupBean>();
    	UserGroupBean userGroup = null;

    	for(UserGroup userGroupBean : userGroups){
    		userGroup = new UserGroupBean();

    		userGroup.setId(userGroupBean.getId());
		    userGroup.setActive(userGroupBean.getActive());
		    userGroup.setDeleted(userGroupBean.getDeleted());
		    userGroup.setDescription(userGroupBean.getDescription());
		    userGroup.setCode(userGroupBean.getCode());
		    userGroup.setCompanyId(userGroupBean.getCompanyId());
		    userGroup.setBranchId(userGroupBean.getBranchId());
		    userGroup.setAccessId(userGroupBean.getAccessId());
		    /*userGroup.setCreatedBy(userGroupBean.getCreatedBy());
		    userGroup.setCreationDate(userGroupBean.getCreationDate());
		    userGroup.setLastModifiedBy(userGroupBean.getLastModifiedBy());
		    userGroup.setLastModificationDate(userGroupBean.getLastModificationDate());*/

		    beans.add(userGroup);
	   }
    }

    return beans;
}

public UserGroupBean prepareUserGroupBean(UserGroup userGroup){
	  UserGroupBean 	bean = new UserGroupBean();

	  bean.setId(userGroup.getId());
	  bean.setActive(userGroup.getActive());
	  bean.setDeleted(userGroup.getDeleted());
	  bean.setDescription(userGroup.getDescription());
	  bean.setCode(userGroup.getCode());
	  bean.setCompanyId(userGroup.getCompanyId());
	  bean.setBranchId(userGroup.getBranchId());
	  bean.setAccessId(userGroup.getAccessId());
	  /*bean.setCreatedBy(userGroup.getCreatedBy());
	  bean.setCreationDate(userGroup.getCreationDate());
	  bean.setLastModifiedBy(userGroup.getLastModifiedBy());
	  bean.setLastModificationDate(userGroup.getLastModificationDate());*/

	  return bean;
}
public UserRole prepareUserRoleModel(UserRoleBean userRoleBean){
    UserRole userRole = new UserRole();
 
    userRole.setRoleCode(userRoleBean.getRoleCode());
    userRole.setId(userRoleBean.getId());
    userRole.setRoleName(userRoleBean.getRoleName());
    userRole.setActive(userRoleBean.getActive());
    userRole.setDeleted(userRoleBean.getDeleted());
    //userRole.setGroupId(userRoleBean.getGroupId());
    /*userRole.setCreatedBy(userRoleBean.getCreatedBy());
    userRole.setCreationDate(userRoleBean.getCreationDate());
    userRole.setLastModifiedBy(userRoleBean.getLastModifiedBy());
    userRole.setLastModificationDate(userRoleBean.getLastModificationDate());*/
    
    return userRole;
}

public List<UserRoleBean> prepareListofUserRoleBean(List<UserRole> userRoles){
    List<UserRoleBean> beans = null;
   
    if(userRoles != null && !userRoles.isEmpty()){
    	beans = new ArrayList<UserRoleBean>();
    	UserRoleBean bean = null;
	    
    	for(UserRole userRole : userRoles){
		    bean = new UserRoleBean();
		    
		    bean.setRoleCode(userRole.getRoleCode());
		    bean.setRoleName(userRole.getRoleName());
		    bean.setId(userRole.getId());
		    //bean.setGroupId(userRole.getGroupId());
		    bean.setActive(userRole.getActive());
		    bean.setDeleted(userRole.getDeleted());
		    /*bean.setCreatedBy(userRole.getCreatedBy());
		    bean.setCreationDate(userRole.getCreationDate());
		    bean.setLastModifiedBy(userRole.getLastModifiedBy());
		    bean.setLastModificationDate(userRole.getLastModificationDate());*/
	
		    beans.add(bean);
	   }
    }

    return beans;
}

public UserRoleBean prepareUserRoleBean(UserRole userRole){
	  UserRoleBean 	bean = new UserRoleBean();
	  
	  bean.setRoleCode(userRole.getRoleCode());
	  bean.setRoleName(userRole.getRoleName());
	  bean.setId(userRole.getId());
	  //bean.setGroupId(userRole.getGroupId());
	  bean.setActive(userRole.getActive());
	  bean.setDeleted(userRole.getDeleted());
	  /*bean.setCreatedBy(userRole.getCreatedBy());
	  bean.setCreationDate(userRole.getCreationDate());
	  bean.setLastModifiedBy(userRole.getLastModifiedBy());
	  bean.setLastModificationDate(userRole.getLastModificationDate());*/
	
	  return bean;
 }

 
 public AddressEntries prepareAddressEntriesModel(AddressEntriesBean addressEntriesBean){
    AddressEntries addressEntries = new AddressEntries();

    addressEntries.setSerialPos(addressEntriesBean.getSerialPos());
    addressEntries.setAddrFieldValue(addressEntriesBean.getAddrFieldValue());
    addressEntries.setAddrFieldName(addressEntriesBean.getAddrFieldName());
    addressEntries.setActive(addressEntriesBean.getActive());
    addressEntries.setKeyId(addressEntriesBean.getKeyId());  
    addressEntries.setTypeId(addressEntriesBean.getTypeId());
    addressEntries.setSerialPos(addressEntriesBean.getSerialPos());    
    addressEntries.setDeleted(addressEntriesBean.getDeleted());
    addressEntries.setCreatedBy(addressEntriesBean.getCreatedBy());
    addressEntries.setCreationDate(addressEntriesBean.getCreationDate());
    addressEntries.setLastModifiedBy(addressEntriesBean.getLastModifiedBy());
    addressEntries.setLastModificationDate(addressEntriesBean.getLastModificationDate());
    //addressEntriesBean.setId(null);
    
    addressEntries.setId(addressEntriesBean.getId());
 
    return addressEntries;
}

 public List<AddressEntriesBean> prepareListofAddressEntriesBean(List<AddressEntries> addressEntriess){
    List<AddressEntriesBean> beans = null;
   
    if(addressEntriess != null && !addressEntriess.isEmpty()){
    	beans = new ArrayList<AddressEntriesBean>();
    	AddressEntriesBean bean = null;
	    
    	for(AddressEntries addressEntries : addressEntriess){
		    bean = new AddressEntriesBean();

		    bean.setSerialPos(addressEntries.getSerialPos());
		    bean.setAddrFieldName(addressEntries.getAddrFieldName());
		    bean.setAddrFieldValue(addressEntries.getAddrFieldValue());
		    bean.setId(addressEntries.getId());
		    bean.setKeyId(addressEntries.getKeyId());
			bean.setTypeId(addressEntries.getTypeId());
		    bean.setSerialPos(addressEntries.getSerialPos());
		    bean.setActive(addressEntries.getActive());
		    bean.setDeleted(addressEntries.getDeleted());
		    bean.setCreatedBy(addressEntries.getCreatedBy());
		    bean.setCreationDate(addressEntries.getCreationDate());
		    bean.setLastModifiedBy(addressEntries.getLastModifiedBy());
		    bean.setLastModificationDate(addressEntries.getLastModificationDate());
	
		    beans.add(bean);
	   }
    }

    return beans;
}

 public AddressEntriesBean prepareAddressEntriesBean(AddressEntries addressEntries){
	  AddressEntriesBean 	bean = new AddressEntriesBean();

	  bean.setSerialPos(addressEntries.getSerialPos());
	  bean.setAddrFieldValue(addressEntries.getAddrFieldValue());
	  bean.setAddrFieldName(addressEntries.getAddrFieldName());
	  bean.setId(addressEntries.getId());
	  bean.setKeyId(addressEntries.getKeyId());
	  bean.setTypeId(addressEntries.getTypeId());
	  bean.setSerialPos(addressEntries.getSerialPos());
	  bean.setActive(addressEntries.getActive());
	  bean.setDeleted(addressEntries.getDeleted());
	  bean.setCreatedBy(addressEntries.getCreatedBy());
	  bean.setCreationDate(addressEntries.getCreationDate());
	  bean.setLastModifiedBy(addressEntries.getLastModifiedBy());
	  bean.setLastModificationDate(addressEntries.getLastModificationDate());
	
	  return bean;
  }
 
 public Tax prepareTaxModel(TaxBean taxBean){
	    Tax tax = new Tax();

	    tax.setId(taxBean.getId());
	    tax.setCompanyId(taxBean.getCompanyId());
	    tax.setBranchId(taxBean.getBranchId());
	    tax.setTaxGroupId(taxBean.getTaxGroupId());
	    tax.setCountryId(taxBean.getCountryId());
	    tax.setLocationDependent(taxBean.getLocationDependent());
	    tax.setActive(taxBean.getActive());
	    tax.setDeleted(taxBean.getDeleted());
	    tax.setTaxName(taxBean.getTaxName());
	    tax.setTaxCode(taxBean.getTaxCode());
	    tax.setTaxDescription(taxBean.getTaxDescription());
	    tax.setRate(taxBean.getRate());
	    tax.setCreatedBy(taxBean.getCreatedBy());
	    tax.setCreationDate(taxBean.getCreationDate());
	    tax.setLastModifiedBy(taxBean.getLastModifiedBy());
	    tax.setLastModificationDate(taxBean.getLastModificationDate());
	    tax.setId(taxBean.getId());

	    return tax;
}

 public List<TaxBean> prepareListofTaxBean(List<Tax> taxs){
     List<TaxBean> beans = null;

     if(taxs != null && !taxs.isEmpty()){
     	beans = new ArrayList<TaxBean>();
     	TaxBean tax = null;

     	for(Tax item : taxs){
     		tax = new TaxBean();

	     		tax.setId(item.getId());
	     		tax.setCompanyId(item.getCompanyId());
	     		tax.setBranchId(item.getBranchId());
	     	    tax.setCountryId(item.getCountryId());
	     	    tax.setTaxGroupId(item.getTaxGroupId());
	     	    tax.setLocationDependent(item.getLocationDependent());
			    tax.setActive(item.getActive());
			    tax.setDeleted(item.getDeleted());
			    tax.setTaxName(item.getTaxName());
			    tax.setTaxCode(item.getTaxCode());
				tax.setTaxDescription(item.getTaxDescription());
			    tax.setRate(item.getRate());
			    tax.setCreatedBy(item.getCreatedBy());
			    tax.setCreationDate(item.getCreationDate());
			    tax.setLastModifiedBy(item.getLastModifiedBy());
			    tax.setLastModificationDate(item.getLastModificationDate());

			    beans.add(tax);
		   }
	    }

     return beans;
}

public TaxBean prepareTaxBean(Tax tax){
		  TaxBean 	bean = new TaxBean();

		  bean.setId(tax.getId());
		  bean.setCompanyId(tax.getCompanyId());
		  bean.setBranchId(tax.getBranchId());
		  bean.setTaxGroupId(tax.getTaxGroupId());
		  bean.setCountryId(tax.getCountryId());
		  bean.setLocationDependent(tax.getLocationDependent());	
		  bean.setActive(tax.getActive());
		  bean.setDeleted(tax.getDeleted());
		  bean.setTaxName(tax.getTaxName());
		  bean.setTaxCode(tax.getTaxCode());
		  bean.setTaxDescription(tax.getTaxDescription());
		  bean.setRate(tax.getRate());
	      bean.setCreatedBy(tax.getCreatedBy());
		  bean.setCreationDate(tax.getCreationDate());
		  bean.setLastModifiedBy(tax.getLastModifiedBy());
		  bean.setLastModificationDate(tax.getLastModificationDate());

		  return bean;
}

public LoanRequestException prepareLoanRequestExceptionModel(LoanRequestExceptionBean loanRequestExceptionBean){
	    LoanRequestException loanRequestException = new LoanRequestException();
	
	    loanRequestException.setId(loanRequestExceptionBean.getId());
	    loanRequestException.setLoanCaseId(loanRequestExceptionBean.getLoanCaseId());
	    loanRequestException.setExceptionMessage(loanRequestExceptionBean.getExceptionMessage());
	    loanRequestException.setExceptionStatus(loanRequestExceptionBean.getExceptionStatus());
	    loanRequestException.setClosureStatus(loanRequestExceptionBean.getClosureStatus());
	    loanRequestException.setClosureComment(loanRequestExceptionBean.getClosureComment());
	    loanRequestException.setClosedBy(loanRequestExceptionBean.getClosedBy());
	    loanRequestException.setClosureDate(loanRequestExceptionBean.getClosureDate());
	    loanRequestException.setCreatedBy(loanRequestExceptionBean.getCreatedBy());
	    loanRequestException.setCreationDate(loanRequestExceptionBean.getCreationDate());
	    loanRequestException.setLastModifiedBy(loanRequestExceptionBean.getLastModifiedBy());
	    loanRequestException.setLastModificationDate(loanRequestExceptionBean.getLastModificationDate());
	
	    return loanRequestException;
}

public List<LoanRequestExceptionBean> prepareListofLoanRequestExceptionBean(List<LoanRequestException> loanRequestExceptions){
    List<LoanRequestExceptionBean> beans = null;

    if(loanRequestExceptions != null && !loanRequestExceptions.isEmpty()){
    	beans = new ArrayList<LoanRequestExceptionBean>();
    	LoanRequestExceptionBean loanRequestExceptionBean = null;

    	for(LoanRequestException loanRequestException : loanRequestExceptions){
    		loanRequestExceptionBean = new LoanRequestExceptionBean();

    		loanRequestExceptionBean.setId(loanRequestException.getId());
    		loanRequestExceptionBean.setLoanCaseId(loanRequestException.getLoanCaseId());
    		loanRequestExceptionBean.setExceptionMessage(loanRequestException.getExceptionMessage());
    		loanRequestExceptionBean.setExceptionStatus(loanRequestException.getExceptionStatus());
    		loanRequestExceptionBean.setClosureStatus(loanRequestException.getClosureStatus());
    		loanRequestExceptionBean.setClosureComment(loanRequestException.getClosureComment());
    		loanRequestExceptionBean.setClosedBy(loanRequestException.getClosedBy());
    	    loanRequestExceptionBean.setClosureDate(loanRequestException.getClosureDate());
    	    loanRequestExceptionBean.setCreatedBy(loanRequestException.getCreatedBy());
    	    loanRequestExceptionBean.setCreationDate(loanRequestException.getCreationDate());
    	    loanRequestExceptionBean.setLastModifiedBy(loanRequestException.getLastModifiedBy());
    	    loanRequestExceptionBean.setLastModificationDate(loanRequestException.getLastModificationDate());

		    beans.add(loanRequestExceptionBean);
	   }
    }

    return beans;
}

public LoanRequestExceptionBean prepareLoanRequestExceptionBean(LoanRequestException loanRequestException){
    	LoanRequestExceptionBean 	loanRequestExceptionBean = new LoanRequestExceptionBean();

		loanRequestExceptionBean.setId(loanRequestException.getId());
		loanRequestExceptionBean.setLoanCaseId(loanRequestException.getLoanCaseId());
		loanRequestExceptionBean.setExceptionMessage(loanRequestException.getExceptionMessage());
		loanRequestExceptionBean.setExceptionStatus(loanRequestException.getExceptionStatus());
		loanRequestExceptionBean.setClosureStatus(loanRequestException.getClosureStatus());
		loanRequestExceptionBean.setClosureComment(loanRequestException.getClosureComment());
		loanRequestExceptionBean.setClosedBy(loanRequestException.getClosedBy());
	    loanRequestExceptionBean.setClosureDate(loanRequestException.getClosureDate());
	    loanRequestExceptionBean.setCreatedBy(loanRequestException.getCreatedBy());
	    loanRequestExceptionBean.setCreationDate(loanRequestException.getCreationDate());
	    loanRequestExceptionBean.setLastModifiedBy(loanRequestException.getLastModifiedBy());
	    loanRequestExceptionBean.setLastModificationDate(loanRequestException.getLastModificationDate());
	
	    return loanRequestExceptionBean;
}

public Product prepareProductModel(ProductBean productBean){
    Product product = new Product();

    product.setCompanyId(productBean.getCompanyId());
    product.setBranchId(productBean.getBranchId());
    product.setId(productBean.getId());
    product.setName(productBean.getName());
    product.setCode(productBean.getCode());
    product.setInterestRate(productBean.getInterestRate());
    product.setInterestRateMin(productBean.getInterestRateMin());
    product.setInterestRateMax(productBean.getInterestRateMax());
    product.setInitialAmountMin(productBean.getInitialAmountMin());
    product.setInitialAmountMax(productBean.getInitialAmountMax());
    product.setProductTypeId(productBean.getProductTypeId());
    product.setProductTypeCode(productBean.getProductTypeCode());
    product.setCurrencyId(productBean.getCurrencyId());
    product.setIsDeleted(productBean.getIsDeleted());
    product.setIsActive(productBean.getIsActive());
    
    product.setHasPenalty(productBean.getHasPenalty());
    product.setPenalty(productBean.getPenalty());
    product.setLoanTypeCode(productBean.getLoanTypeCode());
    product.setLoanDurationInMonth(productBean.getLoanDurationInMonth());
    
    product.setPenaltyFormula(productBean.getPenaltyFormula());

    return product;
}

public List<ProductBean> prepareListofProductBean(List<Product> products){
    List<ProductBean> beans = null;

    if(products != null && !products.isEmpty()){
    	beans = new ArrayList<ProductBean>();
    	ProductBean product = null;

    	for(Product productBean : products){
    		product = new ProductBean();

    	    product.setCompanyId(productBean.getCompanyId());
    	    product.setBranchId(productBean.getBranchId());
    	    product.setId(productBean.getId());
    	    product.setName(productBean.getName());
    	    product.setCode(productBean.getCode());
    	    product.setInterestRate(productBean.getInterestRate());
    	    product.setInterestRateMin(productBean.getInterestRateMin());
    	    product.setInterestRateMax(productBean.getInterestRateMax());
    	    product.setInitialAmountMin(productBean.getInitialAmountMin());
    	    product.setInitialAmountMax(productBean.getInitialAmountMax());
    	    product.setProductTypeId(productBean.getProductTypeId());
    	    product.setProductTypeCode(productBean.getProductTypeCode());
    	    product.setCurrencyId(productBean.getCurrencyId());
    	    product.setIsDeleted(productBean.getIsDeleted());
    	    product.setIsActive(productBean.getIsActive());
    	    
    	    product.setHasPenalty(productBean.getHasPenalty());
    	    product.setPenalty(productBean.getPenalty());
    	    product.setLoanTypeCode(productBean.getLoanTypeCode());
    	    product.setLoanDurationInMonth(productBean.getLoanDurationInMonth());
    	    
    	    product.setPenaltyFormula(productBean.getPenaltyFormula());

		    beans.add(product);
	   }
    }

    return beans;
}

public ProductBean prepareProductBean(Product product){
	  ProductBean 	bean = new ProductBean();

	  bean.setCompanyId(product.getCompanyId());
	  bean.setBranchId(product.getBranchId());
	  bean.setId(product.getId());
	  bean.setName(product.getName());
	  bean.setCode(product.getCode());
	  bean.setInterestRate(product.getInterestRate());
	  bean.setInterestRateMin(product.getInterestRateMin());
	  bean.setInterestRateMax(product.getInterestRateMax());
	  bean.setInitialAmountMin(product.getInitialAmountMin());
	  bean.setInitialAmountMax(product.getInitialAmountMax());
	  bean.setProductTypeId(product.getProductTypeId());
	  bean.setProductTypeCode(product.getProductTypeCode());
	  bean.setCurrencyId(product.getCurrencyId());
	  bean.setIsDeleted(product.getIsDeleted());
	  bean.setIsActive(product.getIsActive());
	  
	  bean.setHasPenalty(product.getHasPenalty());
	  bean.setPenalty(product.getPenalty());
	  bean.setLoanTypeCode(product.getLoanTypeCode());
	  bean.setLoanDurationInMonth(product.getLoanDurationInMonth());
	  bean.setPenaltyFormula(product.getPenaltyFormula());

	  return bean;
}

public LoanRepayFreq prepareLoanRepayFreqModel(LoanRepayFreqBean loanRepayFreqBean){
    LoanRepayFreq loanRepayFreq = new LoanRepayFreq();

    loanRepayFreq.setActive(loanRepayFreqBean.getActive());
    loanRepayFreq.setDeleted(loanRepayFreqBean.getDeleted());
    loanRepayFreq.setName(loanRepayFreqBean.getName());
    loanRepayFreq.setCode(loanRepayFreqBean.getCode());
    loanRepayFreq.setCreatedBy(loanRepayFreqBean.getCreatedBy());
    loanRepayFreq.setCreationDate(loanRepayFreqBean.getCreationDate());
    loanRepayFreq.setLastModifiedBy(loanRepayFreqBean.getLastModifiedBy());
    loanRepayFreq.setLastModificationDate(loanRepayFreqBean.getLastModificationDate());
    loanRepayFreq.setId(loanRepayFreqBean.getId());

    //loanRepayFreqBean.setId(null);
    return loanRepayFreq;
}

public List<LoanRepayFreqBean> prepareListofLoanRepayFreqBean(List<LoanRepayFreq> loanRepayFreqs){
    List<LoanRepayFreqBean> beans = null;

    if(loanRepayFreqs != null && !loanRepayFreqs.isEmpty()){
    	beans = new ArrayList<LoanRepayFreqBean>();
    	LoanRepayFreqBean loanRepayFreqBean = null;

    	for(LoanRepayFreq item: loanRepayFreqs){
    		loanRepayFreqBean = new LoanRepayFreqBean();

    		loanRepayFreqBean.setId(item.getId());
    		loanRepayFreqBean.setActive(item.getActive());
    		loanRepayFreqBean.setDeleted(item.getDeleted());
    		loanRepayFreqBean.setName(item.getName());
    		loanRepayFreqBean.setCode(item.getCode());
    		loanRepayFreqBean.setCreatedBy(item.getCreatedBy());
    		loanRepayFreqBean.setCreationDate(item.getCreationDate());
    		loanRepayFreqBean.setLastModifiedBy(item.getLastModifiedBy());
    		loanRepayFreqBean.setLastModificationDate(item.getLastModificationDate());

		    beans.add(loanRepayFreqBean);
	   }
    }

    return beans;
}

public LoanRepayFreqBean prepareLoanRepayFreqBean(LoanRepayFreq loanRepayFreq){
	  LoanRepayFreqBean bean = new LoanRepayFreqBean();

	  bean.setId(loanRepayFreq.getId());
	  bean.setActive(loanRepayFreq.getActive());
	  bean.setDeleted(loanRepayFreq.getDeleted());
	  bean.setName(loanRepayFreq.getName());
	  bean.setCode(loanRepayFreq.getCode());
	  bean.setCreatedBy(loanRepayFreq.getCreatedBy());
	  bean.setCreationDate(loanRepayFreq.getCreationDate());
	  bean.setLastModifiedBy(loanRepayFreq.getLastModifiedBy());
	  bean.setLastModificationDate(loanRepayFreq.getLastModificationDate());

	  return bean;
}

public MemberView prepareMemberModel(MemberViewBean memberViewBean){
    MemberView memberView = new MemberView();

    memberView.setMemberId(memberViewBean.getMemberId());
    memberView.setMemberTypeId(memberViewBean.getMemberTypeId());
    memberView.setCompanyId(memberViewBean.getCompanyId());
    memberView.setBranchId(memberViewBean.getBranchId());
    memberView.setCompmemberId(memberViewBean.getCompmemberId());
    memberView.setMiddlename(memberViewBean.getMiddlename());
    memberView.setStatusId(memberViewBean.getStatusId());
    memberView.setGender(memberViewBean.getGender());
    memberView.setReligionId(memberViewBean.getReligionId());
    memberView.setFirstname(memberViewBean.getFirstname());
    memberView.setSurname(memberViewBean.getSurname());
    memberView.setIdentificationCode(memberViewBean.getIdentificationCode());
    memberView.setIdentificationId(memberViewBean.getIdentificationId());
    memberView.setDob(memberViewBean.getDob());
    memberView.setDelDate(memberViewBean.getDelDate());
    memberView.setDelFlg(memberViewBean.getDelFlg());
    memberView.setCreatedBy(memberViewBean.getCreatedBy());
    memberView.setCreatedDate(memberViewBean.getCreatedDate());
    memberView.setMemberNo(memberViewBean.getMemberNo());

    return memberView;
}

public List<MemberViewBean> prepareListofMemberBean(List<MemberView> memberViews){
    List<MemberViewBean> beans = null;

    if(memberViews != null && !memberViews.isEmpty()){
    	beans = new ArrayList<MemberViewBean>();
    	MemberViewBean bean = null;

    	for(MemberView memberView : memberViews){
		    bean = new MemberViewBean();

		    bean.setMemberId(memberView.getMemberId());
		    bean.setMemberTypeId(memberView.getMemberTypeId());
		    bean.setCompanyId(memberView.getCompanyId());
		    bean.setBranchId(memberView.getBranchId());
		    bean.setCompmemberId(memberView.getCompmemberId());
		    bean.setMiddlename(memberView.getMiddlename());
		    bean.setStatusId(memberView.getStatusId());
		    bean.setGender(memberView.getGender());
		    bean.setReligionId(memberView.getReligionId());
		    bean.setFirstname(memberView.getFirstname());
		    bean.setSurname(memberView.getSurname());
		    bean.setIdentificationCode(memberView.getIdentificationCode());
		    bean.setIdentificationId(memberView.getIdentificationId());
		    bean.setDob(memberView.getDob());
		    bean.setDelDate(memberView.getDelDate());
		    bean.setDelFlg(memberView.getDelFlg());
		    bean.setCreatedBy(memberView.getCreatedBy());
		    bean.setCreatedDate(memberView.getCreatedDate());
		    bean.setMemberNo(memberView.getMemberNo());


		    beans.add(bean);
	   }
    }

    return beans;
}

public MemberViewBean prepareMemberBean(MemberView memberView){
	    MemberViewBean 	bean = new MemberViewBean();

	    bean.setMemberId(memberView.getMemberId());
	    bean.setMemberTypeId(memberView.getMemberTypeId());
	    bean.setCompanyId(memberView.getCompanyId());
	    bean.setBranchId(memberView.getBranchId());
	    bean.setCompmemberId(memberView.getCompmemberId());
	    bean.setMiddlename(memberView.getMiddlename());
	    bean.setStatusId(memberView.getStatusId());
	    bean.setGender(memberView.getGender());
	    bean.setReligionId(memberView.getReligionId());
	    bean.setFirstname(memberView.getFirstname());
	    bean.setSurname(memberView.getSurname());
	    bean.setIdentificationCode(memberView.getIdentificationCode());
	    bean.setIdentificationId(memberView.getIdentificationId());
	    bean.setDob(memberView.getDob());
	    bean.setDelDate(memberView.getDelDate());
	    bean.setDelFlg(memberView.getDelFlg());
	    bean.setCreatedBy(memberView.getCreatedBy());
	    bean.setCreatedDate(memberView.getCreatedDate());
	    bean.setMemberNo(memberView.getMemberNo());

	    return bean;
   }

public LoanGuarantor prepareLoanGuarantorModel(LoanGuarantorBean loanGuarantorBean){
    LoanGuarantor loanGuarantor = new LoanGuarantor();

    loanGuarantor.setCompanyId(loanGuarantorBean.getCompanyId());
    loanGuarantor.setBranchId(loanGuarantorBean.getBranchId());
    loanGuarantor.setLoanCaseId(loanGuarantorBean.getLoanCaseId());
    loanGuarantor.setMemberNo(loanGuarantorBean.getMemberNo());
    loanGuarantor.setGuarantorNo(loanGuarantorBean.getGuarantorNo());
    loanGuarantor.setGuarantorComment(loanGuarantorBean.getGuarantorComment());
    loanGuarantor.setAcceptanceStatus(loanGuarantorBean.getAcceptanceStatus());
    loanGuarantor.setRequestDate(loanGuarantorBean.getRequestDate());
    loanGuarantor.setAcceptanceDate(loanGuarantorBean.getAcceptanceDate());

    loanGuarantor.setCreatedBy(loanGuarantorBean.getCreatedBy());
    loanGuarantor.setCreationDate(loanGuarantorBean.getCreationDate());
    loanGuarantor.setLastModifiedBy(loanGuarantorBean.getLastModifiedBy());
    loanGuarantor.setLastModificationDate(loanGuarantorBean.getLastModificationDate());
    loanGuarantor.setId(loanGuarantorBean.getId());
    //loanGuarantorBean.setId(null);

    return loanGuarantor;
}

public List<LoanGuarantorBean> prepareListofLoanGuarantorBean(List<LoanGuarantor> loanGuarantors){
    List<LoanGuarantorBean> beans = null;

    if(loanGuarantors != null && !loanGuarantors.isEmpty()){
    	beans = new ArrayList<LoanGuarantorBean>();
    	LoanGuarantorBean loanGuarantor = null;

    	for(LoanGuarantor loanGuarantorBean : loanGuarantors){
    		loanGuarantor = new LoanGuarantorBean();

		    loanGuarantor.setCompanyId(loanGuarantorBean.getCompanyId());
		    loanGuarantor.setBranchId(loanGuarantorBean.getBranchId());

    		loanGuarantor.setId(loanGuarantorBean.getId());

		    loanGuarantor.setLoanCaseId(loanGuarantorBean.getLoanCaseId());
		    loanGuarantor.setMemberNo(loanGuarantorBean.getMemberNo());
		    loanGuarantor.setGuarantorNo(loanGuarantorBean.getGuarantorNo());
		    loanGuarantor.setGuarantorComment(loanGuarantorBean.getGuarantorComment());
		    loanGuarantor.setAcceptanceStatus(loanGuarantorBean.getAcceptanceStatus());
		    loanGuarantor.setRequestDate(loanGuarantorBean.getRequestDate());
		    loanGuarantor.setAcceptanceDate(loanGuarantorBean.getAcceptanceDate());

		    loanGuarantor.setCreatedBy(loanGuarantorBean.getCreatedBy());
		    loanGuarantor.setCreationDate(loanGuarantorBean.getCreationDate());
		    loanGuarantor.setLastModifiedBy(loanGuarantorBean.getLastModifiedBy());
		    loanGuarantor.setLastModificationDate(loanGuarantorBean.getLastModificationDate());

		    beans.add(loanGuarantor);
	   }
    }

    return beans;
}

public LoanGuarantorBean prepareLoanGuarantorBean(LoanGuarantor loanGuarantor){
	  LoanGuarantorBean 	bean = new LoanGuarantorBean();

	  bean.setId(loanGuarantor.getId());
	  bean.setLoanCaseId(loanGuarantor.getLoanCaseId());
	  bean.setMemberNo(loanGuarantor.getMemberNo());
	  bean.setGuarantorNo(loanGuarantor.getGuarantorNo());
	  bean.setGuarantorComment(loanGuarantor.getGuarantorComment());
	  bean.setAcceptanceStatus(loanGuarantor.getAcceptanceStatus());
	  bean.setRequestDate(loanGuarantor.getRequestDate());
	  bean.setAcceptanceDate(loanGuarantor.getAcceptanceDate());
	  bean.setCreatedBy(loanGuarantor.getCreatedBy());
	  bean.setCreationDate(loanGuarantor.getCreationDate());
	  bean.setLastModifiedBy(loanGuarantor.getLastModifiedBy());
	  bean.setLastModificationDate(loanGuarantor.getLastModificationDate());

	  return bean;
}

public LoanRequest prepareLoanRequestModel(LoanRequestBean loanRequestBean){
    LoanRequest loanRequest = new LoanRequest();

    loanRequest.setCompanyId(loanRequestBean.getCompanyId());
    loanRequest.setBranchId(loanRequestBean.getBranchId());
    loanRequest.setLoanCaseId(loanRequestBean.getLoanCaseId());
    loanRequest.setAppliedRate(loanRequestBean.getAppliedRate());
    loanRequest.setApprovalComment(loanRequestBean.getApprovalComment());
    loanRequest.setApprovalDate(loanRequestBean.getApprovalDate());
    loanRequest.setApprovedAmount(loanRequestBean.getApprovedAmount());
    loanRequest.setRequestedAmount(loanRequestBean.getRequestedAmount());
    loanRequest.setRequestStatus(loanRequestBean.getRequestStatus());
    loanRequest.setApprovedBy(loanRequestBean.getApprovedBy());
    loanRequest.setBalanceInterest(loanRequestBean.getBalanceInterest());
    loanRequest.setBalancePrincipal(loanRequestBean.getBalancePrincipal());
    loanRequest.setBalanceTotal(loanRequestBean.getBalanceTotal());
    loanRequest.setDuration(loanRequestBean.getDuration());
    loanRequest.setNoOfInstallments(loanRequestBean.getNoOfInstallments());
    loanRequest.setLastRepaymentDate(loanRequestBean.getLastRepaymentDate());
    loanRequest.setLoanAccountNumber(loanRequestBean.getLoanAccountNo());
    loanRequest.setLoanIntTotal(loanRequestBean.getLoanIntTotal());
    loanRequest.setLoanStatus(loanRequestBean.getLoanStatus());
    loanRequest.setLoanType(loanRequestBean.getLoanType());
    loanRequest.setInterestType(loanRequestBean.getInterestType());
    loanRequest.setMemberNo(loanRequestBean.getMemberNo());
    loanRequest.setProductRate(loanRequestBean.getProductRate());
    loanRequest.setRepayAmount(loanRequestBean.getRepayAmount());
    loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
    loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
    loanRequest.setRequestBy(loanRequestBean.getRequestBy());	    
    loanRequest.setRequestDate(loanRequestBean.getRequestDate());
    
    loanRequest.setDisburseDate(loanRequestBean.getDisburseDate());
    loanRequest.setDisburseBy(loanRequestBean.getDisburseBy());
    
    loanRequest.setProposedCommencementDate(loanRequestBean.getProposedCommencementDate());
    loanRequest.setActualCommencementDate(loanRequestBean.getActualCommencementDate());
    
    loanRequest.setLastModifiedBy(loanRequestBean.getLastModifiedBy());
    loanRequest.setLastModificationDate(loanRequestBean.getLastModificationDate());
    loanRequest.setId(loanRequestBean.getId());

    return loanRequest;
}

public List<LoanRequestBean> prepareListofLoanRequestBean(List<LoanRequest> loanRequests){
    List<LoanRequestBean> beans = null;

    if(loanRequests != null && !loanRequests.isEmpty()){
    	beans = new ArrayList<LoanRequestBean>();
    	LoanRequestBean loanRequest = null;

    	for(LoanRequest loanRequestBean : loanRequests){
    		loanRequest = new LoanRequestBean();

    		loanRequest.setId(loanRequestBean.getId());
    	    loanRequest.setCompanyId(loanRequestBean.getCompanyId());
    	    loanRequest.setBranchId(loanRequestBean.getBranchId());
    	    loanRequest.setLoanCaseId(loanRequestBean.getLoanCaseId());
    	    loanRequest.setAppliedRate(loanRequestBean.getAppliedRate());
    	    loanRequest.setApprovalComment(loanRequestBean.getApprovalComment());
    	    loanRequest.setApprovalDate(loanRequestBean.getApprovalDate());
    	    loanRequest.setApprovedAmount(loanRequestBean.getApprovedAmount());    	    
    	    loanRequest.setRequestedAmount(loanRequestBean.getRequestedAmount());
    	    loanRequest.setRequestStatus(loanRequestBean.getRequestStatus());    	    
    	    loanRequest.setApprovedBy(loanRequestBean.getApprovedBy());
    	    loanRequest.setBalanceInterest(loanRequestBean.getBalanceInterest());
    	    loanRequest.setBalancePrincipal(loanRequestBean.getBalancePrincipal());
    	    loanRequest.setBalanceTotal(loanRequestBean.getBalanceTotal());
    	    loanRequest.setDuration(loanRequestBean.getDuration());
    	    loanRequest.setNoOfInstallments(loanRequestBean.getNoOfInstallments());
    	    loanRequest.setLastRepaymentDate(loanRequestBean.getLastRepaymentDate());
    	   
    	    loanRequest.setDisburseDate(loanRequestBean.getDisburseDate());
    	    loanRequest.setDisburseBy(loanRequestBean.getDisburseBy());
    	    
    	    loanRequest.setProposedCommencementDate(loanRequestBean.getProposedCommencementDate());
    	    loanRequest.setActualCommencementDate(loanRequestBean.getActualCommencementDate());
    	    
    	    loanRequest.setLoanAccountNo(loanRequestBean.getLoanAccountNumber());
    	    loanRequest.setLoanIntTotal(loanRequestBean.getLoanIntTotal());
    	    loanRequest.setLoanStatus(loanRequestBean.getLoanStatus());
    	    loanRequest.setLoanType(loanRequestBean.getLoanType());
    	    loanRequest.setInterestType(loanRequestBean.getInterestType());
    	    loanRequest.setMemberNo(loanRequestBean.getMemberNo());
    	    loanRequest.setProductRate(loanRequestBean.getProductRate());
    	    loanRequest.setRepayAmount(loanRequestBean.getRepayAmount());
    	    loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
    	    loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
    	    loanRequest.setRequestBy(loanRequestBean.getRequestBy());
    	    loanRequest.setRequestDate(loanRequestBean.getRequestDate());
    	    loanRequest.setRequestedAmount(loanRequestBean.getRequestedAmount());
    	    loanRequest.setRequestStatus(loanRequestBean.getRequestStatus());
    	    loanRequest.setTotPenaltyDue(loanRequestBean.getTotPenaltyDue());
    	    loanRequest.setTotPenaltyPaid(loanRequestBean.getTotPenaltyPaid());
		    loanRequest.setLastModifiedBy(loanRequestBean.getLastModifiedBy());
		    loanRequest.setLastModificationDate(loanRequestBean.getLastModificationDate());

		    beans.add(loanRequest);
	   }
    }

    return beans;
}

public LoanRequestBean prepareLoanRequestBean(LoanRequest loanRequest){
	  LoanRequestBean 	bean = new LoanRequestBean();

	  bean.setId(loanRequest.getId());
	  bean.setCompanyId(loanRequest.getCompanyId());
	  bean.setBranchId(loanRequest.getBranchId());

	  bean.setLoanCaseId(loanRequest.getLoanCaseId());
	  bean.setLoanCaseId(loanRequest.getLoanCaseId());
	  bean.setAppliedRate(loanRequest.getAppliedRate());
	  bean.setApprovalComment(loanRequest.getApprovalComment());
      bean.setApprovalDate(loanRequest.getApprovalDate());
      bean.setApprovedAmount(loanRequest.getApprovedAmount());      
      bean.setRequestedAmount(loanRequest.getRequestedAmount());
      bean.setRequestStatus(loanRequest.getRequestStatus());      
      bean.setApprovedBy(loanRequest.getApprovedBy());
      bean.setBalanceInterest(loanRequest.getBalanceInterest());
      bean.setBalancePrincipal(loanRequest.getBalancePrincipal());
      bean.setBalanceTotal(loanRequest.getBalanceTotal());
      bean.setDuration(loanRequest.getDuration());
      bean.setNoOfInstallments(loanRequest.getNoOfInstallments());
      bean.setLastRepaymentDate(loanRequest.getLastRepaymentDate());
      
      bean.setDisburseDate(loanRequest.getDisburseDate());
      bean.setDisburseBy(loanRequest.getDisburseBy());
      
      bean.setProposedCommencementDate(loanRequest.getProposedCommencementDate());
      bean.setActualCommencementDate(loanRequest.getActualCommencementDate());
	    
      bean.setLoanAccountNo(loanRequest.getLoanAccountNumber());
      bean.setLoanIntTotal(loanRequest.getLoanIntTotal());
      bean.setLoanStatus(loanRequest.getLoanStatus());
      bean.setLoanType(loanRequest.getLoanType());
      bean.setInterestType(loanRequest.getInterestType());
      bean.setMemberNo(loanRequest.getMemberNo());
      bean.setProductRate(loanRequest.getProductRate());
      bean.setRepayAmount(loanRequest.getRepayAmount());
      bean.setRepayFrequency(loanRequest.getRepayFrequency());
      bean.setRepayFrequency(loanRequest.getRepayFrequency());
      bean.setRequestBy(loanRequest.getRequestBy());
      bean.setRequestDate(loanRequest.getRequestDate());
	  bean.setLastModifiedBy(loanRequest.getLastModifiedBy());
	  bean.setLastModificationDate(loanRequest.getLastModificationDate());

	  return bean;
   }

public AddressItems prepareAddressItemsModel(AddressItemsBean addressItemsBean){
    AddressItems addressItems = new AddressItems();

    addressItems.setAddrFieldName(addressItemsBean.getAddrFieldName());
    addressItems.setActive(addressItemsBean.getActive());
    addressItems.setDeleted(addressItemsBean.getDeleted());
    addressItems.setCreatedBy(addressItemsBean.getCreatedBy());
    addressItems.setCreationDate(addressItemsBean.getCreationDate());
    addressItems.setLastModifiedBy(addressItemsBean.getLastModifiedBy());
    addressItems.setLastModificationDate(addressItemsBean.getLastModificationDate());
    addressItems.setId(addressItemsBean.getId());

    return addressItems;
}

public List<AddressItemsBean> prepareListofAddressItemsBean(List<AddressItems> addressItemss){
    List<AddressItemsBean> beans = null;

    if(addressItemss != null && !addressItemss.isEmpty()){
    	beans = new ArrayList<AddressItemsBean>();
    	AddressItemsBean bean = null;

    	for(AddressItems addressItems : addressItemss){
		    bean = new AddressItemsBean();

		    bean.setAddrFieldName(addressItems.getAddrFieldName());
		    bean.setId(addressItems.getId());
		    bean.setActive(addressItems.getActive());
		    bean.setDeleted(addressItems.getDeleted());
		    bean.setCreatedBy(addressItems.getCreatedBy());
		    bean.setCreationDate(addressItems.getCreationDate());
		    bean.setLastModifiedBy(addressItems.getLastModifiedBy());
		    bean.setLastModificationDate(addressItems.getLastModificationDate());

		    beans.add(bean);
	   }
    }

    return beans;
}

public AddressItemsBean prepareAddressItemBean(AddressItems addressItems){
	  AddressItemsBean 	bean = new AddressItemsBean();

	  bean.setAddrFieldName(addressItems.getAddrFieldName());
	  bean.setId(addressItems.getId());
	  bean.setActive(addressItems.getActive());
	  bean.setDeleted(addressItems.getDeleted());
	  bean.setCreatedBy(addressItems.getCreatedBy());
	  bean.setCreationDate(addressItems.getCreationDate());
	  bean.setLastModifiedBy(addressItems.getLastModifiedBy());
	  bean.setLastModificationDate(addressItems.getLastModificationDate());

	  return bean;
}

public InterestType prepareInterestTypeModel(InterestTypeBean interestTypeBean){
    InterestType interestType = new InterestType();

    interestType.setActive(interestTypeBean.getActive());
    interestType.setDeleted(interestTypeBean.getDeleted());
    interestType.setTypeName(interestTypeBean.getTypeName());
    interestType.setTypeCode(interestTypeBean.getTypeCode());
    interestType.setCreatedBy(interestTypeBean.getCreatedBy());
    interestType.setCreationDate(interestTypeBean.getCreationDate());
    interestType.setLastModifiedBy(interestTypeBean.getLastModifiedBy());
    interestType.setLastModificationDate(interestTypeBean.getLastModificationDate());
    interestType.setId(interestTypeBean.getId());
    //interestTypeBean.setId(null);

    return interestType;
}

public List<InterestTypeBean> prepareListofInterestTypeBean(List<InterestType> interestTypes){
    List<InterestTypeBean> beans = null;

    if(interestTypes != null && !interestTypes.isEmpty()){
    	beans = new ArrayList<InterestTypeBean>();
    	InterestTypeBean interestType = null;

    	for(InterestType interestTypeBean : interestTypes){
    		interestType = new InterestTypeBean();

    		interestType.setId(interestTypeBean.getId());
		    interestType.setActive(interestTypeBean.getActive());
		    interestType.setDeleted(interestTypeBean.getDeleted());
		    interestType.setTypeCode(interestTypeBean.getTypeCode());
		    interestType.setTypeName(interestTypeBean.getTypeName());
		    interestType.setCreatedBy(interestTypeBean.getCreatedBy());
		    interestType.setCreationDate(interestTypeBean.getCreationDate());
		    interestType.setLastModifiedBy(interestTypeBean.getLastModifiedBy());
		    interestType.setLastModificationDate(interestTypeBean.getLastModificationDate());

		    beans.add(interestType);
	   }
    }

    return beans;
}

public InterestTypeBean prepareInterestTypeBean(InterestType interestType){
	  InterestTypeBean 	bean = new InterestTypeBean();

	  bean.setId(interestType.getId());
	  bean.setActive(interestType.getActive());
	  bean.setDeleted(interestType.getDeleted());
	  bean.setTypeCode(interestType.getTypeCode());
	  bean.setTypeName(interestType.getTypeName());
	  bean.setCreatedBy(interestType.getCreatedBy());
	  bean.setCreationDate(interestType.getCreationDate());
	  bean.setLastModifiedBy(interestType.getLastModifiedBy());
	  bean.setLastModificationDate(interestType.getLastModificationDate());

	  return bean;
}

public FiscalPeriodItem prepareFiscalPeriodItemModel(FiscalPeriodItemBean fiscalPeriodItemBean){
    FiscalPeriodItem fiscalPeriodItem = new FiscalPeriodItem();

    fiscalPeriodItem.setYear(fiscalPeriodItemBean.getYear());
    fiscalPeriodItem.setPeriodId(fiscalPeriodItemBean.getPeriodId());
    fiscalPeriodItem.setPeriodStart(fiscalPeriodItemBean.getPeriodStart());
    fiscalPeriodItem.setPeriodEnd(fiscalPeriodItemBean.getPeriodEnd());
    fiscalPeriodItem.setId(fiscalPeriodItemBean.getId());

    return fiscalPeriodItem;
}

public List<FiscalPeriodItemBean> prepareListofFiscalPeriodItemBean(List<FiscalPeriodItem> fiscalPeriodItems){
    List<FiscalPeriodItemBean> beans = null;

    if(fiscalPeriodItems != null && !fiscalPeriodItems.isEmpty()){
    	beans = new ArrayList<FiscalPeriodItemBean>();
    	FiscalPeriodItemBean fiscalPeriodItem = null;

    	for(FiscalPeriodItem fiscalPeriodItemBean : fiscalPeriodItems){
    		fiscalPeriodItem = new FiscalPeriodItemBean();

    	    fiscalPeriodItem.setYear(fiscalPeriodItemBean.getYear());
    	    fiscalPeriodItem.setPeriodId(fiscalPeriodItemBean.getPeriodId());
    	    fiscalPeriodItem.setPeriodStart(fiscalPeriodItemBean.getPeriodStart());
    	    fiscalPeriodItem.setPeriodEnd(fiscalPeriodItemBean.getPeriodEnd());
    	    fiscalPeriodItem.setId(fiscalPeriodItemBean.getId());

		    beans.add(fiscalPeriodItem);
	   }
    }

    return beans;
}

public FiscalPeriodItemBean prepareFiscalPeriodItemBean(FiscalPeriodItem fiscalPeriodItem){
	  FiscalPeriodItemBean 	bean = new FiscalPeriodItemBean();

	  bean.setYear(fiscalPeriodItem.getYear());
	  bean.setPeriodId(fiscalPeriodItem.getPeriodId());
	  bean.setPeriodStart(fiscalPeriodItem.getPeriodStart());
	  bean.setPeriodEnd(fiscalPeriodItem.getPeriodEnd());
	  bean.setId(fiscalPeriodItem.getId());

	  return bean;
}

public TaxGroup prepareTaxGroupModel(TaxGroupBean taxGroupBean){
    TaxGroup taxGroup = new TaxGroup();

    taxGroup.setId(taxGroupBean.getId());
    taxGroup.setActive(taxGroupBean.getActive());
    taxGroup.setDeleted(taxGroupBean.getDeleted());
    taxGroup.setDescription(taxGroupBean.getDescription());
    taxGroup.setCode(taxGroupBean.getCode());
    taxGroup.setCompanyId(taxGroupBean.getCompanyId());
    taxGroup.setBranchId(taxGroupBean.getBranchId());
    /*taxGroup.setCreatedBy(taxGroupBean.getCreatedBy());
    taxGroup.setCreationDate(taxGroupBean.getCreationDate());
    taxGroup.setLastModifiedBy(taxGroupBean.getLastModifiedBy());
    taxGroup.setLastModificationDate(taxGroupBean.getLastModificationDate());*/

    //taxGroupBean.setId(null);

    return taxGroup;
}

public List<TaxGroupBean> prepareListofTaxGroupBean(List<TaxGroup> taxGroups){
    List<TaxGroupBean> beans = null;

    if(taxGroups != null && !taxGroups.isEmpty()){
    	beans = new ArrayList<TaxGroupBean>();
    	TaxGroupBean taxGroup = null;

    	for(TaxGroup taxGroupBean : taxGroups){
    		taxGroup = new TaxGroupBean();

    		taxGroup.setId(taxGroupBean.getId());
		    taxGroup.setActive(taxGroupBean.getActive());
		    taxGroup.setDeleted(taxGroupBean.getDeleted());
		    taxGroup.setDescription(taxGroupBean.getDescription());
		    taxGroup.setCode(taxGroupBean.getCode());
		    taxGroup.setCompanyId(taxGroupBean.getCompanyId());
		    taxGroup.setBranchId(taxGroupBean.getBranchId());
		    /*taxGroup.setCreatedBy(taxGroupBean.getCreatedBy());
		    taxGroup.setCreationDate(taxGroupBean.getCreationDate());
		    taxGroup.setLastModifiedBy(taxGroupBean.getLastModifiedBy());
		    taxGroup.setLastModificationDate(taxGroupBean.getLastModificationDate());*/

		    beans.add(taxGroup);
	   }
    }

    return beans;
}

public TaxGroupBean prepareTaxGroupBean(TaxGroup taxGroup){
	  TaxGroupBean 	bean = new TaxGroupBean();

	  bean.setId(taxGroup.getId());
	  bean.setActive(taxGroup.getActive());
	  bean.setDeleted(taxGroup.getDeleted());
	  bean.setDescription(taxGroup.getDescription());
	  bean.setCode(taxGroup.getCode());
	  bean.setCompanyId(taxGroup.getCompanyId());
	  bean.setBranchId(taxGroup.getBranchId());	  
	  /*bean.setCreatedBy(taxGroup.getCreatedBy());
	  bean.setCreationDate(taxGroup.getCreationDate());
	  bean.setLastModifiedBy(taxGroup.getLastModifiedBy());
	  bean.setLastModificationDate(taxGroup.getLastModificationDate());*/

	  return bean;
}

public ModuleMenu prepareModuleMenuModel(ModuleMenuBean moduleMenuBean){
    ModuleMenu moduleMenu = new ModuleMenu();

    moduleMenu.setId(moduleMenuBean.getId());
	moduleMenu.setId(moduleMenuBean.getId());
	moduleMenu.setModule(moduleMenuBean.getModule());
	moduleMenu.setMenucode(moduleMenuBean.getMenucode());
	moduleMenu.setMenupath(moduleMenuBean.getMenupath());
	moduleMenu.setMenusortorder(moduleMenuBean.getMenusortorder());
	moduleMenu.setMenurole(moduleMenuBean.getMenurole());
	moduleMenu.setDisplaytext(moduleMenuBean.getDisplaytext());

	return moduleMenu;
}

public List<ModuleMenuBean> prepareListofModuleMenuBean(List<ModuleMenu> moduleMenus){
    List<ModuleMenuBean> beans = null;

    if(moduleMenus != null && !moduleMenus.isEmpty()){
    	beans = new ArrayList<ModuleMenuBean>();
    	ModuleMenuBean moduleMenu = null;

    	for(ModuleMenu item : moduleMenus){
    		moduleMenu = new ModuleMenuBean();

    		moduleMenu.setId(item.getId());
    		moduleMenu.setModule(item.getModule());
    		moduleMenu.setMenucode(item.getMenucode());
    		moduleMenu.setMenupath(item.getMenupath());
    		moduleMenu.setMenusortorder(item.getMenusortorder());
    		moduleMenu.setMenurole(item.getMenurole());
    		moduleMenu.setDisplaytext(item.getDisplaytext());

		    beans.add(moduleMenu);
	   }
    }

    return beans;
}

public ModuleMenuBean prepareModuleMenuBean(ModuleMenu moduleMenu){
	  ModuleMenuBean 	bean = new ModuleMenuBean();

	  bean.setId(moduleMenu.getId());
	  bean.setModule(moduleMenu.getModule());
	  bean.setMenucode(moduleMenu.getMenucode());
	  bean.setMenupath(moduleMenu.getMenupath());
	  bean.setMenusortorder(moduleMenu.getMenusortorder());
	  bean.setMenurole(moduleMenu.getMenurole());
	  bean.setDisplaytext(moduleMenu.getDisplaytext());

	  return bean;
}

 public Module prepareModuleModel(ModuleBean moduleBean){
    Module module = new Module();

	module.setId(moduleBean.getId());
	module.setCode(moduleBean.getCode());
	module.setDescription(moduleBean.getDescription());
	module.setIcon(moduleBean.getIcon());
	module.setSortorder(moduleBean.getSortorder());

    return module;
  }

  public List<ModuleBean> prepareListofModuleBean(List<Module> modules){
    List<ModuleBean> beans = null;

    if(modules != null && !modules.isEmpty()){
    	beans = new ArrayList<ModuleBean>();
    	ModuleBean module = null;

    	for(Module item : modules){
    		module = new ModuleBean();

    		module.setId(item.getId());
    		module.setCode(item.getCode());
    		module.setDescription(item.getDescription());
    		module.setIcon(item.getIcon());
    		module.setSortorder(item.getSortorder());

		    beans.add(module);
	   }
    }

    return beans;
  }

  public ModuleBean prepareModuleBean(Module module){
	  ModuleBean 	bean = new ModuleBean();

	  bean.setId(module.getId());
	  bean.setCode(module.getCode());
	  bean.setDescription(module.getDescription());
	  bean.setIcon(module.getIcon());
	  bean.setSortorder(module.getSortorder());

	  return bean;
  }


public LoanRepaymentSchedule prepareLoanRepaymentScheduleModel(LoanRepaymentScheduleBean loanRepaymentScheduleBean){
    LoanRepaymentSchedule loanRepaymentSchedule = new LoanRepaymentSchedule();

    loanRepaymentSchedule.setCompanyId(loanRepaymentScheduleBean.getCompanyId());
    loanRepaymentSchedule.setBranchId(loanRepaymentScheduleBean.getBranchId());
    loanRepaymentSchedule.setActive(loanRepaymentScheduleBean.getActive());

    loanRepaymentSchedule.setLoanCaseId(loanRepaymentScheduleBean.getLoanCaseId());
    loanRepaymentSchedule.setActualRepaymentAmount(loanRepaymentScheduleBean.getActualRepaymentAmount());
    loanRepaymentSchedule.setActualRepaymentDate(loanRepaymentScheduleBean.getActualRepaymentDate());
    loanRepaymentSchedule.setAmountInterest(loanRepaymentScheduleBean.getAmountInterest());
    loanRepaymentSchedule.setAmountPrincipal(loanRepaymentScheduleBean.getAmountPrincipal());
    loanRepaymentSchedule.setExpectedRepaymentAmount(loanRepaymentScheduleBean.getExpectedRepaymentAmount());
    loanRepaymentSchedule.setExpectedRepaymentDate(loanRepaymentScheduleBean.getExpectedRepaymentDate());
    loanRepaymentSchedule.setMemberNo(loanRepaymentScheduleBean.getMemberNo());
    loanRepaymentSchedule.setPaymentStatus(loanRepaymentScheduleBean.getPaymentStatus());
    loanRepaymentSchedule.setPenaltyIncurred(loanRepaymentScheduleBean.getPenaltyIncurred());
    loanRepaymentSchedule.setCummPrincipal(loanRepaymentScheduleBean.getCummPrincipal());

    loanRepaymentSchedule.setCreatedBy(loanRepaymentScheduleBean.getCreatedBy());
    loanRepaymentSchedule.setCreationDate(loanRepaymentScheduleBean.getCreationDate());
    loanRepaymentSchedule.setLastModifiedBy(loanRepaymentScheduleBean.getLastModifiedBy());
    loanRepaymentSchedule.setLastModificationDate(loanRepaymentScheduleBean.getLastModificationDate());
    loanRepaymentSchedule.setId(loanRepaymentScheduleBean.getId());
    //loanRepaymentScheduleBean.setId(null);

    return loanRepaymentSchedule;
}

public List<LoanRepaymentScheduleBean> prepareListofLoanRepaymentScheduleBean(List<LoanRepaymentSchedule> loanRepaymentSchedules){
    List<LoanRepaymentScheduleBean> beans = null;

    if(loanRepaymentSchedules != null && !loanRepaymentSchedules.isEmpty()){
    	beans = new ArrayList<LoanRepaymentScheduleBean>();
    	LoanRepaymentScheduleBean loanRepaymentSchedule = null;

    	for(LoanRepaymentSchedule loanRepaymentScheduleBean : loanRepaymentSchedules){
    		loanRepaymentSchedule = new LoanRepaymentScheduleBean();

    		loanRepaymentSchedule.setId(loanRepaymentScheduleBean.getId());

    	    loanRepaymentSchedule.setCompanyId(loanRepaymentScheduleBean.getCompanyId());
    	    loanRepaymentSchedule.setBranchId(loanRepaymentScheduleBean.getBranchId());
    	    loanRepaymentSchedule.setActive(loanRepaymentScheduleBean.getActive());

    	    loanRepaymentSchedule.setLoanCaseId(loanRepaymentScheduleBean.getLoanCaseId());
    	    loanRepaymentSchedule.setActualRepaymentAmount(loanRepaymentScheduleBean.getActualRepaymentAmount());
    	    loanRepaymentSchedule.setActualRepaymentDate(loanRepaymentScheduleBean.getActualRepaymentDate());
    	    loanRepaymentSchedule.setAmountInterest(loanRepaymentScheduleBean.getAmountInterest());
    	    loanRepaymentSchedule.setAmountPrincipal(loanRepaymentScheduleBean.getAmountPrincipal());
    	    loanRepaymentSchedule.setExpectedRepaymentAmount(loanRepaymentScheduleBean.getExpectedRepaymentAmount());
    	    loanRepaymentSchedule.setExpectedRepaymentDate(loanRepaymentScheduleBean.getExpectedRepaymentDate());
    	    loanRepaymentSchedule.setMemberNo(loanRepaymentScheduleBean.getMemberNo());
    	    loanRepaymentSchedule.setPaymentStatus(loanRepaymentScheduleBean.getPaymentStatus());
    	    loanRepaymentSchedule.setPenaltyIncurred(loanRepaymentScheduleBean.getPenaltyIncurred());
    	    loanRepaymentSchedule.setCummPrincipal(loanRepaymentScheduleBean.getCummPrincipal());

  		  	loanRepaymentSchedule.setCreatedBy(loanRepaymentScheduleBean.getCreatedBy());
		    loanRepaymentSchedule.setCreationDate(loanRepaymentScheduleBean.getCreationDate());
		    loanRepaymentSchedule.setLastModifiedBy(loanRepaymentScheduleBean.getLastModifiedBy());
		    loanRepaymentSchedule.setLastModificationDate(loanRepaymentScheduleBean.getLastModificationDate());

		    beans.add(loanRepaymentSchedule);
	   }
    }

    return beans;
}

public LoanRepaymentScheduleBean prepareLoanRepaymentScheduleBean(LoanRepaymentSchedule loanRepaymentSchedule){
	  LoanRepaymentScheduleBean 	bean = new LoanRepaymentScheduleBean();

	  bean.setId(loanRepaymentSchedule.getId());
	  bean.setCompanyId(loanRepaymentSchedule.getCompanyId());
	  bean.setBranchId(loanRepaymentSchedule.getBranchId());
	  bean.setActive(loanRepaymentSchedule.getActive());

	  bean.setLoanCaseId(loanRepaymentSchedule.getLoanCaseId());
	  bean.setActualRepaymentAmount(loanRepaymentSchedule.getActualRepaymentAmount());
	  bean.setActualRepaymentDate(loanRepaymentSchedule.getActualRepaymentDate());
	  bean.setAmountInterest(loanRepaymentSchedule.getAmountInterest());
	  bean.setAmountPrincipal(loanRepaymentSchedule.getAmountPrincipal());
	  bean.setExpectedRepaymentAmount(loanRepaymentSchedule.getExpectedRepaymentAmount());
	  bean.setExpectedRepaymentDate(loanRepaymentSchedule.getExpectedRepaymentDate());
	  bean.setMemberNo(loanRepaymentSchedule.getMemberNo());
	  bean.setPaymentStatus(loanRepaymentSchedule.getPaymentStatus());
	  bean.setPenaltyIncurred(loanRepaymentSchedule.getPenaltyIncurred());
	  bean.setCummPrincipal(loanRepaymentSchedule.getCummPrincipal());

	  bean.setCreatedBy(loanRepaymentSchedule.getCreatedBy());
	  bean.setCreationDate(loanRepaymentSchedule.getCreationDate());
	  bean.setLastModifiedBy(loanRepaymentSchedule.getLastModifiedBy());
	  bean.setLastModificationDate(loanRepaymentSchedule.getLastModificationDate());

	  return bean;
}

public AppConfiguration prepareAppConfigurationModel(AppConfigurationBean appConfigurationBean){
    AppConfiguration appConfiguration = new AppConfiguration();

    appConfiguration.setActive(appConfigurationBean.getActive());
    appConfiguration.setDeleted(appConfigurationBean.getDeleted());
    appConfiguration.setCompanyId(appConfigurationBean.getCompanyId());
    appConfiguration.setBranchId(appConfigurationBean.getBranchId());

    appConfiguration.setConfigName(appConfigurationBean.getConfigName());
    appConfiguration.setConfigType(appConfigurationBean.getConfigType());
    appConfiguration.setConfigMinValue(appConfigurationBean.getConfigMinValue());
    appConfiguration.setConfigMaxValue(appConfigurationBean.getConfigMaxValue());

    appConfiguration.setComputationType(appConfigurationBean.getComputationType());
    appConfiguration.setFormulaValue(appConfigurationBean.getFormulaValue());
    
    appConfiguration.setOperand(appConfigurationBean.getOperand());
    appConfiguration.setMultiplier(appConfigurationBean.getMultiplier());
   
    appConfiguration.setCreatedBy(appConfigurationBean.getCreatedBy());
    appConfiguration.setCreationDate(appConfigurationBean.getCreationDate());
    appConfiguration.setLastModifiedBy(appConfigurationBean.getLastModifiedBy());
    appConfiguration.setLastModificationDate(appConfigurationBean.getLastModificationDate());
    appConfiguration.setId(appConfigurationBean.getId());
    //appConfigurationBean.setId(null);

    return appConfiguration;
}

public List<AppConfigurationBean> prepareListofAppConfigurationBean(List<AppConfiguration> appConfigurations){
    List<AppConfigurationBean> beans = null;

    if(appConfigurations != null && !appConfigurations.isEmpty()){
    	beans = new ArrayList<AppConfigurationBean>();
    	AppConfigurationBean appConfiguration = null;

    	for(AppConfiguration appConfigurationBean : appConfigurations){
    		appConfiguration = new AppConfigurationBean();

    		appConfiguration.setId(appConfigurationBean.getId());
		    appConfiguration.setActive(appConfigurationBean.getActive());
		    appConfiguration.setDeleted(appConfigurationBean.getDeleted());
		    appConfiguration.setCompanyId(appConfigurationBean.getCompanyId());
		    appConfiguration.setBranchId(appConfigurationBean.getBranchId());

		    appConfiguration.setConfigName(appConfigurationBean.getConfigName());
		    appConfiguration.setConfigType(appConfigurationBean.getConfigType());
		    appConfiguration.setConfigMinValue(appConfigurationBean.getConfigMinValue());
		    appConfiguration.setConfigMaxValue(appConfigurationBean.getConfigMaxValue());

		    appConfiguration.setComputationType(appConfigurationBean.getComputationType());
		    appConfiguration.setFormulaValue(appConfigurationBean.getFormulaValue());

		    appConfiguration.setOperand(appConfigurationBean.getOperand());
		    appConfiguration.setMultiplier(appConfigurationBean.getMultiplier());

		    appConfiguration.setCreatedBy(appConfigurationBean.getCreatedBy());
		    appConfiguration.setCreationDate(appConfigurationBean.getCreationDate());
		    appConfiguration.setLastModifiedBy(appConfigurationBean.getLastModifiedBy());
		    appConfiguration.setLastModificationDate(appConfigurationBean.getLastModificationDate());

		    beans.add(appConfiguration);
	   }
    }

    return beans;
}

public AppConfigurationBean prepareAppConfigurationBean(AppConfiguration appConfiguration){
	  AppConfigurationBean 	bean = new AppConfigurationBean();

	  bean.setId(appConfiguration.getId());
	  bean.setActive(appConfiguration.getActive());
	  bean.setDeleted(appConfiguration.getDeleted());
	  bean.setCompanyId(appConfiguration.getCompanyId());
	  bean.setBranchId(appConfiguration.getBranchId());

	  bean.setConfigName(appConfiguration.getConfigName());
	  bean.setConfigType(appConfiguration.getConfigType());
	  bean.setConfigMinValue(appConfiguration.getConfigMinValue());
	  bean.setConfigMaxValue(appConfiguration.getConfigMaxValue());
	  
	  bean.setComputationType(appConfiguration.getComputationType());
	  bean.setFormulaValue(appConfiguration.getFormulaValue());

	  bean.setOperand(appConfiguration.getOperand());
	  bean.setMultiplier(appConfiguration.getMultiplier());

	  bean.setCreatedBy(appConfiguration.getCreatedBy());
	  bean.setCreationDate(appConfiguration.getCreationDate());
	  bean.setLastModifiedBy(appConfiguration.getLastModifiedBy());
	  bean.setLastModificationDate(appConfiguration.getLastModificationDate());

	  return bean;
   }

public RepaymentUploadItems prepareRepaymentUploadItemModel(RepaymentUploadItemsBean bean){
    RepaymentUploadItems item = new RepaymentUploadItems();

    item.setCompanyId(bean.getCompanyId());
    item.setId(bean.getId());
    item.setBranchId(bean.getBranchId());
    item.setAmount(bean.getAmount());
    item.setLoanCaseId(bean.getLoanCaseId());
    item.setUploadedBy(bean.getUploadedBy());
    item.setUploadedDate(bean.getUploadedDate());
    item.setMemberNo(bean.getMemberNo());
    item.setBatchId(bean.getBatchId());
    item.setActive(bean.getActive());
    item.setPenalty(bean.getPenalty());

	return item;
}

public List<RepaymentUploadItemsBean> prepareListofRepaymentUploadItemBean(List<RepaymentUploadItems> items){
    List<RepaymentUploadItemsBean> beans = null;

    if(items != null && !items.isEmpty()){
    	beans = new ArrayList<RepaymentUploadItemsBean>();
    	RepaymentUploadItemsBean bean = null;

    	for(RepaymentUploadItems item : items){
    		bean = new RepaymentUploadItemsBean();

    		bean.setCompanyId(item.getCompanyId());
    		bean.setId(item.getId());
    		bean.setBranchId(item.getBranchId());
    		bean.setAmount(item.getAmount());
    		bean.setLoanCaseId(item.getLoanCaseId());
    		bean.setUploadedBy(item.getUploadedBy());
    		bean.setUploadedDate(item.getUploadedDate());
    		bean.setMemberNo(item.getMemberNo());
    		bean.setBatchId(item.getBatchId());
    		bean.setActive(item.getActive());
    		bean.setPenalty(item.getPenalty());

    		beans.add(bean);
	   }
    }

    return beans;
}

public RepaymentUploadItemsBean prepareRepaymentUploadItemBean(RepaymentUploadItems item){
        RepaymentUploadItemsBean 	bean = new RepaymentUploadItemsBean();
  		
		bean.setCompanyId(item.getCompanyId());
		bean.setId(item.getId());
		bean.setBranchId(item.getBranchId());
		bean.setAmount(item.getAmount());
		bean.setLoanCaseId(item.getLoanCaseId());
		bean.setUploadedBy(item.getUploadedBy());
		bean.setUploadedDate(item.getUploadedDate());
		bean.setMemberNo(item.getMemberNo());
		bean.setBatchId(item.getBatchId());
		bean.setActive(item.getActive());
		bean.setPenalty(item.getPenalty());
		
		return bean;
}

public Currency prepareCurrencyModel(CurrencyBean currencyBean){
    Currency currency = new Currency();

    currency.setActive(currencyBean.getActive());
    currency.setDeleted(currencyBean.getDeleted());
    currency.setIsBase(currencyBean.getIsBase());
    currency.setCurrencyCode(currencyBean.getCurrencyCode());
    currency.setCurrencyName(currencyBean.getCurrencyName());
    currency.setCreatedBy(currencyBean.getCreatedBy());
    currency.setCreationDate(currencyBean.getCreationDate());
    currency.setLastModifiedBy(currencyBean.getLastModifiedBy());
    currency.setLastModificationDate(currencyBean.getLastModificationDate());
    currency.setId(currencyBean.getId());

    return currency;
}

public List<CurrencyBean> prepareListofCurrencyBean(List<Currency> currencys){
    List<CurrencyBean> beans = null;

    if(currencys != null && !currencys.isEmpty()){
    	beans = new ArrayList<CurrencyBean>();
    	CurrencyBean currency = null;

    	for(Currency currencyBean : currencys){
    		currency = new CurrencyBean();

    		currency.setId(currencyBean.getId());
		    currency.setActive(currencyBean.getActive());
		    currency.setDeleted(currencyBean.getDeleted());
		    currency.setCurrencyCode(currencyBean.getCurrencyCode());
		    currency.setCurrencyName(currencyBean.getCurrencyName());
		    currency.setIsBase(currencyBean.getIsBase());
			currency.setCreatedBy(currencyBean.getCreatedBy());
		    currency.setCreationDate(currencyBean.getCreationDate());
		    currency.setLastModifiedBy(currencyBean.getLastModifiedBy());
		    currency.setLastModificationDate(currencyBean.getLastModificationDate());

		    beans.add(currency);
	   }
    }

    return beans;
}

public CurrencyBean prepareCurrencyBean(Currency currency){
	  CurrencyBean 	bean = new CurrencyBean();

	  bean.setId(currency.getId());
	  bean.setActive(currency.getActive());
	  bean.setDeleted(currency.getDeleted());
	  bean.setCurrencyCode(currency.getCurrencyCode());
	  bean.setCurrencyName(currency.getCurrencyName());
	  bean.setIsBase(currency.getIsBase());
	  bean.setCreatedBy(currency.getCreatedBy());
	  bean.setCreationDate(currency.getCreationDate());
	  bean.setLastModifiedBy(currency.getLastModifiedBy());
	  bean.setLastModificationDate(currency.getLastModificationDate());

	  return bean;
}

public Reports prepareReportsModel(ReportsBean reportsBean){
    Reports reports = new Reports();

    reports.setReportID(reportsBean.getReportID());
	reports.setReportcode(reportsBean.getReportcode());
	reports.setReportFileName(reportsBean.getReportFileName());
	reports.setDescription(reportsBean.getDescription());
	reports.setReportrole(reportsBean.getReportrole());
	reports.setRangeCriteria(reportsBean.getRangeCriteria());
	reports.setSortCode(reportsBean.getSortCode());
	reports.setType(reportsBean.getType());
	reports.setReportgroup(reportsBean.getReportgroup());

	
	return reports;
}

public List<ReportsBean> prepareListofReportsBean(List<Reports> reportss){
    List<ReportsBean> beans = null;

    if(reportss != null && !reportss.isEmpty()){
    	beans = new ArrayList<ReportsBean>();
    	ReportsBean reports = null;

    	for(Reports item : reportss){
    		reports = new ReportsBean();

    	    reports.setReportID(item.getReportID());
    		reports.setReportcode(item.getReportcode());
    		reports.setReportFileName(item.getReportFileName());
    		reports.setDescription(item.getDescription());
    		reports.setReportrole(item.getReportrole());
    		reports.setRangeCriteria(item.getRangeCriteria());
    		reports.setSortCode(item.getSortCode());
    		reports.setType(item.getType());
    		reports.setReportgroup(item.getReportgroup());
    		
    		beans.add(reports);
	   }
    }

    return beans;
}

public ReportsBean prepareReportsBean(Reports reports){
    ReportsBean 	bean = new ReportsBean();

    bean.setReportID(reports.getReportID());
    bean.setReportcode(reports.getReportcode());
    bean.setReportFileName(reports.getReportFileName());
    bean.setDescription(reports.getDescription());
    bean.setReportrole(reports.getReportrole());
	bean.setRangeCriteria(reports.getRangeCriteria());
	bean.setSortCode(reports.getSortCode());
	bean.setType(reports.getType());
	bean.setReportgroup(reports.getReportgroup());
  
    return bean;
}

public ReportGroup prepareReportGroupModel(ReportGroupBean reportGroupBean){
    ReportGroup reportGroup = new ReportGroup();

	reportGroup.setId(reportGroupBean.getId());
	reportGroup.setCode(reportGroupBean.getCode());
	reportGroup.setDescription(reportGroupBean.getDescription());
	reportGroup.setFormatclass(reportGroupBean.getFormatclass());

    return reportGroup;
}

public List<ReportGroupBean> prepareListofReportGroupBean(List<ReportGroup> reportGroups){
    List<ReportGroupBean> beans = null;

    if(reportGroups != null && !reportGroups.isEmpty()){
    	beans = new ArrayList<ReportGroupBean>();
    	ReportGroupBean reportGroup = null;

    	for(ReportGroup item : reportGroups){
    		reportGroup = new ReportGroupBean();

    		reportGroup.setId(item.getId());
    		reportGroup.setCode(item.getCode());
    		reportGroup.setDescription(item.getDescription());
    		reportGroup.setFormatclass(item.getFormatclass());

		    beans.add(reportGroup);
	   }
    }

    return beans;
}

public ReportGroupBean prepareReportGroupBean(ReportGroup reportGroup){
	  ReportGroupBean 	bean = new ReportGroupBean();

	  bean.setId(reportGroup.getId());
	  bean.setCode(reportGroup.getCode());
	  bean.setDescription(reportGroup.getDescription());
		  bean.setFormatclass(reportGroup.getFormatclass());

	  return bean;
}
}