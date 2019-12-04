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

        Float[] data = new Float[] {1.0F,2.0F,3.0F,4.0F,5.0F,6.0F,6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 4.0F, 5.0F, 7.0F, 10.0F, 6.0F,1.0F,2.0F,3.0F,4.0F,5.0F,6.0F,6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 4.0F, 5.0F, 7.0F, 10.0F, 6.0F};
        LineChart chart1 = (LineChart) findViewById(R.id.chart1);
        LineChart chart2 = (LineChart) findViewById(R.id.chart2);
        LineChart chart3 = (LineChart) findViewById(R.id.chart3);

//        WE NEED TO QUERY DATA BASED ON BUTTON LISTENERS, DEFAULT WILL BE 1W

        List<Entry> entries = new ArrayList<Entry>();
        Float i = 1F;
        for (Float x: data){
            entries.add(new Entry(i,x));
            i+=1F;
        }

        LineDataSet dataSet = new LineDataSet(entries, "AAPL(USD)");
        dataSet.setLineWidth(2F);
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
//CHART 1
        chart1.setData(lineData);
        chart1.setDrawBorders(false);
        chart1.setDrawGridBackground(false);
//        chart.setAutoScaleMinMaxEnabled(true);
        chart1.getAxisLeft().setDrawGridLines(false);
        chart1.getAxisRight().setDrawGridLines(false);
        chart1.getXAxis().setDrawGridLines(false);

        chart1.getDescription().setEnabled(false);
        chart1.animateY(1000);

        chart1.invalidate();
//CHART 2

        chart2.setData(lineData);
        chart2.setDrawBorders(false);
        chart2.setDrawGridBackground(false);
//        chart.setAutoScaleMinMaxEnabled(true);
        chart2.getAxisLeft().setDrawGridLines(false);
        chart2.getAxisRight().setDrawGridLines(false);
        chart2.getXAxis().setDrawGridLines(false);

        chart2.getDescription().setEnabled(false);
        chart2.animateY(1000);

        chart2.invalidate();

//CHART 3
        chart3.setData(lineData);
        chart3.setDrawBorders(false);
        chart3.setDrawGridBackground(false);
//        chart.setAutoScaleMinMaxEnabled(true);
        chart3.getAxisLeft().setDrawGridLines(false);
        chart3.getAxisRight().setDrawGridLines(false);
        chart3.getXAxis().setDrawGridLines(false);

        chart3.getDescription().setEnabled(false);
        chart3.animateY(1000);

        chart3.invalidate();


    }
}
