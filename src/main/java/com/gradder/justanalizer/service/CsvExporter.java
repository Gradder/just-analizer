package com.gradder.justanalizer.service;

import com.gradder.justanalizer.model.LegionBook;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CsvExporter {

    private final DataKeeper dataKeeper;
    private static final String CSV_FILE_NAME = "wordbearers.csv";

    public void writeToCsv() throws FileNotFoundException {

        List<LegionBook> legionBooks = dataKeeper.getLegionBooks();

        File csvOutputFile = new File(CSV_FILE_NAME);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            legionBooks.stream()
                    .map(o -> Arrays.asList(o.getLegionBackground(), o.getLegionesAstartes(), o.getAdvancedReaction()))
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
    }

    public String convertToCSV(List<String> data) {
        return data.stream()
//                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
