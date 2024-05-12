package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity {

    private ApiService apiService;
    private Button easy;
    private Button normal;
    private Button difficult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ApiServiceインスタンスを取得
        apiService = ApiClient.getApiService();

        easy=findViewById(R.id.easy);
        normal=findViewById(R.id.normal);
        difficult=findViewById(R.id.difficult);

    }

    public void setEasy(View view){
        Quiz_select data =new Quiz_select(1,1);
        apiService.insertQuiz_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
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
        Intent intent = new Intent(this,game_easy.class);
        startActivity(intent);
    }

    public void setNormal(View view){
        Quiz_select data=new Quiz_select(1,2);
        apiService.insertQuiz_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
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
        Intent intent = new Intent(this,game_normal.class);
        startActivity(intent);
    }

    public void setDifficult(View view){
        Quiz_select data=new Quiz_select(1,3);
        apiService.insertQuiz_selectData(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
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
        Intent intent = new Intent(this,game_difficult.class);
        startActivity(intent);
    }

//    public void home_game_easy(View view){
//        Intent intent = new Intent(this,game_easy.class);
//        startActivity(intent);
//    }
    public void home_game_normal(View view){
        Intent intent = new Intent(this,game_normal.class);
        startActivity(intent);
    }
    public void home_game_difficult(View view){
        Intent intent = new Intent(this,game_difficult.class);
        startActivity(intent);
    }
}