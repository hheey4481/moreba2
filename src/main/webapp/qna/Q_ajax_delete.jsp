<%@ page import="com.example.moreba2.mvc.model.dao.Qna_RippleDAO" %>
<%@ page import="com.example.moreba2.mvc.model.dto.Qna_RippleDTO" %>
<%@ include file="../inc/dbconn.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%

    request.setCharacterEncoding("UTF-8");
    int rippleId = Integer.parseInt(request.getParameter("rippleId"));

    Qna_RippleDAO dao = Qna_RippleDAO.getInstance();
    Qna_RippleDTO ripple = new Qna_RippleDTO();
    ripple.setRippleId(rippleId);
    if(dao.deleteRipple(ripple)){ %>
{"result":"true"}
    <%
    }
    else { %>
    {"result":"false"}
    <%}
%>