'use client'
import { usePersistStroe } from "@/store/usePersistStore";
import { useEffect, useState } from "react";


export default function PersistPage() {

  const {theme, toggleTheme} = usePersistStroe();

  //Hydration 에러 해결하기 위한 state 선언
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    setIsMounted(true);
  },[]);

  if(!isMounted) return <div>로딩 중...</div>;

  return (
    <div style={{background: theme === "dark" ? "#333" : "#fff", height:"100vh", padding:"20px"}}>
      <p>현재 테마: {theme}</p>
      <button onClick={toggleTheme}>테마 변경</button>
    </div>
  )
}