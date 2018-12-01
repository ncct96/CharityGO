package org.charitygo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainUI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener, StepListener {

    private TextView txtProgress;
    private ProgressBar progressBar;
    private Handler handler = new Handler();


    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private static int savedNumSteps;
    private double progress;
    private int progressCircle = 0;

    //CK CHANGES
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_ui);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToOrganizations(view);
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Create progress bar
        // Step counter
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        sensorManager.registerListener(MainUI.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        txtProgress = findViewById(R.id.numOfStep);
        progressBar = findViewById(R.id.stepProgress);

        //txtProgress.setText(TEXT_NUM_STEPS + savedNumSteps + "\n" + "Progress: "+ progressCircle + "%");
        txtProgress.setText(numSteps + "\nSTEPS");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Steps", numSteps);
        savedNumSteps = numSteps;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        numSteps = savedInstanceState.getInt("Steps");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        String strProg = String.valueOf(numSteps);
        progress = (Double.parseDouble(strProg) / 100) * 100;
        progressCircle = (int)progress;
        progressBar.setProgress(progressCircle);
        //txtProgress.setText(TEXT_NUM_STEPS + numSteps + "\n" + "Progress: "+ progressCircle + "%");
        txtProgress.setText(numSteps + "\nSTEPS");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(MainUI.this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //CK CHANGES
    //Get log in status
    public static boolean getLoggedStatus(Context context){
        return LoginActivity.getPreference(context).getBoolean(LoginActivity.Logged_IN,false);
    }

    //CK CHANGES
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem profile = menu.findItem(R.id.nav_profile);*/

        boolean isLog = getLoggedStatus(getApplicationContext()); //Get current logged in user's status
        if(isLog){
            //addLoginMenu();
        }else{
            //removeLoginMenu();
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_ui, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_home) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //CK CHANGES
    public void loginPage(MenuItem menuItem){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void logOut(MenuItem menuItem){
        //removeLoginMenu();
        LoginActivity.getPreference(getApplicationContext()).edit().clear().apply();
        Toast.makeText(MainUI.this, "Successfully Signed Out !",Toast.LENGTH_LONG).show();
        Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void profile(MenuItem menuItem){
        /*
        * INSERT CODES TO START PROFILE ACTIVITY HERE
        * */
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }
    public void goToOrganizations(MenuItem menuItem){
        Intent intent = new Intent (getApplicationContext(), OrganizationActivity.class);
        startActivity(intent);
    }
    public void goToSponsors(MenuItem menuItem){
        Intent intent = new Intent (getApplicationContext(), SponsorActivity.class);
        startActivity(intent);
    }
    public void goToLeaderBoard(MenuItem menuItem){
        Intent intent = new Intent (getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }
    public void goToAbout(MenuItem menuItem){
        Intent intent = new Intent (getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    public void goToOrganizations(View view){
        Intent intent = new Intent (this, OrganizationActivity.class);
        startActivity(intent);
    }
}
