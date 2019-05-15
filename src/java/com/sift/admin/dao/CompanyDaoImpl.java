package com.sift.admin.dao;

import java.io.File;
import java.util.Calendar;
import java.util.List;  
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Repository;
import com.sift.admin.interfaces.*;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.model.CountryAddressFilter;
import com.sift.admin.model.User;
import com.sift.admin.bean.AddressEntriesBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.MailBean;
import com.sift.loan.utility.PassEncoder;
import com.sift.loan.utility.RandomPasswordGenerator;
import com.sift.webservice.utility.WebServiceUtility;
  
/** 
 * @author XTOFFEL CONSULT
 * 
 */  
@Repository("companyDao")  
public class CompanyDaoImpl implements CompanyDao{  
 
 BeanMapperUtility   beanMapper =new BeanMapperUtility();
 RandomPasswordGenerator passUTIL=new RandomPasswordGenerator();
 EazyCoopUtility   EazyCoopUTIL =new EazyCoopUtility();

 @Autowired  
 private SessionFactory sessionFactory;  
   
 public void addCompany(Company company){  
   sessionFactory.getCurrentSession().saveOrUpdate(company);  
 }  
 
 public boolean addCompany(Company company,List<AddressEntriesBean> beanList){  
	 org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 boolean success=false;
	
	 try {
		    session = sessionFactory.getCurrentSession();
		    //tx = session.beginTransaction();
		    //System.out.println("companyId:="+company.getId());
		    
		    session.saveOrUpdate(company);		   
		    String companyId=null;
		    String companyCode="";
		  
		    if(company.getId()!=null){
			   deleteCompanyAddressEntries(company,session);
			   companyId=company.getId().toString();
		    }else{
			   companyId=getCompanyByName(company.getName()).getId().toString();
		    }
		    		    
		    for(int k=companyId.length(); k<4; k++){
		    	companyCode+="0";
		    }
		    
		    companyCode+=companyId;
		    company.setCode(companyCode);
		    session.saveOrUpdate(company);
		    
		    //System.out.println("companyId:=" +String.valueOf(companyId));		   
		    //now attempt to insert new address entries
		    for(AddressEntriesBean item: beanList){
			   item.setKeyId(companyId);		   
			   session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
		    }
		   
		    success=true;
		   //if(!tx.wasCommitted()){tx.commit();}
	}catch(HibernateException e) {
		success=false;
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		
		throw e;
	}catch(RuntimeException e){
		success=false;
		try{
			if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}finally{
		  tx=null;
	}	
	
	return success;
 }  
 
 /*************************************************************************************************************
 public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser){
	 org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 EazyCoopUtility eazy=new EazyCoopUtility();
	 boolean success=false;
	 
	 try{
		    //tx=session.beginTransaction();
		    session = sessionFactory.getCurrentSession();
		    //tx = session.beginTransaction();
		    System.out.println("companyId:="+company.getId());
		    
		    session.saveOrUpdate(company);		   
		    String companyId=null;
		    String companyCode="";
		  
		    if(company.getId()!=null){
			   deleteCompanyAddressEntries(company,session);
			   companyId=company.getId().toString();
		    }else{
			   companyId=getCompanyByName(company.getName()).getId().toString();
		    }
		    		    
		    for(int k=companyId.length(); k<4; k++){
		       companyCode+="0";
		    }
		    
		    companyCode+=companyId;
		    company.setCode(companyCode);
		    session.saveOrUpdate(company);
		    
		    //System.out.println("companyId:=" +String.valueOf(companyId));		   
		    //now attempt to insert new address entries
		    for(AddressEntriesBean item: beanList){
			   item.setKeyId(companyId);		   
			   session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
		    }		    
		    
		    //create default branch
	    	//create default branch
	    	Branch brn=eazy.createBranchFromCompany(company);
	    	
	    	//save branch
	    	session.save(brn);
	    	session.refresh(brn);
	    	
	    	//create default configs for coop
	    	eazy.createDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId(),1);
	    	eazy.createCoyDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId());
	    	
	    	//create default configs for branch
	    	eazy.createDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId(),2);
	    	eazy.createBranchDefaults(sessionFactory, brn.getId().toString(), brn.getCompanyId());
	    	
	    	//apply same address to branch
	    	for(AddressEntriesBean item: beanList){
	    		item.setTypeId("2");
				item.setKeyId(brn.getId().toString());		   
				session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
			}
	    	
	    	success=true;
		    
	}catch(HibernateException e) {
		success=false;
		try{
			//if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(RuntimeException e){
		success=false;
		try{
			//if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		
		throw e;
	}catch(Exception e){
		success=false;
		e.printStackTrace();
		//throw e;
	}finally{
		  tx=null;
	}	   
	
	return success;
 } 
 
 public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser,String contextPath){
	 org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 EazyCoopUtility eazy=new EazyCoopUtility();
	 WebServiceUtility webServiceUtility = new WebServiceUtility();
	 boolean success=false;
	 
	 String srcImagePath=contextPath+Definitions.SRC_IMAGE_PATH;
	 String srcPath=contextPath+Definitions.SRC_PATH;
	 	 
	 try{
		    //tx=session.beginTransaction();
		    session = sessionFactory.getCurrentSession();
		    //tx = session.beginTransaction();
		    System.out.println("companyId:="+company.getId());
		    
		    session.saveOrUpdate(company);		   
		    String companyId=null;
		    String companyCode="";
		  
		    if(company.getId()!=null){
			   deleteCompanyAddressEntries(company,session);
			   companyId=company.getId().toString();
		    }else{
			   companyId=getCompanyByName(company.getName()).getId().toString();
		    }
		    		    
		    for(int k=companyId.length(); k<4; k++){
		       companyCode+="0";
		    }
		    
		    companyCode+=companyId;
		    company.setCode(companyCode);
		    session.saveOrUpdate(company);
		    
		    //system.out.println("companyId:=" +String.valueOf(companyId));		   
		    //now attempt to insert new address entries
		    for(AddressEntriesBean item: beanList){
			   item.setKeyId(companyId);		   
			   session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
		    }		    
		    
		    //create default branch
	    	//create default branch
	    	Branch brn=eazy.createBranchFromCompany(company);
	    	
	    	//save branch
	    	session.save(brn);
	    	session.refresh(brn);
	    	
	    	//create default configs for coop
	    	eazy.createDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId(),1);
	    	eazy.createCoyDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId());
	    	
	    	//create default configs for branch
	    	eazy.createDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId(),2);
	    	eazy.createBranchDefaults(sessionFactory, brn.getId().toString(),brn.getCompanyId());
	    	
	    	//apply same address to branch
	    	for(AddressEntriesBean item: beanList){
	    		item.setTypeId("2");
				item.setKeyId(brn.getId().toString());		   
				session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
			}
	    	
	    	if(createCoyUser){
	    		//System.out.println("srcImagePath:="+srcImagePath);
	    		//System.out.println("srcPath:="+srcPath);	    		
	    		boolean done=EazyCoopUTIL.copyFile(new File(srcImagePath),new File(srcPath +companyId + ".jpg"));
	    	}
	    	            
            try{
				if(brn.getConnectToEazyCoop().equalsIgnoreCase("Y")){
				        String resource = "coop";
				        webServiceUtility.webserviceClient(resource, webServiceUtility.createCoop(brn.getCompanyId(), 
				        		brn.getId().toString(), company.getName(), brn.getBranchName()));
				}
			}catch(RuntimeException e){
				e.printStackTrace();
			}

	    	
	    	success=true;
		    

	}catch(HibernateException e) {
		success=false;
		try{
			//if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(RuntimeException e){
		success=false;
		try{
			//if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(Exception e){
		success=false;
		e.printStackTrace();
		//throw e;
	}finally{
		  tx=null;
	}	
	
	return success;
 }
 *********************************************************************************************/
 
 public boolean addCompany(Company company,List<AddressEntriesBean> beanList,boolean createCoyUser,String contextPath,String currencyCode){
	 org.hibernate.classic.Session session=null;
	 Transaction tx=null;  
	 EazyCoopUtility eazy=new EazyCoopUtility();
	 WebServiceUtility webServiceUtility = new WebServiceUtility();
	 boolean success=false;
	 
	 String srcImagePath=contextPath+Definitions.SRC_IMAGE_PATH;
	 String srcPath=contextPath+Definitions.SRC_PATH;
	 	 
	 try{
		    //tx=session.beginTransaction();
		    session = sessionFactory.getCurrentSession();
		    //tx = session.beginTransaction();
		    System.out.println("companyId:="+company.getId());
		    
		    session.saveOrUpdate(company);		   
		    String companyId=null;
		    String companyCode="";
		  
		    if(company.getId()!=null){
			   deleteCompanyAddressEntries(company,session);
			   companyId=company.getId().toString();
		    }else{
			   companyId=getCompanyByName(company.getName()).getId().toString();
		    }
		    		    
		    for(int k=companyId.length(); k<4; k++){
		       companyCode+="0";
		    }
		    
		    companyCode+=companyId;
		    company.setCode(companyCode);
		    session.saveOrUpdate(company);
		    
		    //system.out.println("companyId:=" +String.valueOf(companyId));		   
		    //now attempt to insert new address entries
		    for(AddressEntriesBean item: beanList){
			   item.setKeyId(companyId);		   
			   session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
		    }		    
		    
		    //create default branch
	    	//create default branch
	    	Branch brn=eazy.createBranchFromCompany(company,currencyCode);
	    	
	    	//save branch
	    	session.save(brn);
	    	session.refresh(brn);
	    	
	    	//create default configs for coop
	    	eazy.createDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId(),1);
	    	eazy.createCoyDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId());
	    	
	    	//create default configs for branch
	    	eazy.createDefaults(sessionFactory,brn.getId().toString(),brn.getCompanyId(),2);
	    	eazy.createBranchDefaults(sessionFactory, brn.getId().toString(),brn.getCompanyId());
	    	
	    	//apply same address to branch
	    	for(AddressEntriesBean item: beanList){
	    		item.setTypeId("2");
				item.setKeyId(brn.getId().toString());		   
				session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
			}
	    	
	    	if(createCoyUser){
	    		//System.out.println("srcImagePath:="+srcImagePath);
	    		//System.out.println("srcPath:="+srcPath);	    		
	    		boolean done=EazyCoopUTIL.copyFile(new File(srcImagePath),new File(srcPath +companyId + ".jpg"));
	    	}
	    	            
            try{
				if(brn.getConnectToEazyCoop().equalsIgnoreCase("Y")){
				        String resource = "coop";
				        webServiceUtility.webserviceClient(resource, webServiceUtility.createCoop(brn.getCompanyId(), 
				        		brn.getId().toString(), company.getName(), brn.getBranchName(),company.getShortName()));
				}
			}catch(RuntimeException e){
				e.printStackTrace();
			}

	    	
	    	success=true;
		    
/*		    if(createCoyUser){
		    			    	
		    	User user=new User();
		    	String tempPass=passUTIL.generatePswd();
		    	
		    	user.setCompanyId(companyId);
		    	user.setBranchId(brn.getId().toString());
		    	user.setEmail(company.getEmail());
		    	user.setPassword(new PassEncoder().doEndcodePass(tempPass));
		    	user.setUserId(company.getEmail());
		    	user.setUsername(company.getShortName());
		    	user.setCreatedBy("Super Admin");
		    	user.setCreationDate(new java.util.Date());
		    	user.setGroupId(Definitions.COMPANY_ADMIN_CODE);
		    	user.setDeleted("N");
		    	user.setAuthMode("DB");
		    	user.setMustChangePass("Y");
		    	user.setEnabled(1);
		    	
		    	session.saveOrUpdate(user);    	
		    	
		    	//email password to user
		    	MailBean MB=eazy.getMailConfig();
		    	MB.setToemail(company.getEmail());
		    	MB.setSubject(Definitions.MAIL_SUBJECT_NEW_USER_SETUP);
		    	
		    	String uri=(String)new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
			    String mailBody= "<HTML>" +
			    		"	<HEAD>" +
			    		"    <style type=\"text/css\">" +
						"	<!--" +
						"	.style2 {" +
						"		font-family: Verdana, Arial, Helvetica, sans-serif;" +
						"		font-size: 14px;" +
						"	}" +
						"	-->" +
						"	</style>" +
						"   </HEAD>" +
						"  <BODY>" +
						"	 <p class=\"style2\">Hello "+company.getEmail()+", </p>" +
						"	<p class=\"style2\">You have been setup as a Company admin on EazyCoop Solution. <br>" +
						"   <strong>Below is your logon details: </strong></p>" +
						"	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
						"	  <tr>" +
						"	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
						"	    <td width=\"50%\"><span class=\"style2\">" + company.getEmail() + "</span></td>" +
						"	  </tr>" +
						
						"	  <tr>" +
						"	    <td><span class=\"style2\">Temporary Password:</span></td>" +
						"	    <td><span class=\"style2\">" + tempPass + " </span></td>" +
						"	  </tr>" +
						
						"	  <tr>" +
						"	    <td><span class=\"style2\">Application URL:</span></td>" +
						"	    <td><span class=\"style2\">" + uri + " </span></td>" +
						"	  </tr>" +
		
					    "     <tr>" +
						"	    <td colspan=2><hr/></td>" +
						"	    " +
						"	  </tr>" +
						
		                "     <tr>" +
						"	    <td colspan=2><hr/></td>" +
						"	    " +
						"	  </tr>" +        
						"	</table>" +
						"	<p class=\"style2\"><br>" +
						"	  Thanks </p> </BODY></HTML>" ;
	    
                	MB.setMailBody(mailBody);	    	
                	eazy.sendMail(MB);
		    }
*/		   
		    //if(!tx.wasCommitted()){tx.commit();}
	}catch(HibernateException e) {
		success=false;
		try{
			//if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(RuntimeException e){
		success=false;
		try{
			//if(!tx.wasRolledBack()){tx.rollback();}
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(Exception e){
		success=false;
		e.printStackTrace();
		//throw e;
	}finally{
		  tx=null;
	}	
	
	return success;
 }
  
 @SuppressWarnings("unchecked")  
 public List<Company> listCompanies(){  
    return (List<Company>) sessionFactory.getCurrentSession().createCriteria(Company.class).list();  
 }  
  
 public List<Company> listCompaniesDistinct(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Company.class);
	 criteria.add(Restrictions.eq("id",Integer.parseInt(companyId)));
	 
	 return  (List<Company>)criteria.list();
 }
 
 public Company getCompany(int id){  
    return (Company) sessionFactory.getCurrentSession().get(Company.class,id);  
 }  
 
 public Company getCompanyByName(String name){  
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Company.class);
	 criteria.add(Restrictions.eq("companyName",name));
	 
	 return  criteria==null?null: (Company)criteria.list().get(0);
 }  
 
 public Company getCompanyByName(String name,org.hibernate.classic.Session session){  
	 Criteria criteria = session.createCriteria(Company.class);
	 criteria.add(Restrictions.eq("companyName",name));
	 
	 return  criteria==null?null: (Company)criteria.list().get(0);
 }
  
 public void deleteCompany(Company company,List<AddressEntriesBean> beanList) {  
	 sessionFactory.getCurrentSession().createQuery("delete from company where id = "+company.getId()).executeUpdate();  
 } 
 
 public void deleteCompany(Company company) {  
	 sessionFactory.getCurrentSession().createQuery("delete from company where id = "+company.getId()).executeUpdate();  
 }
 
 public void deleteCompanyAddressEntries(Company company) {  
	 sessionFactory.getCurrentSession().createQuery("delete from AddressEntries where typeId=1 and keyId = "+company.getId()).executeUpdate();  
 }
 
 public void deleteCompanyAddressEntries(Company company,org.hibernate.classic.Session session) {  
	 session.createQuery("delete from AddressEntries where typeId=1 and keyId = "+company.getId()).executeUpdate();  
 }

}