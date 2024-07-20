package com.config_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.config_server.Repo.AccountingRepository;
import com.config_server.entities.Accounting;
import com.config_server.service.AccountingService;

import java.util.List;

@Service
public class AccountingServiceImpl implements AccountingService {

    @Autowired
    private AccountingRepository accountingRepository;

	@Override
	public Accounting save(Accounting accounting) {
		return accountingRepository.save(accounting);
	}

	@Override
	public List<Accounting> findAll() {
		return accountingRepository.findAll();
	}

    
}

