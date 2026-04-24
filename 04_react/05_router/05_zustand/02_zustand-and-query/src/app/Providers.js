'use client'

import { QueryClient, QueryClientProvider } from "@tanstack/react-query"
import { useState } from "react"

export default function Providers({children}) {
  const [queryClient] = useState(() => new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 60 * 1000, //1분 동안은 다시 요청하지 않고, 캐시를 사용
      },
    },
  }));

  return (
    <QueryClientProvider client = {queryClient}>
      {children}
    </QueryClientProvider>
  )
}