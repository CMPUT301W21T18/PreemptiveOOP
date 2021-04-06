package com.example.preemptiveoop;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.experiment.DisplayExpHistogram;
import com.example.preemptiveoop.experiment.DisplayExpStats;
import com.example.preemptiveoop.experiment.DisplayExpTimePlot;
import com.example.preemptiveoop.experiment.ExperimentList;
import com.example.preemptiveoop.experiment.PublishExperiment;
import com.example.preemptiveoop.post.QuestionListActivity;
import com.example.preemptiveoop.trial.ExecuteTrial;

import com.example.preemptiveoop.trial.TrialList;
import com.example.preemptiveoop.uiwidget.LocationPicker;
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
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.app.PendingIntent.getActivity;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestExperimentTest {

    private Solo solo;

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
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("OK");
        //solo.assertCurrentActivity("Wrong Activity", UserRegister.class);
        solo.enterText((EditText) solo.getView(R.id.EditText_username), "TestUser2");
        solo.enterText((EditText) solo.getView(R.id.EditText_contact), "TestUser2@mock.com");
        solo.clickOnView(solo.getView(R.id.Button_register));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    public void T1_openActivity() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void T2_CountExpTrial() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickOnView(solo.getView(R.id.Button_addExp));
        solo.clickOnView(solo.getView(R.id.RadioButton_count));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestCountExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "2");
        solo.clickOnView(solo.getView(R.id.Button_pickLocation));
        solo.assertCurrentActivity("Wrong Activity", LocationPicker.class);
        //solo.waitForText("",1,200);
        solo.clickOnView(solo.getView(R.id.Button_currLocation));
        solo.clickOnView(solo.getView(R.id.Button_finish));
        solo.clickOnView(solo.getView(R.id.Button_publish));
        assertTrue(solo.waitForText("TestCountExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);

        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_participate));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_doTrial));
        solo.assertCurrentActivity("Wrong Activity", ExecuteTrial.class);
        solo.clickOnView(solo.getView(R.id.Button_record));
        solo.clickOnView(solo.getView(R.id.Button_record));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_stats));
        solo.waitForActivity(DisplayExpStats.class);
        TextView textView = (TextView) solo.getView(R.id.tv_mean);
        assertEquals("1.00", textView.getText().toString());
    }

    @Test
    public void T3_MeasExpTrial() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickOnView(solo.getView(R.id.Button_addExp));
        solo.clickOnView(solo.getView(R.id.RadioButton_measurement));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestMeasurementExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "2");
        solo.clickOnView(solo.getView(R.id.Button_pickLocation));
        solo.assertCurrentActivity("Wrong Activity", LocationPicker.class);
        solo.clickOnView(solo.getView(R.id.Button_currLocation));
        solo.clickOnView(solo.getView(R.id.Button_finish));
        solo.clickOnView(solo.getView(R.id.Button_publish));
        assertTrue(solo.waitForText("TestMeasurementExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);

        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_participate));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_doTrial));
        solo.assertCurrentActivity("Wrong Activity", ExecuteTrial.class);

        solo.enterText((EditText) solo.getView(R.id.EditText_result), "3.2");
        solo.clickOnView(solo.getView(R.id.Button_record));
        solo.clearEditText((EditText) solo.getView(R.id.EditText_result));
        solo.enterText((EditText) solo.getView(R.id.EditText_result), "3.2");
        solo.clickOnView(solo.getView(R.id.Button_record));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_stats));
        solo.waitForActivity(DisplayExpStats.class);
        TextView textView = (TextView) solo.getView(R.id.tv_mean);
        assertEquals("3.20", textView.getText().toString());
    }

    @Test
    public void T4_NonNExpTrial() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickOnView(solo.getView(R.id.Button_addExp));
        solo.clickOnView(solo.getView(R.id.RadioButton_nonnegative));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestNonNExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "2");
        solo.clickOnView(solo.getView(R.id.Button_pickLocation));
        solo.assertCurrentActivity("Wrong Activity", LocationPicker.class);
        solo.clickOnView(solo.getView(R.id.Button_currLocation));
        solo.clickOnView(solo.getView(R.id.Button_finish));
        solo.clickOnView(solo.getView(R.id.Button_publish));
        assertTrue(solo.waitForText("TestNonNExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_participate));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_doTrial));
        solo.assertCurrentActivity("Wrong Activity", ExecuteTrial.class);

        solo.enterText((EditText) solo.getView(R.id.EditText_result), "3");
        solo.clickOnView(solo.getView(R.id.Button_record));
        solo.clearEditText((EditText) solo.getView(R.id.EditText_result));
        solo.enterText((EditText) solo.getView(R.id.EditText_result), "3.2");
        solo.clickOnView(solo.getView(R.id.Button_record));
        assertTrue(solo.waitForText("Invalid Result", 1, 2000));
        solo.clickOnButton("OK");
        solo.clearEditText((EditText) solo.getView(R.id.EditText_result));
        solo.enterText((EditText) solo.getView(R.id.EditText_result), "3");
        solo.clickOnView(solo.getView(R.id.Button_record));
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_stats));
        solo.waitForActivity(DisplayExpStats.class);
        TextView textView = (TextView) solo.getView(R.id.tv_mean);
        assertEquals("3.00", textView.getText().toString());
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();

    }

    @AfterClass
    public static void deleteFireBase() throws Exception{
        String deviceId = DeviceId.getDeviceId(getApplicationContext());
        FirebaseFirestore.getInstance().collection("Users").document("TestUser2")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        return;
                    }
                });
    }
}
