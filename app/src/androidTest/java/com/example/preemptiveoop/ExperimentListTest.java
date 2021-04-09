package com.example.preemptiveoop;

import android.app.Activity;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.preemptiveoop.MainActivity;
import com.example.preemptiveoop.experiment.ExperimentList;
import com.example.preemptiveoop.user.model.User;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ExperimentListTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<ExperimentList> rule = new ActivityTestRule<ExperimentList>(ExperimentList.class,true,true) {
        @Override
        protected Intent getActivityIntent() {
            User mockUser = new User("", "user#5", "");

            Intent mockIntent = new Intent();
            mockIntent.putExtra(".user", mockUser);

            return mockIntent;
        }
    };

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void activityStart() throws Exception {
        Activity activity = rule.getActivity();
        solo.assertCurrentActivity("Wrong Activity", ExperimentList.class);

        TimeUnit.SECONDS.sleep(10);
    }
}
