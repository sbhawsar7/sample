package com.poc.sample.gui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.poc.base.gui.activity.BaseActivity
import com.poc.sample.MyApplication
import com.poc.sample.R
import com.poc.sample.SharedPreferenceConstants
import com.poc.sample.base.getValue
import com.poc.sample.base.gui.failure
import com.poc.sample.base.setValue
import com.poc.sample.databinding.ActivityMainBinding
import com.poc.sample.gui.animateToFade
import com.poc.sample.gui.handleFailure
import com.poc.sample.gui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity:BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override val layoutResId: Int get() = R.layout.activity_main
    override val bindingInflater: (LayoutInflater) -> ViewDataBinding get() = ActivityMainBinding::inflate
    override val binding: ActivityMainBinding get() = super.binding as ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.apply {
            failure(failure, ::handleFailure)
        }
        initialise()
    }

    private fun initialise() {

        // Navigation view initialisation
        val navView: NavigationView = findViewById(R.id.nav_view)
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        // Fragment host initialisation
        val host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
        val navController = host.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.sermonFragment -> supportActionBar?.title = getString(R.string.sermon)
                R.id.classesFragment -> supportActionBar?.title = getString(R.string.classes)
                R.id.gospelFragment -> supportActionBar?.title = getString(R.string.gospel)
                else -> {}
            }
        }
        // Show welcome message
        val user = sharedPreferences.getValue(SharedPreferenceConstants.NAME,"")
        MyApplication.showToastMessage(this, " Welcome $user")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionSermon-> findNavController(R.id.navHostFragment).navigate(R.id.sermonFragment)
            R.id.actionClass-> findNavController(R.id.navHostFragment).navigate(R.id.classesFragment)
            R.id.actionGospel-> findNavController(R.id.navHostFragment).navigate(R.id.gospelFragment)
            R.id.actionLogout-> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.app_name))
                builder.setMessage("Do you want to Logout?")
                builder.setPositiveButton("Yes") { dialog, which ->
                    sharedPreferences.setValue(SharedPreferenceConstants.IS_LOGIN, false)
                    sharedPreferences.all.clear()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    animateToFade()
                    finish()
                }
                builder.setNegativeButton("No"){ dialog, which -> dialog.cancel() }
                builder.setCancelable(false)
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
            else -> { }
        }
        binding.drawerLayout.closeDrawers()
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getFragments().forEach { fragment ->
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getFragments().forEach { fragment ->
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getFragments():List<Fragment>{
        val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
        if(navHostFragment != null)
            return navHostFragment.childFragmentManager.fragments
        else
            return listOf()
    }
}