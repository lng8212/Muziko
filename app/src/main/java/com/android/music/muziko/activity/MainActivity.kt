package com.android.music.ui.activity

import android.Manifest
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.android.music.R
import com.android.music.databinding.ActivityMainBinding
import com.android.music.muziko.PermissionProvider
import com.android.music.muziko.fragments.MainFragment
import com.android.music.muziko.fragments.PlayerPanelFragment
import com.android.music.muziko.utils.ScreenSizeUtils.getScreenHeight
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    companion object {
        var permissionsGranted: Boolean = false
        lateinit var playerPanelFragment: PlayerPanelFragment
        lateinit var activity: MainActivity

    }



    var prefs: SharedPreferences? = null

    fun updateVisibility() {
        //binding.slidingLayout.panelHeight = getScreenHeight() * 1 / 10
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        checkForPermissions()
        initMainFragment()
        initBottomSheet()

        binding.slidingLayout.panelHeight = 0

        binding.slidingLayout.requestLayout()

        binding.slidingLayout.addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                when (binding.slidingLayout.panelState) {
                    SlidingUpPanelLayout.PanelState.EXPANDED -> {
                        playerPanelFragment.updatePanelBasedOnState(SlidingUpPanelLayout.PanelState.EXPANDED)
                    }
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
                        playerPanelFragment.updatePanelBasedOnState(SlidingUpPanelLayout.PanelState.COLLAPSED)

                    }
                }
            }
        })
    }

    private fun initMainFragment() {
        val mainFragment = MainFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
//        transaction.addToBackStack(null)
        transaction.add(
            binding.fragmentBaseContainer.id,
            mainFragment,
            "main fragment container"
        )
            .commit()
    }

    private fun initBottomSheet() {

        playerPanelFragment = PlayerPanelFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
//        transaction.addToBackStack("playerPanel")
        transaction.add(
            binding.bottomSheetContainer.id,
            playerPanelFragment,
            "bottom sheet container"
        )
            .commit()
    }

    private fun checkForPermissions() {
        val permissionProvider = PermissionProvider()
        permissionProvider.askForPermission(this, permissions)

        if (permissionProvider.permissionIsGranted) {

        }
    }
}