package com.example.ExpenseTracker.services;

import com.example.ExpenseTracker.transactiondto.TransactionResponseDTO;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class CsvExportService {
    public void writeTransactionToCsv(List<TransactionResponseDTO> transactions, PrintWriter writer) {
        CSVWriter csvWriter = new CSVWriter(writer);

        String[] header = {"id", "description", "amount", "type", "date", "categoryId", "userId"};
        csvWriter.writeNext(header);

        for (TransactionResponseDTO dto : transactions) {
            String[] line = {
                    String.valueOf(dto.getId()),
                    dto.getDescription(),
                    dto.getAmount() != null ? dto.getAmount().toString() : "",
                    dto.getType() != null ? dto.getType().toString() : "",
                    dto.getDate() != null ? dto.getDate().toString() : "",
                    dto.getCategoryId() != null ? dto.getCategoryId().toString() : "",
                    dto.getUserId() != null ? dto.getUserId().toString() : ""
            };
            csvWriter.writeNext(line);
        }

        try {
            csvWriter.close();
        } catch (Exception e) {
            throw new RuntimeException("Error closing CSVWriter", e);
        }
    }
}
