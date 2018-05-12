package org.goldenworkshop.trenden.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.goldenworkshop.trenden.model.User;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleAuthController {
    private static String googleOAuth = "1069165987697-c8c4rvl7nc0jpbbuoom8uodjtn4n0nua.apps.googleusercontent.com";
    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    public User authenticate(String idTokenString) throws IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), jacksonFactory)
                .setAudience(Collections.singletonList(googleOAuth))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)
        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());

            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");


            User user = new User();
            user.setEmail(emailVerified);
            user.setUsername(name);
            user.setLocale(locale);
            user.setPictureUrl(pictureUrl);

            AuthContext.get().setUser(user);
            return user;

        } else {
            AuthContext.get().setUser(null);
        }

        return null;
    }
}

