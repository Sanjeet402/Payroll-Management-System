package com.payrollbackend.controller;

import com.payrollbackend.dto.*;
import com.payrollbackend.service.US7_AccountStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-statement")
@CrossOrigin(origins = "http://localhost:3000")
public class US7_AccountStatementController {

    @Autowired
    private US7_AccountStatementService US7AccountStatementService;

    @GetMapping
    public ResponseEntity<List<US7_AccountSummaryDTO>> getAllAccounts() {
        List<US7_AccountSummaryDTO> accounts = US7AccountStatementService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountNumber}/transactions")
    public List<US7_TransactionHistoryDTO> getTransactions(
            @PathVariable String accountNumber,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {
        return US7AccountStatementService.getTransactionsByAccount(accountNumber, fromDate, toDate);
    }

    @GetMapping("/{accountNumber}/statement")
    public US7_AccountStatementDTO getAccountStatement(
            @PathVariable String accountNumber,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {
        return US7AccountStatementService.getAccountStatement(accountNumber, fromDate, toDate);
    }
}
