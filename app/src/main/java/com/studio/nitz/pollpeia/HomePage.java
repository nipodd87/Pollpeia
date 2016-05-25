package com.studio.nitz.pollpeia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.facebook.Profile;

/**
 * Created by nitinpoddar on 12/21/15.
 */
public class HomePage extends ActionBarActivity {
    private ImageView imageProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        Profile profile = Profile.getCurrentProfile();
      /*  Intent intent = getIntent();
        Profile profile = intent.getParcelableExtra("profile"); */
        Uri profilePic = profile.getProfilePictureUri(100,100);
        imageProfile.setImageURI(profilePic);
    }
}
