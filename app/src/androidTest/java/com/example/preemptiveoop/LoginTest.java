package com.example.preemptiveoop;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.user.UserLogin;
import com.example.preemptiveoop.user.UserRegister;
import com.example.preemptiveoop.user.model.DeviceId;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.*;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.*;

/*
* Test class for Login mechanism
* */
public class LoginTest {
    private Solo solo;
    private String existedUserName = "steven";  // this variable may update every time before running the test
    private static String nonExistedUserName = "test#1";
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Test the app start and get the activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /*
    * Test the login mechanism for new user
    * Test the new user input a existed user name
    * Test the new user input a valid user name
    * */
    @Test
    public void firstLogin() {
        solo.assertCurrentActivity("Wrong Activity", UserRegister.class);
        solo.clickOnButton("OK");

        solo.enterText((EditText) solo.getView(R.id.EditText_username), existedUserName);
        solo.enterText((EditText) solo.getView(R.id.EditText_contact), "Test@Test.com");
        solo.clickOnView(solo.getView(R.id.Button_register));

        assertTrue(solo.waitForText("Invalid Username", 1, 2000));
        solo.clickOnButton("OK");

        solo.clearEditText((EditText) solo.getView(R.id.EditText_username));
        solo.clearEditText((EditText) solo.getView(R.id.EditText_contact));
        solo.enterText((EditText) solo.getView(R.id.EditText_username), nonExistedUserName);
        solo.enterText((EditText) solo.getView(R.id.EditText_contact), "Test@Test.com");
        solo.clickOnView(solo.getView(R.id.Button_register));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /*
    * Test the existed user login
    * */
    @Test
    public void secondLogin() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertTrue(solo.waitForText(nonExistedUserName, 1, 2000));
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    @AfterClass
    public static void deleteFireBase() throws Exception{
        FirebaseFirestore.getInstance().collection("Users").document(nonExistedUserName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        return;
                    }
                });

    }
}
