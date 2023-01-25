package com.example.a8zad

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RegisterFragmentTests {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()
    lateinit var decorView: View

    @Before
    fun setUp() {
        activityScenarioRule.scenario.onActivity { activity ->
            decorView = activity.window.decorView
        }
        onView(withId(R.id.loginNavButton)).perform(click())
    }

    @Test
    fun navigateToLoginFragment() {
        onView(withId(R.id.registerNavButton)).perform(click())
        onView(withId(R.id.loginFragment)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun emptyEmail(){
        onView(withId(R.id.username)).perform(ViewActions.typeText(""))
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.empty_login))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emptyPassword(){
        onView(withId(R.id.password)).perform(ViewActions.typeText(""))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.empty_password))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emptyRepeatedPassword(){
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText(""))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.repeated_empty_password))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun loginTooShort1(){
        onView(withId(R.id.username)).perform(ViewActions.typeText("1"))
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.login_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun loginTooShort2(){
        onView(withId(R.id.username)).perform(ViewActions.typeText("1234"))
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.login_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun loginLongEnough(){
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.login_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun passwordTooShort1(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("1"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.password_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun passwordTooShort2(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("1234"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.password_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun passwordLongEnough(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.password_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun repeatedPasswordTooShort1(){
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("1"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.repeated_password_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    fun repeatedPasswordTooShort2(){
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("1234"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.repeated_password_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun repeatedPasswordLongEnough(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.repeated_password_too_short))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun passwordAndRepeatedPasswordAreIdentical(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("test@test.pl"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.different_passwords))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun passwordAndRepeatedPasswordAreDifferent(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("123456"))
        onView(withId(R.id.repeatedPasswordEditText)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("test@test.pl"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.different_passwords))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun emailNotValid1(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid2(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("plainaddress"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid3(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("#@%^%#\$@#\$@#.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid4(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid5(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("Joe Smith <email@example.com>"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid6(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("email.example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid7(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("email@example@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid8(){
        onView(withId(R.id.password)).perform(ViewActions.typeText(".email@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid9(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("email..email@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailNotValid10(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("あいうえお@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun emailValid1(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("email@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun emailValid2(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("firstname.lastname@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun emailValid3(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("email@subdomain.example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }
    @Test
    fun emailValid4(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("firstname+lastname@example.com"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun emailValid5(){
        onView(withId(R.id.password)).perform(ViewActions.typeText("email@123.123.123.123"))
        onView(withId(R.id.username)).perform(ViewActions.typeText("12345"))
        onView(withId(R.id.registerButton)).perform(click())
        onView(ViewMatchers.withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(ViewAssertions.doesNotExist())
    }

    private fun withDecor(): Matcher<Root>? {
        return RootMatchers.withDecorView(Matchers.not(decorView))
    }

}