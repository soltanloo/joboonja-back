package Joboonja;

import Exceptions.ProjectException;
import Exceptions.UserException;
import Models.Bid;
import Models.Project;
import Models.Skill;
import Models.User;

import java.util.ArrayList;

public class ProjectManager {

    public static void addProject(Project newProject) throws ProjectException {
        if (Database.projects.stream().noneMatch(p -> (p.getId().equals(newProject.getId()))))
            Database.projects.add(newProject);
        else
            throw new ProjectException("Project with ID \'" + newProject.getId() + "\' already exists.");
    }

    public static void addBidToProject(int bidAmount, String userId, String projectId) throws UserException,
            ProjectException {
        User user = UserManager.getUserByID(userId);
        Project project = ProjectManager.getProjectByID(projectId);
        Bid newBid = new Bid(user, project, bidAmount);
        if (project.getBids().stream().noneMatch(bid -> bid.getBiddingUser().getId().equals(userId))) {
            if (project.getBudget() >= newBid.getBidAmount()){
                if (hasSkills(user, project)) {
                    project.addBid(newBid);
                } else {
                    throw new ProjectException("User with id \'" + userId
                            + "\' does not have enough skills to bid on project with id \'" + projectId + "\'.");
                }
            } else {
                throw new ProjectException("Project with id \'" + projectId
                        + "\' has a maximum budget of \'" + project.getBudget() + "\'.");
            }
        } else {
            throw new ProjectException("User with id \'" + userId
                    + "\' has already bidden on project with id \'" + projectId + "\'.");
        }
    }

    public static Project getProjectByID(String id) throws ProjectException {
        for (Project p :
                Database.projects) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new ProjectException(id);
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

    public static User auction(Project project) {
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