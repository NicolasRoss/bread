package com.example.bread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {
            case R.id.homepage:
                //do nothing
                break;

            case R.id.account_summary:
                Log.d("Account", "Option 1 selected");
                // go to account summary layout
                Intent intent = new Intent(this, account.class);
                startActivity(intent);
                break;

            case R.id.budget:
                Log.d("budget", "Option 2 selected");
                // go to budget layout
//                Intent intent = new Intent(this, .class);
//                startActivity(intent);
                break;

            case R.id.stocks:
                Log.d("stonks", "Option 3 selected");
                // go to stocks layout
                Intent intentStocks = new Intent(this, stonks.class);
                startActivity(intentStocks);
                break;

            case R.id.action_about:
                Log.d("Toolbar", "Option 4 selected");
                CharSequence text = "Version 1.0, by Nicolas Ross";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
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
