package com.example.preemptiveoop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.UserLogin;
import com.example.preemptiveoop.user.UserRegister;
import com.example.preemptiveoop.user.model.DeviceId;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
public class MainPageTest {
    private Solo solo;
    private boolean DevIsRegisted = false;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void checkDevID() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        if (!DevIsRegisted){
            solo.assertCurrentActivity("Wrong Activity", UserRegister.class);
            solo.clickOnButton("OK");
            solo.enterText((EditText) solo.getView(R.id.EditText_username), "TestUser");
            solo.enterText((EditText) solo.getView(R.id.EditText_contact), "TestUser@mock.com");
            solo.clickOnView(solo.getView(R.id.Button_register));
            DevIsRegisted = true;
            solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        } else {
            solo.clickOnButton(R.id.Button_login);
            solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        }
    }

    @Test
    public void checkLogin() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
        // perform query


    }

    @AfterClass
    public static void deletFireBase() throws Exception{
        String deviceId = DeviceId.getDeviceId(getApplicationContext());
        FirebaseFirestore.getInstance().collection("Users").document("TestUser")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        return;
                    }
                });

    }


}