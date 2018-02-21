package com.example.jai.project;

      import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
      import android.support.v4.app.FragmentTransaction;
      import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
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


public class Menu3 extends Fragment implements View.OnClickListener {
    public static final String DATABASE_NAME = "id4265674_apps";
    Spinner spinnerGenre;
    TextView textViewViewEmployees;
    EditText i,p,s;
    View view;
    SQLiteDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        view = inflater.inflate(R.layout.fragment_menu_3, container, false);

        i = (EditText) view.findViewById(R.id.item);
        p = (EditText) view.findViewById(R.id.price);
        s = (EditText) view.findViewById(R.id.ls);
        spinnerGenre = (Spinner) view.findViewById(R.id.spinnerGenres);


        view.findViewById(R.id.buttonAddBus).setOnClickListener(this);


        //creating a database
        mDatabase = getActivity().openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
createBusInfoTable();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 3");
    }

    private boolean inputsAreCorrect(String ID, String Math,String Bio) {
        System.out.println(ID+"  "+Math+"  "+Bio);
        if (ID.isEmpty()) {
            i.setError("Please enter a name");
            i.requestFocus();
            return false;
        }

        if (Math.isEmpty()|| Integer.parseInt(Math) <= 0) {
            p.setError("Please valid price");
            p.requestFocus();
            return false;
        }
if(Bio.isEmpty()|| Integer.parseInt(Bio) <= 0 ||  Integer.parseInt(Bio) >=10000 )
{
    s.setError("Please enter reasonable stock (i.e <10000");
    s.requestFocus();
    return false;
}
        return true;
    }
    private void createBusInfoTable() {

              String d=  "CREATE TABLE IF NOT EXISTS stock (\n" +
                        "    item varchar(100) PRIMARY KEY,\n" +
                        "    price int(10) NOT NULL,\n" +
                        " genre varchar(100) NOT NULL,\n"+
                      "lstock int(5) not null\n"+
                                         ");";
        mDatabase.execSQL(d);

    }
    private void addBus() {

        String ID = i.getText().toString().trim();
        String Math = p.getText().toString().trim();
        String Eng = spinnerGenre.getSelectedItem().toString().trim();
String Bio=s.getText().toString().trim();
        //validating the inptus
        if (inputsAreCorrect(ID, Math,Bio)) {

            String insertSQL = "INSERT INTO stock \n" +
                    "(item,price,genre,lstock)\n" +
                    "VALUES \n" +
                    "(?, ?, ?,?);";

            //using the same method execsql for inserting values
            //this time it has two parameters
            //first is the sql string and second is the parameters that is to be binded with the query
            mDatabase.execSQL(insertSQL, new String[]{ID, Math, Eng,Bio});

            Toast.makeText(getActivity(), "Record Successfully added", Toast.LENGTH_SHORT).show();
            i.setText("");
            p.setText("");
            s.setText("");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddBus:
                addBus();

                   break;
        }
    }
}