package com.deborger.rest.webservices.restfulwebservices.service;

import com.deborger.rest.webservices.restfulwebservices.bean.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDAOService {

    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {

        Calendar cal = Calendar.getInstance();
        cal.set(1985, 1, 8); // Assumes MM/dd/yyyy

        users.add(new User(1,"Lucifer", cal.getTime()));
        users.add(new User(2,"Maze", new Date()));
        users.add(new User(3,"Amanadiel", new Date()));

    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if(user.getId()==null) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user:users) {
            if (user.getId()==id) {
                return user;
            }
        } return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId()==id) {
                iterator.remove();
                return user;
            }
        } return null;
    }
}
