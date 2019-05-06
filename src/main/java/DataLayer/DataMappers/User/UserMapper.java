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

        st.executeUpdate("CREATE TABLE IF NOT EXISTS User" +
                "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "firstName TEXT NOT NULL," +
                "lastName TEXT NOT NULL," +
                "jobTitle TEXT NOT NULL," +
                "profilePictureURL TEXT," +
                "bio TEXT)");

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
        return "SELECT (count(*) > 0) as found FROM User WHERE id = ?";
    }

    public String getFindAllStatement() {
        return "SELECT * FROM User" +
                " WHERE not id = ?";
    }

    public String getAddStatement() {
        return "INSERT INTO User" +
                " VALUES (?, ?, ?, ?, ?, ?)";
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

    public Boolean userExists(Integer id) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getCheckExistsStatement())
        ) {
            st.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.userExists query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(User user) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddStatement())
        ) {
            st.setString(1, user.getId().toString());
            st.setString(2, user.getFirstName());
            st.setString(3, user.getLastName());
            st.setString(4, user.getJobTitle());
            st.setString(5, user.getProfilePictureURL());
            st.setString(6, user.getBio());
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in SkillMapper.addSkill query.");
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
