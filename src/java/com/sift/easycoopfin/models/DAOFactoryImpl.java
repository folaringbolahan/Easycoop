package com.sift.easycoopfin.models;

import com.sift.easycoopfin.models.dao.AccountDAO;
import com.sift.easycoopfin.models.impl.UsersDAOImpl;
import com.sift.easycoopfin.models.impl.ProductAccountDAOImpl;
import com.sift.easycoopfin.models.impl.ProductAccountValDAOImpl;
import com.sift.easycoopfin.models.impl.ProductAccountTypeDAOImpl;
import com.sift.easycoopfin.models.impl.SavingsDAOImpl;
import com.sift.easycoopfin.models.impl.VoteresultsDAOImpl;
import com.sift.easycoopfin.models.impl.VoteoptionsDAOImpl;
import com.sift.easycoopfin.models.impl.VotesDAOImpl;
import com.sift.easycoopfin.models.impl.ProductTypeDAOImpl;
import com.sift.easycoopfin.models.impl.BranchDAOImpl;
import com.sift.easycoopfin.models.impl.ProductsDAOImpl;
import com.sift.easycoopfin.models.impl.AgmsDAOImpl;
import com.sift.easycoopfin.models.impl.CompanyDAOImpl;
import com.sift.easycoopfin.models.impl.AgmattendeesDAOImpl;
import com.sift.easycoopfin.models.impl.MemberDAOImpl;
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
import com.sift.easycoopfin.models.dao.CurrencyDAO;
import com.sift.easycoopfin.models.dao.CustaccountdetailsDAO;
import com.sift.easycoopfin.models.dao.FileUploadDAO;
import com.sift.easycoopfin.models.dao.UsersDAO;
import com.sift.easycoopfin.models.dao.VoteAccessQuestionsDAO;
import com.sift.easycoopfin.models.impl.AccountDAOImpl;
import com.sift.easycoopfin.models.impl.CurrencyDAOImpl;
import com.sift.easycoopfin.models.impl.CustaccountdetailsDAOImpl;
import com.sift.easycoopfin.models.impl.FileUploadDAOImpl;
import com.sift.easycoopfin.models.impl.VoteAccessQuestionsDAOImpl;
import com.sift.easycoopfin.models.dao.AgmProxyDAO;
import com.sift.easycoopfin.models.dao.FileUploadErrorDAO;
import com.sift.easycoopfin.models.impl.AgmProxyDAOImpl;
import com.sift.easycoopfin.models.dao.TxnCodeDAO;
import com.sift.easycoopfin.models.impl.TxnCodeDAOImpl;
import com.sift.easycoopfin.models.dao.SavingsErrorDAO;
import com.sift.easycoopfin.models.impl.FileUploadErrorDAOImpl;
import com.sift.easycoopfin.models.impl.SavingsErrorDAOImpl;

public class DAOFactoryImpl extends DAOFactory {

    private CompanyDAO _companyDAO = new CompanyDAOImpl();

    public CompanyDAO getCompanyDAO() {
        return _companyDAO;
    }
    private VotesDAO _votesDAO = new VotesDAOImpl();

    public VotesDAO getVotesDAO() {
        return _votesDAO;
    }
    private SavingsDAO _savingsDAO = new SavingsDAOImpl();

    public SavingsDAO getSavingsDAO() {
        return _savingsDAO;
    }
    private VoteoptionsDAO _voteoptionsDAO = new VoteoptionsDAOImpl();

    public VoteoptionsDAO getVoteoptionsDAO() {
        return _voteoptionsDAO;
    }
    private AgmsDAO _agmsDAO = new AgmsDAOImpl();

    public AgmsDAO getAgmsDAO() {
        return _agmsDAO;
    }
    private ProductsDAO _productsDAO = new ProductsDAOImpl();

    public ProductsDAO getProductsDAO() {
        return _productsDAO;
    }
    private VoteresultsDAO _voteresultsDAO = new VoteresultsDAOImpl();

    public VoteresultsDAO getVoteresultsDAO() {
        return _voteresultsDAO;
    }
    private UsersDAO _usersDAO = new UsersDAOImpl();

    public UsersDAO getUsersDAO() {
        return _usersDAO;
    }
    private BranchDAO _branchDAO = new BranchDAOImpl();

    public BranchDAO getBranchDAO() {
        return _branchDAO;
    }
    private MemberDAO _memberDAO = new MemberDAOImpl();

    public MemberDAO getMemberDAO() {
        return _memberDAO;
    }
    private AgmattendeesDAO _agmattendeesDAO = new AgmattendeesDAOImpl();

    public AgmattendeesDAO getAgmattendeesDAO() {
        return _agmattendeesDAO;
    }
    private ProductTypeDAO _productTypeDAO = new ProductTypeDAOImpl();

    public ProductTypeDAO getProductTypeDAO() {
        return _productTypeDAO;
    }
    private ProductAccountDAO _productAccountDAO = new ProductAccountDAOImpl();

    public ProductAccountDAO getProductAccountDAO() {
        return _productAccountDAO;
    }
    private ProductAccountTypeDAO _productAccountTypeDAO = new ProductAccountTypeDAOImpl();

    public ProductAccountTypeDAO getProductAccountTypeDAO() {
        return _productAccountTypeDAO;
    }
    private CurrencyDAO _currencyDAO = new CurrencyDAOImpl();

    public CurrencyDAO getCurrencyDAO() {
        return _currencyDAO;
    }
    private AccountDAO _accountDAO = new AccountDAOImpl();

    public AccountDAO getAccountDAO() {
        return _accountDAO;
    }
    private FileUploadDAO _fileUploadDAO = new FileUploadDAOImpl();

    public FileUploadDAO getFileUploadDAO() {
        return _fileUploadDAO;
    }
    private FileUploadErrorDAO _fileUploadErrorDAO = new FileUploadErrorDAOImpl();

    public FileUploadErrorDAO getFileUploadErrorDAO() {
        return _fileUploadErrorDAO;
    }
    private CustaccountdetailsDAO _custAccountDAO = new CustaccountdetailsDAOImpl();

    public CustaccountdetailsDAO getCustaccountdetailsDAO() {
        return _custAccountDAO;
    }
    private VoteAccessQuestionsDAO _voteAccessQuestions = new VoteAccessQuestionsDAOImpl();

    public VoteAccessQuestionsDAO getVoteAccessQuestionsDAO() {
        return _voteAccessQuestions;
    }

     private AgmProxyDAO _agmProxyDAO = new AgmProxyDAOImpl();
     
    public AgmProxyDAO getAgmProxyDAO() {
       return _agmProxyDAO;
    }

   private TxnCodeDAO _txnCodeDAO = new TxnCodeDAOImpl();
    public TxnCodeDAO getTxnCodeDAO() {
        return _txnCodeDAO;
    }

     private SavingsErrorDAO _savingsErrorDAO = new SavingsErrorDAOImpl();
    public SavingsErrorDAO getSavingsErrorDAO() {
       return _savingsErrorDAO;
    }

    private ProductAccountValDAO _productAccountValDAO = new ProductAccountValDAOImpl();
    public ProductAccountValDAO getProductAccountValDAO() {
       return _productAccountValDAO;
    }
}
