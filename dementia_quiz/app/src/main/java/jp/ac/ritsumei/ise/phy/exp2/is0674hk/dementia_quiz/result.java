package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class result extends AppCompatActivity {
    private ApiService apiService;
    private boolean act1,act2,act3;
    private boolean quiz1;
    private float act_percent,quiz_percent,percent;
    int act_count,quiz_count;
    private TextView act1_text,act2_text,act3_text;
    private TextView quiz1_text;
    private DataBaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // ApiServiceインスタンスを取得
        apiService = ApiClient.getApiService();
        act1_text=findViewById(R.id.act1_text);
        act2_text=findViewById(R.id.act2_text);
        act3_text=findViewById(R.id.act3_text);
        quiz1_text=findViewById(R.id.quiz1_text);
        databaseHelper = new DataBaseHelper(this);
        getTF();
    }

    //クイズの正誤を取得
    public void getTF(){
//      actの正誤
        apiService.getAct_tfs().enqueue(new Callback<List<Act_TF>>() {
            @Override
            public void onResponse(Call<List<Act_TF>> call, Response<List<Act_TF>> response) {
                if(response.isSuccessful() && response.body() != null){
                    act1=response.body().get(0).isCor();
                    act2=response.body().get(1).isCor();
                    act3=response.body().get(2).isCor();
                    MakeActPercent(act1,act2,act3);
                    setTF_act();
                }
            }
            @Override
            public void onFailure(Call<List<Act_TF>> call, Throwable t) {
                Log.e("getTF","connection_error");
            }
        });

//      quizの正誤
        apiService.getQuiz_tfs().enqueue(new Callback<List<Quiz_TF>>() {
            @Override
            public void onResponse(Call<List<Quiz_TF>> call, Response<List<Quiz_TF>> response) {
                if(response.isSuccessful() && response.body() != null){
                    quiz1=response.body().get(0).isCor();
                    MakeQuizPercent(quiz1);
                    setTF_quiz();
                    percent=(act_percent+quiz_percent)*100;


                    Log.e("act_percent",String.valueOf(act_percent));
                    Log.e("percent",String.valueOf(percent));
                }
            }

            @Override
            public void onFailure(Call<List<Quiz_TF>> call, Throwable t) {
                Log.e("getTF","connection_error");
            }
        });

    }
    //〇✕テキストをセット
    public void setTF_act(){
        act1_text.setText(marubatsu(act1));
        act2_text.setText(marubatsu(act2));
        act3_text.setText(marubatsu(act3));

    }
    public void setTF_quiz() {
        quiz1_text.setText(marubatsu(quiz1));
    }

    //booleanから〇✕返す
    public String marubatsu(boolean value) {
        Log.e("marubatu",String.valueOf(value));
        if (value) {
            return "〇";
        } else {
            return "✕";
        }
    }

    //履歴に残す%の計算
    public void MakeActPercent(boolean act1,boolean act2, boolean act3){
        act_count=0;
        if (act1) {
            act_count+=20;
            Log.e("act_count",String.valueOf(act_count));
        }
        if (act2) {
            act_count+=20;
            Log.e("act_count",String.valueOf(act_count));
        }
        if (act3) {
            act_count+=20;
            Log.e("act_count",String.valueOf(act_count));
        }
        Log.e("act_count",String.valueOf(act_count));
        act_percent=(float)act_count/100;
        Log.e("act_percent",String.valueOf(act_percent));
    }
    public void MakeQuizPercent(boolean quiz1){
        quiz_count=0;
        if(quiz1){
            quiz_count+=40;
        }
        quiz_percent=(float) quiz_count/100;
    }
    //perをPOST＋tfデータ削除＋画面遷移
    public void post_per(View view){
        Log.e("percent",String.valueOf(percent));
        User user =new User(1,percent);
        databaseHelper.insertUser(user);
        apiService.deleteAllQuizTF().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    //ACT-TFの削除リクエストを送信
                    apiService.deleteAllActTF().enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Intent intent = new Intent(result.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                // Act_TFの削除リクエストが失敗した場合のエラーハンドリング
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Act_TFの削除リクエストが失敗した場合のエラーハンドリング
                        }
                    });
                }else{
                    // Quiz_TFの削除リクエストが失敗した場合のエラーハンドリング
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Quiz_TFの削除リクエストが失敗した場合のエラーハンドリング
            }
        });
    }
}