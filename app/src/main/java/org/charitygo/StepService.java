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
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private String uid = currentUser.getUid();
    private Notification notification;
    private static long timestamp = System.currentTimeMillis();
    private DateFormat df = new DateFormat();
    private String monthYearPath = String.valueOf(df.longToYearMonth(timestamp));
    private String dayDatePath = String.valueOf(df.longToYearMonthDay(timestamp));
    private static int stepCounts, initStepCounts;
    private StepHistory steps;


    public StepService() {
        //stepCounts = stepsRef.
    }


    @Override
    public void onCreate() {
        super.onCreate();
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
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        createNotificationChannel();

        notification = new NotificationCompat.Builder(this, "fg01")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Pedometer Running")
                .setColor(R.drawable.gradient_blue)
                .setColorized(true)
                .setContentText("Total Steps: " + stepCounts)
                .setContentInfo("Total Steps : ")
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(300, notification);

        startForeground(300, notification);

        return START_STICKY;
    }

    public void createNotificationChannel() {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)

        {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
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
        Toast.makeText(this, "Background service starting", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Background service stopping", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

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

        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR)
        {
            stepCounts++;
        }

        notification.number = stepCounts;
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        stepsRef = ref.child("stepHistory");
        stepsRef.child(dayDatePath).child(uid).child("steps").setValue(stepCounts);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public int initSteps() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
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

    public int retrieveData(int steps){
        stepCounts = steps;
        Log.e("FFFFFFFFFFF", String.valueOf(stepCounts));
        return steps;
    }
}
