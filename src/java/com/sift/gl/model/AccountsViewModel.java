/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

import com.sift.gl.model.AccountsModel;
import com.sift.gl.model.Txn;
import com.sift.gl.model.Account;
import java.util.LinkedList;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Textbox;

/** model for accounts list on zk views
 *
 * @author yomi-pc
 */
public class AccountsViewModel {
        Integer vCompany;//=7;
        Integer vBranch;//="02";
        private Account selected;
        private String keyword="";
	private java.util.List<Account> accounts; // = new LinkedList<Account>(new AccountsModel(vBranch,vCompany).getaccountList());
        /**
     *
     */
    protected Txn currtxn = new Txn();
	/**
     *
     * @param arg1
     * @param arg2
     */
    @Init
	public void init(@BindingParam("arg1") Integer arg1,@BindingParam("arg2") Integer arg2) {	// Initialize
                vCompany=arg2;
                vBranch=arg1;
                accounts = new LinkedList<Account>(new AccountsModel(vBranch,vCompany,keyword).getaccountList());
               /* try {
		  selected = accounts.get(0); // Selected First One
                }
                catch (Exception ex) {
                   selected = null; 
                }   */
	}
        /**
     *
     * @return
     */
    public java.util.List<Account> getAccountList() {
		return accounts;
	}

	/**
     *
     * @param selected
     */
    public void setSelectedAccount(Account selected) {
		this.selected = selected;
	}

	/**
     *
     * @return
     */
    public Account getSelectedAccount() {
		return selected;
	}

        //@NotifyChange({"txnList","msg","currtxn"})
        /**
     *
     * @param dacc
     */
    @GlobalCommand 
        public void addtxn(@BindingParam("selectedData") Account dacc){
          currtxn.setAccountno(dacc.getAccountNo());
          currtxn.setTitle(dacc.getName());
          currtxn.setCurrency(dacc.getCurrency());
          
        } 
      /*  @NotifyChange({"currtxn","selectedAccount","accountList"})
        @GlobalCommand 
        public void addblanktxn(){
          currtxn.setAccountno("");
          currtxn.setTitle("");
          currtxn.setCurrency("");
          this.selected.setAccountNo("");
          this.selected.setName("");
          this.selected.setCurrency("");
          //System.out.println("truely called 1" + currtxn.getAccountno());
          //System.out.println("truely called 1" + this.selected.getAccountNo());
        } 
        */
        /**
     *
     * @param dcomp
     */
    @Command
        public void setcomp(@BindingParam("parentcomp") Bandbox dcomp) {
          dcomp.setValue(this.selected.getAccountNo());
          dcomp.close();
        }
      
        /**
     *
     * @param dcomp
     * @param dctrl1
     * @param dctrl2
     */
    @GlobalCommand
        public void setcompnull(@BindingParam("parentcomp") Bandbox dcomp,@BindingParam("ctrl1") Textbox dctrl1,@BindingParam("ctrl2") Textbox dctrl2) {
          dctrl1.setValue("");
          dctrl2.setValue("");
          dcomp.setValue("");
          dcomp.close();
        } 
    
    @Command
    @NotifyChange("accountList")
    public void search(){
        accounts = new LinkedList<Account>(new AccountsModel(vBranch,vCompany,keyword).getaccountList());
    }
    
     public void setKeyword(String keyword) {
		this.keyword = keyword;
     }

    public String getKeyword() {
		return keyword;
    }
}
