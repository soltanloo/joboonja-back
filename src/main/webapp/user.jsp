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
            <c:forEach var="skill" items="${user.skills}">
                <li>
                    <c:out value="${skill.name}"/>:  <c:out value="${skill.point}"/>
                    <c:choose>
                        <c:when test="${isCurrentUser}">
                            <form action="/user/removeskill" method="POST">
                                <input type="hidden" name="skillName" value="${skill.name}">
                                <button>Delete</button>
                            </form>
                        </c:when>
                        <c:when test="${!isCurrentUser}">
                            <form action="/user/endorseskill" method="POST">
                                <input type="hidden" name="skillName" value="${skill.name}">
                                <button>Endorse</button>
                            </form>
                        </c:when>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>
    </li>
    <c:if test="${isCurrentUser && !addableSkills.isEmpty()}">
        Add a skill:
        <form action="/user/addskill" method="POST">
            <select name="skillName">
                <c:forEach var="skill" items="${addableSkills}">
                    <option value="${skill.name}"><c:out value="${skill.name}"/></option>
                </c:forEach>
            </select>
            <button>Add</button>
        </form>
    </c:if>
</ul>
</body>
</html>