package com.example.prasanth.socialloginapplication.Sociallogins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prasanth.socialloginapplication.R;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

public class FacebookProfilePage extends AppCompatActivity {
    private ImageView fbProfilePic;
    private TextView fbName, fbGender, fbEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_profile_page);
        intiViews();
        setFbProfileData();
    }

    private void setFbProfileData() {
        Bundle fbUserProfieData = getIntent().getExtras();
        fbName.setText(fbUserProfieData.get("name").toString());
        fbGender.setText(fbUserProfieData.get("gender").toString());
        fbEmail.setText(fbUserProfieData.get("email").toString());
        Picasso.with(this)
                .load("https://graph.facebook.com/" + fbUserProfieData.get("fbUserId") + "/picture?type=large")
                .into(fbProfilePic);

    }

    private void intiViews() {
        fbName = (TextView) findViewById(R.id.fbName);
        fbGender = (TextView) findViewById(R.id.fbGender);
        fbEmail = (TextView) findViewById(R.id.fbEmail);
        fbProfilePic = (ImageView) findViewById(R.id.fbProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                LoginManager.getInstance().logOut();
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
