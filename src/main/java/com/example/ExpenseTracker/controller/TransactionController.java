package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.dto.CategorySummaryDTO;
import com.example.ExpenseTracker.dto.MonthlySummaryDTO;
import com.example.ExpenseTracker.dto.TransactionRequestDTO;
import com.example.ExpenseTracker.dto.TransactionResponseDTO;
import com.example.ExpenseTracker.services.ChartServices;
import com.example.ExpenseTracker.services.CsvExportService;
import com.example.ExpenseTracker.services.PdfExportService;
import com.example.ExpenseTracker.services.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private final TransactionService transactionServices;
    private final CsvExportService csvExportService;
    private final PdfExportService pdfExportService;
    private final ChartServices chartServices;

    public TransactionController(TransactionService transactionServices, CsvExportService csvExportService, PdfExportService pdfExportService, ChartServices chartServices) {
        this.transactionServices = transactionServices;
        this.csvExportService = csvExportService;
        this.pdfExportService = pdfExportService;
        this.chartServices = chartServices;
    }

    @GetMapping("/user/{userId}")
    public List<TransactionResponseDTO> getUserTransactions(@PathVariable Long userId) {
        return transactionServices.getTransactionsByUser(userId);
    }

    @PostMapping
    public TransactionResponseDTO createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        return transactionServices.createTransaction(transactionRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        transactionServices.deleteTransaction(id);
    }

    @GetMapping("/summary")
    public MonthlySummaryDTO getMonthlySummary(
            @RequestParam int year,
            @RequestParam int month) {
        return transactionServices.getMonthlySummary(month, year);
    }

    @GetMapping("/summary/categories")
    public List<CategorySummaryDTO> getCategorySummary(
            @RequestParam int year,
            @RequestParam int month) {
        return transactionServices.getCategorySummary(month, year);
    }

    @GetMapping(value = "/export/csv", produces = "text/csv")
    public ResponseEntity<String> exportTransactionsToCsv() {
        List<TransactionResponseDTO> transactions = transactionServices.getAllTransactions();

        StringBuilder csv = new StringBuilder();
        csv.append("id,description,amount,type,date,categoryId,userId\n");

        for (TransactionResponseDTO dto : transactions) {
            csv.append(dto.getId()).append(",")
                    .append(dto.getDescription()).append(",")
                    .append(dto.getAmount()).append(",")
                    .append(dto.getType()).append(",")
                    .append(dto.getDate()).append(",")
                    .append(dto.getCategoryId()).append(",")
                    .append(dto.getUserId()).append("\n");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.csv")
                .body(csv.toString());
    }

    @GetMapping("/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.csv");

        List<TransactionResponseDTO> transactions = transactionServices.getAllTransactions();

        csvExportService.writeTransactionToCsv(transactions, response.getWriter());
    }

    @GetMapping("/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

        List<TransactionResponseDTO> transactions = transactionServices.getAllTransactions();
        pdfExportService.writeTransactionsToPdf(transactions, response.getOutputStream());
    }

    @GetMapping("/report/categories/chart")
    public void getCategorySummaryChart(
            @RequestParam int month,
            @RequestParam int year,
            HttpServletResponse response) throws Exception {

        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "inline; filename=category-summary.png");

        List<CategorySummaryDTO> summary = transactionServices.getCategorySummary(month, year);
        chartServices.generateCategorySummaryPieChart(summary, response.getOutputStream());
    }
}
