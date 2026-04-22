import { usePathname } from "next/navigation";

export default function Navbar()  {
    
    const pathname = usePathname(); //현재 url 경로를 문자열로 반환
  
    return (
      <>
        <div>
          <ul>
            <li><Link href="/">메인</Link></li>
            <li><Link href="/about">소개</Link></li>
            <li><Link href="/menu">매뉴</Link></li>
          </ul>
        </div>
      </>
    )
}