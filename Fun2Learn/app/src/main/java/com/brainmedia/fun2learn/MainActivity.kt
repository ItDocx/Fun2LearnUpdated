package com.brainmedia.fun2learn

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.brainmedia.fun2learn.Dialogues.AboutUsDialogue
import com.brainmedia.fun2learn.Dialogues.RateUsDialogue
import com.brainmedia.fun2learn.Fragments.FragmentAudioBook
import com.brainmedia.fun2learn.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var onBackPressed: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Setting Up Navigation Drawer
            setSupportActionBar(binding.mainToolbar)
            toggle = ActionBarDrawerToggle(this@MainActivity,binding.mainDrawer,binding.mainToolbar,R.string.open,R.string.close)
            binding.mainDrawer.addDrawerListener(toggle)
            toggle.drawerArrowDrawable.color = getColor(R.color.border_color)
            toggle.syncState()

        //TODO: Adding Click Listeners on Card Views

        binding.layoutAudioBook.setOnClickListener{

            onBackPressed = true
            val fragment = FragmentAudioBook()
            supportFragmentManager.beginTransaction().replace(R.id.layout_transaction,fragment).addToBackStack(null).commit()

        }

        binding.llAlphabets.setOnClickListener{

            onBackPressed = true
           startActivity(Intent(this@MainActivity,Alphabets::class.java))

        }



        // TODO: NavigationView Listener
        binding.navView.setNavigationItemSelectedListener{
            when(it.itemId){

                R.id.mhome_btn->{
                    startActivity(Intent(this,MainActivity::class.java))
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                    finish()

                }
                R.id.mcounting_btn->{
                    Toast.makeText(this,"Counting",Toast.LENGTH_LONG).show()
                }

                R.id.malphabets_btn->{
                    Toast.makeText(this,"Alphabets",Toast.LENGTH_LONG).show()
                }

                R.id.mnumbers_btn->{
                    Toast.makeText(this,"Numbers",Toast.LENGTH_LONG).show()
                }

                R.id.mshapes_btn->{
                    Toast.makeText(this,"Shapes",Toast.LENGTH_LONG).show()
                }

                R.id.mdrawing_btn->{
                    Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show()
                }

                R.id.feedback_btn -> {
                    settingRateUsDialoge()
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }
//                R.id.settings_btn -> Toast.makeText(baseContext,"Settings", Toast.LENGTH_SHORT).show()

                R.id.maudio_btn->{
                    val fragment = FragmentAudioBook()
                    supportFragmentManager.beginTransaction().replace(R.id.layout_transaction,fragment).addToBackStack(null).commit()
                    binding.mainDrawer!!.closeDrawer(GravityCompat.START)
                }

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


    override fun onBackPressed() {

        if (onBackPressed){
            super.onBackPressed()
        }
        else{
            onBackPressed = true
            Toast.makeText(this, "Press Again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ onBackPressed = false }, 3000)
        }
        }
}