<%--
  Created by IntelliJ IDEA.
  User: thesoli
  Date: 2019-02-27
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Projects</title>
    <style>
        table {
            text-align: center;
            margin: 0 auto;
        }
        td {
            min-width: 300px;
            margin: 5px 5px 5px 5px;
            text-align: center;
        }
    </style>
</head>
<body>
    <table>
        <tr>
            <th>id</th>
            <th>title</th>
            <th>budget</th>
        </tr>
        <c:forEach var="project" items="${projects}">
            <tr>
                <td><c:out value="${project.id}" /></td>
                <td><c:out value="${project.title}" /></td>
                <td><c:out value="${project.budget}" /></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>