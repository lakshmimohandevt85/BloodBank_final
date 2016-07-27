package com.sdsu.cs646.lakshmi.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.sdsu.cs646.lakshmi.bloodbank.domain.UserDetails;
import com.sdsu.cs646.lakshmi.bloodbank.util.FirebaseUtill;

import java.util.Map;

public class UserSignUpActivity extends AppCompatActivity
{

    Firebase firebase;
    EditText firstName;
    UserDetails userDetails = new UserDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        Firebase.setAndroidContext(this);

        firebase = FirebaseUtill.getConnection(this);
    }

    public void addUser(View view)
    {

        userDetails.setFirstName(((EditText)findViewById(R.id.first_name)).getText().toString());
        userDetails.setLastName(((EditText) findViewById(R.id.last_name)).getText().toString());
        userDetails.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
        userDetails.setPassword(((EditText) findViewById(R.id.password)).getText().toString());



        firebase.createUser(userDetails.getEmail(), userDetails.getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>()
        {
            @Override
            public void onSuccess(Map<String, Object> result)
            {

                Toast.makeText(getApplicationContext(),userDetails.getFirstName() +" "+ userDetails.getLastName()+ getResources().getString(R.string.added), Toast.LENGTH_LONG).show();

            }
            @Override
            public void onError(FirebaseError firebaseError)
            {

                Toast.makeText(getApplicationContext(),String.valueOf(firebaseError) ,Toast.LENGTH_LONG).show();

            }
        });
    }
}
