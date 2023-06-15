package com.example.moreba2.mvc.controller;

import com.example.moreba2.mvc.model.dao.MicDAO;
import com.example.moreba2.mvc.model.dto.MicDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@WebServlet("*.ho")
public class MicController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String RequestURI = request.getRequestURI();   //전체 경로 가져오기 (프로젝트명 + 주소)
        String contextPath = request.getContextPath();   //프로젝트의 path만 가져오기 (프로젝트명)
        String command = RequestURI.substring(contextPath.length());

        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");

        out.println(command);   //디버깅 방법 중에 하나. 밑에 찍히는 걸로 확인

//        if (command.contains("/MicDeleteAction.do")) {
//            requestMicDelete(req, resp);
//        }
//        if (command.contains("/MicViewAction.ho")) {    //mic 리스트 가져오기
//            //requestMicList(request);
//            RequestDispatcher rd = request.getRequestDispatcher("./MicView.ho");
//            rd.forward(request, response);
//        }

        if (command.contains("/MicWriteAction.ho")) {
            //글 등록하기 버튼을 누르면 데이터 베이스에 넣고 리스트 페이지로 이동
            requestMicWrite(request, response);
            RequestDispatcher rd = request.getRequestDispatcher("./micList.jsp");
            rd.forward(request, response);

        }
        else if (command.contains("/MicListAction.ho")) {
            requestMicList(request, response);
        }
        else {
            // command 잘못되었을 경우
            System.out.println("doGET wrong url(command) : " + command);
        }

    }

    private void requestMicList(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding ("UTF-8");

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        //String boardName = request.getParameter("boardName");
        //int num = Integer.parseInt(request.getParameter("num"));
        MicDAO dao = MicDAO.getInstance();
        ArrayList<MicDTO> list = dao.getMicList();

        StringBuilder result = new StringBuilder("{ \"listData\" : [");
        int i = 0;
        for (MicDTO dto : list) {
            //boolean flag = sessionId != null && sessionId.equals(dto.getMemberId()) ? true : false;
            result.append("{\"rippleId\":\"")
                    .append(dto.getMicId())
                    .append("\", \"content\":\"")
                    .append(dto.getContent())
                    //.append(flag)
                    .append("\"}");
            // value가 배열 형태로 들어가서 마지막 요소의 경우에는 콤마가 나오면 안됨.

            if (i++ < list.size() - 1)
                result.append(", ");
        }
        result.append("]}");
        // 결과 화면을 출력 스트림을 통해 출력
        out.append(result.toString());
    }

//    private void requestMicList(HttpServletRequest request) {
//        MicDAO dao = MicDAO.getInstance();
//        List<MicDTO> micList = new ArrayList<MicDTO>();
//        //int num = Integer.parseInt(request.getParameter("num"));
//
//        micList = dao.getMicList();
//        request.setAttribute("MicList", micList);
//    }

    public void requestMicWrite (HttpServletRequest request, HttpServletResponse response) throws IOException {
        MicDAO dao = MicDAO.getInstance();
        MicDTO mic = new MicDTO();
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        mic.setMemberId((String) session.getAttribute("sessionId"));
        mic.setContent(request.getParameter("content"));

        mic.setIp(request.getRemoteAddr());
        String result = "{\"result\" : ";
        if (dao.insertMic(mic)) {
            result += "\"true\"}";
        } else {
            result += "\"false\"}";

        }
        // 결과 화면을 출력 스트림을 통해 출력
        PrintWriter out = response.getWriter();
        out.append(result);
    }





}


