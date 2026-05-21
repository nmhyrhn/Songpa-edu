package com.ohgiraffers.jsonjdbc.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static com.ohgiraffers.jsonjdbc.commom.JDBCTemplate.close;
import static com.ohgiraffers.jsonjdbc.commom.JDBCTemplate.getConnection;

public class MemoService {

    private final MemoDAO memoDAO = new MemoDAO();

    public List<MemoDTO> findAllMemos() {

        Connection con = getConnection();

        try {
            return memoDAO.selectAllMemos(con);

        } finally {
            close(con);
        }



    }


    public MemoDTO registMemo(String content) {
        Connection con = getConnection();

        try{
            return  memoDAO.insertMemo(con, content);
        } finally {
            close(con);
        }
    }
}
