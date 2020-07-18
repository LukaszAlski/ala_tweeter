package services.implementation;

import dao_dataAccessObject.UserDao;
import dao_dataAccessObject.implementation.AppUserDao;
import model.AppUser;
import services.UserManagementService;

import javax.persistence.NoResultException;
import java.util.Set;

public class UserManagementServiceImpl implements UserManagementService {
    private UserDao dao;

    // TODO implement dependency injection
    public UserManagementServiceImpl() {
        dao = new AppUserDao();
    }

    @Override
    public void saveUser(AppUser appUser) {
        dao.saveUser(appUser);
    }

    @Override
    public void deleteUser(AppUser appUser) {
        dao.deleteUser(appUser.getId());
    }

    @Override
    public void follow(String currentUserLogin, String userLoginToFollow) {
        dao.follow(currentUserLogin, userLoginToFollow);
    }

    @Override
    public void stopFollowing(String currentUserLogin, String userLoginToUnFollow) {
        dao.stopFollowing(currentUserLogin, userLoginToUnFollow);
    }

    @Override
    public Set<AppUser> getFollowedUsers(String currentUserLogin) {
        return dao.getFollowedUser(currentUserLogin);
    }

    @Override
    public Set<AppUser> getNotFollowedUsers(String currentUserLogin) {
        return dao.getNotFollowedUser(currentUserLogin);
    }

    @Override
    public boolean isUserValid(String login, String password) {
        try {
            AppUser userByLogin = dao.getUserByLogin(login);
            if (userByLogin.getPassword().equals(password)) {
                return true;
            }
            return false;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public boolean isUserLoginAvailable(String login) {
        try {
            dao.getUserByLogin(login);
            return false;
        } catch (NoResultException e) {
            return true;
        }
    }

    @Override
    public boolean isUserMailAvailable(String email) {
        try {
            dao.getUserByEmail(email);
            return false;
        } catch (NoResultException e) {
            return true;
        }
    }
}
