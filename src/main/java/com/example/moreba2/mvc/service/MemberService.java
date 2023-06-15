package com.example.moreba2.mvc.service;


import com.example.moreba2.mvc.domain.MemberVO;
import com.example.moreba2.mvc.model.dao.MemberDAO;
import com.example.moreba2.mvc.model.dto.MemberDTO;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// YES OR NO 판단하는 애 : 서비스
@Log4j2
public enum MemberService {
    // 컨트롤러를 간단하게 하기 위한 MemberService
    INSTANCE;

    private MemberDAO memberDAO;
    private ModelMapper modelMapper;

    // 1. 회원 가입을 위한 메소드
    public void addMember(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String year = req.getParameter("birthyy");             // 생일 연도
        String month = req.getParameterValues("birthmm")[0];   // 생일 월
        String day = req.getParameter("birthdd");              // 생일 일
        String mail1 = req.getParameter("mail1");              // 메일 앞문자
        String mail2 = req.getParameterValues("mail2")[0];     // 메일 뒷문자
        String address01 = req.getParameter("address01");      // 주소 앞(지역/동 등.. ?)
        String address02 = req.getParameter("address02");      // 주소 (상세 주소?)

        MemberVO memberVo = MemberVO.builder()
                .id(req.getParameter("id"))            // 아이디 값
                .passwd(req.getParameter("passwd"))        // 비밀번호 값
                .name(req.getParameter("name"))        // 이름값
                .nickname(req.getParameter("nickname"))// 닉네임
                .birth(year + "/" + month + "/" + day)       // 생일
                .mail(mail1 + "@" + mail2)                   // 이메일
                .phone(req.getParameter("phone"))      // 전화번호
                .address(address01 + "/" + address02)  // 주소
                .build();

        memberDAO = new MemberDAO();

        // 회원가입이 false 일 경우, true일 경우 : boolean 으로 잡아주기
        if (memberDAO.newMember(memberVo)) {
            // 회원가입 성공
            System.out.println("!!!SUCCESS AddMember!!!");
            resp.sendRedirect("/login.go?success"); // LoginController의 서블릿을 이용하여 가게 함. login.jsp 에서

        }
        else {
            // 회원가입 실패
            System.out.println("!!!Fail!!!");
//            req.getRequestDispatcher("").forward(req, resp);
            resp.sendRedirect("/login.go?fail");
        }
    }

    // 1-2. 아이디 중복확인을 위한 메소드
    public boolean checkId(HttpServletRequest req) throws Exception {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");

        System.out.println("MemberService checkId : " + id);

        memberDAO = new MemberDAO();

        System.out.println("MemberService, DAO getCheckID : " + memberDAO.checkId(id));

        return memberDAO.checkId(id);
    }

    // 1-3. 닉네임 중복 확인을 위한 메소드
    public boolean checkNickname(HttpServletRequest req) throws Exception {
        req.setCharacterEncoding("UTF-8");
        String nickname = req.getParameter("nickname");
        System.out.println(nickname);
        memberDAO = new MemberDAO();
        System.out.println(memberDAO.checkNickname(nickname));
        return memberDAO.checkNickname(nickname);
    }

    // 2. 로그인 실행 메소드 : 아이디와 비밀번호의 값을 받아옴
    public void loginInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        memberDAO = new MemberDAO();

        if (memberDAO.getLoginInfo(req)) {
            // 로그인이 성공한 경우
            resp.sendRedirect("/board/HotListAction.do?pageNum=1");


        } else {
            // 로그인이 실패한 경우
//            req.setAttribute("result", "error");
            req.getRequestDispatcher("/loginMember.go?loginFail").forward(req, resp);
        }
    }

    // 2-2. 자동 로그인 메소드 :


    // 2-3. 로그아웃 메소드
    public void logOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println("session ID :" + session.getId()); // 세션의 아이디 받아오기
        session.invalidate(); // 세션을 시원하게 날려서 그전 세션과 다른 세션 아이디를 받아와용
        System.out.println("invalidate after : " + session.getId());
//        req.getRequestDispatcher("/inc/loginFail.jsp").forward(req, resp);
        resp.sendRedirect("/loginMember.go");
    }

    // 3. 회원 정보 가져오기
    public void memberInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        String id = (String) session.getAttribute("sessionID");

        memberDAO = new MemberDAO();
        MemberVO memberVO;

        memberVO = memberDAO.selectOne(id);
        req.setAttribute("memberVO", memberVO); // el 태그로 memberVO를 사용할 수 있음.

    }

    // 3. 회원 전체 목록 가져오기
    public List<MemberDTO> listAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<MemberVO> voList = memberDAO.selectAll(); // memberDAO의 선택사항을 다 가지고 옴
        log.info("voList... :" + voList);
        List<MemberDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, MemberDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    // 4. 회원 수정을 위한 메소드
    public void updateMember(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        String id = (String) session.getAttribute("sessionID");
        String address01 = req.getParameter("address01");              // 주소 앞문자
        String address02 = req.getParameter("address02");     // 쥿ㅎ 뒷문자

        MemberVO memberVo = MemberVO.builder()
                .passwd(req.getParameter("passwd"))             // 비밀번호
                .address(address01 + "/" + address02)                 // 주소
                .phone(req.getParameter("phone"))               // 전화번호
                .id(id)
                .build();

        memberDAO = new MemberDAO();

        // 회원 정보 수정이 false일 경우, true일 경우
        System.out.println(memberDAO.updateMember(memberVo));
        if (memberDAO.updateMember(memberVo)) {
            // 회원 정보 수정 성공 => 결과값 보여주기 
            // updateMember의 flag가 1 이상이면 redirect 해서 마이페이지 보여줘야함
            resp.sendRedirect("/mypage/list.go");
        } else {
            // 회원 정보 수정이 실패할 경우, 마이페이지로 이동하고, alert창으로 정보 수정 취소했다 뜨도록..?
            resp.sendRedirect("/mypage/list.jsp");
        }
    }
}
