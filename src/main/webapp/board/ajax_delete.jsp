
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.moreba.mvc.model.dao.RippleDAO" %>
<%@ page import="com.example.moreba.mvc.model.dto.RippleDTO" %>
<%@ include file="../inc/dbconn.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%

    request.setCharacterEncoding("UTF-8");
    int rippleId = Integer.parseInt(request.getParameter("rippleId"));

    RippleDAO dao = RippleDAO.getInstance();
    RippleDTO ripple = new RippleDTO();
    ripple.setRippleId(rippleId);
    if(dao.deleteRipple(ripple)){ %>
{"result":"true"}
    <%
    }
    else { %>
    {"result":"false"}
    <%}
%>