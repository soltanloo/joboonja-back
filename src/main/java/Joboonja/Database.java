package Joboonja;

import DataLayer.DataMappers.Project.ProjectMapper;
import DataLayer.DataMappers.Skill.SkillMapper;
import DataLayer.DataMappers.User.UserMapper;
import DataLayer.DataMappers.UserSkill.UserSkillMapper;
import Models.Project;
import Models.Skill;
import Models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
    public static UserMapper userMapper;
    public static UserSkillMapper userSkillMapper;
    public static SkillMapper skillMapper;
    public static ProjectMapper projectMapper;

    static {
        try {
            userMapper = new UserMapper();
            userSkillMapper = new UserSkillMapper();
            skillMapper = new SkillMapper();
            projectMapper = new ProjectMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static Database databaseInstance = null;
    static User currentUser;

    private Database() throws SQLException {
        currentUser = userMapper.find(1);
    }

    public static Database getInstance() throws SQLException {
        if (databaseInstance == null)
            databaseInstance = new Database();

        return databaseInstance;
    }

}
