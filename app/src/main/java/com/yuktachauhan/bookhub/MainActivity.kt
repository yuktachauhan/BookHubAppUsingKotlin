package com.yuktachauhan.bookhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var frame: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        frame = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        setUpToolBar()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        //to change home button(arrow) to hamburger icon and vice versa on opening and closing of navigation drawer
        actionBarDrawerToggle.syncState()

        //to set click listener on menu items
        navigationView.setNavigationItemSelectedListener {
            //it represents the current selected item
            when (it.itemId) {
                R.id.dashboard -> {
                    Toast.makeText(this@MainActivity, "Dashboard is clicked", Toast.LENGTH_LONG)
                        .show()
                }
                R.id.profile -> {
                    Toast.makeText(this@MainActivity, "Profile is clicked", Toast.LENGTH_LONG)
                        .show()
                }
                R.id.about_app -> {
                    Toast.makeText(this@MainActivity, "About App is clicked", Toast.LENGTH_LONG)
                        .show()
                }
                R.id.favourites -> {
                    Toast.makeText(this@MainActivity, "Favourites is clicked", Toast.LENGTH_LONG)
                        .show()
                }

            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Hub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //this function makes the home button and other menu items functional
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        //if we select hamburger then drawer will open ,hamburger is also a menu item
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
}