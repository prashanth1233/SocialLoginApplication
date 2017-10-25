package com.example.prasanth.socialloginapplication.Sociallogins;

import android.content.Context;
import android.widget.Toast;

import com.example.prasanth.socialloginapplication.MainActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import io.fabric.sdk.android.Fabric;


public class TwitterSignIn {

    private static String TWITTER_CONSUMER_KEY = "TkvV9knHcROXTAm8kRIjJ6qYa";
    private static String TWITTER_CONSUMER_SECRET = "M31sNOwp0KXcSASY3SYlZeyjRAa9XAFyLfssaX0QXChVVc4uUM";
    private TwitterAuthClient twitterAuthClient;
    private Context context;

    public TwitterSignIn(Context context) {
        this.context = context;
         /*Authorization configuration details*/
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);

        /*Fabric SDK separates functionality into modules called Kits.
        You must indicate which kits you wish to use via Fabric.with().*/

        Fabric.with(context, new com.twitter.sdk.android.Twitter(authConfig));


    }

    public void initTwitter() {

        twitterAuthClient = new TwitterAuthClient();
        //make the call to login
        twitterAuthClient.authorize((MainActivity) context, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //feedback
                result.data.getUserName();
                Toast.makeText(context, "Login worked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(com.twitter.sdk.android.core.TwitterException exception) {
                Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();
            }

        });
    }
}
