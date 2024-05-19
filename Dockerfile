# ベースイメージとしてPythonを使用
FROM python:3.9

# 環境変数を設定
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

# ワークディレクトリを設定
WORKDIR /app

# Pythonの依存関係をインストール
COPY requirements.txt /app/
RUN pip install --no-cache-dir -r requirements.txt

# Djangoプロジェクトをコンテナにコピー
COPY . /app/

# ポートを公開
EXPOSE 8000

# Djangoアプリケーションを実行
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]