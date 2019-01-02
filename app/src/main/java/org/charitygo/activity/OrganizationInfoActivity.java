package org.charitygo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_info);

        String organizationID = getIntent().getStringExtra("EXTRA_ID");

        initializeUIValues(organizationID);
        initializeActionWidgets();
    }

    protected void initializeUIValues(String id) {
        DatabaseReference organizationRef = ref.child("organizations/"+id);

        organizationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Organization organization = dataSnapshot.getValue(Organization.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Get info from database here

        TextView background = (TextView) findViewById(R.id.info_text_background);
        background.setText("Founded in 1678 by Richard B. Patterson, this organization has grown to over 50 branches acrross Malaysia.");

        TextView about = (TextView) findViewById(R.id.info_text_about);
        about.setText("Sell stuff, I guess...\nI heard their cookies are nice. :)");

        TextView contact = (TextView) findViewById(R.id.info_text_contact);
        contact.setText("Email: info@mail.noreply.com");
    }

    protected void initializeActionWidgets() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton email = (FloatingActionButton) findViewById(R.id.spon_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:recipient@example.com"));
                startActivity(intent);
            }
        });

        FloatingActionButton call = (FloatingActionButton) findViewById(R.id.info_phone);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
