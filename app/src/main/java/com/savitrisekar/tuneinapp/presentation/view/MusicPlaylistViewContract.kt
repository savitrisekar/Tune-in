package com.savitrisekar.tuneinapp.presentation.view

interface MusicPlaylistViewContract {
    interface View{
        var isLoading: Boolean
        var onItemMusicClick: ((MusicPlaylistViewData) -> Unit)
        var errorMessage: String?
        var onErrorClicked: (() -> Unit)?

        fun init()
        fun initAdapter()
        fun setData(data: List<MusicPlaylistViewData>)

        fun onShowLoading()
        fun showErrorMessage()
        fun initErrorMessage()
    }
}