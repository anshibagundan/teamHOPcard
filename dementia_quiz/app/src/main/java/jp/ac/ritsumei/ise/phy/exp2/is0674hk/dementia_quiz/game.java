package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class game extends AppCompatActivity {

    private ApiService apiService;
    private TextView act_text;
    private TextView quiz_text;
    private TextView nowgame;
    private String quiz_diff_text;
    private String act_diff_text;
    private WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // ApiServiceインスタンスを取得
        apiService = ApiClient.getApiService();

        act_text = findViewById(R.id.act_text);
        quiz_text = findViewById(R.id.quiz_text);
        nowgame = findViewById(R.id.nowgame);

        act_setText();
        quiz_setText();
        startWebSocket();
    }

    public void act_setText() {
        apiService.getAct_select().enqueue(new Callback<List<Act_select>>() {
            @Override
            public void onResponse(Call<List<Act_select>> call, Response<List<Act_select>> response) {
                int act_diff = 0;
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("act_diff", String.valueOf(response.body().get(0)));
                    act_diff = response.body().get(0).getSelect_diff();
                    switch (act_diff) {
                        case 1:
                            act_diff_text = "簡単";
                            break;
                        case 2:
                            act_diff_text = "普通";
                            break;
                        case 3:
                            act_diff_text = "難しい";
                            break;
                    }
                    act_text.setText(String.valueOf(act_diff_text));
                    Log.d("act_diff", String.valueOf(act_diff));
                } else {
                    Log.e("act_diff", "fail ");
                }
            }

            @Override
            public void onFailure(Call<List<Act_select>> call, Throwable t) {
                Log.e("act_diff", "onFailure ");
            }
        });
    }

    public void quiz_setText() {
        apiService.getQuiz_select().enqueue(new Callback<List<Quiz_select>>() {
            @Override
            public void onResponse(Call<List<Quiz_select>> call, Response<List<Quiz_select>> response) {
                int quiz_diff = 0;
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("quiz_diff", String.valueOf(response.body().get(0)));
                    quiz_diff = response.body().get(0).getSelect_diff();
                    switch (quiz_diff) {
                        case 1:
                            quiz_diff_text = "簡単";
                            break;
                        case 2:
                            quiz_diff_text = "普通";
                            break;
                        case 3:
                            quiz_diff_text = "難しい";
                            break;
                    }
                    quiz_text.setText(String.valueOf(quiz_diff_text));
                    Log.d("quiz_diff", String.valueOf(quiz_diff));
                } else {
                    Log.e("quiz_diff", "fail ");
                }
            }

            @Override
            public void onFailure(Call<List<Quiz_select>> call, Throwable t) {
                Log.e("quiz_diff", "onFailure");
            }
        });
    }

    public void game_result(View view) {
        Intent intent = new Intent(this, result.class);
        startActivity(intent);
    }

    private void startWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://teamhopcard-aa92d1598b3a.herokuapp.com/ws/hop/").build();
        ws = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                Log.d("WebSocket", "Opened");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d("WebSocket", "Message: " + text);
                runOnUiThread(() -> {
                    try {
                        JSONObject json = new JSONObject(text);
                        double x = json.getDouble("x");
                        double y = json.getDouble("y");
                        nowgame.setText("X: " + x + ", Y: " + y);
                    } catch (JSONException e) {
                        Log.e("WebSocket", "JSON parsing error", e);
                    }
                });
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Log.d("WebSocket", "Message: " + bytes.hex());
                // Handle binary message if needed
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                webSocket.close(1000, null);
                Log.d("WebSocket", "Closing: " + code + " / " + reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                Log.e("WebSocket", "Error: " + t.getMessage(), t);
            }
        });

        client.dispatcher().executorService().shutdown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ws != null) {
            ws.close(1000, "Activity destroyed");
        }
    }
}