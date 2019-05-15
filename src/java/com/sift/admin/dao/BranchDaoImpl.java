package com.sift.admin.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.model.User;
import com.sift.admin.bean.AddressEntriesBean;
import com.sift.admin.bean.BranchBean;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.utility.MailBean;
import com.sift.loan.utility.PassEncoder;
import com.sift.loan.utility.RandomPasswordGenerator;

/**
 * @author XTOFFEL CONSULT
 */
@Repository("branchDao")
public class BranchDaoImpl implements BranchDao{

 @Autowired
 private SessionFactory sessionFactory;
 
 BeanMapperUtility   beanMapper =new BeanMapperUtility();
 EazyCoopUtility     UTIL= new EazyCoopUtility();
 RandomPasswordGenerator passUTIL=new RandomPasswordGenerator();
 HelperUtility helperUTIL=new HelperUtility(); 

 public void addBranch(Branch branch){
   sessionFactory.getCurrentSession().saveOrUpdate(branch);
 }
 
 public boolean addBranch(Branch branch,List<AddressEntriesBean> beanList){  
	 Session session=null;
	 Transaction tx=null; 
	 boolean success=false;
	 boolean isNew=false;
	 
	 try{
		   session=sessionFactory.getCurrentSession();
		   //session = sessionFactory.openSession();		   
		   //tx = session.beginTransaction();
		   if(branch.getId()==null){//new branch
			     isNew=true;
			     Integer serialPos=UTIL.getNextSerial(sessionFactory,branch.getCompanyId());
				 String  serialStr=serialPos.toString();
				 
				 for(int k=0; k<=3-serialStr.length(); k++){
					 serialStr="0"+serialStr;
				 }
				 
				 branch.setBranchCode(serialStr);
				 session.saveOrUpdate(branch); 
				 UTIL.incrementSerial(sessionFactory,branch.getCompanyId());
				 System.out.println(""+branch.getId());
		   }else{
			     session.saveOrUpdate(branch); 
		   }
		   
		   String branchId=null;
		   
		   if(!isNew){
			   deleteBranchAddressEntries(branch);
			   branchId=branch.getId().toString();
		   }else{
			   branchId=getBranchByName(branch.getBranchName()).getId().toString();
			   
			   //create default configs for branch
		       UTIL.createDefaults(sessionFactory,branchId,branch.getCompanyId(),2);
		       UTIL.createBranchDefaults(sessionFactory,branchId,branch.getCompanyId());

		   }
		   	   
		   //now attempt to insert new address entries
		   for(AddressEntriesBean item: beanList){
			   item.setKeyId(branchId);			   
			   session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
		   }
		   
		   if(isNew){
			   //create default user for the branch		   
/*			   User user=new User();
		       String tempPass=passUTIL.generatePswd();
		       
		       user.setCompanyId(branch.getCompanyId());
		       user.setBranchId(branchId);
		       user.setEmail(branch.getEmail());
		       user.setPassword(new PassEncoder().doEndcodePass(tempPass));
		       user.setUserId(branch.getEmail());
		       user.setUsername(branch.getEmail());
		       user.setCreatedBy("Super Admin");
		       user.setCreationDate(new java.util.Date());
		       user.setGroupId(Definitions.BRANCH_ADMIN_CODE);
		       user.setDeleted("N");
		       user.setAuthMode("DB");
		       user.setMustChangePass("Y");
		       user.setEnabled(1);
		    	
		       session.saveOrUpdate(user);*/
		       		       
		       //email password to user
		       /*MailBean MB=UTIL.getMailConfig();
		       MB.setToemail(branch.getEmail());
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
		       					" <style type=\"text/css\">" +
								"	<!--" +
								"	.style2 {" +
								"		font-family: Verdana, Arial, Helvetica, sans-serif;" +
								"		font-size: 14px;" +
								"	}" +
								"	-->" +
								"	</style>" +
								"	 <p class=\"style2\">Hello "+branch.getEmail()+", </p>" +
								"	 <p class=\"style2\">You have been setup as a branch admin on EazyCoop Solution. <br>" +
								"    <strong>Below is your logon details: </strong></p>" +
								"	 <table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
								"	  <tr>" +
								"	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
								"	    <td width=\"50%\"><span class=\"style2\">" + branch.getEmail() + "</span></td>" +
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
								"	Thanks </p> </BODY></HTML>" ;
		       		       
		       MB.setMailBody(mailBody);		    	
		       UTIL.sendMail(MB);*/
		   }
		   
		   //tx.commit();
	}catch(HibernateException e){
		success=false;
		try{
			tx.rollback();
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		
		throw e;
	}catch(RuntimeException e){
		success=false;
		try{
			tx.rollback();
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(Exception e){
		success=false;
		try{
			tx.rollback();
		}catch(Exception rbe){
			rbe.printStackTrace();
		}
		//throw e;
	}finally{
		tx=null;
	}	
	
	return success;
 }  
 
 public boolean addBranch(Branch branch,List<AddressEntriesBean> beanList,boolean isNew){  
	 Session session=null;
	 Transaction tx=null; 
	 boolean success=false;
	 
	 try{
		   session=sessionFactory.getCurrentSession();
		   //session = sessionFactory.openSession();		   
		   //tx = session.beginTransaction();
		   System.out.println("branch.getId():="+branch.getId());
		   System.out.println("isNew:="+isNew);
		   
		   if(isNew){//new branch
			     Integer serialPos=UTIL.getNextSerial(sessionFactory,branch.getCompanyId());
				 String  serialStr=serialPos.toString();
				 
				 for(int k=0; k<=3-serialStr.length(); k++){
					 serialStr="0"+serialStr;
				 }
				 
				 branch.setBranchCode(serialStr);
				 session.saveOrUpdate(branch); 
				 UTIL.incrementSerial(sessionFactory,branch.getCompanyId());
				 System.out.println(""+branch.getId());
		   }else{
			     session.saveOrUpdate(branch); 
		   }
		   
		   String branchId=null;
		   
		   if(!isNew){
			   deleteBranchAddressEntries(branch);
			   branchId=branch.getId().toString();
		   }else{
			   branchId=getBranchByName(branch.getBranchName()).getId().toString();
			   
			   //create default configs for branch
		       UTIL.createDefaults(sessionFactory,branchId,branch.getCompanyId(),2);
		       UTIL.createBranchDefaults(sessionFactory,branchId,branch.getCompanyId());
		   }
		   	   
		   //now attempt to insert new address entries
		   for(AddressEntriesBean item: beanList){
			   item.setKeyId(branchId);			   
			   session.saveOrUpdate(beanMapper.prepareAddressEntriesModel(item)); 
		   }
		   
		   if(isNew){
			   //create default user for the branch		   
/*			   User user=new User();
		       String tempPass=passUTIL.generatePswd();
		       
		       user.setCompanyId(branch.getCompanyId());
		       user.setBranchId(branchId);
		       user.setEmail(branch.getEmail());
		       user.setPassword(new PassEncoder().doEndcodePass(tempPass));
		       user.setUserId(branch.getEmail());
		       user.setUsername(branch.getEmail());
		       user.setCreatedBy("Super Admin");
		       user.setCreationDate(new java.util.Date());
		       user.setGroupId(Definitions.BRANCH_ADMIN_CODE);
		       user.setDeleted("N");
		       user.setAuthMode("DB");
		       user.setMustChangePass("Y");
		       user.setEnabled(1);
		    	
		       session.saveOrUpdate(user);*/
		       
		      
		       
		       //email password to user
		       /*MailBean MB=UTIL.getMailConfig();
		       MB.setToemail(branch.getEmail());
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
		       					" <style type=\"text/css\">" +
								"	<!--" +
								"	.style2 {" +
								"		font-family: Verdana, Arial, Helvetica, sans-serif;" +
								"		font-size: 14px;" +
								"	}" +
								"	-->" +
								"	</style>" +
								"	 <p class=\"style2\">Hello "+branch.getEmail()+", </p>" +
								"	 <p class=\"style2\">You have been setup as a branch admin on EazyCoop Solution. <br>" +
								"    <strong>Below is your logon details: </strong></p>" +
								"	 <table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
								"	  <tr>" +
								"	    <td width=\"50%\"><span class=\"style2\">Username(Email):</span></td>" +
								"	    <td width=\"50%\"><span class=\"style2\">" + branch.getEmail() + "</span></td>" +
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
								"	Thanks </p> </BODY></HTML>" ;
		       		       
		       MB.setMailBody(mailBody);		    	
		       UTIL.sendMail(MB);*/
		   }
		   
		   //tx.commit();
	}catch(HibernateException e){
		success=false;
		try{
			tx.rollback();
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		
		throw e;
	}catch(RuntimeException e){
		success=false;
		try{
			tx.rollback();
		}catch(RuntimeException rbe){
			rbe.printStackTrace();
		}
		throw e;
	}catch(Exception e){
		success=false;
		try{
			tx.rollback();
		}catch(Exception rbe){
			rbe.printStackTrace();
		}
		//throw e;
	}finally{
		tx=null;
	}	
	
	return success;
 }  

 @SuppressWarnings("unchecked")
 public List<Branch> listBranchs(){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Branch.class);
	 criteria.add(Restrictions.eq("active","Y"));
	 
	 return  (List<Branch>)criteria.list();
 }
 
 public List<Branch> listBranchsDistinct(String branchId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Branch.class);
	 criteria.add(Restrictions.eq("id",Integer.parseInt(branchId)));
	 criteria.add(Restrictions.eq("active","Y"));
	 
	 return  (List<Branch>)criteria.list();
 }
 
 public List<Branch> listInActiveBranches(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Branch.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("active","N"));
	 criteria.add(Restrictions.eq("deleted","N"));
	 
	 return  (List<Branch>)criteria.list();
 }
 
 public List<BranchBean> listInActiveBranches(){
	 return helperUTIL.getInActiveBranchList(sessionFactory);
 }

 @SuppressWarnings("unchecked")
 public List<Branch> listBranchs(String companyId){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Branch.class);
	 criteria.add(Restrictions.eq("companyId",companyId));
	 criteria.add(Restrictions.eq("active","Y"));
     return criteria.list();
 }
 
 @SuppressWarnings("unchecked")
 public List<BranchBean> listBranchBeans(String companyId){
	 return helperUTIL.getBranchList(sessionFactory,companyId);
 }
 
 @SuppressWarnings("unchecked")
 public List<Branch> listBranchs(String atribName,String atribValue){
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Branch.class);
	 criteria.add(Restrictions.eq(atribName,atribValue));
     return criteria.list();
 }

 public Branch getBranch(int id){
     return (Branch) sessionFactory.getCurrentSession().get(Branch.class, id);
 }

 public void deleteBranch(Branch branch){
	 sessionFactory.getCurrentSession().createQuery("delete from Branch where id = "+branch.getId()).executeUpdate();
 }
 
 public boolean updateActiveStatus(Branch branch){
	boolean success=false;
	
	try{
		sessionFactory.getCurrentSession().createQuery("update Branch set active='"+branch.getActive()+"' where id = "+branch.getId()).executeUpdate();
		success=true;
	}catch(HibernateException e){
		success=false;
		e.printStackTrace();
	}
	
	return success;
 }
 
 public Branch getBranchByName(String name){  
	 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Branch.class);
	 criteria.add(Restrictions.eq("branchName",name));
	 
	 return  criteria==null?null: (Branch)criteria.list().get(0);
 }  
 
 public void deleteBranchAddressEntries(Branch branch) {  
	 sessionFactory.getCurrentSession().createQuery("delete from AddressEntries where typeId=2 AND keyId = "+branch.getId()).executeUpdate();  
 }
  
 private Branch prepareModel(BranchBean branchBean){
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
	    branch.setCreatedBy(branchBean.getCreatedBy());
	    branch.setCreationDate(branchBean.getCreationDate());
	    branch.setLastModifiedBy(branchBean.getLastModifiedBy());
	    branch.setLastModificationDate(branchBean.getLastModificationDate());
	    //branch.setId(branchBean.getId());

	    branchBean.setId(null);

	    return branch;
}

private List<BranchBean> prepareListofBean(List<Branch> branchs){
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
			    bean.setCreatedBy(branch.getCreatedBy());
			    bean.setCreationDate(branch.getCreationDate());
			    bean.setLastModifiedBy(branch.getLastModifiedBy());
			    bean.setLastModificationDate(branch.getLastModificationDate());

			    beans.add(bean);
		   }
	    }

     return beans;
 }

 private BranchBean prepareBranchBean(Branch branch){
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
		  bean.setCreatedBy(branch.getCreatedBy());
		  bean.setCreationDate(branch.getCreationDate());
		  bean.setLastModifiedBy(branch.getLastModifiedBy());
		  bean.setLastModificationDate(branch.getLastModificationDate());

		  return bean;
 }
}