import { create } from "zustand";

export const useCartStore = create((set) => ({
  items: [],

  addItem: (item) => set((state) => ({
    items: [...state.items, item],
    //기존의 배열을 직접 수정(push) 하면 안되고, 반드시 새로운 배열로 복사해서 
    //교체하여야 React가 상태 바뀐걸 눈치채고 화면을 다시 그린다.
  })),

  removeItem: (id) => set((state) => ({
    items: state.items.filter((item) => item.id !== id),
  })),

  clearCart: () => set({ items: [] }),
}));

/**
 * immer + zustand 와 같이 사용하면 일반 변수처럼 편안하게 수정할 수 있게 도와줌
 * immer란? 불변성을 유지하면서도 마치 가변 객체처럼 상태를 업데이트할 수 있게 해주는 라이브러리
 */