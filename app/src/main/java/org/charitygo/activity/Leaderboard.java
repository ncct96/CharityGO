package org.charitygo.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.adapter.LeaderboardAdapter;
import org.charitygo.model.LeaderInfo;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {
    private List<LeaderInfo> leaderList = new ArrayList<>();
    static String[] nameArray = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "JellyBean", "Kitkat", "Lollipop"};
    static String[] versionArray = {"1.5", "1.6", "2.0-2.1", "2.2-2.2.3", "2.3-2.3.7", "3.0-3.2.6", "4.0-4.0.4", "4.1-4.3.1", "4.4-4.4.4", "5.0-5.1.1"};
    static Integer[] imageArray = {R.drawable.gold_medal, R.drawable.silver_medal, R.drawable.bronze_medal, R.drawable.gold_medal, R.drawable.silver_medal, R.drawable.bronze_medal, R.drawable.gold_medal, R.drawable.silver_medal, R.drawable.bronze_medal, R.drawable.gold_medal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLeaderboard();
        LeaderboardAdapter adapter = new LeaderboardAdapter(leaderList);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        RecyclerView recList = (RecyclerView) findViewById(R.id.rank_recycler_view);
        recList.setHasFixedSize(true);
        recList.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setNestedScrollingEnabled(false);
        //getData();

    }

/*    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }


    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }*/

    public void loadLeaderboard() {
        int rankSize = 10;

        for (int i = 0; i < rankSize; i++) {
            LeaderInfo li = new LeaderInfo(imageArray[i], nameArray[i], 1000);
            leaderList.add(li);
        }
    }


}
