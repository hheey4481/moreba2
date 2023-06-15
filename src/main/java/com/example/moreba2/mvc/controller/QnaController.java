package com.example.moreba2.mvc.controller;

import com.example.moreba2.mvc.model.dao.QnaDAO;
import com.example.moreba2.mvc.model.dao.Qna_RippleDAO;
import com.example.moreba2.mvc.model.dto.QnaDTO;
import com.example.moreba2.mvc.model.dto.Qna_RippleDTO;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet("*.ro")
//@WebServlet(name="qnaController", value="/qna/Q_writeForm.ro")
public class QnaController extends HttpServlet {
    static final int LISTCOUNT = 5; // 페이지 당 게시물 수
    private String qnaName = "qna";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);   //get 방식으로 넘어온 것을 post로 넘기는 것
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String RequestURI = req.getRequestURI();   //전체 경로 가져오기 (프로젝트명 + 주소)
        String contextPath = req.getContextPath();   //프로젝트의 path만 가져오기 (프로젝트명)
        String command = RequestURI.substring(contextPath.length());

        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("utf-8");

        System.out.println(command);   //디버깅 방법 중에 하나. 밑에 찍히는 걸로 확인
        if (command.contains("/QnaListAction.ro")) {//등록된 글 목록 페이지 출력하기
            requestQnaList(req);   //메소드 실행
            RequestDispatcher rd = req.getRequestDispatcher("../qna/Q_list.jsp");
            //RequestDispatcher rd객체가 가지고 있는 path에 해당하는 페이지로 이동
            //객체를 생성후 forward() 메소드를 이용하여 이동
            rd.forward(req, resp);

        } else if (command.contains("/QnaWriteForm.ro")) {// qna 글 등록 페이지
            RequestDispatcher rd = req.getRequestDispatcher("../qna/Q_writeForm.jsp");
            rd.forward(req, resp);
        } else if (command.contains("/QnaWriteAction.ro")) { //새로운 글 등록하기
            try {
                requestQnaWrite(req);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = req.getRequestDispatcher("../qna/QnaListAction.ro");
            rd.forward(req, resp);
        } else if (command.contains("/QnaViewAction.ro")) { // 선택된 글 상세 페이지 가져오기
            requestQnaView(req);
//            requestRippleList(req);
            RequestDispatcher rd = req.getRequestDispatcher("../qna/QnaView.ro");
            rd.forward(req, resp);
        }else if (command.contains("/QnaView.ro")) { // 글 상세 페이지 출력하기
            RequestDispatcher rd = req.getRequestDispatcher("../qna/Q_view.jsp");
            rd.forward(req, resp);
        } else if (command.contains("/QnaUpdateAction.ro")) { //글 수정하기
            requestQnaUpdate(req);
            RequestDispatcher rd = req.getRequestDispatcher("../qna/QnaListAction.ro");
            rd.forward(req, resp);
        } else if (command.contains("/QnaDeleteAction.ro")) {//선택된 글 삭제하기
            requestQnaUpdateDelete(req);
            RequestDispatcher rd = req.getRequestDispatcher("../qna/QnaListAction.ro");
            rd.forward(req, resp);
        }else if (command.contains("/QnaUpdateForm.ro")) { //글 수정폼 출력
            //
            requestQnaView(req);
            RequestDispatcher rd = req.getRequestDispatcher("../qna/Q_updateForm.jsp");
            rd.forward(req, resp);
        }
//        else if (command.contains("/QnaRippleWriteAction.ro")) {//리플쓰기
//            requestQnaRippleWrite(req);
//         //   String num = req.getParameter("num");
//        //    String pageNum = req.getParameter("pageNum");
//           // resp.sendRedirect("QnaViewAction.ro?num=" + num + "&pageNum=" + pageNum);
//        }else if (command.contains("/QnaRippleDeleteAction.ro")) {
//            //리플삭제
//            requestQnaRippleDelete(req);
//            String num = req.getParameter("num");
//            String pageNum = req.getParameter("pageNum");
//            resp.sendRedirect("QnaViewAction.ro?num=" + num + "&pageNum=" + pageNum);
//        }
        else if (command.contains("/QnaRippleDeleteAction.ro")) {
            requestRippleDelete(req, resp);
        }
        else if (command.contains("/QnaRippleWriteAction.ro")) {
            requestRippleWrite(req, resp);
        } else if (command.contains("/QnaRippleListAction.ro")) {
            requestRippleList(req, resp);
        }
    }



    //등록된 글 목록 가져오기
    private void requestQnaList(HttpServletRequest req) {
        QnaDAO dao = QnaDAO.getInstance();
        List<QnaDTO> qnaList = new ArrayList<QnaDTO>();

        int pageNum = 1;   //페이지 번호가 전달이 안 되면 1페이지
        int limit = LISTCOUNT;   //페이지 당 게시물 수

        if (req.getParameter("pageNum") != null) //페이지 번호가 전달된 경우
            pageNum = Integer.parseInt(req.getParameter("pageNum"));



        String items = req.getParameter("items");   //검색 필드
        String text = req.getParameter("text");      //검색어
        String mal = req.getParameter("mal");

        qnaList = dao.getQnaList(pageNum, limit, items, text,mal);    //현재 페이지에 해당하는 목록 데이터 들고 오기
        int total_record = dao.getQnaCount(items, text);

        int total_page;      //전체 페이지

        if (total_record % limit == 0) {   //전체 게시물이 limit의 배수일 때 페이지 수
            total_page = total_record / limit;
            Math.floor(total_page);
        } else {   //나머지 남을 때 페이지 수
            total_page = total_record / limit;
            Math.floor(total_page);
            total_page = total_page + 1;
        }

        //view에 보여줄 거 정리하는 역할?
        req.setAttribute("limit", limit);
        req.setAttribute("pageNum", pageNum);         //페이지 번호
        req.setAttribute("total_page", total_page);      //전체 페이지 수
        req.setAttribute("total_record", total_record);   //전체 게시물 수
        req.setAttribute("qnaList", qnaList);      //현재 페이지에 해당하는 목록 데이터
        req.setAttribute("text", text);
        req.setAttribute("items", items);

    }


    //qna 글 새로 등록하기
    private void requestQnaWrite(HttpServletRequest req) throws Exception{
        QnaDAO dao = QnaDAO.getInstance();
        QnaDTO dto = new QnaDTO();
        HttpSession session = req.getSession();
        dto.setId((String) session.getAttribute("sessionId"));

        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 폼 페이지에서 전송된 파일을 저장할 서버의 경로를 작성.
        String path = "/Users/parksohee/img";
        // 파일 업로드를 위해 DiskFileUpload 클래스를 생성.
        DiskFileUpload upload = new DiskFileUpload();
        // 업로드할 파일의 최대 크기, 메모리상에 저장할 최대 크기, 업로드된 파일을 임시로 저장할 경로를 작성.
        upload.setSizeMax(1000000);
        upload.setSizeThreshold(4096);
        upload.setRepositoryPath(path);
        // 폼 페이지에서 전송된 요청 파라미터를 전달받도록 DiskFileUpload 객체 타입의 parseRequest() 메서드를 작성.
        List items = upload.parseRequest(req);
        // 폼 페이지에서 전송된 요청 파라미터를 Iterator 클래스로 변환.
        Iterator params = items.iterator();
        while (params.hasNext()) { // 폼 페이지에서 전송된 요청 파라미터가 없을 때까지 반복하도록 Iterator 객체 타입의 hasNext() 메서드를 작성.
            // 폼 페이지에서 전송된 요청 파라미터의 이름을 가져오도록 Iterator 객체 타입의 next() 메서드를 작성.
            FileItem item = (FileItem) params.next();
            if (item.isFormField()) {
                // 폼 페이지에서 전송된 요청 파라미터가 일반 데이터이면 요청 파라미터의 이름과 값을 출력.
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                switch (name) {
                    case "mal":
                        dto.setMal(value);
                    case "name":
                        dto.setName(value);
                        break;
                    case "subject":
                        dto.setSubject(value);
                        break;
                    case "content":
                        dto.setContent(value);
                        break;
                }
                System.out.println(name + "=" + value + "<br>");
            }
            else {
                //폼페이지에서 전송된 요청 파라미터가 파일이면
                //요청 파라미터의 이름, 저장 파일의 이름, 파일 컨텐츠 유형, 파일 크기에 대한 정보를 출력.
                String fileFieldName = item.getFieldName();
                String fileName = item.getName();
                String contentType=item.getContentType();

                if(!fileName.isEmpty()){
                    System.out.println("파일 이름 : " + fileName);
                    fileName = nowStr + fileName.substring(fileName.lastIndexOf("\\") + 1);
                    long fileSize = item.getSize();

                    File file = new File(path + "/" + fileName);
                    item.write(file);

                    dto.setFilename(fileName);
                    dto.setFilesize(fileSize);

                    System.out.println("----------------------------------<br>");
                    System.out.println("요청 파라미터 이름 : " + fileFieldName  + "<br>");
                    System.out.println("저장 파일 이름 : " + fileName + "<br>");
                    System.out.println("파일 콘텐츠 타입 : " + contentType + "<br>");
                    System.out.println("파일 크기 : " + fileSize);
                }
            }
        }
        dao.insertQna(dto);
    }

    //선택된 글 상세 페이지 가져오기
    private void requestQnaView(HttpServletRequest req) {
        QnaDAO dao = QnaDAO.getInstance();
        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        QnaDTO qna = new QnaDTO();
        qna = dao.getQnaByNum(num, pageNum);

        req.setAttribute("num", num);
        req.setAttribute("page", pageNum);
        req.setAttribute("qna", qna);
    }

    //선택된 글 내용 수정하기
    private void requestQnaUpdate(HttpServletRequest req) {
        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        QnaDAO dao = QnaDAO.getInstance();
        QnaDTO dto = new QnaDTO();
        dto.setNum(num);
        dto.setName(req.getParameter("name"));
        dto.setSubject(req.getParameter("subject"));
        dto.setContent(req.getParameter("content"));

        dao.updateQna(dto);
    }

    //선택된 글 삭제하기
    private void requestQnaUpdateDelete(HttpServletRequest req) {
        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        QnaDAO dao = QnaDAO.getInstance();
        dao.deleteQna(num);
    }

    private void requestQnaRippleWrite(HttpServletRequest req)  throws UnsupportedEncodingException {
        /* 댓글 등록 */
        int num = Integer.parseInt(req.getParameter("num"));
        Qna_RippleDAO dao = Qna_RippleDAO.getInstance();
        Qna_RippleDTO dto = new Qna_RippleDTO();
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();

        dto.setBoardName(this.qnaName);
        dto.setNum(num);
        dto.setMemberId((String) session.getAttribute("sessionId"));
        dto.setName(req.getParameter("name"));
        dto.setContent(req.getParameter("content"));

        dao.insertRipple(dto);
    }
    private void requestQnaRippleDelete(HttpServletRequest req) {
        //댓글 삭제
        int rippleId = Integer.parseInt(req.getParameter("rippleId"));

        Qna_RippleDAO dao = Qna_RippleDAO.getInstance();
        Qna_RippleDTO ripple = new Qna_RippleDTO();
        ripple.setRippleId(rippleId);
        dao.deleteRipple(ripple);
    }

    private void requestRippleDelete(HttpServletRequest req, HttpServletResponse resp) {
        //댓글 삭제
        int rippleId = Integer.parseInt(req.getParameter("rippleId"));

        Qna_RippleDAO dao = Qna_RippleDAO.getInstance();
        Qna_RippleDTO dto = new Qna_RippleDTO();
        dto.setRippleId(rippleId);
        dao.deleteRipple(dto);
    }

    private void requestRippleWrite(HttpServletRequest req, HttpServletResponse resp)throws IOException {

        Qna_RippleDAO dao = Qna_RippleDAO.getInstance();
        Qna_RippleDTO dto = new Qna_RippleDTO();
        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");

        dto.setNum(Integer.parseInt(req.getParameter("num")));
        dto.setMemberId((String) session.getAttribute("sessionId"));
        dto.setName(req.getParameter("name"));
        dto.setContent(req.getParameter("content"));
        dto.setBoardName(req.getParameter("qnaName"));

        String result = "{\"result\" : ";
        if (dao.insertRipple(dto)) {
            result += "\"true\"}";
        } else {
            result += "\"false\"}";

        }
        // 결과 화면을 출력 스트림을 통해 출력
        PrintWriter out = resp.getWriter();
        out.append(result);
    }
    private void requestRippleList(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        req.setCharacterEncoding ("UTF-8");

        HttpSession session = req.getSession();
        String sessionId = (String) session.getAttribute("sessionId");
        String qnaName = req.getParameter("qnaName");
        int num = Integer.parseInt(req.getParameter("num"));
        Qna_RippleDAO dao = Qna_RippleDAO.getInstance();
        ArrayList<Qna_RippleDTO> list = dao.getRippleList(qnaName, num);

        StringBuilder result = new StringBuilder("{ \"listData\" : [");
        int i = 0;
        for (Qna_RippleDTO dto : list) {
            boolean flag = sessionId != null && sessionId.equals(dto.getMemberId()) ? true : false;
            result.append("{\"rippleId\":\"")
                    .append(dto.getRippleId())
                    .append("\", \"name\":\"")
                    .append(dto.getName())
                    .append("\", \"content\":\"")
                    .append(dto.getContent())
                    .append("\", \"isWriter\":\"")
                    .append(flag).append("\"}");
            // value가 배열 형태로 들어가서 마지막 요소의 경우에는 콤마가 나오면 안됨.

            if (i++ < list.size() - 1)
                result.append(", ");
        }
        result.append("]}");
        // 결과 화면을 출력 스트림을 통해 출력
        PrintWriter out = resp.getWriter();
        out.append(result.toString());
    }

}



