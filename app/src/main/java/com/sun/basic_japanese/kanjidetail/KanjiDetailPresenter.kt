package com.sun.basic_japanese.kanjidetail

import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class KanjiDetailPresenter(
    private val kanjiBasicView: KanjiDetailContract.View,
    private val kanjiRepository: KanjiRepository
) : KanjiDetailContract.Presenter {

    override fun getStrokeOrder(input: String) {
        kanjiRepository.getStrokeOrder(input, object : OnDataLoadedCallback<String> {
            override fun onSuccess(data: String) {
                kanjiBasicView.showStrokeOrderAnimation(data)
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

    override fun updateFavoriteKanjiAdvance(kanjiAdvance: KanjiAdvance) {
        kanjiRepository.updateFavoriteKanjiAdvance(
            kanjiAdvance,
            object : OnDataLoadedCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                }

                override fun onFailed(exception: Exception) {
                    kanjiBasicView.showError(exception.message.toString())
                }
            })
    }
}
