package org.charitygo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.R;
import org.charitygo.model.Organization;

public class OrganizationInfoActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private Organization organization = new Organization();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_info);

        String organizationID = getIntent().getStringExtra("EXTRA_ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeUIValues(organizationID);
    }

    protected void initializeUIValues(String id) {
        DatabaseReference organizationRef = ref.child("organizations/"+id);

        organizationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                organization = dataSnapshot.getValue(Organization.class);

                TextView name = (TextView) findViewById(R.id.info_name);
                name.setText(organization.getName());

                TextView description = (TextView) findViewById(R.id.info_description);
                description.setText(organization.getDescription());

                TextView address = (TextView) findViewById(R.id.info_address);
                address.setText(organization.getAddress());

                TextView phone = (TextView) findViewById(R.id.info_phone);
                phone.setText(organization.getPhone());

                TextView email = (TextView) findViewById(R.id.info_email);
                email.setText(organization.getEmail());

                ImageView imageView = findViewById(R.id.backdrop);
                imageView.setImageResource(organization.getDrawable());

                FloatingActionButton sendMail = (FloatingActionButton) findViewById(R.id.info_sendMail);
                sendMail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setType("message/rfc822");
                        intent.setData(Uri.parse("mailto:"+organization.getEmail()));
                        startActivity(intent);
                    }
                });

                FloatingActionButton call = (FloatingActionButton) findViewById(R.id.info_call);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+organization.getPhone()));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
