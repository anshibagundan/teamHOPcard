# consumers.py
import json
from channels.generic.websocket import AsyncWebsocketConsumer

class HOPConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        await self.channel_layer.group_add("HOP_group", self.channel_name)
        await self.accept()

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard("HOP_group", self.channel_name)

    async def receive(self, text_data):
        data = json.loads(text_data)
        x = data.get('x')
        y = data.get('y')
        z = data.get('z')
        print(f"Received coordinates via WebSocket: x={x}, y={y}, z={z}")

        # クライアント（例えばAndroidデバイス）にデータを送信
        await self.send(text_data=json.dumps({
            'x': x,
            'y': y,
            'z': z,
        }))