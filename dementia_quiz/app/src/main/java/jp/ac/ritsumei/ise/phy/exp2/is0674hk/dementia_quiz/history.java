package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
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

    }
}