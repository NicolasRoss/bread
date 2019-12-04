package com.example.bread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "login";
    static SQLiteDatabase db;
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean checkEmailForValidity(String email) {
        email = email.trim();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Database dbHelper = new Database(this);
        db = dbHelper.getWritableDatabase();
        Log.i(ACTIVITY_NAME, "In onCreate()");
        final SharedPreferences sharedPref = getSharedPreferences("DefaultEmail", Context.MODE_PRIVATE);
        String email =sharedPref.getString("DefaultEmail", "email@domain.com");
        final EditText email_field = (EditText) findViewById(R.id.email_txt);
        email_field.setText(email);

        Button login = (Button) findViewById(R.id.login_btn);
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

        Button logincreateaccount_btn = (Button) findViewById(R.id.newuser_btn);
        logincreateaccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                final LayoutInflater inflater = LayoutInflater.from(login.this);
                final View view = inflater.inflate(R.layout.activity_new_user, null, false);
                final EditText first= view.findViewById(R.id.fname_txt);
                final EditText last = view.findViewById(R.id.lname_txt);
                final EditText email_input = view.findViewById(R.id.inputemail_txt);
                final EditText password_input = view.findViewById(R.id.inputpassword_txt);
                builder.setView(view)
                        .setTitle(R.string.dialog_message)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //User clicked OK button
                                Log.i(ACTIVITY_NAME, "Item added:");
                                String fnames = first.getText().toString();
                                Log.i(ACTIVITY_NAME, "Item added: " + fnames);
                                String lnames = last.getText().toString();
                                Log.i(ACTIVITY_NAME, "Item added: " + lnames);
                                String emails = email_input.getText().toString();
                                Log.i(ACTIVITY_NAME, "Item Added: " + emails);
                                String pass = password_input.getText().toString();
                                Log.i(ACTIVITY_NAME, "Item added: " + pass);

                                boolean emailIsValid = checkEmailForValidity(emails);
                                if (emailIsValid == true && !fnames.equals("") && !lnames.equals("") && !pass.equals("")){
                                    CharSequence toasttext = "Your account have been created";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast createaccount_toast = Toast.makeText(getApplicationContext(), toasttext, duration);
                                    createaccount_toast.show();
                                    ContentValues content = new ContentValues();
                                    content.put(Database.EMAIL, emails);
                                    content.put(Database.FNAME, fnames);
                                    content.put(Database.LNAME, lnames);
                                    content.put(Database.PW, pass);
                                    db.insert(Database.USERS, "nullPlaceHolder", content);
                                }
                                else{
                                    CharSequence toasttext = "An entry has not been filled properly. Please go back and check your entries and ensure you are filling everything in properly";
                                    int duration = Toast.LENGTH_LONG;
                                    Toast createaccount_toast = Toast.makeText(getApplicationContext(), toasttext, duration);
                                    createaccount_toast.show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
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
