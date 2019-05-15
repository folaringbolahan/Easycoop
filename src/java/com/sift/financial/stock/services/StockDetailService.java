package com.sift.financial.stock.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.sift.financial.member.*;


public class StockDetailService {
	
	private CompanyDAO companyDAO;
	private MemberDAO memberDAO;
	private AddressTypeDAO addressTypeDAO;
	private CountryAddressFilterDAO countryAddressFilterDAO;
	private CompStockTypeDAO compStockTypeDAO;
	private CompStockTypeDetailDAO compStockTypeDetailDAO;
	private CompStockPropertyDAO  compStockPropertyDAO;
	private TaxGroupsDAO  taxGroupsDAO;
	
	//private CompStockPropertyDAO compStockPropertyDAO;
	
	
	public MemberDAO getMemberDAO() {
		return memberDAO;
	}
	@Autowired
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	public AddressTypeDAO getAddressTypeDAO() {
		return addressTypeDAO;
	}
	@Autowired
	public void setAddressTypeDAO(AddressTypeDAO addressTypeDAO) {
		this.addressTypeDAO = addressTypeDAO;
	}
	public CountryAddressFilterDAO getCountryAddressFilterDAO() {
		return countryAddressFilterDAO;
	}
	@Autowired
	public void setCountryAddressFilterDAO(
			CountryAddressFilterDAO countryAddressFilterDAO) {
		this.countryAddressFilterDAO = countryAddressFilterDAO;
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
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}
	@Autowired
	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	
	//method to evaluate Member voting right for newly added stock added
	public String getNewVotingRight(Member mem, CompStockType stock, String quantity)
	{
		
		
		
		
		
	  return null;
	}
	
	//method to evaluate amount paid for stock by member
	
	/**
	 * @param mem
	 * @param stock
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Double evaluateStockBuyAmt(Member mem, CompStockType stock, double quantity) throws Exception
	{
		
	  return evaluateTotalStockPrice (StockInterface.STOCKCOSTPPTY, stock,  quantity);
	}
	
	
	//method to evaluate amount paid out for stock by sale back to Company

	/**
	 * @param mem
	 * @param stock
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	public Double evaluateStockSaleAmt(Member mem, CompStockType stock, double quantity) throws Exception
	{
		
		return evaluateTotalStockPrice (StockInterface.STOCKPERVALUEPPTY, stock,  quantity);
		
	}
	
	
	
	/**
	 * @param priceType
	 * @param stock
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	private Double evaluateTotalStockPrice (String priceType, CompStockType stock, double quantity ) throws Exception
	{
		
		Double returnVal= null;
		
		List ppties = 	getCompStockPropertyDAO().findByStockPptyName(priceType);
		
		if (ppties!=null)
		{
			
			CompStockProperty pptyObj = (CompStockProperty)ppties.get(0);
			
			//GET STOCK PROPERTY
			List details = getCompStockTypeDetailDAO().findByPPty(pptyObj.getStockPptyId().toString(),stock.getCompStockTypeId().toString(),stock.getCompany().getId().toString());
			
			 if(details!=null)
			 {
				 
				 Object[] objDet = (Object[])details.get(0);
				 
				 returnVal = quantity * ((Double)objDet[4]);
			 }
			 else
			 {
				 
				 throw new Exception("Unable to retrieve Stock Details");
			 }
		}
		else
		{
			throw new Exception("Problem with retrieving default Stock property");
		}
		
		return returnVal;
	}
	

	//method to evaluate total stock for member
	
	public Map<String, Object> getMemberStocks(Member mem, Company comp) 
	{
		
		
		
		
		
		
		
	  return null;
	}
	
	
	//method evaluate member total voting Right
	
	public Double getMemberVotingRight(Member mem, Company commp)
	{
		
		
		
	  return null;
	}
	//method to evaluate stock movement on expulsion
	//not necessary
	
	//method to evaluate voting Right on expulsion
	//not necessary
	
	//method to evaluate stock movement on transfer
	
	public void doStockTransfer(Member mem, Member targetmem, Company commp)
	{
		
		
		
		
		
	
	}
	
	//method to evaluate voting Right on transfer
	public void doVotingRightTransfer(Member mem, Member targetmem, Company commp)
	{
		
		
		
		
		
		
		
		
		
	

	}
	//method to evaluate stock movement on Resignation
	
	//not required
	
	
	//method to evaluate voting Right on Resignation
	//not required

}
