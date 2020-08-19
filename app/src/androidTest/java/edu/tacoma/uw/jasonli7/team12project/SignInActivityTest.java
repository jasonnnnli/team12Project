package edu.tacoma.uw.jasonli7.team12project;

import android.content.Context;
import android.service.autofill.Validator;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import edu.tacoma.uw.jasonli7.team12project.authenticate.SignInActivity;

import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {
    @Rule
    public ActivityTestRule<SignInActivity> mActivityRule = new ActivityTestRule<>(
            SignInActivity.class);
    @Test
    public void testLogin() {
        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(10000) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        // Type text and then press the button.
        onView(withId(R.id.email_address_id))
                .perform(typeText(email));
        onView(withId(R.id.password_id))
                .perform(typeText("test1@#"));
        onView(withId(R.id.login_button))
                .perform(click());

        onView(withText(email))
                .inRoot(withDecorView((Matcher<View>) not((Validator) is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testLoginInvalidEmail() {
        // Type text and then press the button.
        onView(withId(R.id.email_address_id))
                .perform(typeText("jasonli.edu"));
        onView(withId(R.id.password_id))
                .perform(typeText("test1@#"));
        onView(withId(R.id.login_button))
                .perform(click());

        onView(withText("Enter valid email address"))
                .inRoot(withDecorView((Matcher<View>) not((Validator) is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void testRegisterInvalidPassword() {
        // Type text and then press the button.
        onView(withId(R.id.email_address_id))
                .perform(typeText("jasonli@uw.edu"));
        onView(withId(R.id.password_id))
                .perform(typeText(""));
        onView(withId(R.id.login_button))
                .perform(click());

        onView(withText("Enter valid password (at least 6 characters)"))
                .inRoot(withDecorView((Matcher<View>) not((Validator) is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
}