package Joboonja;

import Exceptions.UserNotFoundException;
import Models.User;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.ArrayList;

public class UserManager {

    public static void register(User newUser) throws IOException {
        if (Database.users.stream().noneMatch(u -> (u.getId().equals(newUser.getId()))))
            Database.users.add(newUser);
        else
            System.out.println("User \'" + newUser.getId() + "\' already exists.");
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
    public static User getCurrentUser() {
        return Database.currentUser;
    }

    public static ArrayList<User> getUsers(){return Database.users;}
}