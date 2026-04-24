import { create } from "zustand";
import { persist } from "zustand/middleware";

export const usePersistStroe = create(
  persist( // localStorage에 저장하는 기능
    (set) => ({
      theme: "light",
      toggleTheme: () => set((state) => ({theme:state.theme === "light"? "dark" : "light"}))
    }),
    {
      name: "theme-storage"
    }
  )
)