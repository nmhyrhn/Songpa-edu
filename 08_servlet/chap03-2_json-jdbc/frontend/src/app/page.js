'use client'

import {useEffect, useState} from "react";
import {useMemoStore} from "@/store/memoStore";

export default function Home() {

    const [content, setContent] = useState('');

    const{memos, loading, error, fetchMemos, addMemo} = useMemoStore();

    useEffect(() => {

        //컴포넌트가 처음 화면에 나타났을 때 메모 목록을 불러온다
        fetchMemos();
    }, [fetchMemos]);

    //form 제출 시 실행되는 함수
    const handleSubmit = async (e) => {
        e.preventDefault(); //form 기본 새로고침 동작 막기

        //입력값이 비어있거나 공백이면 서버에 보내지 않는다
        if(!content.trim()) {
            return;
        }
        //Zustand의 action을 호출하여 서버에 메모 등록 요청을 보냄
        await addMemo(content);

        setContent('');

    }

    return (
        <>
            <main>
                <h1>JDBC Memo API</h1>

                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        placeholder="메모 내용 입력하세요"
                    />
                    <button type="submit">등록</button>
                </form>

                <button type="button" onClick={fetchMemos}>새로고침</button>

                {loading && <p>불러오는 중..</p>}

                {error && <p>{error}</p>}

                <ul>
                    {memos.map((memo) => (
                        <li key={memo.id}>
                           <span>
                               {memo.content} <samll>{memo.createdAt}</samll>
                           </span>

                        </li>

                    ))}
                </ul>

            </main>
        </>
    )
}
