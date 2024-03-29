package org.charitygo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.activity.MainUI;
import org.charitygo.model.StepHistory;
import org.charitygo.model.StepsRanking;
import org.charitygo.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StepService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mStepDetectorSensor;
    private final FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    DatabaseReference ref = mFirebase.getReference();
    DatabaseReference stepsRef = mFirebase.getReference().child("stepHistory");
    DatabaseReference rankRef = mFirebase.getReference().child("stepRanking");
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private String uid;
    private Notification notification;
    private static long timestamp = System.currentTimeMillis();
    private DateFormat df = new DateFormat();
    private String monthYearPath = String.valueOf(df.longToYearMonth(timestamp));
    private String dayDatePath = String.valueOf(df.longToYearMonthDay(timestamp));
    private static int stepCounts, initStepCounts, accSteps;
    private StepHistory steps;
    private NotificationCompat.Builder mb;
    private NotificationManager mNotificationManager;


    public StepService() {
        //stepCounts = stepsRef.
    }


    @Override
    public void onCreate() {
        super.onCreate();

        try {
            uid = currentUser.getUid();
        } catch (Exception e) {

        }

        mSensorManager = (SensorManager)
                this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    public void initUser() {
/*        user = new User("Nicholas", "ncct96@gmail.com");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = ref.child("stepsHistory/users/" + uid);
        Map<String, User> users = new HashMap<>();
        users.put(user.name, user);
        userRef.setValue(users);*/
        //userRef.setValue(users);
    }

    public int startServiceForeground(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainUI.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent dismissIntent = new Intent(getApplicationContext(), NotifyBroadcast.class);
        dismissIntent.putExtra("dismiss", "dismiss");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        createNotificationChannel();

        mb = new NotificationCompat.Builder(this, "fg01");

        mb.setSmallIcon(R.drawable.ic_launcher)
                .setBadgeIconType(R.drawable.ic_launcher)
                .setContentTitle("Pedometer Running")
                .setColor(R.drawable.gradient_blue)
                .setColorized(true)
                .setContentText("Total Steps: " + stepCounts)
                .addAction(R.color.white, "Stop", actionIntent)
                .setContentIntent(pendingIntent)
                .setOngoing(true);

        notification = mb.build();

/*        notification = new NotificationCompat.Builder(this, "fg01")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Pedometer Running")
                .setColor(R.drawable.gradient_blue)
                .setColorized(true)
                .setContentText("Total Steps: " + stepCounts)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();*/


        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(300, notification);

        startForeground(300, notification);

        return START_STICKY;
    }

    public void createNotificationChannel() {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)

        {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel("fg01", Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServiceForeground(intent, flags, startId);
        initSteps();
        Log.e("Steps", String.valueOf(stepCounts));
        return START_STICKY;
    }

    private TriggerEventListener Listener = new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mStepDetectorSensor != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {

/*        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // Retrieve data from google user
            FirebaseUser googleUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference googleRef = FirebaseDatabase.getInstance().getReference("stepHistory").child(googleUser.getUid());
            DatabaseReference dateRef = googleRef.child("" + todayDate);
            googleRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final StepHistory stepHistory = dataSnapshot.getValue(StepHistory.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/

        FirebaseUser user = firebaseAuth.getCurrentUser();
        dayDatePath = String.valueOf(df.longToYearMonthDay(System.currentTimeMillis()));
        monthYearPath = String.valueOf(df.longToYearMonth(System.currentTimeMillis()));

        if (user != null) {

            ref.child("stepHistory/" + dayDatePath + "/" + uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot.getValue());
                    initStepCounts = dataSnapshot.child("steps").getValue(Integer.class);
                    retrieveData(initStepCounts);
                    if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                        stepCounts++;
                    }

                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    stepsRef = ref.child("stepHistory");
                    rankRef = ref.child("stepRanking");

                    stepsRef.child(dayDatePath).child(uid).child("steps").setValue(stepCounts);
                    rankRef.child(monthYearPath).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            accSteps = dataSnapshot.child("accSteps").getValue(Integer.class);
                            retrieveAccSteps(accSteps);

                            ++accSteps;
                            rankRef.child(monthYearPath).child(uid).child("accSteps").setValue(accSteps);
                            mb.setContentText("Total Steps: " + stepCounts);
                            notification = mb.build();
                            mNotificationManager.notify(300, notification);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public int initSteps() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        dayDatePath = String.valueOf(df.longToYearMonthDay(System.currentTimeMillis()));
        stepsRef = ref.child("stepHistory/" + dayDatePath + "/" + uid);
        if (user != null) {

            stepsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot.getValue());
                    initStepCounts = dataSnapshot.child("steps").getValue(Integer.class);
                    retrieveData(initStepCounts);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        Log.e("STPPPPPPP", String.valueOf(initStepCounts));
        return initStepCounts;
    }

    public int retrieveData(int steps) {
        stepCounts = steps;
        Log.e("FFFFFFFFFFF", String.valueOf(stepCounts));
        return steps;
    }

    public int retrieveAccSteps(int steps) {
        accSteps = steps;

        return accSteps;
    }

    public int getAccSteps() {

        rankRef.child(monthYearPath).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accSteps = dataSnapshot.child("accSteps").getValue(Integer.class);
                retrieveAccSteps(accSteps);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return accSteps;
    }

    public boolean checkExistDate() {

        dayDatePath = String.valueOf(df.longToYearMonthDay(System.currentTimeMillis()));

        Intent intent = new Intent(getApplicationContext(), StepService.class);
        stopService(intent);

        DatabaseReference stepsRef = ref.child(dayDatePath);

        stepsRef.orderByKey().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists() || dataSnapshot.getValue().equals(null)) {
                    StepHistory steps = new StepHistory(0, 0, dayDatePath);
                    ref.child("stepHistory").child(dayDatePath).child(uid).setValue(steps);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return true;
    }

    public boolean checkExistRank() {
        monthYearPath = String.valueOf(df.longToYearMonth(System.currentTimeMillis()));

        DatabaseReference rankStepsRef = rankRef.child(monthYearPath);

        rankStepsRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists() || dataSnapshot.getValue().equals(null)) {
                    StepsRanking rank = new StepsRanking(0, currentUser.getDisplayName(), dataSnapshot.getKey());
                    rankRef.child(monthYearPath).child(uid).setValue(rank);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return true;
    }
}
