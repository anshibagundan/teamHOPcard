import json
from channels.generic.websocket import AsyncWebsocketConsumer

class HOPConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        await self.channel_layer.group_add("HOP_group", self.channel_name)
        await self.accept()
        print(f"WebSocket connection accepted: {self.channel_name}")

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard("HOP_group", self.channel_name)
        print(f"WebSocket connection closed: {self.channel_name} with code {close_code}")

    async def receive(self, text_data):
        if not text_data.strip():
            print("Received empty message")
            return

        try:
            data = json.loads(text_data)
            print(f"Received JSON data: {data}")
        except json.JSONDecodeError as e:
            print(f"JSONDecodeError: {e}")
            return

        x = data.get('x')
        y = data.get('y')
        z = data.get('z')
        print(f"Received coordinates via WebSocket: x={x}, y={y}, z={z}")

        # クライアント（例えばAndroidデバイス）にデータを送信
        response_data = {
            'x': x,
            'y': y,
            'z': z,
        }
        await self.send(text_data=json.dumps(response_data))
        print(f"Sent coordinates back to client: {response_data}")
