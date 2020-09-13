<%--
  Created by IntelliJ IDEA.
  User: Dmitry
  Date: 030 30.08.20
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Meals</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>Meal Id</th>
        <th>Description</th>
        <th>Calories</th>
        <th>DateTime</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <c:choose>
            <c:when test="${meal.excess}">
                <tr bgcolor="red">
            </c:when>
            <c:otherwise>
                <tr bgcolor="green">
            </c:otherwise>
        </c:choose>
        <td><c:out value="${meal.id}" /></td>
        <td><c:out value="${meal.description}" /></td>
        <td><c:out value="${meal.calories}" /></td>
        <td><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH-mm-ss"/></td>
        <td><a href="MealController?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
        <td><a href="MealController?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="MealController?action=insert">Add Meal</a></p>
</body>
</html><tbody>
