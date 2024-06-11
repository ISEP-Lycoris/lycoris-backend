package com.lycoris.lycorisbackend.controller;

import com.lycoris.lycorisbackend.service.GoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoogleSheetsController {

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @GetMapping("/get-sheet-data")
    public List<List<Object>> getSheetData() {
        try {
            // Call the service method without passing parameters
            return googleSheetsService.getSpreadsheetData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
