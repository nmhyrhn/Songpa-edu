import Footer from "./Footer";
import Header from "./Header";

export default function Layout({children}) {
    return (
      <>
        <Header/>
        {children}  {/* 현재 페이지 내용이 들어올 자리 */}
        <Footer/> 
      </>
    )
}