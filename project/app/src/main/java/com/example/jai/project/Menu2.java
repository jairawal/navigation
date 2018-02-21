package com.example.jai.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu2 extends Fragment implements View.OnClickListener {
  public static final String DATABASE_NAME = "id4265674_apps";
    Spinner spinnerGenre;
    View view;
    SQLiteDatabase mDatabase;
    String genre,item[]=new String[50];
    ListView listViewArtists;
    List<String> stocks;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view= inflater.inflate(R.layout.fragment_menu_2, container, false);
        spinnerGenre = (Spinner) view.findViewById(R.id.spinnerGenres);
        mDatabase = getActivity().openOrCreateDatabase(MainActivity.DATABASE_NAME, Context.MODE_PRIVATE, null);

        listViewArtists = (ListView) view.findViewById(R.id.listViewArtists);
        view.findViewById(R.id.buttonAddUser).setOnClickListener(this);
        stocks = new ArrayList<>();

spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        stocks.clear();
        genre = spinnerGenre.getSelectedItem().toString().trim();
        String getSQL="SELECT * FROM stock where genre=\""+genre+"\";";

        Cursor cursorEmployees = mDatabase.rawQuery(getSQL, null);
        //using the same method execsql for inserting values
        //this time it has two parameters
        //first is the sql string and second is the parameters that is to be binded with the query
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                String j= cursorEmployees.getString(0);
                stocks.add(j);

            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();
        //creating adapter
        stocklist artistAdapter = new stocklist(getActivity(), stocks);

        //attaching adapter to the listview
        listViewArtists.setAdapter(artistAdapter);
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.uprompt, null);
                final String cities = String.valueOf(adapterView.getItemAtPosition(i));
                item[o]=cities;
                System.out.println(cities+"   "+item[o]);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("Update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        String z=userInput.getText().toString().trim();
                                        String updateSQL = "update stock set lstock="+z+" where item='"+cities+"';";

                                        //using the same method execsql for inserting values
                                        //this time it has two parameters
                                        //first is the sql string and second is the parameters that is to be binded with the query
                                        mDatabase.execSQL(updateSQL);

                                        Toast.makeText(getActivity(), "Record Successfully updated", Toast.LENGTH_SHORT).show();

                                    }
                                })
                        .setNegativeButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
mDatabase.execSQL("delete from stock where item='"+cities+"';");
Toast.makeText(getActivity(), "Record Successfully deleted", Toast.LENGTH_SHORT).show();
stocks.remove(i);
                                        stocklist artistAdapter = new stocklist(getActivity(), stocks);

                                        //attaching adapter to the listview
                                        listViewArtists.setAdapter(artistAdapter);

                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        } );
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 2");
    }
    int o=0,q[]=new int[50];
    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {

        }
    }
 }