package com.example.bread;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class cardFragment extends Fragment {

    MainActivity cards;

    public cardFragment(MainActivity cards) {
        this.cards = cards;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View result = inflater.inflate(R.layout.card_fragment, container, false);

        final String cardNum = getArguments().getString("card");
        String firstName = getArguments().getString("first_name");
        String lastName = getArguments().getString("last_name");

        final TextView card = result.findViewById(R.id.card_num);
        final TextView first = result.findViewById(R.id.first_name);
        final TextView last = result.findViewById(R.id.last_name);

        card.setText(cardNum);
        first.setText(firstName);
        last.setText(lastName);

        Button delete = result.findViewById(R.id.delete_card);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cards == null) {
                    Log.d("Wrong place", "onClick: ");

                    Intent data = new Intent();
                    String row = card.getText().toString();
                    data.putExtra("row", row);
                    getActivity().setResult(Activity.RESULT_OK, data);
                    getActivity().finish();

                } else {
                    cards.deleteRow(cardNum);
                }

            }
        });
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}