package com.example.preemptiveoop;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.user.activity.UserLogin;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainPageTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainPage> rule =
            new ActivityTestRule<>(MainPage.class,true,true);

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
    public void checkLogin() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", UserLogin.class);

        solo.enterText((EditText) solo.getView(R.id.EditText_username), "user#1");
        solo.enterText((EditText) solo.getView(R.id.EditText_password), "user#1");
        //        solo.clickOnButton("ADD CITY");
        solo.clickOnView(solo.getView(R.id.Button_login));
        solo.wait(2000);
        MainActivity activity = (MainActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Main_page Activity",MainPage.class);

    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}
