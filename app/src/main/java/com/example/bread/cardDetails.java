package com.example.bread;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class cardDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_details);

        FragmentManager fragmentManager = getSupportFragmentManager();
        cardFragment fragment = new cardFragment(null);
        Bundle arguments = getIntent().getExtras();
        fragment.setArguments(arguments);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.card_details, fragment).addToBackStack(null).commit();

    }

}
