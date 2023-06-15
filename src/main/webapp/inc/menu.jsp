<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <link href="../resources/css/menu.css" rel="stylesheet">
    <title>JSP - Hello World</title>
</head>
<body>
<header>
    <nav class="navbar bg-light">
        <div class="container-fluid col-9">
            <a class="navbar-brand mb-0 h1" href="../inc/menu.jsp"> MoreBa</a>
        </div>
        <div class="container-fluid col-3 list-unstyled ">

            <c:choose>
                <c:when test="${empty sessionNickName}">
                    <a class="nav-link" href="/loginMember.go">로그인</a>
                    <a class="nav-link" href="/addMember.join">회원가입</a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="/mypage/list.go">마이페이지</a>
                    <a class="nav-link" href="/logout.go">로그아웃</a>
                </c:otherwise>
            </c:choose>


        </div>
    </nav>
    <nav class="navbar navbar-expand-lg bg-light " >
        <div >
            <%--            <a class="nav-link active" aria-current="page" href="#">Home</a>--%>
            <button  class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav  ">
                    <a class="nav-link me-5" href="#">Home</a>
                    <a class="nav-link me-5" href="<c:url value="../notice/NoticeListAction.no?pageNum=1"/>">공지사항</a>
                    <a class="nav-link me-5" href="<c:url value="../board/HotListAction.do?pageNum=1"/>">HOT</a>
                    <li class="nav-item "><a class="nav-link me-5" href="<c:url value="../board/BoardListAction.do?pageNum=1"/>">원보드</a></li>
                    <%--                    <a class="nav-link me-5" href="/board/list.jsp">원보드</a>--%>
                    <a class="nav-link me-5" href="<c:url value="../qna/QnaListAction.ro?pageNum=1"/>">문의</a>
                    <%--                    <a class="nav-link me-5" href="#">(후원)</a>--%>
                </div>
            </div>
        </div>
    </nav>
    <%--    <nav class="navbar bg-light justify-content-center ">--%>

    <%--            <form action="<c:url value="./BoardListAction.do"/>" method="post" class="d-flex" role="search" style="width: 50%;">--%>
    <%--                <input name="text2" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">--%>
    <%--                <button class="btn btn-outline-success" id="btnAdd" type="submit">Search</button>--%>
    <%--            </form>--%>

    <%--    </nav>--%>
</header>

</body>
<!-- footer -->
<%--<footer class="container-fluid navbar-fixed-bottom">--%>
<%--    <p>moreBa</p>--%>
<%--</footer>--%>
<!-- footer 끝 -->




</html>