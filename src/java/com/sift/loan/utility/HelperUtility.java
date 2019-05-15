package com.sift.loan.utility;

import com.sift.admin.bean.*;
import com.sift.admin.model.*;
import com.sift.financial.member.Member;
import com.sift.loan.bean.*;
import com.sift.loan.model.*;
import com.sift.webservice.model.UserGroupBeanWS;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HelperUtility {

    @Autowired
    private SessionFactory sessionFactory;

    //@Autowired
    //private JndiObjectFactoryBean datasource; 
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*	public JndiObjectFactoryBean getDatasource() {
     return datasource;
     }

     public void setDatasource(JndiObjectFactoryBean datasource) {
     this.datasource = datasource;
     }*/
    @SuppressWarnings("unchecked")
    public List<UserGroupBean> getUserGroupBeanList() {
        System.out.println("sessionFactory=:" + String.valueOf(sessionFactory));
        return prepareListofBean((List<UserGroup>) sessionFactory.getCurrentSession().createCriteria(UserGroup.class).list());
    }

    public List<UserRoleBean> getUserRoleBeanList() {
        System.out.println("sessionFactory=:" + String.valueOf(sessionFactory));
        return prepareListofUserRoleBean((List<UserRole>) sessionFactory.getCurrentSession().createCriteria(UserRole.class).list());
    }

    public List<CountryBean> getCountryBeanList() {
        System.out.println("sessionFactory=:" + String.valueOf(sessionFactory));
        return prepareListofCountryBean((List<Country>) sessionFactory.getCurrentSession().createCriteria(Country.class).list());
    }

    public Branch getBranch(int id) {
        return (Branch) sessionFactory.getCurrentSession().get(Branch.class, id);
    }

    public boolean LoanExist(String loanCaseId) {
        long row = 0;
        Query query = sessionFactory.getCurrentSession().createQuery("select count(loanCaseId) from LoanRequest where loanCaseId='" + loanCaseId + "'");

        for (Iterator it = query.iterate(); it.hasNext();) {
            row = (Long) it.next();
        }

        System.out.println("row=:" + row);

        return row > 0 ? true : false;
    }

    public boolean UserEmailExist(String emailId) {
        long row = 0;
        Query query = sessionFactory.getCurrentSession().createQuery("select count(email) from User where email='" + emailId + "'");

        for (Iterator it = query.iterate(); it.hasNext();) {
            row = (Long) it.next();
        }

        return row > 0 ? false : true;
    }

    public boolean UserEmailExist(String emailId, String userId) {
        long row = 0;
        Query query = sessionFactory.getCurrentSession().createQuery("select count(email) from User where  lower(email)='" + emailId.trim().toLowerCase() + "' AND LOWER(userId)<>'" + userId.trim().toLowerCase() + "'");

        for (Iterator it = query.iterate(); it.hasNext();) {
            row = (Long) it.next();
        }

        return row > 0 ? false : true;
    }

    public Long getLoanScheduleID(String loanCaseId) {
        long row = 0;
        Query query = sessionFactory.getCurrentSession().createQuery("select max(id) from LoanRepaymentSchedule where loanCaseId='" + loanCaseId + "'");

        for (Iterator it = query.iterate(); it.hasNext();) {
            row = Long.valueOf(String.valueOf(it.next()));
        }

        return row;
    }

    public String getTimeZoneGivenCountry(String countryId) {
        String value = "";
        String sql = "select timez from countries where id=" + countryId;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        value = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        return value;
    }

    public int getLoanAccountsCountByMemberAndProduct(int companyId, int branchId, String segmentCode, String memberNo, String branchCode) {
        int row = 0;
        String accountType = "M";
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select count(accountNo) from accounts where companyId='" + companyId + "'"
                + " and branch ='" + branchId + "' AND aseg1 ='" + segmentCode + "' AND aseg2 ='" + memberNo + "'"
                + " AND aseg4 ='" + branchCode + "' AND accounttype ='" + accountType + "'");

        row = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
        return row;

    }

    public String getActiveLoanProductsCount(String memberNo, String productCode, String branchId, String companyId) {
        String value = "";
        String sql = "SELECT count(a.accountno) as kount FROM accounts a inner join custaccountdetails b on a.accountno = b.accountno and a.companyid = b.companyid  and a.branch = b.branchid where a.asegc ='" + memberNo + "' and b.product = '" + productCode + "' and a.branch = " + branchId + " and a.companyid = " + companyId;
        System.out.println("ActiveLoanProductsCount sql=: " + sql);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        value = (query == null || query.list().isEmpty()) ? "0" : query.list().get(0).toString();

        System.out.println("current loans count=: " + value);
        return value;
    }

    public double getMemberTotalSavings(String memberId, String companyId, String branchId) {
        double savingsTotal = 0.00;

        /* String sql=  " SELECT a.Balance AS TOTAL_CONTRIBUTION FROM custaccountdetails c JOIN accounts  a ON a.AccountNo=c.AccountNo " +
         " join products p on p.code=c.Product " +
         " where p.company_id="+companyId+" and p.branch_id="+branchId+" and c.Companyid="+companyId+" " +
         " and c.Branchid="+branchId+" and a.asegc='"+memberId+"' and p.product_type_code IN ('C','S')";*/

        String sql = " select SUM(a.Balance) BALANCE from accounts a inner join custaccountdetails c on a.AccountNo=c.AccountNo and a.branch = c.Branchid and a.Companyid = c.Companyid " 
                + " inner join Products p on c.Product=p.code and c.Branchid = p.branch_id and c.Companyid = p.company_id where 1=1 "
                + "	and a.branch=" + branchId + " and a.companyid=" + companyId + "   		"
                + "	and p.product_type_code IN ('C','S')							"
                + "	and a.asegc='" + memberId + "'										"
                + "	group by a.asegc";

        System.out.println("getMemberTotalSavings  SQL=: " + sql);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List data = query.list();

        double totalSavings = 0.0;

        if (data != null && !data.isEmpty()) {

            for (Object item : data) {
                //Double row = (Double) item;
                //Double row = (Double) item;
                //BigDecimal row = (BigDecimal) item;
                String strNumber = item.toString();
                BigDecimal row = new BigDecimal(strNumber);
                totalSavings += row == null ? Double.parseDouble("0.00") : Double.parseDouble(row.toString());
            }

            savingsTotal = totalSavings;
        }

        System.out.println("savingsTotal: " + savingsTotal);
        return savingsTotal;
    }
    
    public double getMemberLoanBalance(String memberno, String companyId, String branchId) {
        double  runningLoanBal = 0.00;

        String sql = "select sum(OUTSTANDING_BAL_TOTAL) as Total from loan_request where ( LOAN_STATUS='A' or LOAN_STATUS='D') and member_no='"+memberno+"' and  company_id='"+companyId+"' and branch_id='"+branchId+"' group by member_no, branch_id ,company_id";


        System.out.println("running loan balance  SQL=: " + sql);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        String valueStr = (query == null || query.list().isEmpty()) ? "0.00" : query.list().get(0).toString();

        try {
            runningLoanBal = Double.parseDouble(valueStr);
            System.out.println("runing loan balance "+ runningLoanBal);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            runningLoanBal = 0.00;
        }
        return  runningLoanBal;
    }

    public double getMemberTotalSavingContribution(String memberId, String companyId, String branchId) {
        double savingsTotal = 0.00;

        /* String sql=  " SELECT a.Balance AS TOTAL_CONTRIBUTION FROM custaccountdetails c JOIN accounts  a ON a.AccountNo=c.AccountNo " +
         " join products p on p.code=c.Product " +
         " where p.company_id="+companyId+" and p.branch_id="+branchId+" and c.Companyid="+companyId+" " +
         " and c.Branchid="+branchId+" and a.asegc='"+memberId+"' and p.product_type_code IN ('C','S')";*/

       /* String sql = " select SUM(a.Balance) BALANCE from accounts a ,custaccountdetails c ,Products p where 1=1 "
                + "	and a.branch=" + branchId + " and a.companyid=" + companyId + "   		"
                + "	and a.AccountNo=c.AccountNo			 							"
                + "	and p.company_id=" + companyId + " and p.branch_id=" + branchId + "		"
                + "	and p.code=c.Product											"
                + "	and p.product_type_code IN ('C','S')							"
                + "	and a.asegc='" + memberId + "'										"
                + "	group by a.asegc";*/
        
         String sql = " select SUM(a.Balance) BALANCE from accounts a inner join custaccountdetails c on a.AccountNo=c.AccountNo and a.branch = c.Branchid and a.Companyid = c.Companyid " 
                + " inner join Products p on c.Product=p.code and c.Branchid = p.branch_id and c.Companyid = p.company_id where 1=1 "
                + "	and a.branch=" + branchId + " and a.companyid=" + companyId + "   		"
                + "	and p.product_type_code IN ('C','S')							"
                + "	and a.asegc='" + memberId + "'										"
                + "	group by a.asegc";

        System.out.println("getMemberTotalSavings  SQL=: " + sql);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List data = query.list();

        double totalSavings = 0.0;

        if (data != null && !data.isEmpty()) {
            for (Object item : data) {
                Double row = (Double) item;
                totalSavings += row == null ? Double.parseDouble("0.00") : Double.parseDouble(row.toString());
            }
        }

        savingsTotal = totalSavings;
        System.out.println("savingsTotal: " + savingsTotal);

        return savingsTotal;
    }

    public double getMemberTotalSavingContributionNew(String memberId, String companyId, String branchId) {
        double savingsTotal = 0.00;

        /* String sql=  " SELECT a.Balance AS TOTAL_CONTRIBUTION FROM custaccountdetails c JOIN accounts  a ON a.AccountNo=c.AccountNo " +
         " join products p on p.code=c.Product " +
         " where p.company_id="+companyId+" and p.branch_id="+branchId+" and c.Companyid="+companyId+" " +
         " and c.Branchid="+branchId+" and a.asegc='"+memberId+"' and p.product_type_code IN ('C','S')";*/

       /* String sql = " select SUM(a.Balance) BALANCE from accounts a ,custaccountdetails c ,Products p where 1=1 "
                + "	and a.branch=" + branchId + " and a.companyid=" + companyId + "   		"
                + "	and a.AccountNo=c.AccountNo			 							"
                + "	and p.company_id=" + companyId + " and p.branch_id=" + branchId + "		"
                + "	and p.code=c.Product											"
                + "	and p.product_type_code IN ('C','S')							"
                + "	and a.asegc='" + memberId + "'										"
                + "	group by a.asegc";*/

        String sql = " select SUM(a.Balance) BALANCE from accounts a inner join custaccountdetails c on a.AccountNo=c.AccountNo and a.branch = c.Branchid and a.Companyid = c.Companyid " 
                + " inner join Products p on c.Product=p.code and c.Branchid = p.branch_id and c.Companyid = p.company_id where 1=1 "
                + "	and a.branch=" + branchId + " and a.companyid=" + companyId + "   		"
                + "	and p.product_type_code IN ('C','S')							"
                + "	and a.asegc='" + memberId + "'										"
                + "	group by a.asegc";
        
        System.out.println("getMemberTotalSavings  SQL=: " + sql);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        String valueStr = (query == null || query.list().isEmpty()) ? "0.00" : query.list().get(0).toString();

        try {
            savingsTotal = Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            savingsTotal = 0.00;
        }

        return savingsTotal;
    }

    public String getTimeZoneGivenCompanyId(String companyId) {
        String value = "";
        String sql = "select timez from countries where id=(select country_id from company where id='" + companyId + "')";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        value = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        return value;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<UserBean> getInActiveUserBeanList(SessionFactory curSession, String branchId) {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.enabled,a.userId,a.email,a.username,a.groupId,a.phone,a.companyId,a.branchId,b.name,c.branchName from User a,Company b, Branch c where a.enabled=0 and b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id and a.branchId='" + branchId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object[] row = results.next();

            userBean.setId((Integer) row[i++]);
            userBean.setEnabled((Integer) row[i++]);
            userBean.setUserId((String) row[i++]);
            userBean.setEmail((String) row[i++]);
            userBean.setUsername((String) row[i++]);
            userBean.setGroupId((String) row[i++]);
            userBean.setPhone((String) row[i++]);
            userBean.setCompanyId((String) row[i++]);
            userBean.setBranchId((String) row[i++]);
            userBean.setCompanyName((String) row[i++]);
            userBean.setBranchName((String) row[i++]);

            list.add(userBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<UserBean> getInActiveBAUserBeanList(SessionFactory curSession, String companyId) {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.enabled,a.userId,a.email,a.username,a.groupId,a.phone,a.companyId,a.branchId,b.name,c.branchName from User a,Company b, Branch c where a.enabled=0 and a.isBranchUser='1' and b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id and a.companyId='" + companyId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object[] row = results.next();

            userBean.setId((Integer) row[i++]);
            userBean.setEnabled((Integer) row[i++]);
            userBean.setUserId((String) row[i++]);
            userBean.setEmail((String) row[i++]);
            userBean.setUsername((String) row[i++]);
            userBean.setGroupId((String) row[i++]);
            userBean.setPhone((String) row[i++]);
            userBean.setCompanyId((String) row[i++]);
            userBean.setBranchId((String) row[i++]);
            userBean.setCompanyName((String) row[i++]);
            userBean.setBranchName((String) row[i++]);

            list.add(userBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<UserBean> getUserBeanList(SessionFactory curSession, String companyId, String branchId) {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.enabled,a.userId,a.email,a.username,a.groupId,a.phone,a.companyId,a.branchId,b.name,c.branchName from User a,Company b, Branch c where b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id  and a.companyId='" + companyId + "' and a.branchId='" + branchId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object[] row = results.next();

            userBean.setId((Integer) row[i++]);
            userBean.setEnabled((Integer) row[i++]);
            userBean.setUserId((String) row[i++]);
            userBean.setEmail((String) row[i++]);
            userBean.setUsername((String) row[i++]);
            userBean.setGroupId((String) row[i++]);
            userBean.setPhone((String) row[i++]);
            userBean.setCompanyId((String) row[i++]);
            userBean.setBranchId((String) row[i++]);
            userBean.setCompanyName((String) row[i++]);
            userBean.setBranchName((String) row[i++]);

            list.add(userBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<LoanRequestBean> getLoanRequestList(String branchId) {
        ArrayList<LoanRequestBean> list = null;
        LoanRequestBean requestBean = null;
        Query query = sessionFactory.getCurrentSession().createQuery("select a.id,a.loanCaseId,a.memberNo,b.memberNo,b.compmemberId,concat(b.surname ,' ',b.firstname), a.loanType,a.loanStatus,a.requestStatus,a.branchId,c.branchName,"
                + " a.companyId,a.productRate,a.requestedAmount,a.approvedAmount,a.appliedRate,a.requestBy,a.requestDate, "
                + " a.approvedBy,a.approvalComment,a.approvalDate,a.disburseDate,a.disburseBy,a.proposedCommencementDate,a.actualCommencementDate, "
                + " a.loanIntTotal,a.duration,a.noOfInstallments,a.balancePrincipal,a.balanceInterest,a.balanceTotal,a.lastRepaymentDate, "
                + " a.repayFrequency,a.repayAmount,a.totPenaltyDue,a.totPenaltyPaid,a.loanAccountNumber,a.interestType "
                + " from LoanRequest a,MemberView b, Branch c where a.memberNo=b.memberId and c.id=a.branchId and a.branchId='" + branchId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<LoanRequestBean>();
            }
            requestBean = new LoanRequestBean();

            Object[] row = results.next();

            requestBean.setId((Integer) row[i++]);
            requestBean.setLoanCaseId((String) row[i++]);
            requestBean.setMemberNo((String) row[i++]);

            requestBean.setMemberNoStr((String) row[i++]);
            requestBean.setCoyMemberIdStr((String) row[i++]);

            requestBean.setMemberName((String) row[i++]);

            requestBean.setLoanType((String) row[i++]);
            requestBean.setLoanStatus((String) row[i++]);
            requestBean.setRequestStatus((String) row[i++]);
            requestBean.setBranchId((String) row[i++]);
            requestBean.setBranchName((String) row[i++]);
            requestBean.setCompanyId((String) row[i++]);
            requestBean.setProductRate((Double) row[i++]);

            requestBean.setRequestedAmount((Double) row[i++]);
            requestBean.setApprovedAmount((Double) row[i++]);
            requestBean.setAppliedRate((Double) row[i++]);

            requestBean.setRequestBy((String) row[i++]);
            requestBean.setRequestDate((Date) row[i++]);

            if ("E".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Entered");
            } else if ("A".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Approved");
            } else if ("D".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Disbursed");
            } else if ("C".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Closed");
            } else if ("R".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Rejected");
            }else if ("X".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Cancelled");
            }

            list.add(requestBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<LoanRequestBean> getLoanRequestList(String branchId, String requestStatus) {
        ArrayList<LoanRequestBean> list = null;
        LoanRequestBean requestBean = null;
        Query query = sessionFactory.getCurrentSession().createQuery("select a.id,a.loanCaseId,a.memberNo,b.memberNo,b.compmemberId,concat(b.surname ,' ',b.firstname), a.loanType,a.loanStatus,a.requestStatus,a.branchId,c.branchName,"
                + " a.companyId,a.productRate,a.requestedAmount,a.approvedAmount,a.appliedRate,a.requestBy,a.requestDate, "
                + " a.approvedBy,a.approvalComment,a.approvalDate,a.disburseDate,a.disburseBy,a.proposedCommencementDate,a.actualCommencementDate, "
                + " a.loanIntTotal,a.duration,a.noOfInstallments,a.balancePrincipal,a.balanceInterest,a.balanceTotal,a.lastRepaymentDate, "
                + " a.repayFrequency,a.repayAmount,a.totPenaltyDue,a.totPenaltyPaid,a.loanAccountNumber,a.interestType "
                + " from LoanRequest a,MemberView b, Branch c where a.memberNo=b.memberId and c.id=a.branchId and  a.requestStatus='" + requestStatus.trim() + "' and a.branchId='" + branchId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<LoanRequestBean>();
            }
            requestBean = new LoanRequestBean();

            Object[] row = results.next();

            requestBean.setId((Integer) row[i++]);
            requestBean.setLoanCaseId((String) row[i++]);
            requestBean.setMemberNo((String) row[i++]);

            requestBean.setMemberNoStr((String) row[i++]);
            requestBean.setCoyMemberIdStr((String) row[i++]);

            requestBean.setMemberName((String) row[i++]);

            requestBean.setLoanType((String) row[i++]);
            requestBean.setLoanStatus((String) row[i++]);
            requestBean.setRequestStatus((String) row[i++]);
            requestBean.setBranchId((String) row[i++]);
            requestBean.setBranchName((String) row[i++]);
            requestBean.setCompanyId((String) row[i++]);
            requestBean.setProductRate((Double) row[i++]);
            requestBean.setRequestedAmount((Double) row[i++]);

            requestBean.setApprovedAmount((Double) row[i++]);
            requestBean.setAppliedRate((Double) row[i++]);

            requestBean.setRequestBy((String) row[i++]);
            requestBean.setRequestDate((Date) row[i++]);

            if ("E".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Entered");
            } else if ("A".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Approved");
            } else if ("D".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Disbursed");
            } else if ("C".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Closed");
            } else if ("R".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Rejected");
            }

            list.add(requestBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<LoanRequestBean> getLoanRequestListByLoanStatus(String branchId, String loanStatus) {
        ArrayList<LoanRequestBean> list = null;
        LoanRequestBean requestBean = null;
        Query query = sessionFactory.getCurrentSession().createQuery("select a.id,a.loanCaseId,a.memberNo,b.memberNo,b.compmemberId,concat(b.surname ,' ',b.firstname), a.loanType,a.loanStatus,a.requestStatus,a.branchId,c.branchName,"
                + " a.companyId,a.productRate,a.requestedAmount,a.approvedAmount,a.appliedRate,a.requestBy,a.requestDate, "
                + " a.approvedBy,a.approvalComment,a.approvalDate,a.disburseDate,a.disburseBy,a.proposedCommencementDate,a.actualCommencementDate, "
                + " a.loanIntTotal,a.duration,a.noOfInstallments,a.balancePrincipal,a.balanceInterest,a.balanceTotal,a.lastRepaymentDate, "
                + " a.repayFrequency,a.repayAmount,a.totPenaltyDue,a.totPenaltyPaid,a.loanAccountNumber,a.interestType "
                + " from LoanRequest a,MemberView b, Branch c where a.memberNo=b.memberId and c.id=a.branchId and  a.loanStatus='" + loanStatus.trim() + "' and a.branchId='" + branchId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<LoanRequestBean>();
            }
            requestBean = new LoanRequestBean();

            Object[] row = results.next();

            requestBean.setId((Integer) row[i++]);
            requestBean.setLoanCaseId((String) row[i++]);
            requestBean.setMemberNo((String) row[i++]);

            requestBean.setMemberNoStr((String) row[i++]);
            requestBean.setCoyMemberIdStr((String) row[i++]);

            requestBean.setMemberName((String) row[i++]);

            requestBean.setLoanType((String) row[i++]);
            requestBean.setLoanStatus((String) row[i++]);
            requestBean.setRequestStatus((String) row[i++]);
            requestBean.setBranchId((String) row[i++]);
            requestBean.setBranchName((String) row[i++]);
            requestBean.setCompanyId((String) row[i++]);
            requestBean.setProductRate((Double) row[i++]);
            requestBean.setRequestedAmount((Double) row[i++]);

            requestBean.setApprovedAmount((Double) row[i++]);
            requestBean.setAppliedRate((Double) row[i++]);

            requestBean.setRequestBy((String) row[i++]);
            requestBean.setRequestDate((Date) row[i++]);

            if ("E".equalsIgnoreCase(requestBean.getLoanStatus())) {
                requestBean.setLoanStatusDesc("Entered");
            } else if ("A".equalsIgnoreCase(requestBean.getLoanStatus())) {
                requestBean.setLoanStatusDesc("Approved");
            } else if ("D".equalsIgnoreCase(requestBean.getLoanStatus())) {
                requestBean.setLoanStatusDesc("Disbursed");
            } else if ("C".equalsIgnoreCase(requestBean.getLoanStatus())) {
                requestBean.setLoanStatusDesc("Closed");
            } else if ("R".equalsIgnoreCase(requestBean.getLoanStatus())) {
                requestBean.setLoanStatusDesc("Rejected");
            }

            list.add(requestBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public LoanRequestBean getLoanRequestDetails(String loanCaseId) {
        LoanRequestBean requestBean = null;
        Query query = sessionFactory.getCurrentSession().createQuery("select a.id,a.loanCaseId,a.memberNo,b.memberNo,b.compmemberId,concat(b.surname ,' ',b.firstname), a.loanType,a.loanStatus,a.requestStatus,a.branchId,c.branchName,"
                + " a.companyId,a.productRate,a.requestedAmount,a.approvedAmount,a.appliedRate,a.requestBy,a.requestDate, "
                + " a.approvedBy,a.approvalComment,a.approvalDate,a.disburseDate,a.disburseBy,a.proposedCommencementDate,a.actualCommencementDate, "
                + " a.loanIntTotal,a.duration,a.noOfInstallments,a.balancePrincipal,a.balanceInterest,a.balanceTotal,a.lastRepaymentDate, "
                + " a.repayFrequency,a.repayAmount,a.totPenaltyDue,a.totPenaltyPaid,a.loanAccountNumber,a.interestType "
                + " from LoanRequest a,MemberView b, Branch c where a.memberNo=b.memberId and c.id=a.branchId and a.loanCaseId='" + loanCaseId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        if (results.hasNext()) {
            i = 0;
            requestBean = new LoanRequestBean();

            Object[] row = results.next();

            requestBean.setId((Integer) row[i++]);
            requestBean.setLoanCaseId((String) row[i++]);
            requestBean.setMemberNo((String) row[i++]);

            requestBean.setMemberNoStr((String) row[i++]);
            requestBean.setCoyMemberIdStr((String) row[i++]);

            requestBean.setMemberName((String) row[i++]);

            requestBean.setLoanType((String) row[i++]);
            requestBean.setLoanStatus((String) row[i++]);
            requestBean.setRequestStatus((String) row[i++]);
            requestBean.setBranchId((String) row[i++]);
            requestBean.setBranchName((String) row[i++]);
            requestBean.setCompanyId((String) row[i++]);
            requestBean.setProductRate((Double) row[i++]);
            requestBean.setRequestedAmount((Double) row[i++]);

            requestBean.setApprovedAmount((Double) row[i++]);
            requestBean.setAppliedRate((Double) row[i++]);

            requestBean.setRequestBy((String) row[i++]);
            requestBean.setRequestDate((Date) row[i++]);

            if ("E".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Entered");
            } else if ("A".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Approved");
            } else if ("D".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Disbursed");
            } else if ("C".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Closed");
            } else if ("R".equalsIgnoreCase(requestBean.getRequestStatus())) {
                requestBean.setLoanStatusDesc("Rejected");
            }
        }

        return requestBean;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<UserBean> getUserBeanListByCoopByGroup(SessionFactory curSession, String companyId, String userGroup) {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.enabled,a.userId,a.email,a.username,a.groupId,a.phone,a.companyId,a.branchId,b.name,c.branchName from User a,Company b, Branch c where a.groupId='" + userGroup + "' and b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id  and a.companyId='" + companyId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object[] row = results.next();

            userBean.setId((Integer) row[i++]);
            userBean.setEnabled((Integer) row[i++]);
            userBean.setUserId((String) row[i++]);
            userBean.setEmail((String) row[i++]);
            userBean.setUsername((String) row[i++]);
            userBean.setGroupId((String) row[i++]);
            userBean.setPhone((String) row[i++]);
            userBean.setCompanyId((String) row[i++]);
            userBean.setBranchId((String) row[i++]);
            userBean.setCompanyName((String) row[i++]);
            userBean.setBranchName((String) row[i++]);

            list.add(userBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<UserBean> getUserBeanListByGroup(SessionFactory curSession, String userGroup) {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.enabled,a.userId,a.email,a.username,a.groupId,a.phone,a.companyId,a.branchId,b.name,c.branchName from User a,Company b, Branch c where a.groupId='" + userGroup + "' and b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object[] row = results.next();

            userBean.setId((Integer) row[i++]);
            userBean.setEnabled((Integer) row[i++]);
            userBean.setUserId((String) row[i++]);
            userBean.setEmail((String) row[i++]);
            userBean.setUsername((String) row[i++]);
            userBean.setGroupId((String) row[i++]);
            userBean.setPhone((String) row[i++]);
            userBean.setCompanyId((String) row[i++]);
            userBean.setBranchId((String) row[i++]);
            userBean.setCompanyName((String) row[i++]);
            userBean.setBranchName((String) row[i++]);

            list.add(userBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<UserBean> getUserBeanList(SessionFactory curSession, String companyId, String branchId, int accessId) {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.enabled,a.userId,a.email,a.username,a.groupId,a.phone,a.companyId,a.branchId,b.name,c.branchName from User a,Company b, Branch c where b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id  and a.companyId='" + companyId + "' and a.branchId='" + branchId + "'"
                + " and a.groupId in (select code from UserGroup where accessId=" + accessId + " and companyId=" + companyId + " and branchId=" + branchId + ")");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object[] row = results.next();

            userBean.setId((Integer) row[i++]);
            userBean.setEnabled((Integer) row[i++]);
            userBean.setUserId((String) row[i++]);
            userBean.setEmail((String) row[i++]);
            userBean.setUsername((String) row[i++]);
            userBean.setGroupId((String) row[i++]);
            userBean.setPhone((String) row[i++]);
            userBean.setCompanyId((String) row[i++]);
            userBean.setBranchId((String) row[i++]);
            userBean.setCompanyName((String) row[i++]);
            userBean.setBranchName((String) row[i++]);

            list.add(userBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<UserBean> getActiveBranchUsers(String branchId) {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;
        Query query = sessionFactory.getCurrentSession().createQuery("select a.id,a.enabled,a.userId,a.email,a.username,a.groupId,a.phone,a.companyId,a.branchId,b.name,c.branchName from User a,Company b, Branch c where b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id and a.branchId='" + branchId + "'"
                + " and a.enabled=1 and a.isBranchUser=1");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object[] row = results.next();

            userBean.setId((Integer) row[i++]);
            userBean.setEnabled((Integer) row[i++]);
            userBean.setUserId((String) row[i++]);
            userBean.setEmail((String) row[i++]);
            userBean.setUsername((String) row[i++]);
            userBean.setGroupId((String) row[i++]);
            userBean.setPhone((String) row[i++]);
            userBean.setCompanyId((String) row[i++]);
            userBean.setBranchId((String) row[i++]);
            userBean.setCompanyName((String) row[i++]);
            userBean.setBranchName((String) row[i++]);

            list.add(userBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<FiscalPeriodBean> getFiscalPeriodBeanList(SessionFactory curSession, String companyId, String branchId) {
        ArrayList<FiscalPeriodBean> list = null;
        FiscalPeriodBean fpBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.year,a.noOfPeriods,a.companyId,a.branchId,b.companyName,b.branchName from FiscalPeriod a,Company b, Branch c where b.id=c.companyId and a.companyId=b.id  and a.branchId=c.id  and a.companyId='" + companyId + "' and a.branchId='" + branchId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<FiscalPeriodBean>();
            }
            fpBean = new FiscalPeriodBean();

            Object[] row = results.next();

            fpBean.setId((Integer) row[i++]);
            fpBean.setYear((Integer) row[i++]);
            fpBean.setNoOfPeriods((Integer) row[i++]);
            fpBean.setCompanyId((String) row[i++]);
            fpBean.setBranchId((String) row[i++]);
            fpBean.setCompanyName((String) row[i++]);
            fpBean.setBranchName((String) row[i++]);

            list.add(fpBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<FiscalPeriodBean> getFiscalPeriodBeanList(SessionFactory curSession, String branchId) {
        ArrayList<FiscalPeriodBean> list = null;
        FiscalPeriodBean fpBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.year,a.noOfPeriods,a.companyId,a.branchId,b.name,c.branchName,a.active from FiscalPeriod a,Company b, Branch c where b.id=a.companyId and c.companyId=b.id  and a.branchId=c.id and a.branchId='" + branchId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<FiscalPeriodBean>();
            }
            fpBean = new FiscalPeriodBean();

            Object[] row = results.next();

            fpBean.setId((Integer) row[i++]);
            fpBean.setYear((Integer) row[i++]);
            fpBean.setNoOfPeriods((Integer) row[i++]);
            fpBean.setCompanyId((String) row[i++]);
            fpBean.setBranchId((String) row[i++]);
            fpBean.setCompanyName((String) row[i++]);
            fpBean.setBranchName((String) row[i++]);
            fpBean.setActive((String) row[i++]);

            list.add(fpBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<FiscalPeriodItemBean> getFiscalPeriodItemBeanList(SessionFactory curSession, String branchId, int year) {
        ArrayList<FiscalPeriodItemBean> list = null;
        FiscalPeriodItemBean fpiBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.year,a.periodId,a.fiscalPeriodId,a.periodStart,a.periodEnd from FiscalPeriodItem a where a.fiscalPeriodId  in (select id from FiscalPeriod where branchId='" + branchId + "') and a.year='" + year + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<FiscalPeriodItemBean>();
            }
            fpiBean = new FiscalPeriodItemBean();

            Object[] row = results.next();

            fpiBean.setId((Integer) row[i++]);
            fpiBean.setYear((Integer) row[i++]);
            fpiBean.setPeriodId((Integer) row[i++]);
            fpiBean.setFiscalPeriodId((Integer) row[i++]);
            fpiBean.setPeriodStart((java.util.Date) row[i++]);
            fpiBean.setPeriodEnd((java.util.Date) row[i++]);

            list.add(fpiBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<FiscalPeriodItemBean> getFiscalPeriodItemBeanList_(SessionFactory curSession, String branchId, int year) {
        ArrayList<FiscalPeriodItemBean> list = null;
        FiscalPeriodItemBean fpiBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.year,a.periodId,a.fiscalPeriodId,a.periodStart,a.periodEnd,b.companyName,b.branchName from FiscalPeriodItem a where a.fiscalPeriodId  in (select id from FiscalPeriod where branchId='" + branchId + "') and a.year='" + year + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<FiscalPeriodItemBean>();
            }
            fpiBean = new FiscalPeriodItemBean();

            Object[] row = results.next();

            fpiBean.setId((Integer) row[i++]);
            fpiBean.setYear((Integer) row[i++]);
            fpiBean.setPeriodId((Integer) row[i++]);
            fpiBean.setFiscalPeriodId((Integer) row[i++]);
            fpiBean.setPeriodStart((java.util.Date) row[i++]);
            fpiBean.setPeriodEnd((java.util.Date) row[i++]);

            list.add(fpiBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<CountryAddressFilterBean> getCountryAddressFilterBeanList(SessionFactory curSession, String countryId) {
        ArrayList<CountryAddressFilterBean> list = null;
        CountryAddressFilterBean cBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.addrFieldName,a.addrFieldIndx,b.countryCode,b.countryName,a.countryId from CountryAddressFilter a,Country b where b.id=a.countryId and a.countryId='" + countryId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<CountryAddressFilterBean>();
            }
            cBean = new CountryAddressFilterBean();

            Object[] row = results.next();

            cBean.setId((Integer) row[i++]);
            cBean.setAddrFieldIndx((String) row[i++]);
            cBean.setAddrFieldName((String) row[i++]);
            cBean.setCountryCode((String) row[i++]);
            cBean.setCountryName((String) row[i++]);
            cBean.setCountryId((String) row[i++]);

            list.add(cBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<CountryAddressFilterBean> getCountryAddressFilterBeanList(SessionFactory curSession) {
        ArrayList<CountryAddressFilterBean> list = null;
        CountryAddressFilterBean cBean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.addrFieldName,a.addrFieldIndx,b.countryCode,b.countryName,a.countryId from CountryAddressFilter a,Country b where b.id=a.countryId");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<CountryAddressFilterBean>();
            }
            cBean = new CountryAddressFilterBean();

            Object[] row = results.next();

            cBean.setId((Integer) row[i++]);
            cBean.setAddrFieldIndx((String) row[i++]);
            cBean.setAddrFieldName((String) row[i++]);
            cBean.setCountryCode((String) row[i++]);
            cBean.setCountryName((String) row[i++]);
            cBean.setCountryId((String) row[i++]);

            list.add(cBean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<BranchBean> getBranchList_(SessionFactory curSession, String companyId) {
        ArrayList<BranchBean> list = null;
        BranchBean bean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.branchCode,a.branchName,a.email,a.companyId,b.name from Branch a,Company b where b.id=a.companyId and a.id='" + companyId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<BranchBean>();
            }
            bean = new BranchBean();

            Object[] row = results.next();

            bean.setId((Integer) row[i++]);
            bean.setBranchCode((String) row[i++]);
            bean.setBranchName((String) row[i++]);
            bean.setEmail((String) row[i++]);
            bean.setCompanyId((String) row[i++]);
            bean.setCompanyName((String) row[i++]);

            list.add(bean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<BranchBean> getBranchList(SessionFactory curSession, String companyId) {
        ArrayList<BranchBean> list = null;
        BranchBean bean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.branchCode,a.branchName,a.email,a.companyId,b.name from Branch a,Company b where b.id=a.companyId and b.id='" + companyId + "'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<BranchBean>();
            }
            bean = new BranchBean();

            Object[] row = results.next();

            bean.setId((Integer) row[i++]);
            bean.setBranchCode((String) row[i++]);
            bean.setBranchName((String) row[i++]);
            bean.setEmail((String) row[i++]);
            bean.setCompanyId((String) row[i++]);
            bean.setCompanyName((String) row[i++]);

            list.add(bean);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<BranchBean> getInActiveBranchList(SessionFactory curSession) {
        ArrayList<BranchBean> list = null;
        BranchBean bean = null;
        Query query = curSession.getCurrentSession().createQuery("select a.id,a.branchCode,a.branchName,a.email,a.companyId,b.name from Branch a,Company b where b.id=a.companyId and a.active='N' and a.deleted='N'");

        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<BranchBean>();
            }
            bean = new BranchBean();

            Object[] row = results.next();

            bean.setId((Integer) row[i++]);
            bean.setBranchCode((String) row[i++]);
            bean.setBranchName((String) row[i++]);
            bean.setEmail((String) row[i++]);
            bean.setCompanyId((String) row[i++]);
            bean.setCompanyName((String) row[i++]);

            list.add(bean);
        }

        return list;
    }

    public String getContraAccount(String productCode, String prodTypeCode, String companyId, String branchId) {
        String str = "";
        String sql = "select a.gl_account_number from productaccount a, products b where a.company_id=" + companyId + " and a.branch_id=" + branchId + " and a.product_id=b.id and a.product_account_type_code='" + prodTypeCode.trim() + "' and b.code='" + productCode.trim() + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        str = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        return str;
    }

    public String getBranchSetting(String branchId, String companyId) {
        String settingStr = null;
        String query1 = " SELECT value from `settings` where setting='ACCOUNTINGMETHOD' and branch='" + branchId + "' and companyId='" + companyId + "' ";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(query1);
        settingStr = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();

        return settingStr;
    }

    public String getTransactionNaration(String tranCode) {
        String narrative = "";
        String sql = "select a.narrative from txncodes a  where a.transactioncode='" + tranCode.trim() + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        narrative = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString().trim();
        return narrative;
    }

    public String getContraAccount(SessionFactory sessionFactory1, String productCode, String prodTypeCode, String companyId, String branchId) {
        String str = "";
        String sql = "select a.gl_account_number from productaccount a, products b where a.company_id=" + companyId + " and a.branch_id=" + branchId + " and a.product_id=b.id and a.product_account_type_code='" + prodTypeCode.trim() + "' and b.code='" + productCode.trim() + "'";
        Query query = sessionFactory1.getCurrentSession().createSQLQuery(sql);

        str = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        return str;
    }

    public void markBatchAsPaid(String batchId) {
        sessionFactory.getCurrentSession().createQuery("update FileUpload set paymentStatus='P',processingStatus='P' where batchId = " + batchId).executeUpdate();
    }

    public void updateFailedLogonCount(String email) {
        sessionFactory.getCurrentSession().createQuery("update User set failedLogon=failedLogon+1  where email='" + email.trim() + "'");
    }

    public String padValue(String str, String padE, int padLenth, boolean LEFTPAD) {
        if (str == null) {
            str = "";
        } else {
            str = str.trim();
        }

        while (str.length() < padLenth) {
            str = LEFTPAD ? padE + str : str + padE;
        }

        return str;
    }

    public int getFailedLogonCount(String email) {
        int counter = 0;
        String query1 = " SELECT failedLogon from `Users` where email='" + email.trim() + "'";

        Query query = sessionFactory.getCurrentSession().createQuery(query1);
        counter = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());

        return counter;
    }

    public void disableUser(String email) {
        sessionFactory.getCurrentSession().createQuery("update User set enable=0 where email='" + email.trim() + "'");
    }

    public void disableUser(String email, boolean setFLF) {
        if (setFLF) {
            sessionFactory.getCurrentSession().createQuery("update User set enable=0,failedLogon=failedLogon+1  where email='" + email.trim() + "'");
        } else {
            sessionFactory.getCurrentSession().createQuery("update User set enable=0  where email='" + email.trim() + "'");
        }
    }

    public void enableUser(String email) {
        sessionFactory.getCurrentSession().createQuery("update User set enable=1 where email='" + email.trim() + "'");
    }

    public LoanRequest getLoanByCaseID(String loanCaseId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from LoanRequest where loanCaseId='" + loanCaseId + "'");
        return query.list() == null ? null : (LoanRequest) query.list().get(0);
    }

    
     public void UpdateNewLoanGuarantor(String ids) {
        //System.out.println("running update of new loan guarantor now...");
       // System.out.println("i am here with: " + uniqLoanCase + ", replaced guarantor Id: " + replacedGuarantorId);
        String query = "update loan_guarantor_change set approved='Y' where id='" + ids + "' ";
        sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
        System.out.println("update of new loan guarantors completed successfully..");

    }

    /**
     * updates the original loan guarantor
     *
     * @param uniqLoanCase the loan case Id
     * @param newGuarantorId the new guarantor Id
     * @param replacedGuarantorId the old guarantor Id
     */
    public void UpdateOriginalLoanGuarantor(String uniqLoanCase, String newGuarantorId, String replacedGuarantorId) {
        System.out.println("running update of original loan guarantor now...");
        System.out.println("i am here with: " + uniqLoanCase + ", replaced guarantor Id: " + replacedGuarantorId + ", new guarantor Id: " + newGuarantorId);
    
        String query2 = "update loan_guarantor set guarantor_No='" + newGuarantorId + "' where guarantor_No='" + replacedGuarantorId + "' and loan_Case_Id='" + uniqLoanCase + "'";
        sessionFactory.getCurrentSession().createSQLQuery(query2).executeUpdate();
       System.out.println("update on the loan guarantor table completed successfully..");
    }

    
    private List<UserRoleBean> prepareListofUserRoleBean(List<UserRole> userRoles) {
        List<UserRoleBean> beans = null;

        if (userRoles != null && !userRoles.isEmpty()) {
            beans = new ArrayList<UserRoleBean>();
            UserRoleBean bean = null;

            for (UserRole userRole : userRoles) {
                bean = new UserRoleBean();

                bean.setRoleName(userRole.getRoleName());
                bean.setId(userRole.getId());
                //bean.setGroupId(userRole.getGroupId());
                bean.setRoleCode(userRole.getRoleCode());

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

    private List<CountryBean> prepareListofCountryBean(List<Country> countrys) {
        List<CountryBean> beans = null;

        if (countrys != null && !countrys.isEmpty()) {
            beans = new ArrayList<CountryBean>();
            CountryBean country = null;

            for (Country item : countrys) {
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

                beans.add(country);
            }
        }

        return beans;
    }

    private List<UserGroupBean> prepareListofBean(List<UserGroup> userGroups) {
        List<UserGroupBean> beans = null;

        if (userGroups != null && !userGroups.isEmpty()) {
            beans = new ArrayList<UserGroupBean>();
            UserGroupBean userGroup = null;

            for (UserGroup userGroupBean : userGroups) {
                userGroup = new UserGroupBean();

                userGroup.setId(userGroupBean.getId());
                userGroup.setActive(userGroupBean.getActive());
                userGroup.setDeleted(userGroupBean.getDeleted());
                userGroup.setDescription(userGroupBean.getDescription());

                /*userGroup.setCreatedBy(userGroupBean.getCreatedBy());
                 userGroup.setCreationDate(userGroupBean.getCreationDate());
                 userGroup.setLastModifiedBy(userGroupBean.getLastModifiedBy());
                 userGroup.setLastModificationDate(userGroupBean.getLastModificationDate());*/

                beans.add(userGroup);
            }
        }

        return beans;
    }

    public double getLoanPenalty(LoanRequest loan, Product product, int defaultDays) {
        double penalty = 0.0;

        if (defaultDays > 0) {
            String formula = product.getPenaltyFormula();
            double outstandingPrincipal = loan.getBalancePrincipal();
            double outstandingInterest = loan.getBalanceInterest();
            double penaltyRate = product.getPenalty();
            String formattedFormula = formula;

            if (formula.trim().contains("OP")) {
                formattedFormula = formattedFormula.replace("OP", new java.text.DecimalFormat("#.00").format(outstandingPrincipal));
            } else if (formula.trim().contains("OI")) {
                formattedFormula = formattedFormula.replace("OI", new java.text.DecimalFormat("#.00").format(outstandingInterest));
            } else if (formula.trim().contains("PR")) {
                formattedFormula = formattedFormula.replace("PR", new java.text.DecimalFormat("#.00").format(penaltyRate));
            } else if (formula.trim().contains("NDD")) {
                formattedFormula = formattedFormula.replace("NDD", new java.text.DecimalFormat("#.00").format(defaultDays));
            }

            try {
               // System.out.println("formattedFormula for method 1 :: " + formattedFormula);
                penalty = eval(formattedFormula);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return penalty;
    }

    public double getLoanPenalty(LoanRepaymentSchedule schedule, Product product, int defaultDays) {
        double penalty = 0.0;       
        //System.out.println("defaultDays :: " + defaultDays);
        if(product.getHasPenalty() == 1){
            
        if (defaultDays > 0) {
            System.out.println("Default days is greater than 0 :: ");
            String formula = product.getPenaltyFormula();
            double outstandingPrincipal = schedule.getAmountPrincipal();
            double outstandingInterest = schedule.getAmountInterest();
            double penaltyRate = product.getPenalty();
            String formattedFormula = formula;

            if (formula.trim().contains("OP")) {
                formattedFormula = formattedFormula.replace("OP", new java.text.DecimalFormat("#.00").format(outstandingPrincipal));
               // System.out.println("formattedFormula @ OP :: " + formattedFormula);
            } if (formula.trim().contains("OI")) {
                formattedFormula = formattedFormula.replace("OI", new java.text.DecimalFormat("#.00").format(outstandingInterest));
               // System.out.println("formattedFormula @ OIP :: " + formattedFormula);
            } if (formula.trim().contains("PR")) {
                formattedFormula = formattedFormula.replace("PR", new java.text.DecimalFormat("#.00").format(penaltyRate));
               // System.out.println("formattedFormula @ PR :: " + formattedFormula);
            } if (formula.trim().contains("NDD")) {
                formattedFormula = formattedFormula.replace("NDD", new java.text.DecimalFormat("#.00").format(defaultDays));
             //   System.out.println("formattedFormula @ NDD :: " + formattedFormula);
            }
            
            try {
                /*
                String aa  = new java.text.DecimalFormat("#.00").format(outstandingPrincipal);
                System.out.println("aa :: aa :: " + aa);
                System.out.println("OP :: outstandingPrincipal :: " + outstandingPrincipal);
                System.out.println("OI :: outstandingInterest :: " + outstandingInterest);
                System.out.println("PR :: penaltyRate :: " + penaltyRate);
                System.out.println("NDD :: defaultDays :: " + defaultDays);

                System.out.println("formattedFormula for method 2 :: " + formattedFormula);
                * */
                penalty = eval(formattedFormula);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        }

        return penalty;
    }

    /**
     * *******************************************************************************************************************************************
     * LN Disburs MemNo: %s , AcNo: %s , ID: %s LN Repmnt MemNo: %s to AcNo: %s
     * , ID: %s LN Int MemNo: %s AcNo: %s , ID: %s
     *
     * UPDATE `easycoopfin`.`txncodes` SET `Narrative` = 'LN Disburs MemNo: %s ,
     * AcNo: %s , ID: %s' WHERE `txncodes`.`transactioncode` = 'LDD'; UPDATE
     * `easycoopfin`.`txncodes` SET `Narrative` = 'LN Repmnt MemNo: %s to AcNo:
     * %s , ID: %s' WHERE `txncodes`.`transactioncode` = 'LOR'; UPDATE
     * `easycoopfin`.`txncodes` SET `Narrative` = 'LN Int MemNo: %s AcNo: %s ,
     * ID: %s' WHERE `txncodes`.`transactioncode` = 'LIA';
	 *******************************************************************************************************************************************
     */
    //This UTILITY is required to parse Formulas
    public static double eval(final String str) {
        class Parser {

            int pos = -1, c;

            void eatChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            void eatSpace() {
                while (Character.isWhitespace(c)) {
                    eatChar();
                }
            }

            double parse() {
                eatChar();
                double v = parseExpression();
                if (c != -1) {
                    throw new RuntimeException("Unexpected: " + (char) c);
                }
                return v;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor | term brackets
            // factor = brackets | number | factor `^` factor
            // brackets = `(` expression `)`
            double parseExpression() {
                double v = parseTerm();
                for (;;) {
                    eatSpace();
                    if (c == '+') { // addition
                        eatChar();
                        v += parseTerm();
                    } else if (c == '-') { // subtraction
                        eatChar();
                        v -= parseTerm();
                    } else {
                        return v;
                    }
                }
            }

            double parseTerm() {
                double v = parseFactor();
                for (;;) {
                    eatSpace();
                    if (c == '/') { // division
                        eatChar();
                        v /= parseFactor();
                    } else if (c == '*' || c == '(') { // multiplication
                        if (c == '*') {
                            eatChar();
                        }
                        v *= parseFactor();
                    } else {
                        return v;
                    }
                }
            }

            double parseFactor() {
                double v;
                boolean negate = false;
                eatSpace();
                if (c == '+' || c == '-') { // unary plus & minus
                    negate = c == '-';
                    eatChar();
                    eatSpace();
                }
                if (c == '(') { // brackets
                    eatChar();
                    v = parseExpression();
                    if (c == ')') {
                        eatChar();
                    }
                } else { // numbers
                    StringBuilder sb = new StringBuilder();
                    while ((c >= '0' && c <= '9') || c == '.') {
                        sb.append((char) c);
                        eatChar();
                    }
                    if (sb.length() == 0) {
                        throw new RuntimeException("Unexpected: " + (char) c);
                    }
                    v = Double.parseDouble(sb.toString());
                }
                eatSpace();
                if (c == '^') { // exponentiation
                    eatChar();
                    v = Math.pow(v, parseFactor());
                }
                if (negate) {
                    v = -v; // unary minus is applied after exponentiation; e.g. -3^2=-9
                }
                return v;
            }
        }

        return new Parser().parse();
    }

    public static void main(String args[]) {
        System.out.println(eval("2^3 - 3 + 1 + 3 * ((4+4*4)/2) / 5 + -5"));
    }
    
    public String getMemberNofromId(String memberId) {
        String memberno = "";
        String sql = " select member_no from member where member_id= ' " + memberId + "'";
        //System.out.println("getMemberno  SQL=: " + sql);

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        memberno = (query == null || query.list().isEmpty()) ? padValue(memberId, "0", 6, true) : query.list().get(0).toString();
        return memberno;
    }
  public  void saveMemberGroupSettings(String id,String companyid,String branchid){
     String sql = "insert into settings (setting,value,branch,companyid,Display) VALUES ('MEMBERGROUP','"+id+"','"+branchid+"','"+companyid+"','N')";
    sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
  }
    public  void deleteMemberGroupSettings(String companyid){
     String sql = "delete from  settings where  companyid='"+companyid+"' and setting='MEMBERGROUP'";
    sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
  }
    public  void deleteMemberGroupSettingById(int id){
     String sql = "delete from  settings where value='"+id+"'  and setting='MEMBERGROUP'";
    sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
  }
  public boolean checkifMemberGroupExist(int id,int companyid,int branchid){
        boolean membergrouped= false;
        String sqlCount = "SELECT count(*) FROM settings where setting='MEMBERGROUP' and value='"+id+"' and branch='"+branchid+"' and companyid='"+companyid+"'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlCount);
        String result = query.list().get(0).toString();
        int result_int = Integer.parseInt(result);
        
        if(result_int>0){
           
            membergrouped=true;
            return  membergrouped;
           }
       
         return membergrouped;
}
 
     public ArrayList<UserBean> getRegistrarAdminMails() {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;

        String Sql = "SELECT email from users where branch='1' and companyid='1'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object row = results.next();
            userBean.setEmail((String) row);

            list.add(userBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the email list " + list.size());
            System.out.println("the  first mail" + list.get(0).getEmail().toString());
        }

        return list;
    }   
   public void UpdateMemberPhoneNumber(int id,String fieldvalue,String memberno,int branchid,int companyid,String loggedUser) {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date date = new Date();
           String currentdate=dateFormat.format(date);
     String query = "update memberupdatepending set approved='Y', approvedby='"+loggedUser+"', approved_date='"+currentdate+"' where id="+id+"";
        sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
        
     String query1 = "update member set phone_no_1= '"+fieldvalue+"' where member_no='"+memberno+"' and  branch_id="+branchid+" and company_id="+companyid+"";
        sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
       System.out.println("update on the memberupdatepending table completed successfully..");
    }
    
     public void UpdateMemberName(int id,String fieldvalue,String memberno,int branchid,int companyid,String loggedUser) {
           String membername= fieldvalue;
           String[] membername_split= membername.split("\\s+",3);
           String surname=membername_split[0];
           String firstname=membername_split[1];
           String middlename="";
           if (membername_split.length==3)
           {
            middlename=membername_split[2];
           }
           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date date = new Date();
          String currentdate=dateFormat.format(date);
           System.out.println("The Surname  is------ "+surname+" The Firstname is "+firstname+" The middle name is "+middlename);
     String query = "update memberupdatepending set approved='Y', approvedby='"+loggedUser+"' , approved_date='" + currentdate + "' where id="+id+"";
        sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
        
     String query1 = "update member set first_name= '"+ firstname +"' ,middle_name= '"+ middlename + "' ,surname='"+surname+"' where member_no='"+memberno+"' and  branch_id="+branchid+" and company_id="+companyid+"";
        sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
       System.out.println("update on the memberupdatepending table completed successfully..");
    }
     
    
     @SuppressWarnings("unchecked")
    public ArrayList<Member> listBranchMembers(int coopid, int branchid) {
        ArrayList<Member> list = null;
        Member member = null;
        String Sql = "SELECT member_id,first_name,middle_name,surname,email_add_1 FROM  member where company_id="+coopid+" and branch_id='"+branchid+"'";
          Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<Member>();
            }
           member = new Member();

            Object[] row = results.next();
            member.setMemberId((Integer) row[i++]);
            member.setFirstName((String) row[i++]);
            member.setMiddleName((String) row[i++]);
            member.setSurname((String) row[i++]);
            member.setEmailAdd1((String) row[i++]);
            list.add(member);
        }

        return list;
    }
    
   public void approveMemberEmailChanges(String id, String approvedby ,String emailaddrs, int memberid,int branchid,int companyid){
    String query = "update members_email_change set approved='Y', approvedby='"+approvedby+"'  where id="+id+"";
        sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();
        
     String query1 = "update member set email_add_1= '"+ emailaddrs +"' where member_id="+memberid+" and  branch_id="+branchid+" and company_id="+companyid+"";
        sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
       System.out.println("update successful..");
    }
      public boolean validEmailFormat(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }
      
      
    public ArrayList<UserGroupBeanWS> listAdminFuncMail(String branchid, String companyid,String functionname) {
        ArrayList<UserGroupBeanWS> list = null;
       UserGroupBeanWS userGroupBeanWS = null;
        String Sql = "SELECT  usergrpmdl.id, usergrpmdl.usergroup, usergrpmdl.branchid, usergrpmdl.companyid,users.userid FROM usergrpmdl "
                + "inner join users on  usergrpmdl.branchid=users.branch and  usergrpmdl.companyid= users.companyid and  usergrpmdl.usergroup=users.accesslevel "
                + "where usergrpmdl.menu = '"+functionname+"' and usergrpmdl.branchid='" + branchid + "' and usergrpmdl.companyid='" + companyid + "' ";
               
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        //String Sql = "SELECT a.usergroup,a.branchid, a.companyid,b.userid,a.id FROM easycoopfin.usergrpmdl a inner join easycoopfin.users b on a.branchid=b.branch and a.companyid= b.companyid and a.usergroup=b.accesslevel  where a.menu = '" + functionname + "' and a.branchid='" + branchid + "' and a.companyid='" + companyid + "'";
         
     System.out.println("QUERY"+Sql);
          Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserGroupBeanWS>();
            }
           userGroupBeanWS = new UserGroupBeanWS();

            Object[] row = results.next();
             userGroupBeanWS.setId((Integer) row[i++]);
             userGroupBeanWS.setUsergroup((String) row[i++]);
             userGroupBeanWS.setBranchId((String) row[i++]);
             userGroupBeanWS.setCompanyId((String) row[i++]);
             userGroupBeanWS.setUserId((String) row[i++]);
            
            list.add( userGroupBeanWS);
        }

        return list;
    }
   
}