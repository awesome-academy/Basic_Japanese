package com.sun.basic_japanese.kanjibasic

import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.model.KanjiBasicResponse
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class KanjiBasicPresenter(
    private val kanjiBasicView: KanjiBasicContract.View,
    private val kanjiRepository: KanjiRepository
) : KanjiBasicContract.Presenter {

    override fun getKanjiBasicData(lesson: Int) {
        kanjiRepository.getKanjiBasicLocal(
            lesson.toString(),
            object : OnDataLoadedCallback<KanjiBasicResponse> {
                override fun onSuccess(data: KanjiBasicResponse) {
                    kanjiBasicView.showKanjiBasicData(data.kanjiBasicList)
                }

                override fun onFailed(exception: Exception) {
                    kanjiBasicView.showError(exception.message.toString())
                }
            })
    }

    override fun getFavoriteKanjiBasic() {
        kanjiRepository.getFavoriteKanjiBasic(object : OnDataLoadedCallback<KanjiBasicResponse> {
            override fun onSuccess(data: KanjiBasicResponse) {
                kanjiBasicView.showKanjiBasicData(data.kanjiBasicList)
            }

            override fun onFailed(exception: Exception) {
                kanjiBasicView.showError(exception.message.toString())
            }
        })
    }

    override fun updateFavoriteKanjiBasic(kanjiBasic: KanjiBasic) {
        kanjiRepository.updateFavoriteKanjiBasic(
            kanjiBasic,
            object : OnDataLoadedCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                }

                override fun onFailed(exception: Exception) {
                    kanjiBasicView.showError(exception.message.toString())
                }
            })
    }
}
