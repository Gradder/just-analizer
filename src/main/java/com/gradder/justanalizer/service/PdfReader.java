package com.gradder.justanalizer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class PdfReader {
    public static final String PATH_TO_PDF = "src/main/resources/pdf/";
    private final ArrayList<String> pdfFileNames = new ArrayList<>();

    public PdfReader() {
        var folder = new File(PATH_TO_PDF);
        var listOfFiles = folder.listFiles();
        Optional.ofNullable(listOfFiles).ifPresent(
                files -> Arrays.stream(files)
                        .filter(File::isFile)
                        .forEach(filename -> pdfFileNames.add(filename.getName())));
    }


    public void readAllPdfs() {
        pdfFileNames.forEach(this::readPdf);
    }

    public String readPdf(String filename) {
        if ((filename != null) && !filename.isEmpty()) {
            var pdfFile = new File(PATH_TO_PDF + filename);

            try (PDDocument doc = PDDocument.load(pdfFile)) {

                var stripper = new PDFTextStripper();

//                stripper.setStartPage(0);
//                stripper.setEndPage(1);
//                String pageText = stripper.getText(doc);
                var text = stripper.getText(doc);

                return text;
//                System.out.println("Text size: " + text.length() + " characters:");
//                System.out.println(text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
