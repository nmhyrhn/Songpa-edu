"""여러 router가 함께 사용하는 요청과응답 모델"""
from typing import Literal

from pydantic import BaseModel, Field

Level = Literal["beginner", "intermediate"]

class StudentCreate(BaseModel):
    name: str = Field(min_length=2, max_length=30)
    level: Level = "beginner"

class StudentResponse(BaseModel):
    id: int
    name: str
    level: Level

class CourseResponse(BaseModel):
    id: int
    title: str
    level: Level
    hours: int