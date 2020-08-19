package edu.tacoma.uw.jasonli7.team12project;

import android.service.autofill.Validator;
import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import edu.tacoma.uw.jasonli7.team12project.authenticate.RegisterActivity;
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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {
    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule = new ActivityTestRule<>(
            RegisterActivity.class);
    @Test
    public void testLogin() {
        Random random = new Random();
        //Generate an email address
        String email = "email" + (random.nextInt(10000) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        String user = "a" + (random.nextInt(10000) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
                ;
        // Type text and then press the button.
        onView(withId(R.id.add_first))
                .perform(typeText("test1@#"));
        onView(withId(R.id.add_last))
                .perform(typeText("test1@#"));
        onView(withId(R.id.add_email))
                .perform(typeText(email));
        onView(withId(R.id.add_user_name))
                .perform(typeText(user));
        onView(withId(R.id.add_password))
                .perform(typeText("test1@#"));
        onView(withId(R.id.btn_add_user))
                .perform(click());

        onView(withText(email))
                .inRoot(withDecorView((Matcher<View>) not((Validator) is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));

    }

}