package com.savitrisekar.tuneinapp.presentation

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.savitrisekar.tuneinapp.databinding.ActivityMusicPlaylistBinding
import com.savitrisekar.tuneinapp.MyApplication
import com.savitrisekar.tuneinapp.domain.base.BaseActivity
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.presentation.view.MusicPlaylistViewData
import javax.inject.Inject

class MusicPlaylistActivity :
    BaseActivity<ActivityMusicPlaylistBinding>(),
    MusicPlaylistContract.View {

    @Inject
    lateinit var presenter: MusicPlaylistPresenter

    private var term: String = ""

    override fun getViewBinding(): ActivityMusicPlaylistBinding {
        return ActivityMusicPlaylistBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        initPresenter()
        initView()

        initData()
    }

    private fun initData() {
        fetchPlaylist()
    }

    private fun fetchPlaylist() {
        presenter.getPlaylist(term)
    }

    override fun initView() {
        initSearch()
    }

    private fun initSearch() {
        binding?.apply {
            componentSearchMusic.apply {
                componentSearchInput.setOnEditorActionListener { v, actionId, event ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_SEARCH -> {
                            term = v?.text?.toString().orEmpty().trim()
                            initData()
                            return@setOnEditorActionListener true
                        }
                    }
                    false
                }
            }
        }
    }

    override fun initPresenter() {
        presenter.doAttachView(this)
    }

    override fun onLoadingFetchPlaylist(isLoading: Boolean) {
        binding?.apply {
            componentMusicPlaylistView.onShowLoading()
        }
    }

    override fun onEmptyFetchPlaylist() {
        binding?.apply {
            componentMusicPlaylistView.setData(emptyList())
        }
    }

    override fun onErrorFetchPlaylist(errorMessage: String?) {
        binding?.apply {
            componentMusicPlaylistView.showErrorMessage()
        }
    }

    override fun onSuccessFetchPlaylist(data: List<MusicPlaylist>) {
        binding?.apply {
            componentMusicPlaylistView.setData(data.map {
                MusicPlaylistViewData(
                    id = it.id,
                    picture = it.artworkUrl100,
                    title = it.trackName,
                    artist = it.artistName,
                )
            })
        }
    }
}