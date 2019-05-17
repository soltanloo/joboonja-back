package Servlets;

import DTOs.MessageWithStatusCode;
import DTOs.UserWithMeta;
import Exceptions.SkillException;
import Exceptions.UserException;
import Joboonja.ProjectManager;
import Joboonja.SkillManager;
import Joboonja.UserManager;
import Models.Project;
import Models.Skill;
import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/auth/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = "";
        if (request.getParameterMap().containsKey("lastName"))
            lastName = request.getParameter("lastName");

        String password = request.getParameter("password");
        String username = request.getParameter("username");

        String jobTitle = "";
        if (request.getParameterMap().containsKey("jobTitle"))
            jobTitle = request.getParameter("jobTitle");

        String pictureURL = "";
        if (request.getParameterMap().containsKey("pictureURL"))
            pictureURL = request.getParameter("pictureURL");
                ;
        String bio = "";
        if (request.getParameterMap().containsKey("bio"))
            bio = request.getParameter("bio");

        String hashedPassword = password;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            System.out.println(password);
            System.out.println(username);
            System.out.println(firstName);
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        MessageWithStatusCode messageWithStatusCode = new MessageWithStatusCode();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        try {
            UserManager.register(firstName, lastName, jobTitle, pictureURL, bio, username, hashedPassword);
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage("User is added successfully.");
        } catch (UserException e) {
            response.setStatus(400);
            messageWithStatusCode.setStatusCode(400);
            messageWithStatusCode.setMessage(e.getMessage());
        }
        String message = mapper.writeValueAsString(messageWithStatusCode);
        out.println(message);
    }
}
