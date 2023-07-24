package com.savitrisekar.tuneinapp.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import coil.load
import com.savitrisekar.tuneinapp.R
import com.savitrisekar.tuneinapp.databinding.ActivityMusicPlaylistBinding
import com.savitrisekar.tuneinapp.domain.di.AppModule
import com.savitrisekar.tuneinapp.domain.di.MusicDomainModule
import com.savitrisekar.tuneinapp.domain.di.DaggerApplicationComponent
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.presentation.base.BaseActivity
import com.savitrisekar.tuneinapp.presentation.view.MusicPlaylistViewData
import javax.inject.Inject

class MusicPlaylistActivity :
    BaseActivity<ActivityMusicPlaylistBinding>(), MediaPlayer.OnPreparedListener,
    MusicPlaylistContract.View {

    @Inject
    lateinit var presenter: MusicPlaylistPresenter

    private var query: String = ""
    private var isPlay: Boolean = false
    private var mediaPlayer: MediaPlayer? = null

    override fun getViewBinding(): ActivityMusicPlaylistBinding {
        return ActivityMusicPlaylistBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(this.application))
            .musicDomainModule(MusicDomainModule())
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)

        initPresenter()
        initView()

        initData()
        initMediaPlayer()
        fetchSearch()
    }

    private fun initMediaPlayer() {
        binding?.apply {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setOnPreparedListener(this@MusicPlaylistActivity)
                setOnCompletionListener {
                    initButtonPause()
                }
            }
        }
    }

    private fun initData() {
        binding?.componentMusicPlaylistView?.clearItems()

        fetchPlaylist()
    }

    private fun fetchSearch() {
        presenter.getSearchPlaylist(query)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.doDetachView()
        mediaPlayer?.release()
    }

    private fun fetchPlaylist() {
        presenter.getPlaylist()
    }

    override fun initView() {
        initSearch()
        initPlaylist()
    }

    private fun initPlaylist() {
        binding?.apply {
            componentMusicPlaylistView.onItemMusicClick = { item ->
                musicPlaylistPlayNow.visibility = View.VISIBLE
                onNowPlaying(item.song)
                playNowImage.load(item.picture)
                playNowSong.text = item.title
                playNowArtist.text = item.artist
            }

            playNowBtnPlay.setOnClickListener {
                if (isPlay) {
                    initButtonPlay()
                    mediaPlayer?.start()
                } else {
                    initButtonPause()
                    mediaPlayer?.pause()
                }
            }

            componentMusicPlaylistView.onErrorClicked = {
                presenter.getPlaylist()
            }
        }
    }

    private fun onNowPlaying(song: String) {
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(song)
        mediaPlayer?.prepareAsync()
    }

    private fun initButtonPlay() {
        binding?.apply {
            isPlay = false
            playNowBtnPlay.setImageResource(R.drawable.ic_pause_24)
        }
    }

    private fun initButtonPause() {
        binding?.apply {
            isPlay = true
            playNowBtnPlay.setImageResource(R.drawable.ic_play_24)
        }
    }

    private fun initSearch() {
        binding?.apply {
            componentSearchMusic.apply {
                componentSearchInput.setOnEditorActionListener { v, actionId, event ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_SEARCH -> {
                            query = v?.text?.toString().orEmpty().trim()
                            fetchSearch()
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
            componentMusicPlaylistView.onShowLoading(isLoading)
        }
    }

    override fun onEmptyFetchPlaylist() {
        binding?.apply {
            componentMusicPlaylistView.setData(emptyList())
        }
    }

    override fun onErrorFetchPlaylist(errorMessage: String?) {
        binding?.apply {
            componentMusicPlaylistView.showErrorMessage(errorMessage)
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
                    song = it.previewUrl
                )
            })
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
        initButtonPlay()
    }
}