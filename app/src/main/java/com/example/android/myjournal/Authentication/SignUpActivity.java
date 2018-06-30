package com.example.android.myjournal.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myjournal.HomepageActivity;
import com.example.android.myjournal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
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
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        TextView userName = headerLayout.findViewById(R.id.sidebarname);
        final TextView semail = headerLayout.findViewById(R.id.sidebaremail);

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






            }});}

            //just refix the whole firebase and ignore my code. i was in a hurry
            private boolean checkEmail (String email){
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }

    private boolean checkPassword (String password){
        return !TextUtils.isEmpty(password);
    }

    private boolean checkFields () {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (!checkEmail(email)) {
            emailEditText.setError("Check email");
            return false;
        }
        if (!checkPassword(password)) {
            passwordEditText.setError("Check password !!!");
            return false;
        }
        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this, "User is Signed in", Toast.LENGTH_LONG).show();
        username.setText(currentUser.getDisplayName());
        startActivity(new Intent(SignUpActivity.this, HomepageActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }



}



