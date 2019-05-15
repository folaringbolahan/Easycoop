/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import com.sift.gl.model.Txnsheader;
import com.sift.gl.model.Txn;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Account;
import com.sift.cli.GLJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.GetSetting;
import com.sift.gl.NewSerialno;
import com.sift.gl.NotificationServicezk;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
//import org.zkoss.spring.
import com.sift.gl.model.Users;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.Selectors;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
/**
 *
 * @author yomi-pc
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TxnViewModel {
        //@WireVariable
       // private CurrentuserdisplayImpl currentuserdisplayImpl;
        @WireVariable
        private CurrentuserdisplayImpl currrentuserServicex;
        
        Integer vCompany;//=7;
        String vBranch;//="02";
        private Txn selected;
	private java.util.List<Txn> txnList; // = new LinkedList<Account>(new AccountsModel(vBranch,vCompany).getaccountList());
        private Txn currtxn;
        private String msg;
        private double balance=0.0;
        private Date txndate;
        private String theerrmess;
        private String theref;
        private Integer dcompany;
        private String duser;
        private Integer dbranch;
        private Integer dyr;
        private Integer dper;
        private Date dpostdate;
        private String dtimezone;
        //@Autowired
        
        //private Users user;
        //ApplicationContext applicationContext = null;
        //Session sess;
	@Init
	public void init(@ContextParam(ContextType.SESSION) Session session) {	// Initialize
                txnList = new LinkedList<Txn>();
                currtxn = new Txn(); 
                CurrentuserdisplayImpl cdx = (CurrentuserdisplayImpl) session.getAttribute("currrentuserServicex");  //Executions.getCurrent().getSession().getAttribute("currrentuserServicex"); 
                //System.out.println("here now - " + cdx.getCurruser().getUserId());
               //session.getAttribute(vBranch);
               // sess = Executions.getCurrent().getSession();
                //sess = Sessions.getCurrent();
              //  user = (Users)sess.getAttribute("user");
             //   System.out.println("Getting user dets - " + user.getUserId());
             ////user = (Users)SpringUtil.getBean("user");
           //   System.out.println("getting resolution -2 " + currentuserdisplayImpl.getCurruser().getUserId());   
	}
        
      
        public java.util.List<Txn> getTxnList() {
		return txnList;
	}
        @NotifyChange("currtxn")
	public void setSelectedTxn(Txn selected) {
		this.selected = selected;
                currtxn.setAccountno(selected.getAccountno());
                currtxn.setTitle(selected.getTitle());
                currtxn.setCurrency(selected.getCurrency());
	}

	public Txn getSelectedTxn() {
		return selected;
	}
        public void setCurrtxn(Txn currtxn) {
		this.currtxn = currtxn;
	}

	public Txn getCurrtxn() {
		return currtxn;
	}
        public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}
        public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
               
        public void setDcompany(Integer dcompany) {
		this.dcompany = dcompany;
	}

	public Integer getDcompany() {
		return dcompany;
	}
        public void setDuser(String duser) {
		this.duser = duser;
	}
        public String getDuser() {
		return duser;
	}
        public void setDbranch(Integer dbranch) {
		this.dbranch = dbranch;
	}

	public Integer getDbranch() {
		return dbranch;
	}
        public void setDyr(Integer dyr) {
		this.dyr = dyr;
	}
        public Integer getDyr() {
		return dyr;
	}
        public void setDper(Integer dper) {
		this.dper = dper;
	}
        public Integer getDper() {
		return dper;
	}
        public void setTxndate(Date txndate) {
		this.txndate = txndate;
	}
        public Date getTxndate() {
		return txndate;
	}
        public void setDpostdate(Date dpostdate) {
		this.dpostdate = dpostdate;
	}
        public Date getDpostdate() {
		return dpostdate;
	}
         public void setDtimezone(String dtimezone) {
		this.dtimezone = dtimezone;
	}
        public String getDtimezone() {
		return dtimezone;
	}
        @NotifyChange({"currtxn"})
        @GlobalCommand 
        public void addtxn(@BindingParam("selectedData") Account dacc){
          currtxn.setAccountno(dacc.getAccountNo());
          currtxn.setTitle(dacc.getName());
          currtxn.setCurrency(dacc.getCurrency());
        } 
      /*  @NotifyChange({"currtxn","selected"})
        @GlobalCommand 
        public void addblanktxn(){
          currtxn.setAccountno("");
          currtxn.setTitle("");
          currtxn.setCurrency("");
          //this.selected.setAccountno("");
          //this.selected.setTitle("");
          //this.selected.setCurrency("");
         // System.out.println("truely called 2" + currtxn.getAccountno());
          //System.out.println("truely called 2" + this.selected.getAccountno());
        } 
        */
        @NotifyChange({"txnList","msg","balance","currtxn"})
        @Command 
        public void add(){
           double amtSum=0.0; 
           Integer sno = txnList.size();
           currtxn.setTxnID(sno+1);
           txnList.add(currtxn);
           BigDecimal bgdvalueamtSum = new BigDecimal(amtSum);
           bgdvalueamtSum = bgdvalueamtSum.setScale(2,BigDecimal.ROUND_HALF_UP);
           BigDecimal bgdvalueCredit = new BigDecimal(0.0);
           bgdvalueCredit = bgdvalueCredit.setScale(2,BigDecimal.ROUND_HALF_UP);
           BigDecimal bgdvalueDebit = new BigDecimal(0.0);
           bgdvalueDebit = bgdvalueDebit.setScale(2,BigDecimal.ROUND_HALF_UP);
           
           for (Txn b : txnList) {
            // amtSum = amtSum + b.getCredit() - b.getDebit(); rounding issues
             bgdvalueCredit =  BigDecimal.valueOf(b.getCredit());
             bgdvalueDebit = BigDecimal.valueOf(b.getDebit());
             bgdvalueamtSum = bgdvalueamtSum.add(bgdvalueCredit);
             bgdvalueamtSum = bgdvalueamtSum.subtract(bgdvalueDebit);
           } 
           amtSum = bgdvalueamtSum.doubleValue();
           this.balance=amtSum;
           System.out.println("truely called 4 " + this.balance);
           msg = "Added Transaction for account " + currtxn.getAccountno() + " : " + currtxn.getTitle(); //update message
           currtxn = new Txn();
           getCurrentuserdisplayImpl();
           //currtxn.setAccountno("");
         
         } 
        
        @NotifyChange({"txnList","msg","balance","currtxn"})
        @Command 
        public void removeitem(@BindingParam("itm") Integer item ){
           double amtSum=0.0; 
           Integer sno = 0;//txnList.size();
           //currtxn.setTxnID(sno+1);
           txnList.remove(item-1);
           BigDecimal bgdvalueamtSum = new BigDecimal(amtSum);
           bgdvalueamtSum = bgdvalueamtSum.setScale(2,BigDecimal.ROUND_HALF_UP);
           BigDecimal bgdvalueCredit = new BigDecimal(0.0);
           bgdvalueCredit = bgdvalueCredit.setScale(2,BigDecimal.ROUND_HALF_UP);
           BigDecimal bgdvalueDebit = new BigDecimal(0.0);
           bgdvalueDebit = bgdvalueDebit.setScale(2,BigDecimal.ROUND_HALF_UP);
           for (Txn b : txnList) {
             sno = sno + 1;  
             b.setTxnID(sno);
             //amtSum = amtSum + b.getCredit() - b.getDebit();
             bgdvalueCredit =  BigDecimal.valueOf(b.getCredit());
             bgdvalueDebit = BigDecimal.valueOf(b.getDebit());
             bgdvalueamtSum = bgdvalueamtSum.add(bgdvalueCredit);
             bgdvalueamtSum = bgdvalueamtSum.subtract(bgdvalueDebit);
           } 
           amtSum = bgdvalueamtSum.doubleValue();
           this.balance=amtSum;
           System.out.println("truely called 5 " + this.balance);
           msg = "Transaction removed "; //update message
           currtxn = new Txn();
           //currtxn.setAccountno("");
         
         } 
        
        @NotifyChange({"txnList","msg","balance","currtxn"})
        @Command 
        public void savetxns(){
           
           if (this.balance== 0)
           {
             int respcode = callservice(txnList,txndate);
             if (respcode==201) {
               msg = "Journal Saved " ;
               currtxn = new Txn();
               txnList = new LinkedList<Txn>();
               
               auditlogcall(32,"GLPJL","",duser,dtimezone,theref,Integer.toString(respcode) + " " + msg,this.dbranch,this.dcompany);
               
               // do task - to mail notification to authorizer - check event settings tab
               
               NotificationServicezk notificationService = new NotificationServicezk();
               GetSetting sendnoticeset = new GetSetting();
                String sendnotice = sendnoticeset.GetSettingvalue("ENABLEGLEMAILNOTIF", this.dbranch, this.dcompany);
                String mailsubject = "Transactions Postings Awaiting Approval";
                if (sendnotice.equalsIgnoreCase("ON") == true) {
                    Map model = new HashMap();
                    model.put("referenceid", theref);
                    notificationService.createflowemailnotice("GL6", mailsubject, "glacctxnsjnapprvmail.ftl", this.dbranch, this.dcompany, model);
                }
                
               
             }
             else {
                 msg = "Journal Cannot be saved " + theerrmess ;
             }
           }
           else {
               System.out.println("truely called 7 " + this.balance);
             msg = "Journal not balanced. Aborting... " ;  
           }
            //update message
           
         }
     
         private Integer callservice(java.util.List<Txn> dtxnlist,Date dtxndate){
             Integer respo = 0;
             //var start
             theref = "";
             Integer branchcode = dbranch;   //"02";
             ////branchcode = user.getBranch();
             Integer coyid = dcompany;//currrentuserServicex.getCurruser().getCompanyid(); // "7";
             ////coyid = user.getCompanyid();
             Integer coyperiod = dper;//5;
             Integer coyyear = dyr;//2015;
             String userid = duser;//currrentuserServicex.getCurruser().getUserId();
             ////userid = user.getUserId();
             String sourcemod = "GL";
             String txntype = "JVP";
             //next line was for direct posting
             ///String txntype = "JV";
             Date txndate = dtxndate;
              
             Date postdate = dpostdate;
             
             TimeZone timeZone = TimeZone.getTimeZone(dtimezone);
             Calendar rightNow = Calendar.getInstance(timeZone);
             Date entrydate = rightNow.getTime();
             //Date entrydate = dtxndate;
             //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
             double currrate = 1.0;
//var end
             Entrys ent = new Entrys();
             NewSerialno nvSerial = new NewSerialno();
             Integer varSerialint;
             varSerialint = nvSerial.returnSerialno("Dref", branchcode, coyid);
             String varSerial = Integer.toString(varSerialint);
             Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid,dtimezone);
             ent.setTxnsheader(txnhdr);

             java.util.LinkedList<Entry> dentlist;
             dentlist = new LinkedList<Entry>();
             double amt = 0.0;
             String txncode = "";
             BigDecimal bgdvalueamtSum = new BigDecimal(amt);
             bgdvalueamtSum = bgdvalueamtSum.setScale(2,BigDecimal.ROUND_HALF_UP);
             BigDecimal bgdvalueCredit = new BigDecimal(0.0);
             bgdvalueCredit = bgdvalueCredit.setScale(2,BigDecimal.ROUND_HALF_UP);
             BigDecimal bgdvalueDebit = new BigDecimal(0.0);
             bgdvalueDebit = bgdvalueDebit.setScale(2,BigDecimal.ROUND_HALF_UP);
         
             for (Txn b : txnList) {
                 amt = 0.0;
                 //amt = b.getCredit() - b.getDebit();
                 bgdvalueamtSum = new BigDecimal(amt);
                 bgdvalueCredit =  BigDecimal.valueOf(b.getCredit());
                 bgdvalueDebit = BigDecimal.valueOf(b.getDebit());
                 bgdvalueamtSum = bgdvalueamtSum.add(bgdvalueCredit);
                 bgdvalueamtSum = bgdvalueamtSum.subtract(bgdvalueDebit);
                 amt = bgdvalueamtSum.doubleValue();
                 if (amt > 0){
                    txncode = "CRV";
                 }
                 else {
                    txncode = "DRV";
                 }
                 Entry ent1 = new Entry(txntype, txncode, b.getAccountno(), "2000-10-10", b.getReference(), varSerial, b.getNarrative(),"1" , amt, amt, currrate, userid, branchcode, coyyear, coyperiod, coyid);
                 dentlist.add(ent1);
             }

             ent.setEntrys(dentlist);       
             GLJerseyClient cliserv = new GLJerseyClient();
             //respo = cliserv.calldservice(ent);
             respo = cliserv.calldunauthorizedservice(ent);
             
             theerrmess= cliserv.gettheerrmess();
             theref = cliserv.getthereference();
             return respo;
         }
         
         public void  getCurrentuserdisplayImpl() {
            //currentuserdisplayImpl = (CurrentuserdisplayImpl) new DelegatingVariableResolver().resolveVariable("currentuserdisplayImpl");
           // System.out.println("getting resolution -1 " + currentuserdisplayImpl.getCurruser().getUserId());
             // Selectors.wireVariables( page,this, Selectors.newVariableResolvers(getClass(), null));
          //  System.out.println("getting resolution -1x " + currentuserdisplayImpl.getCurruser().getUserId());
           /*  if (applicationContext != null){
                System.out.println("getting resolution -1 ");
            }
            if (applicationContext.containsBean("currrentuserServicex") == true){
                System.out.println("getting resolution -2 ");
            }
            if (applicationContext.containsBean("CurrentuserdisplayImpl") == true){
                System.out.println("getting resolution -3 ");
            }
             System.out.println("getting resolution -4 ");
             if (applicationContext != null && applicationContext.containsBean("currrentuserServicex")){
               CurrentuserdisplayImpl beanA = (CurrentuserdisplayImpl) applicationContext.getBean("currrentuserServicex");
            //Do something with this AccessBean
               System.out.println("getting resolution -1 " + beanA.getCurruser().getUserId());
             }
             */
             //ApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
             
             //currrentuserServicex = (CurrentuserdisplayImpl) SpringUtil.getBean("currentuserdisplayImpl") ;//new DelegatingVariableResolver().resolveVariable("currrentuserServicex");
            ///currrentuserServicex = (CurrentuserdisplayImpl) SpringUtil.getBean("currrentuserServicex");
            //System.out.println("getting resolution -2 " + currrentuserServicex.getCurruser().getCompanyid());
             System.out.println("getting resolution -2 " + dcompany);   //currrentuserServicexg.getCurruser().getCompanyid());//currrentuserServicex.getCurruser().getUserId());
         } 
         
      public void auditlogcall( int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
       Activitylog ent;
       ent = new Activitylog();
       String theerrmess;        
       ent.setBranchid(branch); 
       ent.setEvent(eventid);
       ent.setAction(eventactioncode);
       ent.setCompanyid(company); 
       ent.setUsername(username);
       ent.setTimezone(timezone); 
       ent.setDescription(""); 
       ent.setIpaddress(ipaddr);
       ent.setActionItem(actionitem);
       ent.setActionResult(result); 
        AuditlogService cliserv = new AuditlogService();
       String respo = cliserv.create(ent);
       System.out.println(respo);
       //theerrmess= cliserv.gettheerrmess();
       //System.out.println(theerrmess);
    }
}
