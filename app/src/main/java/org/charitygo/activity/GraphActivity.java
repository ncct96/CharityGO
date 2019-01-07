package org.charitygo.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.DateFormat;
import org.charitygo.R;
import org.charitygo.StepService;
import org.charitygo.model.StepHistory;
import org.charitygo.model.StepsRanking;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    private ArrayList<Entry> yValues = new ArrayList<>();
    private ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private DatabaseReference ref = database.getReference("stepHistory");
    private static long timestamp = System.currentTimeMillis();
    private DateFormat df = new DateFormat();
    private String dayDatePath = String.valueOf(df.longToYearMonthDay(timestamp));
    private List<StepHistory> stepsList = new ArrayList<>();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph);

        final LineChart chart = (LineChart) findViewById(R.id.chart);
        TextView lblMonth = findViewById(R.id.lblMonth);


        chart.setOnChartGestureListener(GraphActivity.this);
        chart.setOnChartValueSelectedListener(GraphActivity.this);
        chart.setDragEnabled(true);
        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        lblMonth.setText(df.getMonthString(timestamp));
        chart.getDescription().setText("");

        LimitLine upper_limit = new LimitLine(10000f, "Healthy");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(15f);

        LimitLine lower_limit = new LimitLine(3000f, "Unhealthy");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        lower_limit.setTextSize(15f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upper_limit);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(20000f);
        leftAxis.enableGridDashedLine(10f, 10f, 0);
        leftAxis.setDrawLimitLinesBehindData(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        String firstday = String.valueOf(df.getFirstDayofMonth(timestamp));
        String lastday = String.valueOf(df.getLastDayofMonth(timestamp));

        Log.e("1", firstday);
        Log.e("1", lastday);

        ref.orderByKey().startAt(firstday).endAt(lastday).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot stepSnapShot : dataSnapshot.getChildren()) {
                    StepHistory steps = stepSnapShot.child(currentUser.getUid()).getValue(StepHistory.class);
                    if (steps != null) {
                        stepsList.add(steps);
                        count++;
                        System.out.println("OLO " + steps.getSteps());
                    }
                }

                System.out.println("dd" + count);

                String[] month = new String[stepsList.size()];

                for (int i = 0; i < stepsList.size(); i++) {
                    System.out.println("LOL" + stepsList.get(i).getSteps());
                    yValues.add(new Entry(i, stepsList.get(i).getSteps()));
                    int date = Integer.parseInt(stepsList.get(i).getKey());
                    int day = date % 100;
                    if (day < 10) {
                        day = day % 10;
                    }
                    month[i] = String.valueOf(day);
                }

                LineDataSet set1 = new LineDataSet(yValues, "Daily Steps");

                set1.setFillAlpha(110);
                set1.setLineWidth(7f);
                set1.setValueTextSize(10f);

                dataSets.add(set1);

                LineData data = new LineData(dataSets);

                chart.setData(data);
                chart.invalidate();

                XAxis xAxis = chart.getXAxis();
                xAxis.setValueFormatter(new MyXValueFormatter(month));
                xAxis.setGranularity(0f);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public class MyXValueFormatter implements IAxisValueFormatter {

        private String[] xValues;

        public MyXValueFormatter(String[] values) {
            this.xValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if (value >= 0) {
                if (xValues.length > (int) value) {
                    return xValues[(int) value];
                } else {
                    axis.setGranularityEnabled(false);
                    return "";
                }
            } else {
                return "";
            }
        }
    }
}
