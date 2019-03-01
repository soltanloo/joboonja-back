package Servlets;

import Exceptions.UserNotFoundException;
import Joboonja.Database;
import Joboonja.ProjectManager;
import Joboonja.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/project")
public class ProjectsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("projects", ProjectManager.getEligibleProjects(UserManager.getCurrentUser()));
        request.getRequestDispatcher("/projects.jsp").forward(request, response);
    }
}
