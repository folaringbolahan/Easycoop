/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.controller;


import com.sift.admin.bean.BranchBean;
import com.sift.admin.bean.CompanyBean;
import com.sift.admin.bean.RecordReplicationBean;
import com.sift.admin.bean.UserBean;
import com.sift.admin.dao.AddressEntriesDao;
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.model.InterestType;
import com.sift.admin.service.AddressEntriesService;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.CurrencyService;
import com.sift.admin.service.UserService;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.ProductTypeCriteria;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.financial.member.ActivityInterface;
import com.sift.financial.member.AddressEntries;
import com.sift.financial.member.AddressEntriesDAO;
import com.sift.financial.member.Member;
import com.sift.financial.member.MemberDAO;
import com.sift.financial.member.schedule.ConnectEasyCoopTask;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.utility.MailBean;
import com.sift.products.service.ProductService;
import com.sift.webservice.utility.WebServiceUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kola
 */
@Controller
public class RecordReplicationController {
BeanMapperUtility beanMapper = new BeanMapperUtility();
 @Autowired
 private CompanyService companyService;
  @Autowired
 private ProductService productServicex;
  
 @Autowired
    private TaskExecutor taskExec;
 @Autowired
    private ConnectEasyCoopTask  coopTask;
  @Autowired
   private HelperUtility helperUTIL;
  
  @Autowired
private BranchService branchService;


EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();
WebServiceUtility webServiceUtility = new WebServiceUtility();

 @Autowired
	 @Value("${apprvMemberStatusShort}")
	 private String apprvMemberStatusShort;
 
 private MemberDAO memberDAO;
 
  @Autowired
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
  
   private AddressEntriesDAO addressEntriesDAO;
 
  @Autowired
	public void setAddressEntriesDAOO(AddressEntriesDAO addressEntriesDAO) {
		this.addressEntriesDAO = addressEntriesDAO;
	}
  
  //public void setProductService(ProductService productServicex) {
  //      productService = productServicex;
  //  }

  @RequestMapping(value = "/replicateRecord", method = RequestMethod.GET)  
  public ModelAndView recordSettings(@ModelAttribute("recReplication")RecordReplicationBean recordReplicationBean, BindingResult result){
       Map<String, Object> model = new HashMap<String, Object>();
       // model.put("companies", prepareListofBranchBean(companyService.listCompanies()));
        model.put("companies",  prepareListofBranchBean(branchService.listBranch()));
        return new ModelAndView("replicate", model);
  
  }
    @RequestMapping(value = "/moveRecords", method = RequestMethod.POST)  
  public ModelAndView moveRecords(@ModelAttribute("recReplication")RecordReplicationBean recordReplicationBean, BindingResult result,HttpServletRequest req){
   //  public ModelAndView moveRecords(@ModelAttribute("recReplication")RecordReplicationBean recordReplicationBean, BindingResult result,HttpServletRequest req)  throws PersistentException {
       String redurlpath="";
        String branchid=req.getParameter("branchid");
       String coopOption=req.getParameter("movecoop");
       String productOption=req.getParameter("moveproduct");
       String memberOption=req.getParameter("movemember");
       String addrOption =req.getParameter("moveaddress");
       
        System.out.println("Selected Coop BranchId >>>>> "+branchid);
       System.out.println(" Coop checked>>>>> "+coopOption);
       System.out.println(" Product Checked >>>>> " +productOption);
       System.out.println(" Member  Checked >>>>> " +memberOption);
       System.out.println(" Address  Checked >>>>> " +addrOption);
       try
       {  
        if (coopOption==null) {
            coopOption="false";
        }
        if (productOption==null) {
            productOption="false";
        } 
        if (memberOption==null) {
            memberOption="false";
        } 
        if (addrOption==null) {
            addrOption="false";
        } 
        domovement(branchid,coopOption,productOption,memberOption,addrOption,req);
       }
       catch(PersistentException pex)
       {
           return new ModelAndView("redirect:/doError.htm?message=Cooperative/Product/Member Replication failed. Please try again later.&redirectURI=replicateRecord");
       }    
        //send mail to admin
               List<UserBean> adminMails= helperUTIL.getRegistrarAdminMails();
               for(int i = 0; i <adminMails.size(); i++){       
                    MailBean MB = new MailBean();
                    MB = eazyCoopUTIL.getMailConfig();
                    MB.setSubject("RECORD REPLICATION ON EASYCOOP");
                    MB.setMailBody("Member Records has just been moved to Easycoop from Easycoopfin");
                    MB.setToemail(adminMails.get(i).getEmail().toString());
                    System.out.println("counts "+ adminMails.get(i).getEmail().toString());
                    eazyCoopUTIL.sendMail(MB);
               }
     redurlpath = "redirect:/doFeedback.htm?message= Ongoing process,a mail would be sent to admin at the end of the process&redirectURI=replicateRecord";   
      return new ModelAndView(redurlpath);  
        //return new ModelAndView("redirect:/replicateRecord");
  
  }
private List<RecordReplicationBean> prepareListofBranchBean(List<Branch> allbranches) {
        List<RecordReplicationBean > beans = null;
        
        if ((allbranches != null )&& (! allbranches.isEmpty())) {
            beans = new ArrayList<RecordReplicationBean >();
          RecordReplicationBean  bean = null;

            for (Branch branch : allbranches ) {
            bean = new RecordReplicationBean ();
          bean.setCompanyid(Integer.parseInt(branch.getCompanyId()));
          //use branch id to get company name
          bean.setCompanyName(companyService.getCompany(Integer.parseInt(branch.getCompanyId())).getName());
          
          bean.setBranchName(branch.getBranchName());
          bean.setBranchid(branch.getId());
                 
             
                beans.add(bean);
            }
        }

        return beans;
    } 
    

private void domovement(String branchid,String coopOption,String productOption,String memberOption,String memberaddrOption,HttpServletRequest req) throws PersistentException {
       int dbranchid = 0;
       try {
           dbranchid = Integer.parseInt(branchid);
           BranchBean obj=prepareBranchBean(branchService.getBranch(dbranchid));
           if (coopOption.equalsIgnoreCase("true")) {
               boolean cooprecexists = true;
               if (callcoopexistservice(Integer.parseInt(obj.getCompanyId()),dbranchid,"coop","")==0) {
                 cooprecexists = false;  
               }
               if (cooprecexists==false) {
                 
                   
	    
	    /**************************************************************************
	    System.out.println("getCompanyId:=" + obj.getCompanyId());
	    System.out.println("getBranchId:=" + obj.getBranchCode());
	    System.out.println("getBranchName:=" + obj.getBranchName());
	    System.out.println("getConnectToEazyCoop:=" + obj.getConnectToEazyCoop());
	    System.out.println("Active:=" + obj.getActive());
		***************************************************************************/
	    
	    
		Activitylog activity=new Activitylog();
		  
		activity.setEvent(Definitions.EVENT_BRANCH_ACTIVATION);
		activity.setAction("Branch Actv(Gen 1) for Branch Code: " + obj.getBranchCode());
		activity.setActionDate(new java.util.Date());
		activity.setActionItem("Branch Name: " + obj.getBranchName());
		activity.setActionResult("Branch Actv(Gen 1) for Branch Code: " + obj.getBranchCode());
		activity.setDescription("Branch Actv(Gen 1) for Branch Code: " + obj.getBranchCode());
		activity.setIpaddress(req.getRemoteHost());
		activity.setBranchid(dbranchid);
		activity.setCompanyid(Integer.parseInt(obj.getCompanyId()));
		activity.setUsername(req.getRemoteUser());	
		activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(obj.getCompanyId()));
		activity.setToDate("");
		
		
		
			    try{eazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}
			    
                try{
                        int coopid = 0;
                        
			            try{
			               coopid = Integer.parseInt(obj.getCompanyId());
			            }catch(NumberFormatException nuex) {			                
			            }
			            
			            Company comp = companyService.getCompany(coopid);
			            if (obj.getConnectToEazyCoop().equalsIgnoreCase("Y") /*&& obj.getActive().equalsIgnoreCase("Y") */){
			                    String resource = "coop";
			
			                    webServiceUtility.webserviceClient(resource, webServiceUtility.createCoop(obj.getCompanyId(), 
			                         obj.getId().toString(), comp.getName(), obj.getBranchName(),comp.getShortName()));
			            }
                }catch(Exception ex){                	
                }
          
                   
               }
               
           }
           if (productOption.equalsIgnoreCase("true")) {
               //get all products for branch and create
               List<Products> products = null;
               
               
               try {
            int dbranch = obj.getId();
            int dcompany = Integer.parseInt(obj.getCompanyId());
            byte status = 3;
            String contribcode = "C";
            ProductsCriteria pcriteria = new ProductsCriteria();
            pcriteria.add(Restrictions.eq("companyId", dcompany));
            pcriteria.add(Restrictions.eq("branchId", dbranch));
            pcriteria.add(Restrictions.ne("isActive", status));
            pcriteria.add(Restrictions.ne("productTypeCode", contribcode));
            products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listAllProductsByCriteria(pcriteria);
            ProductTypeCriteria ptcriteria = new ProductTypeCriteria();
            ptcriteria.add(Restrictions.ne("code", "T"));
            String dproducttype = "";
            if (products!=null && products.size()>0) {
                     for (Products product: products)
                     {
                        if (product.getProductTypeCode().equalsIgnoreCase("L")) {
                            dproducttype= "productl";
                        } 
                        else if (product.getProductTypeCode().equalsIgnoreCase("S")) {
                            dproducttype= "products";
                        } 
                        else if (product.getProductTypeCode().equalsIgnoreCase("P")) {
                            dproducttype= "producto";
                        } 
                         
                        if(callprodexistservice(dcompany,dbranchid,dproducttype,Integer.toString(product.getId()))==0)
                        {
                          if (branchService.getBranch(dbranch).getConnectToEazyCoop().equalsIgnoreCase("Y")
                      && branchService.getBranch(dbranch).getActive().equalsIgnoreCase("Y")) {
                            
                          productServicex.createProdWS(product, dcompany, dbranch);
                
                
                          }
                        }
                        
                     }
                     
                 }
            
            
            
        } catch (PersistentException ex) {

            System.out.print("listProducts calling method listAllProductsByQuery(null, null)"+ ex);
        } finally {
            com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession().close();
        }
               
               
           }
           if (memberOption.equalsIgnoreCase("true")) {
               
               
               List<Object[]> memList = memberDAO.getMemberListByStatuswthemail(obj.getCompanyId(), apprvMemberStatusShort, branchid);
               List<Member> members =new ArrayList<Member>();
              // Map<String, String> validmemaddress = new HashMap<String, String>();
              // String address = "";
               int n = 0;

                            while(n < memList.size())
                            {
                                Object[] memobj  = memList.get(n);
                                //Member memRecord = (Member)memobj;
                                Member memRecord = new Member();
                                //address = "";
                                memRecord.setMemberId((Integer)memobj[0]);
                                memRecord.setFirstName((String)memobj[1]);
                                memRecord.setSurname((String)memobj[2]);
                                memRecord.setMemberNo((String)memobj[3]);
                                memRecord.setBranch((com.sift.financial.member.Branch)memobj[4]);
                                memRecord.setCompany((com.sift.financial.member.Company)memobj[5]);
                                memRecord.setCreatedDate((java.util.Date)memobj[6]);
                                memRecord.setEmailAdd1((String)memobj[7]);
                                memRecord.setPhoneNo1((String)memobj[8]);
                                //memRecord.setAddressStr((String)memobj[9]);
                                memRecord.setAddressStr("");
                                memRecord.setMiddleName((String)memobj[9]);
                                memRecord.setMemberCompId((String)memobj[10]);
                                
                                members.add(memRecord);
                                n = n+1;
                            }
                            
                            if(obj.getConnectToEazyCoop().equalsIgnoreCase("Y"))
                         {
                                            
                            coopTask.setTargetObjs(members);
                            coopTask.setTypeInd(ActivityInterface.BATCHMEMBER);
                             taskExec.execute(coopTask);
                             
                             
                          }
           }
           
           if (memberaddrOption.equalsIgnoreCase("true")) {
               
               List<Object[]> memList = memberDAO.getMemberListByStatuswthemail(obj.getCompanyId(), apprvMemberStatusShort, branchid);
               //Map<String, String> validmemaddress = new HashMap<String, String>();
               ArrayList<String[]> validmemaddress = new ArrayList<String[]>();
               String address = "";
               int n = 0;
                    if(obj.getConnectToEazyCoop().equalsIgnoreCase("Y"))
                        {
                            while(n < memList.size())
                            {
                                                            
                                Object[] memobj  = memList.get(n);
                                address = "";
                               // System.out.println("memobj[0].toString() " + memobj[0].toString());
                                List<AddressEntries> addrList = addressEntriesDAO.findByKeyId(Long.parseLong(memobj[0].toString()));
                                int j = 0;
                                //if (addrList.size()>0) {
                                     while(j < addrList.size())
                                     {
                                       //  System.out.println("j " + j);
                                        if (addrList.get(j).getAddrFieldValue().trim().isEmpty()==false) 
                                        { 
                                         address=address + (addrList.get(j).getAddrFieldValue().trim()+ " ");
                                        //  System.out.println("address " + address);
                                         
                                        } 
                                        j=j+1;
                                     }
                                 address=address.trim();
                               // }
                                
                                if((address.isEmpty()==false) && (address!=null))
                                {
                                   //validmemaddress.put((String)memobj[0], address);
                                   String[] arr ={(String)memobj[3], branchid, obj.getCompanyId(), address};
                                   validmemaddress.add(arr);
                                  //    coopTask.doEasyCoop((String)memobj[0], dbranchid, Integer.parseInt(obj.getCompanyId()), address, true);
                                 }
                                 n = n+1;
                            }
                            
                            //if mapsize > 0
                            
                            if (validmemaddress.size()>0)
                            {        
                            coopTask.setTargetObjs(validmemaddress);
                            coopTask.setTypeInd(ActivityInterface.ADRESSUPDATE);
                            taskExec.execute(coopTask);
                            }
                      }      
           }
           
           
           
           
       }
       catch(NumberFormatException nfex){
           System.out.println("Invalid branchid  "+branchid);
       }
      
       
  }


  private int callcoopexistservice(int coyid,int branchid,String rectype,String recid){
       int itexists = 0;
       String dresponse = "";
       //calll webservice
        String resource = "recexists";
        try
        {    
	dresponse =webServiceUtility.webserviceClient(resource, webServiceUtility.checkifrecexists(coyid,branchid,rectype,recid));  
         if (dresponse.equalsIgnoreCase("1")) {
            itexists = 1;
         }
        }catch(Exception ex){                	
        }
        System.out.println("check itexists " + itexists);
      return  itexists;
  }
  
  private int callprodexistservice(int coyid,int branchid,String rectype,String recid){
       int itexists = 0;
       String dresponse = "";
       //calll webservice
        String resource = "recexists";
        try
        {    
	dresponse =webServiceUtility.webserviceClient(resource, webServiceUtility.checkifrecexists(coyid,branchid,rectype,recid));  
         if (dresponse.equalsIgnoreCase("1")) {
            itexists = 1;
         }
        }catch(Exception ex){                	
        }   
      return  itexists;
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
		  
		  bean.setPostDate(branch.getPostDate());
		  bean.setCurrentYear(branch.getCurrentYear());
		  bean.setCurrentPeriod(branch.getCurrentPeriod());	
		  bean.setBaseCurrency(branch.getBaseCurrency());
		  bean.setSetupDate(branch.getSetupDate());	
		    
		  bean.setCreatedBy(branch.getCreatedBy());
		  bean.setCreationDate(branch.getCreationDate());
		  bean.setLastModifiedBy(branch.getLastModifiedBy());
		  bean.setLastModificationDate(branch.getLastModificationDate());
		  bean.setConnectToEazyCoop(branch.getConnectToEazyCoop());
		  
		  return bean;
 }
}
