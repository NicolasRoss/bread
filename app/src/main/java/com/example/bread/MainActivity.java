package com.example.bread;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This is the our home screen. It holds 5 buttons, ListView and TextView.
 * You are able to add your card which will be added to our ListView and Database.
 * The other 3 buttons, are gateways to our other Activities and one button is our settings button that will
 * show the app information.
 * The TextView shows the current User's balance.
 * {@link account}
 * {@link stonks}
 * {@link login}
 */
public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";
    static final String GET_CARDS = "SELECT CARD_NUMBER, FIRST_NAME, LAST_NAME FROM BANK_CARDS";
    static SQLiteDatabase database;
    Cursor cursor;
    cardFragment fragment;
    FragmentTransaction ft;
    ImageButton accButton;
    ImageButton logButton;
    ImageButton budgButton;
    ImageButton stockButton;
    Button addcard;
    ListView cardView;
    ArrayList<String> cards;
    CardAdapter cardAdapter;

    /**
     * This opens the Activity on our application. Setting up the ListView by retreiving card information
     * from the Database.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        cards = new ArrayList<>();
        Database dbHelper = new Database(this);
        database = dbHelper.getWritableDatabase();
        cursor = database.rawQuery(GET_CARDS, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "Cursors column count =" + cursor.getColumnCount());

            cards.add(cursor.getString(cursor.getColumnIndex(Database.CARD_NUM)));
            cursor.moveToNext();
        }

        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );
        for (int i = 0; i <cursor.getColumnCount();i++){
            Log.i(ACTIVITY_NAME, "Column Name: "+ cursor.getColumnName(i));
        }

        cardView = findViewById(R.id.card_view);
        cursor.moveToFirst();
        /**
         * This is setting the information from our DialogBox to input it into the database.
         */
        cardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String card = cursor.getString(cursor.getColumnIndex("CARD_NUMBER"));
                String fname = cursor.getString(cursor.getColumnIndex("FIRST_NAME"));
                String lname = cursor.getString(cursor.getColumnIndex("LAST_NAME"));
                Log.i(ACTIVITY_NAME, card);
                Bundle arguments = new Bundle();
                Log.i("Passing Card Number", card);

                arguments.putString("card", card);
                arguments.putString("first_name", fname);
                arguments.putString("last_name", lname);

                Intent intent = new Intent(MainActivity.this, cardDetails.class);
                intent.putExtras(arguments);
                startActivityForResult(intent, 10);
            }
        });

        accButton = findViewById(R.id.account);
        /**
         * This is an action button. This button takes you to our Account Activity screen.
         */
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Clicked Account Button.");

                Intent intent = new Intent(MainActivity.this, account.class);
                startActivity(intent);
            }
        });

        logButton = findViewById(R.id.logout);
        /**
         * This leads to our logout login screen if "ok" is clicked when the dialogBox is opened up on our screen.
         */
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
                                Intent resultIntent = new Intent(MainActivity.this, login.class);
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
        /**
         * This button takes you to you stonks Activity.
         */
        stockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "Clicked Stock Button");

                Intent intent = new Intent(MainActivity.this, stonks.class);
                startActivity(intent);
            }
        });

        cardAdapter = new CardAdapter(this);
        addcard = findViewById(R.id.add_card);
        /**
         * When the 'Add Card' button is clicked it brings up a dialogBox to input your Card number,
         * Last Name, and First name. Once you enter in a correct card number and your first and last name
         * the card number will be added to a ListView and the Database. This is where you can manage your cards.
         */
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
                                String num = cardNumber.getText().toString();
                                String first = firstName.getText().toString();
                                String last = lastName.getText().toString();

                                if (num.length() == 16 && !first.equals("") && !last.equals("")) {
                                    cards.add(num);
                                    //Add all the views to the database.
                                    ContentValues cValues = new ContentValues();
                                    cValues.put(Database.CARD_NUM, num);
                                    cValues.put(Database.FNAME, first);
                                    cValues.put(Database.LNAME, last);
                                    database.insert(Database.BANK_CARDS, "NullPlaceHolder", cValues);
                                    cardAdapter.notifyDataSetChanged();
                                    cardNumber.setText("");

                                    Log.i(ACTIVITY_NAME, "Item Added:" + cardNumber.getText().toString());

                                    CharSequence text = ("Card Added.");
                                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
                                    toast.show();

                                } else {
                                    Log.i(ACTIVITY_NAME, "Invalid Input.");

                                    CharSequence text = ("Invalid Input.");
                                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
                                    toast.show();
                                }
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

        cardView.setAdapter(cardAdapter);
    }

    /**
     *
     */
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

        /**
         * When an Item in the ListView is clicked, this inflates the fragment containing card information.
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
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
        database.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            String row = data.getStringExtra("row");
            Log.i("delete row: ", ""+row);
            database.delete(Database.BANK_CARDS,Database.CARD_NUM+ "="+row,null);
            cards.remove(row);
            cardAdapter.notifyDataSetChanged();
        }

    }

    void deleteRow(String row){
        FragmentManager fragmentManager = getSupportFragmentManager();
        database.delete(Database.BANK_CARDS,Database.CARD_NUM+ "="+ row,null);
        cards.remove(row);

        ft = fragmentManager.beginTransaction();
        ft.remove(fragment).commit();
        cardAdapter.notifyDataSetChanged();
    }
}
