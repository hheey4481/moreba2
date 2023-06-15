<%@ page import="com.example.moreba.mvc.model.dao.RippleDAO" %>
<%@ page import="com.example.moreba.mvc.model.dto.RippleDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file ="../inc/dbconn.jsp" %>

<%
    RippleDAO dao = RippleDAO.getInstance();
    RippleDTO ripple = new RippleDTO();

    request.setCharacterEncoding("UTF-8");

    ripple.setBoardName(request.getParameter("boardName"));
    ripple.setBoardNum(Integer.parseInt(request.getParameter("num")));
    ripple.setMemberId((String) session.getAttribute("sessionId"));
    ripple.setName(request.getParameter("name"));
    ripple.setContent(request.getParameter("content"));
    ripple.setIp(request.getRemoteAddr());

    if (dao.insertRipple(ripple)) {%>
{"result" : "true"}
<%
}

else {%>
{"result" : "false"}
<%}
%>