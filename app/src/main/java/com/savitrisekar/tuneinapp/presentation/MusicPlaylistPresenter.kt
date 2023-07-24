package com.savitrisekar.tuneinapp.presentation

import com.savitrisekar.tuneinapp.domain.base.ResultData
import com.savitrisekar.tuneinapp.domain.usecase.GetPlaylistMusicUseCase
import com.savitrisekar.tuneinapp.domain.usecase.GetSearchPlaylistMusicUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicPlaylistPresenter @Inject constructor(
    private val getPlaylistMusicUseCase: GetPlaylistMusicUseCase,
    private val getSearchPlaylistMusicUseCase: GetSearchPlaylistMusicUseCase,
) : MusicPlaylistContract.Presenter {

    private var _getPlaylist: Job? = null
    private var _getSearchPlaylist: Job? = null

    override fun getPlaylist() {
        _getPlaylist?.cancel()
        view?.onLoadingFetchPlaylist(true)
        _getPlaylist = scope.launch {
            getPlaylistMusicUseCase.invoke(term = "jack").collect {
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

    override fun getSearchPlaylist(term: String) {
        _getSearchPlaylist?.cancel()
        view?.onLoadingFetchPlaylist(true)
        _getSearchPlaylist = scope.launch {
            getSearchPlaylistMusicUseCase.invoke(term = term).collect {
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