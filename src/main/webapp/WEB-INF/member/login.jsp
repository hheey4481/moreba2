<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>MoreBa LogIn</title>
</head>
<body>
<jsp:include page="/inc/menu.jsp"/>

<%--<c:if test="${result == 'error'}">--%>
<%--    <h1>아이디, 비밀번호 BA~</h1>--%>
<%--</c:if>--%>
<%
    String error = request.getParameter("error");
    if (error != null) {
        out.println("<div class='alert alert-danger'>");
        out.println("아이디와 비밀번호를 확인해 주세요");
        out.println("</div>");
    }
%>
<%
    String success = request.getParameter("success");
    if (success != null) {
        out.println("<div class='alert alert-danger'>");
        out.println("회원가입 해주셔서 감사합니다.");
        out.println("</div>");
    }

    String fail = request.getParameter("fail");
    if (fail != null) {
        out.println("회원가입 실패!");
    }

    String loginSuccess = request.getParameter("loginSuccess");
    if (loginSuccess != null) {
        out.println("로그인 성공!");
    }

    //String loginFail = request.getParameter("loginFail");
    //if (loginFail != null) {

      //  out.println("<div class='alert alert-danger'>");
        //out.println("로그인 실패!");
        //out.println("아이디와 비밀번호를 확인해 주세요");
        //out.println("</div>");
  //  }
%>

<%--    <form action="/login.go" method="post">--%>
<%--        <input type="text" name="id">--%>
<%--        <input type="password" name="passwd">--%>
<%--        <button type="submit">Login</button>--%>
<%--    </form>--%>


<div class="container mt-5" align="center">
    <div class="col-md-4 col-md-offset-4">
        <h3 class="form-signin-heading">Please sign in</h3>

        <form action="/login.go" method="post" class="form-signin">
            <div class="form-group">
                <label for="inputUserName" class="sr-only"></label>
                <input type="text" name="id" required autofocus class="form-control" placeholder="ID">
            </div>
            <div class="form-group">
                <label for="inputPassword" class="sr-only"></label>
                <input type="password" name="passwd" placeholder="Password" class="form-control" required >
            </div>

            <button type="submit" class="btn btn btn-lg btn-outline-primary btn-block mt-3">Log in</button>

        </form>
    </div>
</div>
</body>
</html>
