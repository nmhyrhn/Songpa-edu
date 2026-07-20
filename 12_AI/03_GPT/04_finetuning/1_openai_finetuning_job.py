from openai import OpenAI
from dotenv import load_dotenv
from pathlib import Path

load_dotenv()

client = OpenAI()

BASE_DIR = Path(__file__).resolve().parent
print(BASE_DIR)
dataset = BASE_DIR / "fine_tuning_dataset_example.jsonl"

with dataset.open("rb") as f:
  uploaded = client.files.create(
    file=f,
    purpose="fine-tune" 
  )

print("=== 업로드 완료 ===")
print("File ID: ", uploaded.id)

try:
  job = client.fine_tuning.jobs.create(
    training_file=uploaded.id,
    model="gpt-4.1-mini"
  )
  print("job.id: ", job.id)

except Exception as e:
  print(e)