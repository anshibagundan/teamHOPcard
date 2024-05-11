package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void home_game_easy(View view){
        Intent intent = new Intent(this,game_easy.class);
        startActivity(intent);
    }
    public void home_game_normal(View view){
        Intent intent = new Intent(this,game_normal.class);
        startActivity(intent);
    }
    public void home_game_difficult(View view){
        Intent intent = new Intent(this,game_difficult.class);
        startActivity(intent);
    }
}