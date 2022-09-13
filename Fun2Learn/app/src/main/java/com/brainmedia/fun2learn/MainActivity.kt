package com.brainmedia.fun2learn

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.graphics.convertTo
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.graphics.toColor
import androidx.core.view.GravityCompat
import com.brainmedia.fun2learn.Dialogues.AboutUsDialogue
import com.brainmedia.fun2learn.Dialogues.RateUsDialogue
import com.brainmedia.fun2learn.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            setSupportActionBar(binding.mainToolbar)
            toggle = ActionBarDrawerToggle(this@MainActivity,binding.mainDrawer,binding.mainToolbar,R.string.open,R.string.close)
            binding.mainDrawer.addDrawerListener(toggle)
            toggle.drawerArrowDrawable.color = getColor(R.color.border_color)
            toggle.syncState()

        // NavigationView Listener
        binding.navView.setNavigationItemSelectedListener{
            when(it.itemId){

                R.id.feedback_btn -> {
                    settingRateUsDialoge()
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }
//                R.id.settings_btn -> Toast.makeText(baseContext,"Settings", Toast.LENGTH_SHORT).show()


                R.id.aboutUs_btn -> {
                    setAboutUsDialoge()
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }
                R.id.exit_btn -> exitProcess(1)
            }
            true
        }


    }


    fun settingRateUsDialoge() {
        val rate =
            RateUsDialogue(this@MainActivity)
        rate.getWindow()?.setBackgroundDrawable(
            ColorDrawable(
                resources
                    .getColor(android.R.color.white)
            )
        )
        rate.setCancelable(false)
        rate.show()
    }

    fun setAboutUsDialoge() {
        val dashBoardDialog = AboutUsDialogue(this@MainActivity)
        dashBoardDialog.setCancelable(false)
        dashBoardDialog.show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }

}