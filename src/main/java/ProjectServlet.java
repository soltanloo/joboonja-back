import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/*")
public class ProjectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Manager.getInstance();
            String id = request.getPathInfo();
            String project_id = id.substring(1);
            //System.out.println(project_id);
            request.setAttribute("project", Manager.projectManager.getProjectByID(project_id));
            //request.setAttribute("skills", Manager.projectManager.getProjectByID(project_id).getSkills());
        } catch (ProjectNotFoundException e) {
            e.printStackTrace();
        }
        //request.setAttribute("myId", 1);
        request.getRequestDispatcher("/project.jsp").forward(request, response);
    }
}
