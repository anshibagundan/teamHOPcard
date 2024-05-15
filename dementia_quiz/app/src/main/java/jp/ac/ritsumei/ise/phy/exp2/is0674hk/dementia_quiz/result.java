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
    private boolean quiz1,quiz2,quiz3;
    private TextView act1_text,act2_text,act3_text;
    private TextView quiz1_text,quiz2_text,quiz3_text;

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
        quiz2_text=findViewById(R.id.quiz2_text);
        quiz3_text=findViewById(R.id.quiz3_text);
        getTF();
    }

//    クイズの正誤を取得
    public void getTF(){
//      actの正誤
        apiService.getAct_tfs().enqueue(new Callback<List<Act_TF>>() {
            @Override
            public void onResponse(Call<List<Act_TF>> call, Response<List<Act_TF>> response) {
                if(response.isSuccessful() && response.body() != null){
                    act1=response.body().get(0).isCor();
                    //Log.e("act1",String.valueOf(act1));
                    act2=response.body().get(1).isCor();
                    Log.e("act2",String.valueOf(act2));
                    act3=response.body().get(2).isCor();
                    Log.e("act3",String.valueOf(act3));
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
                    Log.e("quiz1",String.valueOf(quiz1));
                    quiz2=response.body().get(1).isCor();
                    Log.e("quizmanji",String.valueOf(response.body().get(1)));
                    Log.e("quiz2",String.valueOf(quiz2));
                    quiz3=response.body().get(2).isCor();
                    Log.e("quiz3",String.valueOf(quiz3));

                }
            }

            @Override
            public void onFailure(Call<List<Quiz_TF>> call, Throwable t) {
                Log.e("getTF","connection_error");
            }
        });
        setTF();
    }
//    〇✕テキストをセット
    public void setTF(){
        act1_text.setText(marubatsu(act1));
        Log.e("act1",String.valueOf(act1));
        act2_text.setText(marubatsu(act2));
        act3_text.setText(marubatsu(act3));
        quiz1_text.setText(marubatsu(quiz1));
        quiz2_text.setText(marubatsu(quiz2));
        quiz3_text.setText(marubatsu(quiz3));
    }
//    booleanから〇✕返す
    public String marubatsu(boolean value) {
        Log.e("marubatu",String.valueOf(value));
        if (value) {
            return "〇";
        } else {
            return "✕";
        }
    }


    public void result_main(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}