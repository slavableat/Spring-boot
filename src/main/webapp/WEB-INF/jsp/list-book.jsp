<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<style><%@include file="style.css"%></style>
<html>
<head>
    <title>Book Management Application</title>
    <link real="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
<center>
    <h1>Book Management</h1>
    <h2>
        <a href="new">Add New Book</a>
        &nbsp;&nbsp;&nbsp;
        <a href="list">List All Book</a>

    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Books</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Genre</th>
            <th>Authors</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="book" items="${listBook}">
            <tr>
                <td><c:out value="${book.id}" /></td>
                <td><c:out value="${book.name}" /></td>
                <td><c:out value="${book.genre.name}"/></td>
                <td>
                    <c:forEach var="author" items="${book.authors}">
                    <p>${author.name} </p>
                    </c:forEach>
                </td>
                <td>
                    <a href="edit?id=<c:out value='${book.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete/<c:out value='${book.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>