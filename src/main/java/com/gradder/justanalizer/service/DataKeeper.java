package com.gradder.justanalizer.service;

import com.gradder.justanalizer.model.LegionBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataKeeper {

    private final ArrayList<LegionBook> legionBooks = new ArrayList<>();

    public void addLegionBook(LegionBook legionBook) {
        this.legionBooks.add(legionBook);
    }
    public List<LegionBook> getLegionBooks() {
        return legionBooks;
    }
}
