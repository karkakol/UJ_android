package com.example.a8zad

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class LoginFragmentTests {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    lateinit var decorView: View

    @Before
    fun setUp() {
        activityScenarioRule.scenario.onActivity { activity ->
            decorView = activity.window.decorView
        }
    }

    @Test
    fun navigateToRegisterFragment() {
        onView(withId(R.id.loginNavButton)).perform(click())
        onView(withId(R.id.registerFragment)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun emptyEmail(){
        onView(withId(R.id.loginEditText)).perform(typeText(""))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.empty_login))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emptyPassword(){
        onView(withId(R.id.passwordEditText)).perform(typeText(""))
        onView(withId(R.id.loginEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.empty_password))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun loginTooShort1(){
        onView(withId(R.id.loginEditText)).perform(typeText("1"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.login_too_short))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun loginTooShort2(){
        onView(withId(R.id.loginEditText)).perform(typeText("1234"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.login_too_short))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun loginLongEnough(){
        onView(withId(R.id.loginEditText)).perform(typeText("12345"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.login_too_short))
            .inRoot(withDecor())
            .check(doesNotExist())
    }

    @Test
    fun passwordTooShort1(){
        onView(withId(R.id.passwordEditText)).perform(typeText("1"))
        onView(withId(R.id.loginEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.password_too_short))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    fun passwordTooShort2(){
        onView(withId(R.id.passwordEditText)).perform(typeText("1234"))
        onView(withId(R.id.loginEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.password_too_short))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun passwordLongEnough(){
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.password_too_short))
            .inRoot(withDecor())
            .check(doesNotExist())
    }

    @Test
    fun emailNotValid1(){
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid2(){
        onView(withId(R.id.loginEditText)).perform(typeText("plainaddress"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid3(){
        onView(withId(R.id.loginEditText)).perform(typeText("#@%^%#\$@#\$@#.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid4(){
        onView(withId(R.id.loginEditText)).perform(typeText("@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid5(){
        onView(withId(R.id.loginEditText)).perform(typeText("Joe Smith <email@example.com>"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid6(){
        onView(withId(R.id.loginEditText)).perform(typeText("email.example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid7(){
        onView(withId(R.id.loginEditText)).perform(typeText("email@example@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid8(){
        onView(withId(R.id.loginEditText)).perform(typeText(".email@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid9(){
        onView(withId(R.id.loginEditText)).perform(typeText("email..email@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailNotValid10(){
        onView(withId(R.id.loginEditText)).perform(typeText("あいうえお@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(matches(isDisplayed()))
    }

    @Test
    fun emailValid1(){
        onView(withId(R.id.loginEditText)).perform(typeText("email@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(doesNotExist())
    }

    @Test
    fun emailValid2(){
        onView(withId(R.id.loginEditText)).perform(typeText("firstname.lastname@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(doesNotExist())
    }

    @Test
    fun emailValid3(){
        onView(withId(R.id.loginEditText)).perform(typeText("email@subdomain.example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(doesNotExist())
    }
    @Test
    fun emailValid4(){
        onView(withId(R.id.loginEditText)).perform(typeText("firstname+lastname@example.com"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(doesNotExist())
    }

    @Test
    fun emailValid5(){
        onView(withId(R.id.loginEditText)).perform(typeText("email@123.123.123.123"))
        onView(withId(R.id.passwordEditText)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText(R.string.email_not_valid))
            .inRoot(withDecor())
            .check(doesNotExist())
    }

    private fun withDecor(): Matcher<Root>? {
        return withDecorView(Matchers.not(decorView))
    }

}