package org.charitygo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import java.net.InetAddress;
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

    private SignInButton googleSignIn;
    private TextView usernameDisplay; private TextView emailDisplay;
    private String UID; private boolean checkExist;
    private View progressView; private View loginView; private ProgressDialog progressDialog;

    public boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternatAvailable(){
        try{
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        }catch(Exception e){
            return false;
        }
    }

    public Dialog buildDialog (Context c){
        AlertDialog.Builder build = new AlertDialog.Builder(c);
        build.setTitle("No Internet Connection");
        build.setMessage("This app require internet. \nPlease retry your connections.");
        build.setCancelable(false);
        build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        Dialog dialog = build.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
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

        googleSignIn = findViewById(R.id.signInBtn);
        setGooglePlusButtonText(googleSignIn, "Sign In With Google");

        //Set Progress Dialog
        progressDialog = new ProgressDialog(GoogleLoginActivity.this);
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please wait for a moment");
        progressDialog.setCancelable(false); progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        //Button Listeners
        findViewById(R.id.signInBtn).setOnClickListener(this);

        googleSignClient = GoogleSignIn.getClient(this, gso);

        //Get current uid of user, and add value event listener
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser != null){
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
            progressDialog.dismiss();
            Intent intent = new Intent(this, MainUI.class);
            startActivity(intent);
        }else if (!checkExist && currentUser != null){
            //Redirect to Register Page
            progressDialog.dismiss();
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

    @Override
    public void onStart() {
        super.onStart();
        fireAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = fireAuth.getInstance().getCurrentUser();
        fireAuth.addAuthStateListener(authListener);
        if(!isNetworkConnected() && !isInternatAvailable()){
            buildDialog(GoogleLoginActivity.this).show();
        }else{
            if(currentUser != null){
                //Start initialize authentication
                progressDialog.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.show();
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
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.coordinatorLayout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
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
        }
    }
}
