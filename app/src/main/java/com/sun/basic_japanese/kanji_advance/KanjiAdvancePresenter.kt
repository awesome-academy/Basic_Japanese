package com.sun.basic_japanese.kanji_advance

import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class KanjiAdvancePresenter(
    private val kanjiAdvanceView: KanjiAdvanceContract.View,
    private val kanjiRepository: KanjiRepository
) : KanjiAdvanceContract.Presenter {

    override fun getKanjiAdvanceData(from: Int, to: Int) {
        kanjiRepository.getKanjiAdvanceRemote(
            from.toString(),
            to.toString(),
            object : OnDataLoadedCallback<KanjiAdvanceResponse> {
                override fun onSuccess(data: KanjiAdvanceResponse) {
                    kanjiAdvanceView.showKanjiAdvanceData(data.kanjiAdvanceList)
                }

                override fun onFailed(exception: Exception) {
                    kanjiAdvanceView.showError(exception.message.toString())
                }
            })
    }

    override fun getFavoriteKanjiAdvance() {
        kanjiRepository.getFavoriteKanjiAdvance(object :
            OnDataLoadedCallback<KanjiAdvanceResponse> {
            override fun onSuccess(data: KanjiAdvanceResponse) {
                kanjiAdvanceView.showKanjiAdvanceData(data.kanjiAdvanceList)
            }

            override fun onFailed(exception: Exception) {
                kanjiAdvanceView.showError(exception.message.toString())
            }
        })
    }
}
