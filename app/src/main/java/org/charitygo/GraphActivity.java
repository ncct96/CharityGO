package org.charitygo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    private ArrayList<Entry> yValues = new ArrayList<>();
    private ArrayList<ILineDataSet> dataSets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        LineChart chart = (LineChart) findViewById(R.id.chart);

        chart.setOnChartGestureListener(GraphActivity.this);
        chart.setOnChartValueSelectedListener(GraphActivity.this);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);


        yValues.add(new Entry(0, 6000));
        yValues.add(new Entry(1, 5000));
        yValues.add(new Entry(2, 3000));
        yValues.add(new Entry(3, 2000));
        yValues.add(new Entry(4, 1000));
        yValues.add(new Entry(5, 7000));
        yValues.add(new Entry(6, 8000));

        LineDataSet set1 = new LineDataSet(yValues, "Monthly History");

        set1.setFillAlpha(110);
        set1.setLineWidth(7f);
        set1.setValueTextSize(10f);

        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul"};

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyXValueFormatter(month));

        chart.setTouchEnabled(true);
        chart.setData(data);
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

    public class MyXValueFormatter implements IAxisValueFormatter{

        private String[] xValues;

        public MyXValueFormatter(String[] values){
            this.xValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return null;
        }
    }
}
