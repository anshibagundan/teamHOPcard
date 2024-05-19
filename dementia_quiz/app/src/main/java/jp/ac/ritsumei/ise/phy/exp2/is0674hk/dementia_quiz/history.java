package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class history extends AppCompatActivity {
    private DataBaseHelper databaseHelper;
    private UserAdapter userAdapter;
    public static ListView userListView;
    public static ArrayAdapter<String> adapter;
    public FrameLayout popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseHelper = new DataBaseHelper(this);

        // データ取得
        List<User> users = databaseHelper.getAllUsers();

        // リストビューにデータ表示
        userAdapter = new UserAdapter(this, users);
        userListView = findViewById(R.id.userListView);
        userListView.setAdapter(userAdapter);
        popup=findViewById(R.id.popup);
    }

    // ボタンが押されたときにperとdateを削除するメソッド
    public void clearPerAndDate(View view) {
        databaseHelper.clearPerAndDate();
        popup.setVisibility(View.VISIBLE);
    }

    public void history_main(View view){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        popup.setVisibility(View.GONE);
    }



}