package com.payrollbackend.controller;

import com.payrollbackend.dto.US3_PayrollBatchGridDTO;
import com.payrollbackend.exceptions.ResourceNotFoundException;
import com.payrollbackend.model.PayrollBatch;
import com.payrollbackend.model.PayrollBatchPayment;
import com.payrollbackend.repository.PayrollBatchPaymentRepository;
import com.payrollbackend.service.US3_PayrollCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.payrollbackend.model.Account;
import com.payrollbackend.repository.US7_AccountRepository;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/us3/batch")
public class US3_PayrollCreationController {

    private final US3_PayrollCreationService batchService;
    private final US7_AccountRepository US7AccountRepository;
    private final PayrollBatchPaymentRepository paymentRepo;

    @Autowired
    public US3_PayrollCreationController(US3_PayrollCreationService batchService, US7_AccountRepository US7AccountRepository, PayrollBatchPaymentRepository paymentRepo) {
        this.batchService = batchService;
        this.US7AccountRepository = US7AccountRepository;
        this.paymentRepo = paymentRepo;
    }

    @PostMapping
    public ResponseEntity<PayrollBatch> createBatch(@RequestBody PayrollBatch batch, @RequestParam(name = "debitAccountId", required = false) Long debitAccountId) {
        if (debitAccountId == null && (batch.getDebitAccount() == null || batch.getDebitAccount().getId() == null)) {
            throw new IllegalArgumentException("Debit account ID is required");
        }
        if (debitAccountId != null) {
            Account account = US7AccountRepository.findById(debitAccountId)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "id", debitAccountId));
            batch.setDebitAccount(account);
        }
        PayrollBatch saved = batchService.saveBatch(batch);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/grid")
    public List<US3_PayrollBatchGridDTO> gridEndpoint() {
        return batchService.getBatchGridList();
    }

}
