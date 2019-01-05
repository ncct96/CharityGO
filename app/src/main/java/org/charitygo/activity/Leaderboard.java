package org.charitygo.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.DateFormat;
import org.charitygo.R;
import org.charitygo.adapter.LeaderboardAdapter;
import org.charitygo.model.LeaderInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private DatabaseReference ref = mDatabase.getReference("stepHistory");

    private long timestamp = System.currentTimeMillis();
    private DateFormat df = new DateFormat();
    private int dayDatePath = df.longToYearMonthDay(timestamp);

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

        int lastDay = df.getLastDayofMonth(timestamp);

        ref.startAt(String.valueOf(dayDatePath)).endAt(String.valueOf(lastDay)).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("abcde  " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        for (int i = 0; i < rankSize; i++) {
            LeaderInfo li = new LeaderInfo(imageArray[i], nameArray[i], 1000);
            leaderList.add(li);
        }
    }


}
