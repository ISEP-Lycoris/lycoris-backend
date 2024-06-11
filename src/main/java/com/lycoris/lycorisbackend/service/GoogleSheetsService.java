package com.lycoris.lycorisbackend.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {

    private static final String APPLICATION_NAME = "backend";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";

    // Define the constant spreadsheet ID
    private static final String SPREADSHEET_ID = "1zeyNiu0xAZvWD0IRiE0P6fWkpDb5PTNQzNlUeyWcqdM";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
        FileInputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public List<List<Object>> getSpreadsheetData() throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Use the constant spreadsheet ID
        String spreadsheetId = SPREADSHEET_ID;

        ValueRange response = service.spreadsheets().values().get(spreadsheetId, "A:D").execute(); // Fetch all columns
        List<List<Object>> allData = response.getValues();

        // Check if there is any data
        if (allData == null || allData.isEmpty()) {
            return Collections.emptyList();
        }

        List<List<Object>> filteredData = new ArrayList<>();

        // Start from the second row (index 1) to skip the header
        for (int i = 1; i < allData.size(); i++) {
            List<Object> row = allData.get(i);
            if (row != null && row.size() > 1) { // Check if there are at least two columns
                List<Object> filteredRow = new ArrayList<>();
                // Start from the second column (index 1) to skip the first column
                for (int j = 1; j < row.size(); j++) {
                    filteredRow.add(row.get(j));
                }
                filteredData.add(filteredRow);
            }
        }

        return filteredData;
    }
}
