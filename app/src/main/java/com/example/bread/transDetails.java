package com.example.bread;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;


public class transDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        transFragment fragment = new transFragment(null);
        Bundle arguments = getIntent().getExtras();
        fragment.setArguments(arguments);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.trans_details,fragment).addToBackStack(null).commit();
    }
}
