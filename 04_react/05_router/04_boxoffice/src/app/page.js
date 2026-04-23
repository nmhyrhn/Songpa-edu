import Link from "next/link";

export default function Home() {
    return (
      <>
        <h1>영화 상영 순위</h1>
        <br/>
        <nav>
          <Link href="/movie">지금 확인 해보기</Link>
        </nav>
      </>
    );
}