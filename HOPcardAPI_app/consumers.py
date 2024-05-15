import json
import logging
from channels.generic.websocket import AsyncWebsocketConsumer

# ログ設定
logger = logging.getLogger(__name__)
logging.basicConfig(level=logging.DEBUG)

class HOPConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        await self.channel_layer.group_add("HOP_group", self.channel_name)
        await self.accept()
        logger.debug(f"WebSocket connection accepted: {self.channel_name}")

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard("HOP_group", self.channel_name)
        logger.debug(f"WebSocket connection closed: {self.channel_name} with code {close_code}")

    async def receive(self, text_data):
        data = json.loads(text_data)
        x = data.get('x')
        y = data.get('y')
        z = data.get('z')
        logger.debug(f"Received coordinates via WebSocket: x={x}, y={y}, z={z}")

        # クライアント（例えばAndroidデバイス）にデータを送信
        await self.send(text_data=json.dumps({
            'x': x,
            'y': y,
            'z': z,
        }))
        logger.debug(f"Sent coordinates back to client: x={x}, y={y}, z={z}")
