import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Parser {
    private ObjectMapper objectMapper = new ObjectMapper();
    public ArrayList<Project> parseProjects(String prjList) throws IOException {
        return objectMapper.readValue(prjList, new TypeReference<ArrayList<Project>>(){});
    }
    public ArrayList<Skill> parseSkills(String skiList) throws IOException {
        return objectMapper.readValue(skiList, new TypeReference<ArrayList<Skill>>(){});
    }
    public ArrayList<User> parseUsers(String usrList) throws IOException {
        return objectMapper.readValue(usrList, new TypeReference<ArrayList<User>>(){});
    }
}
