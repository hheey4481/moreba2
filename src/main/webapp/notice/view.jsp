<%@ page import="com.example.moreba2.mvc.model.dto.NoticeDTO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    NoticeDTO notice = (NoticeDTO) request.getAttribute("notice");
    int num = ((Integer) request.getAttribute("num")).intValue();
    int nowpage = ((Integer) request.getAttribute("page")).intValue();
%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Board</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        let goUpdate=function() {
            const frm = document.frmUpdate;
            // document.querySelector("#del").setAttribute("href", "./BoardDeleteAction.do?num=<%=notice.getNum()%>&pageNum=<%=nowpage%>");
            frm.action="../noticeController/NoticeUpdateForm.no";
            frm.submit();
        }
        let goDelete = function(){
            if(confirm("삭제하시겠습니까?"))
            {
                const frm = document.frmUpdate;
                frm.action = "../noticeController/NoticeDeleteAction.no";
                frm.submit();
            }
        }

    </script>
</head>
<body>
<jsp:include page="../inc/menu.jsp" />
<div class="jumbotron">
    <div class="container">
        <h1 class="display-6 mt-4">게시판</h1>
    </div>
</div>

<div class="container">
    <!--form name="newUpdate" class="form-horizontal"  action="./NoticeUpdateAction.do?num=<%=notice.getNum()%>&pageNum=<%=nowpage%>" method="post"-->
    <div class="form-group  row">
        <label class="col-sm-2 control-label"  >성명</label>
        <div class ="col-sm-3"><%=notice.getName() %></div>

    </div>
    <div class="form-group  row">
        <label class="col-sm-2 control-label">제목</label>
        <div class="col-sm-5"><%=notice.getSubject() %></div>
    </div>
    <div class="form-group  row">
        <label class="col-sm-2 control-label">내용</label>
        <div class="col-sm-8" style="word-break: break-all">
            <%=notice.getContent()%>
        </div>
    </div>
    <% if(notice.getFilename() != null && ! notice.getFilename().isEmpty()){ %>
    <div class="form-group row">
        <label class="col-sm-2 control-Label" >이미지</label>
        <div class="col-sm-8" style="word-break: break-all;">
            <img src="/img/<%=notice.getFilename()%>" class="user-gallery-image" style="width: 300px; height: 300px">
        </div>
    </div>
    <%} %>
    <div class="form-group row">
        <div class="col-sm-offset-2 col-sm-10 ">
            <c:set var="userId" value="<%=notice.getId()%>" />
            <c:if test="${sessionId==userId}">
                <p>
                    <span class="btn btn-outline-danger btn-sm" onclick="goDelete();">삭제</span>
                    <span class="btn btn-outline-success btn-sm" onclick="goUpdate();">수정</span>
                     <a href="./NoticeListAction.no?pageNum=<%=nowpage%>" class="btn btn-outline-primary btn-sm"> 목록</a></p>
<%--                    <span class="btn btn-outline-warning btn-sm" onclick="goAccuss();">신고</span>--%>
                </p>
            </c:if>

        </div>
    </div>
    <!--/form-->
    <hr>

    <form name="frmUpdate" method="post">
        <input type="hidden" name="num" value="<%=num%>">
        <input type="hidden" name="pageNum" value="<%=nowpage%>">
    </form>





</div>
<!-- //리플 쓰기 -->

</body>
</html>
