package com.example.preemptiveoop;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.experiment.ExperimentList;
import com.example.preemptiveoop.experiment.PublishExperiment;
import com.example.preemptiveoop.trial.ExecuteTrial;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BinoExperimentTest {

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
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void testA_openActivity() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void testB_createBinoExp() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
        solo.clickOnButton("Experiment");
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
        solo.clickOnView(solo.getView(R.id.Button_addExp));
        solo.clickOnView(solo.getView(R.id.RadioButton_binomial));
        solo.enterText((EditText) solo.getView(R.id.EditText_description), "TestBinoExp");
        solo.enterText((EditText) solo.getView(R.id.EditText_minNumOfTrials), "4");
        solo.clickOnButton("OK");
        assertTrue(solo.waitForText("TestBinoExp", 1, 2000));
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);
    }

    @Test
    public void testC_createBinoTrial() throws InterruptedException {
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
    }


    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
