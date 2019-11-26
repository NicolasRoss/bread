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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";
    ImageButton accButton;
    ImageButton logButton;
    ImageButton budgButton;
    ImageButton stockButton;
    Button addcard;
    ListView cardView;
    ArrayList<String> cards;
    CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        cardView = findViewById(R.id.card_view);
        cards = new ArrayList<>();

        cardAdapter = new CardAdapter(this);
        cardView.setAdapter(cardAdapter);

        accButton = findViewById(R.id.account);
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Clicked Account Button.");

                Intent intent = new Intent(MainActivity.this, account.class);
                startActivity(intent);
            }
        });

        logButton = findViewById(R.id.logout);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Clicked Logout Button.");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.logout_dialog)

                        .setTitle(R.string.logout_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent(  );
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.cancel();
                                return;
                            }
                        })
                        .show();
            }
        });

        budgButton = findViewById(R.id.budget);
        budgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Clicked Budget Button");
            }
        });

        stockButton = findViewById(R.id.stocks);
        stockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Clicked Stock Button");

                Intent intent = new Intent(MainActivity.this, stonks.class);
                startActivity(intent);
            }
        });

        addcard = findViewById(R.id.add_card);
        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Clicked Add Card Button");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View view = inflater.inflate(R.layout.add_card_dialog, null, false);
                final EditText cardNumber = view.findViewById(R.id.card_number);
                Log.i(ACTIVITY_NAME, cardNumber.toString());
                final EditText firstName = view.findViewById(R.id.firstName);
                final EditText lastName = view.findViewById(R.id.lastName);

                builder.setView(view)
                        .setTitle(R.string.card_dialog)
                        // Add action buttons
                        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(ACTIVITY_NAME, "Item Added:" + cardNumber.getText().toString());
                                String num = cardNumber.getText().toString();
                                String first = firstName.getText().toString();
                                String last = lastName.getText().toString();

                                cards.add(num);
                                cardAdapter.notifyDataSetChanged();
                                cardNumber.setText("");
                                //Add all the views to the database.

                                CharSequence text = ("Card Added.") ;
                                Toast toast = Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                return;
                            }
                        })
                .show();
            }
        });


    }

    private class CardAdapter extends ArrayAdapter<String> {
        public CardAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount() {
            return cards.size();
        }

        public String getItem(int position) {
            return cards.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            View result = inflater.inflate(R.layout.card_display, null);
            TextView card = result.findViewById(R.id.card_text);
            card.setText(getItem(position));
            return result;
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
