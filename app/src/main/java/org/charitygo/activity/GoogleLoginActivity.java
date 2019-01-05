package org.charitygo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.EmailAuthProvider;
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

public class GoogleLoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "GoogleActivity";
    private GoogleSignInClient googleSignClient;
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth fireAuth;
    private FirebaseUser currentUser;
    private FirebaseAuth.AuthStateListener authListener;
    private final FirebaseDatabase instant = FirebaseDatabase.getInstance();
    private DatabaseReference ref = instant.getReference();
    private DatabaseReference dataRef;

    private TextView usernameDisplay; private TextView emailDisplay;
    private String UID; private boolean checkExist;
    private View progressView; private View loginView; private ProgressBar progressb;

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            //Progress Dialog
            int shortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

            loginView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        progressView = findViewById(R.id.google_login_progress);
        loginView = findViewById(R.id.google_login_scroll);

        //Button Listeners
        findViewById(R.id.signInBtn).setOnClickListener(this);
        findViewById(R.id.signOutBtn).setOnClickListener(this);

        //Text View
        usernameDisplay = findViewById(R.id.displayUsername);
        emailDisplay = findViewById(R.id.displayEmail);

        googleSignClient = GoogleSignIn.getClient(this, gso);

        //Get current uid of user, and add value event listener
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser != null){
                    //Toast.makeText(GoogleLoginActivity.this, "Logged in Successful!", Toast.LENGTH_SHORT).show();
                    UID = currentUser.getUid();
                    dataRef = ref.child("users").child(UID);
                    dataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            showData(dataSnapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        };
    }

    private void showData(DataSnapshot dataSnapshot){
        if(dataSnapshot.exists()){
            checkExist = true;
        }
        if(checkExist && currentUser != null){
            //Redirect to Main Page
            showProgress(true);
            Intent intent = new Intent(this, MainUI.class);
            startActivity(intent);
        }else if (!checkExist && currentUser != null){
            //Redirect to Register Page
            showProgress(true);
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    private void signIn() {
        if(currentUser != null){

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

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null ) {
            usernameDisplay.setText(user.getDisplayName());
            emailDisplay.setText(user.getEmail());

            usernameDisplay.setVisibility(View.VISIBLE);
            emailDisplay.setVisibility(View.VISIBLE);

            findViewById(R.id.signInBtn).setVisibility(View.GONE);
            findViewById(R.id.signOutBtn).setVisibility(View.VISIBLE);
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
        //Start initialize authentication
        fireAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = fireAuth.getInstance().getCurrentUser();
        fireAuth.addAuthStateListener(authListener);

        //Get the last signed in users account
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if(acct != null){
//            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//            currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(GoogleLoginActivity.this, "Reauthenticated.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
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
                            //updateUI(currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.coordinatorLayout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        hideProgressDialog();
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
