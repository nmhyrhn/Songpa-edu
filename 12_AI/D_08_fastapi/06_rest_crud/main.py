from typing import Annotated, Literal

from fastapi import FastAPI, HTTPException, Query, Response, status
from pydantic import BaseModel, Field

app = FastAPI(title="REST CRUD")

class TaskResponse(BaseModel):
  id: int
  title: str
  description: str | None
  priority: Literal["low", "normal", "high"]
  completed: bool

class TaskReplace(BaseModel):
  title: str = Field(min_length=1, max_length=100)
  description: str | None = Field(default=None, max_length=300)
  priority: Literal["low", "normal", "high"]
  completed: bool

class TaskUpdate(BaseModel):
  title: str | None = Field(default=None, min_length=1, max_length=100)
  description: str | None = Field(default=None, max_length=300)
  priority: Literal["low", "normal", "high"] | None = None
  completed: bool | None

tasks: dict[int, TaskResponse] = {
  1: TaskResponse(
    id=1,
    title="FastAPI 문서 열기",
    description="/docs에서 endpoint확인",
    priority="high",
    completed=False
  ),
  2: TaskResponse(
    id=2,
    title="Path와 Query복습",
    description=None,
    priority="normal",
    completed=True
  )
}

def find_task_or_404(task_id: int) -> TaskResponse:
  task = tasks.get(task_id)
  if task is None:
    raise HTTPException(status_code=404, detail="해당 task를 찾을 수 없습니다.")
  return task

@app.get("/api/v1/tasks", response_model=list[TaskResponse], tags=["tasks"])
def list_tasks(
  completed: bool | None = None,
  priority: Literal["low", "normal", "high"] | None = None,
  keyword: Annotated[str | None, Query(min_length=2)] = None
) -> list[TaskResponse]:
  result = list(tasks.values())
  if completed is not None:
    result = [task for task in result if task.completed == completed]
  if priority is not None:
    result = [task for task in result if task.priority == priority]
  if keyword is not None:
    result = [task for task in result if keyword.lower() in task.title.lower()]
  return result

# 고정결로는 /{task_id} 보다 먼저 선언한다.
@app.get("/app/v1/tasks/{task_id}", response_model=TaskResponse, tags=["tasks"])
def get_task(task_id: int)-> TaskResponse:
  return find_task_or_404(task_id)

@app.put("/api/v1/tasks/{task_id}", response_model=TaskResponse, tags=["tasks"])
def replace_task(task_id: int, request: TaskReplace) -> TaskResponse:
  """PUT은 ID를 유지하면서 나머지 자원 표현 전체를 교체"""
  find_task_or_404(task_id)
  replaced = TaskResponse(id=task_id, **request.model_dump())
  tasks[task_id] = replaced
  print(tasks)
  return replaced

@app.patch("/api/v1/tasks/{task_id}", response_model=TaskResponse, tags=["tags"])
def update_task(task_id: int, request: TaskUpdate) -> TaskResponse:
  """exclude_unset=True로 클라이언트가 보낸 필드만 수정"""
  task = find_task_or_404(task_id)
  changed_fileds = request.model_dump(exclude_unset=True)
  updated = task.model_copy(update=changed_fileds)
  tasks[task_id] = updated
  return updated