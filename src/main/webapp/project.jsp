<%--
  Created by IntelliJ IDEA.
  Models.User: thesoli
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
    <title>Models.Project</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${project.id}" /> </li>
    <li>title: <c:out value="${project.title}" /></li>
    <li>description: <c:out value="${project.description}" /></li>
    <li>imageUrl: <img src= '<c:out value="${project.imageUrl}" />' style="width: 50px; height: 50px;" ></li>
    <li>budget: <c:out value="${project.budget}" /></li>
</ul>

<!-- display form if user has not bidded before -->
<form action="/project/bid" method="POST">
    <label for="bidAmount">Models.Bid Amount:</label>
    <input type="number" name="bidAmount">
    <input type="hidden" name="bidder" value="${myId}">
    <button>Submit</button>
</form>
</body>
</html>