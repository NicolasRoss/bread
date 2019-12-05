package com.example.bread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class account extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "Account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        final Button buttonAdd = findViewById(R.id.btnAddTrans);
        final Button buttonDel = findViewById(R.id.btnDel);
        final ListView lstTrans = findViewById(R.id.listview_transactions);
        final EditText edtText = findViewById(R.id.editText);
        final ArrayList<String> trans = new ArrayList<>();

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
        lstTrans.setAdapter(transAdapter);
        buttonAdd.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = edtText.getText().toString();
                try{
                    //this thing is dumb
                    Float.parseFloat(value);
                    if(value.indexOf('.') == -1){
                        value = value +  ".00";
                    }
                    trans.add(value);
                    transAdapter.notifyDataSetChanged();
                }catch(Exception e){
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(account.this);
                    builder.setMessage("Input Error, please enter a valid float value.")
                            .setTitle("Input Error")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            })
                            .show();
                }
                edtText.setText("");
            }
        }));
        buttonDel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = edtText.getText().toString();
                int index = trans.indexOf(value);
                try {
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

        lstTrans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = "Value of transaction: ";
                text = text + lstTrans.getItemAtPosition(i).toString();
                int duration = Snackbar.LENGTH_LONG;
                //Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                //toast.show();
                Snackbar.make(view,text,duration).setAction("Reaction",null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.account_summary_toolbar_menu, m);
        return true;
    }

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
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
