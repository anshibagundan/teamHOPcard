# asgi.py
import os
from django.core.asgi import get_asgi_application
from channels.routing import ProtocolTypeRouter, URLRouter
from channels.auth import AuthMiddlewareStack
from HOPcardAPI_app.routing import websocket_urlpatterns  # フルパスでインポート

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'HOPcardAPI.settings')

application = ProtocolTypeRouter({
    'http': get_asgi_application(),
    'websocket': AuthMiddlewareStack(
        URLRouter(
            websocket_urlpatterns
        )
    ),
})
