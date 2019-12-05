package com.example.bread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import android.widget.Adapter;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *This activity creates three graphs that shows the stocks for popular stocks on NASDAQ.
 *The data for the stocks gets generated randomly through our generateData function.
 *The data is saved to Database which holds Stock_Id and stock_value.
 * @author Noah Nichols
 * @version 2019.12
 *
 */

public class stonks extends AppCompatActivity {
    static SQLiteDatabase db;
    static final String GET_STOCK_DATA = "SELECT STOCK_NAME, STOCK_VALUE FROM STOCKS";
    static final String ACTIVITY_NAME = "STOCKS";
    protected class dataQuery extends AsyncTask<String, Integer, ArrayList<Float>> {

        @Override
        protected ArrayList<Float> doInBackground(String... strings) {
            Log.i(ACTIVITY_NAME, "Started async");
            String graphNum = strings[0];
            ArrayList<Float> newGraph = new ArrayList<Float>();
            final Cursor cursor = db.rawQuery(GET_STOCK_DATA, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "SQL NAME:" + cursor.getString(cursor.getColumnIndex(Database.STOCK_NAME)));
                Log.i(ACTIVITY_NAME, "SQL VALUE:" + cursor.getFloat(cursor.getColumnIndex(Database.STOCK_VALUE)));
                if (cursor.getString(cursor.getColumnIndex(Database.STOCK_NAME)).equals(graphNum)) {
                    newGraph.add(cursor.getFloat(cursor.getColumnIndex(Database.STOCK_VALUE)));

                }
                cursor.moveToNext();
                Log.i(ACTIVITY_NAME, "Finished async");
            }

            return newGraph;
        }
    }
    /**
     * This is setting the stonks activity. We generate three different graphs that show the three most popular
     * NASDAQ stocks.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stonks);

        Database dbHelper = new Database(this);
        db = dbHelper.getWritableDatabase();

//        generateData();

        ArrayList<Float> data1 = new ArrayList<Float>();
        ArrayList<Float> data2 = new ArrayList<Float>();
        ArrayList<Float> data3 = new ArrayList<Float>();
//        dataQuery g1Query = new dataQuery();
//        dataQuery g2Query = new dataQuery();
//        dataQuery g3Query = new dataQuery();

//        g1Query.execute("0");
//        g2Query.execute("1");
//        g3Query.execute("2");

        try {
            data1 = new dataQuery().execute("0").get();
            data2 = new dataQuery().execute("1").get();
            data3 = new dataQuery().execute("2").get();
        }catch(Exception e){
            Log.i(ACTIVITY_NAME, "Errored on loading the data");
        }

        Log.i(ACTIVITY_NAME, "READING ARRAYLISTS:");
        Log.i(ACTIVITY_NAME, data1.toString());
        Log.i(ACTIVITY_NAME, data2.toString());
        Log.i(ACTIVITY_NAME, data3.toString());


//        Float[] data = new Float[] {1.0F,2.0F,3.0F,4.0F,5.0F,6.0F,6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 4.0F, 5.0F, 7.0F, 10.0F, 6.0F,1.0F,2.0F,3.0F,4.0F,5.0F,6.0F,6.0F, 6.0F, 6.0F, 6.0F, 6.0F, 5.0F, 4.0F, 5.0F, 7.0F, 10.0F, 6.0F};
        LineChart chart1 =  findViewById(R.id.chart1);
        LineChart chart2 =  findViewById(R.id.chart2);
        LineChart chart3 =  findViewById(R.id.chart3);

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

    /**
     * This is randomly generating data for our Stock graphs. This is a helper function.
     */
    public void generateData(){
        Random rd = new Random();
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 55; j++) {
                ContentValues xValues = new ContentValues();
                Float newValue = rd.nextFloat() * 100.0F * rd.nextFloat();
                xValues.put(Database.STOCK_NAME, Integer.toString(i));
                xValues.put(Database.STOCK_VALUE, newValue);
                db.insert(Database.STOCKS, "NullPlaceHolder", xValues);

            }
        }
    }


    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.stocks_toolbar_menu, m);
        return true;
    }

    /**
     * This function is where we generate our graphs based on the data, and our Line Chart.
     * @param data1
     * @param chart1
     */
    public void generateGraph(ArrayList<Float> data1, LineChart chart1){
        List<Entry> entries1 = new ArrayList<Entry>();


    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {
            case R.id.homepage:
                Log.d("Homepage","Option 1 selected");
                Intent intentHP = new Intent(this,MainActivity.class);
                startActivity(intentHP);
                //do nothing
                break;


            case R.id.stocks:
//                Log.d("stonks", "Option 3 selected");
//                // go to stocks layout
//                Intent intentStocks = new Intent(this, stonks.class);
//                startActivity(intentStocks);
                break;


        }
        return true;
    }
}
