package australia.godoer.pinupp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String PARSE_LOGIN_FIRST_NAME_KEY = "firstName";
    public static final String PARSE_LOGIN_LAST_NAME_KEY = "lastName";
    public static final String PARSE_LOGIN_PINUPP_USERPROFILE_KEY = "PinuppUser";
    public static final String PARSE_LOGIN_NAME_KEY = "name";
    public static final String name_key = "name";
    public static final String pass_key = "password";
    public static final String email_key = "emailid";
    public User login_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //read intent to warn no user obj obtained from login to home

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Username / email");
        passwordWrapper.setHint("Password");

        final EditText username = (EditText) findViewById(R.id.login_username_edit);
        final EditText password = (EditText) findViewById(R.id.login_password_edit);
        username.setText(getIntent().getStringExtra(email_key));
        password.setText(getIntent().getStringExtra(pass_key));

        Button signup_btn = (Button) findViewById(R.id.login_signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_intent = new Intent(getApplicationContext(), SignupActivity.class);
                // assuming username is email
                new_intent.putExtra(email_key,((EditText) findViewById(R.id.login_username_edit)).getText().toString());
                new_intent.putExtra(pass_key, ((EditText) findViewById(R.id.login_password_edit)).getText().toString());
                startActivity(new_intent);
                finish();
            }
        });

        Button login_btn = (Button) findViewById(R.id.login_login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                //ParseUser.logInInBackground("fa@la.com", "pass", new LogInCallback() {
                        public void done(ParseUser user, ParseException e){
                            if (e == null & user != null) {
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else if(user != null){
                                Helper.showMsg(findViewById(R.id.login_linear_layout), "No Account found! Please Signup", getResources().getColor(R.color.warnColor));
                            }else{
                                Helper.showMsg(findViewById(R.id.login_linear_layout), "Login Failed!",getResources().getColor(R.color.errorColor));
                            }
                        }
                    });
            }
        });
    }
}
