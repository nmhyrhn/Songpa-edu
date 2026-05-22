package com.ohgiraffers.frontcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemoController {

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<MemoDTO> memos = new ArrayList<>();
    private int sequence = 1;

    public MemoController() {

        memos.add(new MemoDTO(sequence++, "Front Controller로 /api/* 요청 받기"));
        memos.add(new MemoDTO(sequence++, "next.js 화면 이동은 프론트 라우터가 담당하기"));
    }

    //목록 조회
    public void findAll(HttpServletResponse response) throws IOException {
        mapper.writeValue(response.getWriter(), memos);
    }

    //메모 등록
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MemoDTO requestMemo = mapper.readValue(req.getReader(), MemoDTO.class);
        String content = requestMemo.getContent() == null ? "" : requestMemo.getContent().trim();

        if(content.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("content is required"));
            return;
        }

        MemoDTO savedMemo = new MemoDTO(sequence++, content);
        memos.add(0, savedMemo);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        mapper.writeValue(resp.getWriter(), savedMemo);
    }

    //메모 삭제
    public void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        MemoDTO requestMemo = mapper.readValue(req.getReader(), MemoDTO.class);

        //요청 body(본문)에서 id를 읽는다
        // List에서 같은 id를 가진 메모를 제거한다.
        boolean removed = memos.removeIf(memo -> memo.getId() == requestMemo.getId());

        if(removed) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(resp.getWriter(), new ErrorResponse("Memo를 찾을 수 없습니다."));
            return;
        }

        //삭제 성공
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }





}


