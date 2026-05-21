package com.ohgiraffers.jsonjdbc.api;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.ohgiraffers.jsonjdbc.commom.JDBCTemplate.close;

public class MemoDAO {

    //목록 조회
    public List<MemoDTO> selectAllMemos(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<MemoDTO> memos = new ArrayList<>();

        String query = """
                        SELECT memo_id, content, created_at
                        FROM tbl_memo
                        ORDER BY memo_id DESC
                        """;

        try {
            pstmt = con.prepareStatement(query);
            rset =  pstmt.executeQuery();

            while(rset.next()){
                memos.add(new MemoDTO(
                        rset.getInt("memo_id"),
                        rset.getString("content"),
                        formatTimestamp(rset.getTimestamp("created_at"))
                ));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return memos;

    }

    //메모 등록
    public MemoDTO insertMemo(Connection con, String content) {
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;

        String  query = "INSERT INTO tbl_memo (content) VALUES (?)";

        try {
            //Statement.RETURN_GENERATED_KEYS: INSERT 실해애 후 자동 생성된 PK를 읽을 수 있게 해줌
            pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, content);
            pstmt.executeUpdate();

            //db가 자동으로 만든 Key 목록을 ResultSet 형태로 반환 받을 수 있다.
            generatedKeys = pstmt.getGeneratedKeys();

            if(generatedKeys.next()){
                int id = generatedKeys.getInt(1);
                return selectMemoById(con, id);
            }
            throw  new SQLException("생성된 memo_id를 읽을 수 없다");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            close(generatedKeys);
            close(pstmt);
        }

    }

    private MemoDTO selectMemoById(Connection con, int id) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;


        String query = """
                       SELECT memo_id, content, created_at
                       FROM tbl_memo
                       WHERE memo_id = ?;                  
                        """;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            rset = pstmt.executeQuery();

            if(rset.next()){
                return new MemoDTO(
                        rset.getInt("memo_id"),
                        rset.getString("content"),
                        formatTimestamp(rset.getTimestamp("created_at"))
                );
            }
            throw new SQLException("등록된 메모를 조회할 수 없습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(rset);
            close(pstmt);

        }
    }





    private  String formatTimestamp(Timestamp timestamp){
        if(timestamp == null){
            return "";
        }
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }



}
