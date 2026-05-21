/** @type {import('next').NextConfig} */
const nextConfig = {
    //URL 재작성 규칙을 정의한느 함수
    async rewrites() {
        return[
            {
            //개발 서버가 감지할 요청 주소
            source: '/api/:path*',
            // 실제 요청을 전달할 대상 주소
            destination: 'http://localhost:8080/api/:path*',

            }
        ]
    }

};

export default nextConfig;
