package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public void main_home(View view){
    //ここで前データ削除用メソッド呼び出し
    apiService.deleteAllQuizSelect();
    apiService.deleteAllActSelect();
    Intent intent = new Intent(this,home.class);
    startActivity(intent);
}


}