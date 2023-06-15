<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>My Page Modify Ba</title>
</head>
<body>
    <%@ include file="../../inc/menu.jsp"%>
    <div class="container col-6 mt-5">
        <h3>My Page Ba</h3>
        <form action="/mypage/listModify" method="post">
            <ol class="list-group">
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold"><h5>내 닉네임 님</h5></div>
                    </div>
                    <span><button type="button" class="btn btn-primary">변경</button></span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">이름</div>
                        Content for list item
                    </div>

                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">아이디</div>
                        Content for list item
                    </div>

                </li>

                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">이메일</div>
                        Content for list item
                    </div>
                </li>

                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">연락처</div>
                        Content for list item
                    </div>
                    <span><button type="button" class="btn btn-primary">변경</button></span>
                </li>

                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">주소</div>
                        Content for list item
                    </div>
                    <span><button type="button" class="btn btn-primary">변경</button></span>

                </li>

                </li>
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">생일</div>
                        Content for list item
                    </div>

                </li>
            </ol>
        </form> <!-- end form -->
    </div> <!-- end 마이페이지 -->

    <div class="container col-6 mt-5">
        <h3>Todo List</h3>


        <ol class="list-group">

            <c:forEach var="dto" items="${dtoList}">
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <span>${dto.title}</span>
                    <span>${dto.dueDate}</span>
                    <span>${dto.finished ? "DONE" : "NOT YET"}</span>
                </li>
            </c:forEach>
        </ol>
        <div><a href="./register">REGISTER</a></div>
    </div>

    <div class="container col-6 mt-5"></div>
</body>
</html>