package com.brainmedia.fun2learn

import android.R
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.brainmedia.fun2learn.Dialogues.AboutUsDialogue
import com.brainmedia.fun2learn.Dialogues.RateUsDialogue
import com.brainmedia.fun2learn.Fragments.FragmentAudioBook
import com.brainmedia.fun2learn.databinding.ActivityPlayerBinding
import com.example.jean.jcplayer.model.JcAudio
import kotlin.system.exitProcess


class PlayerActivity : AppCompatActivity() {

    private lateinit var playertoggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // TODO: Setting Up Navigation Drawer
        setSupportActionBar(binding.playerToolbar)
        playertoggle = ActionBarDrawerToggle(this@PlayerActivity,binding.playerDrawer,binding.playerToolbar,
            com.brainmedia.fun2learn.R.string.open,
            com.brainmedia.fun2learn.R.string.close)
        binding.playerDrawer.addDrawerListener(playertoggle)
        playertoggle.drawerArrowDrawable.color = getColor(com.brainmedia.fun2learn.R.color.border_color)
        playertoggle.syncState()


            val url = intent.getStringExtra("url")
            val  songname = intent.getStringExtra("name")


        val jcAudios: ArrayList<JcAudio> = ArrayList()
        jcAudios.add(JcAudio.createFromURL(songname.toString(),
            url = url.toString()))
        binding.audioPlayer.initPlaylist(jcAudios, null)
        binding.audioPlayer.createNotification();


        // TODO: NavigationView Listener
        binding.navPlayer.setNavigationItemSelectedListener{
            when(it.itemId){

                com.brainmedia.fun2learn.R.id.mhome_btn->{
                    startActivity(Intent(this,MainActivity::class.java))
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                    finish()

                }

                com.brainmedia.fun2learn.R.id.mcounting_btn->{
                    Toast.makeText(this,"Counting",Toast.LENGTH_LONG).show()
                }

                com.brainmedia.fun2learn.R.id.malphabets_btn->{
                    Toast.makeText(this,"Alphabets",Toast.LENGTH_LONG).show()
                }

                com.brainmedia.fun2learn.R.id.mnumbers_btn->{
                    Toast.makeText(this,"Numbers",Toast.LENGTH_LONG).show()
                }

                com.brainmedia.fun2learn.R.id.mshapes_btn->{
                    Toast.makeText(this,"Shapes",Toast.LENGTH_LONG).show()
                }

                com.brainmedia.fun2learn.R.id.mdrawing_btn->{
                    Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show()
                }

                com.brainmedia.fun2learn.R.id.feedback_btn -> {
                    settingRateUsDialoge()
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }
//                R.id.settings_btn -> Toast.makeText(baseContext,"Settings", Toast.LENGTH_SHORT).show()

                com.brainmedia.fun2learn.R.id.maudio_btn->{
                    val fragment = FragmentAudioBook()
                    supportFragmentManager.beginTransaction().replace(com.brainmedia.fun2learn.R.id.layout_transaction,fragment).addToBackStack(null).commit()
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                    finish()
                }

                com.brainmedia.fun2learn.R.id.aboutUs_btn -> {
                    setAboutUsDialoge()
                    binding.playerDrawer!!.closeDrawer(GravityCompat.START)
                }
                com.brainmedia.fun2learn.R.id.exit_btn -> exitProcess(1)
            }
            true
        }


    }


    fun settingRateUsDialoge() {
        val rate =
            RateUsDialogue(this@PlayerActivity)
        rate.getWindow()?.setBackgroundDrawable(
            ColorDrawable(
                resources
                    .getColor(R.color.white)
            )
        )
        rate.setCancelable(false)
        rate.show()
    }

    fun setAboutUsDialoge() {
        val dashBoardDialog = AboutUsDialogue(this@PlayerActivity)
        dashBoardDialog.setCancelable(false)
        dashBoardDialog.show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(playertoggle.onOptionsItemSelected(item))
            return true

        return super.onOptionsItemSelected(item)
    }


}