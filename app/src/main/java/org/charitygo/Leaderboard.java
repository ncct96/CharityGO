package org.charitygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Leaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        String[] userArray = getResources().getStringArray(R.array.user_array);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_leaderboard, userArray);

        ListView listView = (ListView) findViewById(R.id.leaderList);
        listView.setAdapter(adapter);
    }
}
