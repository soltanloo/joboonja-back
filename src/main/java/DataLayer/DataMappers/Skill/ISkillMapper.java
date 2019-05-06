package DataLayer.DataMappers.Skill;

import DataLayer.DataMappers.IMapper;
import Models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ISkillMapper extends IMapper<String, String> {
    void addSkill(String name) throws SQLException;
    ArrayList<String> getSystemSkills() throws  SQLException;
    String getFindAllStatement();
    String getCheckExistsStatement();
}
