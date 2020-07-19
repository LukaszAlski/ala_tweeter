package controllers;

import services.UserManagementService;
import services.implementation.UserManagementServiceImpl;
import utils.ServletsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utils.ServletsUtils.USER_LOGIN_TO_FOLLOW;
import static utils.ServletsUtils.USER_LOGIN_TO_UNFOLLOW;

@WebServlet(name = "UnfollowServlet", value = "/unfollow")
public class UnfollowServlet extends HttpServlet {
    private UserManagementService service;

    @Override
    public void init() throws ServletException {
        service = new UserManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLoginFromSession = ServletsUtils.getUserLoginFromSession(req);
        if (userLoginFromSession == null) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        String userLoginToUnfollow = req.getParameter(USER_LOGIN_TO_UNFOLLOW);
        service.stopFollowing(userLoginFromSession, userLoginToUnfollow);
        req.getRequestDispatcher("users").forward(req, resp);

    }
}
