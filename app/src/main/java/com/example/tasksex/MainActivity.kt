package com.example.tasksex

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.tasksex.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NavigationBarView.OnItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNav: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        bottomNav = binding.appBarMain.bottomNav

        // Tool Bar
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.title = getString(R.string.menu_home)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Floating Action Button && SnackBar
        binding.appBarMain.fab.setOnClickListener {
            // Alert Dialog
            showDialog()

        }

        // Navigation Controller
        navController = findNavController(R.id.nav_host_fragment_content_main)

        // Drawer Navigation && Toggle
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.appBarMain.toolbar,
            R.string.start, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Navigation View Menu
        navView.bringToFront()
        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.nav_home)


        // Bottom Navigation View
        bottomNav.setOnItemSelectedListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                replaceFragment(R.id.nav_home)
                supportActionBar?.title = resources.getString(R.string.menu_home)
                navView.setCheckedItem(R.id.nav_home)
                navView.itemIconTintList
                bottomNav.menu.findItem(item.itemId).isChecked = true
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_gallery -> {
                replaceFragment(R.id.nav_gallery)
                supportActionBar?.title = resources.getString(R.string.menu_gallery)
                bottomNav.menu.findItem(item.itemId).isChecked = true
                navView.setCheckedItem(R.id.nav_gallery)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_slideshow -> {
                replaceFragment(R.id.nav_slideshow)
                supportActionBar?.title = resources.getString(R.string.menu_slideshow)
                navView.setCheckedItem(R.id.nav_slideshow)
                bottomNav.menu.findItem(item.itemId).isChecked = true
                // why function setSelectedItemId Make App Freeze
                // bottomNav.setSelectedItemId(item.itemId) !!! it's Freeze App
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    private fun replaceFragment(idRes: Int) {
        navController.navigate(idRes)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }
    private fun showDialog(){
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setMessage(resources.getString(R.string.supporting_text))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                // Respond to neutral button press
                dialog.cancel()
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                // Respond to positive button press
                showSnackBar()
            }
            .setCancelable(false)
            .show()
    }

    private fun showSnackBar(){
        Snackbar.make(drawerLayout, "Success", Snackbar.LENGTH_SHORT)
            .setAnchorView(binding.appBarMain.fab)
            .setActionTextColor(ResourcesCompat.getColor(resources,R.color.teal_200,theme))
            .setBackgroundTint(Color.GRAY)
            .setTextColor(Color.BLACK)
            .setAction("Action"){}
            .show()
    }
}