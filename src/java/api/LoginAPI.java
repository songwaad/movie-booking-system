package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
import java.util.Deque;
import java.util.List;
import model.User;
import modelDAO.UserDAO;
import utility.Constants;
import utility.DatabaseConnector;

@WebServlet(name = "Login", urlPatterns = {"/api/login"})
public class LoginAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private UserDAO userDAO = new UserDAO(dbConnector);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        String action = jsonObject.get("action").getAsString();

        try (PrintWriter out = response.getWriter()) {
            if ("register".equals(action)) {
                register(response, gson, jsonObject, out);
            } else if ("login".equals(action)) {
                login(request, response, jsonObject, out);
            }
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response, JsonObject jsonObject, PrintWriter out)
            throws ServletException, IOException {
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        User user = new User();
        password = user.hashPassword(password);

        if (userDAO.validateLogin(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", userDAO.getUserByEmail(email).getUserId());
            session.setAttribute("email", email);
            session.setAttribute("role", userDAO.getUserByEmail(email).getRole());
            session.setMaxInactiveInterval(30 * 60);
            
            System.out.println("-------------- ADMIN ------------" + userDAO.getUserByEmail(email).getRole());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            out.write("{\"message\": \"Login successful\" , \"role\": \"" + userDAO.getUserByEmail(email).getRole() + "\"}");
        } else {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"message\": \"Invalid username or password\"}");
        }
    }

    private void register(HttpServletResponse response, Gson gson, JsonObject jsonObject, PrintWriter out)
            throws ServletException, IOException {
        User user = gson.fromJson(jsonObject, User.class);
        user.setPassword(user.getPassword());
        if (userDAO.addUser(user)) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.write("{\"message\": \"Success\"}");
        } else {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"message\": \"Failed to add user\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        out.write("{\"message\": \"Success\"}");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
