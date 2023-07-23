package com.savitrisekar.tunein.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.core.view.isGone
import com.savitrisekar.tunein.R
import com.savitrisekar.tunein.databinding.ComponentMusicPlaylistBinding
import timber.log.Timber

class MusicPlaylistView : FrameLayout, MusicPlaylistViewContract.View {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    private var _binding: ComponentMusicPlaylistBinding? = null
    private var _adapter: MusicPlaylistViewAdapter? = null
    private var _playlistData: List<MusicPlaylistViewData>? = null

    override var isLoading: Boolean = false
        set(value) {
            field = value
            onShowLoading()
        }

    override var onItemMusicClick: (MusicPlaylistViewData) -> Unit = {}

    override var errorMessage: String? = null
        set(value) {
            field = value
            showErrorMessage()
        }

    override var onErrorClicked: (() -> Unit)? = null

    override fun init() {
        _binding = ComponentMusicPlaylistBinding.inflate(LayoutInflater.from(context), this, true)

        initAdapter()
        initErrorMessage()
    }

    override fun initAdapter() {
        _binding?.apply {
            _adapter = MusicPlaylistViewAdapter {
                onItemMusicClick.invoke(it)
            }.also {
                componentMusicPlaylistRv.adapter = _adapter
            }
        }
    }

    override fun setData(data: List<MusicPlaylistViewData>) {
        _binding?.apply {
            _playlistData = data

            if (data.isNotEmpty()) {
                componentMusicPlaylistError.visibility = View.GONE
                componentMusicPlaylistRv.visibility = View.VISIBLE
                _adapter?.setItems(data)
            } else {
                componentMusicPlaylistRv.visibility = View.GONE
                componentMusicPlaylistError.visibility = View.VISIBLE
            }
        }
    }

    override fun onShowLoading() {
        _binding?.apply {
            if (isLoading) {
                if (isGone) {
                    visibility = View.VISIBLE
                }
                componentMusicPlaylistRv.visibility = View.GONE
                componentMusicPlaylistError.visibility = View.GONE
                componentMusicPlaylistShimmer.visibility = View.VISIBLE
                return
            }
            componentMusicPlaylistShimmer.visibility = View.GONE
        }
    }

    override fun showErrorMessage() {
        _binding?.apply {
            if (errorMessage.isNullOrEmpty()) {
                componentMusicPlaylistError.visibility = View.GONE
                return
            }
            componentMusicPlaylistError.visibility = View.VISIBLE
            componentMusicPlaylistError.text =
                errorMessage ?: context.getString(R.string.error_something_tap_to_retry)
        }
    }

    override fun initErrorMessage() {
        _binding?.apply {
            componentMusicPlaylistError.setOnClickListener {
                if (onErrorClicked == null) {
                    Timber.d("textError click not implemented yet")
                    return@setOnClickListener
                }
                onErrorClicked?.invoke()
                errorMessage = null
            }
        }
    }
}