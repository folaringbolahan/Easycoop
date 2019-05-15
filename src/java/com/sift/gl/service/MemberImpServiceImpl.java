/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.gl.service;

import com.sift.gl.dao.MemberImpDao;
import com.sift.gl.model.MemberImp;
import com.sift.gl.model.Products;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("memberImpService")
@Transactional
public class MemberImpServiceImpl implements MemberImpService{

    @Autowired
    public MemberImpDao memberImpDao;
    
    @Override
    public boolean addOrUpdateMemberImp(MemberImp memberImp) {
        return memberImpDao.addOrUpdateMemberImp(memberImp);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberImp> listMembersByBranch(int companyId, int branchId) {
        return memberImpDao.listMembersByBranch(companyId, branchId);
    }

    @Override
    public ArrayList<MemberImp> listAllMembers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MemberImp getMemberById(int companyId, int branchId, String memberNo) {
        return memberImpDao.getMemberById(companyId, branchId, memberNo);
    }

    @Override
    public String getCountryName(int countryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Products> listActiveProductsByBranch(int companyId, int branchId) {
        return memberImpDao.listActiveProductsByBranch(companyId, branchId);
    }

    @Override
    public boolean memberExist(String memberNo) {
        return memberImpDao.memberExist(memberNo);
    }

    @Override
    public int getMemberId(int companyId, int branchId, String memberNo) {
        return memberImpDao.getMemberId(companyId, branchId, memberNo);
    }
    
}
