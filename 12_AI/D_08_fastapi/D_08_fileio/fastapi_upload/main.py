from typing import Annotated
import json
from fastapi import FastAPI, File, Form, HTTPException, UploadFile
from pathlib import Path
from pydantic import BaseModel
from uuid import UUID, uuid4

app = FastAPI(title="Form and UploadFile")
BASE_DIR = Path(__file__).resolve().parent
UPLOAD_DIR = BASE_DIR / "uploads"
UPLOAD_DIR.mkdir(exist_ok=True)
MAX_FILE_SIZE = 1024 * 1024
ALLOWD_CONTENT_TYPES = {"text/plain", "application/pdf"}
ALLOWD_CONTENT_TYPES_ = {"text/plain": ".txt", "application/pdf":".pdf"}

class LoginResponse(BaseModel):
    username: str
    authenticated: bool

class UploadResponse(BaseModel):
    filename: str
    content_type: str
    size: int
    description: str

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


@app.post("/login", response_model=LoginResponse, tags=["form"])
async def login(
    username: Annotated[str, Form(min_length=2)],
    password: Annotated[str, Form(min_length=8)]
) -> LoginResponse:
    return LoginResponse(username=username, authenticated=bool(password))

@app.post("/files", response_model=UploadResponse, tags=["mutipart"])
async def inspect_upload(
    file: Annotated[UploadFile, File()],
    description: Annotated[str, Form(min_length=1, max_length=100)]
) -> UploadResponse:
    content_type = file.content_type or "application/octect-stream"
    if content_type not in ALLOWD_CONTENT_TYPES:
        raise HTTPException(status_code=415, detail="txt 또는 pdf만 허용합니다.")

    content = await file.read(MAX_FILE_SIZE + 1)
    if len(content) > MAX_FILE_SIZE:
        raise HTTPException(status_code=413, detail="파일은 1MB 이하여야 합니다.")

    return UploadResponse(
        filename=file.filename or "unknown",
        content_type=content_type,
        size=len(content),
        description=description
    )


@app.post("/api/v1/files", response_model=FileInfo, status_code=201)
async def upload_file(
    file: Annotated[UploadFile, File()],
    description: Annotated[str, Form(min_length=1, max_length=100)]
) -> FileInfo:
    content_type = file.content_type or "application/octet-stream"
    suffix = ALLOWD_CONTENT_TYPES_.get(content_type)
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