'use client';
import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Navbar() {

    const pathname = usePathname(); // 현재 url 경로를 문자열로 반환

    const isActive = (path) => pathname === path;

    const activeStyle= {
        backgroundColor : 'yellow',
        color: 'red'
    }

    return (
        <>
            <div>
                <ul>
                    <li><Link style={isActive('/')? activeStyle : undefined} href="/">메인</Link></li>
                    <li><Link style={isActive('/about')? activeStyle : undefined} href="/about">소개</Link></li>
                    <li><Link style={isActive('/menu')? activeStyle : undefined} href="/menu">메뉴</Link></li>
                    <li><Link style={isActive('/dashboard')? activeStyle : undefined} href="/dashboard">관리자</Link></li>
                </ul>
            </div>
        </>
    )
}