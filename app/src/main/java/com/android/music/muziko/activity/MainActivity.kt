package com.android.music.ui.activity

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.music.R
import com.android.music.databinding.ActivityMainBinding
import com.android.music.muziko.PermissionProvider
import com.android.music.muziko.activity.PlayerPanelActivity
import com.android.music.muziko.helper.Coordinator
import com.android.music.muziko.utils.ImageUtils
import com.android.music.muziko.model.Song
import com.android.music.muziko.repository.RoomRepository
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var permissionsGranted: Boolean = false
        lateinit var activity: MainActivity

    }



    var prefs: SharedPreferences? = null

    fun updateVisibility(song : Song) {
        if(Coordinator.isPlaying()){
            binding.btnPlay.visibility = View.GONE
            binding.btnPause.visibility = View.VISIBLE
        }
        else{
            binding.btnPlay.visibility = View.VISIBLE
            binding.btnPause.visibility = View.GONE
        }
        binding.layoutOnCollapsed.visibility = View.VISIBLE
//        binding.btnPlay.visibility = View.GONE
//        binding.btnPause.visibility = View.VISIBLE
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

    private fun layoutCollapsedListener(){
        binding.layoutOnCollapsed.setOnClickListener {
            val intent = Intent(this, PlayerPanelActivity::class.java)
            startActivity(intent)

        }
        binding.playPauseLayout.setOnClickListener {
            if (Coordinator.isPlaying()) {
                Coordinator.pause()
            } else {
                Coordinator.resume()
            }
            switchPlayPauseButton()
        }
    }

    private fun switchPlayPauseButton() {
        if (Coordinator.isPlaying()) {
            binding.btnPlay.visibility = View.GONE
            binding.btnPause.visibility = View.VISIBLE
        } else {
            binding.btnPlay.visibility = View.VISIBLE
            binding.btnPause.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
        Coordinator.setup(baseContext) // set up media player
        RoomRepository.createDatabase()

        Coordinator.setup(baseContext) // set up


        supportActionBar?.hide()

        activity = this

        // set up navigation
        val navView: BottomNavigationView = binding.navView
        val navHostFragment  = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)
        checkForPermissions()

        binding.layoutOnCollapsed.setOnClickListener {
            val intent = Intent(this, PlayerPanelActivity::class.java)
            startActivity(intent)

        }
//        initMainFragment()
//        initBottomSheet()
        layoutCollapsedListener()

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

    override fun onResume() {
        super.onResume()
        Coordinator.currentPlayingSong?.let { updateVisibility(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        Coordinator.mediaPlayerAgent.stop()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}