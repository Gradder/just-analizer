package com.gradder.justanalizer.controller;

import com.gradder.justanalizer.service.CsvExporter;
import com.gradder.justanalizer.service.PdfReader;
import com.gradder.justanalizer.service.TextParser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {

    private final PdfReader pdfReader;
    private final TextParser textParser;
    private final CsvExporter csvExporter;

    @GetMapping("/test")
    String test(){
        pdfReader.readAllPdfs();
        textParser.parseLegion("xvii");
        return "OK";
    }

    @GetMapping("/csv")
    String csv() throws FileNotFoundException {
        csvExporter.writeToCsv();
        return "OK";
    }
}
