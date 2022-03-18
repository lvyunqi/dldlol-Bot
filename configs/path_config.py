from pathlib import Path
import os

# 图片路径
IMAGE_PATH = Path() / "src" / "pic"



def load_path():
    old_img_dir = Path() / "src" / "img"
    if not IMAGE_PATH.exists() and old_img_dir.exists():
        os.rename(old_img_dir, IMAGE_PATH)

    IMAGE_PATH.mkdir(parents=True, exist_ok=True)


load_path()