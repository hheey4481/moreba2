<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>moreBa 회원가입</title>
</head>
<body>
<form action="/newMember" name="newMember" method="post">
    <label>아이디</label>
    <input type="text" name="id" placeholder="아이디를 입력하세요">
    <input type="button" name="btnIsDuplication" value="중복확인">
    <br>

    <label>닉네임</label>
    <input type="text" name="nickname" placeholder="닉네임을 입력하세요">
    <input type="button" name="btnIsDuplication2" value="중복확인">
    <br>

    <label>비밀번호</label>
    <input type="password" name="passwd" placeholder="비밀번호를 입력하세요">
    <br>

    <label>비밀번호 확인</label>
    <input type="password" name="passwd_chk" placeholder="비밀번호를 입력하세요">
    <br>

    <label>이름</label>
    <input type="text" name="name" placeholder="이름을 입력하세요">
    <br>

    <label>생일</label>
    <input type="text" name="birthyy" maxlength="4" placeholder="년(4자)" size="6">
    <select name="birthmm">
        <option value="">월</option>
        <option value="01">1</option>
        <option value="02">2</option>
        <option value="03">3</option>
        <option value="04">4</option>
        <option value="05">5</option>
        <option value="06">6</option>
        <option value="07">7</option>
        <option value="08">8</option>
        <option value="09">9</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
    </select> <input type="text" name="birthdd" maxlength="2" placeholder="일" size="4">
    <br>

    <label>이메일</label>
    <input type="text" name="mail1" maxlength="50">@
    <select name="mail2">
        <option>naver.com</option>
        <option>daum.net</option>
        <option>gmail.com</option>
        <option>nate.com</option>
    </select>
    <br>

    <label>전화번호</label>
    <input name="phone" type="text" placeholder="전화번호를 입력해주세요" >
    <br>

    <input type="submit" value="등록" >
</form>
</body>
</html>
