package controllers;

import controllers.validation.ValidationError;
import services.UserManagementService;
import services.implementation.UserManagementServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.ServletsUtils.*;

@WebServlet(name = "LoginServlet", urlPatterns = {"", "/login"})
public class LoginServlet extends HttpServlet {
    private UserManagementService service;

    @Override
    public void init() throws ServletException {
        service = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;
        if (req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals(LOGIN)) {
                    login = c.getValue();
                } else if (c.getName().equals(PASSWORD)) {
                    password = c.getValue();
                }
            }
        }
// if login and password are not null it means that client has cookies(LOGIN, PASSWORD) already saved.
// so doPost method can be invoked with login and password taken from them.
        if (login != null && password != null) {
            req.setAttribute(LOGIN, login);
            req.setAttribute(PASSWORD, password);
            doPost(req, resp);
            return;
        }
// in the other case, login.jsp is served to get login and password from user from AS PARAMETERS
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        boolean isRememberChecked = (COOKIE_CHECKED).equals(req.getParameter(REMEMBER));

// if login and password are null it means that doPost was invoked (odwoływać się do) in doGet method with values from cookies
// because parameters can be set only by filling form by user on login page
        if (login == null && password == null) {
            login = (String) req.getAttribute(LOGIN);
            password = (String) req.getAttribute(PASSWORD);
        }

        if (!service.isUserValid(login, password)) {
            List<ValidationError> errors = new ArrayList<>();
            errors.add(new ValidationError(ERROR_LOGIN_HEADER, ERROR_LOGIN_MESSAGE));
            req.setAttribute(ERRORS, errors);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }

        if (isRememberChecked) {
            Cookie loginCookie = new Cookie(LOGIN, login);   // tu jest mechanizm tworzenia ciastek
            loginCookie.setMaxAge(60 * 60);                   // "ożywienie" ciastka, poprzez ustawienie jego czasu życia
            resp.addCookie(loginCookie);
            Cookie passwordCookie = new Cookie(PASSWORD, password);
            passwordCookie.setMaxAge(60 * 60);
            resp.addCookie(passwordCookie);
        }
        req.getRequestDispatcher("users").forward(req, resp);
    }

}

