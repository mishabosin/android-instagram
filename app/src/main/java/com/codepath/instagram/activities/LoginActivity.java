package com.codepath.instagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codepath.instagram.R;
import com.codepath.instagram.networking.InstagramClient;
import com.codepath.oauth.OAuthLoginActivity;

public class LoginActivity extends OAuthLoginActivity<InstagramClient> {

    Button btnLogin;
    Button btnLogout;

    // This fires once the user is authenticated, or fires immediately
    // if the user is already authenticated.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addClickHandlers();
    }

    private void addClickHandlers() {
        btnLogin = (Button) this.findViewById(R.id.bLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToRest(v);
            }
        });

        btnLogout = (Button) this.findViewById(R.id.bLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    // Fires if the authentication process fails for any reason.
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    // Method to be called to begin the authentication process
    // assuming user is not authenticated.
    // Typically used as an event listener for a button for the user to press.
    public void loginToRest(View view) {
        getClient().connect();
    }

    private void logout() {
        getClient().clearAccessToken();
    }
}
