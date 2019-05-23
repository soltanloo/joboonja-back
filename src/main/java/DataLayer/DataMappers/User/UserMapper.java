package DataLayer.DataMappers.User;

import DataLayer.DBCPDBConnectionPool;
import DataLayer.DataMappers.Mapper;
import Joboonja.Database;
import Joboonja.UserManager;
import Models.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

public class UserMapper extends Mapper<User, Integer> implements IUserMapper {

    private static final String COLUMNS = " id, lastname, firstname, gpa ";


    public UserMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();

        st.executeUpdate("CREATE TABLE IF NOT EXISTS `User` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `firstName` varchar(50) NOT NULL,\n" +
                "  `lastName` varchar(50) DEFAULT NULL,\n" +
                "  `jobTitle` varchar(50) DEFAULT NULL,\n" +
                "  `profilePictureURL` varchar(200) DEFAULT NULL,\n" +
                "  `bio` varchar(2000) DEFAULT NULL,\n" +
                "  `username` varchar(50) NOT NULL,\n" +
                "  `password` varchar(50) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `id_UNIQUE` (`id`),\n" +
                "  UNIQUE KEY `username_UNIQUE` (`username`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci");

        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT *" +
                " FROM User" +
                " WHERE id = ?";
    }

    @Override
    public String getCheckExistsStatement() {
        return "SELECT (count(*) > 0) as found FROM User WHERE username = ?";
    }

    public String getCheckPasswordStatement() {
        return "SELECT (count(*) > 0) as found FROM User WHERE username = ? and password = ?";
    }

    public String getFindAllStatement() {
        return "SELECT * FROM User" +
                " WHERE not id = ?";
    }

    public String getFindByUsernameStatement() {
        return "SELECT * FROM User" +
                " WHERE username = ?";
    }

    public String getAddUserStatement() {
        return "INSERT INTO User (firstName, lastName, jobTitle, profilePictureURL, bio, username, password)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String getFindQueriedStatement(String query) {
        return "SELECT * FROM User" +
                " WHERE (firstName LIKE '%" + query +
                "%' or lastName LIKE '%" + query + "%') and not id = ?";
    }


    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                Database.userSkillMapper.getUserSkills(rs.getInt(1)),
                rs.getString(6)
        );
    }

    public Boolean userExists(String username) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getCheckExistsStatement())
        ) {
            st.setString(1, username);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.userExists query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean isValidPassword(String username, String password) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getCheckPasswordStatement())
        ) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.userExists query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(User user, String username, String password) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddUserStatement());
        ) {
            st.setString(1, user.getFirstName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getJobTitle());
            st.setString(4, user.getProfilePictureURL());
            st.setString(5, user.getBio());
            st.setString(6, username);
            st.setString(7, password);
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in UserMapper.addUser query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers(){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindAllStatement())
        ) {
            ResultSet resultSet;
            try {
                st.setInt(1, UserManager.getCurrentUser().getId());
                resultSet = st.executeQuery();
                ArrayList<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = convertResultSetToDomainModel(resultSet);
                    users.add(user);
                }
                return users;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getAllUsers query.");
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByUsername(String username){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindByUsernameStatement())
        ) {
            st.setString(1, username);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> getUsersByQuery(String query){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindQueriedStatement(query))
        ) {
            ResultSet resultSet;
            try {
                st.setInt(1, UserManager.getCurrentUser().getId());
                resultSet = st.executeQuery();
                ArrayList<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = convertResultSetToDomainModel(resultSet);
                    users.add(user);
                }
                return users;
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.getAllUsers query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
