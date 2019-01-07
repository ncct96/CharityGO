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
import android.widget.ImageView;
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
import org.charitygo.model.StepsRanking;
import org.charitygo.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private DatabaseReference ref = mDatabase.getReference("stepRanking");
    private DatabaseReference usersRef = mDatabase.getReference("users");

    private long timestamp = System.currentTimeMillis();
    private DateFormat df = new DateFormat();
    private int dayDatePath = df.longToYearMonthDay(timestamp);

    private List<StepsRanking> rankList = new ArrayList<>();
    static String[] nameArray = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "JellyBean", "Kitkat", "Lollipop"};
    static String[] versionArray = {"1.5", "1.6", "2.0-2.1", "2.2-2.2.3", "2.3-2.3.7", "3.0-3.2.6", "4.0-4.0.4", "4.1-4.3.1", "4.4-4.4.4", "5.0-5.1.1"};
    static Integer[] imageArray = {R.drawable.silver_medal, R.drawable.bronze_medal, R.drawable.gold_medal, R.drawable.silver_medal, R.drawable.bronze_medal, R.drawable.gold_medal, R.drawable.silver_medal, R.drawable.bronze_medal, R.drawable.gold_medal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        rankList.clear();
        loadLeaderboard();
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
        final int rankSize = 3;

        String month = String.valueOf(df.longToYearMonth(System.currentTimeMillis()));

        final ImageView top1Image = findViewById(R.id.lb_pic_1);
        final TextView top1Name = findViewById(R.id.lb_name_1);
        final TextView top1Steps = findViewById(R.id.lb_points_1);


        ref.child(month).orderByChild("accSteps").limitToLast(4).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot rankSnapShot : dataSnapshot.getChildren()){
                    System.out.println(rankSnapShot.getValue(StepsRanking.class));
                    StepsRanking ranker = rankSnapShot.getValue(StepsRanking.class);
                    System.out.println("TEST" + ranker);
                    rankList.add(ranker);
                }

                Collections.reverse(rankList);

                String first = rankList.get(0).getKey();

                usersRef.child(first).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        top1Name.setText(user.getName());
                        top1Steps.setText(rankList.get(0).getAccSteps());
                        rankList.remove(0);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                LeaderboardAdapter adapter = new LeaderboardAdapter(Leaderboard.this, rankList);
                RecyclerView recList = (RecyclerView) findViewById(R.id.rank_recycler_view);
                recList.setHasFixedSize(true);
                recList.setAdapter(adapter);
                LinearLayoutManager llm = new LinearLayoutManager(Leaderboard.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recList.setLayoutManager(llm);
                recList.setNestedScrollingEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*        for (int i = 0; i < rankSize; i++) {
            System.out.println(rankList.get(i).getName());
        }*/
    }


}
