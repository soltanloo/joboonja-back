package DataLayer.DataMappers.UserSkill;

import DataLayer.DataMappers.IMapper;
import Models.Skill;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserSkillMapper extends IMapper<Skill, Integer> {

    ArrayList<Skill> getUserSkills(Integer userId) throws SQLException;
    ArrayList<Integer> getUserSkillEndorsers(Integer userId, String skillName) throws SQLException;
    String getFindEndorsementsStatement();
}
