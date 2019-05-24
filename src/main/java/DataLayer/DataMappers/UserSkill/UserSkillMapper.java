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

        String query = "CREATE TABLE IF NOT EXISTS `UserSkill` (\n" +
                "  `userId` int(15) NOT NULL,\n" +
                "  `skillName` varchar(50) NOT NULL,\n" +
                "  PRIMARY KEY (`userId`,`skillName`),\n" +
                "  KEY `UserSkill_skillName_fk_idx` (`skillName`),\n" +
                "  CONSTRAINT `UserSkill_skillName_fk` FOREIGN KEY (`skillName`) REFERENCES `Skill` (`name`),\n" +
                "  CONSTRAINT `UserSkill_userId_fk` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";

        st.executeUpdate(query);

        st.executeUpdate("CREATE TABLE IF NOT EXISTS `Endorsement` (\n" +
                "  `endorserId` int(15) NOT NULL,\n" +
                "  `endorsedId` int(15) NOT NULL,\n" +
                "  `skillName` varchar(50) NOT NULL,\n" +
                "  KEY `Endorsement_endorsedId_fk_idx` (`endorsedId`),\n" +
                "  KEY `Endorsement_endorserId_fk_idx` (`endorserId`),\n" +
                "  KEY `Endorsement_skillName_fk_idx` (`skillName`),\n" +
                "  CONSTRAINT `Endorsement_endorsedId_fk` FOREIGN KEY (`endorsedId`) REFERENCES `User` (`id`),\n" +
                "  CONSTRAINT `Endorsement_endorserId_fk` FOREIGN KEY (`endorserId`) REFERENCES `User` (`id`),\n" +
                "  CONSTRAINT `Endorsement_skillName_fk` FOREIGN KEY (`skillName`) REFERENCES `Skill` (`name`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci");

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
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
