package com.example.moreba2.mvc.filter;//package com.example.moreba.mvc.filter;
//
//import com.example.moreba.mvc.model.dto.MemberDTO;
//import com.example.moreba.mvc.service.MemberService;
//import lombok.extern.log4j.Log4j2;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Log4j2
//@WebServlet(urlPatterns = {"//*"})
//public class LoginCheckFilter implements Filter {
//    // 자동 로그인 필터 질문사항,
//    // Session의 값을 받아오는 아이들을 다 pattern으로 지정해야하는가?
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("Login check filter..");
//
//        HttpServletRequest req = (HttpServletRequest)request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//
//        HttpSession session = req.getSession();
//
//        if(session.getAttribute("loginInfo") != null) {
//            chain.doFilter(req, resp);
//            return;
//        }
//
//        // session에 loginInfo 값이 없다면, 쿠키를 체크하기
//        Cookie cookie = findCookie(req.getCookies(), "myLog");
//
//        // 세션에도 없고, 쿠키에도 없을 경우, 일반 로그인으로
//        if (cookie == null) {
//            resp.sendRedirect("/login.go");
//            return;
//        }
//
//        // 쿠키가 존재하는 상태라면,
//        log.info("cookie is exist");
//        String uuid = cookie.getValue(); // uuid 값
//
//        try {
//            // 데이터 베이스 확인
//            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);
//
//            log.info("cookie value search user info : " + memberDTO);
//            if(memberDTO == null) {
//                throw new Exception ("Cookie value is not valid");
//            }
//
//            // 회원 정보를 세션에 추가
//            session.setAttribute("loginInfo", memberDTO);
//            chain.doFilter(req, resp);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    private Cookie findCookie(Cookie[] cookies, String cookieName) {
//        // 현재 요청(req)에 있는 모든 쿠키 중, 조회 목록 쿠키를 찾아내는 .. ?
//        // 특정 ~가 쿠키의 내용물에 있는지 확인???
//        Cookie targetCookie = null;
//
//        if(cookies != null && cookies.length > 0) {
//            for(Cookie cookie : cookies) {
//                if(cookie.getName().equals(cookieName)) {
//                    targetCookie = cookie;
//                    break;
//                }
//            }
//        }
//        return targetCookie;
//    }
//
//}
