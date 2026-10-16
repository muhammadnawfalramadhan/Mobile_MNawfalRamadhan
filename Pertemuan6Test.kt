package com.example.nawfal_3tif

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tes otomatis untuk Test Drive Pertemuan 6.
 * Jalankan di emulator/HP: klik kanan file ini -> Run 'Pertemuan6Test'.
 */
@RunWith(AndroidJUnit4::class)
class Pertemuan6Test {

    private val context: Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    private val sharedPref
        get() = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    @Before
    fun hapusDataLogin() {
        // Mulai setiap tes dalam keadaan belum login
        sharedPref.edit().clear().commit()
    }

    @Test
    fun splash_belumLogin_keAuthActivity() {
        ActivityScenario.launch(SplashScreenActivity::class.java)

        // Splash tampil dulu
        onView(withId(R.id.ivSplashLogo)).check(matches(isDisplayed()))

        // Tunggu delay 2 detik dari splash
        Thread.sleep(3000)

        // isLogin = false -> halaman login
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
    }

    @Test
    fun login_usernameBedaDenganPassword_tampilDialogCobaLagi() {
        ActivityScenario.launch(AuthActivity::class.java)

        onView(withId(R.id.etUsername)).perform(typeText("nawfal"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText("Silahkan coba lagi"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

        // Data login tidak boleh tersimpan
        assertFalse(sharedPref.getBoolean("isLogin", false))
    }

    @Test
    fun login_usernameSamaDenganPassword_keMainDanSimpanSharedPref() {
        ActivityScenario.launch(AuthActivity::class.java)

        onView(withId(R.id.etUsername)).perform(typeText("nawfal"), closeSoftKeyboard())
        onView(withId(R.id.etPassword)).perform(typeText("nawfal"), closeSoftKeyboard())
        onView(withId(R.id.btnLogin)).perform(click())

        // Pindah ke MainActivity
        onView(withId(R.id.btnLogout)).check(matches(isDisplayed()))

        // isLogin dan username tersimpan
        assertTrue(sharedPref.getBoolean("isLogin", false))
        assertEquals("nawfal", sharedPref.getString("username", null))
    }

    @Test
    fun splash_sudahLogin_langsungKeMainActivity() {
        // Simulasi aplikasi dibuka lagi setelah pernah login
        sharedPref.edit()
            .putBoolean("isLogin", true)
            .putString("username", "nawfal")
            .commit()

        ActivityScenario.launch(SplashScreenActivity::class.java)
        Thread.sleep(3000)

        // isLogin = true -> langsung ke MainActivity tanpa login
        onView(withId(R.id.btnLogout)).check(matches(isDisplayed()))
    }

    @Test
    fun logout_pilihYa_hapusSharedPref() {
        sharedPref.edit()
            .putBoolean("isLogin", true)
            .putString("username", "nawfal")
            .commit()

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btnLogout)).perform(click())

        // Dialog konfirmasi muncul
        onView(withText("Apakah Anda yakin ingin logout?"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

        onView(withText("Ya")).inRoot(isDialog()).perform(click())

        // Kembali ke halaman login
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))

        // SharedPreferences terhapus
        assertFalse(sharedPref.getBoolean("isLogin", false))
        assertEquals(null, sharedPref.getString("username", null))
    }
}
