/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.service;
import java.util.List;

import com.sift.admin.model.MemberCategory;
/**
 *
 * @author Nelson Akpos
 */
public interface MemberCategoryService {
        public void addMemberCategory(MemberCategory memberCategory);
	public List<MemberCategory> listMemberCategories();
        public MemberCategory getMemberCategory(int id);
        public void deleteMemberCategory(MemberCategory memberCategory);
        
	
}
