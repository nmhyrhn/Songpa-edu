'use client';
import Link from "next/link";
import { useState } from "react";
import { getBoxOfficeList } from "@/api/MovieAPI";

export default function Movie() {
  const [list, setList] = useState([]);
  const [error, setError] = useState(null);

  const handleMovie = async () => {
    setError(null);
    const data = await getBoxOfficeList();

    if (!data || data.length === 0) {
      setError("영화 목록을 불러오지 못했습니다.");
      return;
    }

    setList(data);
  };

  return (
    <>
      <h1>지금 가장 핫한 영화는?</h1>
      <button onClick={handleMovie}>영화 정보 조회하기</button>

      {list.length > 0 && (
        <ul>
          {list.map((movie) => (
            <li key={movie.movieCd}>
              <Link href={`/movie/${movie.movieCd}`}>
                {movie.rank}. {movie.movieNm} (개봉: {movie.openDt})
              </Link>
            </li>
          ))}
        </ul>
      )}
    </>
  );
}