/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.admin.service;

import com.sift.admin.dao.BankDao;
import com.sift.admin.model.Bank;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service("bankService")
@Transactional
public class BankServiceImpl implements BankService{
    
    @Autowired
    public BankDao bankDao;
    
    @Override
    public boolean addOrUpdateBank(Bank bank) {
        return bankDao.addOrUpdateBank(bank);
    }

    @Override
    public boolean deleteBank(int bankId) {
        return bankDao.deleteBank(bankId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bank> listBankByCountry(int countryId) {
        return bankDao.listBankByCountry(countryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Bank getBankById(int id) {
        return bankDao.getBankById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Bank> listAllBanks() {
        return bankDao.listAllBanks();
    }

    @Override
    public String getCountryName(int countryId) {
        return bankDao.getCountryName(countryId);
    }
    
}
