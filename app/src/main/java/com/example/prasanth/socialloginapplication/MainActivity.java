package com.example.prasanth.socialloginapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.prasanth.socialloginapplication.Sociallogins.FacebookLogin;
import com.example.prasanth.socialloginapplication.Sociallogins.FacebookProfilePage;
import com.example.prasanth.socialloginapplication.Sociallogins.GoogleSignIn;
import com.example.prasanth.socialloginapplication.Sociallogins.TwitterSignIn;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FbPageNavigateInterface {

    private Button google_sign_in_button, facebook_sign_in_button, twitter_sign_in_button;
    private GoogleApiClient googleApiClient;
    private final int GOOGLE_SIGN_IN_REQUEST_CODE = 11011;
    private String userName, userEmail;
    private Intent signInIntent;
    private boolean googleSignInStatus = false;
    private boolean twitter_button_click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListeners();
    }


    private void initViews() {
        google_sign_in_button = (Button) findViewById(R.id.google_sign_in_button);
        facebook_sign_in_button = (Button) findViewById(R.id.facebook_sign_in_button);
        twitter_sign_in_button = (Button) findViewById(R.id.twitter_sign_in_button);
    }

    private void setListeners() {
        google_sign_in_button.setOnClickListener(this);
        facebook_sign_in_button.setOnClickListener(this);
        twitter_sign_in_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google_sign_in_button:
                GoogleSignIn googleSignIn = new GoogleSignIn();

                /*checking for first time signIn*/
                if (!googleSignInStatus) {
                    if (googleApiClient == null) {
                        googleApiClient = googleSignIn.initGooglePlus(this);
                        signUpWithGoogle();
                    } else {
                        signUpWithGoogle();
                    }
                } else {
                    Toast.makeText(this, "You are already Loggiedin", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.facebook_sign_in_button:
                FacebookLogin facebookLogin = new FacebookLogin(this, this);
                facebookLogin.initFaceBook();
                facebookLogin.signUpWithFacebook();
                break;
            case R.id.twitter_sign_in_button:
                twitter_button_click = true;
                TwitterSignIn twitterSignIn = new TwitterSignIn(this);
                twitterSignIn.initTwitter();
                break;
            default:
                break;
        }
    }


    public void signUpWithGoogle() {
        /*Starting the intent prompts the user to select a Google account to sign in with*/
        signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent()
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            GoogleSignInResult signInresult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(signInresult);
        }
        /*twitter_button_click is used to check whether twitter signin button is clicked or not*/
        if (twitter_button_click)
            if (new TwitterAuthClient().getRequestCode() == requestCode) {
                new TwitterAuthClient().onActivityResult(requestCode, resultCode, data);
            }
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            FacebookLogin.fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleSignInResult(GoogleSignInResult signInresult) {
        if (signInresult.isSuccess()) {
            // Signed in successfully
            googleSignInStatus = true;
            GoogleSignInAccount googleSignInAccount = signInresult.getSignInAccount();
            userName = googleSignInAccount.getDisplayName();

            /*We can get UserDetails here using googleSignInAccount*/
            /*ex: googleSignInAccount.getDisplayName();*/
        }
    }

    @Override
    public void navigateToFbProfilePage(Bundle bundle) {
        Intent fbProfilePage = new Intent(this, FacebookProfilePage.class);
        fbProfilePage.putExtras(bundle);
        startActivity(fbProfilePage);
    }
}