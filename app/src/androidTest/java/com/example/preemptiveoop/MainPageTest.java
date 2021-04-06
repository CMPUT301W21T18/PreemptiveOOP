package com.example.preemptiveoop;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.user.model.DeviceId;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Current Device ID NOT IN FIREBASE
 * TestID4Repeat MUST BE IN FIREBASE
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    public void T0_start() throws Exception{
        Activity activity = rule.getActivity();
    }


    @Test
    public void T1_checkInvalid1() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        if(!DevIsRegisted){
            solo.clickOnButton("OK");
            //solo.assertCurrentActivity("Wrong Activity", UserRegister.class);
            solo.enterText((EditText) solo.getView(R.id.EditText_username), "TestID4Repeat");
            solo.enterText((EditText) solo.getView(R.id.EditText_contact), "TestID4Repeat");
            solo.clickOnView(solo.getView(R.id.Button_register));
            solo.clickOnButton("OK");
        } else {
            solo.clickOnButton(R.id.Button_login);
        }
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    public void T2_checkInvalid2() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        if(!DevIsRegisted){
            solo.clickOnButton("OK");
            //solo.assertCurrentActivity("Wrong Activity", UserRegister.class);
            solo.enterText((EditText) solo.getView(R.id.EditText_username), "");
            solo.enterText((EditText) solo.getView(R.id.EditText_contact), "");
            solo.clickOnView(solo.getView(R.id.Button_register));
            solo.clickOnButton("OK");
        } else {
            solo.clickOnButton(R.id.Button_login);
        }
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    public void T3_checkDevID() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        if(!DevIsRegisted){
            solo.clickOnButton("OK");
            //solo.assertCurrentActivity("Wrong Activity", UserRegister.class);
            solo.enterText((EditText) solo.getView(R.id.EditText_username), "TestUser");
            solo.enterText((EditText) solo.getView(R.id.EditText_contact), "TestUser@mock.com");
            solo.clickOnView(solo.getView(R.id.Button_register));
            DevIsRegisted = true;
        } else {
            solo.clickOnButton(R.id.Button_login);
        }
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    public void T4_checkLogin() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

    }

    @Test
    public void T5_checkProfile() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("User Profile");
        assertTrue(solo.waitForText("User Profile", 1, 200));
        solo.clearEditText((EditText) solo.getView(R.id.editTextTextEmailAddress));
        solo.enterText((EditText) solo.getView(R.id.editTextTextEmailAddress), "newcontect@sth.com");
        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("User Profile");
        assertTrue(solo.waitForText("newcontect@sth.com", 1, 200));
    }

    @Ignore
    public void T6_retrieveProfile() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Retrieve Profile");
        assertTrue(solo.waitForText("Retrieve", 1, 200));
//        solo.enterText((EditText) solo.getView(R.id.EditText_username), "TestUser");
//        solo.clickOnButton(R.id.Button_retrieve);
//        assertTrue(solo.waitForText("newcontect@sth.com",1,200));
//        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.enterText((EditText) solo.getView(R.id.EditText_username), "user#1");
        solo.clickOnButton(R.id.Button_retrieve);
        assertTrue(solo.waitForText("user#1@gmail.com",1,200));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);


    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    @AfterClass
    public static void deleteFireBase() throws Exception{
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