import Header from "./Header";
import Navbar from "./Navbar";

export default function Layout({children}) {
    return (
        <>
            <Header/>
            <Navbar/>
            {children}  {/* 현재 페이지 내용이 들어올 자리 */}
        </>
    )
}