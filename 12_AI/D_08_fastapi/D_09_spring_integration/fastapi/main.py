import json
from pathlib import Path
from typing import Annotated
from uuid import UUID, uuid4

from fastapi import FastAPI, File, Form, HTTPException, Response, UploadFile
from pydantic import BaseModel
"""
uvicorn D_08_fastapi.D_09_spring_integration.fastapi.main:app --reload --port 8002
"""

BASE_DIR = Path(__file__).resolve().parent
UPLOAD_DIR = BASE_DIR / "uploads"
UPLOAD_DIR.mkdir(exist_ok=True)
MAX_FILE_SIZE = 1024 * 1024
ALLOWED_TYPES = {"text/plain": ".txt", "application/pdf": ".pdf"}
app = FastAPI(title="File Service")

class FileInfo(BaseModel):
    id: str
    original_name: str
    content_type: str
    size: int
    description: str
    stored_name: str

def metadata_path(file_id: str) -> Path:
    try:
        safe_id = str(UUID(file_id))
    except ValueError as e:
        raise HTTPException(status_code=404, detail="파일을 찾을 수 없습니다.") from e
    return UPLOAD_DIR / f"{safe_id}.json"

@app.get("/health", tags=["system"])
def health() -> dict[str, str]:
    return {"status": "ok", "service": "fastapi-file-service"}

@app.post("/api/v1/files", response_model=FileInfo, status_code=201, tags=["files"])
async def upload_file(
    file: Annotated[UploadFile, File()],
    description: Annotated[str, Form(min_length=1, max_length=100)]
) -> FileInfo:
    content_type = file.content_type or "application/octet-stream"
    suffix = ALLOWED_TYPES.get(content_type)
    original_name = Path(file.filename or "").name
    if suffix is None or Path(original_name).suffix.lower() != suffix:
        raise HTTPException(status_code=415, detail="확장자와 형식이 일치하는 txt/pdf만 허용합니다.")

    content = await file.read(MAX_FILE_SIZE + 1)
    if len(content) > MAX_FILE_SIZE:
        raise HTTPException(status_code=413, detail="파일은 1MB 이하여야 합니다.")

    file_id = str(uuid4())
    stored_name = f"{file_id}{suffix}"

    (UPLOAD_DIR / stored_name).write_bytes(content)
    info = FileInfo(
        id=file_id,
        original_name=original_name,
        content_type=content_type,
        size=len(content),
        description=description,
        stored_name=stored_name
    )

    metadata_path(file_id).write_text(
        json.dumps(info.model_dump(), ensure_ascii=False, indent=2), encoding="utf-8"
    )

    return info