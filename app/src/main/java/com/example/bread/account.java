package com.example.bread;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 *This is a overview page of your spending habits. There is a graph at the top half of the page
 * showing the user, their current spending. The bottom half of the screen has an ListView of all your
 * transactions, an EditText to input your transaction spend (E.x. $4.20), two buttons to add and delete transactions
 * which is added and deleted in the database.
 * @author Luke Krete
 * @author Prayrit Khanna
 * @author Noah Nichols
 * @version 2019.12
 */
public class account extends AppCompatActivity {
    transFragment fragment;
    FragmentTransaction ft;
    ProgressBar pb;
    private Handler handler = new Handler();
    int progress = 0;
    protected static final String ACTIVITY_NAME = "Account";
    static final String GET_TRANSACTIONS = "SELECT COST FROM TRANSACTIONS";
    ArrayList<String> trans;
    TransAdapter transAdapter;
    static SQLiteDatabase database;
    Cursor cursor;


    //FrameLayout frameLayout;
    //TransFragment fragment;
    //FragmentTransaction fragmentTransaction;

    protected class dataQuery extends AsyncTask<String, Integer, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            Log.i(ACTIVITY_NAME, "Started async");
            String name = strings[0];
            ArrayList<String> newTrans = new ArrayList<>();
            final Cursor cursor = database.rawQuery(name, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "Cursors column count =" + cursor.getColumnCount());
                newTrans.add(cursor.getString(cursor.getColumnIndex(Database.COST)));
                cursor.moveToNext();
            }
            return newTrans;
        }
    }


    /**
     * Start the account activity, where you fill in the listView with your transactions. This will be updated in the
     * database.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        trans = new ArrayList<>();
        transAdapter = new TransAdapter(this);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        pb = findViewById(R.id.progressBar);
        pb.setMax(100);
        final Button buttonAdd = findViewById(R.id.btnAddTrans);
        final Button buttonDel = findViewById(R.id.btnDel);
        final ListView lstTrans = findViewById(R.id.listview_transactions);
        final EditText edtText = findViewById(R.id.editText);

        Database dbHelper = new Database(this);
        database = dbHelper.getWritableDatabase();


        //System.out.println("HERE");
        dataQuery q1 = new dataQuery();
        q1.execute(GET_TRANSACTIONS);
        try{
            trans = q1.get();
        }catch(Exception e){
            Log.i(ACTIVITY_NAME,"Query Failed");
        }




        /**
         *TransAdapter interacts with the ListView to check if there are any changes in the ListView.
         */
        class TransAdapter extends ArrayAdapter {
            public TransAdapter(Context ctx){super(ctx,0);}
            public int getCount(){return trans.size();}
            public String getItem(int position){return trans.get(position);}
            public View getView(int position, View convertView, ViewGroup parent){
                LayoutInflater inflater = account.this.getLayoutInflater();
                View result = inflater.inflate(R.layout.add_trans,null);
                TextView message = result.findViewById(R.id.transaction);
                message.setText(getItem(position));
                return result;
            }
        }
        final TransAdapter transAdapter = new TransAdapter(this);
        /**
         * Once you click this button, you add your transaction to the listView, which will be added to the database.
         */

        buttonAdd.setOnClickListener((new View.OnClickListener() {
            @Override
            /**
             *
             */
            public void onClick(View v) {
                String value = edtText.getText().toString();
                try{
                    //this thing is dumb
                    Float.parseFloat(value);
                    if(value.indexOf('.') == -1){
                        value = value +  ".00";
                    }

                    ContentValues cValues = new ContentValues();
                    cValues.put(Database.COST,value);
                    cValues.put(Database.EMAIL,"test");
                    //PROGRESS BAR STUFF------------------
                    new Thread(new Runnable(){
                       @Override
                       public void run(){
                           while(progress < 100){
                               progress+=1;
                               android.os.SystemClock.sleep(5);
                               handler.post(new Runnable(){
                                  @Override
                                  public void run(){
                                      pb.setProgress(progress);
                                  }
                               });
                           }
                       }
                    }).start();
                    //------------------------------------------
                    //System.out.println(progress);

                    Log.i(ACTIVITY_NAME,"Progress is at 100");
                    database.insert(Database.TRANSACTIONS,"NullPlaceHolder",cValues);
                    trans.add(value);
                    transAdapter.notifyDataSetChanged();
                    progress = 0;
                    pb.setProgress(0);

                }catch(Exception e){
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(account.this);
                    builder.setMessage("Input Error, please enter a valid float value.")
                            .setTitle("Input Error")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            })
                            .show();
                }
                edtText.setText("");
            }
        }));
        /**
         *This will delete the selected transaction from the listView, and the database.
         */
        buttonDel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = edtText.getText().toString();
                //System.out.println(value);
                int index = trans.indexOf(value);
                try {
                    database.delete(Database.TRANSACTIONS,Database.COST+"="+value,null);
                    trans.remove(index);
                    transAdapter.notifyDataSetChanged();
                }catch(Exception e){
                    value = value + ": This transaction does not exist.";
                    Toast toast = Toast.makeText(getApplicationContext(),value, Toast.LENGTH_LONG);
                    toast.show();
                }
                //trans.add(value);
                edtText.setText("");
            }
        }));

        /**
         *
         */

        lstTrans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cost = trans.remove(i);
                //String text = "Value of transaction: ";
                Bundle arguments = new Bundle();
                arguments.putString("cost",cost);
                //text = text + lstTrans.getItemAtPosition(i).toString();

                Intent intent = new Intent(account.this, transDetails.class);
                intent.putExtras(arguments);
                startActivity(intent);

                //int duration = Snackbar.LENGTH_LONG;
                //Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                //toast.show();
                //Snackbar.make(view,text,duration).setAction("Reaction",null).show();
            }
        });
        lstTrans.setAdapter(transAdapter);
    }
    /*
    private void setProgressValue(final int progress) {
        // set the progress
        pb.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        Thread.sleep(10);
                        thread.interrupt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setProgressValue(progress + 1);
                    System.out.println(progress);
                }
        });
        thread.start();
    }
     */

    class TransAdapter extends ArrayAdapter {
        public TransAdapter(Context ctx){super(ctx,0);}
        public int getCount(){return trans.size();}
        public String getItem(int position){return trans.get(position);}
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = account.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.trans_display,null);
            TextView message = result.findViewById(R.id.trans_text);
            message.setText(getItem(position));
            return result;


        }
    }

    /**
     *This inflates our toolbar menu which contains our buttons to move to other screens
     * @param m
     * @return
     */

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.account_summary_toolbar_menu, m);
        return true;
    }

    /**
     * This is our options in our menu. This is where we set our cases to move from one activity, to another.
     * @param mi
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {
            case R.id.homepage:
                Log.d("Homepage","Option 1 selected");
                Intent intentHP = new Intent(this,MainActivity.class);
                startActivity(intentHP);
                //do nothing
                break;

            case R.id.stocks:
                Log.d("stonks", "Option 3 selected");
                // go to stocks layout
                Intent intentStocks = new Intent(this, stonks.class);
                startActivity(intentStocks);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    void deleteRow(String row){
        FragmentManager fragmentManager = getSupportFragmentManager();
        database.delete(Database.TRANSACTIONS,Database.COST+ "="+ row,null);
        //int index = trans.indexOf(row);
        trans.remove(row);
        ft = fragmentManager.beginTransaction();
        ft.remove(fragment).commit();
        transAdapter.notifyDataSetChanged();
    }
}
