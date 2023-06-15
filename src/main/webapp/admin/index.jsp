<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>관리자 메인</title>
</head>
<body>
<%@include file="menu.jsp"%>
<div class="jumbotron">
    <div class="container">
        <h1 class="display-3">관리자 메인</h1>
    </div>
</div>
<div class="container">
<%--    <%--%>
<%--        String sessionAdminId = (String) session.getAttribute("sessionAdminId");--%>
<%--        String sessionAdminName = (String) session.getAttribute("sessionAdminName");--%>

<%--        if(sessionAdminId == null){--%>
<%--            response.sendRedirect("./loginAdmin.jsp");--%>
<%--        }--%>


<%--    %>--%>

    <%
        out.println(" <h2 class='alert alert-danger'>" + sessionAdminName + "님 환영합니다</h2>");
        String sessionAdminDay =  (String) session.getAttribute("sessionAdminDay");
        out.println(" <h2 class='alert alert-danger'>최근 로그인한 시간은 " + sessionAdminDay + "입니다.</h2>");
    %>
</div>
<%--<%@include file="../inc/footer.jsp"%>--%>
</body>
</html>