<%--
  Created by IntelliJ IDEA.
  User: thesoli
  Date: 2019-02-27
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${user.id}" /></li>
    <li>first name: <c:out value="${user.firstName}" /></li>
    <li>last name: <c:out value="${user.lastName}" /></li>
    <li>jobTitle: <c:out value="${user.jobTitle}" /></li>
    <li>bio: <c:out value="${user.bio}" /></li>
    <li>
        skills:
        <ul>
            <c:forEach var="skill" items="${skills}">
                <li>
                    <c:out value="${skill.name}"/>:  <c:out value="${skill.point}"/>
                    <%--<c:if></c:if>--%>
                    <form action="/user/endorseskill" method="POST">
                        <input type="hidden" name="skillName" value="${skill.name}">
                        <input type="hidden" name="endorser" value="${myId}">
                        <button>Endorse</button>
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