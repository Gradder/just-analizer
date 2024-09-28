package com.gradder.justanalizer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradder.justanalizer.model.LegionBook;
import com.gradder.justanalizer.model.LegionBookParserRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
@RequiredArgsConstructor
public class TextParser {

    private final ObjectMapper om;

    private final PdfReader pdfReader;
    private final LegionBookParserRules legionParserRules;
    private final DataKeeper dataKeeper;
    private final Map<String, String> legionsNumberMap;

    public void parseLegion(String number){

        String legionName = legionsNumberMap.get(number);
        String legion = pdfReader.readPdf(number + ".pdf");

        LegionBook legionBook = LegionBook.builder()
                .legionBackground(extractLegionBackground(legion))
                .legionesAstartes(extractLegionesAstartes(legion, number, legionName))
                .advancedReaction(extractAdvancedReaction(legion, legionName))
                .build();

        dataKeeper.addLegionBook(legionBook);
//        System.out.println(legionBook);
    }

    private String extractLegionBackground(String text) {
        return text.split(legionParserRules.getLegionBackground().getEndsWith())[0];
    }
    private String extractLegionesAstartes(String text, String number, String legionName) {
        var separatorForStart = legionParserRules.getLegionesAstartes().getStartsWith() + "\\(" + legionName + "\\)";
        var firstCrop = text.split(separatorForStart)[1];
        var result = firstCrop.split(legionParserRules.getLegionesAstartes().getEndsWith())[0];
        var endIndexToCrop = result.length() - number.length() - 2;
        return result.substring(0, endIndexToCrop);
    }

    private String extractAdvancedReaction(String text, String legionName) {
        var separatorForStart = legionName + legionParserRules.getAdvancedReaction().getStartsWith();
        var firstCrop = text.split(separatorForStart)[1];
        var separatorForEnd = legionName + legionParserRules.getAdvancedReaction().getEndsWith();
        var result = firstCrop.split(separatorForEnd)[0];
        return result;
    }

}
