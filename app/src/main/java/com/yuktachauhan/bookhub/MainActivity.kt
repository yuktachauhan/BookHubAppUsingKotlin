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
    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawerLayout)
        frame = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        setUpToolBar()
        openDashBoard()
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

            //code for highlighting the current menu item which it points to
            if (previousMenuItem != null) {
                //uncheck the previous menu item
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.dashboard -> {
                    openDashBoard()
                    drawerLayout.closeDrawers()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.about_app -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AboutAppFragment())
                        .commit()
                    supportActionBar?.title = "About App"
                    drawerLayout.closeDrawers()
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavouritesFragment())
                        .commit()
                    supportActionBar?.title = "Favourites"
                    drawerLayout.closeDrawers()
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

    fun openDashBoard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, DashBoardFragment())
            .commit()
        supportActionBar?.title = "DashBoard"
        //highlighting the dashboard icon when dashboard fragment is opened
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        //frag holds the current fragment
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when (frag) {
            //if we are on some other fragment then on pressing back button we will come to dashboard fragment
            !is DashBoardFragment -> openDashBoard()
            else -> super.onBackPressed() //default functionality that is exit the app
        }
    }
}