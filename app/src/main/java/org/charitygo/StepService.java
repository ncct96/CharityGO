package org.charitygo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.IBinder;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    DatabaseReference stepsRef = mFirebase.getReference("stepsHistory");
    private User user;
    private int stepCounts = 0;

    public StepService() {
        //stepCounts = stepsRef.
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initUser();
        mSensorManager = (SensorManager)
                this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
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


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Background service starting", Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }

    private TriggerEventListener Listener = new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {

        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference stepsRef = ref.child("stepsHistory/users/" + uid);

        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCounts++;
        }

        StepHistory steps = new StepHistory(user, new Date(), new Date(), stepCounts, 1000);

        stepsRef.setValue(steps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
