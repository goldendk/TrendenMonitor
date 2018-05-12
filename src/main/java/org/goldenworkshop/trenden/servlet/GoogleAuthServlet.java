package org.goldenworkshop.trenden.servlet;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.goldenworkshop.trenden.controller.AuthContext;
import org.goldenworkshop.trenden.controller.GoogleAuthController;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        String idTokenString = req.getParameter("idToken");
        GoogleAuthController controller = new GoogleAuthController();
        User authenticate = controller.authenticate(idTokenString);
        if(authenticate != null){
            resp.sendRedirect("/user/protected.html");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().print("Google token did not work.");
        }
    }
}
