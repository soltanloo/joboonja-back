import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Auctioneer {
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<User> users = new ArrayList<User>();

    void register(String commandValue) throws IOException {
        User newUser = this.objectMapper.readValue(commandValue, User.class);
        if (!users.stream().anyMatch(u -> (u.getUsername().equals(newUser.getUsername()))))
            users.add(newUser);
        else
            System.out.println("User \'" + newUser.getUsername() + "\' already exists.");
    }

    void addProject(String commandValue) throws IOException {
        Project newPro = this.objectMapper.readValue(commandValue, Project.class);
        if (!projects.stream().anyMatch(p -> (p.getTitle().equals(newPro.getTitle()))))
            projects.add(newPro);
        else
            System.out.println("Project \'" + newPro.getTitle() + "\' already exists.");
    }

    void bid(String commandValue) throws IOException {
        Bid b = this.objectMapper.readValue(commandValue, Bid.class);
        Project project = null;
        User user = null;
        for (Project p:
             projects) {
            if (p.getTitle().equals(b.getProjectTitle())){
                project = p;
            }
        }
        for (User u:
                users) {
            if (u.getUsername().equals(b.getBiddingUser())){
                user = u;
            }
        }
        User finalUser = user;
        if (project.getBids().stream().anyMatch(bid -> bid.getBiddingUser().equals(finalUser.getUsername())))
            System.out.println("User \'" + user.getUsername() + "\' has already bidden on this project.");
        else if (project.getBudget() >= b.getBidAmount()){
            int containedSkills = 0;
            for (Skill s :
                    project.getSkills()) {
                if (user.getSkills().stream().anyMatch(us -> (us.getName().equals(s.getName()) && us.getPoints() >= s.getPoints()))) {
                    containedSkills++;
                }
            }
            if (containedSkills == project.getSkills().size()){
                project.addBid(b);
                System.out.println("Bid added.");
            }
        }
    }

    String auction(String commandValue) throws IOException {
        Map<String, String> project;
        project = objectMapper.readValue(commandValue, new TypeReference<Map<String, String>>(){});
        String winningBidder = "";
        int maxSum = 0;
        for (Project p :
                projects) {
            if (p.getTitle().equals(project.get("projectTitle"))){
                for (Bid b :
                        p.getBids()) {
                    int sum = 0;
                    String biddingUser = "";
                    for (User u :
                            users) {

                        if (u.getUsername().equals(b.getBiddingUser())) {
                            biddingUser = b.getBiddingUser();
                            for (Skill s :
                                    u.getSkills()) {
                                for (Skill ps:
                                        p.getSkills()) {
                                    if (ps.getName().equals(s.getName())){
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