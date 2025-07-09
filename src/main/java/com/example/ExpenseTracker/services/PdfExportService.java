package com.example.ExpenseTracker.services;

import com.example.ExpenseTracker.dto.TransactionResponseDTO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PdfExportService {
    public void writeTransactionsToPdf(List<TransactionResponseDTO> transactions, OutputStream outputStream) throws IOException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.add(new Paragraph("Expense Tracker - Transactions"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            table.addCell("ID");
            table.addCell("Description");
            table.addCell("Amount");
            table.addCell("Type");
            table.addCell("Date");
            table.addCell("CategoryId");

            for (TransactionResponseDTO t : transactions) {
                table.addCell(String.valueOf(t.getId()));
                table.addCell(t.getDescription());
                table.addCell(t.getAmount().toString());
                table.addCell(t.getType().toString());
                table.addCell(t.getDate().toString());
                table.addCell(t.getCategoryId() != null ? t.getCategoryId().toString() : "");
            }

            document.add(table);
        } catch (DocumentException e) {
            throw new IOException(e);
        } finally {
            document.close();
        }
    }
}
