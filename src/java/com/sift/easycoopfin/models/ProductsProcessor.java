package com.sift.easycoopfin.models;

import org.orm.PersistentException;

public class ProductsProcessor {

    private int id;
    private String code;
    private String segmentCode;
    private byte isDeleted = 0;
    private String name;
    private int companyId;
    private float initialAmountMax = 0;
    private float initialAmountMin = 0;
    private float interestRateMin = 0;
    private float interestRateMax = 0;
    private float interestRate;
    private int branchId;
    private String productTypeCode;
    private String action = "";

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setSegmentCode(String value) {
        this.segmentCode = value;
    }

    public String getSegmentCode() {
        return segmentCode == null ? "" : segmentCode;
    }

    public void setIsDeleted(byte value) {
        this.isDeleted = value;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }
 public int getBranchId(){
        return branchId;
    }
    public void setBranchId(int value){
        this.branchId = value;
    }
    public void setInitialAmountMax(float value) {
        this.initialAmountMax = value;
    }

    public float getInitialAmountMax() {
        return initialAmountMax;
    }

    public void setInitialAmountMin(float value) {
        this.initialAmountMin = value;
    }

    public float getInitialAmountMin() {
        return initialAmountMin;
    }

    public void setInterestRateMin(float value) {
        this.interestRateMin = value;
    }

    public float getInterestRateMin() {
        return interestRateMin;
    }

    public void setInterestRateMax(float value) {
        this.interestRateMax = value;
    }

    public float getInterestRateMax() {
        return interestRateMax;
    }

    public void setInterestRate(float value) {
        this.interestRate = value;
    }

    public float getInterestRate() {
        return interestRate;
    }

    /**
     * public void setCurrencyId(int value) { this.currencyId = value; }
     *
     * public int getCurrencyId() { return currencyId; }
	*
     */
    public void setProductTypeCode(String value) {
        this.productTypeCode = value;
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String process() {
        String result = "Unexcepted result";
        if (action.equals("search")) {
            try {
                com.sift.easycoopfin.models.Products _products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().loadProductsByORMID(getId());
                if (_products != null) {
                    copyFromBean(_products);
                    result = "Search success";
                } else {
                    result = "Search failed";
                }
            } catch (PersistentException e) {
                result = "Search error: " + e.toString();
            }
        } else if (action.equals("insert")) {
            try {
                com.sift.easycoopfin.models.Products _products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().createProducts();
                copyToBean(_products);
                if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().save(_products)) {
                    result = "Insert success";
                } else {
                    result = "Insert failed";
                }
            } catch (Exception e) {
                result = "Insert error: " + e.toString();
            }
        } else if (action.equals("update")) {
            try {
                com.sift.easycoopfin.models.Products _products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().loadProductsByORMID(getId());
                if (_products != null) {
                    copyToBean(_products);
                    if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().save(_products)) {
                        result = "Update success";
                    } else {
                        result = "Update failed";
                    }
                } else {
                    result = "Update failed";
                }

            } catch (PersistentException e) {
                result = "Update error: " + e.toString();
            }
        } else if (action.equals("delete")) {
            try {
                com.sift.easycoopfin.models.Products _products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().loadProductsByORMID(getId());
                if (_products != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().delete(_products)) {
                    result = "Delete success";
                } else {
                    result = "Delete failed";
                }
            } catch (PersistentException e) {
                result = "Delete error: " + e.toString();
            }
        } else if (action.equals("")) {
            result = "";
        }
        action = "";
        return result;
    }

    private void copyFromBean(com.sift.easycoopfin.models.Products _products) {
        setCode(_products.getCode());
        setIsDeleted(_products.getIsDeleted());
        setName(_products.getName());
        setCompanyId(_products.getCompanyId());
        setInitialAmountMax(_products.getInitialAmountMax());
        setInitialAmountMin(_products.getInitialAmountMin());
        setInterestRateMin(_products.getInterestRateMin());
        setInterestRateMax(_products.getInterestRateMax());
        setInterestRate(_products.getInterestRate());
        //setCurrencyId(_products.getCurrencyId());
        setProductTypeCode(_products.getProductTypeCode());
        setId(_products.getORMID());
    }

    private void copyToBean(com.sift.easycoopfin.models.Products _products) {
        _products.setCode(getCode());
        _products.setIsDeleted(getIsDeleted());
        _products.setName(getName());
        _products.setCompanyId(getCompanyId());
        _products.setInitialAmountMax(getInitialAmountMax());
        _products.setInitialAmountMin(getInitialAmountMin());
        _products.setInterestRateMin(getInterestRateMin());
        _products.setInterestRateMax(getInterestRateMax());
        _products.setInterestRate(getInterestRate());
       // _products.setCurrencyId(getCurrencyId());
        _products.setProductTypeCode(getProductTypeCode());
    }
}
