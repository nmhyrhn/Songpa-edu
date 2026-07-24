from typing import Annotated

from fastapi import Body, FastAPI
from pydantic import BaseModel, Field

app = FastAPI(title="03 Request Body")

class ItemCreate(BaseModel):
    name: str = Field(min_length=1, max_length=50)
    description: str | None = None
    price: float = Field(gt=0)
    tax: float | None = Field(default=None, ge=0)

class UserInfo(BaseModel):
    """사용자정보"""
    username: str = Field(min_length=2, max_length=30)
    full_name: str | None = None

@app.post("/items", tags=["simple-body"])
async def create_item(item: ItemCreate) -> dict:
    """Pydantic 모델 매개변수는 JSON Body로 해석된다."""
    result = item.model_dump()
    print("🍩" , result)
    return result

# http://localhost:8000/items/10?notify=True
@app.put("/items/{item_id}", tags=["combined"])
async def update_item(
    item_id: int,
    item: ItemCreate,
    notify: bool = False,
) -> dict:
    # FastAPI가 Path, Query, Body를 타입 선언으로 구분
    return {"item_id": item_id, "item": item, "notify": notify}

@app.post("/users/{user_id}/items", tags=["multiple-body"])
async def create_user_item(
    user_id: int,
    item: ItemCreate,
    user: UserInfo,
    importance: Annotated[int, Body(gt=0, le=5)]
) -> dict:
    # 여러모델과 단일 값을 하나의 JSON Body에서 받는다.
    return {
        "user_id": user_id,
        "item": item,
        "user": user,
        "importance": importance
    }