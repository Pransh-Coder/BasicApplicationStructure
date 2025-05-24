package com.example.basicapplicationstructure

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.basicapplicationstructure.di.NetworkDBModule
import com.example.basicapplicationstructure.di.RepositoryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NetworkDBModule::class, RepositoryModule::class)
class MoviesMainScreenTest {

    //when we are using hilt for testing we need to create a custom test runner class, becoz when we
    // setup hilt we need to have an Application class annotated with @HiltAndroidApp & in test cases
    //we don't use the SampleApplication class as the root application class, instead there would be
    // a custom Junit Tester class & that won't have this @HiltAndroidApp annotation, so hilt
    // would not be able to provide dependencies to this class

    //Thus, we need to create a TestRunner for which we will define our actual application class that has an annotation


    //@HiltAndroidTest annotation is not enough to provide dependencies to this class, so we need to
    // define so called RULE
    //In JUnit we have different types of rules that we can define that will just give us specific
    // behaviour for our test cases, so there could be activity rules that help us to do activity stuff(like sending intents, receiving intent results)

    //Here we would be needing a hilt rule that gives us the behaviour to actually inject dependencies
    @get:Rule(order = 0)    //order = 0 this is imp when we want to apply this rule 1st
    val hiltRule = HiltAndroidRule(this@MoviesMainScreenTest)

    //we would be needing one more rule becoz we deal with compose we want to have a screen & we want
    // to simulate new UI test here basically & for that we need the compose test rule, so that we
    // can simulate clicks, swipes, make assertions on views,that we can actually find views or composables
    //we are using createAndroidComposeRule rule here becoz by that we can specify our own Activity to
    //launch, becoz the activity should also have @AndroidEntryPoint annotation if we want to inject dependencies to that such as VM

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()   //firstly we want to inject dependencies before every test case

        //java.lang.IllegalStateException: com.example.basicapplicationstructure.MainActivity@42bf8a7 has already set content.

       /*
        When trying to write the below code I was facing the above crash becoz you're trying to manually
        call setContent {} in the test, but you're already launching MainActivity via
        createAndroidComposeRule<MainActivity>(), which automatically sets the Compose content using the Activity's onCreate().
        */

        //In the next step we want to setup screen so we now use the compose rule
        /*composeRule.setContent {
            //set the custom content for the specific test case, the stuff you actually want to test
            BasicApplicationStructureTheme {
                Toolbar(toolsText = "Test Movies Data", onBackPressed = {})
            }
        }*/
    }

    @Test
    fun clickBackArrow_isWorking(){
        // Look for a text node with the toolbar title and assert it's displayed
        composeRule.onNodeWithText(text = "Movies Data").assertIsDisplayed()
    }

    @Test
    fun toolbarText_isVisible(){
        // Find the node with contentDescription "backArrow" and perform a click
        composeRule.onNodeWithContentDescription("backArrow").performClick()
    }

    //This is correct just an alternative way for waiting for the API to be called
    /*@Test
    fun moviesList_isVisible()  = runBlocking<Unit> {
        delay(5000)
        composeRule.onAllNodesWithTag("movieItemText")[0].assertTextEquals("Title: Avatar")
        composeRule.onAllNodesWithTag("movieItemText")[1].assertTextEquals("Title: I Am Legend")
    }*/

    @Test
    fun moviesList_isVisible() {
        //Waits for at least one UI node with the tag "movieItem" to appear on screen, or fails after 5 seconds.
        composeRule.waitUntil(timeoutMillis = 5_000) {
            //This retrieves the raw semantics behind those nodes — basically a low-level snapshot of their current state.
            //It allows you to safely check if the nodes actually exist in the UI without throwing an exception if they don’t (unlike onNode().assertExists() which throws if it doesn’t find one).
            composeRule.onAllNodesWithTag("movieItem").fetchSemanticsNodes().isNotEmpty()
        }

        //WRONG: In the below line there are 2 major mistakes:
        //1. You are directly trying to get the card tag with `movieItem` which has many childs not just
        // title, so it is not able to find the the node with text
        // We are displaying in form of Title: MovieName but here you have just directly added name

        //composeRule.onAllNodesWithTag("movieItem")[0].assertTextEquals("Avatar")

        composeRule.onAllNodesWithTag("movieItemText")[0].assertTextEquals("Title: Avatar")
        composeRule.onAllNodesWithTag("movieItemText")[1].assertTextEquals("Title: I Am Legend")
    }
}