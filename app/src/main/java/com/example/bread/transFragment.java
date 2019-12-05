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

public class transFragment extends Fragment {
    account account;
    public transFragment(account account) {
        this.account = account;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View result = inflater.inflate(R.layout.activity_trans_fragment, container, false);
        final String cost = getArguments().getString("cost");
        //System.out.println(cost);
        final TextView costV = result.findViewById(R.id.cost);
        costV.setText(cost);
        Button delete = result.findViewById(R.id.delete_transaction);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account == null) {
                    Log.d("Wrong place", "onClick: ");
                    Intent data = new Intent();
                    String row = costV.getText().toString();
                    data.putExtra("row", row);
                    getActivity().setResult(Activity.RESULT_OK, data);
                    getActivity().finish();
                } else {
                    account.deleteRow(cost);
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
