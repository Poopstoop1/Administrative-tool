package com.project.Mesa.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class GoogleSheetsConfig {
	private static final String APPLICATION_NAME = "Google Sheets API Java";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String SPREADSHEET_ID = "1VAuTk3wiid_wefj91mual-S4CQrGThk6xVpMNVau_aQ";
    
    public String getSpreadSheetID() {
    	return SPREADSHEET_ID;
    }
    
    public Sheets getSheetsService() throws IOException, GeneralSecurityException {
    	
    	Dotenv dotenv = Dotenv.load();
    			
    	String jsonCredentials = dotenv.get("GOOGLE_CREDENTIALS_JSON");

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(jsonCredentials))
                .createScoped(List.of(SheetsScopes.SPREADSHEETS_READONLY));

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
}
