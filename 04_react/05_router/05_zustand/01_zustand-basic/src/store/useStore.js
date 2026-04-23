import { create } from "zustand";

export const useStore = create((set) => ({
  count: 0,
  text:"",

  increase: () => set((state) => ({ count: state.count + 1 })),
  decrease: () => set((state) => ({ count: state.count - 1 })),

  setText: (value) => set({text : value})
}));