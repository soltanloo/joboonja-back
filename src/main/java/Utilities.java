import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class Utilities{
    private ObjectMapper objectMapper;
    private ArrayList<Project> projects;
    private ArrayList<User> users;
    public Utilities(){
        this.objectMapper = new ObjectMapper();
        this.projects = new ArrayList<Project>();
        this.users = new ArrayList<User>();
    }

    public void register(String commandValue) throws IOException {
        User newUser = this.objectMapper.readValue(commandValue, User.class);
        users.add(newUser);
    }

    public void addProject(String commandValue){
        Project newPro = this.objectMapper.readValue(commandValue, Project.class);
        projects.add(newPro);
    }

    public void bid(String commandValue){

    }

    public void auction(String commandValue){
        
    }
}