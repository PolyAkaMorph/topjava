<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
    <tr>
        <td>Дата</td>
        <td>Описание</td>
        <td>Калории</td>
        <td>Переедание</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="mealTo" items="${mealToList}">
        <tr>
            <td>${mealTo.getFormattedDateTime()}</td>
            <td>${mealTo.getDescription()}</td>
            <td>${mealTo.getCalories()}</td>
            <td>${mealTo.isExcess()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>