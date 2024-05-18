package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import retrofit2.Call;
import retrofit2.Callback;

public class home extends AppCompatActivity {

    private ApiService apiService;
    private Button easy;
    private Button normal;
    private Button difficult;
    private FrameLayout act_selectDiff;
    private FrameLayout quiz_selectDiff;
    private WebSocket webSocket;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ApiServiceインスタンスを取得
        apiService = ApiClient.getApiService();

        easy = findViewById(R.id.easy);
        normal = findViewById(R.id.normal);
        difficult = findViewById(R.id.difficult);
        act_selectDiff = findViewById(R.id.act_selectDiff);
        quiz_selectDiff = findViewById(R.id.quiz_selectDiff);

        // WebSocket接続を確立
        startWebSocket();

        easy.setOnClickListener(v -> sendDataAndCloseWebSocket(1));
        normal.setOnClickListener(v -> sendDataAndCloseWebSocket(2));
        difficult.setOnClickListener(v -> sendDataAndCloseWebSocket(3));
    }

    // WebSocket接続を確立
    private void startWebSocket() {
        Request request = new Request.Builder().url("wss://teamhopcard-aa92d1598b3a.herokuapp.com/ws/hop/start/").build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                super.onOpen(webSocket, response);
                Log.d("WebSocket", "Connected");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                super.onFailure(webSocket, t, response);
                Log.e("WebSocket", "Connection failed: " + t.getMessage());
            }
        });
    }

    // データを送信してWebSocket接続を閉じる
    private void sendDataAndCloseWebSocket(int difficulty) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("start", "OK");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        webSocket.send(jsonObject.toString());
        webSocket.close(1000, null);

        Quiz_select data = new Quiz_select(1, difficulty);
        apiService.insertQuiz_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("setDifficulty", "success");
                } else {
                    Log.e("setDifficulty", "fail");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("setDifficulty", "connection_fail");
            }
        });

        Intent intent = new Intent(this, game.class);
        startActivity(intent);
    }

    // アクションの難易度をPOST＋画面遷移
    public void set_actDifficulty(View view, int difficulty) {
        Act_select data = new Act_select(1, difficulty);
        apiService.insertAct_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("set_actDifficulty", "success");
                } else {
                    Log.e("set_actDifficulty", "fail");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("set_actDifficulty", "connection_fail");
            }
        });
        act_selectDiff.setVisibility(View.GONE);
        quiz_selectDiff.setVisibility(View.VISIBLE);
    }

    public void set_actEasy(View view) {
        set_actDifficulty(view, 1);
    }

    public void set_actNormal(View view) {
        set_actDifficulty(view, 2);
    }

    public void set_actDifficult(View view) {
        set_actDifficulty(view, 3);
    }
}
