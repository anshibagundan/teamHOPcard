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
        easy.setOnClickListener(v -> startWebSocket(1));
        normal.setOnClickListener(v -> startWebSocket(2));
        difficult.setOnClickListener(v -> startWebSocket(3));
    }

    // quiz難易度選択すると画面遷移＋json送信
    private void startWebSocket(int difficulty) {
        private void startWebSocket(int difficulty) {
            Request request = new Request.Builder().url("wss://teamhopcard-aa92d1598b3a.herokuapp.com/ws/hop/start/").build();
            webSocket = client.newWebSocket(request, new WebSocketListener() {
        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            super.onOpen(webSocket, response);
            // 接続が確立された後、少し遅延を入れてからデータを送信する
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("start", "OK");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    webSocket.send(jsonObject.toString());
                }
            }, 1000); // 1秒の遅延を入れる（適宜調整してください）
        }
    });

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
