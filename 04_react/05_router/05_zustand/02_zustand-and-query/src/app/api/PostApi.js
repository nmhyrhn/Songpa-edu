const BASE_URL = "http://localhost:3001/posts";

export const postApi = {
  getPosts: async() =>{
    const res = await fetch(BASE_URL);
    return res.json();
  },
  //새로운 글 등록하기
  //주소는 그대로지만 method를 POST로
  //body에 데이터를 담아 보낸다.
  createPost: async(newPost) => {
    const res = await fetch(BASE_URL, {
      method: "POST",
      headers:{"Content-Type": "application/json"}, //JSON 데이터임을 알리기
      body: JSON.stringify(newPost),
    });
    return res.json();
  }
}