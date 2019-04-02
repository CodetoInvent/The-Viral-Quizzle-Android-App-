package com.dream.malik.theviralquizzle; /*
  Created by malik on 4/22/2018.
 */

        import com.google.firebase.iid.FirebaseInstanceId;

public class MyFirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();



    }

    private void sendRegistrationToServer(String token) {
        //we can implement this method to store the token on your server
        //Not required for current project
    }
}