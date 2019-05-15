/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member;

import com.sift.easycoopfin.models.ProductType;
import com.sift.easycoopfin.models.Products;
import java.util.Date;


/**
 *
 * @author baydel200
 */
public class MemberEnquiry {
    
    private Member member;
    private Date startDate;
    private Date endDate;
    private MemberContribution memberContribution;
    private MemberHoldings memberHoldings;
    private Products products;
    private ProductType productType;
    private Status status;
    
    private String createdBy;
    private Date createdDate;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public MemberContribution getMemberContribution() {
        return memberContribution;
    }

    public void setMemberContribution(MemberContribution memberContribution) {
        this.memberContribution = memberContribution;
    }

    public MemberHoldings getMemberHoldings() {
        return memberHoldings;
    }

    public void setMemberHoldings(MemberHoldings memberHoldings) {
        this.memberHoldings = memberHoldings;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
