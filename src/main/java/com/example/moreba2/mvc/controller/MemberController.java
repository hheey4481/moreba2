package com.example.moreba2.mvc.controller;


import com.example.moreba2.mvc.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
@WebServlet("*.go")
public class MemberController extends HttpServlet {
    // 로그인, 로그아웃, 회원 정보 수정, 회원 탈퇴 등

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter out = resp.getWriter(); // 컨트롤러에서 입력되도록 함

        // command 하기
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = requestURI.substring(contextPath.length());

        System.out.println("MemberController command : " + command);

        if(command.contains("/loginMember.go")) {
            // 로그인 버튼을 클릭하면 가야하는 경로
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/member/login.jsp");
            rd.forward(req, resp);
        }
        
        else if(command.contains("/login.go")) {
            // 아이디와 비밀번호를 입력하고, 로그인 버튼을 클릭했을 때 실행
            // 1. 아이디와 비밀번호의 값을 받아오기
            // 2. 받아온 값과 DB의 값을 비교
            // 3. 같으면 로그인 성공, 다르면 로그인 실패

            try {
                MemberService.INSTANCE.loginInfo(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
        else if(command.contains("/logout.go")) {
            // MemberService 에서 로그아웃 하는 메소드 호출
            // 1. 로그아웃 버튼을 누르면, 로그아웃 실행되고, 메인으로 이동.
            MemberService.INSTANCE.logOut(req, resp);
        }
        else if(command.contains("/mypage/list.go")) {
            // 마이페이지 누르면 가야하는 경로
            try {
                MemberService.INSTANCE.memberInfo(req, resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            RequestDispatcher rd = req.getRequestDispatcher("/mypage/list.jsp");
            rd.forward(req, resp);
            System.out.println("success /mypage/list.go");


        }
        else if(command.contains("/mypage/listModify.go")) {
            // 마이페이지 사용자 정보 변경 경로
            // 회원 정보 수정 페이지로 이동함
            System.out.println("success /mypage/listModify.go");
            RequestDispatcher rd = req.getRequestDispatcher("/mypage/listModify.jsp");
            rd.forward(req, resp);

        }
        else if(command.contains("/mypage/modifyF.go")) {
            // 마이페이지 사용자 정보 변경 경로
            // 변경을 하면 DB에서 업데이트 되야함.
            try {
                MemberService.INSTANCE.updateMember(req, resp);
                System.out.println("aaaa");
            } catch (Exception e) {
                System.out.println("wrong command /mypage/listModify.go :" + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else {

        }
    }

}
