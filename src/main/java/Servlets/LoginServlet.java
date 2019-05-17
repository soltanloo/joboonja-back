package Servlets;

import DTOs.MessageWithStatusCode;
import Exceptions.UserException;
import Joboonja.UserManager;
import Models.User;
import ServerConfig.PrivateKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String hashedPassword = password;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
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
            User user = UserManager.validateCredentials(username, hashedPassword);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, 1);
            String jws = Jwts.builder()
                    .setIssuer("joboonja")
                    .setIssuedAt(new Date())
                    .setExpiration(cal.getTime())
                    .claim("userId", user.getId())
                    .claim("username", username)
                    .signWith(PrivateKey.getKey())
                    .compact();
            //FIXME: change message field to token field
            messageWithStatusCode.setStatusCode(200);
            messageWithStatusCode.setMessage(jws);
        } catch (UserException e) {
            response.setStatus(403);
            messageWithStatusCode.setStatusCode(403);
            messageWithStatusCode.setMessage(e.getMessage());
        }
        String message = mapper.writeValueAsString(messageWithStatusCode);
        out.println(message);
    }
}
