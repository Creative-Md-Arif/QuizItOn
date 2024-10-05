package com.example.quiziton

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menuButton: ImageButton
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize drawer layout, menu button, and navigation view
        drawerLayout = findViewById(R.id.drawer_layout)
        menuButton = findViewById(R.id.menuBtn)
        navigationView = findViewById(R.id.navigation_view)

        // Get references to the header view's user image and username
        val headerView: View = navigationView.getHeaderView(0)  // Get the first header view
        val userImage: ImageView = headerView.findViewById(R.id.profilePic)
        val usernameText: TextView = headerView.findViewById(R.id.userName)

        // Set click listeners for user image and username text
        userImage.setOnClickListener {
            Toast.makeText(this, "User Image clicked", Toast.LENGTH_SHORT).show()
        }

        usernameText.setOnClickListener {
            Toast.makeText(this, "Username clicked", Toast.LENGTH_SHORT).show()
        }

        // Set click listener for the menu button to toggle the drawer
        menuButton.setOnClickListener {
            toggleDrawer()
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.categoriesMenu) {
                Toast.makeText(this, "categories clicked", Toast.LENGTH_SHORT).show()
            } else if (menuItem.itemId == R.id.articlesMenu) {
                Toast.makeText(this, "Articles clicked", Toast.LENGTH_SHORT).show()
            } else if (menuItem.itemId == R.id.feedbackMenu) {
                Toast.makeText(this, "Feedback clicked", Toast.LENGTH_SHORT).show()
            } else if (menuItem.itemId == R.id.loginBtn) {
                Toast.makeText(this, "Login clicked", Toast.LENGTH_SHORT).show()
            } else if (menuItem.itemId == R.id.registerBtn) {
                Toast.makeText(this, "Register clicked", Toast.LENGTH_SHORT).show()
            }
            // Close the drawer after an item is clicked
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }


}