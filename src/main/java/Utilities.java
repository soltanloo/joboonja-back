import Skill.java;
import User.java;
import Project.java;
import Bid.java;
import

public class Utilities{
    public Utilities(){}

    public void register(String commandValue){
        ObjectMapper objectMapper = new ObjectMapper();
        User newUser = objectMapper.readValue(commandValue);
        return newUser;
    }

    public void addProject(String commandValue){

    }
}