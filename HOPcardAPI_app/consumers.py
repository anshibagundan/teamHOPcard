import json
from channels.generic.websocket import AsyncWebsocketConsumer

class HOPConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        await self.channel_layer.group_add("HOP_group", self.channel_name)
        await self.accept()
        print(f"[DEBUG] WebSocket connection accepted: {self.channel_name}")

        # 接続時にメッセージを送信
        await self.send(text_data=json.dumps({"message": "Welcome!"}))
        print("[DEBUG] Sent welcome message to client")

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard("HOP_group", self.channel_name)
        print(f"[DEBUG] WebSocket connection closed: {self.channel_name} with code {close_code}")

    async def receive(self, text_data):
        print(f"[DEBUG] Received raw data: {text_data}")

        if not text_data.strip():
            print("[DEBUG] Received empty message")
            return

        try:
            data = json.loads(text_data)
            print(f"[DEBUG] Received JSON data: {data}")
        except json.JSONDecodeError as e:
            print(f"[DEBUG] JSONDecodeError: {e}")
            return

        x = data.get('x')
        y = data.get('y')
        z = data.get('z')
        print(f"[DEBUG] Received coordinates via WebSocket: x={x}, y={y}, z={z}")

        response_data = {
            'x': x,
            'y': y,
            'z': z,
        }
        await self.send(text_data=json.dumps(response_data))
        print(f"[DEBUG] Sent coordinates back to client: {response_data}")
