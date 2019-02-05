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

    public void bid(String commandValue) throws IOException {
        Bid b = this.objectMapper.readValue(commandValue, Bid.class);
        Project project = null;
        User user = null;
        for (Project p:
             projects) {
            if (p.getTitle() == b.getProjectTitle()){
                project = p;
            }
        }
        for (User u:
                users) {
            if (u.getUsername() == b.getBiddingUser()){
                user = u;
            }
        }
        if (project.getBudget() >= b.getBidAmount()){
            int containedSkills = 0;
            for (Skill s :
                    project.getSkills()) {
                for (Skill us :
                        user.getSkills()) {
                    if (us.getName() == s.getName()){
                        containedSkills++;
                    }
                }
            }
            if (containedSkills == user.getSkills().size()){
                project.addBid(b);
            }
        }
    }

    public void auction(String commandValue){
        
    }
}