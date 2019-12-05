package com.example.bread;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is creating a newUser in the database. This asks for first name, last name, email, password.
 */
public class NewUser extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "NewUser";
    EditText inputfirstandlastname, inputemail, inputpassword, inputpostalcode;
    Button inputcreateaccount;

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
        setContentView(R.layout.activity_new_user);
        inputfirstandlastname = (EditText) findViewById(R.id.fname_txt);
        inputemail = (EditText) findViewById(R.id.inputemail_txt);
        inputpassword = (EditText) findViewById(R.id.inputpassword_txt);
        inputcreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean fieldsOK = validate(new EditText[]{inputfirstandlastname, inputemail, inputpassword, inputpostalcode});
                boolean emailIsValid = checkEmailForValidity(inputemail.getText().toString());
                if (fieldsOK == true && emailIsValid == true){
                    CharSequence toasttext = "Your account have been created";
                    int duration = Toast.LENGTH_SHORT;
                    Toast createaccount_toast = Toast.makeText(getApplicationContext(), toasttext, duration);
                    createaccount_toast.show();

                }
                else{
                    CharSequence toasttext = "An entry has not been filled properly. Please go back and check your entries and ensure you are filling everything in properly";
                    int duration = Toast.LENGTH_LONG;
                    Toast createaccount_toast = Toast.makeText(getApplicationContext(), toasttext, duration);
                    createaccount_toast.show();
                }
            }
        });
    }

    //Checking to see if the EditText fields are empty.
    //This will be a check.
    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
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
