<%@ page import="com.example.moreba2.mvc.model.dao.Qna_RippleDAO" %>
<%@ page import="com.example.moreba2.mvc.model.dto.Qna_RippleDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file ="../inc/dbconn.jsp" %>

<%
    Qna_RippleDAO dao = Qna_RippleDAO.getInstance();
    Qna_RippleDTO dto = new Qna_RippleDTO();

    request.setCharacterEncoding("UTF-8");

    dto.setBoardName(request.getParameter("boardName"));
    dto.setNum(Integer.parseInt(request.getParameter("num")));
    dto.setMemberId((String) session.getAttribute("sessionId"));
    dto.setName(request.getParameter("name"));
    dto.setContent(request.getParameter("content"));

    if (dao.insertRipple(dto)) {%>
{"result" : "true"}
<%
}

else {%>
{"result" : "false"}
<%}
%>