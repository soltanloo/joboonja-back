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
            <li>
                HTML: 2
                <form action="" method="">
                    <button>Endorse</button>
                </form>
            </li>
            <li>
                Java: 1
                <form action="" method="">
                    <button>Endorse</button>
                </form>
            </li>
            <li>
                CSS: 2
                <!-- no form if already endorsed -->
            </li>
        </ul>
    </li>
</ul>
</body>
</html>