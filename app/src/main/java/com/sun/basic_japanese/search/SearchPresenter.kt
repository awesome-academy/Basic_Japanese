package com.sun.basic_japanese.search

import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class SearchPresenter(
    private val searchView: SearchContract.View,
    private val kanjiRepository: KanjiRepository
) : SearchContract.Presenter {

    override fun getKanjiAdvanceData(query: String) {
        kanjiRepository.searchKanji(query, object : OnDataLoadedCallback<KanjiAdvanceResponse> {
            override fun onSuccess(data: KanjiAdvanceResponse) {
                searchView.showKanjiAdvanceData(data.kanjiAdvanceList)
            }

            override fun onFailed(exception: Exception) {
                searchView.showError(exception.message.toString())
            }
        })
    }
}
