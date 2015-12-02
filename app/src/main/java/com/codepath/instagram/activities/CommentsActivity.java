package com.codepath.instagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Constants;

public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        final Intent intent = getIntent();
        String mediaId = intent.getStringExtra(Constants.INTENT_PAYLOAD_MEDIA_ID);

        Toast.makeText(CommentsActivity.this, "Media " + mediaId + " was clicked!", Toast.LENGTH_SHORT).show();
    }
}
