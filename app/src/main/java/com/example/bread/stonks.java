package com.example.bread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class stonks extends AppCompatActivity {
    static SQLiteDatabase db;
    static final String GET_STOCK_DATA = "SELECT STOCK_NAME, STOCK_VALUE FROM STOCKS";
    static final String ACTIVITY_NAME = "STOCKS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stonks);

        Database dbHelper = new Database(this);
        db = dbHelper.getWritableDatabase();
//        generateData();
        final Cursor cursor = db.rawQuery(GET_STOCK_DATA, null);
        cursor.moveToFirst();
        ArrayList<Float> data1 = new ArrayList<Float>();
        ArrayList<Float> data2 = new ArrayList<Float>();
        ArrayList<Float> data3 = new ArrayList<Float>();


        while(!cursor.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL NAME:" + cursor.getString(cursor.getColumnIndex(Database.STOCK_NAME)));
            Log.i(ACTIVITY_NAME, "SQL VALUE:" + cursor.getFloat(cursor.getColumnIndex(Database.STOCK_VALUE)));
            if(cursor.getString(cursor.getColumnIndex(Database.STOCK_NAME)).equals("0")){
                data1.add(cursor.getFloat(cursor.getColumnIndex(Database.STOCK_VALUE)));
            }else if(cursor.getString(cursor.getColumnIndex(Database.STOCK_NAME)).equals("1")){
                data2.add(cursor.getFloat(cursor.getColumnIndex(Database.STOCK_VALUE)));
            }else if(cursor.getString(cursor.getColumnIndex(Database.STOCK_NAME)).equals("2")){
                data3.add(cursor.getFloat(cursor.getColumnIndex(Database.STOCK_VALUE)));

            }

            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "READING ARRAYLISTS:");
        Log.i(ACTIVITY_NAME, data1.toString());
        Log.i(ACTIVITY_NAME, data2.toString());
        Log.i(ACTIVITY_NAME, data3.toString());


//        Float[] data = new Float[] {1.0F,2.0F,3.0F,4.0F,5.0F,6.0F,6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 4.0F, 5.0F, 7.0F, 10.0F, 6.0F,1.0F,2.0F,3.0F,4.0F,5.0F,6.0F,6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 4.0F, 5.0F, 7.0F, 10.0F, 6.0F};
        LineChart chart1 = (LineChart) findViewById(R.id.chart1);
        LineChart chart2 = (LineChart) findViewById(R.id.chart2);
        LineChart chart3 = (LineChart) findViewById(R.id.chart3);

//        WE NEED TO QUERY DATA BASED ON BUTTON LISTENERS, DEFAULT WILL BE 1W

        List<Entry> entries1 = new ArrayList<Entry>();
        List<Entry> entries2 = new ArrayList<Entry>();
        List<Entry> entries3 = new ArrayList<Entry>();


//        FIRST GRAPH
        Float i = 1F;
        for (Float x: data1){
            entries1.add(new Entry(i,x));
            i+=1F;
        }
//       SECOND GRAPH
        i = 1F;
        for (Float x: data2){
            entries2.add(new Entry(i,x));
            i+=1F;
        }
//       THIRD GRAPH
        i = 1F;
        for (Float x: data3){
            entries3.add(new Entry(i,x));
            i+=1F;
        }
        Log.i(ACTIVITY_NAME, "START OF ENTRIES:");
        Log.i(ACTIVITY_NAME, entries1.toString());
        Log.i(ACTIVITY_NAME, entries2.toString());
        Log.i(ACTIVITY_NAME, entries3.toString());

//CHART 1
        LineDataSet dataSet = new LineDataSet(entries1, "AAPL(USD)");
        dataSet.setLineWidth(2F);
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
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
        LineDataSet dataSet2 = new LineDataSet(entries2, "TSLA(USD)");
        dataSet2.setLineWidth(2F);
        dataSet2.setDrawValues(false);
        LineData lineData2 = new LineData(dataSet2);
        chart2.setData(lineData2);
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
        LineDataSet dataSet3 = new LineDataSet(entries3, "AMZN(USD)");
        dataSet3.setLineWidth(2F);
        dataSet3.setDrawValues(false);
        LineData lineData3 = new LineData(dataSet3);
        chart3.setData(lineData3);
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
    public void generateData(){
        Random rd = new Random();
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                ContentValues xValues = new ContentValues();
                Float newValue = rd.nextFloat() * 100.0F * rd.nextFloat();
                xValues.put(Database.STOCK_NAME, Integer.toString(i));
                xValues.put(Database.STOCK_VALUE, newValue);
                db.insert(Database.STOCKS, "NullPlaceHolder", xValues);

            }
        }
    }

    public void generateGraph(ArrayList<Float> data1, LineChart chart1){
        List<Entry> entries1 = new ArrayList<Entry>();

        Float i = 1F;
        for (Float x: data1){
            entries1.add(new Entry(i,x));
            i+=1F;
        }
        LineDataSet dataSet = new LineDataSet(entries1, "AAPL(USD)");
        dataSet.setLineWidth(2F);
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
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
    }
}
