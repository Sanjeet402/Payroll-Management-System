package com.payrollbackend.controller;

import com.payrollbackend.dto.US5_ApprovalBatchListDTO;
import com.payrollbackend.dto.US5_BatchDetailDTO;
import com.payrollbackend.exceptions.UnauthorizedException;
import com.payrollbackend.model.PayrollBatch;
import com.payrollbackend.service.US5_ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/us5/approval")
public class US5_ApprovalController {

    private final US5_ApprovalService approvalService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public US5_ApprovalController(US5_ApprovalService approvalService, AuthenticationManager authenticationManager) {
        this.approvalService = approvalService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/pending")
    public List<US5_ApprovalBatchListDTO> getPendingApprovalBatches() {
        return approvalService.getPendingApprovalBatches();
    }

    @GetMapping("/details/{batchReference}")
    public US5_BatchDetailDTO getBatchDetails(@PathVariable String batchReference) {
        return approvalService.getBatchDetailsWithTransactions(batchReference);
    }

    @PostMapping("/approve")
    public ResponseEntity<Map<String, String>> approveBatches(@RequestBody Map<String, Object> payload) {
        @SuppressWarnings("unchecked")
        List<String> batchReferences = (List<String>) payload.get("batchReferences");
        String password = (String) payload.get("password");

        if (batchReferences == null || batchReferences.isEmpty()) {
            throw new IllegalArgumentException("batchReferences is required");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Invalid password");
        }

        approvalService.approveBatches(batchReferences, username);
        return ResponseEntity.ok(Map.of("message", "Batches approved successfully"));
    }

    @PostMapping("/reject")
    public ResponseEntity<Map<String, String>> rejectBatches(@RequestBody Map<String, Object> payload) {
        @SuppressWarnings("unchecked")
        List<String> batchReferences = (List<String>) payload.get("batchReferences");
        String password = (String) payload.get("password");

        if (batchReferences == null || batchReferences.isEmpty()) {
            throw new IllegalArgumentException("batchReferences is required");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Invalid password");
        }

        approvalService.rejectBatches(batchReferences);
        return ResponseEntity.ok(Map.of("message", "Batches rejected successfully"));
    }

    @GetMapping("/processed")
    public List<US5_ApprovalBatchListDTO> getProcessedBatches() {
        return approvalService.getProcessedBatches();
    }
}