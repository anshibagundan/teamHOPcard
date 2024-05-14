package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class result extends AppCompatActivity {

    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // ApiServiceインスタンスを取得
        apiService = ApiClient.getApiService();
    }

    public void getTF(){
        apiService.getAct_tfs().enqueue(new Callback<List<Act_TF>>() {
            @Override
            public void onResponse(Call<List<Act_TF>> call, Response<List<Act_TF>> response) {


            }

            @Override
            public void onFailure(Call<List<Act_TF>> call, Throwable t) {

            }
        });

    }


}