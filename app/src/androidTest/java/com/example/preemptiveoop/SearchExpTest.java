//package com.example.preemptiveoop;
//
//import android.app.Activity;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.rule.ActivityTestRule;
//
//import com.example.preemptiveoop.experiment.DisplayExpHistogram;
//import com.example.preemptiveoop.experiment.DisplayExpStats;
//import com.example.preemptiveoop.experiment.DisplayExpTimePlot;
//import com.example.preemptiveoop.experiment.ExperimentList;
//import com.example.preemptiveoop.experiment.PublishExperiment;
//import com.example.preemptiveoop.post.QuestionListActivity;
//import com.example.preemptiveoop.trial.ExecuteTrial;
//
//import com.example.preemptiveoop.trial.TrialList;
//import com.example.preemptiveoop.user.model.DeviceId;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.robotium.solo.Solo;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//
//import static android.app.PendingIntent.getActivity;
//import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class SearchExpTest {
//
//    private Solo solo;
//
//    @Rule
//    public ActivityTestRule<MainActivity> rule =
//            new ActivityTestRule<>(MainActivity.class,true,true);
//
//    @Before
//    public void setUp() throws Exception{
//        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
//    }
//
//    /**
//     * Gets the Activity
//     * @throws Exception
//     */
//    @Test
//    public void S0_start() throws Exception{
//        Activity activity = rule.getActivity();
//        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
//        solo.clickOnButton("OK");
//        //solo.assertCurrentActivity("Wrong Activity", UserRegister.class);
//        solo.enterText((EditText) solo.getView(R.id.EditText_username), "TestUser");
//        solo.enterText((EditText) solo.getView(R.id.EditText_contact), "TestUser@mock.com");
//        solo.clickOnView(solo.getView(R.id.Button_register));
//        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
//    }
//
//    @Test
//    public void S1_open() throws InterruptedException {
//        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
//        solo.clickOnButton("Login");
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//        solo.clickOnButton("Search");
//        //for future search class
//        //solo.assertCurrentActivity("Wrong Activity", Search.class);
//    }
//    @Test
//    public void S2_operation() throws InterruptedException {
//        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
//        solo.clickOnButton("Login");
//        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);
//        solo.clickOnButton("Search");
//        //solo.assertCurrentActivity("Wrong Activity", Search.class);
//        //solo.enterText((EditText) solo.getView(R.id.et_exp_searchKeyword), "binomial");
//        //solo.clickOnView(solo.getView(R.id.bt_exp_search));
//        //assertTrue(solo.waitForText("exp", 1, 2000));
//        //solo.assertCurrentActivity("Wrong Activity", Search.class);
//    }
//
//
//    @After
//    public void tearDown() throws Exception{
//        solo.finishOpenedActivities();
//
//    }
//
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
//}
