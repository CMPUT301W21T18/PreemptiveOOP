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
import com.example.preemptiveoop.user.model.DeviceId;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
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
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void T2_createRestExp() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickOnView(solo.getView(R.id.Button_addExp));
        solo.clickOnView(solo.getView(R.id.RadioButton_count));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestCountExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "4");
        solo.clickOnButton("OK");
        assertTrue(solo.waitForText("TestCountExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);



        solo.clickOnView(solo.getView(R.id.Button_addExp));
        solo.clickOnView(solo.getView(R.id.RadioButton_measurement));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestMeasurementExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "4");
        solo.clickOnButton("OK");
        assertTrue(solo.waitForText("TestMeasurementExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);



        solo.clickOnView(solo.getView(R.id.Button_addExp));
        solo.clickOnView(solo.getView(R.id.RadioButton_nonnegative));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestNonnegativeExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "4");
        solo.clickOnButton("OK");
        assertTrue(solo.waitForText("TestNonnegativeExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);


    }

    @Test
    public void T3_createCountTrial() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnButton("Participate Experiment");
        solo.clickInList(1);
        solo.clickOnButton("Record a Trial");
        solo.assertCurrentActivity("Wrong Activity", ExecuteTrial.class);
        solo.clickOnButton("Success");
        solo.clickOnButton("Failure");
        solo.clickOnButton("Success");
        solo.clickOnButton("Failure");
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);

        solo.clickInList(2);
        solo.clickOnButton("Participate Experiment");
        solo.clickInList(2);
        solo.clickOnButton("Record a Trial");
        solo.assertCurrentActivity("Wrong Activity", ExecuteTrial.class);
        solo.clickOnButton("Success");
        solo.clickOnButton("Failure");
        solo.clickOnButton("Success");
        solo.clickOnButton("Failure");
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);

        solo.clickInList(3);
        solo.clickOnButton("Participate Experiment");
        solo.clickInList(3);
        solo.clickOnButton("Record a Trial");
        solo.assertCurrentActivity("Wrong Activity", ExecuteTrial.class);
        solo.clickOnButton("Success");
        solo.clickOnButton("Failure");
        solo.clickOnButton("Success");
        solo.clickOnButton("Failure");
        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);




    }

    @Test
    public void T4_modify() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnButton("View Trials");
        solo.assertCurrentActivity("Wrong Activity", TrialList.class);
        solo.clickInList(1);
        solo.waitForText("true", 1, 200);
        TextView textView = (TextView) solo.getView(R.id.TextView_isIgnored);
        assertEquals("Ignored: true", textView.getText().toString());
    }




    @Test
    public void T5_modify2() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnButton("Stats for Experiment");
        solo.waitForActivity(DisplayExpStats.class);
        //solo.assertCurrentActivity("Wrong Activity", DisplayExpStats.class);
        TextView textView = (TextView) solo.getView(R.id.tv_mean);
        assertEquals("0.67", textView.getText().toString());
        solo.clickOnButton("View Histogram");
        solo.assertCurrentActivity("Wrong Activity", DisplayExpHistogram.class);
        solo.goBackToActivity("DisplayExpStats");
        solo.clickOnButton("View Time Plot");
        solo.assertCurrentActivity("Wrong Activity", DisplayExpTimePlot.class);

    }

    @Test
    public void T6_modify3() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnButton("End Experiment");
        solo.waitForText("ended", 1, 200);
        TextView textView = (TextView) solo.getView(R.id.TextView_status);
        assertEquals("Status: ended", textView.getText().toString());
        solo.clickInList(1);
        solo.clickOnButton("Unpublish Experiment");
        solo.waitForText("unpublish", 1, 200);
        assertEquals("Status: unpublished", textView.getText().toString());
    }

    @Test
    public void T7_addQuestion() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnButton("View Questions");
        solo.clickOnView(solo.getView(R.id.Button_post_question));
        solo.enterText((EditText) solo.getView(R.id.EditText_question_title), "TestQuesTitle");
        solo.enterText((EditText) solo.getView(R.id.EditText_question_body), "mock Question body");
        solo.clickOnButton("Confirm");
        assertTrue(solo.waitForText("TestQuesTitle", 1, 200));
        solo.assertCurrentActivity("Wrong Activity", QuestionListActivity.class);
    }

    @Test
    public void T8_replyQuestion() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnButton("View Questions");
        assertTrue(solo.waitForText("TestQuesTitle", 1, 200));
        solo.clickInList(1);
        solo.clickOnButton("Reply Question");
        solo.enterText((EditText) solo.getView(R.id.EditText_question_title), "TestReplyTitle");
        solo.enterText((EditText) solo.getView(R.id.EditText_question_body), "mock Reply body");
        solo.clickOnButton("Confirm");
        //assertTrue(solo.waitForText("Replies: 1", 1, 200);
        solo.assertCurrentActivity("Wrong Activity", QuestionListActivity.class);
    }

    @Test
    public void T9_replyQuestion() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickInList(1);
        solo.clickOnButton("View Questions");
        assertTrue(solo.waitForText("TestQuesTitle", 1, 200));
        solo.clickInList(1);
        solo.clickOnButton("View All Replies");
        assertTrue(solo.waitForText("Reply List", 1, 200));
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
