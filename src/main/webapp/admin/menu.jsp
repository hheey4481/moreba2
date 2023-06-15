<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
  String sessionAdminId = (String) session.getAttribute("sessionAdminId");
  String sessionAdminName = (String) session.getAttribute("sessionAdminName");

  if(sessionAdminId == null){
    response.sendRedirect("./loginAdmin.jsp");
  }


%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<nav class = "navbar navbar-expand navbar-dark bg-dark">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href ="./welcome.jsp">Home</a>
    </div>
    <div>
      <ul class="navbar-nav mr-auto">
        <li style="paddint-top: 7px; color:white">[<%=sessionAdminName%>님]</li>
        <li class="nav-item"><a class="nav-link" href="<c:url value="#"/>">로그아웃</a></li>
        <li class="nav-item"><a class="nav-link" href="<c:url value="#"/>">정보수정</a></li>
        <li class="nav-item"><a class="nav-link" href="<c:url value="#"/>">회원목록</a></li>
        <li class="nav-item"><a class="nav-link" href="<c:url value="#"/>">게시판수정</a></li>
        <li class="nav-item"><a class="nav-link" href="<c:url value="../notice/NoticeWriteForm.no"/>">공지 등록</a></li>
      </ul>
    </div>
  </div>
</nav>
