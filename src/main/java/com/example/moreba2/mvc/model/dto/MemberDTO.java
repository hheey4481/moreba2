package com.example.moreba2.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String id;          // 아이디
    private String passwd;      // 비밀번호
    private String name;        // 이름
    private String nickname;    // 닉네임
    private String birth;       // 생일
    private String mail;        // 이메일
    private String phone;       // 전화번호
    private String address;     // 주소
    private Date regist_day;    // 가입날짜
    private String uuid;        // 쿠키
}
