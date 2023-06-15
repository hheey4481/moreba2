package com.example.moreba2.mvc.controller;

import com.example.moreba2.mvc.model.dao.NoticeDAO;
import com.example.moreba2.mvc.model.dto.NoticeDTO;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet("*.no")
public class NoticeController extends HttpServlet  {
    static final int LISTCOUNT = 5;   //페이지 당 게시물 수

    private String noticeName = "notice";   //원래 board

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

        //url 패턴을 확인해서 /BoardListAction.do 얘가 맞으면 메소드 실행
        if (command.contains("/NoticeListAction.no")) {//등록된 글 목록 페이지 출력하기
            requestNoticeList(req);   //메소드 실행
            RequestDispatcher rd = req.getRequestDispatcher("../notice/list.jsp");
            //RequestDispatcher rd객체가 가지고 있는 path에 해당하는 페이지로 이동
            //객체를 생성후 forward() 메소드를 이용하여 이동
            rd.forward(req, resp);

        } else if (command.contains("/NoticeWriteForm.no")) {   //글 등록 페이지 출력하기
            //requestLoginName(req);   //메소드 실행
            RequestDispatcher rd = req.getRequestDispatcher("../admin/notice/writeForm.jsp");
            rd.forward(req, resp);
        } else if (command.contains("/NoticeWriteAction.no")) { //새로운 글 등록하기
            try {
                requestNoticeWrite(req);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            RequestDispatcher rd = req.getRequestDispatcher("../notice/NoticeListAction.no");
            rd.forward(req, resp);
        } else if (command.contains("/NoticeViewAction.no")) { // 선택된 글 상세 페이지 가져오기
            requestNoticeView(req);
            RequestDispatcher rd = req.getRequestDispatcher("../notice/NoticeView.no");
            rd.forward(req, resp);
        } else if (command.contains("/NoticeView.no")) { // 글 상세 페이지 출력하기
            RequestDispatcher rd = req.getRequestDispatcher("../notice/view.jsp");
            rd.forward(req, resp);
        } else if (command.contains("/NoticeUpdateAction.no")) { //글 수정하기
            requestNoticeUpdate(req);
            RequestDispatcher rd = req.getRequestDispatcher("../notice/NoticeListAction.no");
            rd.forward(req, resp);
        } else if (command.contains("/NoticeDeleteAction.no")) {//선택된 글 삭제하기
            requestNoticeDelete(req);
            RequestDispatcher rd = req.getRequestDispatcher("../notice/NoticeListAction.no");
            rd.forward(req, resp);
        } else if (command.contains("/NoticeUpdateForm.no")) { //글 수정폼 출력
            //
            requestNoticeView(req);
            RequestDispatcher rd = req.getRequestDispatcher("../admin/notice/updateForm.jsp");
            rd.forward(req, resp);
        }

    }

    //등록된 글 목록 가져오기
    public void requestNoticeList(HttpServletRequest req) {

        NoticeDAO dao = NoticeDAO.getInstance();
        List<NoticeDTO> noticelist = new ArrayList<NoticeDTO>();

        int pageNum = 1;   //페이지 번호가 전달이 안 되면 1페이지
        int limit = LISTCOUNT;   //페이지 당 게시물 수

        if (req.getParameter("pageNum") != null) //페이지 번호가 전달된 경우
            pageNum = Integer.parseInt(req.getParameter("pageNum"));


        String items = req.getParameter("items");   //검색 필드
        String text = req.getParameter("text");      //검색어
//
        noticelist = dao.getNoticeList(pageNum, limit, items, text);   //현재 페이지에 해당하는 목록 데이터 들고 오기
        int total_record = dao.getNoticeCount(items, text);

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
        req.setAttribute("noticelist", noticelist);      //현재 페이지에 해당하는 목록 데이터
        req.setAttribute("text", text);
        req.setAttribute("items", items);

    }

    //인증된 사용자명 가져오기
//        public void requestLoginName(HttpServletRequest req){
//
//            String id = req.getParameter("id");   //이렇게 하는 건 보안 상 안 좋고 session으로 받아야 함
//
//            NoticeDAO dao = NoticeDAO.getInstance();
//
//            String name = dao.getLoginNameById(id); //이름으로 된 문자열 반환하도록 만들어보기
//
//            req.setAttribute("name", name);
//        }


    //새로운 글 등록하기
    public void requestNoticeWrite(HttpServletRequest req) throws Exception{

        NoticeDAO dao = NoticeDAO.getInstance();
        NoticeDTO notice = new NoticeDTO();
        HttpSession session = req.getSession();
        notice.setId((String) session.getAttribute("sessionId"));
        notice.setId((String) session.getAttribute("sessionName"));

        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//        notice.setId(req.getParameter("id"));
//        notice.setName(req.getParameter("name"));
//        notice.setSubject(req.getParameter("subject"));
//        notice.setContent(req.getParameter("content"));
//
//        System.out.println(req.getParameter("name"));
//        System.out.println(req.getParameter("subject"));
//        System.out.println(req.getParameter("content"));
//
//        notice.setHit(0);
//            notice.setRegist_day(regist_day);


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
                    case "name":
                        notice.setName(value);
                        break;
                    case "subject":
                        notice.setSubject(value);
                        break;
                    case "content":
                        notice.setContent(value);
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

                    notice.setFilename(fileName);
                    notice.setFilesize(fileSize);

                    System.out.println("----------------------------------<br>");
                    System.out.println("요청 파라미터 이름 : " + fileFieldName  + "<br>");
                    System.out.println("저장 파일 이름 : " + fileName + "<br>");
                    System.out.println("파일 콘텐츠 타입 : " + contentType + "<br>");
                    System.out.println("파일 크기 : " + fileSize);
                }
            }
        }
        notice.setIp(req.getRemoteAddr());

        dao.insertNotice(notice);
    }


    // 선택된 글 상세 페이지 가져오기
    public void requestNoticeView(HttpServletRequest request) {
        NoticeDAO dao = NoticeDAO.getInstance();
        int num = Integer.parseInt(request.getParameter("num"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));

        NoticeDTO notice = new NoticeDTO();
        notice = dao.getNoticeByNum(num, pageNum);

        request.setAttribute("num", num);
        request.setAttribute("page", pageNum);
        request.setAttribute("notice", notice);
    }

    //선택된 글 내용 수정하기
    public void requestNoticeUpdate(HttpServletRequest req) {

        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        NoticeDAO dao = NoticeDAO.getInstance();
        NoticeDTO notice = new NoticeDTO();
        notice.setNum(num);
        notice.setName(req.getParameter("name"));
        notice.setSubject(req.getParameter("subject"));
        notice.setContent(req.getParameter("content"));

        dao.updateNotice(notice);
    }

    //선택된 글 삭제하기
    public void requestNoticeDelete(HttpServletRequest req) {
        int num = Integer.parseInt(req.getParameter("num"));
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        NoticeDAO dao = NoticeDAO.getInstance();
        dao.deleteNotice(num);
    }


}
