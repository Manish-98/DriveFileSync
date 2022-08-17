package client;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleClient {
    private final List<String> scopes = List.of(DriveScopes.DRIVE_READONLY);
    private final String CREDENTIAL_STORE = "/credentials.json";
    private final String TOKEN_STORE = "src/main/resources/tokens";


    public Credential getCredentials() {
        try {
            NetHttpTransport netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            InputStream credentialsFile = Utilities.getResourceAsStream(CREDENTIAL_STORE);
            GoogleClientSecrets secrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(credentialsFile));

            GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(netHttpTransport, jsonFactory, secrets, scopes)
                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKEN_STORE)))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();

            return new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, localServerReceiver).authorize("user");
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
    }
}
