<%--
  Created by IntelliJ IDEA.
  User: thesoli
  Date: 2019-02-27
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Project</title>
    </head>
    <body>
        <ul>
            <li>id: <c:out value="${project.id}" /> </li>
            <li>title: <c:out value="${project.title}" /></li>
            <li>description: <c:out value="${project.description}" /></li>
            <li>imageUrl: <img src= '<c:out value="${project.imageUrl}" />' style="width: 50px; height: 50px;" ></li>
            <li>budget: <c:out value="${project.budget}" /></li>
        </ul>
        <c:choose>
            <c:when test="${hasBidden}">
                You have already bidden on this project.
            </c:when>
            <c:when test="${!hasBidden}">
                <form action="/project/bid" method="POST">
                    <label for="bidAmount">Bid Amount:</label>
                    <input type="number" name="bidAmount" id="bidAmount">
                    <input type="hidden" name="projectId" value="${project.id}">
                    <button>Bid</button>
                </form>
            </c:when>
        </c:choose>
    </body>
</html>