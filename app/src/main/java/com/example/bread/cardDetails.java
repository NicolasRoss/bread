package com.example.bread;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * This is a ListView on our MainActivity screen to show all your cards.
 * @author Nick Ross
 * @author Prayrit Khanna
 * @version 2019.12
 * {@link cardFragment}
 */
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
