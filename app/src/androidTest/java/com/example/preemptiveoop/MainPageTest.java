package com.example.preemptiveoop;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.experiment.ExperimentList;
import com.example.preemptiveoop.scan.ScanCodeActivity;
import com.example.preemptiveoop.user.RetrieveProfileFragment;
import com.example.preemptiveoop.user.UserLogin;
import com.example.preemptiveoop.user.UserProfileFragment;
import com.example.preemptiveoop.user.UserRegister;
import com.example.preemptiveoop.user.model.DeviceId;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for MainActivity with a registered device
 * If device is not registered before running the test, this test will fail
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainPageTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Before
    public void setUp() throws Exception{
        Intent intent = new Intent();
        intent.putExtra(".user", new User("deviceId", "steven", "s@s.com"));
        rule.getActivity().onActivityResult(1, Activity.RESULT_OK, intent);
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        solo.clickOnView(solo.getView(R.id.Button_login));
    }

    @Test
    public void T1_testExperimentButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void T2_testSearchButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_search));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void T3_testScanButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_qrcode));
        solo.assertCurrentActivity("Wrong Activity", ScanCodeActivity.class);
    }

    @Test
    public void T4_testUserProfileButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_usrprofile));
        assertTrue(solo.waitForText("User Profile", 1, 2000));
    }

    @Test
    public void T5_testRetrieveProfileButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_retrieve_profile));
        assertTrue(solo.waitForText("Retrieve User Profile", 1, 2000));
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}