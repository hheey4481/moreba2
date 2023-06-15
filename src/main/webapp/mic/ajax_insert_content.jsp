<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file ="../inc/dbconn.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.moreba2.mvc.model.dao.MicDAO" %>
<%@ page import="com.example.moreba2.mvc.model.dto.MicDTO" %>

<%
  MicDAO dao = MicDAO.getInstance();
  MicDTO micDTO = new MicDTO();

  request.setCharacterEncoding("UTF-8");

  micDTO.setMicId(Integer.parseInt(request.getParameter("micId")));
  micDTO.setMemberId((String) session.getAttribute("sessionId"));
  micDTO.setContent(request.getParameter("content"));

  micDTO.setIp(request.getRemoteAddr());

  if (dao.insertMic(micDTO)) {%>
{"result" : "true"}
<%
}

else {%>
{"result" : "false"}
<%}
%>
