# routing.py
from django.urls import re_path
from . import consumers,consumers_start  # フルパスでインポート

websocket_urlpatterns = [
    re_path(r'ws/hop/$', consumers.HOPConsumer.as_asgi()),
    re_path(r'ws/hop/start/$', consumers_start.HOPConsumer.as_asgi()),
]
