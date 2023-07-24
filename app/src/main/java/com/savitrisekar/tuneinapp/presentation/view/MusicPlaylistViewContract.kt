package com.savitrisekar.tuneinapp.presentation.view

interface MusicPlaylistViewContract {
    interface View{
        var onItemMusicClick: ((MusicPlaylistViewData) -> Unit)
        var onErrorClicked: (() -> Unit)?

        fun init()
        fun initAdapter()
        fun setData(data: List<MusicPlaylistViewData>)

        fun onShowLoading(isLoading: Boolean)
        fun showErrorMessage(message: String?)
    }
}