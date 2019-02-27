<%--
  Created by IntelliJ IDEA.
  User: thesoli
  Date: 2019-02-27
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>id: 1</li>
    <li>first name: <%= request.getParameter("firstname")%></li>
    <li>last name: <%= request.getParameter("lastname")%></li>
    <li>jobTitle: <%= request.getParameter("jobTitle")%></li>
    <li>bio: <%= request.getParameter("bio")%></li>
    <li>
        skills:
        <ul>
            <c:forEach var="skill" items="${requestScope("skills")}">
                <li>
                    <c:out value="${skill.name}"/>:  <c:out value="${skill.point}"/>
                    <%--<c:if></c:if>--%>
                    <form action="/user/endorseskill" method="POST">
                        <input type="hidden" name="skillName" value="${skill.name}">
                        <input type="hidden" name="endorser" value="${requestScope("myid")}">
                        <button name="${skill.name}" , value="${}">Endorse</button>
                    </form>
                </li>
            </c:forEach>

            <li>
                CSS: 2
                <!-- no form if already endorsed -->
            </li>
        </ul>
    </li>
</ul>
</body>
</html>