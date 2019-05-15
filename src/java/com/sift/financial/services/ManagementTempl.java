package com.sift.financial.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.text.*;

import javax.annotation.Resource;

import com.sift.financial.*;
import com.sift.financial.member.*;
import com.sift.financial.member.ws.client.*;

@SuppressWarnings("unchecked")

public class ManagementTempl {
    
    private static final Log log = LogFactory.getLog(ManagementTempl.class);
    
	private MemberDAO memberDao;
	private StatusFlowDAO statusFlowDAO;
	private StatusDAO statusDAO;
	private EventDAO eventDAO;
	private AddressEntriesDAO addEntriesDAO;
	private BranchDAO branchDAO;
	private CompanyDAO companyDAO;
	private IdentificationDocDAO identificationDocDAO;
	private MemberTypeDAO memberTypeDAO;
	private AddressTypeDAO addressTypeDAO;
	private ReligionDAO religionDAO;
	private CountryAddressFilterDAO countryAddressFilterDAO;
	private CompStockTypeDAO compStockTypeDAO;
	private CompStockTypeDetailDAO compStockTypeDetailDAO;
	private CompStockPropertyDAO  compStockPropertyDAO;
	private TaxGroupsDAO  taxGroupsDAO;
	private BanksDAO  banksDAO;
	private CompanyMemberIdentifierDAO  companyMemberIdentifierDAO;
	private GenericConfigDAO genericConfigDAO;
	private MemberHoldingsMovementDAO memberHoldingsMovementDAO;
	private DividendDAO  dividendDAO;
	private DividendTypeDAO  dividendTypeDAO;
	private DividendScheduleDAO  dividendScheduleDAO;
        private MemberHoldingsDAO memberHoldingsDAO;
        private MemberArchiveDAO  memberArchiveDAO;
        private MemberContributionDAO  memberContributionDAO;
	private BatchUploadTypeDAO batchUploadTypeDAO;
        private BatchMemberDAO batchMemberDAO;
        private BatchUploadFileDAO batchUploadFileDAO;
        private BatchUploadReferenceDAO batchUploadReferenceDAO;
        
        private MemberContributionArchiveDAO  memberContributionArchiveDAO;
        
        private MemberExtrafldDAO memberExtrafldDAO;
        private MemberExtrafldoptionDAO memberExtrafldoptionDAO;
        private MembersExtrafldEntriesDAO membersExtrafldEntriesDAO;
                
        private BatchStockDAO batchStockDAO;
        
        private BatchAddressEntriesDAO batchAddressEntriesDAO;
        private BatchExtrafldEntriesDAO batchExtrafldEntriesDAO;
        
	private ActivityLogDAO activityLogDAO;
	

	private GenericServiceImpl genericServiceImpl;
        
        private MailNotificationImpl mailNotificationImpl;
	
	@Resource (name="defaultProducts")
	private Map<String, String> defaultProducts;
	
	@Autowired
	@Value("${defProdSubCode}")
	private String defProdSubCode;
	
	@Autowired
	@Value("${isoDateFormat}")
	private String isoDateFormat;
	
	@Autowired
	@Value("${normDateFormat}")
	private String normDateFormat;
	
	@Resource (name="serviceTypes")
	private Map<String,  Map<String, String>> serviceTypes;

	private ApplicationContext ctx; 
	
	

	public MemberDAO getMemberDao() {
		return memberDao;
	}
	@Autowired
	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	@Autowired
	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public AddressEntriesDAO getAddEntriesDAO() {
		return addEntriesDAO;
	}

	@Autowired
	public void setAddEntriesDAO(AddressEntriesDAO addEntriesDAO) {
		this.addEntriesDAO = addEntriesDAO;
	}

	public ApplicationContext getCtx() {
		return ctx;
	}

	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	public BranchDAO getBranchDAO() {
		return branchDAO;
	}

	@Autowired
	public void setBranchDAO(BranchDAO branchDAO) {
		this.branchDAO = branchDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	@Autowired
	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	
	public List<Company> getAllCompanies()
	{
		return (List<Company>)companyDAO.findAll();
	}
	
	//get Company
	public Company getCompany(String companyId)
	{
		return  companyDAO.findById(Integer.valueOf(companyId));
	}
	
	
	public List getCompanyList(String companyId)
	{
		List compList = new ArrayList();
		
		Company comp = companyDAO.findById(Integer.valueOf(companyId));
		
		System.out.println("retrieved comp: " + comp.getId());

		compList.add(comp);
		
		return  compList;
	}
	
	
	
	public List<MemberType> getAllMemberTypes()
	{
		return (List<MemberType>)memberTypeDAO.findAll();
	}
	
	
	public List<Object[]> getAllIdentificationDocByCountryId(String country)
	{
		return identificationDocDAO.getIdentificationDocByCountryId(country);
	}
	
	
	public List getAllIdentificationDocByCompanyRef(String company)
	{
		log.info("company :"  +  company);
				
		Company  comp = getCompany(company);
		
		log.info("Retrieved Id: " + comp.getId());
		log.info("Retrieved country Id: " + comp.getCountries().getId());
		
		return identificationDocDAO.getIdentificationDocByCountryId(comp.getCountries().getId().toString());
	}
	
	
	/**
	 * @param serviceObject Object Type Streamable to JSON or XML required
	 * @param servType  Indication of the service type e.g. ACCOUNT OPENING,TRANSACTION POSTING, PRODUCT CREATION etc
	 * @return Object 
	 * @throws Throwable 
	 */
	@SuppressWarnings("unused")
	public Object doRestService(Object serviceObject, String servType) throws Throwable
	{
		
		//create default contribution account account
		//get default Accounts to create
		
		Object returnVal = null;
		
		String serviceStr = null;
                
                String servStr = null;
			
		//get ServiceTypes
		Map<String, String> theServInfo = getServiceTypes().get(servType);
		//System.out.println("theServInfo :: " + theServInfo);
		if(theServInfo!=null)
		{
			if(theServInfo.get("OBJ-TYPE").endsWith("xml"))
			{
				serviceStr = genericServiceImpl.convertObjectToXML(serviceObject, theServInfo.get("OBJ"));
			}
			else if(theServInfo.get("OBJ-TYPE").endsWith("json"))
			{
				serviceStr = genericServiceImpl.convertObjectToJson(serviceObject);
			}
			else
			{
				throw new Exception("Unknown Service");
			}
                        
                        
                         servStr = genericServiceImpl.consumeServ(serviceStr, theServInfo.get("OBJ-URI"), theServInfo.get("OBJ-TYPE"));
                        
		}
		else
		{
			
			throw new Exception("Undefined Service type");
		}
		
		
                if(servStr !=null)
                {
                      try
                      {
                          if(theServInfo.get("OBJ-TYPE").endsWith("xml"))
			{
				returnVal = genericServiceImpl.convertXMLToObject(servStr, theServInfo.get("OBJ"));
			}
			else if(theServInfo.get("OBJ-TYPE").endsWith("json"))
			{
				returnVal = genericServiceImpl.convertJsonToObject(servStr, theServInfo.get("OBJ"));
			}
                      }
                      catch(Exception ex)
                      {
                        return servStr; 
                      }
		//String servStr = genericServiceImpl.consumeServ(serviceStr, theServInfo.get("OBJ-URI"), theServInfo.get("OBJ-TYPE"));
                }
                return returnVal;
	}
	

	public AddressType getAddressTypeByName (String name)
	{
		
		return (AddressType)(getAddressTypeDAO().findByProperty("typeName", name).get(0));
	}
	
	public IdentificationDocDAO getIdentificationDocDAO() {
		return identificationDocDAO;
	}
	@Autowired
	public void setIdentificationDocDAO(IdentificationDocDAO identificationDocDAO) {
		this.identificationDocDAO = identificationDocDAO;
	}

	public MemberTypeDAO getMemberTypeDAO() {
		return memberTypeDAO;
	}
	@Autowired
	public void setMemberTypeDAO(MemberTypeDAO memberTypeDAO) {
		this.memberTypeDAO = memberTypeDAO;
	}
	public StatusFlowDAO getStatusFlowDAO() {
		return statusFlowDAO;
	}
	@Autowired
	public void setStatusFlowDAO(StatusFlowDAO statusFlowDAO) {
		this.statusFlowDAO = statusFlowDAO;
	}
	public StatusDAO getStatusDAO() {
		return statusDAO;
	}
	@Autowired
	public void setStatusDAO(StatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}
	public AddressTypeDAO getAddressTypeDAO() {
		return addressTypeDAO;
	}
	@Autowired
	public void setAddressTypeDAO(AddressTypeDAO addressTypeDAO) {
		this.addressTypeDAO = addressTypeDAO;
	}
	public ReligionDAO getReligionDAO() {
		return religionDAO;
	}
	@Autowired
	public void setReligionDAO(ReligionDAO religionDAO) {
		this.religionDAO = religionDAO;
	}
	public CountryAddressFilterDAO getCountryAddressFilterDAO() {
		return countryAddressFilterDAO;
	}
	
	@Autowired
	public void setCountryAddressFilterDAO(
			CountryAddressFilterDAO countryAddressFilterDAO) {
		this.countryAddressFilterDAO = countryAddressFilterDAO;
	}
        
        public MemberExtrafldDAO getMemberExtrafldDAO() {
		return memberExtrafldDAO;
	}
	
	@Autowired
	public void setMemberExtrafldDAO(
			MemberExtrafldDAO memberExtrafldDAO) {
		this.memberExtrafldDAO = memberExtrafldDAO;
	}
        public MemberExtrafldoptionDAO getMemberExtrafldoptionDAO() {
		return memberExtrafldoptionDAO;
	}
	
	@Autowired
	public void setMemberExtrafldoptionDAO(
			MemberExtrafldoptionDAO memberExtrafldoptionDAO) {
		this.memberExtrafldoptionDAO = memberExtrafldoptionDAO;
	}
        
        public MembersExtrafldEntriesDAO getMembersExtrafldEntriesDAO() {
		return membersExtrafldEntriesDAO;
	}

	@Autowired
	public void setMembersExtrafldEntriesDAO(MembersExtrafldEntriesDAO membersExtrafldEntriesDAO) {
		this.membersExtrafldEntriesDAO = membersExtrafldEntriesDAO;
	}
        
	public CompStockTypeDAO getCompStockTypeDAO() {
		return compStockTypeDAO;
	}
	@Autowired
	public void setCompStockTypeDAO(CompStockTypeDAO compStockTypeDAO) {
		this.compStockTypeDAO = compStockTypeDAO;
	}
	public CompStockTypeDetailDAO getCompStockTypeDetailDAO() {
		return compStockTypeDetailDAO;
	}
	@Autowired
	public void setCompStockTypeDetailDAO(
			CompStockTypeDetailDAO compStockTypeDetailDAO) {
		this.compStockTypeDetailDAO = compStockTypeDetailDAO;
	}
	public CompStockPropertyDAO getCompStockPropertyDAO() {
		return compStockPropertyDAO;
	}
	@Autowired
	public void setCompStockPropertyDAO(CompStockPropertyDAO compStockPropertyDAO) {
		this.compStockPropertyDAO = compStockPropertyDAO;
	}
	public TaxGroupsDAO getTaxGroupsDAO() {
		return taxGroupsDAO;
	}
	@Autowired
	public void setTaxGroupsDAO(TaxGroupsDAO taxGroupsDAO) {
		this.taxGroupsDAO = taxGroupsDAO;
	}
	public BanksDAO getBanksDAO() {
		return banksDAO;
	}
	@Autowired
	public void setBanksDAO(BanksDAO banksDAO) {
		this.banksDAO = banksDAO;
	}
	public CompanyMemberIdentifierDAO getCompanyMemberIdentifierDAO() {
		return companyMemberIdentifierDAO;
	}
	@Autowired
	public void setCompanyMemberIdentifierDAO(
			CompanyMemberIdentifierDAO companyMemberIdentifierDAO) {
		this.companyMemberIdentifierDAO = companyMemberIdentifierDAO;
	}
	public GenericServiceImpl getGenericServiceImpl() {
		return genericServiceImpl;
	}
	@Autowired
	public void setGenericServiceImpl(GenericServiceImpl genericServiceImpl) {
		this.genericServiceImpl = genericServiceImpl;
	}
	public Map<String, String> getDefaultProducts() {
		return defaultProducts;
	}
	public void setDefaultProducts(Map<String, String> defaultProducts) {
		this.defaultProducts = defaultProducts;
	}
	
	public Map<String, Map<String, String>> getServiceTypes() {
		return serviceTypes;
	}
	public void setServiceTypes(Map<String, Map<String, String>> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}
	public String getDefProdSubCode() {
		return defProdSubCode;
	}
	public void setDefProdSubCode(String defProdSubCode) {
		this.defProdSubCode = defProdSubCode;
	}
	public GenericConfigDAO getGenericConfigDAO() {
		return genericConfigDAO;
	}
	@Autowired
	public void setGenericConfigDAO(GenericConfigDAO genericConfigDAO) {
		this.genericConfigDAO = genericConfigDAO;
	}
	
	
	public Date getCountryDate(Date theDate, String countryId, String format) throws ParseException
	{
		
		Map<String, String> countryMap = getGenericConfigDAO().getCountryInfo(countryId);
	
		SimpleDateFormat theDateFormat = new SimpleDateFormat(getIsoDateFormat());
		
		String dateStr = theDateFormat.format(theDate);
		
		theDateFormat.setTimeZone(TimeZone.getTimeZone(countryMap.get("time_zone")));
		
		return theDateFormat.parse(dateStr);
	}
	
	
	public String getIsoDateFormat() {
		return isoDateFormat;
	}
	public void setIsoDateFormat(String isoDateFormat) {
		this.isoDateFormat = isoDateFormat;
	}
	public String getNormDateFormat() {
		return normDateFormat;
	}
	public void setNormDateFormat(String normDateFormat) {
		this.normDateFormat = normDateFormat;
	}
	public MemberHoldingsMovementDAO getMemberHoldingsMovementDAO() {
		return memberHoldingsMovementDAO;
	}
	@Autowired
	public void setMemberHoldingsMovementDAO(
			MemberHoldingsMovementDAO memberHoldingsMovementDAO) {
		this.memberHoldingsMovementDAO = memberHoldingsMovementDAO;
	}
	public DividendDAO getDividendDAO() {
		return dividendDAO;
	}
	@Autowired
	public void setDividendDAO(DividendDAO dividendDAO) {
		this.dividendDAO = dividendDAO;
	}
	public DividendTypeDAO getDividendTypeDAO() {
		return dividendTypeDAO;
	}
	@Autowired
	public void setDividendTypeDAO(DividendTypeDAO dividendTypeDAO) {
		this.dividendTypeDAO = dividendTypeDAO;
	}
	public DividendScheduleDAO getDividendScheduleDAO() {
		return dividendScheduleDAO;
	}
	@Autowired
	public void setDividendScheduleDAO(DividendScheduleDAO dividendScheduleDAO) {
		this.dividendScheduleDAO = dividendScheduleDAO;
	}
	
	public ActivityLogDAO getActivityLogDAO() {
		return activityLogDAO;
	}
        @Autowired
	public void setActivityLogDAO(ActivityLogDAO activityLogDAO) {
		this.activityLogDAO = activityLogDAO;
	}
        
        
        
        public MemberArchive getArchiveObject(Member obj)
        {
            MemberArchive returnVal = new MemberArchive();
           
            returnVal.setBankId(obj.getBanks().getBankId());
            returnVal.setApprovedBy(obj.getApprovedBy()==null?null:obj.getApprovedBy());
            returnVal.setApprovedDate(obj.getApprovedDate()==null?null:obj.getApprovedDate());
            returnVal.setBankAccount(obj.getBankAccount()==null?null:obj.getBankAccount());
            returnVal.setBranchId(obj.getBranch().getId());
            returnVal.setCompanyId(obj.getCompany().getId());
            returnVal.setCreatedBy(obj.getCreatedBy());
            returnVal.setCreatedDate(obj.getCreatedDate());
            returnVal.setDob(obj.getDob());
            returnVal.setDelFlg(obj.getDelFlg());
            returnVal.setDelDate(obj.getDelDate()==null?null:obj.getDelDate());
            returnVal.setEmailAdd1(obj.getEmailAdd1());
            returnVal.setEmailAdd2(obj.getEmailAdd2()==null?null:obj.getEmailAdd2());
            returnVal.setEmailAdd3(obj.getEmailAdd3()==null?null:obj.getEmailAdd3());
            returnVal.setFirstName(obj.getFirstName());
            returnVal.setGender(obj.getGender());
            returnVal.setIdentificationCode(obj.getIdentificationCode());
            returnVal.setIdentificationId(obj.getIdentificationDoc().getIdentificationDocId());
            returnVal.setMemberId(obj.getMemberId());
            returnVal.setMemberCompId(obj.getMemberCompId());
            returnVal.setMemberNo(obj.getMemberNo());
            returnVal.setMemberTypeId(obj.getMemberType().getMemberTypeId());
            returnVal.setMiddleName(obj.getMiddleName()==null?null:obj.getMiddleName());
            returnVal.setModifiedBy(obj.getModifiedBy()==null?null:obj.getModifiedBy());
            returnVal.setModifiedDate(obj.getModifiedDate()==null?null:obj.getModifiedDate());
            returnVal.setPhoneNo1(obj.getPhoneNo1());
            returnVal.setPhoneNo2(obj.getPhoneNo2()==null?null:obj.getPhoneNo2());
            returnVal.setPhoneNo3(obj.getPhoneNo3()==null?null:obj.getPhoneNo3());
            returnVal.setReligionId(obj.getReligion().getReligionId());
            //returnVal.setResignDate(obj.R);
            returnVal.setStatusId(obj.getStatus().getStatusId());
            returnVal.setSurname(obj.getSurname());
            try
            {    
            returnVal.setTaxGroup(obj.getTaxGroups().getId()==null?null:obj.getTaxGroups().getId());
            }
            catch(NullPointerException npex) {
             returnVal.setTaxGroup(null);   
            }
            returnVal.setNokName(obj.getNokName());
            returnVal.setNokSurname(obj.getNokSurname());
            returnVal.setNokMiddleName(obj.getNokMiddleName());
            returnVal.setNokPhone(obj.getNokPhone());
         return returnVal;
        }

    public MemberHoldingsDAO getMemberHoldingsDAO() {
        return memberHoldingsDAO;
    }

    @Autowired
    public void setMemberHoldingsDAO(MemberHoldingsDAO memberHoldingsDAO) {
        this.memberHoldingsDAO = memberHoldingsDAO;
    }

    public MemberArchiveDAO getMemberArchiveDAO() {
        return memberArchiveDAO;
    }

    @Autowired
    public void setMemberArchiveDAO(MemberArchiveDAO memberArchiveDAO) {
        this.memberArchiveDAO = memberArchiveDAO;
    }

    public MemberContributionDAO getMemberContributionDAO() {
        return memberContributionDAO;
    }
    
    @Autowired
    public void setMemberContributionDAO(MemberContributionDAO memberContributionDAO) {
        this.memberContributionDAO = memberContributionDAO;
    }

    public BatchUploadTypeDAO getBatchUploadTypeDAO() {
        return batchUploadTypeDAO;
    }

    @Autowired
    public void setBatchUploadTypeDAO(BatchUploadTypeDAO batchUploadTypeDAO) {
        this.batchUploadTypeDAO = batchUploadTypeDAO;
    }

    public BatchMemberDAO getBatchMemberDAO() {
        return batchMemberDAO;
    }
    
    @Autowired
    public void setBatchMemberDAO(BatchMemberDAO batchMemberDAO) {
        this.batchMemberDAO = batchMemberDAO;
    }

    public BatchUploadFileDAO getBatchUploadFileDAO() {
        return batchUploadFileDAO;
    }

    @Autowired
    public void setBatchUploadFileDAO(BatchUploadFileDAO batchUploadFileDAO) {
        this.batchUploadFileDAO = batchUploadFileDAO;
    }

    public BatchUploadReferenceDAO getBatchUploadReferenceDAO() {
        return batchUploadReferenceDAO;
    }

    @Autowired
    public void setBatchUploadReferenceDAO(BatchUploadReferenceDAO batchUploadReferenceDAO) {
        this.batchUploadReferenceDAO = batchUploadReferenceDAO;
    }
   
    
    public boolean doMemberProducts (Member memRecord, List<Map<String,Object>>  theKeysProd) throws Exception
    {
        
        log.info("Here inside WriteObjects....................creating account for member....." + memRecord.getMemberNo());
        boolean  returnVal = false;
         Map<String, String> counMap = getGenericConfigDAO().getCountryInfoByCompany(memRecord.getCompany().getId().toString());
                        
                        //create deault product account if 
                        for(Object key : theKeysProd)
                            {
                                    Map<String,Object> keyStr = (Map<String,Object>)key;
                                    Object serVStr = null;
                                    Accnowbs theAcct = new Accnowbs();

                                    theAcct.setBranchcode(memRecord.getBranch().getBranchCode());
                                    theAcct.setBranchId(memRecord.getBranch().getId().toString());
                                    theAcct.setCompany(memRecord.getCompany().getId().toString());
                                    theAcct.setCompanycode(memRecord.getCompany().getCode());
                                    theAcct.setCustomerno(memRecord.getMemberNo());
                                    //theAcct.setProductcode(defAccounts.get(keyStr));
                                    theAcct.setProductcode((String)keyStr.get("code"));								
                                    theAcct.setSubno(getDefProdSubCode());
                                    theAcct.setTimezone(counMap.get("time_zone"));

                                    try
                                    {

                                    serVStr = doRestService(theAcct, ActivityInterface.SERVTYPEACCOUNT);

                                        if(serVStr instanceof Accnowbs)
                                        {
                                          theAcct = (Accnowbs)serVStr;
                                          //theAcct.get
                                        }
                                        else if(serVStr instanceof String)
                                        {

                                        log.info("serVStr :" + serVStr);

                                        }
                                    } catch (Throwable e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                            log.info("Account creation failed for Product Code :" + (String)keyStr.get("code") + " with name: " + (String)keyStr.get("name") + " for member " + memRecord.getMemberNo() + " company " + memRecord.getCompany().getCode());
                                            
                                            throw new Exception("Account creation failed for Product Code :" + (String)keyStr.get("code") + " with name: " + (String)keyStr.get("name") + " for member " + memRecord.getMemberNo() + " company " + memRecord.getCompany().getCode());
                                    }
                            }
                       
      return returnVal;
    }
    
    
    public Map<String, String> canApproveRequest(String initiator, String modifier, String approver) {
        
        String returnVal = "false";
        String returnMsg = "Oops! You cannnot perform this operation";
        
        Map<String, String> returnObj = new HashMap<String, String>();
        
        if(initiator !=null && approver!=null)
        {
            if(initiator.toLowerCase().equals(approver.toLowerCase()))
            {
                 returnVal = "false";
                 
            }
            else if (modifier !=null && modifier.toLowerCase().equals(approver.toLowerCase()))
            {
                 returnVal = "false";
            }
            else
            {
                 returnVal = "true";
                 returnMsg = "Great! You have access to this operation";
            }
        }
        else
        {
        returnVal = "false";
        }
        
        returnObj.put("STATE", returnVal);
        returnObj.put("MSG", returnMsg);
       
      return returnObj;
    }

    public BatchStockDAO getBatchStockDAO() {
        return batchStockDAO;
    }

    @Autowired
    public void setBatchStockDAO(BatchStockDAO batchStockDAO) {
        this.batchStockDAO = batchStockDAO;
    }

    public BatchAddressEntriesDAO getBatchAddressEntriesDAO() {
        return batchAddressEntriesDAO;
    }

    @Autowired
    public void setBatchAddressEntriesDAO(BatchAddressEntriesDAO batchAddressEntriesDAO) {
        this.batchAddressEntriesDAO = batchAddressEntriesDAO;
    }

    public BatchExtrafldEntriesDAO getBatchExtrafldEntriesDAO() {
        return batchExtrafldEntriesDAO;
    }

    @Autowired
    public void setBatchExtrafldEntriesDAO(BatchExtrafldEntriesDAO batchExtrafldEntriesDAO) {
        this.batchExtrafldEntriesDAO = batchExtrafldEntriesDAO;
    }
    public MemberContributionArchiveDAO getMemberContributionArchiveDAO() {
        return memberContributionArchiveDAO;
    }
    
    @Autowired
    public void setMemberContributionArchiveDAO(MemberContributionArchiveDAO memberContributionArchiveDAO) {
        this.memberContributionArchiveDAO = memberContributionArchiveDAO;
    }

    
    
    public List<String> getAllValidUserEmail (int branchid, int companyid)
    {
        String mySqlAll = "select a.username as username, a.email as email from users a inner join usergrpmdl b on a.branch = b.branchid and a.companyid = b.companyid and a.accesslevel = b.usergroup where b.branchid = " + branchid + " and b.companyid = " + companyid + " and a.enabled = 1";
        
        List<Map<String,Object>> listEmails = null;
        
        List<String> returnVal = new ArrayList<String> ();
        
        listEmails = getGenericConfigDAO().getQueryRead(mySqlAll);
        
        if(listEmails!= null && listEmails.size()>0)
        {
            for(Map<String,Object> obj : listEmails)
            {
                returnVal.add((String)obj.get("email"));
            }
        }
        return returnVal;
    }
    
    
    public List<String> getNextValidUserEmail (int branchid, int companyid, String role)
    {
    
        String mySqlNextLevel = "select a.username as username, a.email as email from users a inner join usergrpmdl b on a.branch = b.branchid and a.companyid = b.companyid and a.accesslevel = b.usergroup where b.menu = '" + role + "' and b.branchid = " + branchid + " and b.companyid = " + companyid + " and a.enabled = 1"; 
        
        List<Map<String,Object>> listEmails = null;
        
        List<String> returnVal = new ArrayList<String> ();
        
        listEmails = getGenericConfigDAO().getQueryRead(mySqlNextLevel);
        
        if(listEmails!= null && listEmails.size()>0)
        {
            for(Map<String,Object> obj : listEmails)
            {
                returnVal.add((String)obj.get("email"));
            }
        }
        return returnVal;
 
    }
    
    
    
}
