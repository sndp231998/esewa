package com.config_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.config_server.entities.Accounting;
import com.config_server.service.AccountingService;

import java.util.List;

@RestController
@RequestMapping("/accounting")
public class AccountingController {

    @Autowired
    private AccountingService accountingService;

    @PostMapping("/save")
    public Accounting saveAccounting(@RequestBody Accounting accounting) {
        return accountingService.save(accounting);
    }

    @GetMapping("/all")
    public List<Accounting> getAllAccounting() {
        return accountingService.findAll();
    }
}

