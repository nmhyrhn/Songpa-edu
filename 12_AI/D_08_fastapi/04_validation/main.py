from typing import Annotated, Literal
from fastapi import FastAPI, Path, Query
from pydantic import BaseModel, ConfigDict, Field, field_validator

app = FastAPI(title="04. Validation")

class ProductCreate(BaseModel):
    model_config = ConfigDict(extra="forbid") # 없는 필드를 조용히 버리지않고 오류로 처리

    name: str = Field(min_length=2, max_length=50)
    price: float = Field(gt=0, le=1_000_000)
    category: Literal["book", "course", "tool"]
    tags: list[str] = Field(default_factory=list, max_length=5)

    @field_validator("name")
    @classmethod
    def normalize_name(cls, value: str) -> str:
        normalized = value.strip()
        if not normalized:
            raise ValueError("상품명은 공백만 입력할 수 없습니다.")
        if "무료" in normalized:
            raise ValueError("상품명에는 '무료'를 사용할 수 없습니다.")
        return normalized



@app.post("/examples/raw", tags=["comparison"])
async def accept_raw_json(payload: dict) -> dict:
    """dict만 사용하면 내부 필드의 타입, 범위, 필수 값을 자동 검증하지 않습니다."""
    return {"received": payload, "warning":"내부 필드 검증 없음"}

@app.get("/items/{item_id}", tags=["path-query"])
async def read_item(
    item_id: Annotated[int, Path(ge=1, le=9999)],
    keyword: Annotated[
        str | None,
        Query(alias="item-query", min_length=2, max_length=20)
    ] = None
) -> dict:
    return {"item_id": item_id, "keyword": keyword}

@app.get("/search", tags=["path-query"])
async def search(
    q: Annotated[
        str, 
        Query(min_length=2, max_length=30, pattern=r"^[A-Za-z0-9가-힣]+$")
    ]
) -> dict[str, str]:
    """ 검색어에 허용하지 않은 특수문자가 들어오면 422응답으로 거절"""
    return {"q": q} 

@app.post("/products", response_model=ProductCreate, tags=["body"])
async def create_product(product: ProductCreate) -> ProductCreate:
    return product