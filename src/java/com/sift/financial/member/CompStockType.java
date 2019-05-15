package com.sift.financial.member;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CompStockType entity. @author MyEclipse Persistence Tools
 */

public class CompStockType implements java.io.Serializable {

	// Fields

	private Integer compStockTypeId;
	private Company company;
	private String compStockName;
	private Date createdDate;
	private String delFlg;
	private String createdBy;
	private String defaultStock;
	private String shortName;
	private Set compStockTypeDetails = new HashSet(0);
	private Status status;
	private String stockAcctProd;
        private String stockControlAcct;
        
        private String stockCashAcct;
        private String stockParAcct;
        private String stockExcessAcct;
        private String stockTreasuryAcct;
        
        private Timestamp approvedDate;
	private String approvedBy;
        private Timestamp modifiedDate;
	private String modifiedBy;
        
        private String registerStock ;
	
	private transient String action;
	private transient String newProd;
        
        private transient String MEMBER;
        private transient String TAXABLE;
        private transient String REPURCHASABLE;
        private transient String SALABLE;
        private transient String PARVALUE;
        private transient String EARNDIVIDEND;
        private transient String PRIORITY;
        private transient String MINVALUE;
        private transient String MAXVALUE;
        private transient String CANVOTE;
        private transient String VOTEFORMULA;
        private transient String stckoperand;
        private transient String VOTERIGHTPROP;
        private transient String PATRONAGEREFUND;
        private transient String UNITCOST;
        private transient String TOTALLIMITED;
        private transient String TOTALAVAILABLE;
        private transient String PRODHASINT;
        private transient String PRODINTVAL;
        private transient String PRODPENALTY;

	// Constructors

	/** default constructor */
	public CompStockType() {
	}

	/** minimal constructor */
	public CompStockType(Company company, String compStockName,
			Date createdDate, String delFlg, String createdBy, String defaultStock, String shortName,Status status,String registerStock) {
		this.company = company;
		this.compStockName = compStockName;
		this.createdDate = createdDate;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.defaultStock = defaultStock;
		this.shortName = shortName;
		this.status = status;
                this.registerStock =registerStock;
	}

	/** full constructor */
	public CompStockType(Company company, String compStockName,
			Date createdDate, String delFlg, String createdBy,String defaultStock, String shortName,
			Set compStockTypeDetails,Status status, String stockAcctProd,Timestamp modifiedDate, String modifiedBy,
                        Timestamp approvedDate, String approvedBy,String registerStock) {
		this.company = company;
		this.compStockName = compStockName;
		this.createdDate = createdDate;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.defaultStock = defaultStock;
		this.shortName = shortName;
		this.compStockTypeDetails = compStockTypeDetails;
		this.status = status;
		this.stockAcctProd=stockAcctProd;
               this.approvedDate=approvedDate;
                this.approvedBy = approvedBy;
                this.modifiedDate=modifiedDate;
                this.modifiedBy = modifiedBy;
                this.registerStock =registerStock;
		
	}

	// Property accessors

	public Integer getCompStockTypeId() {
		return this.compStockTypeId;
	}

	public void setCompStockTypeId(Integer compStockTypeId) {
		this.compStockTypeId = compStockTypeId;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getCompStockName() {
		return this.compStockName;
	}

	public void setCompStockName(String compStockName) {
		this.compStockName = compStockName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDelFlg() {
		return this.delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Set getCompStockTypeDetails() {
		return this.compStockTypeDetails;
	}

	public void setCompStockTypeDetails(Set compStockTypeDetails) {
		this.compStockTypeDetails = compStockTypeDetails;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDefaultStock() {
		return defaultStock;
	}

	public void setDefaultStock(String defaultStock) {
		this.defaultStock = defaultStock;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getStockAcctProd() {
		return stockAcctProd;
	}

	public void setStockAcctProd(String stockAcctProd) {
		this.stockAcctProd = stockAcctProd;
	}

	public String getNewProd() {
		return newProd;
	}

	public void setNewProd(String newProd) {
		this.newProd = newProd;
	}
        
        
         public String getMEMBER() {
        return MEMBER;
    }

    public void setMEMBER(String MEMBER) {
        this.MEMBER = MEMBER;
    }

    public String getTAXABLE() {
        return TAXABLE;
    }

    public void setTAXABLE(String TAXABLE) {
        this.TAXABLE = TAXABLE;
    }

    public String getREPURCHASABLE() {
        return REPURCHASABLE;
    }

    public void setREPURCHASABLE(String REPURCHASABLE) {
        this.REPURCHASABLE = REPURCHASABLE;
    }

    public String getSALABLE() {
        return SALABLE;
    }

    public void setSALABLE(String SALABLE) {
        this.SALABLE = SALABLE;
    }

    public String getPARVALUE() {
        return PARVALUE;
    }

    public void setPARVALUE(String PARVALUE) {
        this.PARVALUE = PARVALUE;
    }

    public String getEARNDIVIDEND() {
        return EARNDIVIDEND;
    }

    public void setEARNDIVIDEND(String EARNDIVIDEND) {
        this.EARNDIVIDEND = EARNDIVIDEND;
    }

    public String getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(String PRIORITY) {
        this.PRIORITY = PRIORITY;
    }

    public String getMINVALUE() {
        return MINVALUE;
    }

    public void setMINVALUE(String MINVALUE) {
        this.MINVALUE = MINVALUE;
    }

    public String getMAXVALUE() {
        return MAXVALUE;
    }

    public void setMAXVALUE(String MAXVALUE) {
        this.MAXVALUE = MAXVALUE;
    }

    public String getCANVOTE() {
        return CANVOTE;
    }

    public void setCANVOTE(String CANVOTE) {
        this.CANVOTE = CANVOTE;
    }

    public String getVOTEFORMULA() {
        return VOTEFORMULA;
    }

    public void setVOTEFORMULA(String VOTEFORMULA) {
        this.VOTEFORMULA = VOTEFORMULA;
    }

    public String getStckoperand() {
        return stckoperand;
    }

    public void setStckoperand(String stckoperand) {
        this.stckoperand = stckoperand;
    }

    public String getVOTERIGHTPROP() {
        return VOTERIGHTPROP;
    }

    public void setVOTERIGHTPROP(String VOTERIGHTPROP) {
        this.VOTERIGHTPROP = VOTERIGHTPROP;
    }

    public String getPATRONAGEREFUND() {
        return PATRONAGEREFUND;
    }

    public void setPATRONAGEREFUND(String PATRONAGEREFUND) {
        this.PATRONAGEREFUND = PATRONAGEREFUND;
    }

    public String getUNITCOST() {
        return UNITCOST;
    }

    public void setUNITCOST(String UNITCOST) {
        this.UNITCOST = UNITCOST;
    }

    public String getTOTALLIMITED() {
        return TOTALLIMITED;
    }

    public void setTOTALLIMITED(String TOTALLIMITED) {
        this.TOTALLIMITED = TOTALLIMITED;
    }

    public String getTOTALAVAILABLE() {
        return TOTALAVAILABLE;
    }

    public void setTOTALAVAILABLE(String TOTALAVAILABLE) {
        this.TOTALAVAILABLE = TOTALAVAILABLE;
    }

    public String getStockControlAcct() {
        return stockControlAcct;
    }

    public void setStockControlAcct(String stockControlAcct) {
        this.stockControlAcct = stockControlAcct;
    }

    public String getPRODHASINT() {
        return PRODHASINT;
    }

    public void setPRODHASINT(String PRODHASINT) {
        this.PRODHASINT = PRODHASINT;
    }

    public String getPRODINTVAL() {
        return PRODINTVAL;
    }

    public void setPRODINTVAL(String PRODINTVAL) {
        this.PRODINTVAL = PRODINTVAL;
    }

    public String getPRODPENALTY() {
        return PRODPENALTY;
    }

    public void setPRODPENALTY(String PRODPENALTY) {
        this.PRODPENALTY = PRODPENALTY;
    }

    public Timestamp getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Timestamp approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getRegisterStock() {
        return registerStock;
    }

    public void setRegisterStock(String registerStock) {
        this.registerStock = registerStock;
    }

    public String getStockCashAcct() {
        return stockCashAcct;
    }

    public void setStockCashAcct(String stockCashAcct) {
        this.stockCashAcct = stockCashAcct;
    }

    public String getStockParAcct() {
        return stockParAcct;
    }

    public void setStockParAcct(String stockParAcct) {
        this.stockParAcct = stockParAcct;
    }

    public String getStockExcessAcct() {
        return stockExcessAcct;
    }

    public void setStockExcessAcct(String stockExcessAcct) {
        this.stockExcessAcct = stockExcessAcct;
    }

    public String getStockTreasuryAcct() {
        return stockTreasuryAcct;
    }

    public void setStockTreasuryAcct(String stockTreasuryAcct) {
        this.stockTreasuryAcct = stockTreasuryAcct;
    }
    
    
    
    
}