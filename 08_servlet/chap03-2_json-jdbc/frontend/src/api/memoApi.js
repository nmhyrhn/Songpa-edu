//메모 목록 조회
export async function getMemos() {

    const response = await fetch('/api/memos');

    if(!response.ok) {
        throw new Error('메모 목록을 불러오지 못했습니다.');
    }

    //JavaScript 데이터로 변환해서 반환
    // 반환값은 Memo 객체들이 들어있는 배열
    return response.json();
}

//메모 등록
export  async function createMemo(content){

    const response = await  fetch('/api/memos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',

        },
        //JavaScript 객체을 JSON 문자열로 바꿔 body에 담는다
        body: JSON.stringify({content}),
    });

    if(!response.ok) {
        throw new Error('메모 등록 실패');
    }
    //서버가 저장 후 돌려준 메모 객체 반환
    // {id: 3, content: '새 메모'}
    return response.json();
}