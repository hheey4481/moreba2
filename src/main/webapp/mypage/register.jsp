<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <title>Todo Register</title>
</head>
<body>
<%@ include file="../inc/menu.jsp"%>
<form action="./register" method ="post">
  <div class="container col-6 mt-5">
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon1">Todo</span>
      <input type="text" class="form-control" name="title" aria-describedby="basic-addon1">
    </div>

    <div class="input-group mb-3" >
      <span class="input-group-text" id="basic-addon2">Duedate</span>
      <input type="date" name="dueDate" class="form-control" aria-describedby="basic-addon2" >
    </div>
    <div class="input-group mb-3">
      <button type="reset" class="btn btn-outline-success">RESET</button>
      <button type="submit" class="btn btn-outline-success">REGISTER</button>
    </div>
  </div>
</form>
</body>
</html>
