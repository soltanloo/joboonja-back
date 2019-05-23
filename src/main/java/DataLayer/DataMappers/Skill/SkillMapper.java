package DataLayer.DataMappers.Skill;

import DataLayer.DBCPDBConnectionPool;
import DataLayer.DataMappers.Mapper;
import Joboonja.Database;
import Models.Skill;
import Models.User;

import java.sql.*;
import java.util.ArrayList;

public class SkillMapper extends Mapper<String, String> implements ISkillMapper {

    private static final String COLUMNS = " id, lastname, firstname, gpa ";


    public SkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();

        String query = "CREATE TABLE IF NOT EXISTS `Skill` (\n" +
                "  `name` varchar(50) NOT NULL,\n" +
                "  PRIMARY KEY (`name`),\n" +
                "  UNIQUE KEY `name_UNIQUE` (`name`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

        st.executeUpdate(query);

        st.close();
        con.close();

    }

    @Override
    protected String getFindStatement() {
        return "SELECT *" +
                " FROM Skill" +
                " WHERE name = ?";
    }

    protected String getAddStatement() {
        return "INSERT OR IGNORE INTO Skill(name) VALUES(?)";
    }

    @Override
    public String getFindAllStatement() {
        return "SELECT * from Skill";
    }

    @Override
    public String getCheckExistsStatement() {
        return "SELECT (count(*) > 0) as found FROM Skill WHERE name = ?";
    }

    @Override
    protected String convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        return rs.getString(1);
    }

    @Override
    public void addSkill(String name) {
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getAddStatement())
        ) {
            st.setString(1, name);
            try {
                st.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("error in SkillMapper.addSkill query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getSystemSkills(){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindAllStatement())
        ) {
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                ArrayList<String> skills = new ArrayList<>();
                while (resultSet.next()) {
                    String skillName = resultSet.getString(1);
                    skills.add(skillName);
                }
                return skills;
            } catch (SQLException ex) {
                System.out.println("error in UserSkillMapper.getUserSkills query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean isAvailable(String name){
        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getCheckExistsStatement())
        ) {
            st.setString(1, name);
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            } catch (SQLException ex) {
                System.out.println("error in SkillMapper.isAvailable query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
