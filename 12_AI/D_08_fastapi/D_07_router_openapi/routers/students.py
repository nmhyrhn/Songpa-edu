from typing import Annotated
from fastapi import APIRouter, HTTPException, Query

from D_08_fastapi.D_07_router_openapi.schemas import (
    Level,
    StudentCreate,
    StudentResponse
)

router = APIRouter(prefix="/students", tags=["students"])
students: dict[int, StudentResponse] = {
    1: StudentResponse(id=1, name="수진", level="beginner"),
    2: StudentResponse(id=2, name="민수", level="intermediate")
}

@router.get("", response_model=list[StudentResponse])
def list_students(
    level: Level | None = None,
    keyword: Annotated[str | None, Query(min_lenght=1)] = None
) -> list[StudentResponse]:
    result = list(students.values())
    if level is not None:
        result = [student for student in result if student.level == level]
    if keyword is not None:
        result = [student for student in result if keyword in student.name]
    return result