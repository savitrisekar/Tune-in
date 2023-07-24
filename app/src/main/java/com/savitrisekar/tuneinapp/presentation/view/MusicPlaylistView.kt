package com.savitrisekar.tuneinapp.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.core.view.isGone
import com.savitrisekar.tuneinapp.R
import com.savitrisekar.tuneinapp.databinding.ComponentMusicPlaylistBinding

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

    override var onItemMusicClick: (MusicPlaylistViewData) -> Unit = {}

    override var onErrorClicked: (() -> Unit)? = null

    override fun init() {
        _binding = ComponentMusicPlaylistBinding.inflate(LayoutInflater.from(context), this, true)

        initAdapter()
    }

    override fun initAdapter() {
        _binding?.apply {
            _adapter = MusicPlaylistViewAdapter {
                onItemMusicClick.invoke(it)
            }.also {
                componentMusicPlaylistRv.adapter = it
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

    fun clearItems() {
        _adapter?.clearItems()
    }

    override fun onShowLoading(isLoading: Boolean) {
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

    override fun showErrorMessage(message: String?) {
        _binding?.apply {
            componentMusicPlaylistRv.visibility = View.GONE
            componentMusicPlaylistError.visibility = View.VISIBLE
            componentMusicPlaylistError.text =
                message ?: context.getString(R.string.error_something_tap_to_retry)
        }
    }
}