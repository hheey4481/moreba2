package com.example.moreba2.mvc.model.dao;

import com.example.moreba2.mvc.database.DBConnection;
import com.example.moreba2.mvc.model.dto.MicDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MicDAO {

    private static MicDAO instance;

    private MicDAO() {

    }

    public static MicDAO getInstance() {
        if(instance == null)
            instance = new MicDAO();
        return instance;
    }

    public boolean insertMic(MicDTO micDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int flag = 1;
        try {
            connection = DBConnection.getConnection();
            String sql = "insert into `moreba`.`mic` values (?,?,?,now(),?)";

            preparedStatement = connection.prepareStatement(sql);
            //System.out.println(sql)
            preparedStatement.setInt(1, micDTO.getMicId());
            preparedStatement.setString(2, micDTO.getMemberId());
            preparedStatement.setString(3, micDTO.getContent());
            preparedStatement.setString(4, micDTO.getIp());

            flag = preparedStatement.executeUpdate();

            System.out.println(sql);

        } catch (Exception ex) {
            System.out.println("insertBoard() 에러 : " + ex);

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return flag != 0;
    }

    public ArrayList<MicDTO> getMicList() {
        //게시판 종류와 게시글 번호 필요. 매개변수 2개
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM `moreba`.`mic`";
        ArrayList<MicDTO> list = new ArrayList<>();
        try {
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MicDTO micDTO = new MicDTO();
                micDTO.setMicId(resultSet.getInt("micId"));
                micDTO.setMemberId(resultSet.getString("memberId"));
                micDTO.setContent(resultSet.getString("content"));
                micDTO.setInsertDate(resultSet.getString("insertDate"));
                micDTO.setIp(resultSet.getString("ip"));
                list.add(micDTO);
            }
        } catch(Exception ex){
            System.out.println("insertBoard() 에러 : " + ex);
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
