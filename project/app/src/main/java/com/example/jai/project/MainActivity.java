package com.example.jai.project;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SQLiteDatabase mDatabase;
    private Button btn2;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    public static final String DATABASE_NAME = "id4265674_apps";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_menu1);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void deleteAll()
    {
        //SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        mDatabase.execSQL("DROP TABLE IF EXISTS stock");
createBusInfoTable();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
              if (id == R.id.action_settings) {
                  deleteAll();
                  Fragment fragment=new Menu2();
                  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                  ft.replace(R.id.content_frame, fragment);
                  ft.commit();
            createEmployeeTable();
            return true;
        }

        if (id == R.id.sign_out) {
//add sign out function
            signOut();
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void signOut() {
        auth.signOut();
    }
    private void createEmployeeTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS crud (\n" +
                        "    id varchar(5) PRIMARY KEY,\n" +
                        "    Maths int(3) NOT NULL,\n" +
                        " English int(3) NOT NULL,\n"+

                        "   Physics int(3) NOT NULL,\n" +
                        "    Chemistry int(3) NOT NULL,\n" +
                        "    Biology int(3) NOT NULL\n" +

                        ");"
        );
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu1:
                fragment = new Menu1();
                break;
            case R.id.nav_menu2:
                fragment = new Menu2();
                break;
            case R.id.nav_menu3:
                fragment = new Menu3();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }


}