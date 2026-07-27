from fastapi import FastAPI
from D_08_fastapi.D_07_router_openapi.routers import courses, students
app = FastAPI(
    title="Router and OpenAPI",
    description="students와 corses router를 하나의 API로 조립",
    version="1.0.0",
    openapi_tags=[
        {"name": "system", "description": "서버 상태 확인"},
        {"name": "students", "description": "학생 생성/조회/삭제"},
        {"name": "courses", "description": "강좌 조회"},
    ]
)

app.include_router(courses.router, prefix="/api/v1")
app.include_router(students.router, prefix="/api/v1")

@app.get("/health", tags=["system"])
def health() -> dict[str, str]:
    return {"status": "ok", "routers": "students, coureses"}