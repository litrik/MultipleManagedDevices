This is a sample project showing problems when using instrumented tests with "Test storage" on multiple "managed devices"

Given the following definition of 2 managed devices:

```
testOptions {
    managedDevices {
        devices {
            create<ManagedVirtualDevice>("phone").apply {
                device = "Pixel 2"
                apiLevel = 30
                systemImageSource = "aosp-atd"
            }
            create<ManagedVirtualDevice>("tablet").apply {
                device = "Pixel Tablet"
                apiLevel = 30
                systemImageSource = "aosp-atd"
            }
        }
    }
}
```

Given a dependency on `androidTestUtil("androidx.test.services:test-services:1.4.2")`

Given an instrumentation test that takes a screenshot:
```
@RunWith(AndroidJUnit4::class)
class ScreenshotsTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainScreen() {
        takeScreenshot().writeToTestStorage("main-screen")
    }

}
```

When you comment out the definition of the "tablet" device and run `./gradlew clean phoneDebugAndroidTest` you end up with 1 screenshot in the `app/build/outputs/managed_device_android_test_additional_output/debug/phone` folder. The screenshot has the dimension of a phone. This is expected.

When you comment out the definition of the "phone" device and run `./gradlew clean tabletDebugAndroidTest` you end up with 1 screenshot in the `app/build/outputs/managed_device_android_test_additional_output/debug/tablet` folder. The screenshot has the dimension of a tablet. This is expected.

When you execute `./gradlew clean allDevicesAndroidTest`, two tests are executed:
```
> Task :app:tabletDebugAndroidTest
Starting 1 tests on tablet

> Task :app:phoneDebugAndroidTest
Starting 1 tests on phone

> Task :app:mergeDebugAndroidTestTestResultProtos
Test execution completed. See the report at: file:///home/litrik/git/MultipleManagedDevices/app/build/reports/androidTests/managedDevice/debug/allDevices/index.html
```

Unfortunately, only 1 screenshot ends up in the `app/build/outputs/managed_device_android_test_additional_output/debug` folder. It has the dimensions of a phone, yet it is stored in the `tablet` subfolder.

I would expect to get 2 screenshots: 1 of a phone in the `phone` subfolder and 1 of a tablet in the `tablet` subfolder.