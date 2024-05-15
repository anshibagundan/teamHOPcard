# routing.py
from django.urls import re_path
from HOPcardAPI_app import consumers  # フルパスでインポート

websocket_urlpatterns = [
    re_path(r'ws/hop/$', consumers.HOPConsumer.as_asgi()),
]
