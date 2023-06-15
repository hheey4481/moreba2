<%@ page contentType="text/html; charset=utf-8" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>moreBa 회원가입</title>
    <script src="/resources/js/chkId.js" defer></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
<jsp:include page="/inc/menu.jsp"/>
<div class="container col-6 mt-5">
    <form action="#" name="joinMember" method="post">
        <h3>Join Page Ba</h3>
        <ol class="list-group">
            <input type="hidden" name="checkIdResult" id="CheckId" value="false"/>
            <input type="hidden" name="checkNickResult" id="CheckNick" value="false"/>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">아이디</div>
                    <input type="text" name="id" placeholder="아이디를 입력하세요">
                    <span class="idCheck"></span>
                    <br>
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">닉네임</div>
                    <input type="text" name="nickname" id="nickname" placeholder="닉네임을 입력하세요">
                    <span class="nickCheck"></span>
                    <br>
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">비밀번호</div>
                    <input type="password" name="passwd" id="passwd" placeholder="비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.">
                    <br>
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">비밀번호 확인</div>
                    <input type="password" name="passwd_chk" class="passwd_chk" placeholder="비밀번호를 입력하세요">
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">이름</div>
                    <input type="text" name="name" placeholder="이름을 입력하세요">
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">생일</div>
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
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">이메일</div>
                    <input type="text" name="mail1" maxlength="50">@
                    <select name="mail2">
                        <option>naver.com</option>
                        <option>daum.net</option>
                        <option>gmail.com</option>
                        <option>nate.com</option>
                    </select>
                    <span class="checkMail"></span>
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold">전화번호</div>
                    <input name="phone" type="text" placeholder="전화번호를 입력해주세요">
                </div>
            </li>

            <li class="list-group-item d-flex justify-content-between align-items-start">
                <div class="ms-2 me-auto">
                    <div class="fw-bold"></div>
                    <table>
                        <tr>
                            <th>주소</th>
                            <td>
                                <input name="zipcode" id="zipcode" size="10" maxlength="7" readonly>
                                <span id="search">우편번호 검색</span><br>
                                <input name="address01" id="address01" size="70" maxlength="70" readonly>
                                <input name="address02" id="address02" size="70" maxlength="70">
                            </td>
                        </tr>
                    </table>
                </div>
            </li>

            <input type="submit" id="go" value="등록" class="btn btn-primary">
        </ol>
    </form>
</div>

</body>
</html>
