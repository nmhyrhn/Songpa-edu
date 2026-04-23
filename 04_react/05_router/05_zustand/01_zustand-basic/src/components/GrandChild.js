'use client';

import { useStore } from "@/store/useStore";


export default function GrandChild({count, setCount}) {

  const { count, increase, decrease, text, setText } = useStore();
  console.log(count);
  
  return (
    <div>
      <h2>{count}</h2>
      <button onClick={increase}>증가</button>
      <button onClick={decrease}>감소</button>
      
      <hr/>

      <input value={text} onChange={(e) => setText(e.target.value)} />
      <p>{text}</p>
    </div>
  )
}