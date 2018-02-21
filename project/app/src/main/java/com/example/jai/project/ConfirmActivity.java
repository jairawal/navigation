package com.example.jai.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.squareup.sdk.pos.ChargeRequest;
import com.squareup.sdk.pos.PosClient;
import com.squareup.sdk.pos.PosSdk;

import java.time.chrono.HijrahChronology;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static android.widget.Toast.makeText;
import static com.squareup.sdk.pos.CurrencyCode.INR;
import static com.squareup.sdk.pos.CurrencyCode.USD;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String DATABASE_NAME = "id4265674_apps";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private PosClient posClient;
    ListView listViewArtists;
    Button buttonAddArtist;
    //a list to store all the artist from firebase database
    List<String> stocks;
    SQLiteDatabase mDatabase;
    EditText id;
String a,phoneNo,c,message,f,st;
int z,o=0,amt=0;
String item[]=new String[100];
int q[]=new int[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        id = (EditText) findViewById(R.id.amt);
item=new String[item.length];
q=new int[q.length];
        posClient = PosSdk.createClient(this,"sq0idp-vqzmcaqerWSBJt5ePd__aw");
        findViewById(R.id.buttonAddUser).setOnClickListener(this);
        stocks = new ArrayList<>();
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
         a=getIntent().getStringExtra("id");
         phoneNo=getIntent().getStringExtra("mob");
         System.out.println(phoneNo);
         c=getIntent().getStringExtra("genre");
    }
    final Context context = this;

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        stocks.clear();
           String getSQL="SELECT * FROM stock where genre=\""+c+"\";";

            Cursor cursorEmployees = mDatabase.rawQuery(getSQL, null);
            //using the same method execsql for inserting values
            //this time it has two parameters
            //first is the sql string and second is the parameters that is to be binded with the query
            if (cursorEmployees.moveToFirst()) {
                //looping through all the records
                do {
                    String j= cursorEmployees.getString(0);
                    f=cursorEmployees.getString(3);

stocks.add(j);

                } while (cursorEmployees.moveToNext());
            }
            //closing the cursor
            cursorEmployees.close();
        //creating adapter
        stocklist artistAdapter = new stocklist(ConfirmActivity.this, stocks);

        //attaching adapter to the listview
        listViewArtists.setAdapter(artistAdapter);
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);
                final String cities = String.valueOf(adapterView.getItemAtPosition(i));
                item[o]=cities;

                System.out.println(cities+"   "+item[o]);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        z=Integer.parseInt(String.valueOf(userInput.getText()));
                                        q[o]=z;
                                        System.out.println(z);
                                        o++;
                                   int xz=Integer.parseInt(f)-z,xf=Integer.parseInt(f);
                                   if(xz>0) {
                                       String updateSQL = "update stock set lstock=" + xz + " where item='" + cities + "';";

                                       //using the same method execsql for inserting values
                                       //this time it has two parameters
                                       //first is the sql string and second is the parameters that is to be binded with the query
                                       mDatabase.execSQL(updateSQL);
                                   }
else
                                       Toast.makeText(getApplicationContext(),"Max stock available is "+f,Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                                                           }
                                                       } );
    }

    private static final int CHARGE_REQUEST_CODE = 1;
    public void startTransaction() {
        ChargeRequest request = new ChargeRequest.Builder(amt, USD).build();
        try {
            Intent intent = posClient.createChargeIntent(request);
            startActivityForResult(intent, CHARGE_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            showDialog("Error", "Square Point of Sale is not installed", null);
            posClient.openPointOfSalePlayStoreListing();
        }
    }

    private void showDialog(String title, String message, DialogInterface.OnClickListener listener) {
        Log.d("ConfirmActivity", title + " " + message);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHARGE_REQUEST_CODE) {
            if (data == null) {
                showDialog("Error", "Square Point of Sale was uninstalled or crashed", null);
                return;
            }

            if (resultCode == Activity.RESULT_OK) {
                ChargeRequest.Success success = posClient.parseChargeSuccess(data);
                String message = "Client transaction id: " + success.clientTransactionId;
                showDialog("Success!", message, null);
            } else {
                ChargeRequest.Error error = posClient.parseChargeError(data);

                if (error.code == ChargeRequest.ErrorCode.TRANSACTION_ALREADY_IN_PROGRESS) {
                    String title = "A transaction is already in progress";
                    String message = "Please complete the current transaction in Point of Sale.";

                    showDialog(title, message, new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            // Some errors can only be fixed by launching Point of Sale
                            // from the Home screen.
                            posClient.launchPointOfSale();
                        }
                    });
                } else {
                    showDialog("Error: " + error.code, error.debugDescription, null);
                }
            }
        }
    }
   @Override
    public void onClick(View view) {
        int tm[]=new int[50];
       for (int i = 0; i <= 50; i++) {

           String getSQL = "SELECT * FROM stock where item=\"" + item[i] + "\";";

           Cursor cursorEmployees = mDatabase.rawQuery(getSQL, null);
           //using the same method execsql for inserting values
           //this time it has two parameters
           //first is the sql string and second is the parameters that is to be binded with the query
           if (cursorEmployees.moveToFirst()) {
               //looping through all the records
               do {
                   String j = cursorEmployees.getString(1);
                    tm[i]=q[i]*Integer.parseInt(j);
amt=amt+tm[i];
System.out.println(amt);
               } while (cursorEmployees.moveToNext());
           }
           //closing the cursor
           cursorEmployees.close();
       }
       id.setText(amt+"");
       message="Your net bill payable is Rs."+amt;

       SmsManager sms=SmsManager.getDefault();
       sms.sendTextMessage(phoneNo,null,message,null,null);
       Toast.makeText(getApplicationContext(),"SMS successfully sent",Toast.LENGTH_SHORT).show();

       AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(
               context);

       // set prompts.xml to alertdialog builder
       // set dialog message
       AlertDialog.Builder builder = alertDialogBuilder1;
       builder.setMessage("Your net amount payable is Rs." + amt);
       builder.setCancelable(false);
       builder.setPositiveButton("OK",
               new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // get user input and set it to result
                       // edit text
finish();
                   }
               });

       // create alert dialog
       AlertDialog alertDialog = alertDialogBuilder1.create();

       // show it
       alertDialog.show();
       startTransaction();
   }
}
