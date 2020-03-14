package com.sistempakarthtanak;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class DiagnosaTest {
    public static final String HasilDiagnosa = "97%";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void diagnosaTest() {
        onView(withId(R.id.btn_mulai_diagnosa))
                .perform(click());

        onView(allOf(withId(R.id.check_gejala), withText(endsWith("Hidung meler"))))
                .check(matches(isNotChecked()))
                .perform(click());

        onView(allOf(withId(R.id.check_gejala), withText(endsWith("Bersin"))))
                .check(matches(isNotChecked()))
                .perform(click());

        onView(allOf(withId(R.id.check_gejala), withText(endsWith("Gatal pada hidung, mata, atau tenggorokan"))))
                .check(matches(isNotChecked()))
                .perform(click());

        onView(withId(R.id.btn_hasil_diagnosa))
                .perform(click());

        onView(withId(R.id.tv_persentase))
                .check(matches(withText(HasilDiagnosa)));

    }

}
