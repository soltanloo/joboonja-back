package Joboonja;

import Exceptions.ProjectNotFoundException;
import Exceptions.UserNotFoundException;
import Models.Bid;
import Models.Project;
import Models.Skill;
import Models.User;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectManager {

    public static void addProject(Project newProject) throws IOException {
        if (Database.projects.stream().noneMatch(p -> (p.getId().equals(newProject.getId()))))
            Database.projects.add(newProject);
        else
            System.out.println("Project with ID \'" + newProject.getId() + "\' already exists.");
    }

    public static void bid(Bid newBid) throws IOException {
        Project project = null;
        User user = null;
        for (Project p:
                Database.projects) {
            if (p.getId().equals(newBid.getProject().getId())){
                project = p;
            }
        }
        for (User u:
                Database.users) {
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

    public static void addNewBid(int bidAmount , String userid , String projectid) throws UserNotFoundException,ProjectNotFoundException
    ,IOException{
            User bidUser = UserManager.getUserByID(userid);
            Project bidProject = ProjectManager.getProjectByID(projectid);
            Bid newbid = new Bid(bidUser,bidProject,bidAmount);
            ProjectManager.bid(newbid);
    }

    public static Project getProjectByID(String id) throws ProjectNotFoundException {
        for (Project p :
                Database.projects) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new ProjectNotFoundException(id);
    }

    public static Boolean hasSkills(User user , Project project){
        int containedSkills = 0;
        boolean hasSkill = false;

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

    public static ArrayList<Project> getEligibleProjects(User user) {
        ArrayList<Project> eligibleProjects = new ArrayList<Project>();
        for (Project p :
                Database.projects) {
            if (hasSkills(user, p)) {
                eligibleProjects.add(p);
            }
        }
        return eligibleProjects;
    }

    public static User auction(Project project) throws IOException {
        User winningBidder = new User();
        int maxSum = 0;
        for (Project p :
                Database.projects) {
            if (p.getId().equals(project.getId())){
                for (Bid b :
                        p.getBids()) {
                    int sum = 0;
                    User biddingUser = new User();
                    for (User u :
                            Database.users) {

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