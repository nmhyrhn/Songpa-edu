from fastapi import FastAPI, HTTPException, Response, status
from pydantic import BaseModel, Field

app = FastAPI(title="05. Response and Errors")

users: dict[int, dict] = {
    1: {
        "id": 1,
        "username": "teacher",
        "password": "internal-password",
        "display_name": "강사"
    }
}

class UserResponse(BaseModel):
    id: int
    username: str
    display_name: str

def find_user_or_404(user_id: int) -> dict:
    user = users.get(user_id)
    if user is None:
        raise HTTPException(status_code=404, detail="사용자를 찾을 수 없습니다.")
    return user
# resonse_model을 넣지 않으면 password가 노출된다.
@app.get("/demo/unsafe-user", response_model=UserResponse , tags=["comparison"])
async def unsafe_user() -> dict:
    return users[1]

@app.get("/users", response_model=list[UserResponse], tags=["users"])
async def list_users(response: Response) -> list[dict]:
    response.headers["X-Total-Count"] = str(len(users))
    return list(users.values())

@app.get("/users/{user_id}", response_model=UserResponse, tags=["users"])
async def get_user(user_id:int) -> dict:
    return find_user_or_404(user_id)
