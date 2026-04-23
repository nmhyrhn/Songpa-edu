import axios from 'axios';

const API_KEY = 'f71c6fb45d7012f29c78c8a65c49b23d';
const BASE_URL = 'https://www.kobis.or.kr/kobisopenapi/webservice/rest';

/**일별 박스 오피스 정보 조회 */
export async function getBoxOfficeList() {
    try{
      const response = await axios.get(`${BASE_URL}/boxoffice/searchDailyBoxOfficeList.json`,{
        params: {
          key: API_KEY,
          targetDt: 20260422
        }
      });
      console.log('일별 박스 오피스: ',response.data);
      return response.data.boxOfficeResult.dailyBoxOfficeList;
    } 
    catch (error){
      console.error('영화 정보 조회 실패', error);
    }
}

/**영화 코드 전달하여 영화 상세 정보 조회 */
export async function getMovieDetail(movieCd) {
  try {
    const response = await axios.get(`${BASE_URL}/movie/searchMovieInfo.json`, {
      params: {
        key: API_KEY,
        movieCd: movieCd
      }
    });
    console.log('영화 상세 정보:', response.data);
    return response.data.movieInfoResult.movieInfo;
  } catch (error) {
    console.error('영화 상세 조회 실패', error);
  }
}




