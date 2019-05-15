package com.sift.admin.dao;

import java.util.List;
import com.sift.admin.model.TaxGroupItem;
/**
 * @author XTOFFEL CONSULT
 *
 */
public interface TaxGroupItemDao{
	 public void addTaxGroupItem(TaxGroupItem addDetails);
	 public void addTaxGroupItem(TaxGroupItem taxGroupItem,String options[]);
	 public void addTaxGroupItem(TaxGroupItem taxGroupItem,String options[],String reportMenus[]);
	 public List<TaxGroupItem> listTaxGroupItem();
	 public List<TaxGroupItem> listTaxGroupItem(String usergroup);
	 public TaxGroupItem getTaxGroupItem(String id);
	 public void deleteTaxGroupItem(TaxGroupItem addDetails);
}