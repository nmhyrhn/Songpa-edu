""" 강좌 자원의 조회 기능만 담당하는 APIRouter """

from fastapi import APIRouter, HTTPException

from D_08_fastapi.D_07_router_openapi.schemas import CourseResponse, Level

router = APIRouter(prefix="/courses", tags=["courses"])
courses = {
    1: CourseResponse(id=1, title="FastAPI", level="beginner", hours=8),
    2: CourseResponse(id=2, title="Spring 연동", level="intermediate", hours=6),
    3: CourseResponse(id=3, title="파일 REST API", level="beginner", hours=3)
}

@router.get("", response_model=list[CourseResponse])
def list_courses(level: Level | None = None) -> list[CourseResponse]:
    result = list(courses.values())
    if level is not None:
        result = [course for course in result if course.level == level]
    return result

@router.get("/{course_id}", response_model=CourseResponse)
def get_course(course_id: int) -> CourseResponse:
    course = courses.get(course_id)
    if course is None:
        raise HTTPException(status_code=404, detail="강좌를 찾을 수 없습니다.")
    return course