package com.sdsu.cs646.lakshmi.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.sdsu.cs646.lakshmi.bloodbank.domain.LoginDetails;
import com.sdsu.cs646.lakshmi.bloodbank.util.FirebaseUtill;

public class LoginActivity extends AppCompatActivity
{

    Firebase firebase;
    Context context;
    LoginDetails loginDetails = new LoginDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        firebase = FirebaseUtill.getConnection(this);
    }

    public void signUp(View view)
    {
        Intent intent = new Intent(this,UserSignUpActivity.class);
        startActivity(intent);
    }

    public void login(View view)
    {
        loginDetails.setEmail(((EditText) findViewById(R.id.user_email)).getText().toString());
        loginDetails.setPassword(((EditText) findViewById(R.id.user_password)).getText().toString());
        firebase.authWithPassword(loginDetails.getEmail(), loginDetails.getPassword(), new Firebase.AuthResultHandler()
        {
            @Override
            public void onAuthenticated(AuthData authData)
            {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                Log.d(getResources().getString(R.string.success), authData.getUid());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError)
            {
                Log.d(getResources().getString(R.string.error), firebaseError.toString());
                Toast.makeText(getApplicationContext(), String.valueOf(firebaseError), Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * method to hide the keyboard when clicked outside editTextField
     * @param event
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}
