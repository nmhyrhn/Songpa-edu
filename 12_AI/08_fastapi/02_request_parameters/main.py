from enum import StrEnum
from typing import Annotated
from fastapi import FastAPI, Path, Query
from pydantic import BaseModel

app = FastAPI(title="Request Parameters")

# URL에서 허용할 값을 제한하기위한 Enum
class ModelName(StrEnum):
    alexnet = "alexnet"
    resnet = "resnet"
    lenet = "lenet"

# API가 반환하는 데이터 구조를 정의
class ItemResponse(BaseModel):
    id: int       # 상품ID
    name: str     # 상품명
    category: str # 카테고리

ITEMS = [
    ItemResponse(id=1, name="FastAPI 기초", category="python"),
    ItemResponse(id=2, name="REST API", category="web"),
    ItemResponse(id=3, name="Spring 연동", category="java"),
    ItemResponse(id=4, name="파일 API", category="python")
]



@app.get("/items/{item_id}", tags=["path"])
async def read_item(
    item_id: Annotated[int, Path(ge=1, description="1 이상의 item ID")]
) -> dict[str, int]:
    return {"item_id": item_id}

@app.get("/models/{model_name}", tags=["path"])
async def read_model(model_name: ModelName) -> dict[str, str]:
    descriptions = {
        ModelName.alexnet: "이미지 분류 모델",
        ModelName.resnet: "잔차 연결을 사용하는 이미지 모델",
        ModelName.lenet: "초기 합성곱 신경망"
    }

    return {"model_name": model_name, "description": descriptions[model_name]}


# 고정 경로는 동적 경로보다 먼저 선언해야 
# "me"가 user_id로 처리되지 않습니다.
@app.get("/user/me", tags=["path-order"])
async def read_current_user() -> dict[str, str]:
    return {"user_id": "me", "role": "current user"}

@app.get("/user/{user_id}", tags=["path-order"])
async def read_user(user_id: str) -> dict[str, str]:
    return {"user_id": user_id, "role": "selected user"}

@app.get("/items", 
        response_model=list[ItemResponse], 
        tags=["query"])
async def list_items(
    skip: Annotated[int, Query(ge=0)] = 0,
    limit: Annotated[int, Query(ge=1, le=10)] = 3,
    category: str | None = None
) -> list[ItemResponse]:
    filtered = [item for item in ITEMS if category is None or item.category == category]
    return filtered[skip : skip + limit]