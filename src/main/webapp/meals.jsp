<%--
  Created by IntelliJ IDEA.
  User: Dmitry
  Date: 023 23.08.20
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h4>Список еды</h4>
<table border="1" width="50%" cellpadding="5">
    <tr>
        <th>Время</th>
        <th>Блюдо</th>
        <th>Калории</th>
    </tr>

    <c:forEach items="${meals}" var="meal">
        <c:choose>
            <c:when test="${meal.excess}">
                <tr bgcolor="red">
            </c:when>
            <c:otherwise>
                <tr bgcolor="green">
            </c:otherwise>
        </c:choose>
      
            <td>
                <p><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH-mm-ss"/></p>
            </td>
            <td>
                <p>  ${meal.description}</p>
            </td>
            <td>
                <p>  ${meal.calories}</p>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
