package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class WebSocketClient extends WebSocketListener {
    private static final String TAG = "WebSocketClient";

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private WebSocket webSocket;

    private CustomCircleView customCircleView;



    public void start() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://teamhopcard-aa92d1598b3a.herokuapp.com/ws/hop/").build();
        webSocket = client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }
    public void startGame() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://teamhopcard-aa92d1598b3a.herokuapp.com/ws/hop/start/").build();
        webSocket = client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }


    public WebSocketClient(CustomCircleView customCircleView) {
        this.customCircleView = customCircleView;
    }

    @Override
    public void onOpen(WebSocket webSocket, okhttp3.Response response) {
        Log.d(TAG, "WebSocket Connection Opened");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG, "Received message: " + text);
        // JSONをパースして内容を確認する
        try {
            JSONObject json = new JSONObject(text);
            float x = (float) (((float)json.getDouble("x")+872)*0.145+265);
            float z = (float) (((float)json.getDouble("z")+966)*(-0.165)+1210);
            Log.d(TAG,  "x = " + x + " z = " + z);
            mainHandler.post(() -> {
                customCircleView.setCirclePosition(x, z);
            }); //y座標はいらんっしょ
        } catch (JSONException e) {
            Log.e(TAG, "JSON parsing error: " + e.getMessage());
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.d(TAG, "Received bytes message: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        Log.d(TAG, "WebSocket Connection Closing: " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
        Log.e(TAG, "WebSocket Connection Failed: " + t.getMessage());
    }
}