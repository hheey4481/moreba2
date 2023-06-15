<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.example.moreba2.mvc.model.dto.MicDTO"%>


<html>
<head>
    <title>mic</title>
</head>

<body>
<%@ include file="../inc/menu.jsp"%>
<div class="container">
    <h3>MIC</h3>
    <form name="frmMicWrite" class="form-horizontal mt-5" method="post">
        <input type="hidden" name="pageNum" value="${page}">
        <div class="form-group row">
            <label class="col-sm-2 control-Label" ></label>
            <div class="col-sm-8" style="word-break: break-all;">
                <textarea name="content" class="form-control" cols="50" rows="3"></textarea>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 control-Label"></label>
            <div class="col-sm-3" >
                <span class="btn btn-primary" onclick="goMicSubmit()">등록</span>
            </div>
        </div>
    </form>
        <script>
            function goMicSubmit() {
                let frm = document.frmMicWrite;
                //컨트롤러로 전달
                frm.action = "MicWriteAction.ho";
                frm.submit();
            }
        </script>
        <!--리플리스트-->
    <div class ="form-group row user-ripple-list">

        <ul>

        </ul>
    </div>
    <form name="frmMicDelete" class="form-horizontal" method="post">
        <input type="hidden" name="pageNum" value="${page}">
        <input type="hidden" name="MicId">
    </form>
    <hr>
    <script>
        const xhr = new XMLHttpRequest(); //XMLHttpRequest 객체 생성
        //함수 선언
        const insertItem = function (item) {
            // 목록에 요소를 추가. 처음 로딩시 목록을 출력하거나 새로운 글 등록시 사용
            let tagNew = document.createElement('li');
            tagNew.innerHTML = item.content + ' | ' + item.name;
            if (item.isWriter === 'true') {
                tagNew.innerHTML += '<span class="btn btn-danger" onclick="goRippleDelete(\'' + item.rippleId + '\');">>삭제</span>'
            }
            let tagUl = document.querySelector('.user-repple-list ul');
            tagUl.append(tagNew);
        }

        const insertList = function() {
            //목록을 가지고 옴
            let num = document.querySelector('form[name=frmRippleDelete] input[name=num]');
            //  xhr.open('GET', '../board/ajax_list_item.jsp?boardName=board&num=' + num.value);
            xhr.open('GET', '../boardController/RippleListAction.do?boardName=board&num=' + num.value);
            xhr.send();
            xhr.onreadystatechange = function (){
                if(xhr.readyState !== XMLHttpRequest.DONE) return;

                if(xhr.status===200){// 서버 (url)에 문서가 존재함
                    console.log(xhr.response);
                    const json = JSON.parse(xhr.response);

                    let tagUl = document.querySelector('.user-repple-list ul');
                    tagUl.innerHTML=""; //내부 태그 삭제후 인서트

                    for(let data of json.listData){
                        console.log(data);
                        insertItem(data);
                    }
                } else {//서버(url)에 문서가 존재하지 않음
                    console.error('Error', xhr.status, xhr.statusText);
                }
            }
        }

        const goRippleDelete = function (ID){
            if(confirm("삭제하시겠습니까?")){
                const xhr = new XMLHttpRequest(); //XMLHttpRequest 객체 생성
                // xhr.open('POST', '../board/ajax_delete.jsp?rippleId=' + ID);

                xhr.open('POST', '../boardController/RippleDeleteAction.do?rippleId=' + ID);
                xhr.send();
                xhr.onreadystatechange = () => {
                    if(xhr.readyState !== XMLHttpRequest.DONE) return;

                    if(xhr.status ===200){ //서버(url)에 문서가 존재함
                        console.log(xhr.response);
                        const json = JSON.parse(xhr.response);
                        if(json.result ==='true'){
                            insertList();
                        }
                        else {
                            alert("삭제에 실패했습니다.");
                        }
                    } else {
                        //서버(url)에 문서가 존재하지 않음
                        console.error('Error', xhr.status, xhr.statusText);
                    }
                }
            }
        }
    </script>
    <script>
        document.addEventListener("DOMContentLoaded", function(){
            insertList();
        });
    </script>

    <script>
        document.addEventListener("DOMContentLoaded", function(){
            const xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성

            document.querySelector('span[name=goRippleSubmit]').addEventListener('click', function (){
                /* 등록 버튼 클릭 시
                1) 데이터베이스에 데이터 등록 2) 화면에 표시 */
                let num = document.querySelector('input[name=num]');
                let name = document.querySelector('input[name=name]');
                let content = document.querySelector('textarea[name=content]');

                //xhr.open('POST', '../board/ajax_insert_content.jsp?boardName=board&num=' +
                // num.value + '&name=' + name.value + '&content=' + content.value);
                xhr.open('POST', '../boardController/RippleWriteAction.do?boardName=board&num=' +
                    num.value + '&name=' + name.value + '&content=' + content.value);
                xhr.send();
                xhr.onreadystatechange = () => {
                    if (xhr.readyState !== XMLHttpRequest.DONE) return;

                    if (xhr.status === 200) { // 서버(url)에 문서가 존재함
                        console.log(xhr.response);
                        const json = JSON.parse(xhr.response);
                        if (json.result === 'true') {
                            content.value = ''; // input태그에 입력된 값 삭제.
                            insertList();
                        }
                        else {
                            alert("등록에 실패했습니다.");
                        }
                    }
                    else { // 서버(url)에 문서가 존재하지 않음.
                        console.error('Error', xhr.status,xhr.statusText);
                    }
                }
            });

        });
    </script>
</div>

</body>
</html>
