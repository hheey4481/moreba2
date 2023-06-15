package com.example.moreba2.mvc.model.dao;

import com.example.moreba2.mvc.database.DBConnection;
import com.example.moreba2.mvc.model.dto.NoticeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NoticeDAO {
    private static NoticeDAO instance;

    private NoticeDAO() {

    }

    public static NoticeDAO getInstance() {
        if (instance == null)
            instance = new NoticeDAO();
        return instance;
    }

    // board 테이블의 레코드 개수
    public int getNoticeCount(String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int x = 0; // 레코드 개수 저장할 변수.

        String sql;

            sql = "SELECT count(*) FROM `moreba`.`notice`";
//

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next())
                x = rs.getInt(1);

        } catch (Exception ex) {
            System.out.println("getNoticeCount() 에러: " + ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return x;
    }

    // board 테이블의 레코드 가져오기
    public ArrayList<NoticeDTO> getNoticeList(int page, int limit, String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total_record = getNoticeCount(items, text);
        int start = (page - 1) * limit;
        int index = start + 1;

        String sql;

        sql = "select * from `moreba`.`notice` order by num desc";


        ArrayList<NoticeDTO> list = new ArrayList<NoticeDTO>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // ResultSet.absolute(int index) : ResultSet 커서를 원하는 위치(Index)의 검색행으로 이동하는 메서드. 해당 인덱스가 존재하면 true반환. 없으면 false 반환.
            while (rs.absolute(index)) {
                NoticeDTO board = new NoticeDTO();
                board.setNum(rs.getInt("num"));
                board.setId(rs.getString("id"));
                board.setName(rs.getString("name"));
                board.setSubject(rs.getString("subject"));
                board.setContent(rs.getString("content"));
                board.setRegist_day(rs.getString("regist_day"));
                board.setHit(rs.getInt("hit"));
                board.setIp(rs.getString("ip"));
                list.add(board);

                if (index < (start + limit) && index <= total_record)
                    index++;
                else
                    break;
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getNoticeList() 에러 : " + ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return null;
    }

    //member 테이블에서 인증된 id의 사용자명 가져오기
    public String getLoginNameById(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String name = null;
        String sql = "select * from `moreba`.`member` where id = ? ";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next())
                name = rs.getString("name");
            return name;

        } catch (Exception ex) {
            System.out.println("getBoardByNum() 에러: " + ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return name;
    }

    public void insertNotice(NoticeDTO notice) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBConnection.getConnection();

            String sql = "insert into `moreba`.`notice` values (?, ?, ?, ?, ?, now(), ?, ?,?,?)";

            pstmt = conn.prepareStatement(sql);
            System.out.println(sql);
            pstmt.setInt(1, notice.getNum());
            pstmt.setString(2, notice.getId());
            pstmt.setString(3, notice.getName());
            pstmt.setString(4, notice.getSubject());
            pstmt.setString(5, notice.getContent());
//            pstmt.setString(6, board.getRegist_day());
            pstmt.setInt(6, notice.getHit());
            pstmt.setString(7, notice.getIp());
            pstmt.setString(8,notice.getFilename());
            pstmt.setLong(9,notice.getFilesize());



            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("insertNotice() 에러 : " + ex);
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    // 선택된 글 상세 내용 가져오기
    public NoticeDTO getNoticeByNum(int num, int page) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        NoticeDTO notice = null;

        updateNoticeHit(num);
        String sql = "select * from `moreba`.`notice` where num = ? ";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                notice = new NoticeDTO();
                notice.setNum(rs.getInt("num"));
                notice.setId(rs.getString("id"));
                notice.setName(rs.getString("name"));
                notice.setSubject(rs.getString("subject"));
                notice.setContent(rs.getString("content"));
                notice.setRegist_day(rs.getString("regist_day"));
                notice.setHit(rs.getInt("hit"));
                notice.setIp(rs.getString("ip"));
                notice.setFilename(rs.getString("filename"));
            }
            return notice;
        } catch (Exception ex) {
            System.out.println("getNoticeByNum() 에러 : " + ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return null;
    }

    // 선택된 글의 조회수 증가하기
    public void updateNoticeHit(int num) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
//            String sql = "update `webMarketDB`.`board` set hit = hit + 1 where num = ?";
            conn = DBConnection.getConnection();

//            pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, num);

            String sql = "select `hit` from `moreba`.`notice` where num = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            int hit = 0;

            if (rs.next()) {
                hit = rs.getInt("hit") + 1;
            }

            sql = "update `moreba`.`notice` set hit = ? where num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, hit);
            pstmt.setInt(2, num);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("updateNoticeHit() 에러 : " + ex);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    //선택된 글 내용 수정하기
    public void updateNotice(NoticeDTO notice){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            String sql = "UPDATE `moreba`.`notice` set name=?, subject=?, content=? where num=?";

            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, notice.getName());
            pstmt.setString(2, notice.getSubject());
            pstmt.setString(3, notice.getContent());
            pstmt.setInt(4, notice.getNum());

            pstmt.executeUpdate();

        } catch(Exception ex){
            System.out.println("updateNotice() 에러 : " + ex);

        } finally {
            try{
                if(pstmt !=null)
                    pstmt.close();
                if(conn!=null)
                    conn.close();
            } catch(Exception ex){
                throw new RuntimeException(ex.getMessage());
            }

        }
    }

    public void deleteNotice(int num){
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "DELETE from `moreba`.`notice` where num=? ";
        try{
            conn=DBConnection.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.executeUpdate();
        } catch (Exception ex){
            System.out.println("deleteBoard() 에러 : " + ex);
        } finally {
            try{
                if(pstmt!=null)
                    pstmt.close();
                if(conn!=null)
                    conn.close();
            } catch(Exception ex){
                throw new RuntimeException(ex.getMessage());
            }
        }

    }




}
