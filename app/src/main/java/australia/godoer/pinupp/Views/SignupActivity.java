package australia.godoer.pinupp.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // assuming name will be username
        final EditText first_name = (EditText) findViewById(R.id.signup_firstname_edit);
        final EditText last_name = (EditText) findViewById(R.id.signup_lastname_edit);
        final EditText password = (EditText) findViewById(R.id.signup_password_edit);
        final EditText confirm_password = (EditText) findViewById(R.id.signup_confirm_password_edit);
        final EditText email = (EditText) findViewById(R.id.signup_email_edit);

        email.setText(getIntent().getStringExtra(LoginActivity.email_key));
        password.setText(getIntent().getStringExtra(LoginActivity.pass_key));

        TextView login_btn = (TextView) findViewById(R.id.signup_login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_intent = new Intent(getApplicationContext(), LoginActivity.class);
                //assuming email is uname, don't need name in login
//                new_intent.putExtra(LoginActivity.name_key, username.getText());
                new_intent.putExtra(LoginActivity.pass_key, password.getText().toString());
                new_intent.putExtra(LoginActivity.email_key, email.getText().toString());
                startActivity(new_intent);
                finish();
            }
        });

        Button signup_btn = (Button) findViewById(R.id.signup_create_account_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser user = new ParseUser();
                if(password.getText().toString().equals(confirm_password.getText().toString())){
                    user.setUsername(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.put(LoginActivity.PARSE_LOGIN_FIRST_NAME_KEY, first_name.getText().toString());
                    user.put(LoginActivity.PARSE_LOGIN_LAST_NAME_KEY, last_name.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                finish();
                            } else {
                                Helper.showMsg(findViewById(R.id.signup_coordinator_layout),"Signup failed!",getResources().getColor(R.color.warnColor));
                            }
                        }
                    });
                }else{
                    Helper.showMsg(findViewById(R.id.signup_coordinator_layout),"Passwords don't match!", getResources().getColor(R.color.warnColor));
                    confirm_password.setText("");
                }
            }
        });
            }
        }