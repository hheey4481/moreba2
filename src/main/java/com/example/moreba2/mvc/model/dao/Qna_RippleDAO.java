package com.example.moreba2.mvc.model.dao;

import com.example.moreba2.mvc.database.DBConnection;
import com.example.moreba2.mvc.model.dto.Qna_RippleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Qna_RippleDAO {

    private static Qna_RippleDAO instance;

    private Qna_RippleDAO() {

    }

    public static Qna_RippleDAO getInstance(){
        if(instance==null)
            instance=new Qna_RippleDAO();
        return instance;
    }

    public boolean insertRipple(Qna_RippleDTO dto){
        Connection conn = null;
        PreparedStatement pstmt = null;
        int flag =1;
        try {

            conn = DBConnection.getConnection();
            String sql = "insert into `moreba`.`qna_ripple` values (?,?,?, ?, ?,?,now())";

            pstmt = conn.prepareStatement(sql);
            System.out.println(sql);
            pstmt.setInt(1, dto.getRippleId());
            pstmt.setString(2,dto.getBoardName());
            pstmt.setInt(3, dto.getNum());
            pstmt.setString(4, dto.getMemberId());
            pstmt.setString(5, dto.getName());
            pstmt.setString(6, dto.getContent());

            System.out.println(dto.getRippleId());

            flag=pstmt.executeUpdate();
            System.out.println(sql);

        } catch (Exception ex) {
            System.out.println("insertRipple() 에러 : " + ex);
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
        return flag !=0;
    }

    public boolean deleteRipple(Qna_RippleDTO ripple) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int flag = 0;
        String sql = "DELETE from `moreba`.`qna_ripple` where rippleId=? ";
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ripple.getRippleId());
            flag=pstmt.executeUpdate();

            System.out.println(sql);
            System.out.println(ripple.getRippleId());
        } catch (Exception ex) {
            System.out.println("deleteRipple() 에러 : " + ex);
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
        return flag !=0;
    }

    public ArrayList<Qna_RippleDTO> getRippleList(String Name, int Num){
        //게시판 종류와 게시글 번호 필요. 매개변수 2개
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        System.out.println(Name + Num);
        String sql = "SELECT * FROM `moreba`.`qna_ripple` WHERE boardName = ? AND Num = ?";
        ArrayList<Qna_RippleDTO> list = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Name);
            preparedStatement.setInt(2, Num);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Qna_RippleDTO dto = new Qna_RippleDTO();
                dto.setRippleId(resultSet.getInt("rippleId"));
                dto.setBoardName(resultSet.getString("boardName"));
                dto.setNum(resultSet.getInt("Num"));
                dto.setMemberId(resultSet.getString("memberId"));
                dto.setName(resultSet.getString("name"));
                dto.setContent(resultSet.getString("content"));
                dto.setInsertDate(resultSet.getString("insertDate"));
                list.add(dto);
            }
        } catch(Exception ex){
            System.out.println("insertQnaRipple() 에러 : " + ex);
        } finally{
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return list;
    }
}

