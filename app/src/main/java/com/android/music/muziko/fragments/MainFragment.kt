package com.android.music.muziko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.music.databinding.FragmentMainBinding
import com.android.music.muziko.activity.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.music.ui.fragments.FavouriteFragment
import com.android.music.ui.fragments.LibraryFragment
import com.android.music.ui.fragments.SearchFragment
import com.android.music.ui.fragments.SongFragment





class MainFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding
    private val activity = MainActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                when (item.itemId) {
                    com.android.music.R.id.navigation_library -> {
                        fragmentTransaction.replace(com.android.music.R.id.content, LibraryFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    com.android.music.R.id.navigation_search -> {
                        fragmentTransaction.replace(com.android.music.R.id.content, SearchFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    com.android.music.R.id.navigation_song -> {
                        fragmentTransaction.replace(com.android.music.R.id.content, SongFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                    com.android.music.R.id.navigation_favourite -> {
                        fragmentTransaction.replace(com.android.music.R.id.content, FavouriteFragment()).commit()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
        val navView: BottomNavigationView = binding.navView

        //val navController = findNavController(R.id.nav_host_fragment_activity_main)
        if(activity!=null){

            val navHostFragment  = getActivity()?.supportFragmentManager?.findFragmentById(com.android.music.R.id.nav_host_fragment_activity_main) as? NavHostFragment
            val navController = navHostFragment?.navController
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_library, R.id.navigation_search, R.id.navigation_song, R.id.navigation_favourite
//            )
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
            if (navController != null) {
                navView.setupWithNavController(navController)
            }
        }
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        val fragmentManager = requireActivity().supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(com.android.music.R.id.content, LibraryFragment()).commit()


    }
}