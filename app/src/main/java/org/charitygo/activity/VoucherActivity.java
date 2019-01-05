package org.charitygo.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.R;
import org.charitygo.model.Reward;
import org.charitygo.model.User;

public class VoucherActivity extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference voucherRef = ref.child("vouchers");
    private String rewardID;

    Reward reward = new Reward();
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rewardID = getIntent().getStringExtra("EXTRA_ID");
        initializeUIValues();
    }


    protected void initializeUIValues() {
        DatabaseReference userRef = ref.child("users/" + firebaseUser.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                final DatabaseReference rewardRef = ref.child("rewards/" + rewardID);
                rewardRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reward = dataSnapshot.getValue(Reward.class);

                        TextView name = (TextView) findViewById(R.id.voucher_company);
                        name.setText(reward.getCompany());

                        TextView expiry = (TextView) findViewById(R.id.voucher_expiry);
                        String expiryTime;

                        if (reward.getValidFor() < 60)
                            expiryTime = String.valueOf(reward.getValidFor()) + " minutes";
                        else if (reward.getValidFor() < 1440)
                            expiryTime = String.valueOf(reward.getValidFor() / 60) + " hours and " + String.valueOf(reward.getValidFor() % 60) + " minutes";
                        else
                            expiryTime = String.valueOf(reward.getValidFor() / 1440) + " days and " + String.valueOf(reward.getValidFor() % 1440) + " hours";

                        expiry.setText(expiryTime);

                        TextView description = (TextView) findViewById(R.id.voucher_description);
                        description.setText(reward.getLongDescription());

                        Button button = findViewById(R.id.voucher_redeem);
                        button.setText(reward.getPrice() + " points");
                        if (user.getPoints() < reward.getPrice()) {
                            button.setEnabled(false);
                            button.setBackgroundColor(Color.LTGRAY);
                        }
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

    public void confirmRedeem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle("Redeem voucher?");
        builder.setMessage("Once you redeem a voucher, it becomes valid for a limited time only.");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes, redeem!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                voucherRef.push().setValue(new Reward());
                DatabaseReference userRef = ref.child("users/" + firebaseUser.getUid());
                userRef.child("points").setValue(user.getPoints() - reward.getPrice());

                Button button = findViewById(R.id.voucher_redeem);
                button.setText(reward.getCode());
                button.setEnabled(false);
                button.setBackgroundColor(Color.RED);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
