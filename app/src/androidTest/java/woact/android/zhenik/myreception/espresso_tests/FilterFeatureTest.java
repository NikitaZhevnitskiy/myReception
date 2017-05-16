package woact.android.zhenik.myreception.espresso_tests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import woact.android.zhenik.myreception.EspressoUtils;
import woact.android.zhenik.myreception.view.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static woact.android.zhenik.myreception.R.id.choose_hotel_list;
import static woact.android.zhenik.myreception.R.id.choose_hotel_search;
import static woact.android.zhenik.myreception.R.id.main_activity_logo_img;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterFeatureTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityMain = new ActivityTestRule(MainActivity.class);

    @Test
    public void checkLogo_MainActivity() {onView(withId(main_activity_logo_img)).perform();}

    @Test
    public void checkSearch_ChooseHotelActivity() {
        onView(isRoot()).perform(EspressoUtils.waitFor(4000));
        onView(withId(choose_hotel_search)).check(matches(isDisplayed()));
    }

    @Test
    public void checkCountItems_ListView_ChooseActivity() {
        onView(isRoot()).perform(EspressoUtils.waitFor(4000));
        onView(withId(choose_hotel_list)).check(matches(isDisplayed()));
    }
}
