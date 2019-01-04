package org.charitygo.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.charitygo.R;
import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText Email;
    private EditText Username;
    private EditText ContactNumber;
    private EditText Gender;
    private TextView EmailView;
    private TextView UsernameView;
    private TextView ContactNumberView;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseAuth userInstance = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = userInstance.getCurrentUser();

    private DatabaseReference imageRef;
    private StorageReference imageStorage = FirebaseStorage.getInstance().getReference().child("users");
    private ImageView userProfile;
    private TextView userProfileName;
    private TextView userProfilePoints;
    private TextView userProfileEmail;
    private TextView userProfileNumber;
    private TextView userProfileUsername;
    private TextView userProfilePointsAccu;
    private TextView getUserProfileGender;
    private String path = "images/1546614251952.jpg"; private String name; private String points; private String gender; private String email; private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userProfile = (ImageView) findViewById(R.id.avatarProfile);
        userProfileName = (TextView) findViewById(R.id.user_name);
        userProfilePoints = (TextView) findViewById(R.id.user_points);
        userProfileEmail = (TextView) findViewById(R.id.email);
        userProfileNumber = (TextView) findViewById(R.id.contactnum);
        userProfileUsername = (TextView) findViewById(R.id.username);
        userProfilePointsAccu = (TextView) findViewById(R.id.points);
        getUserProfileGender = (TextView) findViewById(R.id.gender);

        if(currentUser != null){
            imageRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
            //CK CHANGES ON GETTING PICTURE;
            imageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if(ds.exists()){
                            path = dataSnapshot.child("photoID").getValue().toString();
                            name = dataSnapshot.child("name").getValue().toString();
                            points = dataSnapshot.child("points").getValue().toString();
                            gender = dataSnapshot.child("gender").getValue().toString();
                            number = dataSnapshot.child("contactNumber").getValue().toString();
                            email = currentUser.getEmail();

                            userProfileName.setText(name);
                            userProfilePoints.setText(points);
                            userProfileEmail.setText(email);
                            userProfileNumber.setText(number);
                            getUserProfileGender.setText(gender);

                            userProfileUsername.setText(name);
                            userProfilePointsAccu.setText(points);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            imageStorage.child(currentUser.getUid()).child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    userProfile.setImageURI(uri);
                    userProfile.invalidate();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        //DO STORE

        //Get Edit Text
        Email = findViewById(R.id.editEmail);
        Username = findViewById(R.id.editUsername);
        ContactNumber = findViewById(R.id.editPhone);
        Gender = findViewById(R.id.editGender);

//        //Get Text View
//        EmailView = findViewById(R.id.email);
//        UsernameView = findViewById(R.id.username);
//        ContactNumberView = findViewById(R.id.contactnum);

        //Get Text View Text
        String PreEmail = userProfileEmail.getText().toString();
        String PreUsername = userProfileUsername.getText().toString();
        String PreNumber = userProfileNumber.getText().toString();
        String PreGender = getUserProfileGender.getText().toString();

        //Set Edit Text's Text
        Email.setText(PreEmail);
        Username.setText(PreUsername);
        ContactNumber.setText(PreNumber);
        Gender.setText(PreGender);

        fab = findViewById(R.id.editProf);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SET THE ICON TO CANCEL
                if(Email.getVisibility() == View.VISIBLE && Username.getVisibility() == View.VISIBLE && ContactNumber.getVisibility() == View.VISIBLE && Gender.getVisibility() == View.VISIBLE){
                    //Set Text View to Invisible
                    userProfileEmail.setVisibility(View.VISIBLE);
                    userProfileUsername.setVisibility(View.VISIBLE);
                    userProfileNumber.setVisibility(View.VISIBLE);
                    getUserProfileGender.setVisibility(View.VISIBLE);

                    Email.setVisibility(View.GONE);
                    Username.setVisibility(View.GONE);
                    ContactNumber.setVisibility(View.GONE);
                    Gender.setVisibility(View.GONE);
                } else if (userProfileEmail.getVisibility() == View.VISIBLE && userProfileUsername.getVisibility() == View.VISIBLE && userProfileNumber.getVisibility() == View.VISIBLE && getUserProfileGender.getVisibility() == View.VISIBLE){
                    //Set Edit Text to Visible
                    Email.setVisibility(View.VISIBLE);
                    Username.setVisibility(View.VISIBLE);
                    ContactNumber.setVisibility(View.VISIBLE);
                    Gender.setVisibility(View.VISIBLE);

                    userProfileEmail.setVisibility(View.GONE);
                    userProfileUsername.setVisibility(View.GONE);
                    userProfileNumber.setVisibility(View.GONE);
                    getUserProfileGender.setVisibility(View.GONE);
                }
            }
        });

        //HERE NEED TO IMPLEMENT DATABASE CHANGES
        Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(Email.getText().toString().equals(null)){
                    String emailChg = Email.getText().toString();
                    EmailView.setText(emailChg);
                }
            }
        });
        Username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(Username.getText().toString().equals(null)){
                    String usernChg = Username.getText().toString();
                    UsernameView.setText(usernChg);
                }
            }
        });
        ContactNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(ContactNumber.getText().toString().equals(null)){
                    String contNChg = ContactNumber.getText().toString();
                    ContactNumberView.setText(contNChg);
                }
            }
        });
    }
}
