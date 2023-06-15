package com.example.moreba2.mvc.model.dao;

import com.example.moreba2.mvc.database.ConnectionUtil;
import com.example.moreba2.mvc.domain.MemberVO;
import lombok.Cleanup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// enum 이용과 class 이용
// enum : 사용 하는 애가 적을 경우에 사용하기 유용 (INSTANCE 생성으로 new 어쩌고 객체 생성 X )
// class : 사용하는 애가 여럿일 경우 사용하기 유용  ( 할 때 마다 new 로 객체 생성해야함)
// DAO : 데이터 운반만 하는 애 (데이터 이렇게 들어간 게 맞아? 와 같은 판단 여부는 다 service 한테 떠넘기기)
public class MemberDAO {

    // 1. 회원가입
    public boolean newMember(MemberVO memberVo) throws Exception {
        String sql = "INSERT INTO member VALUES (?,?,?,?,?,?,?,?,now(),?)";
        int flag = 0;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, memberVo.getId());
        preparedStatement.setString(2, memberVo.getPasswd());
        preparedStatement.setString(3, memberVo.getName());
        preparedStatement.setString(4, memberVo.getNickname());
        preparedStatement.setString(5, memberVo.getBirth());
        preparedStatement.setString(6, memberVo.getMail());
        preparedStatement.setString(7, memberVo.getPhone());
        preparedStatement.setString(8, memberVo.getAddress());
        preparedStatement.setString(9, memberVo.getUuid());

        flag = preparedStatement.executeUpdate();
        return flag == 1;
    }

    // 1-2. 아이디 중복 검사
    public boolean checkId(String id) throws Exception {

        String sql = "SELECT * FROM `moreba`.`member` WHERE id = ?";
        System.out.println("MemberDAO id : " + id);

        boolean flag = true; // 동일한 아이디가 없을 경우

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);


        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {

            flag = false; // 동일한 아이디가 있는 경우
        }
        return flag;

  }

    // 1-3. 닉네임 중복 검사
    public boolean checkNickname(String nickname) throws Exception {
        String sql = "SELECT * FROM `moreba`.`member` WHERE nickname = ?";
        System.out.println("MemberDAO nickname : " + nickname);

        boolean flag = false; // 동일한 닉네임이 없을 경우
        // 원형 : boolean flag = true

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nickname);


        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {

            flag = true; // 동일한 닉네임 존재
            // 원형 : boolean flag = false
        }
        return flag;
    }

    // 2. 로그인 : 아이디, 비밀번호 가져오기
    public boolean getLoginInfo(HttpServletRequest req) throws Exception {
        // 입력받은 아이디가, DB의 아이디가 가진 id와 pw가 맞는지 비교하고 맞으면 true (메인으로 가기), 아니면 false(로그인창)
        String id = req.getParameter("id");
        String passwd = req.getParameter("passwd");

        String sql = "SELECT id, passwd, name, nickname, mail, birth, address, phone, regist_day FROM `moreba`.`member` WHERE id = ? AND passwd = ?";

        int flag = 0;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // memberVO에서 받아온 id와 pw를 저장하고, 변화한 값이 1이면 반환.
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, passwd);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            // 로그인이 성공한 경우, 아이디의 닉네임 세션을 가져오기
            HttpSession session = req.getSession();
            session.setAttribute("sessionID", resultSet.getString("id"));
            session.setAttribute("sessionNickName", resultSet.getString("nickname")); // 로그인을 성공한 id의 값을 Session객체의 sessionID 라는 이름으로 저장
            session.setAttribute("sessionName", resultSet.getString("name"));
            session.setAttribute("sessionBirth", resultSet.getString("birth"));
            session.setAttribute("sessionMail", resultSet.getString("mail"));
            session.setAttribute("sessionPhone", resultSet.getString("phone"));
            session.setAttribute("sessionAddress", resultSet.getString("address"));
            session.setAttribute("sessionRegist_day", resultSet.getString("regist_day"));

            flag = 1; // 로그인 성공! true 의 값을 가지는 1로 변경
        }
        return flag == 1;
    }

    // 3. 회원 정보 가져오기
    public List<MemberVO> selectAll() throws Exception{
        String sql = "SELECT * FROM `moreba`.`member`";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        @Cleanup ResultSet rs = preparedStatement.executeQuery();

        List<MemberVO> list = new ArrayList<>();

        while(rs.next()) {
            MemberVO memberVo = MemberVO.builder()
                    .id(rs.getString("id"))
                    .name(rs.getString("name"))
                    .nickname(rs.getString("nickname"))
                    .birth(rs.getString("birth"))
                    .mail(rs.getString("mail"))
                    .phone(rs.getString("phone"))
                    .address(rs.getString("address"))
                    .regist_day(rs.getDate("regist_day").toLocalDate())
                    .build();

            list.add(memberVo);
        }
        return list;
    }

    // 3-1. 회원 조회 기능
    public MemberVO selectOne(String id) throws Exception {
        // 특정한 id가 파라미터가 되고, MemberVO를 리턴 타입으로 지정함.
        String sql = "SELECT * FROM `moreba`.`member` WHERE id = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, id);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        MemberVO memberVO = MemberVO.builder()
                .id(resultSet.getString("id"))
                .name(resultSet.getString("name"))
                .nickname(resultSet.getString("nickname"))
                .birth(resultSet.getString("birth"))
                .mail(resultSet.getString("mail"))
                .phone(resultSet.getString("phone"))
                .address(resultSet.getString("address"))
                .regist_day(resultSet.getDate("regist_day").toLocalDate())
                .build();

        return memberVO;
    }

    // 4. 회원 정보 수정
    public boolean updateMember(MemberVO memberVo) throws Exception {
        String sql = "UPDATE member SET passwd = ?, phone = ?, address =? WHERE id = ?";
        int flag = 0;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, memberVo.getPasswd());
        preparedStatement.setString(2, memberVo.getPhone());
        preparedStatement.setString(3, memberVo.getAddress());
        preparedStatement.setString(4, memberVo.getId());

        flag = preparedStatement.executeUpdate();
        System.out.println(sql);
        System.out.println(memberVo.getPasswd());
        System.out.println(memberVo.getPhone());
        System.out.println(memberVo.getAddress());
        System.out.println(memberVo.getId()); // memberVO의 id 값을 못 불러옴 왜죵

        return flag >= 1;
    }


}
