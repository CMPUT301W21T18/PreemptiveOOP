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
import com.google.firebase.firestore.IgnoreExtraProperties;
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


/**
 * This test case is for Binomial Experiment creation and operation
 * */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BinoExperimentTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        solo.clickOnView(solo.getView(R.id.Button_login));
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void T1_start() throws Exception{
        Activity activity = rule.getActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void T2_createBinoExp()  {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_experiment));

        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickOnView(solo.getView(R.id.Button_addExp));

        solo.clickOnView(solo.getView(R.id.RadioButton_binomial));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestBinoExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "4");
        solo.clickOnView(solo.getView(R.id.Button_pickLocation));
        solo.assertCurrentActivity("Wrong Activity", LocationPicker.class);
        solo.clickOnView(solo.getView(R.id.Button_currLocation));
        solo.clickOnView(solo.getView(R.id.Button_finish));
        solo.clickOnView(solo.getView(R.id.Button_publish));
        assertTrue(solo.waitForText("TestBinoExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void T3_createBinoTrial() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id. Button_participate));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id. Button_doTrial));
        solo.assertCurrentActivity("Wrong Activity", ExecuteTrial.class);
        solo.clickOnView(solo.getView(R.id.Button_success));
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.Button_success));
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.Button_failure));
        solo.clickOnButton("OK");
        solo.clickOnView(solo.getView(R.id.Button_failure));
        solo.clickOnButton("OK");
        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
       // assertTrue(solo.waitForText("4/4", 1,200));
    }

    @Test
    public void T4_testTrialIgnore() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id. Button_trials));
        solo.assertCurrentActivity("Wrong Activity", TrialList.class);
        solo.clickInList(1);
        solo.waitForText("Ignored: true", 1, 200);
    }




    @Test
    public void T5_testTrialStat() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_stats));
        solo.waitForActivity(DisplayExpStats.class);
        //solo.assertCurrentActivity("Wrong Activity", DisplayExpStats.class);
        TextView textView = (TextView) solo.getView(R.id.tv_mean);
        assertEquals("0.67", textView.getText().toString());
        solo.clickOnView(solo.getView(R.id.Button_histogram));
        solo.assertCurrentActivity("Wrong Activity", DisplayExpHistogram.class);
        solo.goBackToActivity("DisplayExpStats");
        solo.clickOnView(solo.getView(R.id.Button_timePlot));
        solo.assertCurrentActivity("Wrong Activity", DisplayExpTimePlot.class);

    }

    @Test
    public void T6_testExpStatus() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_endExp));
        solo.waitForText("ended", 1, 200);
        solo.waitForText("Status: ended", 1, 200);
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_unpublish));
        solo.waitForText("Status: unpublish", 1, 200);
    }

    @Test
    public void T7_testAddQuestion() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_view_question));
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
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_view_question));
        assertTrue(solo.waitForText("TestQuesTitle", 1, 200));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_add_reply));
        solo.enterText((EditText) solo.getView(R.id.EditText_question_title), "TestReplyTitle");
        solo.enterText((EditText) solo.getView(R.id.EditText_question_body), "mock Reply body");
        solo.clickOnButton("Confirm");
        //assertTrue(solo.waitForText("Replies: 1", 1, 200);
        solo.assertCurrentActivity("Wrong Activity", QuestionListActivity.class);
    }

    @Test
    public void T9_replyQuestion() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.clickOnView(solo.getView(R.id.Button_experiment));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_view_question));
        assertTrue(solo.waitForText("TestQuesTitle", 1, 200));
        solo.clickInList(1);
        solo.clickOnView(solo.getView(R.id.Button_view_reply));
        assertTrue(solo.waitForText("Reply List", 1, 200));
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();

    }

//    @AfterClass
//    public static void deleteFireBase() throws Exception{
//        String deviceId = DeviceId.getDeviceId(getApplicationContext());
//        FirebaseFirestore.getInstance().collection("Users").document("TestUser")
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        return;
//                    }
//                });
//
//    }
}
