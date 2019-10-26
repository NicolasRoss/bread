package com.example.bread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                startActivity(intent);
            }
        }));
    }
}
