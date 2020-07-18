package dao_dataAccessObject;

import model.AppUser;

import java.util.HashSet;

public interface UserDao {
    HashSet<AppUser> getAll();

    void saveUser(AppUser user);

    void deleteUser(long id);

    AppUser getUserByEmail(String email);

    AppUser getUserByLogin(String login);

    HashSet<AppUser> getUsersByName(String name);

    HashSet<AppUser> getFollowedUser(String login);

    HashSet<AppUser> getNotFollowedUser(String login);

    HashSet<AppUser> getFollowers(String login);

    void follow(String currentUserLogin, String userLoginToFollow);

    void stopFollowing(String currentUserLogin, String userLoginTOStopFollow);
}
