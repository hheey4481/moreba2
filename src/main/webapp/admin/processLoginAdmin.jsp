
<%@include file="/inc/dbconn.jsp"%>

<%
  request.setCharacterEncoding("UTF-8");

  String id = request.getParameter("id");
  String password = request.getParameter("password");

  String sql = "SELECT * FROM `moreba`.`admin` WHERE id = ? AND password = ?";
  pstmt = conn.prepareStatement(sql);
  pstmt.setString(1, id);
  pstmt.setString(2, password);
  rs = pstmt.executeQuery();
  if (rs.next()) { // 로그인 처리
    // 세션을 구움
    session.setAttribute("sessionAdminId", rs.getString("id"));
    session.setAttribute("sessionAdminName", rs.getString("name"));
    session.setAttribute("sessionAdminDay", rs.getString("lately_login_day"));

    // 최근 로그인한 날자를 변경
    sql = "UPDATE `moreba`.`admin` SET lately_login_day = now() WHERE id = ? and password = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, id);
    pstmt.setString(2, password);
    pstmt.executeUpdate();

    // 인덱스 페이지로 이동
    response.sendRedirect("index.jsp");
  }
  else {
    response.sendRedirect("loginAdmin.jsp?error=1");
  }
%>




