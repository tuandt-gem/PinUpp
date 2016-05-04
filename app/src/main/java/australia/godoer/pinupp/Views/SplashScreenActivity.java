package australia.godoer.pinupp.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.parse.ParseUser;

import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean IsNewUser = sharedPref.getBoolean(getString(R.string.preference_file_key), true);
        if(IsNewUser){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.preference_file_key), false);
            editor.commit();
            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
        }else if(ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            intent.putExtra(User.INTENT_KEY, new Gson().toJson(new User(ParseUser.getCurrentUser())));
            startActivity(intent);
            finish();
        }else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
