package com.sift.financial.member;

import java.util.HashSet;
import java.util.Set;

/**
 * CompStockProperty entity. @author MyEclipse Persistence Tools
 */

public class CompStockProperty implements java.io.Serializable {

	// Fields

	private Integer stockPptyId;
	private String stockPptyName;
	private String delFlg;
	private String stockPptyDesc;
	private String stockPptyDisplay;
	private Set compStockTypeDetails = new HashSet(0);
	private String stockPptyTip;
	private String stockPptyFormula;
        private String stockPptyEmptyMsg;
        private String stockPptyCondMsg;
        
        
       
	// Constructors

	/** default constructor */
	public CompStockProperty() {
	}

	/** minimal constructor */
	public CompStockProperty(String stockPptyName, String delFlg,String stockPptyFormula) {
		this.stockPptyName = stockPptyName;
		this.delFlg = delFlg;
		this.stockPptyFormula=stockPptyFormula;
	}

	/** full constructor */
	public CompStockProperty(String stockPptyName, String delFlg,
			String stockPptyDesc, String stockPptyDisplay,
			Set compStockTypeDetails,String stockPptyTip,
	String stockPptyFormula, String stockPptyEmptyMsg,String stockPptyCondMsg) {
		this.stockPptyName = stockPptyName;
		this.delFlg = delFlg;
		this.stockPptyDesc = stockPptyDesc;
		this.stockPptyDisplay = stockPptyDisplay;
		this.compStockTypeDetails = compStockTypeDetails;
		this.stockPptyTip= stockPptyTip;
		this.stockPptyFormula=stockPptyFormula;
                this.stockPptyEmptyMsg= stockPptyEmptyMsg;
                this.stockPptyCondMsg = stockPptyCondMsg;
	}

	// Property accessors

	public Integer getStockPptyId() {
		return this.stockPptyId;
	}

	public void setStockPptyId(Integer stockPptyId) {
		this.stockPptyId = stockPptyId;
	}

	public String getStockPptyName() {
		return this.stockPptyName;
	}

	public void setStockPptyName(String stockPptyName) {
		this.stockPptyName = stockPptyName;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getStockPptyDesc() {
		return this.stockPptyDesc;
	}

	public void setStockPptyDesc(String stockPptyDesc) {
		this.stockPptyDesc = stockPptyDesc;
	}

	public String getStockPptyDisplay() {
		return this.stockPptyDisplay;
	}

	public void setStockPptyDisplay(String stockPptyDisplay) {
		this.stockPptyDisplay = stockPptyDisplay;
	}

	public Set getCompStockTypeDetails() {
		return this.compStockTypeDetails;
	}

	public void setCompStockTypeDetails(Set compStockTypeDetails) {
		this.compStockTypeDetails = compStockTypeDetails;
	}

	public String getStockPptyTip() {
		return stockPptyTip;
	}

	public void setStockPptyTip(String stockPptyTip) {
		this.stockPptyTip = stockPptyTip;
	}

	public String getStockPptyFormula() {
		return stockPptyFormula;
	}

	public void setStockPptyFormula(String stockPptyFormula) {
		this.stockPptyFormula = stockPptyFormula;
	}

        public String getStockPptyEmptyMsg() {
            return stockPptyEmptyMsg;
        }

        public void setStockPptyEmptyMsg(String stockPptyEmptyMsg) {
            this.stockPptyEmptyMsg = stockPptyEmptyMsg;
        }

        public String getStockPptyCondMsg() {
            return stockPptyCondMsg;
        }

        public void setStockPptyCondMsg(String stockPptyCondMsg) {
            this.stockPptyCondMsg = stockPptyCondMsg;
        }
}