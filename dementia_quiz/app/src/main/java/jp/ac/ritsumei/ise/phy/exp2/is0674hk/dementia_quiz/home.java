package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

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

        easy=findViewById(R.id.easy);
        normal=findViewById(R.id.normal);
        difficult=findViewById(R.id.difficult);
        act_selectDiff=findViewById(R.id.act_selectDiff);
        quiz_selectDiff=findViewById(R.id.quiz_selectDiff);
        easy.setOnClickListener(v -> startWebSocket());
    }
    private void startWebSocket() {
        Request request = new Request.Builder().url("wss://teamhopcard-aa92d1598b3a.herokuapp.com/ws/hop/start/").build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                super.onOpen(webSocket, response);
                webSocket.send("{'start': 'OK'}");
            }
        });


    }
//クイズの難易度をPOST＋画面遷移
    public void set_quizEasy(View view){
        Quiz_select data =new Quiz_select(1,1);
        apiService.insertQuiz_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("setEasy","success");
                }else{
                    Log.e("setEasy","fail");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("setEasy","connection_fail");
            }
        });
        Intent intent = new Intent(this, game.class);
        startActivity(intent);
    }
    public void set_quizNormal(View view){
        Quiz_select data=new Quiz_select(1,2);
        apiService.insertQuiz_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()){
                    Log.d("setNormal","success");
                }else {
                    Log.e("setNormal","fail");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("setNormal","connection_fail");
            }
        });
        Intent intent = new Intent(this,game.class);
        startActivity(intent);
    }
    public void set_quizDifficult(View view){
        Quiz_select data=new Quiz_select(1,3);
        apiService.insertQuiz_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()){
                    Log.d("setDifficult","success");
                }else {
                    Log.e("setDifficult","fail");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("setDifficult","connection_fail");
            }
        });
        Intent intent = new Intent(this,game.class);
        startActivity(intent);
    }

//アクションの難易度をPOST＋画面遷移
    public void set_actEasy(View view){
        Act_select data =new Act_select(1,1);
        apiService.insertAct_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("set_actEasy","success");
                }else{
                    Log.e("set_actEasy","fail");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("set_actEasy","connection_fail");
            }
        });
        act_selectDiff.setVisibility(View.GONE);
        quiz_selectDiff.setVisibility(View.VISIBLE);
    }
    public void set_actNormal(View view){
        Act_select data =new Act_select(1,2);
        apiService.insertAct_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("set_actNormal","success");
                }else{
                    Log.e("set_actNormal","fail");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("set_actNormal","connection_fail");
            }
        });
        act_selectDiff.setVisibility(View.GONE);
        quiz_selectDiff.setVisibility(View.VISIBLE);
    }
    public void set_actDifficult(View view) {
        Act_select data = new Act_select(1, 3);
        apiService.insertAct_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("set_actDifficult", "success");
                } else {
                    Log.e("set_actDifficult", "fail");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("set_actDifficult", "connection_fail");
            }
        });
        act_selectDiff.setVisibility(View.GONE);
        quiz_selectDiff.setVisibility(View.VISIBLE);
    }
}




