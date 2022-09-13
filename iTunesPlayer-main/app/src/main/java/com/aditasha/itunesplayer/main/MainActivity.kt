package com.aditasha.itunesplayer.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditasha.itunesplayer.R
import com.aditasha.itunesplayer.adapter.MusicAdapter
import com.aditasha.itunesplayer.databinding.ActivityMainBinding
import com.aditasha.itunesplayer.network.ResultsItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels { ViewModelFactory() }
    private val musicAdapter: MusicAdapter by lazy { MusicAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupRecycler()
        setupSearchView()
        clickListener()
        playingListener()
    }

    private fun setupRecycler() {
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
        val itemDecoration =
            DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation)

        binding.apply {
            recylerView.apply {
                adapter = musicAdapter
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                addItemDecoration(itemDecoration)
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotBlank()) {
                    searchListener(query)
                }
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun searchListener(query: String) {
        lifecycleScope.launch {
            viewModel.getMusicList(query, resources.getString(R.string.country_id))
                .collectLatest {
                    when (it) {
                        is State.Loading -> {
                            showLoading(true)
                            showFailed(false, "")
                            binding.infoText.isVisible = false
                        }
                        is State.Error -> {
                            showLoading(false)
                            showFailed(true, it.message.toString())
                            binding.infoText.isVisible = false
                        }
                        is State.Success -> {
                            showLoading(false)
                            showFailed(false, "")
                            binding.infoText.isVisible = false
                            if (it.data != null) {
                                if (it.data.isEmpty()) {
                                    binding.infoText.text = getString(R.string.no_data)
                                    binding.infoText.isVisible = true
                                }
                                musicAdapter.addData(it.data)
                            }
                        }
                    }
                }
        }
    }

    private fun clickListener() {
        musicAdapter.setOnItemClickCallback(object : MusicAdapter.OnItemClickCallback {
            override fun onItemClicked(music: ResultsItem, position: Int) {
                if (binding.motionLayout.progress == 0.0F) {
                    binding.motionLayout.transitionToEnd()
                }
                lifecycleScope.launch { viewModel.addPlayingPosition(position) }
            }
        })
    }

    private fun playingListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playingMusic.collect {
                    var oldPosition = -1
                    if (musicAdapter.playedRow.isNotEmpty()) {
                        oldPosition = musicAdapter.playedRow[0]
                    }
                    musicAdapter.playedRow.clear()
                    musicAdapter.playedRow.add(it)
                    musicAdapter.notifyItemChanged(it)
                    if (oldPosition > -1) musicAdapter.notifyItemChanged(oldPosition)
                }
            }
        }
    }

    private fun showFailed(isFailed: Boolean, e: String) {
        if (isFailed) {
            val text = getString(R.string.error, e)
            errorDialog(text).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loading.isVisible = isLoading
    }

    private fun errorDialog(e: String): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(this@MainActivity)
            .setMessage(e)
            .setPositiveButton(resources.getString(R.string.close_dialog)) { dialog, _ ->
                dialog.dismiss()
            }
    }
}