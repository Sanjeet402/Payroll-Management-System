package com.payrollbackend.controller;

import com.payrollbackend.dto.US4_BatchGridDTO;
import com.payrollbackend.exceptions.ResourceNotFoundException;
import com.payrollbackend.model.PayrollBatch;
import com.payrollbackend.model.PayrollBatchPayment;
import com.payrollbackend.service.US4_BatchSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/us4/batch")
public class US4_BatchSummaryController {

    private final US4_BatchSummaryService batchService;

    @Autowired
    public US4_BatchSummaryController(US4_BatchSummaryService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/grid")
    public List<US4_BatchGridDTO> getBatchGrid() {
        return batchService.getBatchGridList();
    }

    @GetMapping("/{reference}")
    public PayrollBatch getBatchByReference(@PathVariable String reference) {
        return batchService.getBatchByReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("PayrollBatch", "batchReference", reference));
    }

    @GetMapping("/{reference}/payments")
    public List<PayrollBatchPayment> getPaymentsByBatch(@PathVariable String reference) {
        PayrollBatch batch = batchService.getBatchByReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("PayrollBatch", "batchReference", reference));
        return batch.getPayments();
    }

    @PutMapping("/{reference}")
    public PayrollBatch updateBatchByReference(@PathVariable String reference, @RequestBody PayrollBatch payload) {
        return batchService.updateBatch(reference, payload)
                .orElseThrow(() -> new ResourceNotFoundException("PayrollBatch", "batchReference", reference));
    }

    @DeleteMapping("/{reference}")
    public ResponseEntity<Void> deleteBatchByReference(@PathVariable String reference) {
        batchService.deleteBatch(reference);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reference}/payment/{paymentId}")
    public PayrollBatchPayment updatePayment(@PathVariable String reference, @PathVariable Long paymentId, @RequestBody PayrollBatchPayment payload) {
        return batchService.updatePayment(reference, paymentId, payload)
                .orElseThrow(() -> new ResourceNotFoundException("PayrollBatchPayment", "id", paymentId));
    }

    @DeleteMapping("/{reference}/payment/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable String reference, @PathVariable Long paymentId) {
        batchService.deletePayment(reference, paymentId);
        return ResponseEntity.noContent().build();
    }
}