package Joboonja;

import Exceptions.UserException;
import Models.User;

import java.util.ArrayList;

public class UserManager {
    public static void register(User newUser) throws UserException {
        if (Database.users.stream().noneMatch(u -> (u.getId().equals(newUser.getId()))))
            Database.users.add(newUser);
        else
            throw new UserException("User \'" + newUser.getId() + "\' already exists.");
    }
    public static User getUserByID(String id) throws UserException {
        for (User u :
                Database.users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        throw new UserException("User with id \'" + id + "\' was not found.");
    }
    public static User getCurrentUser() {
        return Database.currentUser;
    }
    public static ArrayList<User> getUsers(){
        return Database.users;
    }
}