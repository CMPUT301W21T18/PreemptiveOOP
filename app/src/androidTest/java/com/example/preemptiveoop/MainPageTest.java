package com.example.preemptiveoop;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.user.model.DeviceId;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
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
    public void checkInvalid1() {
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
    public void checkInvalid2() {
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
    public void checkLogin() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        solo.clickOnButton("Login");

        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);

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