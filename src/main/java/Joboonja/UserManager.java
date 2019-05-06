package Joboonja;

import Exceptions.UserException;
import Models.User;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    public static void register(User newUser) throws UserException {
        if (!Database.userMapper.userExists(newUser.getId()))
            Database.userMapper.addUser(newUser);
        else
            throw new UserException("User \'" + newUser.getId() + "\' already exists.");
    }
    public static User getUserByID(Integer id) throws UserException {
        return Database.userMapper.find(id);
//        throw new UserException("User with id \'" + id + "\' was not found.");
    }
    public static User getCurrentUser() {
        return Database.currentUser;
    }
    public static ArrayList<User> getUsers(){
        return Database.userMapper.getAllUsers();
    }

    public static ArrayList<User> getUsersByQuery(String query){
        return Database.userMapper.getUsersByQuery(query);
    }


}