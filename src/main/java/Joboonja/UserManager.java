package Joboonja;

import Exceptions.UserNotFoundException;
import Models.User;

import java.io.IOException;

public class UserManager {

    public static void register(User newUser) throws IOException {
        if (Database.users.stream().noneMatch(u -> (u.getId().equals(newUser.getId()))))
            Database.users.add(newUser);
        else
            System.out.println("Models.User \'" + newUser.getId() + "\' already exists.");
    }

    public static User getUserByID(String id) throws UserNotFoundException {
        for (User u :
                Database.users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        throw new UserNotFoundException(id);
    }

}