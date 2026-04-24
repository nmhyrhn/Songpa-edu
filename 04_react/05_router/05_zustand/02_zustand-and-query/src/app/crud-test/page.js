'use client'

import { useEffect, useState } from "react";
import { postApi } from "../api/PostApi";

export default function CRUDPage() {

  const [posts, setPosts] = useState([]);

  //Read: 처음 로드할 때 목록 가져오기
  const loadPosts = async() => {
    const data = await postApi.getPosts();
    setPosts(data);
  };

  useEffect(() => {loadPosts();}, [])

  //글 등록 테스트
  const handleAdd = async () => {
    await postApi.createPost({title: "새로운 글" + Date.now(), author: "익명"});
    loadPosts();
  };

  return (
    <>
      <button onClick={handleAdd}>
        새 글 등록
      </button>

      {posts.map(post => (
        <div key={post.id} style={{border: '1px solid black'}}>
          <h3>{post.title} (작성자: {post.author})</h3>
        </div>
      ))}
    </>
  )
}