package com.example.jai.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.chrono.HijrahChronology;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static android.widget.Toast.makeText;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu1 extends Fragment implements View.OnClickListener {

    TextView textViewViewEmployees;
    EditText id,m,ta;
String c;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_menu_1, container, false);
        id = (EditText) view.findViewById(R.id.custid);
        m = (EditText) view.findViewById(R.id.Pno);
id.setText("");
m.setText("");
        view.findViewById(R.id.buttonAddUser).setOnClickListener(this);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");

    }
    private boolean inputsAreCorrect(String ID, String Math) {
        if (ID.isEmpty()) {
            id.setError("Please enter a id");
            id.requestFocus();
            return false;
        }

        if (Math.isEmpty()|| Math.length()!=10) {
            m.setError("Please valid number");
            m.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        System.out.println("hi");
        String a = id.getText().toString().trim();
        String b = m.getText().toString().trim();
        System.out.println(a+b);

        switch (view.getId()) {
                case R.id.buttonAddUser:
                    c="grocery";
                    break;
            case R.id.buttonAddUser1:
                c="cosmetics";
                break;
            case R.id.buttonAddUser2:
                c="stationary";
                break;
            case R.id.buttonAddUser3:
                c="food n beverages";
                break;
            }
                    if (inputsAreCorrect(a,b)) {
id.setText("");
m.setText("");
                        Intent intent = new Intent(getActivity().getBaseContext(), ConfirmActivity.class);
                        intent.putExtra("id", a);
                        intent.putExtra("mob", b);
                        intent.putExtra("genre",c);
                        startActivity(intent);
                    }

        }
    }

