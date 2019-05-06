package Servlets;

import DTOs.MessageWithStatusCode;
import DTOs.ProjectWithBidStatus;
import Exceptions.ProjectException;
import Joboonja.ProjectManager;
import Joboonja.UserManager;
import Models.Project;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/projects")
public class ProjectsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (request.getParameter("id") == null) {
            ArrayList<Project> projects;
            String projectsJson;
            if (request.getParameter("query") == null) {
                projects = ProjectManager.getEligibleProjects(UserManager.getCurrentUser(),
                        Integer.parseInt(request.getParameter("page")),
                        Integer.parseInt(request.getParameter("size")));
            } else {
                projects = ProjectManager.getProjectsByQuery(UserManager.getCurrentUser(),
                        request.getParameter("query"));
            }
            projectsJson = mapper.writeValueAsString(projects);
            out.println(projectsJson);
        } else {
            String projectId = request.getParameter("id");
            try {
                ProjectWithBidStatus projectWithBidStatus = new ProjectWithBidStatus();
                Project project = ProjectManager.getProjectByID(projectId);

//                if (ProjectManager.hasSkills(UserManager.getCurrentUser(), project)) {
                if (true) {
                    projectWithBidStatus.setProject(project);
                    Boolean hasBidden = project.getBids().stream()
                            .anyMatch(bid -> bid.getUserId() == UserManager.getCurrentUser().getId());
                    projectWithBidStatus.setHasBidden(hasBidden);
                    String projectJson = mapper.writeValueAsString(projectWithBidStatus);
                    out.println(projectJson);
                } else {
                    MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
                    messageWithStatusCode.setStatusCode(403);
                    messageWithStatusCode.setMessage("You don't have enough skills to access project with id \'" + projectId + "\'.");
                    response.setStatus(403);
                    String message = mapper.writeValueAsString(messageWithStatusCode);
                    out.println(message);
                }
            } catch (ProjectException e) {
                MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
                messageWithStatusCode.setStatusCode(404);
                messageWithStatusCode.setMessage(e.getMessage());
                response.setStatus(404);
                String message = mapper.writeValueAsString(messageWithStatusCode);
                out.println(message);
            }
        }
    }
}
