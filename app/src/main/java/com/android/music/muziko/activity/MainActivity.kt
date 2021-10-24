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
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bining: ActivityMainBinding
    companion object {
        var permissionsGranted: Boolean = false
        //lateinit var playerPanelFragment: PlayerPanelFragment
        lateinit var activity: MainActivity

    }



    var prefs: SharedPreferences? = null


    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bining = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bining.root)

        supportActionBar?.hide()

        val navView: BottomNavigationView = bining.navView

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