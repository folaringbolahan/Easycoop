/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.service;

import com.sift.gl.model.MemberImp;
import com.sift.gl.model.Products;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Olakunle Awotunbo 
 */
public interface MemberImpService {
    public boolean addOrUpdateMemberImp(MemberImp memberImp);    
    public List<MemberImp> listMembersByBranch(int companyId, int branchId);    
    public ArrayList<MemberImp> listAllMembers();
    public MemberImp getMemberById(int companyId, int branchId, String memberNo);
    public String getCountryName(int countryId);
    public List<Products> listActiveProductsByBranch(int companyId, int branchId);  
    public boolean memberExist(String memberNo);
    public int getMemberId(int companyId, int branchId, String memberNo);
}
