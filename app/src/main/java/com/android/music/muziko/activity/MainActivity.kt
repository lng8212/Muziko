package com.android.music.ui.activity

import android.Manifest
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
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
import com.android.music.muziko.helper.Coordinator
import com.android.music.muziko.utils.ImageUtils
import com.android.music.muziko.utils.ScreenSizeUtils.getScreenHeight
import com.android.music.ui.Song
import com.android.music.muziko.repository.RoomRepository
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

    fun updateVisibility(song : Song) {
        binding.layoutOnCollapsed.visibility = View.VISIBLE
        binding.txtArtistOnHeader.text = song.artist
        binding.txtTitleOnHeader.text = song.title
        song.image?.let {
            ImageUtils.loadImageToImageView(
                context = applicationContext,
                imageView = binding.imgOnHeader,
                image = it
            )
        }
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        Coordinator.setup(baseContext) // set up media player
        RoomRepository.createDatabase()

        supportActionBar?.hide()

        activity = this

        // set up navigation
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(com.android.music.R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_library, com.android.music.R.id.navigation_search, com.android.music.R.id.navigation_song, com.android.music.R.id.navigation_favourite
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        checkForPermissions()

        binding.layoutOnCollapsed.setOnClickListener {
            Log.e("Main", "on click layout collapsed")
            playerPanelFragment = PlayerPanelFragment()
            val fragmentManager: FragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.addToBackStack("playerPanel")
            transaction.replace(
                R.id.container,
                playerPanelFragment,
                "bottom sheet container"
            )
                .commit()


        }
//        initMainFragment()
//        initBottomSheet()

//        binding.slidingLayout.panel_high = 0
//
//        binding.slidingLayout.requestLayout()

//        binding.slidingLayout.addPanelSlideListener(object :
//            SlidingUpPanelLayout.PanelSlideListener {
//            override fun onPanelSlide(panel: View?, slideOffset: Float) {
//            }
//
//            override fun onPanelStateChanged(
//                panel: View?,
//                previousState: SlidingUpPanelLayout.PanelState?,
//                newState: SlidingUpPanelLayout.PanelState?
//            ) {
//                when (binding.slidingLayout.panelState) {
//                    SlidingUpPanelLayout.PanelState.EXPANDED -> {
//                        playerPanelFragment.updatePanelBasedOnState(SlidingUpPanelLayout.PanelState.EXPANDED)
//                    }
//                    SlidingUpPanelLayout.PanelState.COLLAPSED -> {
//                        playerPanelFragment.updatePanelBasedOnState(SlidingUpPanelLayout.PanelState.COLLAPSED)
//
//                    }
//                }
//            }
//        })
    }

//    private fun initMainFragment() {
//        val mainFragment = MainFragment()
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
////        transaction.addToBackStack(null)
//        transaction.add(
//            binding.fragmentBaseContainer.id,
//            mainFragment,
//            "main fragment container"
//        )
//            .commit()
//    }

//    private fun initBottomSheet() {
//
//        playerPanelFragment = PlayerPanelFragment()
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val transaction = fragmentManager.beginTransaction()
////        transaction.addToBackStack("playerPanel")
//        transaction.add(
//            binding.bottomSheetContainer.id,
//            playerPanelFragment,
//            "bottom sheet container"
//        )
//            .commit()
//    }

    private fun checkForPermissions() {
        val permissionProvider = PermissionProvider()
        permissionProvider.askForPermission(this, permissions)

        if (permissionProvider.permissionIsGranted) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Coordinator.mediaPlayerAgent.stop()
    }

}