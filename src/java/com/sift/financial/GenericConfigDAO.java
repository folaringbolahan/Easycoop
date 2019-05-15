package com.sift.financial;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class GenericConfigDAO {
	
	private static final Log log = LogFactory.getLog(GenericConfigDAO.class);
	
	private BaseDao baseDao;
        
       private Map<String,String> opsQueryMap;
	
	public GenericConfigDAO(BaseDao baseDao){
		this.baseDao=baseDao;
	}

	public Map<String,String> getListingConfig(String reqStatus){
		
		String query=" select STATUS_SHORT as statusShort, PAGE_TITLE as pageTitle, "+
		             " REQUEST_VIEW as requestView, NEXT_URL as nextURL, LINK_LABEL as linkLabel,"+
		             " EXCLUDE_USER as excludeUser, EXCLUDE_SOL as excludeSol, "+
		             " REQUIRED_DAO as requiredDAO, "+
		             " SHOW_EDIT as showEdit, EDIT_LABEL as editLabel, EDIT_URL as editUrl, EDIT_EVENT as editEvent  " +
		             " from GENERIC_LISTING_CFG "+
		             " where STATUS_SHORT='"+reqStatus+"'";
		
		return baseDao.queryForMap(query);
	}
	
 
   /**
 * @param actn
 * @return
 */
public Map<String,String> getQualifiedListingConfig(String actn){
		
		String query=" select CONF_ENTRY, CONF_VALUE  from QUALIFIED_LISTING_CFG where STATUS_SHORT='"+actn+"' and DEL_FLG='N'";
		
		List<Map<String,Object>> theConfig = baseDao.getResultMap(query);
		
		Map<String,String> returnMap = new HashMap<String,String>();
		
		if(theConfig !=null && theConfig.size() > 0)
		{
			for (Map<String,Object> theMap : theConfig)
			{
				returnMap.put((String)theMap.get("CONF_ENTRY"), (String)theMap.get("CONF_VALUE"));
			}
		}
		return returnMap;
	}
   
   
   /**
 * @param compId
 * @param StockId
 * @return
 */
public Map<String,String> getStockPropertyListingConfig(String compId, String StockId){
		
		String query=" select b.stock_ppty_name as CONF_ENTRY, a.comp_stock_ppty_val, as CONF_VALUE  "
				+ " from comp_stock_type_detail a, comp_stock_property b, comp_stock_type c "
				+ " where a.comp_stock_type_id= c.comp_stock_type_id and a.stock_ppty_id = b.stock_ppty_id "
				+ "	and c.comp_stock_type_id=" + StockId + " and c.company_id=" + compId + " and b.DEL_FLG='N' "
				+ " order by b.stock_ppty_id";
		
	/*	select b.stock_ppty_name, a.comp_stock_ppty_val from 
		comp_stock_type_detail a, comp_stock_property b, comp_stock_type c

		where a.comp_stock_type_id= c.comp_stock_type_id
		and
		a.stock_ppty_id = b.stock_ppty_id
		and c.comp_stock_type_id=12
		and c.company_id=4
		order by b.stock_ppty_id
	*/
		
		List<Map<String,Object>> theConfig = baseDao.getResultMap(query);
		
		Map<String,String> returnMap = new HashMap<String,String>();
		
		if(theConfig !=null && theConfig.size() > 0)
		{
			for (Map<String,Object> theMap : theConfig)
			{
				returnMap.put((String)theMap.get("CONF_ENTRY"), (String)theMap.get("CONF_VALUE"));
			}
		}
		return returnMap;
	}
   
   
   /**
 * @param companyId
 * @param branchId
 * @param memCode
 * @param prodCode
 * @return
 */
@SuppressWarnings("unchecked")
  public Map<String,String> getMemberAccount(String companyId, String branchId, String memCode, String prodCode)
   {	
	   String query=" select a.accountNo as accountNo, a.name as accountName, "+
		             " a.controlAccountno as controlAccountNo  " +
		             " from accounts a, custaccountdetails b where a.accountno=b.accountno "
		             + " and a.active='1' and a.closed='0' and blocked='0' "
		             + "  and a.companyId ="+ companyId + " and and a.branchId=" + branchId + " and "
		             + "  a.asegc='" + memCode + "' and b.product='" + prodCode + "'";
		 
	   log.info("query  = " + query);
	   
	   return baseDao.queryForMap(query); 
	}
   
   
   /**
 * @param productCode
 * @param branchId
 * @param companyId
 * @return
 */
public List<Map<String,Object>> getContraAccountsByProduct(String productCode, String branchId, String companyId){
	  
		String query=" select A.CODE as productCode, A.PRODUCT_TYPE_CODE as productTypeCode, " +
		             " C.CODE as acctTypeCode , C.DESCRIPTION as acctTypeDesc, D.GL_ACCOUNT_NUMBER as glAccountNumber,"+
		             " B.NAME AS productTypeName, B.CODE as productType   " +
		             " from PRODUCTS A, PRODUCTTYPE B, PRODUCTACCOUNTTYPE C , PRODUCTACCOUNT D "+
		             " where A.PRODUCT_TYPE_CODE=B.PRODUCT_TYPE_CODE AND "
		             + " C.ID=D.PRODCUT_ACCOUNT_TYPE_ID AND "
		             + " A.ID = D.PRODUCT_ID AND " 
		             + " A.COMPANY_ID = D.COMPANY_ID AND " 
		             + " A.BRANCH_ID = D.BRANCH_ID AND " 
		             + " A.CODE ='"+productCode.toUpperCase() +"' AND "
		             + " A.COMPANY_ID =" + companyId + " AND "
		             + " A.BRANCH_ID =" + branchId ;
		
		log.info("query  = " + query);
		
		return baseDao.getResultMapForNull(query);
	}
   
   
 /**
 * @param companyId
 * @param branchId
 * @param zoneAwareCurDate
 * @return
 */
@SuppressWarnings("unchecked")
public Map<String,String> getFiscalYearDetails(String companyId, String branchId, Date zoneAwareCurDate)
   {	
	  // select * from fiscal_period_items where FP_START<= '2015-08-27 00:00:00' and FP_END >= '2015-08-27 00:00:00'
	   
	   java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   
	   String currentTime = sdf.format(zoneAwareCurDate);
	   
	   String query=" select a.year as curYear, a.no_of_period as totalPeriod, "+
		             " b.period_id as curPeriod, b.fp_start as periodStart, b.fp_end as periodEnd  " +
		             " from fiscal_period a, fiscal_period_items b where a.year=b.year "
		             + " and a.active='Y'  "
		             + "  and a.company_Id ="+ companyId + " and and a.branch_Id=" + branchId + " and "
		             + " b.FP_START<='" + currentTime + "' and FP_END >= '" + currentTime + "'";
		 
	   log.info("query  = " + query);
	   
	   return baseDao.queryForMap(query); 
}


/**
 * @param type
 * @return
 */
	public List<Map<String,Object>> getFormulaOperand(String type)
	{
		   String query=" select operand_val as operand_val, operand_display as operand_display, "+
			             " operand_class as operand_class, operand_obj as operand_obj, operand_obj_fld as operand_obj_fld  " +
			             " from comp_operand "
			             + "  where del_flg ='N' and operand_class='" + type + "'";
			           
		   log.info("query  = " + query);
		   
			return baseDao.getResultMapForNull(query);
}

	/**
	 * @return
	 */
	public List<Map<String,Object>> getFormulaOperator()
	{
		
		String query=" select op_display_name as op_display_name, op_val as op_val "+
	    	        " from comp_operator "
	        + "  where del_flg ='N' ";
	      
	log.info("query  = " + query);
	
	return baseDao.getResultMapForNull(query);
	}
	

	
	@SuppressWarnings("unchecked")
	public Map<String,String> getCountryInfo(String countryId)
	{
		
		String query=" select ID as id, COUNTRY_CODE as country_code, COUNTRY_NAME as country_name, TIMEZ as time_zone "+
	    	        " from countries "
	        + "  where id =" + countryId;
	      
	log.info("query  = " + query);
	
	   return baseDao.queryForMap(query); 
	}
	
	
	
	public List<Map<String,Object>> getBranchTradingProducts(String companyId, String branchId, String prodType)
	   {	
		
		  String query=" select code as code, name as name , segment_code as segement_code "+
			              " from products  where product_type_code='" + prodType +"'"
			             + " and  is_deleted='0' "
			             + "  and company_id ="+ companyId + " and branch_id=" + branchId ;
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getResultMapForNull(query); 
		}
        
        
        public List<Map<String,Object>> getProductsByStockNameOrProdCode(String companyId, String branchId, String prodCode)
	   {	
		
		  String query=" select code as code, name as name , segment_code as segement_code "+
			              " from products  where code='" + prodCode +"'"
			             + " and  is_deleted='0' "
			             + "  and company_id ="+ companyId + " and branch_id=" + branchId ;
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getResultMapForNull(query); 
		}
        
        
        public List<Map<String,Object>> getControlAccounts(String companyId, String branchId)
	   {	
		
		  String query=" select AccountNo as accountno, name as name "+
			              " from accounts  where "
			             + "  controlaccount=1 and active =1 and closed= 0 and blocked=0 "
			             + "  and companyId ="+ companyId + " and branch=" + branchId ;
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getResultMapForNull(query); 
		}
        
         public List<Map<String,Object>> getGlAccounts(String companyId, String branchId)
	   {	
		/*
		  String query=" select AccountNo as accountno, name as name "+
			              " from accounts  where "
			             + "  controlaccount=1 and active =1 and closed= 0 and blocked=0 "
                                     + "  and accounttype = 'G' "
			             + "  and companyId ="+ companyId + " and branch=" + branchId ;
		*/
               // new query to load no control acct
               String query=" select AccountNo as accountno, name as name "+
			              " from accounts  where "
			             + "  controlaccount=0 and active =1 and closed= 0 and blocked=0 "
                                     + "  and accounttype = 'G' "
			             + "  and companyId ="+ companyId + " and branch=" + branchId ;
		   log.info("query  = " + query);
		   
		   return baseDao.getResultMapForNull(query); 
	}
        
        public List<Map<String,Object>> getMemberAccounts(String companyId, String branchId, String memCode)
	   {	
		
		  String query=" select a.AccountNo as accountno, a.name as name, b.title,  "+
			              " from accounts a, custaccountdetails  where "
			             + " a.AccountNo=b.accountNo and a.active =1 and a.closed= 0 and a.blocked=0 "
			             + "  and a.companyId ="+ companyId + " and a.branch=" + branchId + " and a.Aseg2='" +memCode+ "'";
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getResultMapForNull(query); 
		}
        
        public List<Map<String,Object>> getMemberAccountByProduct(String companyId, String branchId, String memCode,String prodCode)
	   {	
		
		  String query=" select a.AccountNo as accountno, a.name as name, b.title,  "+
			              " from accounts a, custaccountdetails  where "
			             + " a.AccountNo=b.accountNo and a.active =1 and a.closed= 0 and a.blocked=0 "
			             + "  and a.companyId ="+ companyId + " and a.branch=" + branchId + " and b.product='" +prodCode+ "' and a.Aseg2='" +memCode+ "'";
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getResultMapForNull(query); 
		}
        
        public List<Map<String,Object>> getCashAccount(String companyId, String branchId)
	   {	
		
		  String query=" select AccountNo as accountno, name as name "+
			              " from accounts  where "
			             + "  controlaccount=0 and active =1 and closed= 0 and blocked=0 "
			             + "  and companyId ="+ companyId + " and branch=" + branchId ;
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getResultMapForNull(query); 
           }
        
        
           public String getCurrency(String countryid)
	   {	
		
		  String query=" select a.id as id "+
			              " from currency a, countries b where "
			             + "  a.curency_code = b.currency_code "
			             + "  and b.id ="+ countryid  ;
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getString(query); 
	    }
           
           
           public String getCurrencyIdByCode(String currCode)
	   {
		  String query=" select id as id "+
			              " from currency where "
			             + "  currency_code ='"+ currCode + "'";
			           
		   log.info("query  = " + query);
		   
		   return baseDao.getString(query); 
	    }
           
           
           
        @SuppressWarnings("unchecked")
	public Map<String,String> getTransactionCodeInfo(String tranCode)
	{
		String query=" select ID as id, TransactionCode as tran_code, Description as description, narrative as narrative "+
	    	        " from txncodes "
	        + "  where TransactionCode ='" + tranCode + "'";
	      
	log.info("query  = " + query);
	
	   return baseDao.queryForMap(query); 
	}
        
        
   
        public Map<String,String> getServiceAuth()
        {
          String query= "select user as user, pwd as pwd FROM tblwebserv a where a.app = 'internal'";
	  log.info("query  = " + query);
	
	  return baseDao.queryForMap(query);
        }
        
        
         public List<Map<String,Object>> getProductsByType(String companyId, String branchId, String prodType)
	   {	
		
		  String query=" select code as code, segment_code as segment_code, "+
			              " name as name, segment_code as segment_code from products  where "
			             + "  is_deleted =0  "
			             + "  and company_id ="+ companyId + " and branch_id=" + branchId 
                                     +  " and product_type_code='" + prodType.toUpperCase() + "'  LIMIT 1";
			           
		  System.out.println("query  = " + query);
		   
		  return baseDao.getResultMapForNull(query);  
		}
         
         
          public List<Map<String,Object>> getDefProducts(String companyId, String branchId)
	   {	
		
		   String query=" select code as code, segment_code as segment_code, "+
			              " name as name, segment_code as segment_code from products  where "
			             + "  is_default=1 and is_deleted =0 "
			             + "  and company_id ="+ companyId + " and branch_id=" + branchId;
			           
		   log.info("query  = " + query);
			           	   
		   return baseDao.getResultMapForNull(query); 
           }
          
          
          
          
          
          public List<Map<String,Object>> getDefaultProductsByType(String companyId, String branchId, String prodType)
	   {	
		
               	 String query=" select code as code, segment_code as segment_code, "+
			              " name as name, segment_code as segment_code from products  where "
			             + "  is_default=1 and  is_deleted =0  "
			             + "  and company_id ="+ companyId + " and branch_id=" + branchId 
                                     +  " and product_type_code='" + prodType.toUpperCase() + "'  LIMIT 1";
			           
		  System.out.println("query  = " + query);
		   
		  return baseDao.getResultMapForNull(query);  
		}
   
        public Map<String, List<Map<String,Object>>> getMemberDetail(String objId, Map<String,Map<String,Object>>  pageParams,String[] objCode)
	{
	 Map<String,List<Map<String,Object>>> returnMap= new HashMap<String, List<Map<String,Object>>>();
		  
	  Set<String> keys = pageParams.keySet();
	
		 for(String key : keys) 
		 {
			log.info(" Current key ::: " + key);
                         
			Map<String,Object> theMap = pageParams.get(key);
			
			if(theMap!=null)
			{
				//HashMap configMap = (HashMap)theMap.get(key);
				log.info(" configMap for key ::: " + theMap.size());
				
				Set<String> theMapKeys = theMap.keySet();
				
					 for(String configKey : theMapKeys) 
					 {
						 log.info(" configKey key ::: " + configKey);
					 }
					 
				try
				{
				String beginWhereClause = (String)(theMap).get("openWhereClause");
				log.info("beginWhereClause::: " + beginWhereClause);
				String endWhereClause = (String)(theMap).get("endWhereClause");
				log.info("endWhereClause::: " + endWhereClause);
				String orderClause = (String)(theMap).get("orderClause");
				log.info("orderClause::: " + orderClause);
				boolean mapToParent = Boolean.parseBoolean((String)(theMap).get("mapToParent"));
				log.info("mapToParent::: " + mapToParent);
				
				String theQuery= opsQueryMap.get(key);
				
				log.info("theQuery ::: " + theQuery);
				
				  if(theQuery!=null && !theQuery.equalsIgnoreCase(""))
				  {
					  theQuery=theQuery + " where 1=1 ";
					  
					  log.info("theQuery After Initial where::: " + theQuery);
					  
						   if (beginWhereClause!=null && !beginWhereClause.equalsIgnoreCase("") && beginWhereClause.length() > 0 )
						   {
							   theQuery = theQuery + beginWhereClause + objId + endWhereClause;
						   }
						  
					       log.info("theQuery After whereclause::: " + theQuery);
						   
						   if(orderClause!=null && !orderClause.equalsIgnoreCase("") && orderClause.length() > 0)
						   {
							   theQuery = theQuery + " order by " + orderClause;
						   }
						   
					 log.info("Ops Query:: " + theQuery);
						   
						   Long currentTimeNow = System.currentTimeMillis();
						   
						   List<Map<String,Object>> theList = baseDao.getResultMapForNull(theQuery);
						    
						   Long currentTimeAfter = System.currentTimeMillis();
						   
						   log.info("Time Spent on retrieving " + key + " ===> " + (currentTimeAfter-currentTimeNow) + "ms" );
						   
						    if(theList.size()!=0)
						    {
				    	
						    	if(mapToParent)
						    	{
						    		
						    		returnMap.put(key, theList);
						    		log.info("Mapping  " + key + " to List of size ===> " + theList.size());
						    	}
						    	else
						    	{
						    		returnMap.put(key,theList);
						    		log.info("Mapping  " + key + " to List of size ===> " + theList.size());
						    	}
						    	
						    }
						    else
						    {
						    	theList = new ArrayList<Map<String,Object>>();
						    	returnMap.put(key,theList);
						    	log.info("Mapping  " + key + " with size ===> " + theList.size());
						    }
				  }
				}
				catch(Exception ex)
				{
					log.info(ex.getMessage());
				}
				
			}
			
		}
	 
		return returnMap;
	}

        public List<Map<String,Object>> getAddressColumns(String countryId)
	   {	
		
		   String query=" select addr_field_idx as code, addr_field_name as descr "+
			              " from country_address_filter  where "
			             + "  country_id =" + countryId + " order by addr_field_idx asc";
			           
		   log.info("query  = " + query);
			           	   
		   return baseDao.getResultMapForNull(query); 
           }
        
        
         public List<Map<String,Object>> getStockColumns(String companyId)
	   {	
		   String query=" select short_name as code, comp_stock_name as descr, default_stock as defstock, comp_stock_type_id as stock_id "+
			              " from comp_stock_type a, status b  where "
                                      + " a.status_id = b.status_id  "
			              + " and company_id =" + companyId + " and register_stock='N' and status_short = 'APPR-STOCK' and a.del_flg ='N' order by short_name asc";
                   
		   log.info("query  = " + query);
			           	   
		   return baseDao.getResultMapForNull(query); 
          }
         
         public List<Map<String,Object>> getExtrafldColumns(String companyId)
	   {	
		
		/*   String query=" select ID as code, description as descr, grouped as optional "+
			              " from member_extraflds where "
			             + "  company_id =" + companyId + " and deleted ='N' "
                                     + " union "
                                     + " select concat(ID,'_OPT') as code, concat(trim(description),' OPTION CODE') as descr, grouped as optional "+
			              " from member_extraflds where "
			             + "  company_id =" + companyId + " and deleted ='N' and grouped = 'Y' order by code asc"
                                     ;
                   */
                    String query=" select CONVERT(ID, CHAR) as code, description as descr, grouped as optional "+
			              " from member_extraflds where "
			             + "  company_id =" + companyId + " and deleted ='N' order by code asc"
                                    ;
			           
		   log.info("query  = " + query);
			           	   
		   return baseDao.getResultMapForNull(query); 
           }
         
          public List<Map<String,Object>> getContribColumns(String companyId, String type)
	   {	
		   String query=" select b.code as code, b.name as descr, b.id as prod_id, b.product_type_code as type_id "+
			              " from producttype a, products b  where "
                                      + " b.product_type_code = a.code  "
			              + " and company_id =" + companyId + " and a.code=' " +type+ "'";
                   
		   log.info("query  = " + query);
			           	   
		   return baseDao.getResultMapForNull(query); 
           }

          
         public Map<String,String> getStockProperty(String stockId)
	   {	
		   String query=" select b.stock_ppty_name as stock_ppty_name, b.stock_ppty_desc as stock_ppty_desc, b.stock_ppty_id as stock_ppty_id, "+
			              " stock_ppty_formula as stock_ppty_formula, comp_stock_ppty_val as  comp_stock_ppty_val from comp_stock_type_detail a, comp_stock_property b  where "
                                      + " a.stock_ppty_id = b.stock_ppty_id  "
			              + " and a.comp_stock_type_id =" + stockId + " and b.del_flg ='N' order by b.stock_ppty_id asc";
                   
		   log.info("query  = " + query);
			           	   
		   List<Map<String,Object>> theListMap = baseDao.getResultMapForNull(query); 
                   
                   Map<String,String> returnMap = new HashMap<String,String>();
		
		if(theListMap !=null && theListMap.size() > 0)
		{
			for (Map<String,Object> theMap : theListMap)
			{
				returnMap.put((String)theMap.get("stock_ppty_name"), (String)theMap.get("comp_stock_ppty_val"));
			}
		}
                
                return returnMap;
           }
         
         
        
    @SuppressWarnings("unchecked")
    public Map<String,String> getCountryInfoByCompany(String companyId)
    {

            String query=" select a.ID as id, a.COUNTRY_CODE as country_code, a.COUNTRY_NAME as country_name, a.TIMEZ as time_zone "+
                    " from countries a, company b where a.id=b.country_id "
            + "  and b.id =" + companyId;

    log.info("query  = " + query);

       return baseDao.queryForMap(query); 
    }
    
    
     public Map<String,String> getCompanyInfo(Integer companyId, Integer branchId)
    {

          String query= "select a.name as name ,a.short_name  as short_name,a.code as code,"
                   + " cast(a.Country_id AS CHAR) as country_id, cast(b.id AS CHAR) as branch_id ,cast(b.currentyear AS CHAR) as current_year,"
                   + " cast(b.currentperiod AS CHAR) as current_period,cast(DATE(b.postdate) AS CHAR) as post_date,"
                   + " cast(DATE(b.currentsystemdate) AS CHAR) as current_sysdate,cast(DATE(b.entrydate) AS CHAR) as entry_date,"
                   + " c.currency_code as currency_code, basecurrency as base_currency,"
                   + " c.timez as timez, cast(a.id as CHAR) as company_id from company a , branch b,countries c "
                   + " where a.id = b.company_id and b.country_id = c.id and a.id  =" + companyId  + " and b.id = " + branchId;

            log.info("query  = " + query);

       return baseDao.queryForMap(query); 
    }
     
     
      public Map<String,String> getUserInfo(String userid)
      {
            String userQuery ="select userid as userid, username as username, cast(branch AS CHAR) as branch_id, "
                    + " cast(companyid AS CHAR) as company_id, accesslevel as access_level  "
                    + "FROM users where userid = '" + userid + "'";

         log.info("query  = " + userQuery);

       return baseDao.queryForMap(userQuery); 
     }
      
      
    public Map<String,String> getAddressEntry(String keyId, String typeId, String addFieldName)
        {	
                String query=" select addr_field_value as content, addr_field_name as name "+
                                   " from address_entries where "
                                  + "  type_id =" + typeId + " and key_id=" +keyId+ " and addr_field_name='" + addFieldName + "'";

                log.info("query  = " + query);

                return baseDao.queryForMap(query); 
        }
    
    
     public Map<String,String> getMemberEntryByEmail(String companyId, String email)
      {	
                 String query=" select email_add_1 as email_id, first_name as first_name, cast(member_id as CHAR) as member_id  " +
                        " from member a, status b where a.status_id= b.status_id and company_id=" +companyId+ " and email_add_1='" +email.toLowerCase()+ "' "
                        + "  and status_short ='APR-MEMBER'";
                 
                log.info("query  = " + query);

                return baseDao.queryForMap(query); 
      }
     
    public Map<String,String> getExtrafldEntry(String keyId, String addFieldid)
        {	
                String query=" select mem_field_value as content, mem_field_id as name "+
                                   " from member_extraflds_entries where "
                                  + " key_id=" +keyId+ " and mem_field_id='" + addFieldid + "'";

                log.info("query  = " + query);

                return baseDao.queryForMap(query); 
        }
    
    public List<Map<String,Object>> getQueryRead(String queryRead)
    {	
            log.info("query  = " + queryRead);
            return baseDao.getResultMapForNull(queryRead); 
    }
    
    
    public Map<String,String> getObjectRead(String objectRead)
    {	
            log.info("query  = " + objectRead);
            return baseDao.queryForMap(objectRead); 
    }
    
     public Map<String,Object> getTableRead(String objectRead)
    {	
            log.info("query  = " + objectRead);
            return baseDao.queryForMap(objectRead); 
    }
        

    public Map<String, String> getOpsQueryMap() {
        return opsQueryMap;
    }

    public void setOpsQueryMap(Map<String, String> opsQueryMap) {
        this.opsQueryMap = opsQueryMap;
    }
           
           
}
