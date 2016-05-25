package com.studio.nitz.pollpeia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private LoginButton login_button;
    public static CallbackManager mCallbackManager;
    private ImageView imageProfile;

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Log.d("AccessToken", accessToken.getToken());
            Profile profile = Profile.getCurrentProfile();
            URL image_view;
            try {
                image_view = new URL("http://graph.facebook.com/"+profile.getId()+"/picture?type=large");
                Bitmap mIcon = BitmapFactory.decodeStream(image_view.openConnection().getInputStream());
                imageProfile.setImageBitmap(mIcon);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        login_button = (LoginButton) findViewById(R.id.login_button);
        login_button.setReadPermissions();
        login_button.registerCallback(mCallbackManager,mCallback);


        //Parse.initialize(this, "PUsAXgvXTYEse5aFULL2t3t74rmDBPRWOdQ4ttHK", "oabKaBebWVbpelRtPkKjOH9cPVtOxz8i5cDDJHv9");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.getInBackground("lrskABpHSs", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), "No Data fetched from server", Toast.LENGTH_SHORT).show();
                } else {
                    ParseObject questionObj = null;
                    try {
                        questionObj = query.get("lrskABpHSs");
                        String question = questionObj.getString("question");
                        boolean answerA = questionObj.getBoolean("option_a");
                        boolean answerB = questionObj.getBoolean("option_b");
                        //   Toast.makeText(getApplicationContext(), question, Toast.LENGTH_LONG).show();
                    } catch (com.parse.ParseException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
