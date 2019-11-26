package com.example.bread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class stonks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stonks);

        Float[] data = new Float[] {1.0F,2.0F,3.0F,4.0F,5.0F,6.0F};
        LineChart chart = (LineChart) findViewById(R.id.chart);
//        WE NEED TO QUERY DATA BASED ON BUTTON LISTENERS, DEFAULT WILL BE 1W

        List<Entry> entries = new ArrayList<Entry>();
        Float i = 1F;
        for (Float x: data){
            entries.add(new Entry(x, i));
            i+=1F;
        }

        LineDataSet dataSet = new LineDataSet(entries, "PRICE(USD)");
        dataSet.setLineWidth(2F);
        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.setDrawBorders(false);
        chart.setDrawGridBackground(false);
//        chart.setAutoScaleMinMaxEnabled(true);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        chart.getDescription().setEnabled(false);
        chart.animateY(1000);

        chart.invalidate();
    }
}
