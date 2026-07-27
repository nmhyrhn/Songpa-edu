import asyncio  # 비동기 작업
import time     # 동기 방식의 대기를 사용하기 위한 표준 라이브러리
from typing import Annotated   # 타입 힌트
from fastapi import FastAPI, Query

app = FastAPI(
    title="01. FastAPI Start",
    description="FastAPI 객체, decorator...등을 비교",
    version="1.0.0"
)

# tags는 Swagger에서 API를 그룹화하는 용도
@app.get("/", tags=["start"])
async def root() -> dict[str, str]:
    """GET /요청과 root함수 연결 """
    return {"message": "Hello FastAPI"}

@app.get("/hello/{name}", tags=["start"])
async def say_hello(name: str) -> dict[str, str]:
    # Python Dictionary를 반환하면 FastAPI가 자동으로 JSON으로 변환된다.
    return {"message": f"Hello {name}"} 

@app.get("/server-info", tags=["start"])
async def server_info() -> dict[str, str]:
    return {
        "framework": "FastAPI",
        "server": "Uvicorn",
        "document": "/docs",
        "openapi": "/openai.json"
    }

@app.post("/echo", tags=["method"])
async def echo(message: str = "hello") -> dict[str, str]:
    """같은 URL이라도 허용하지 않은 Method로 요청하면 405에러가 발생"""
    return {"method": "POST", "message": message}

@app.get("/wait/sync", tags=["async"])
def sync_wait(
    seconds: Annotated[float, Query(ge=0, le=2)] = 1
) -> dict[str, float | str]:
    time.sleep(seconds)
    return {"type": "sync", "waited_seconds": seconds}

@app.get("/wait/async", tags=["async"])
async def async_wait(
    seconds: Annotated[float, Query(ge=0, le=2)] = 1
) -> dict[str, float | str]:
    await asyncio.sleep(seconds)
    return {"type": "async", "waited_seconds": seconds}