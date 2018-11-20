package org.charitygo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Leaderboard extends AppCompatActivity {
    private RecyclerView rankRecyclerView;
    private RecyclerView.LayoutManager rankLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_user_list);
        getData();

    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
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
    }

    public void getData() {
        String[] userList = getResources().getStringArray(R.array.user_array);
        String[] userSteps = getResources().getStringArray(R.array.steps_array);
        int rankSize = 10;
        TableLayout tl = findViewById(R.id.rankTable);

        for (int i = 0; i < rankSize; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, "" + (i+1) , Color.RED, Typeface.NORMAL, Color.BLUE));
            tr.addView(getTextView(i + 1, userList[i], Color.WHITE, Typeface.NORMAL, Color.BLUE));
            tr.addView(getTextView(i + 1, userSteps[i], Color.WHITE, Typeface.BOLD, Color.BLUE));
            tl.addView(tr, getLayoutParams());
        }
    }


}
