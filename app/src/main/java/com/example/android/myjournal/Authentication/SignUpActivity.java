package com.example.android.myjournal.Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myjournal.HomepageActivity;
import com.example.android.myjournal.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    private static final int REQUEST_CCODE_SIGN_IN = 12;

    String googleUserName;
    String googleUserMail;

    private FirebaseAuth.AuthStateListener authStateListener;
    @BindView(R.id.sign_up)
    Button signupBtn;
    @BindView(R.id.email_edit_text)
    TextInputEditText emailEditText;
    @BindView(R.id.password)
    TextInputEditText passwordEditText;
    @BindView(R.id.sign_in)
    Button signupTv;
    @BindView(R.id.username)
    TextInputEditText username;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private static final String TAG = SignUpActivity.class.toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        //check if user has signed in before
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(SignUpActivity.this, HomepageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
        NavigationView navigationView = findViewById(R.id.nav_view);
//        View headerLayout = navigationView.getHeaderView(0);
//        TextView userName = headerLayout.findViewById(R.id.sidebarname);
//        final TextView semail = headerLayout.findViewById(R.id.sidebaremail);

        //TODO: Did not use any of these
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


            }
        });

        SignInButton googleSignInBtn = findViewById(R.id.google_sign_in_btn);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(SignUpActivity.this, "Error signing in", Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                assert connectivityManager != null;
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
                if (!isConnected) {
                    Snackbar.make(v, "Please Connect to Internet", Snackbar.LENGTH_LONG).show();
                } else {
                    signIn();
                }
            }
        });
    }


    //just refix the whole firebase and ignore my code. i was in a hurry

//            private boolean checkEmail (String email){
//                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
//            }
//
//    private boolean checkPassword (String password){
//        return !TextUtils.isEmpty(password);
//    }

//    private boolean checkFields () {
//        String email = emailEditText.getText().toString();
//        String password = passwordEditText.getText().toString();
//
//        if (!checkEmail(email)) {
//            emailEditText.setError("Check email");
//            return false;
//        }
//        if (!checkPassword(password)) {
//            passwordEditText.setError("Check password !!!");
//            return false;
//        }
//        return true;
//    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        signInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(signInIntent, REQUEST_CCODE_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);

//        Toast.makeText(this, "User is Signed in", Toast.LENGTH_LONG).show();
//        username.setText(currentUser.getDisplayName());
        //startActivity(new Intent(SignUpActivity.this, HomepageActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CCODE_SIGN_IN && resultCode == Activity.RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);


            if (result.isSuccess()) {
                //Google signin was successfull, authenticate with firebase
                GoogleSignInAccount account = result.getSignInAccount();

                if (account != null) {
                    googleUserName = account.getDisplayName();
                    googleUserMail = account.getEmail();
                    firebaseAuthWithGoogle(account);
                }
            } else {
                //google signin was unsuccessful
                Toast.makeText(this, "Google sign in was unsuccessful", Toast.LENGTH_LONG).show();
            }


        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });

    }
    @Override
    protected void onPause() {
        if (mAuth != null)
            mAuth.removeAuthStateListener(authStateListener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(authStateListener);
        progressBar.setVisibility(View.GONE);
    }


}



