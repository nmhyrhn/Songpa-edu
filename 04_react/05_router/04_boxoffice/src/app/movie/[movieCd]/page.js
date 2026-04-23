import Link from "next/link";
import { getMovieDetail } from "@/api/MovieAPI";

export default async function MovieDetail({ params }) {
  const movie = await getMovieDetail(params.movieCd);

  if (!movie) {
    return (
      <div>
        <p>영화 정보를 불러오지 못했습니다.</p>
        <Link href="/movie">목록으로 돌아가기</Link>
      </div>
    );
  }

  return (
    <div>
      <h1>{movie.movieNm}</h1>
      <p>영화 제목: {movie.movieNm}</p>
      <p>개봉일: {movie.openDt}</p>
      <p>장르: {movie.genres.map((g) => g.genreNm).join(", ")}</p>
      <p>감독: {movie.directors.map((d) => d.peopleNm).join(", ")}</p>
      <Link href="/movie">목록으로 돌아가기</Link>
    </div>
  );
}
