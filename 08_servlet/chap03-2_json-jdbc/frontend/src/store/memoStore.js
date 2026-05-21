import {createMemo, getMemos} from "@/api/memoApi";
import {create} from "zustand";

export const useMemoStore = create((set) => ({

    memos: [],
    loading: false,
    error: '',
    //서버에서 메모 목록을 가져오는 action
    fetchMemos: async () => {
        //요청 시작 전 loading은 true로, 이전 에러 메시지는 빈 문자열로 초기화
        set({loading: true, error:''});

        try{
            const memos = await getMemos();

            //받아온 배열을 Zustand 상태에 저장
            set({memos})
        } catch (error){
            //요청 중 문제가 생겼을 시 에러 메시지를 상태에 저장
            set({error: error.message})
        } finally {
            set({loading: false});
        }

    },

    //메모 등록 action
    addMemo: async (content) => {
        set({error:''});

        try{
            const savedMemo = await createMemo(content);

            set((state) => ({
                //기존 memos 배열 뒤에 새 메모를 추가한 새 배열을 만든다
                //기돈 배열을 직접 push 하지 않고 새 배열을 만드는 방식이 안전하다
                memos: [...state.memos, savedMemo],
            }));
        } catch (error) {
            set({error: error.message});
        }
    }


}))