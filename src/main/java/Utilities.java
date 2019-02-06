import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Utilities{
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<User> users = new ArrayList<User>();

    public void register(String commandValue) throws IOException {
        User newUser = this.objectMapper.readValue(commandValue, User.class);
        users.add(newUser);
        System.out.println(Arrays.toString(users.toArray()));
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

    public String auction(String commandValue) throws IOException {
        Map<String, String> project;
        project = objectMapper.readValue(commandValue, new TypeReference<Map<String, String>>(){});
        String winningBidder = "";
        int maxSum = 0;
        for (Project p :
                projects) {
            if (p.getTitle() == project.get("projectTitle")){
                for (Bid b :
                        p.getBids()) {
                    int sum = 0;
                    String biddingUser = "";
                    for (User u :
                            users) {

                        if (u.getUsername() == b.getBiddingUser()) {
                            biddingUser = b.getBiddingUser();
                            for (Skill s :
                                    u.getSkills()) {
                                for (Skill ps:
                                        p.getSkills()) {
                                    if (ps.getName() == s.getName()){
                                        sum += 10000 * (s.getPoints() - ps.getPoints()) * (s.getPoints() - ps.getPoints());
                                    }
                                }
                            }
                        }
                        sum += (p.getBudget() - b.getBidAmount());
                    }
                    if (sum > maxSum){
                        maxSum = sum;
                        winningBidder = biddingUser;
                    }
                }
            }
        }
        return winningBidder;
    }
}