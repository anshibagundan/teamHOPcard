package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button start_button;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = ApiClient.getApiService();
        start_button = findViewById(R.id.start);
    }

    public void main_home(View view) {
        // Quiz_selectの削除リクエストを送信
        apiService.deleteAllQuizSelect().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Act_selectの削除リクエストを送信
                    apiService.deleteAllActSelect().enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(MainActivity.this, home.class);
                                startActivity(intent);
                            } else {
                                // Act_selectの削除リクエストが失敗した場合のエラーハンドリング
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                            // Act_selectの削除リクエストが失敗した場合のエラーハンドリング
                        }
                    });
                } else {
                    // Quiz_selectの削除リクエストが失敗した場合のエラーハンドリング
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                // Quiz_selectの削除リクエストが失敗した場合のエラーハンドリング
            }
        });
    }


}