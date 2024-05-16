import json
import asyncio
from channels.generic.websocket import AsyncWebsocketConsumer

class HOPConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        await self.channel_layer.group_add("HOP_group", self.channel_name)
        await self.accept()
        print(f"[DEBUG] WebSocket connection accepted: {self.channel_name}")
        
        # Start the loop to send x, y, z data
        self.send_task = asyncio.create_task(self.send_coordinates())

    async def disconnect(self, close_code):
        # Cancel the sending loop task
        self.send_task.cancel()
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

    async def send_coordinates(self):
        while True:
            # Example coordinates, replace with your actual logic to get coordinates
            x = 1
            y = 2
            z = 3

            response_data = {
                'x': x,
                'y': y,
                'z': z,
            }
            await self.send(text_data=json.dumps(response_data))
            print(f"[DEBUG] Sent coordinates to client: {response_data}")

            # Wait for a second before sending the next set of coordinates
            await asyncio.sleep(1)
