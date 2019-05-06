package DataLayer.DataMappers.UserSkill;

import DataLayer.DBCPDBConnectionPool;
import DataLayer.DataMappers.Mapper;
import Joboonja.Database;
import Models.Skill;
import Models.User;

import java.sql.*;
import java.util.ArrayList;

public class UserSkillMapper extends Mapper<Skill, Integer> implements IUserSkillMapper {

    private static final String COLUMNS = " id, lastname, firstname, gpa ";


    public UserSkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();

        String query = "CREATE TABLE IF NOT EXISTS UserSkill " +
                "(name TEXT NOT NULL UNIQUE, " +
                "PRIMARY KEY(name))";

        st.executeUpdate(query);

        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT *" +
                " FROM UserSkill" +
                " WHERE userId = ?";
    }

    @Override
    public String getFindEndorsementsStatement() {
        return "SELECT *" +
                " FROM Endorsement" +
                " WHERE endorsedId = ? and skillName = ?";
    }

    public String getAddEndorsementStatement() {
        return "INSERT INTO Endorsement" +
                " VALUES (?, ?, ?)";
    }

    public String getRemoveSkillStatement() {
        return "DELETE FROM UserSkill" +
                " WHERE userId = ? and skillName = ?";
    }

    public String getAddSkillStatement() {
        return "INSERT INTO UserSkill" +
                " VALUES (?, ?)";
    }

    @Override
    public ArrayList<Skill> getUserSkills(Integer userId) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            st.setString(1, userId.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                ArrayList<Skill> skills = new ArrayList<>();
                int id = 0;
                while (resultSet.next()) {
                    String skillName = resultSet.getString(2);
                    Skill skill = new Skill(skillName, getUserSkillEndorsers(userId, skillName));
                    skill.setId(id);
                    id++;
                    skills.add(skill);
                }
                return skills;
            } catch (SQLException ex) {
                System.out.println("error in UserSkillMapper.getUserSkills query.");
                throw ex;
            }
        }
    }

    @Override
    public ArrayList<Integer> getUserSkillEndorsers(Integer userId, String skillName) throws SQLException {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindEndorsementsStatement())
        ) {
            st.setString(1, userId.toString());
            st.setString(2, skillName);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                ArrayList<Integer> endorsers = new ArrayList<>();
                while (resultSet.next()) {
                    Integer endorserId = resultSet.getInt(1);
                    endorsers.add(endorserId);
                }
                return endorsers;
            } catch (SQLException ex) {
                System.out.println("error in UserSkillMapper.getUserSkills query.");
                throw ex;
            }
        }
    }

    @Override
    protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return new Skill();
    }

    public void addEndorsement(String skillName, Integer endorserId, Integer endorseeId) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddEndorsementStatement())
        ) {
            st.setInt(1, endorserId);
            st.setInt(2, endorseeId);
            st.setString(3, skillName);
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in ProjectMapper.addBid query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSkill(String skillName, Integer userId) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getRemoveSkillStatement())
        ) {
            st.setInt(1, userId);
            st.setString(2, skillName);
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in UserSkillMapper.removeSkill query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserSkill(Integer userId, String skillName) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddSkillStatement())
        ) {
            st.setInt(1, userId);
            st.setString(2, skillName);
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in UserSkillMapper.addUserSkill query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
