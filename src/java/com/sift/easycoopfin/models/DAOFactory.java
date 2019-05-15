
package com.sift.easycoopfin.models;

import com.sift.easycoopfin.models.dao.VotesDAO;
import com.sift.easycoopfin.models.dao.VoteresultsDAO;
import com.sift.easycoopfin.models.dao.AgmsDAO;
import com.sift.easycoopfin.models.dao.SavingsDAO;
import com.sift.easycoopfin.models.dao.ProductTypeDAO;
import com.sift.easycoopfin.models.dao.ProductAccountDAO;
import com.sift.easycoopfin.models.dao.ProductAccountValDAO;
import com.sift.easycoopfin.models.dao.VoteoptionsDAO;
import com.sift.easycoopfin.models.dao.ProductAccountTypeDAO;
import com.sift.easycoopfin.models.dao.MemberDAO;
import com.sift.easycoopfin.models.dao.BranchDAO;
import com.sift.easycoopfin.models.dao.ProductsDAO;
import com.sift.easycoopfin.models.dao.CompanyDAO;
import com.sift.easycoopfin.models.dao.AgmattendeesDAO;
import com.sift.easycoopfin.models.dao.UsersDAO;
import com.sift.easycoopfin.models.dao.CurrencyDAO;
import com.sift.easycoopfin.models.dao.AccountDAO;
import com.sift.easycoopfin.models.dao.AgmProxyDAO;
import com.sift.easycoopfin.models.dao.CustaccountdetailsDAO;
import com.sift.easycoopfin.models.dao.FileUploadDAO;
import com.sift.easycoopfin.models.dao.FileUploadErrorDAO;
import com.sift.easycoopfin.models.dao.SavingsErrorDAO;
import com.sift.easycoopfin.models.dao.TxnCodeDAO;
import com.sift.easycoopfin.models.dao.VoteAccessQuestionsDAO;

public abstract class DAOFactory {
	private static DAOFactory _factory = new DAOFactoryImpl();
	
	public static DAOFactory getDAOFactory() {
		return _factory;
	}
	
	public abstract CompanyDAO getCompanyDAO();
	public abstract VotesDAO getVotesDAO();
	public abstract SavingsDAO getSavingsDAO();
	public abstract VoteoptionsDAO getVoteoptionsDAO();
	public abstract AgmsDAO getAgmsDAO();
	public abstract ProductsDAO getProductsDAO();
	public abstract VoteresultsDAO getVoteresultsDAO();
	public abstract UsersDAO getUsersDAO();
	public abstract BranchDAO getBranchDAO();
	public abstract MemberDAO getMemberDAO();
	public abstract AgmattendeesDAO getAgmattendeesDAO();
	public abstract ProductTypeDAO getProductTypeDAO();
	public abstract ProductAccountDAO getProductAccountDAO();
        public abstract ProductAccountValDAO getProductAccountValDAO();
	public abstract ProductAccountTypeDAO getProductAccountTypeDAO();
        public abstract CurrencyDAO getCurrencyDAO();
        public abstract AccountDAO getAccountDAO();
        public abstract FileUploadDAO getFileUploadDAO();
        public abstract FileUploadErrorDAO getFileUploadErrorDAO();
        public abstract CustaccountdetailsDAO getCustaccountdetailsDAO();      
        public abstract VoteAccessQuestionsDAO getVoteAccessQuestionsDAO();
        public abstract AgmProxyDAO getAgmProxyDAO();
        public abstract TxnCodeDAO getTxnCodeDAO();
        public abstract SavingsErrorDAO getSavingsErrorDAO();
}

