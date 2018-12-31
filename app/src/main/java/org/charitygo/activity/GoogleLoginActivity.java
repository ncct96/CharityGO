package org.charitygo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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

import org.charitygo.R;
import org.charitygo.model.User;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class GoogleLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "GoogleActivity";
    private GoogleSignInClient googleSignClient;
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth fireAuth;
    private FirebaseUser currentUser;
    private FirebaseAuth.AuthStateListener authListener;
    private final FirebaseDatabase instant = FirebaseDatabase.getInstance();
    private DatabaseReference ref = instant.getReference();

    private TextView usernameDisplay; private TextView emailDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Button Listeners
        findViewById(R.id.signInBtn).setOnClickListener(this);
        findViewById(R.id.signOutBtn).setOnClickListener(this);

        //Text View
        usernameDisplay = findViewById(R.id.displayUsername);
        emailDisplay = findViewById(R.id.displayEmail);

        googleSignClient = GoogleSignIn.getClient(this, gso);
        //Start initialize authentication
        fireAuth = FirebaseAuth.getInstance();
    }
    private void signIn() {
        if(currentUser != null){
            Map<String, User> googleUsers = new HashMap<>();
            DatabaseReference userRef = ref.child("users");
            User userClass = new User(currentUser.getDisplayName(), currentUser.getEmail());
            googleUsers.put(userClass.name, userClass);
            userRef.child(currentUser.getUid()).setValue(googleUsers);
            ref.child("users/"+currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        //Username exists in database
                        checkExist = true;
                    }else{
                        checkExist = false;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            Intent signInIntent = googleSignClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    private void signOut() {
        if(currentUser != null){
            // Firebase sign out
            fireAuth.signOut();
            currentUser = null;
            // Google sign out
            googleSignClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateUI(null);
                        }
                    });
        }
    }
    private User user1;
    private boolean checkExist;

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null ) {
            usernameDisplay.setText(user.getDisplayName());
            emailDisplay.setText(user.getEmail());

            usernameDisplay.setVisibility(View.VISIBLE);
            emailDisplay.setVisibility(View.VISIBLE);

            findViewById(R.id.signInBtn).setVisibility(View.GONE);
            findViewById(R.id.signOutBtn).setVisibility(View.VISIBLE);

//            DatabaseReference dataRef = ref.child("users/"+currentUser.getUid());
//            ValueEventListener dataListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.exists()){
//                        //Username exists in database
//                        dataSnapshot.getValue();
//                        checkExist = true;
//                    }else{
//                        checkExist = false;
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            };
//            dataRef.addListenerForSingleValueEvent(dataListener);
            if(checkExist){
                //Redirect to Main Page
                Intent intent = new Intent(this, MainUI.class);
                startActivity(intent);
            }else {
                //Redirect to Register Page
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
        } else {
//            usernameDisplay.setText("Username Signed Out !");
//            emailDisplay.setText("Email Signed Out !");

            usernameDisplay.setVisibility(View.INVISIBLE);
            emailDisplay.setVisibility(View.INVISIBLE);

            findViewById(R.id.signInBtn).setVisibility(View.VISIBLE);
            findViewById(R.id.signOutBtn).setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = fireAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fireAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            currentUser = fireAuth.getCurrentUser();
                            updateUI(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.coordinatorLayout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        //hideProgressDialog();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if( i == R.id.signInBtn){
            signIn();
        }else if (i == R.id.signOutBtn){
            signOut();
        }
    }
}
