package com.example.moreba2.mvc.domain;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
// VO : 테이블과 완벽히 같아서 DB쪽으로 작업하는데 사용
// DTO : 경우에 따라서 추가 등 변경이 가능. 서비스나 뷰 등 자료를 주고 받는 용도로 사용 


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String id;          // 아이디
    private String passwd;      // 비밀번호
    private String name;        // 이름
    private String nickname;    // 닉네임
    private String birth;       // 생일
    private String mail;        // 이메일
    private String phone;       // 전화번호
    private String address;     // 주소
    private LocalDate regist_day;    // 가입날짜
    private String uuid;        // 쿠키

}
