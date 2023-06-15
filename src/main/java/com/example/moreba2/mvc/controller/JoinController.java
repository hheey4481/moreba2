package com.example.moreba2.mvc.controller;

import com.example.moreba2.mvc.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Control : URL만 전달하는 애임

@Log4j2
@WebServlet("*.join")
public class JoinController extends HttpServlet {
    // 회원가입 관련 Controller : 회원가입 입력, 중복 검사, 주소 등
    // 질문사항 : doGet에서 doPost로 (req, resp) 방식으로 해도 되는지 여쭙기.

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter out = resp.getWriter(); // 컨트롤러에서 입력되도록 함
        
        // command 가져오기
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String command = requestURI.substring(contextPath.length());

        System.out.println("JoinController command :" + command);

        if (command.contains("/addMember.join")) {
            // 톰캣 실행 시 페이지 이동 버튼
            // 이후, 회원가입 버튼을 클릭하면 가야하는 경로
            // 단순히 보이는 view 화면과, 등록으로 resp 되는 화면의 서블릿이 같으면 안됨! 거기서 일어난 오류사항이었음.
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/member/addMember.jsp");
            rd.forward(req, resp);
        }
        else if(command.contains("/JoinController/checkID.join")) {
            // 아이디 중복 확인
            System.out.println("success");

            try {
                if(MemberService.INSTANCE.checkId(req)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(command.contains("/JoinController/checkNick.join")) {
            // ajax 닉네임 중복확인
            System.out.print("checkNick.join success");

            try {
                if(MemberService.INSTANCE.checkNickname(req)) {
                    out.print("true");
                }
                else {
                    out.print("false");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (command.contains("/joinMember.join")) {
            // 서비스에서 회원가입(addMember)메소드 호출 (입력한 값을 저장함)
            try {
                MemberService.INSTANCE.addMember(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else {
            // command 잘못되었을 경우
            System.out.println("wrong url(command) : " + command);
        }

    }
}
