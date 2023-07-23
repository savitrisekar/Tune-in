package com.savitrisekar.tuneinapp.presentation

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.usecase.GetPlaylistMusicUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicPlaylistPresenter @Inject constructor(private val getPlaylistMusicUseCase: GetPlaylistMusicUseCase) :
    MusicPlaylistContract.Presenter {

    private var _getPlaylist: Job? = null

    override fun getPlaylist(term: String) {
        _getPlaylist?.cancel()
        view?.onLoadingFetchPlaylist(true)
        _getPlaylist = CoroutineScope(Dispatchers.Main).launch {
            getPlaylistMusicUseCase.invoke(term).collect {
                view?.onLoadingFetchPlaylist(false)
                when (it) {
                    is ResultData.Error -> {
                        view?.onErrorFetchPlaylist(null)
                    }

                    is ResultData.Failure -> {
                        view?.onErrorFetchPlaylist(it.message)
                    }

                    is ResultData.Success -> {
                        if (it.data.isEmpty()) {
                            view?.onEmptyFetchPlaylist()
                        } else {
                            view?.onSuccessFetchPlaylist(it.data)
                        }
                    }
                }
            }
        }
    }

    override var view: MusicPlaylistContract.View? = null
}