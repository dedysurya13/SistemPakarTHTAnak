package com.sistempakarthtanak;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class EspressoTest {

    public static final String HasilDiagnosa = "97%";

    public static final String DaftarPenyakit = "Sinusitis";

    public static final String TentangAplikasi = "16.11.0563";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void diagnosaTest() {
        onView(withId(R.id.btn_mulai_diagnosa))
                .perform(click());

        onView(allOf(withId(R.id.check_gejala), withText(containsString("Hidung meler"))))
                .check(matches(isNotChecked()))
                .perform(click());

        onView(allOf(withId(R.id.check_gejala), withText(containsString("Bersin"))))
                .check(matches(isNotChecked()))
                .perform(click());

        onView(allOf(withId(R.id.check_gejala), withText(containsString("Gatal pada hidung, mata, atau tenggorokan"))))
                .check(matches(isNotChecked()))
                .perform(click());

        onView(withId(R.id.btn_hasil_diagnosa))
                .perform(click());

        onView(withId(R.id.tv_persentase))
                .check(matches(withText(HasilDiagnosa)));
    }

    @Test
    public void daftarPenyakitTest() {
        onView(withId(R.id.btn_daftar_penyakit))
                .perform(click());

        onView(allOf(withId(R.id.nama_penyakit), withText(containsString("Sinusitis"))))
                .perform(click());

        onView(withId(R.id.tv_nama_penyakit))
                .check(matches(withText(DaftarPenyakit)));

    }

    @Test
    public void tentangAplikasiTest() {
        onView(withId(R.id.btn_tentang_aplikasi))
                .perform(click());

        onView(withId(R.id.tv_tentang_aplikasi))
                .check(matches(withText(containsString(TentangAplikasi))));
    }
}
