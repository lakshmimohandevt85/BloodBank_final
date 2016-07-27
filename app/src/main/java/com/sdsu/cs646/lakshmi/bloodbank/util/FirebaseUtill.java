package com.sdsu.cs646.lakshmi.bloodbank.util;

import android.content.Context;

import com.firebase.client.Firebase;
import com.sdsu.cs646.lakshmi.bloodbank.R;

/**
 * Created by lakshmimohandev on 5/10/16.
 */
public class FirebaseUtill
{

    static Firebase firebase;
    static String firebase_url = "https://androidbloodbank.firebaseio.com";
    public static Firebase getConnection(Context ctx)
    {

        if(firebase == null)
        {

            firebase = new Firebase(firebase_url);

            return firebase;
        }
        else
            return firebase;
    }
}
