package com.example.bread;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        final SharedPreferences sharedPref = getSharedPreferences("DefaultEmail", Context.MODE_PRIVATE);
        String email =sharedPref.getString("DefaultEmail", "email@domain.com");
        final EditText email_field = (EditText) findViewById(R.id.email);
        email_field.setText(email);

        Button login = (Button) findViewById(R.id.button);
        login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("DefaultEmail", email_field.getText().toString());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Log.d("My_tag", intent.toString());
                startActivityForResult(intent, 10);
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode == 10) {
            Log.i(ACTIVITY_NAME, "Returned to login.onActivityResult");
        }

        if(responseCode == Activity.RESULT_OK) {
            CharSequence text = ("Logout successful.") ;
            Toast toast = Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG);
            toast.show();
        }
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
