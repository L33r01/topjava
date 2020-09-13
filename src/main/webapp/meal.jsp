<%--
  Created by IntelliJ IDEA.
  User: Dmitry
  Date: 030 30.08.20
  Time: 11:56
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
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new user</title>
</head>
<body>
<script>
    $(function() {
        $('input[name=dateTime').datepicker();
    });
</script>

<form method="POST" action='MealController' name="frmAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="id"
                     value="<c:out value="${meal.id}" />" /> <br />
    Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
    Callories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />
    DateTime : <input
        type="datetime-local" name="dateTime"
        value="<javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH-mm-ss"/>" /> <br />
    <input
        type="submit" value="Submit" />
</form>
</body>
</html>
