package com.android.music.ui.activity

import android.Manifest
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.music.R
import com.android.music.databinding.ActivityMainBinding
import com.android.music.muziko.PermissionProvider
import com.android.music.muziko.fragments.PlayerPanelFragment
import com.android.music.muziko.utils.ScreenSizeUtils.getScreenHeight
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        lateinit var playerPanelFragment: PlayerPanelFragment
        var permissionsGranted: Boolean = false
        //lateinit var playerPanelFragment: PlayerPanelFragment
        lateinit var activity: MainActivity

    }



    var prefs: SharedPreferences? = null

    fun updateVisibility() {
        binding.slidingLayout.panelHeight = getScreenHeight() * 1 / 10
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_library, R.id.navigation_search, R.id.navigation_song, R.id.navigation_favourite
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        checkForPermissions()
    }

    private fun checkForPermissions() {
        val permissionProvider = PermissionProvider()
        permissionProvider.askForPermission(this, permissions)

        if (permissionProvider.permissionIsGranted) {

        }
    }
}