package Joboonja;

import Exceptions.BidException;
import Exceptions.ProjectException;
import Exceptions.UserException;
import Models.Bid;
import Models.Project;
import Models.Skill;
import Models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectManager {

    public static void addProject(Project newProject) throws ProjectException {
        if (!Database.projectMapper.projectExists(newProject.getId()))
            Database.projectMapper.addProject(newProject);
        else
            throw new ProjectException("Project with ID \'" + newProject.getId() + "\' already exists.");
    }

    public static void addBidToProject(int bidAmount, Integer userId, String projectId) throws UserException,
            ProjectException, BidException {
        User user = UserManager.getUserByID(userId);
        Project project = ProjectManager.getProjectByID(projectId);
        Bid newBid = new Bid(userId, projectId, bidAmount);
        if (project.getBids().stream().noneMatch(bid -> bid.getUserId() == userId)) {
            if (project.getBudget() >= newBid.getBidAmount()){
                if (hasSkills(user, project)) {
                    Database.projectMapper.addBid(newBid);
                } else {
                    throw new BidException("User with id \'" + userId
                            + "\' does not have enough skills to bid on project with id \'" + projectId + "\'.");
                }
            } else {
                throw new BidException("Project with id \'" + projectId
                        + "\' has a maximum budget of \'" + project.getBudget() + "\'.");
            }
        } else {
            throw new BidException("User with id \'" + userId
                    + "\' has already bidden on project with id \'" + projectId + "\'.");
        }
    }

    public static Project getProjectByID(String id) throws ProjectException {
        return Database.projectMapper.find(id);
    }

    public static Boolean hasSkills(User user, Project project){
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

    public static ArrayList<Project> getEligibleProjects(User user, Integer page, Integer size) {
        ArrayList<Project> eligibleProjects = new ArrayList<Project>();
        for (Project p :
                Database.projectMapper.getPaginatedProjects(user.getId(), page, size)) {
//            if (hasSkills(user, p)) {
                eligibleProjects.add(p);
//            }
        }
        return eligibleProjects;
    }

    public static ArrayList<Project> getProjectsByQuery(User user, String query) {
        ArrayList<Project> eligibleProjects;
        eligibleProjects = Database.projectMapper.getProjectsByQuery(user.getId(), query);
        return eligibleProjects;
    }

    public static User auction(Project p) {
        User winningBidder = new User();
        winningBidder.setId(0);
        int maxSum = 0;
//        for (Project p :
//                Database.projectMapper.getAllProjects()) {
//            if (p.getId().equals(project.getId())){
                for (Bid b :
                        p.getBids()) {
                    int sum = 0;
                    User biddingUser = Database.userMapper.find(b.getUserId());
                    for (User u :
                            Database.userMapper.getAllUsers()) {

                        if (u.getId() == b.getUserId()) {
                            biddingUser = Database.userMapper.find(b.getUserId());
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
//            }
//        }
        return winningBidder;
    }
}