export {create} from "zustand";

export const useUserStore = create((set) => ({
  user: null,
  loading: false,

  login: (userData) => set({ user: userData }),
  logout: () => set({ user: null }),
  setUser: (user) => set({user}),

  //비동기 액션 추가
  fetchUser: async() => {
    set({loading: true}); //통신 시작 시 로딩 true

    try {
      const res = await fetch('https://jsonplaceholder.typicode.com/users/1');
      const data = await res.json();

      set({user:data}); //데이터 저장
    } catch (error) {
      console.error('유저 정보 조회 실패', error);
    } finally {
      set({loading: false}); //통신 끝나면 로딩 false
    } 
  }
}));