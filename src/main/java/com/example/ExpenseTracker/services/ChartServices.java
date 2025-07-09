package com.example.ExpenseTracker.services;

import com.example.ExpenseTracker.dto.CategorySummaryDTO;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ChartServices {
    public void generateCategorySummaryPieChart(List<CategorySummaryDTO> summary, OutputStream outputStream) throws IOException {
        PieChart chart = new PieChartBuilder()
                .width(800)
                .height(600)
                .title("Expenses by Category")
                .build();

        for (CategorySummaryDTO item : summary) {
            chart.addSeries(item.getCategoryName(), item.getTotal());
        }

        BitmapEncoder.saveBitmap(chart, outputStream, BitmapEncoder.BitmapFormat.PNG);
    }
}
