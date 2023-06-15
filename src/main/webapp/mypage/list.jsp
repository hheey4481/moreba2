<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>My Page Ba</title>
</head>
<body>
  <%@ include file="../inc/menu.jsp"%>
  <div class="container col-6 mt-5">
      <h3>My Page Ba</h3>
      <ol class="list-group">
          <li class="list-group-item d-flex justify-content-between align-items-start">
              <div class="ms-2 me-auto">
                  <div class="fw-bold"><h5>${memberVO.nickname} 님</h5></div>
              </div>
              <a class="btn btn-primary" id="modify" href="/mypage/listModify.go">변경</a>
          </li>
          <li class="list-group-item d-flex justify-content-between align-items-start">
              <div class="ms-2 me-auto">
                  <div class="fw-bold">이름</div>
                  ${memberVO.name}
              </div>

          </li>
          <li class="list-group-item d-flex justify-content-between align-items-start">
              <div class="ms-2 me-auto">
                  <div class="fw-bold">아이디</div>
                  ${memberVO.id}
              </div>

          </li>

          <li class="list-group-item d-flex justify-content-between align-items-start">
              <div class="ms-2 me-auto">
                  <div class="fw-bold">이메일</div>
                  ${memberVO.mail}
              </div>
          </li>

          </li>
          <li class="list-group-item d-flex justify-content-between align-items-start">
              <div class="ms-2 me-auto">
                  <div class="fw-bold">연락처</div>
                  ${memberVO.phone}
              </div>
          </li>

          </li>
          <li class="list-group-item d-flex justify-content-between align-items-start">
              <div class="ms-2 me-auto">
                  <div class="fw-bold">주소</div>
                  ${memberVO.address}
              </div>

          </li>

          </li>
          <li class="list-group-item d-flex justify-content-between align-items-start">
              <div class="ms-2 me-auto">
                  <div class="fw-bold">생일</div>
                  ${memberVO.birth}
              </div>

          </li>
      </ol>
  </div>

    <div class="container col-6 mt-5">
        <span><h3>Todo List</h3></span>
        <span>
        <form id="form3"action="./register" method="get">
            <button type="submit" class="btn btn-outline-success">등록</button>
        </form>
        </span>
        <ol class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-start">
                <span class="col-6">할 일</span>
                <span class="col">날짜</span>
                <span class="col">CHECK</span>
                <span class="col"></span>
            </li>
            <c:forEach var="dto" items="${dtoList}">
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <span class="col-6">${dto.title}</span>
                    <span class="col">${dto.dueDate}</span>
                    <span class="col">${dto.finished ? "DONE" : "NOT YET"}</span>
                    <form id="form2" action="./remove" method="post" class="col">
                        <input type="hidden" name="tno" value="${dto.tno}" readonly>
                        <button type="submit" class="btn btn-outline-success">삭제</button>

                    </form>
                </li>
            </c:forEach>
        </ol>

<%--        <div><a href="./register">REGISTER</a></div>--%>
    </div>

  <div class="container col-6 mt-5"></div>
</body>
</html>
