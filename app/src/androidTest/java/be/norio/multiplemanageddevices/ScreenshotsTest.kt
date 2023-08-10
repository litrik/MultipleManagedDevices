package be.norio.multiplemanageddevices

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.takeScreenshot
import androidx.test.core.graphics.writeToTestStorage
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ScreenshotsTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainScreen() {
        takeScreenshot().writeToTestStorage("main-screen")
    }

}
