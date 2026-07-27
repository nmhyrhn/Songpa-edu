import json
from pathlib import Path
from typing import Annotated
from uuid import UUID, uuid4

from fastapi import FastAPI, File, Form, HTTPException, Response, UploadFile
from pydantic import BaseModel

app = FastAPI(title="File Service")

@app.get("/health", tags=["system"])
def health() -> dict[str, str]:
  return {"status": "ok", "service": "fastapi-file-service"}