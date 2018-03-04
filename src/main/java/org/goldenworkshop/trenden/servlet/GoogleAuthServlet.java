package org.goldenworkshop.trenden.servlet;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.goldenworkshop.trenden.controller.AuthContext;
import org.goldenworkshop.trenden.model.User;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleAuthServlet extends HttpServlet {
    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }
    private static String googleOAuth = "1069165987697-c8c4rvl7nc0jpbbuoom8uodjtn4n0nua.apps.googleusercontent.com";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), jacksonFactory)
                .setAudience(Collections.singletonList(googleOAuth))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

            // (Receive idTokenString by HTTPS POST)
        String idTokenString = req.getParameter("idToken");
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
            resp.sendRedirect("/user/protected.html");
        } else {
            AuthContext.get().setUser(null);
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().print("Google token did not work.");
        }
    }
}