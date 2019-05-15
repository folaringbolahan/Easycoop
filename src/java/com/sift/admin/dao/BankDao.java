/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.dao;

import com.sift.admin.model.Bank;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface BankDao {
    public boolean addOrUpdateBank(Bank bank);
    public boolean deleteBank(int bankId);
    public List<Bank> listBankByCountry(int countryId);
    //public List<Bank> listAllBanks();
    public ArrayList<Bank> listAllBanks();
    public Bank getBankById(int id);
    public String getCountryName(int countryId);
}
