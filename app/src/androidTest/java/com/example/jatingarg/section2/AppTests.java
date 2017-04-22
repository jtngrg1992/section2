package com.example.jatingarg.section2;

import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.ServiceTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static junit.framework.Assert.assertTrue;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by jatingarg on 22/04/17.
 */


@RunWith(AndroidJUnit4.class)
public class AppTests {

    private DBhelper mDBHelper; // for testing if any data is actually inserted in database
    private static final String TAG = "AppTests";

    @Before
    public void setUpDB(){
        mDBHelper = new DBhelper(InstrumentationRegistry.getTargetContext());
    }

    @Rule
    public final ServiceTestRule mServiceRule = new ServiceTestRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testWithBoundService() {
        Intent serviceIntent =
                new Intent(InstrumentationRegistry.getTargetContext(),
                        MyService.class);
        IBinder binder = null;
        try {
            binder = mServiceRule.bindService(serviceIntent);
        }   catch(Exception e) {
        }

        MyService service =
                ((MyService.MyBinder) binder).getService();

        assertThat(service.getRandomNumber(), is(any(Integer.class)));
    }



  @Test
    public void testViews(){
      onView(withId(R.id.viewDataBtn)).check(matches(isDisplayed()));
      onView(withId(R.id.stopServiceBtn)).check(matches(isDisplayed()));
      onView(withId(R.id.generateBtn)).check(matches(isDisplayed()));
      onView(withId(R.id.textView)).check(matches(isDisplayed()));

      //checking toast display once view data is clicked with empty data set
      //onView(withId(R.id.viewDataBtn)).perform(click());
      //onView(withText("No data found in db")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

      //hit generate button and check if entry was made to db
      onView(withId(R.id.generateBtn)).perform(click());
      Cursor c = mDBHelper.getAllData();
      String number = null;
      while(c.moveToNext()){
        number = c.getString(1);
      }
      onView(withId(R.id.textView)).check(matches(withText(number)));

      //hitting view data button now

      onView(withId(R.id.viewDataBtn)).perform(click());
      onView(withId(R.id.listView)).check(matches(isDisplayed()));

      //IMPORTANT NOTE:- Could find any way to test a listview containing data which is loaded async via cursor loader
      //that stuff needs to be checked manually

  }



}


