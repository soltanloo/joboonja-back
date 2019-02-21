import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Manager {
    private static Manager managerInstance = null;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<String> skills = new ArrayList<String>();
    private User currentUser;

    public static Manager getInstance()
    {
        if (managerInstance == null)
            managerInstance = new Manager();

        return managerInstance;
    }

    public void addSkill(String skill) {
        if (!skills.stream().anyMatch(s -> (s.equals(skill))))
            skills.add(skill);
        else
            System.out.println("Skill \'" + skill + "\' already exists.");
    }

    public class UserManager {

        void register(User newUser) throws IOException {
            if (!users.stream().anyMatch(u -> (u.getId().equals(newUser.getId()))))
                users.add(newUser);
            else
                System.out.println("User \'" + newUser.getId() + "\' already exists.");
        }

        User getUserByID(String id){
            for (User u :
                    users) {
                if (u.getId() == id) {
                    return u;
                }
            }
            //TODO: throw new UserNotFoundException(id);
            return null;
        }

    }

    public class ProjectManager {

        void addProject(Project newProject) throws IOException {
            if (!projects.stream().anyMatch(p -> (p.getId().equals(newProject.getId()))))
                projects.add(newProject);
            else
                System.out.println("Project with ID \'" + newProject.getId() + "\' already exists.");
        }

        void bid(Bid newBid) throws IOException {
            Project project = null;
            User user = null;
            for (Project p:
                    projects) {
                if (p.getId().equals(newBid.getProject().getId())){
                    project = p;
                }
            }
            for (User u:
                    users) {
                if (u.getId().equals(newBid.getBiddingUser().getId())){
                    user = u;
                }
            }
            User finalUser = user;
            if (project.getBids().stream().anyMatch(bid -> bid.getBiddingUser().getId().equals(finalUser.getId())))
                System.out.println("User with ID \'" + user.getId() + "\' has already bidden on this project.");
            else if (project.getBudget() >= newBid.getBidAmount()){
                if (hasSkills(user, project)) {
                    project.addBid(newBid);
                }
            }
        }

        Project getProjectByID(String id) {
            for (Project p :
                    projects) {
                if (p.getId() == id) {
                    return p;
                }
            }
            //TODO: throw new ProjectNotFoundException(id);
            return null;
        }

        Boolean hasSkills(User user , Project project){
            int containedSkills = 0;
            Boolean hasSkill = false;

            for (Skill s :
                    project.getSkills()) {
                if (user.getSkills().stream().anyMatch(us -> (us.getName().equals(s.getName()) && us.getPoint() >= s.getPoint()))) {
                    containedSkills++;
                }
            }
            if (containedSkills == project.getSkills().size()) {
                hasSkill = true;
            }
        return hasSkill;
        }

        ArrayList<Project> getEligibleProjects() {
            //TODO: get user as an argument
            ArrayList<Project> eligibleProjects = new ArrayList<Project>();
            for (Project p :
                    projects) {
                if (hasSkills(currentUser, p)) {
                    eligibleProjects.add(p);
                }
            }
            return eligibleProjects;
        }

        User auction(Project project) throws IOException {
            User winningBidder = new User();
            int maxSum = 0;
            for (Project p :
                    projects) {
                if (p.getId().equals(project.getId())){
                    for (Bid b :
                            p.getBids()) {
                        int sum = 0;
                        User biddingUser = new User();
                        for (User u :
                                users) {

                            if (u.getId().equals(b.getBiddingUser().getId())) {
                                biddingUser = b.getBiddingUser();
                                for (Skill s :
                                        u.getSkills()) {
                                    for (Skill ps:
                                            p.getSkills()) {
                                        if (ps.getName().equals(s.getName())){
                                            sum += 10000 * (s.getPoint() - ps.getPoint()) * (s.getPoint() - ps.getPoint());
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
}
