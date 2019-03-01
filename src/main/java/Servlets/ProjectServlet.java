package Servlets;

import Exceptions.ProjectNotFoundException;
import Joboonja.Database;
import Joboonja.ProjectManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/*")
public class ProjectServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectId = request.getPathInfo().substring(1);
        try {
            request.setAttribute("project", ProjectManager.getProjectByID(projectId));
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/project.jsp").forward(request, response);
    }
}
