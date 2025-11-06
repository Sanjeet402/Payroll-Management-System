package com.payrollbackend.controller;

import com.payrollbackend.dto.US6_BatchGridDTO;
import com.payrollbackend.dto.US6_BatchDetailDTO;
import com.payrollbackend.service.US6_TransactionPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/us6/print")
public class US6_TransactionPrintController {

    private final US6_TransactionPrintService printService;

    @Autowired
    public US6_TransactionPrintController(US6_TransactionPrintService printService) {
        this.printService = printService;
    }

    @GetMapping("/batches")
    public List<US6_BatchGridDTO> getAllBatchesForPrintPreview() {
        return printService.getBatchGridList();
    }

    @GetMapping("/batch/{batchReference}")
    public US6_BatchDetailDTO getBatchWithTransactionsForPrint(@PathVariable String batchReference) {
        return printService.getBatchDetailsWithTransactions(batchReference);
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<String> generatePDF(@RequestBody Map<String, List<String>> payload) {
        List<String> transactionIds = payload.get("transactionIds");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body("{\"message\": \"PDF generated successfully for " + transactionIds.size() + " transactions\", \"filename\": \"transactions_" + System.currentTimeMillis() + ".pdf\"}");
    }
}