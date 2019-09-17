package com.sun.basic_japanese.kanji_basic

import com.sun.basic_japanese.data.model.KanjiBasicResponse
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class KanjiBasicPresenter(
    private val kanjiBasicView: KanjiBasicContract.View,
    private val kanjiRepository: KanjiRepository
) : KanjiBasicContract.Presenter {

    override fun getKanjiBasicData(lesson: Int) {
        kanjiRepository.getKanjiBasicRemote(
            lesson.toString(),
            object : OnDataLoadedCallback<KanjiBasicResponse> {
                override fun onSuccess(data: KanjiBasicResponse) {
                    kanjiBasicView.showKanjiBasicData(data.kanjiBasicList)
                }

                override fun onFailed(exception: Exception) {
                    kanjiBasicView.showToast(exception.message.toString())
                }
            })
    }
}
