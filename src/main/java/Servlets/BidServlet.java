package Servlets;

import Exceptions.ProjectNotFoundException;
import Exceptions.UserNotFoundException;
import Joboonja.ProjectManager;
import Joboonja.UserManager;
import Models.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/bid")
public class BidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String amount = request.getParameter("bidAmount");
        String bidder = request.getParameter("bidder");
        String prjid = request.getParameter("projectid");
        int bidAmount = Integer.parseInt(amount);
        try {
            ProjectManager.addNewBid(bidAmount, bidder, prjid);
            response.sendRedirect("/project/bid"+ ProjectManager.getProjectByID(prjid));
        }catch (ProjectNotFoundException e){
            e.printStackTrace();
        }catch (UserNotFoundException e){
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/bid.jsp").forward(request, response);
    }
}
