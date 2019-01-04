package org.charitygo.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.Constants;
import org.charitygo.R;
import org.charitygo.StepService;
import org.charitygo.model.Reward;
import org.charitygo.model.StepHistory;
import org.charitygo.model.User;

import java.util.Date;

public class MainUI extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    private TextView txtProgress;
    private ProgressBar progressBar;
    private Handler handler = new Handler();

    private SensorManager mSensorManager;
    private boolean isSensorPresent = false;
    private Sensor mSensor;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private static int savedNumSteps, goal;

    //Firebase Reference
    final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    final DatabaseReference ref = mDatabase.getReference("stepHistory");

    //CK CHANGES
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseAuth userInstance = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = userInstance.getCurrentUser();
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_ui);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //initializePoints();

        // Create progress bar
        // Step counter
        // Get an instance of the SensorManager

        txtProgress = findViewById(R.id.numOfStep);
        progressBar = findViewById(R.id.stepProgress);

        initSteps();

        mSensorManager = (SensorManager)
                this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
                != null) {
            mSensor =
                    mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
            isSensorPresent = true;
        } else {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
            isSensorPresent = false;
        }

        createNotificationChannel();

        userInstance.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (currentUser != null) {
//                    ref.child("")
//
//                    txtProgress.setText();
                } else {

                }
            }
        });



//        goal = (100 * 100) / savedNumSteps;

        Log.e("goal", String.valueOf(savedNumSteps));

        progressBar.setProgress(goal);

        //txtProgress.setText(TEXT_NUM_STEPS + savedNumSteps + "\n" + "Progress: "+ progressCircle + "%");
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = fireAuth.getInstance().getCurrentUser();
//        fireAuth = FirebaseAuth.getInstance();
//        fireAuth.addAuthStateListener(authListener);
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct != null){
//            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(MainUI.this, "Reauthenticated.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
    }

    public void initSteps() {
        //Firebase retrieve Steps Data
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StepHistory steps = dataSnapshot.child(currentUser.getUid()).getValue(StepHistory.class);
                //System.out.println(steps.getSteps());
                savedNumSteps = steps.getSteps();
                txtProgress.setText(savedNumSteps + "\nSTEPS");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createNotificationChannel() {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)

        {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorPresent) {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(getApplicationContext(), StepService.class);
        intent.putExtra("steps", savedNumSteps);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String uid = currentUser.getUid();

        ref.child(uid + "/steps").setValue(savedNumSteps);
        if (isSensorPresent) {
            mSensorManager.unregisterListener(this);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //CK CHANGES
    //Get log in status
    public static boolean getLoggedStatus(Context context) {
        return LoginActivity.getPreference(context).getBoolean(LoginActivity.Logged_IN, false);
    }

    //CK CHANGES
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem profile = menu.findItem(R.id.nav_profile);*/

        boolean isLog = getLoggedStatus(getApplicationContext()); //Get current logged in user's status
        if (isLog) {
            //addLoginMenu();
        } else {
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
            Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
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
    public void loginPage(MenuItem menuItem) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private GoogleSignInClient googleSignClient;

    public void logOut(MenuItem menuItem) {
        //removeLoginMenu();
        FirebaseAuth.getInstance().signOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignClient = GoogleSignIn.getClient(this, gso);
        googleSignClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        LoginActivity.getPreference(getApplicationContext()).edit().clear().apply();
        Toast.makeText(MainUI.this, "Successfully Signed Out !", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), GoogleLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();

            if (isNewUser) {

                StepHistory stepHistory = new StepHistory();
            }

        } else {
            Toast.makeText(MainUI.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void profile(MenuItem menuItem) {
        /*
         * INSERT CODES TO START PROFILE ACTIVITY HERE
         * */
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }

    public void goToOrganizations(MenuItem menuItem) {
        Intent intent = new Intent(getApplicationContext(), OrganizationActivity.class);
        startActivity(intent);
    }

    public void goToSponsors(MenuItem menuItem) {
        Intent intent = new Intent(getApplicationContext(), SponsorActivity.class);
        startActivity(intent);
    }

    public void goToLeaderBoard(MenuItem menuItem) {
        Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }

    public void goToRewards(MenuItem menuItem) {
        Intent intent = new Intent(getApplicationContext(), RewardActivity.class);
        startActivity(intent);
    }

    public void goToAbout(MenuItem menuItem) {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    public void goToOrganizations(View view) {
        Intent intent = new Intent(this, OrganizationActivity.class);
        startActivity(intent);
    }

    public void goToRewards(View view) {
        Intent intent = new Intent(this, RewardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        txtProgress.setText(++savedNumSteps + "\nSTEPS");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void initializePoints() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference stepRef = ref.child("rewards"+ firebaseUser.getUid());
        DatabaseReference userRef = ref.child("users"+ firebaseUser.getUid());
        User user = new User();


        stepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    StepHistory currentSteps = dataSnapshot1.getValue(StepHistory.class);

/*                    if(currentSteps.getStartDate() == new Date()){
                        TextView donatePoints = findViewById(R.id.main_donate_points);
                        donatePoints.setText(currentSteps.getSteps() / 10 + " donatePoints available");
                        return;
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TextView redeemPoints = findViewById(R.id.main_redeem_points);
        //redeemPoints.setText(calculator.getRedeemPoints() + " donatePoints available");
    }


}
