package com.example.moreba2.mvc.model.dao;

import com.example.moreba2.mvc.database.DBConnection;
import com.example.moreba2.mvc.model.dto.QnaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class QnaDAO {
    private static QnaDAO instance;

    private QnaDAO() {
    }

    public static QnaDAO getInstance() {
        if (instance == null)
            instance = new QnaDAO();
        return instance;
    }

    //qna 테이블 글 쓰기
    public void insertQna(QnaDTO qna) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        System.out.println("qqqqqqq");

        try {
            conn = DBConnection.getConnection();
            String sql = "insert into `moreba`.`qna` values(?,?,?,?,?,now(),?,?,?)";

            pstmt = conn.prepareStatement(sql);
            System.out.println(sql);
            pstmt.setInt(1, qna.getNum());
            pstmt.setString(2, qna.getId());
            pstmt.setString(3, qna.getName());
            pstmt.setString(4, qna.getSubject());
            pstmt.setString(5, qna.getContent());
            pstmt.setString(6, qna.getFilename());
            pstmt.setLong(7, qna.getFilesize());
            pstmt.setString(8, qna.getMal());

            pstmt.executeUpdate();
        }catch(Exception ex) {
            System.out.println("insertQna() 에러 : " + ex);
        } finally{
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

    // qna 테이블의 레코드 가져오기
    public ArrayList<QnaDTO> getQnaList(int page, int limit, String items, String text, String mal) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int total_record = getQnaCount(items,text);
        int start = (page - 1) * limit;
        int index = start + 1;

        String sql;

        if ( items == null && text == null)
            sql = "select * from `moreba`.`qna` order by num desc";
        else
            sql = "select * from `moreba`.`qna` where " + items + " like '%" + text + "%' order by num desc ";

        ArrayList<QnaDTO> list = new ArrayList<QnaDTO>();

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // ResultSet.absolute(int index) : ResultSet 커서를 원하는 위치(Index)의 검색행으로 이동하는 메서드. 해당 인덱스가 존재하면 true반환. 없으면 false 반환.
            while (rs.absolute(index)) {
                QnaDTO qna = new QnaDTO();
                qna.setNum(rs.getInt("num"));
                qna.setId(rs.getString("id"));
                qna.setName(rs.getString("name"));
                qna.setSubject(rs.getString("subject"));
                qna.setContent(rs.getString("content"));
                qna.setRegist_day(rs.getString("regist_day"));
                qna.setMal(rs.getString("mal"));
                list.add(qna);

                if (index < (start + limit) && index <= total_record)
                    index++;
                else
                    break;
            }
            return list;
        } catch (Exception ex) {
            System.out.println("getQnaList() 에러 : " + ex);
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

    // qna 테이블의 레코드 개수
    public int getQnaCount(String items, String text) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int x = 0; // 레코드 개수 저장할 변수.

        String sql;

        if (items == null && text == null)
            sql = "SELECT count(*) FROM `moreba`.`qna`";
        else
            sql = "select count(*) from `moreba`.`qna` where " + items + " like '%" + text + "%'";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next())
                x = rs.getInt(1);

        } catch (Exception ex) {
            System.out.println("getQnaCount() 에러: " + ex);
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

    // 선택된 글 상세 내용 가져오기
    public QnaDTO getQnaByNum(int num, int page) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        QnaDTO qna = null;

        String sql = "select * from `moreba`.`qna` where num = ? ";

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                qna = new QnaDTO();
                qna.setNum(rs.getInt("num"));
                qna.setId(rs.getString("id"));
                qna.setName(rs.getString("name"));
                qna.setSubject(rs.getString("subject"));
                qna.setContent(rs.getString("content"));
                qna.setRegist_day(rs.getString("regist_day"));
                qna.setFilename(rs.getString("filename"));
                qna.setMal(rs.getString("mal"));
            }
            return qna;
        } catch (Exception ex) {
            System.out.println("getQnaByNum() 에러 : " + ex);
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

    public void updateQna(QnaDTO dto) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            String sql = "UPDATE `moreba`.`qna` set name=?, subject=?, content=? where num=?";

            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getSubject());
            pstmt.setString(3, dto.getContent());
            pstmt.setInt(4, dto.getNum());

            pstmt.executeUpdate();

        } catch(Exception ex){
            System.out.println("updateQna() 에러 : " + ex);

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

    public void deleteQna(int num) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String sql = "DELETE from `moreba`.`qna` where num=? ";
        try{
            conn=DBConnection.getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.executeUpdate();
        } catch (Exception ex){
            System.out.println("deleteQna() 에러 : " + ex);
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

