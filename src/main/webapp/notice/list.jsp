<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.moreba2.mvc.model.dto.NoticeDTO" %>
<%
    String sessionId = (String) session.getAttribute("sessionId");
    List boardList = (List) request.getAttribute("noticelist");
    int total_record = ((Integer) request.getAttribute("total_record")).intValue();//전체 게시물수
    int pageNum = ((Integer) request.getAttribute("pageNum")).intValue(); //
    int total_page = ((Integer) request.getAttribute("total_page")).intValue();
    int limit = ((Integer) request.getAttribute("limit")).intValue(); //페이지당 게시물 번호
%>
<html>
<head>
    <link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
    <link href="../resources/css/list.css" rel="stylesheet">
    <title>Notice</title>
    <script type="text/javascript">
        function checkForm() {
            if (${sessionId==null}) {
                alert("로그인 해주세요.");
                return false;
            }

            location.href = "./NoticeWriteForm.no?id=<%=sessionId%>"
        }
    </script>
</head>
<body>
<jsp:include page="../inc/menu.jsp" />
<div class="jumbotron">
    <div class="container">
        <h1 class="display-6 mt-4 ">공지사항</h1>
    </div>
</div>
<div class="container">
    <form action="<c:url value="./NoticeListAction.no"/>" method="post">
        <div>
            <div class="text-right">
                <span class="badge badge-success">전체 <%=total_record%>건	</span>
            </div>
        </div>
        <div style="padding-top: 50px">
            <table class="table table-hover">
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성일</th>
                    <th>조회</th>
                    <th>글쓴이</th>
                </tr>
                <%
                    //목록에 노출되는 게시물 번호를 전체 게시물의 수 기준으로.
                    int serialNumber = total_record-((pageNum-1) * limit); //게시물 일련 번호
                    for (int j = 0; j < boardList.size(); j++) {
                        NoticeDTO notice = (NoticeDTO) boardList.get(j);
                %>
                <tr>
                    <td><%=serialNumber--%></td>
                    <td><a href="./NoticeViewAction.no?num=<%=notice.getNum()%>&pageNum=<%=pageNum%>"><%=notice.getSubject()%></a></td>
                    <td><%=notice.getRegist_day()%></td>
                    <td><%=notice.getHit()%></td>
                    <td><%=notice.getName()%></td>
                </tr>
                <%
                    }
                %>
            </table>
        </div>
        <div align="center">
            <%
                int pagePerBlock = 5; // 페이지 출력시 나올 범위.
                int totalBlock = total_page % pagePerBlock == 0 ? total_page / pagePerBlock : total_page / pagePerBlock + 1; // 전체 블럭 수
                int thisBlock = ((pageNum - 1) / pagePerBlock) + 1; // 현재 블럭
                int firstPage = ((thisBlock - 1) * pagePerBlock) + 1; // 블럭의 첫페이지
                int lastPage = thisBlock * pagePerBlock; // 블럭의 마지막 페이지
                lastPage = (lastPage > total_page)? total_page : lastPage;

//                out.println("total_page :" + total_page);
//                out.println("totalBlock :" + totalBlock);
//                out.println("thisBlock :" + thisBlock);
//                out.println("firstPage :" + firstPage);
//                out.println("lastPage :" + lastPage);
            %>
            <br>
            <c:set var="pagePerBlock" value="<%=pagePerBlock%>" />
            <c:set var="totalBlock" value="<%=totalBlock%>" />
            <c:set var="thisBlock" value="<%=thisBlock%>" />
            <c:set var="firstPage" value="<%=firstPage%>" />
            <c:set var="lastPage" value="<%=lastPage%>" />

            <c:set var="pageNum" value="<%=pageNum%>" />

            <c:choose>
                <c:when test="${empty text}">
                    <c:set var="para" value=""/>
                </c:when>
                <c:otherwise>
                    <c:set var="para" value="&items=${items}&text=${text}"/>
                </c:otherwise>
            </c:choose>

            <nav aria-label="Page navigation example">
            <ul class="pagination pagination-sm mt-3">
                <li class="page-item"><a class="page-link"
                                         href="<c:url value="./NoticeListAction.no?pageNum=1${para}"/>">첫 페이지</a></li>
            <c:if test="${thisBlock > 1}">
                <li class="page-item"><a class="page-link" href="<c:url value="./NoticeListAction.no?pageNum=${firstPage - 1}${para}" />">이전</a></li>

            </c:if>
            <c:forEach var="i" begin="<%=firstPage %>" end="${lastPage}">
                <c:choose>
                    <c:when test="${pageNum==i}">
                        <a class="page-link active"
                           href="<c:url value="NoticeListAction.no?pageNum=${i}${para}"/>">
                                ${i} </a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link"
                           href="<c:url value="./NoticeListAction.no?pageNum=${i}${para}"/>">${i}</a>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
            <c:if test="${thisBlock < totalBlock}">
                <li class="page-item"><a class="page-link"  href="<c:url value="./NoticeListAction.no?pageNum=${lastPage + 1}${para}" />">다음</a></li>
            </c:if>
        <c:if test="${total_page != pageNum}">
                <li class="page-item"><a class="page-link"  href="<c:url value="./NoticeListAction.no?pageNum=${total_page}${para}" />">마지막 페이지</a></li>
        </c:if>
            </ul>
            </nav>
            </div>



    </form>
    <hr>
</div>

</body>
</html>





