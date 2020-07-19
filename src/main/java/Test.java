import dao_dataAccessObject.UserDao;
import dao_dataAccessObject.implementation.AppUserDao;
import model.AppUser;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        UserDao userDao = new AppUserDao();
        AppUser user1 = new AppUser();
        user1.setName("Lukasz");
        user1.setLogin("Lukasz");
        user1.setLastName("Alski");
        user1.setEmail("lukasz.alski@gmail.com");
        user1.setPassword("1234");
        user1.setDateOfRegistration(new Date());
        userDao.saveUser(user1);

        AppUser user2 = new AppUser();
        user2.setName("Arek");
        user2.setLogin("Arek");
        user2.setLastName("Arkowski");
        user2.setEmail("arek.arkowski@gmail.com");
        user2.setPassword("1234");
        user2.setDateOfRegistration(new Date());
        userDao.saveUser(user2);

        userDao.follow("Lukasz", "Arek");
        AppUser Lukasz = userDao.getUserByLogin("Lukasz");
        Set<AppUser> followers = Lukasz.getFollowers();
        userDao.follow("Lukasz", "Arek");
        AppUser Arek = userDao.getUserByLogin("Arek");
        HashSet<AppUser> followers1 = userDao.getFollowers(Arek.getLogin());

        System.out.println(followers.size());
        userDao.saveUser(Arek);

        returnTest();
    }


    public static void returnTest() {
    if (true) {
        System.out.println("IN if");
        return;
    }
        System.out.println("outside");
    }

}
