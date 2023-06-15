<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String name = (String) session.getAttribute("sessionId");
    String sessionMemberName = (String) session.getAttribute("sessionName");
%>
<html>
<head>
    <link href="../resources/css/Qlist.css" rel="stylesheet">
    <title>Board</title>
</head>
<script type="text/javascript">
    function checkForm() {
        if(!document.newWrite.mal.value){
            alert("말머리를 선택하세요.");
            return false;
        }

        if(!document.newWrite.name.value){
            alert("성명을 입력하세요. ");
            return false;
        }
        if(!document.newWrite.subject.value){
            alert("제목을 입력하세요. ");
            return false;
        }
        if(!document.newWrite.content.value){
            alert("내용을 입력하세요. ");
            return false;
        }
    }
</script>
<body>
<jsp:include page="../inc/menu.jsp" />
<div class="jumbotron">
    <div class="container">
        <h1 class="display-6 mt-4">Q & A</h1>
    </div>
</div>

<div class="container">
    <form name="newWrite" action="./QnaWriteAction.ro"
          class="form-horizontal" method="post" onsubmit="return checkForm()"
          enctype="multipart/form-data">
        <input name="id" type="hidden" class="form-control"
               value="${sessionID}">

        <div class="form-group row my-3">
            <label class="col-sm-2 control-Label" >분류</label>
            <div class="col-sm-3 mal">
                <select class="form-select "  aria-label="Default select example" name="mal">
                    <option selected>선택</option>
                    <option value="문의">문의</option>
                    <option value="신고">신고</option>

                </select>
            </div>
        </div>

        <div class="form-group row  my-3">
            <label class="col-sm-2 control-Label" >닉네임</label>
            <div class="col-sm-3">
                <input name="name" type="text" class="form-control" value="${sessionNickName}" placeholder="name">
            </div>
        </div>
        <div class="form-group row  my-3">
            <label class="col-sm-2 control-Label" >제목</label>
            <div class="col-sm-5">
                <input name="subject" type="text" class="form-control"
                       placeholder="subject">
            </div>
        </div>
        <div class="form-group row  my-3">
            <label class="col-sm-2 control-Label" >내용</label>
            <div class="col-sm-8">
                        <textarea name="content" cols="50" rows="5" class="form-control"
                                  placeholder="content"></textarea>
            </div>
        </div>
        <div class="form-group row  my-3">
            <label class="col-sm-2 control-Label" >이미지</label>
            <div class="col-sm-8" >
                <input type="file" name="productImage" class="form-control"  >
            </div>
        </div>
<%--        <div class="form-group row my-3">--%>
<%--            <label class="col-sm-2 control-Label" >비밀글 설정 여부</label>--%>
<%--            <div class="col-sm-8" >--%>
<%--                <input type="checkbox" name="secret" id="secret"  >--%>
<%--            </div>--%>
<%--        </div>--%>

        <div class="form-group row  my-3">
            <div class="col-sm-offset-2 col-sm-10  my-3">
                <input type="submit" class="btn btn-outline-primary" value="등록">
                <input type="reset" class="btn btn-outline-primary" value="취소">
            </div>
        </div>

    </form>
    <hr>
</div>

</body>
</html>