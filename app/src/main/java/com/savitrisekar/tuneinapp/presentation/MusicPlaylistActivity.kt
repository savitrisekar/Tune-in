package com.savitrisekar.tuneinapp.presentation

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import coil.load
import com.savitrisekar.tuneinapp.MyApplication
import com.savitrisekar.tuneinapp.R
import com.savitrisekar.tuneinapp.databinding.ActivityMusicPlaylistBinding
import com.savitrisekar.tuneinapp.domain.model.MusicPlaylist
import com.savitrisekar.tuneinapp.presentation.base.BaseActivity
import com.savitrisekar.tuneinapp.presentation.view.MusicPlaylistViewData
import javax.inject.Inject

class MusicPlaylistActivity :
    BaseActivity<ActivityMusicPlaylistBinding>(), MediaPlayer.OnPreparedListener,
    MusicPlaylistContract.View {

    @Inject
    lateinit var presenter: MusicPlaylistPresenter

    private var term: String = ""
    private var isPlay: Boolean = false
    private var mediaPlayer: MediaPlayer? = null

    override fun getViewBinding(): ActivityMusicPlaylistBinding {
        return ActivityMusicPlaylistBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        initPresenter()
        initView()

        initData()
        initMediaPlayer()
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.doDetachView()
        mediaPlayer?.release()
    }

    private fun fetchPlaylist() {
        presenter.getPlaylist(term)
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

                playNowBtnPlay.apply {
                    when (item.playSetUp) {
                        MusicPlaylistViewData.PlaySetUp.PLAY -> {
                            setImageResource(R.drawable.ic_pause_24)
                        }

                        else -> {
                            setImageResource(R.drawable.ic_play_24)
                        }
                    }
                }
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
                presenter.getPlaylist(term)
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