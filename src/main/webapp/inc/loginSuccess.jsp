<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인성공예에ㅔㅔ</title>
</head>
<body>
    <h1>웰 컴 투 더 스 트 레 인 지 월 드 와 이 드 !</h1>
    <%
        out.println("긴가민가요");
        String mySession = (String)session.getAttribute("sessionNickName");
        out.println("머해~~빨리 찍어봐야지~~ 머 하는 거야~~?" + mySession);
    %>
</body>
</html>
