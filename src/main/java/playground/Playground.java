package playground;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

public class Playground {

    public static void main(String[] args) {
        try {
            String CREDENTIAL_STORE = "/credentials.json";
            List<String> scopes = List.of(DriveScopes.DRIVE_READONLY);
            String TOKEN_STORE = "src/main/resources/tokens";
            String DRIVE_FOLDER = "---folder_id---";
            int PAGE_SIZE = 10;
            String APPLICATION_NAME = "drive-file-sync";


            NetHttpTransport netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            InputStream credsFile = Playground.class.getResourceAsStream(CREDENTIAL_STORE);
            GoogleClientSecrets clientSecrets = null;
            if (credsFile != null) {
                clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(credsFile));
            }
            GoogleAuthorizationCodeFlow googleAuth = new GoogleAuthorizationCodeFlow.Builder(netHttpTransport, jsonFactory, clientSecrets, scopes)
                    .setDataStoreFactory(new FileDataStoreFactory(new File(TOKEN_STORE)))
                    .setAccessType("offline")
                    .build();

            LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();
            Credential credential = new AuthorizationCodeInstalledApp(googleAuth, localServerReceiver).authorize("user");


            Drive drive = new Drive.Builder(netHttpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
            FileList list = drive.files().list().setSpaces("drive").setQ("parents in '" + DRIVE_FOLDER + "'").setPageSize(PAGE_SIZE).execute();
            for (var file : list.getFiles()) {
                System.out.println(file.getName());
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

}
