package com.aditasha.itunesplayer.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aditasha.itunesplayer.R
import com.aditasha.itunesplayer.databinding.FragmentMediaPlayerBinding
import com.aditasha.itunesplayer.main.MainActivityViewModel
import com.aditasha.itunesplayer.main.ViewModelFactory
import com.aditasha.itunesplayer.network.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeUnit

class MediaPlayerFragment : Fragment() {

    private var _binding: FragmentMediaPlayerBinding? = null

    private val binding get() = _binding!!

    private var mediaPlayer: MediaPlayer? = null
    private var ready = false

    private val viewModel: MainActivityViewModel by activityViewModels { ViewModelFactory() }
    private var listMusic = ArrayList<ResultsItem>()
    private var currentPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMediaPlayerBinding.inflate(inflater, container, false)

        setupMediaPlayer()

        binding.playPause.setOnCheckedChangeListener { _, _ ->
            if (mediaPlayer?.isPlaying as Boolean) {
                mediaPlayer?.pause()
            } else {
                mediaPlayer?.start()
            }
        }

        binding.nextTrack.setOnClickListener {
            if (currentPosition > -1 && currentPosition < listMusic.size - 1) {
                currentPosition += 1
                viewModel.addPlayingMusic(currentPosition)
                loadDataSource(currentPosition)
            }
        }

        binding.previousTrack.setOnClickListener {
            if (currentPosition > 0) {
                currentPosition -= 1
                viewModel.addPlayingMusic(currentPosition)
                loadDataSource(currentPosition)
            }
        }
        return binding.root
    }

    private fun setupMediaPlayer() {
        mediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        mediaPlayer?.setAudioAttributes(attribute)

        flowCollection()

        mediaPlayer?.setOnPreparedListener {
            seekBarTime()
            ready = true
            binding.playPause.isChecked = true
        }
        mediaPlayer?.setOnErrorListener { _, _, _ -> false }
        mediaPlayer?.setOnCompletionListener {
            binding.playPause.isChecked = false
            ready = false
            mediaPlayer?.stop()
        }
    }

    private fun flowCollection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listMusic.collectLatest {
                    if (it.isNotEmpty()) listMusic = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.position.collectLatest {
                    if (it > -1) {
                        currentPosition = it
                        loadDataSource(it)
                    }
                }
            }
        }
    }

    private fun loadDataSource(position: Int) {
        binding.playPause.isChecked = false
        binding.seekBar.progress = 0
        ready = false

        if (mediaPlayer?.isPlaying as Boolean) {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
        } else {
            mediaPlayer?.reset()
        }

        try {
            mediaPlayer?.setDataSource(listMusic[position].previewUrl)
            mediaPlayer?.isLooping = false
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaPlayer?.prepareAsync()
    }

    private fun seekBarTime() {
        val duration = mediaPlayer?.duration!!
        binding.totalTimer.text = timeHelper(duration.toLong())

        binding.seekBar.max = duration

        lifecycleScope.launch(Dispatchers.Main) {
            var current = mediaPlayer?.currentPosition!!
            while (current < duration) {
                binding.seekBar.progress = current
                binding.currentTimer.text = timeHelper(current.toLong())
                delay(1000)
                current = mediaPlayer?.currentPosition!!
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, progress: Int, touch: Boolean) {}

            override fun onStartTrackingTouch(seek: SeekBar?) {}

            override fun onStopTrackingTouch(seek: SeekBar?) {
                mediaPlayer?.seekTo(binding.seekBar.progress)
            }

        })
    }

    private fun timeHelper(duration: Long): String {
        val totalMinute = TimeUnit.MILLISECONDS.toMinutes(duration)
        val totalSeconds =
            TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(totalMinute)

        return getString(R.string.minute, totalMinute, totalSeconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}