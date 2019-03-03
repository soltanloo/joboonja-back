package Servlets;

import Exceptions.ProjectException;
import Joboonja.ProjectManager;
import Joboonja.UserManager;
import Models.Project;
import Models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/*")
public class ProjectServlet extends HttpServlet {

    private User currentUser = UserManager.getCurrentUser();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo().equals("/")) {
            response.sendRedirect("/project");
        } else {
            String projectId = request.getPathInfo().substring(1);
            try {
                Project project = ProjectManager.getProjectByID(projectId);
                if (ProjectManager.hasSkills(currentUser, project)) {
                    request.setAttribute("project", project);
                    request.setAttribute("hasBidden", project.getBids().stream()
                            .anyMatch(bid -> bid.getBiddingUser().getId().equals(currentUser.getId())));
                    request.getRequestDispatcher("/project.jsp").forward(request, response);
                } else {
                    response.setStatus(403);
                    request.setAttribute("message",
                            "You don't have enough skills to access project with id \'" + projectId + "\'.");
                    request.getRequestDispatcher("/403.jsp").forward(request, response);
                }
            } catch (ProjectException e) {
                response.setStatus(404);
                request.setAttribute("message",
                        e.getMessage());
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }

        }
    }
}
