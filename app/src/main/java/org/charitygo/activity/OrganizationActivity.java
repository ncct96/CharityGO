package org.charitygo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.R;
import org.charitygo.adapter.OrganizationAdapter;
import org.charitygo.model.Organization;

import java.util.ArrayList;

public class OrganizationActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private DatabaseReference organizationRef = ref.child("organizations");
    static ArrayList<Organization> organizationList = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initOrganizations();
        createViews();
    }

    public void initOrganizations() {
        //organizations.put("1", new Organization("Soda Foundation", 1, 0, R.drawable.city));

        organizationRef.push().setValue(new Organization("Soda Foundation", "We make soda, lots of soda.\nSoda for the rich, soda for the poor", "01120141336", "weeb@trap.con", "www.google.com", "1234 Banana Road, 11300 Monkey Avenue, Pulau Pinang, Malaysia", 0, R.drawable.city));
        organizationRef.push().setValue(new Organization("Chicken Foundation", "We fight against the cruelty shown by humans against chickens.\nEveryday, 2 million chickens are murdered in cold blood while humans get away scot free.", "01120141336", "weeb@trap.con", "www.google.com", "1234 Banana Road, 11300 Monkey Avenue, Pulau Pinang, Malaysia", 0, R.drawable.city));
        organizationRef.push().setValue(new Organization("Potato Foundation", "Even potatoes are human", "01120141336", "weeb@trap.con", "www.google.com", "1234 Banana Road, 11300 Monkey Avenue, Pulau Pinang, Malaysia", 0, R.drawable.tarc));
        organizationRef.push().setValue(new Organization("Nyanko Foundation", "Nya nya nya nya nya nya nya nya nya.\nNya nya nya nya nya nya nya nya nya nya nya nya nya nya nya nya!", "01120141336", "weeb@trap.con", "www.google.com", "1234 Banana Road, 11300 Monkey Avenue, Pulau Pinang, Malaysia", 0, R.drawable.city));
    }

    protected void createViews() {
        organizationList.clear();

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                } else {
                    setErrorMessage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        organizationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    Organization organization = dataSnapshot1.getValue(Organization.class);
                    organization.setKey(key);
                    organizationList.add(organization);
                }

                RecyclerView list = (RecyclerView) findViewById(R.id.organization_view);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(OrganizationActivity.this);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                list.setLayoutManager(mLinearLayoutManager);
                OrganizationAdapter organizationAdapter = new OrganizationAdapter(context, organizationList);
                list.setAdapter(organizationAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                setErrorMessage();
            }
        });
    }

    private void setErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(OrganizationActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle("Unable to contact server");
        builder.setMessage("Please check your internet settings and try again");
        builder.setCancelable(false);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrganizationActivity.this.finish();
            }
        });
        builder.show();
    }
}
