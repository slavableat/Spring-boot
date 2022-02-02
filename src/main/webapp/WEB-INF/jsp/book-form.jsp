<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style><%@include file="style.css"%></style>
<html>
<head>
  <title>Book Management Application</title>
  <link real="stylesheet" href="style.css" type="text/css"/>
</head>

<body>
<center>
  <h1 class="teg_class">Book Management</h1>
  <h2>
    <a href="new">Add New Book</a>
    &nbsp;&nbsp;&nbsp;
    <a href="list">List All Books</a>

  </h2>
</center>
<div align="center">
  <c:if test="${book != null}">
  <form  method="post">
    </c:if>
    <c:if test="${book == null}">
    <form action="insert" method="post">
      </c:if>
      <table border="1" cellpadding="5">
        <caption>
          <h2>
            <c:if test="${book != null}">
              Edit Book
            </c:if>
            <c:if test="${book == null}">
              Add New Book
            </c:if>
          </h2>
        </caption>
        <c:if test="${book != null}">
          <input type="hidden" name="id" value="<c:out value='${book.id}' />" />
        </c:if>
        <tr>
          <th>Book Name: </th>
          <td>
            <input type="text" name="name" size="45"
                   value="<c:out value='${book.name}' />"
            required/>
          </td>
        </tr>
        <tr>
          <th>Book Genre: </th>
          <td>
            <input type="text" name="genre" size="45"
                   value="<c:out value='${book.genre.name}' />"
                   required />
          </td>
        </tr>
<tr>
  <td rowspan="3" class="first">Authors</td>
  <td>
  <c:if test="${book!=null}">
    <ol>
    <c:forEach var="author" items="${book.authors}">
      <li><input type="text" name="author" value="${author.name}"/></li>
    </c:forEach>
    </ol>
  </c:if>

  <div class="dynamic_fields">
    <div class="example_author">
      <div class="table">
        <div class="cell"><input class="form-control" type="text" name="author"/></div>
        <div class="cell">
          <button type="button" class="js-remove pull-right btn btn-danger">-</button>
        </div>
      </div>
    </div>
    <div class="authors">
    </div>
    <button type="button" class="js-add pull-right btn btn-success">+</button>
  </div>
  </td>
</tr>
<script>
  // определение кнопки добавления
  var button_add = document.querySelector( '.dynamic_fields .js-add' );

  // ожидание клика на кнопку .add
  button_add.addEventListener( 'click', function () {

    // определение блока, содержащего элементы
    var authors = document.querySelector( '.dynamic_fields .authors' );

    // клонирование образцового элемента
    var element = document.querySelector( '.example_author' ).cloneNode( true );

    // добавление класса к клонированному элементу
    element.classList.add( 'author' );

    // удаление класса из клонированного элемента
    element.classList.remove( 'example_author' );

    // добавление нового элемента к списку
    authors.appendChild( element );
  } );

  // ожидание клика по документу
  document.addEventListener( 'click', function ( el ) {

    // если клик был по элементу, который содержит класс remove
    if ( el.target && el.target.classList.contains( 'js-remove' ) ) {

      // определение прародительского блока, содержащего кнопку
      var child = el.target.closest( '.table' );

      // удаление элемента списка
      child.parentNode.removeChild( child );
    }
  } );

</script>
</tr>
        <tr>
          <td colspan="2" align="center">
            <input type="submit" value="Save" />
          </td>
        </tr>
      </table>
    </form>
</div>
</body>
</html>