package com.config_server.service;

import java.util.List;

import com.config_server.entities.Accounting;

public interface AccountingService {
	Accounting save(Accounting accounting);
    List<Accounting> findAll();

}
