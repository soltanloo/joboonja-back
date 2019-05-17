package Joboonja;

import Exceptions.UserException;
import Models.User;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    public static void register(String firstName, String lastName,
                                String jobTitle, String pictureUrl, String bio,
                                String username, String password) throws UserException {
        if (!Database.userMapper.userExists(username)) {
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setJobTitle(jobTitle);
            newUser.setProfilePictureURL(pictureUrl);
            newUser.setBio(bio);
            Database.userMapper.addUser(newUser, username, password);
        }
        else
            throw new UserException("User with username \'" + username + "\' already exists.");
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