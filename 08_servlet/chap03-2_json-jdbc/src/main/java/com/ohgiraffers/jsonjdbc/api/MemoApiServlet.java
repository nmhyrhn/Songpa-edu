package com.ohgiraffers.jsonjdbc.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


@WebServlet("/api/memos")
public class MemoApiServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();
    private  final MemoService memoService =  new MemoService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        if(!"/api/memos".equals(req.getServletPath())){
            resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("GET /api/memos를 사용하세요"));
            return;
        }

        List<MemoDTO> memos = memoService.findAllMemos();
        mapper.writeValue(resp.getWriter(), memos);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        registMemo(req,resp);
    }

    private void registMemo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MemoDTO requestMemo = mapper.readValue(req.getReader(), MemoDTO.class);
        String content = requestMemo.getContent() == null ? "" : requestMemo.getContent().trim();

        if(content.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("Content가 필요합ㄴ디ㅏ."));
            return;
        }

        MemoDTO savedMemo = memoService.registMemo(content);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        mapper.writeValue(resp.getWriter(), savedMemo);
    }
}
