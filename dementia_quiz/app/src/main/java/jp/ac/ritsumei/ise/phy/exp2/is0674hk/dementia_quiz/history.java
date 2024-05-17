package jp.ac.ritsumei.ise.phy.exp2.is0674hk.dementia_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;


public class history extends AppCompatActivity {
    private DataBaseHelper databaseHelper;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseHelper = new DataBaseHelper(this);

//        long rowID=databaseHelper.insertUser(user);
        // データ取得
        List<User> users = databaseHelper.getAllUsers();

        // リストビューにデータ表示
        userAdapter = new UserAdapter(this, users);
        ListView userListView = findViewById(R.id.userListView);
        userListView.setAdapter(userAdapter);
    }



}