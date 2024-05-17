using System;
using System.Threading;
using UnityEngine;
using UnityEngine.UI;
using WebSocketSharp;

public class WebSocketManager : MonoBehaviour
{
    WebSocket websocket;
    public Button startButton;

    void Start()
    {
        // ボタンを非表示にする
        startButton.gameObject.SetActive(false);

        // WebSocketの初期化
        websocket = new WebSocket("wss://teamhopcard-aa92d1598b3a.herokuapp.com/ws/hop/start/");
        websocket.OnMessage += OnWebSocketMessage;
        websocket.Connect();
    }

    private void OnWebSocketMessage(object sender, MessageEventArgs e)
    {
        string message = e.Data;
        Debug.Log("Received message: " + message);

        try
        {
            var parsedMessage = JsonUtility.FromJson<Message>(message);
            if (parsedMessage != null && parsedMessage.start == "OK")
            {
                // メインスレッドでボタンを表示する
                SynchronizationContext.Current.Post(_ =>
                {
                    startButton.gameObject.SetActive(true);
                }, null);
            }
        }
        catch (Exception ex)
        {
            Debug.LogError("Error parsing JSON: " + ex.Message);
        }
    }

    void OnDestroy()
    {
        if (websocket != null)
        {
            websocket.Close();
        }
    }

    [Serializable]
    private class Message
    {
        public string start;
    }
}