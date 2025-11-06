package com.payrollbackend.controller;

import com.payrollbackend.model.Account;
import com.payrollbackend.service.US7_AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/accounts")
public class US7_AccountController {

    private final US7_AccountService US7AccountService;

    @Autowired
    public US7_AccountController(US7_AccountService US7AccountService) {
        this.US7AccountService = US7AccountService;
    }

    @GetMapping
    public List<Account> list() {
        return US7AccountService.findAll();
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable Long id) {
        return US7AccountService.findById(id)
                .orElseThrow(() -> new com.payrollbackend.exceptions.ResourceNotFoundException("Account", "id", id));
    }
}
