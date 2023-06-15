// 회원가입 아이디, 비밀번호 확인
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("go").addEventListener("click", checkForm);
});

function checkForm(e) {
    // 왜때문에 페이지 새로고침 되는거지?
    const joinMember = document.joinMember; // 폼 가져오기

    if (!document.joinMember.id.value) { // 아이디 입력
        alert("아이디를 입력하세요.");
        joinMember.id.focus();
        e.preventDefault();
        return false;
    }

    if (joinMember.id.value.length < 4 || joinMember.id.value.length > 15){ // 아이디 유효성 검사
        alert("아이디는 4자 이상 15자 이하로 작성해주세요.");
        joinMember.id.focus();
        e.preventDefault();
        return false;
    }
    
    if(document.joinMember.checkIdResult.value == "false") {
        // 아이디가 중복이 되었음에도 가입하려 할 때
        alert("아이디가 중복입니다.");
        joinMember.id.focus();
        e.preventDefault();
        return false;
    }

    if (!document.joinMember.nickname.value) { // 닉네임 입력
        alert("닉네임을 입력하세요.");
        joinMember.nickname.focus();
        e.preventDefault();
        return false;
    }

    if (joinMember.nickname.value.length < 2 || joinMember.nickname.value.length > 12){ // 닉네임 유효성 검사
        alert("닉네임은 2자 이상 12자 이하로 작성해주세요.");
        joinMember.nickname.focus();
        e.preventDefault();
        return false;
    }

    if(document.joinMember.checkNickResult.value == "false") {
        // 닉네임이 중복이 되었음에도 가입하려 할 때
        alert("닉네임이 중복입니다.");
        joinMember.nickname.focus();
        e.preventDefault();
        return false;
    }

    if (!document.joinMember.passwd.value) { // 비밀번호 입력
        alert("비밀번호를 입력하세요.");
        joinMember.passwd.focus();
        e.preventDefault();
        return false;
    }

    //비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
    let passwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

    if (!passwdCheck.test(joinMember.passwd.value)) {
        alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.");
        passwd.focus();
        e.preventDefault();
        return false;
    };

    // 같은 문자 연속 4번 있는지
    let passwdCheck2 = /(\w)\1\1\1/;

    if (passwdCheck2.test(joinMember.passwd.value)) {
        alert("같은 문자를 4번 이상 연속해서 사용하실 수 없습니다.");
        passwd.focus();
        e.preventDefault();
        return false;
    };

    // \s : 띄어쓰기
    // passwd.value.search(n) : n의 인덱스 반환, -1이면 없다는 뜻
    if(document.joinMember.passwd.value.search(" ") != -1){
        alert("비밀번호는 공백을 포함할 수 없습니다");
        passwd.focus();
        e.preventDefault();
        return false;
    }

    if (document.joinMember.passwd.value != document.joinMember.passwd_chk.value) {
        alert("비밀번호를 동일하게 입력하세요.");
        joinMember.passwd_chk.focus();
        e.preventDefault();
        return false;
    }


    if (!document.joinMember.name.value){
        alert("이름을 입력하세요.");
        joinMember.name.focus();
        e.preventDefault();
        return false;
    }

    //이름 : 한글 검사
    let regExpName = /^[가-힣]*$/;

    if (!regExpName.test(joinMember.name.value)){
        alert("이름은 한글만 입력해 주세요.");
        newMember.name.focus();
        e.preventDefault();
        return false;
    }

    let num_check = /[0-9]/;   // 숫자

    if (!document.joinMember.birthyy.value){
        alert("태어난 해를 입력하세요.");
        joinMember.birthyy.focus();
        e.preventDefault();
        return false;
    }

    if (!document.joinMember.birthmm.value){
        alert("태어난 월를 선택하세요.");
        joinMember.birthmm.focus();
        e.preventDefault();
        return false;
    }

    if (!document.joinMember.birthdd.value){
        alert("태어난 일을 입력하세요.");
        joinMember.birthdd.focus();
        e.preventDefault();
        return false;
    }

    if(num_check.test(joinMember.birthyy.value) == false){
        alert("숫자만 입력할 수 있습니다.");
        joinMember.birthyy.focus();
        e.preventDefault();
        return false;
    }

    if(num_check.test(joinMember.birthdd.value) == false){
        alert("숫자만 입력할 수 있습니다.");
        joinMember.birthdd.focus();
        e.preventDefault();
        return false;
    }



    if (!document.joinMember.mail1.value){
        alert("이메일을 입력하세요.");
        joinMember.mail1.focus();
        e.preventDefault();
        return false;
    }

    let exptext = /^[A-Za-z0-9_\.\-]+/;

    if(exptext.test(joinMember.mail1.value)==false){
        //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
        alert("이메일은 영어와 숫자만 조합할 수 있습니다.");
        joinMember.mail1.focus();
        e.preventDefault();

        return false;
    }

    if (!document.joinMember.mail2.value){
        alert("이메일을 선택하세요.");
        joinMember.mail2.focus();
        e.preventDefault();
        return false;
    }

    // num_check.test(joinMember.birthyy.value) == false
    if (document.joinMember.phone.value.length < 11){
        alert("휴대폰 번호 11자리를 입력하세요.");
        joinMember.phone.focus();
        e.preventDefault();
        return false;
    }

    if(num_check.test(joinMember.phone.value) == false){
        alert("숫자만 입력할 수 있습니다.");
        joinMember.phone.focus();
        e.preventDefault();
        return false;
    }

    if (!document.joinMember.address01.value){
        alert("주소를 입력하세요.");
        joinMember.address01.focus();
        e.preventDefault();
        return false;
    }

    if (!document.joinMember.address02.value){
        alert("상세 주소를 입력하세요.");
        joinMember.address02.focus();
        e.preventDefault();
        return false;
    }

    // if (document.joinMember.termsYN.value == "no"){
    //     alert("약관에 동의해 주세요.");
    //     e.preventDefault();
    //     return false;
    // }
    
    // 등록을 눌렀을 때, 유효성검사를 다 하고, form의 action 값을 부여하여 controller 사용
    document.joinMember.action="/joinMember.join";
}

// 회원가입 주소 입력
document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#search").addEventListener("click", execDaumPostcode);
});
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ''; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;
            }
            else { // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if(data.userSelectedType === 'R'){
                //법정동명이 있을 경우 추가한다.
                if(data.bname !== ''){
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('address01').value = fullAddr;

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('address02').focus();
        }
    }).open();
}

document.addEventListener("DOMContentLoaded", function() {
    const joinMember = document.joinMember; // 폼 들고오기
    const xhr = new XMLHttpRequest(); // XMLHttpRequest 객체 생성

    // 1. ajax를 이용하여 실시간으로 아이디 중복 확인
    const inputId = document.querySelector('input[name=id]');
    inputId.addEventListener("keyup", function() {
        const id = 	joinMember.id.value;	// 아이디 input에 있는 값
        const idCheck = document.querySelector('.idCheck');	// 결과 문자열이 표현될 영역
        let checkId = document.getElementById("CheckId");
        xhr.open('GET', '/JoinController/checkID.join?id=' + id);	// HTTP 요청 초기화. 통신 방식과 url 설정
        xhr.send();	// url에 요청을 보냄
        // 이벤트 등록. XMLHttpReqeust 객체의 readyState 프로퍼티 값이 변할 때마다 자동으로 호출
        xhr.onreadystatechange = () => {
            // readyState 프로퍼티의 값이 DONE : 요청한 데이터의 처리가 완료되어 응답할 준비가 완료됨
            if (xhr.readyState !== XMLHttpRequest.DONE) return;

            if (xhr.status === 200) { // 서버(url)에 문서가 존재하면
                console.log(xhr.response);
                const result = xhr.response;
                if (result === 'false') {
                    checkId.value = "false";

                    idCheck.style.color = "red";
                    idCheck.innerHTML = "동일한 아이디가 있습니다.";
                }
                else if (result === 'true') {
                    checkId.value = "true";

                    idCheck.style.color = "green";
                    idCheck.innerHTML = "동일한 아이디가 없습니다.";
                }
                else {
                    console.log("몬가.. 잘못됨...ㅠ")
                }
            }
            else {	// 서버(url)에 문서가 존재X
                console.error('Error', xhr.status, xhr.statusText);
            }
        }
    });

    // 2. ajax를 이용한 실시간 중복 확인
    const inputNick = document.querySelector('input[name=nickname]');
    inputNick.addEventListener("keyup", function() {
        const nickname = joinMember.nickname.value;
        const nickCheck = document.querySelector('.nickCheck');	// 결과 문자열이 표현될 영역
        let checkNick = document.getElementById("CheckNick");
        xhr.open('GET', '/JoinController/checkNick.join?nickname=' + nickname); // HTTP 요청 초기화. 통신 방식과 url 설정
        xhr.send();	// url에 요청을 보냄
        // 이벤트 등록. XMLHttpReqeust 객체의 readyState 프로퍼티 값이 변할 때마다 자동으로 호출
        xhr.onreadystatechange = () => {
            // readyState 프로퍼티의 값이 DONE : 요청한 데이터의 처리가 완료되어 응답할 준비가 완료됨
            if (xhr.readyState !== XMLHttpRequest.DONE) return;

            if (xhr.status === 200) { // 서버(url)에 문서가 존재하면
                console.log(xhr.response);
                const result = xhr.response;
                if (result === 'true') {
                    checkNick.value = "false";

                    nickCheck.style.color = "red";
                    nickCheck.innerHTML = "동일한 닉네임이 있습니다.";
                }
                else if (result === 'false') {
                    checkNick.value = "true";

                    nickCheck.style.color = "green";
                    nickCheck.innerHTML = "동일한 닉네임이 없습니다.";
                }
                else {
                    console.log("몬가.. 잘못됨...몰까..ㅜ")
                }
            }
            else {	// 서버(url)에 문서가 존재X
                console.error('Error', xhr.status, xhr.statusText);
            }
        }
    });
})

let goUpdate=function() {
    const frm = document.frmUpdate;
    // document.querySelector("#del").setAttribute("href", "./BoardDeleteAction.do?num=<%=board.getNum()%>&pageNum=<%=nowpage%>");
    frm.action="../boardController/BoardUpdateForm.do";
    frm.submit();
}