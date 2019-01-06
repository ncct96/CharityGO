package org.charitygo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.master.glideimageview.GlideImageView;

import org.charitygo.R;
import org.charitygo.model.User;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private EditText Email;
    private EditText Username;
    private EditText ContactNumber;
    private EditText Gender;
    private TextView UsernameView;
    private TextView ContactNumberView;
    private TextView GenderView;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseAuth userInstance = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = userInstance.getCurrentUser();
    private DatabaseReference userData = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());

    private ProgressDialog progressDialog;
    private final static int GET_FROM_GALLERY = 22;

    View inflatedView;
    private DatabaseReference imageRef;
    private StorageReference imageStorage = FirebaseStorage.getInstance().getReference();
    private GlideImageView userProfile;
    private TextView userProfileName;
    private TextView userProfilePoints;
    private TextView userProfileEmail;
    private TextView userProfileNumber;
    private TextView userProfileUsername;
    private TextView userProfilePointsAccu;
    private TextView getUserProfileGender;
    private String url; private String path; private String name; private String points; private String gender; private String email; private String number;
    private Uri uriImg; private Uri selectedImage; private String uploadURL;

    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadImageUpdate(){
        final String photoPath = "images/"+System.currentTimeMillis()+"."+getFileExtension(selectedImage);
        final StorageReference storageImgRef = storageRef.child(photoPath);
        final UploadTask uploadTask = storageImgRef.putFile(selectedImage);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = storageImgRef.putFile(selectedImage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        uploadURL = storageImgRef.getDownloadUrl().toString();
                        return storageImgRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            uploadURL = task.getResult().toString();
                            userData.child("photoURL").setValue(uploadURL);
                            userData.child("photoPath").setValue(photoPath);
                            Toast.makeText(ProfileActivity.this, "Image Changed",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Image Unable To be Upload", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                //Toast.makeText(RegisterActivity.this, "Registering... Please Wait.."+String.format("%f",progress)+"Seconds..", Toast.LENGTH_SHORT).show();
                progressDialog.show();
            }
        });
        imageStorage.child(path).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) { }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            userProfile.setImageURI(selectedImage);
            progressDialog.show();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                uploadImageUpdate();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(ProfileActivity.this);
        progressDialog.setTitle("Making Changes");
        progressDialog.setMessage("Please Wait....");

        userProfile = (GlideImageView) findViewById(R.id.avatarProfile);
        userProfileName = (TextView) findViewById(R.id.user_name);
        userProfilePoints = (TextView) findViewById(R.id.user_points);
        userProfileEmail = (TextView) findViewById(R.id.email);
        userProfileNumber = (TextView) findViewById(R.id.contactnum);
        userProfileUsername = (TextView) findViewById(R.id.username);
        userProfilePointsAccu = (TextView) findViewById(R.id.points);
        getUserProfileGender = (TextView) findViewById(R.id.gender);

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        fab = findViewById(R.id.editProf);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SET THE ICON TO CANCEL
                if(Username.getVisibility() == View.VISIBLE && ContactNumber.getVisibility() == View.VISIBLE && Gender.getVisibility() == View.VISIBLE){
                    //Set Text View to Invisible
                    userProfileUsername.setVisibility(View.VISIBLE);
                    userProfileNumber.setVisibility(View.VISIBLE);
                    getUserProfileGender.setVisibility(View.VISIBLE);

                    //Email.setVisibility(View.GONE);
                    Username.setVisibility(View.GONE);
                    ContactNumber.setVisibility(View.GONE);
                    Gender.setVisibility(View.GONE);
                } else if (userProfileUsername.getVisibility() == View.VISIBLE && userProfileNumber.getVisibility() == View.VISIBLE && getUserProfileGender.getVisibility() == View.VISIBLE){
                    //Set Edit Text to Visible
                    //Email.setVisibility(View.VISIBLE);
                    Username.setVisibility(View.VISIBLE);
                    ContactNumber.setVisibility(View.VISIBLE);
                    Gender.setVisibility(View.VISIBLE);

                    userProfileUsername.setVisibility(View.GONE);
                    userProfileNumber.setVisibility(View.GONE);
                    getUserProfileGender.setVisibility(View.GONE);
                }
            }
        });

        if(currentUser != null){
            imageRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
            //CK CHANGES ON GETTING PICTURE;
            imageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    url = dataSnapshot.child("photoURL").getValue().toString();
                    path = dataSnapshot.child("photoPath").getValue().toString();
                    name = dataSnapshot.child("name").getValue().toString();
                    points = dataSnapshot.child("points").getValue().toString();
                    gender = dataSnapshot.child("gender").getValue().toString();
                    number = dataSnapshot.child("contactNumber").getValue().toString();
                    email = currentUser.getEmail();

                    userProfileName.setText(name);
                    userProfilePoints.setText("Points : "+points);

                    userProfileEmail.setText(email);
                    userProfileNumber.setText(number);
                    getUserProfileGender.setText(gender);
                    userProfileUsername.setText(name);
                    userProfilePointsAccu.setText(points);
                    Glide.with(getApplicationContext()).load(url).into(userProfile);
                    changeUI();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void changeUI(){
        //Get Edit Text
        Username = findViewById(R.id.editUsername);
        ContactNumber = findViewById(R.id.editPhone);
        Gender = findViewById(R.id.editGender);

        //Get Element View
        GenderView = findViewById(R.id.gender);
        UsernameView = findViewById(R.id.username);
        ContactNumberView = findViewById(R.id.contactnum);

        //Get Text View Text
        String PreUsername = userProfileUsername.getText().toString();
        String PreNumber = userProfileNumber.getText().toString();
        String PreGender = getUserProfileGender.getText().toString();

        //Set Edit Text's Text
        Username.setText(PreUsername);
        ContactNumber.setText(PreNumber);
        Gender.setText(PreGender);

        //HERE NEED TO IMPLEMENT DATABASE CHANGES
        Username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!Username.getText().toString().equals(userProfileUsername.getText().toString())){
                    String usernChg = Username.getText().toString();
                    UsernameView.setText(usernChg);
                    if(!usernChg.equals("")){
                        userData.child("name").setValue(usernChg);
                    }
                }
            }
        });
        ContactNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!ContactNumber.getText().toString().equals(userProfileNumber.getText().toString())){
                    String contNChg = ContactNumber.getText().toString();
                    ContactNumberView.setText(contNChg);
                    if(!contNChg.equals("")){
                        userData.child("contactNumber").setValue(contNChg);
                    }
                }
            }
        });
        Gender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!Gender.getText().toString().equals(getUserProfileGender.getText().toString())){
                    String genderChg = Gender.getText().toString();
                    GenderView.setText(genderChg);
                    if(!genderChg.equals("") && (genderChg.equals("Male")|| genderChg.equals("Female"))){
                        userData.child("gender").setValue(genderChg);
                    }
                }
            }
        });
    }
}
