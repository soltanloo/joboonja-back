import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class Utilities{
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<User> users = new ArrayList<User>();

    public void register(String commandValue) throws IOException {
        User newUser = this.objectMapper.readValue(commandValue, User.class);
        users.add(newUser);
    }

    public void addProject(String commandValue) throws IOException {
        Project newPro = this.objectMapper.readValue(commandValue, Project.class);
        projects.add(newPro);
    }

    public void bid(String commandValue){

    }

    public void auction(String commandValue){
        
    }
}