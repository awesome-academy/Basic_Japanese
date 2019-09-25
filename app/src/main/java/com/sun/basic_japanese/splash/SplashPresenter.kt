package com.sun.basic_japanese.splash

import com.sun.basic_japanese.data.model.*
import com.sun.basic_japanese.data.repository.JLPTTestRepository
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.util.Constants

class SplashPresenter(
    private val splashView: SplashContract.View,
    private val kanjiRepository: KanjiRepository,
    private val jlptTestRepository: JLPTTestRepository
) : SplashContract.Presenter {

    override fun downloadKanjiBasicData(lesson: Int) {
        kanjiRepository.getKanjiBasicRemote(
            lesson.toString(),
            object : OnDataLoadedCallback<KanjiBasicResponse> {
                override fun onSuccess(data: KanjiBasicResponse) {
                    updateKanjiBasicLocal(lesson, data.kanjiBasicList)
                }

                override fun onFailed(exception: Exception) {
                    splashView.showError(exception.message.toString())
                }
            })
    }

    override fun downloadKanjiAdvanceData(lesson: Int) {
        val to = lesson * 100
        val from = to - 99
        kanjiRepository.getKanjiAdvanceRemote(
            from.toString(),
            to.toString(),
            object : OnDataLoadedCallback<KanjiAdvanceResponse> {
                override fun onSuccess(data: KanjiAdvanceResponse) {
                    updateKanjiAdvanceLocal(lesson, data.kanjiAdvanceList)
                }

                override fun onFailed(exception: Exception) {
                    splashView.showError(exception.message.toString())
                }
            })
    }

    override fun downloadJlptTestData(category: Int) {
        jlptTestRepository.getJLPTTestRemote(
            category.toString(),
            object : OnDataLoadedCallback<JLPTTestResponse> {
                override fun onSuccess(data: JLPTTestResponse) {
                    updateJLPTTestLocal(category, data.jlptTestList)
                }

                override fun onFailed(exception: Exception) {
                    splashView.showError(exception.message.toString())
                }
            })
    }

    private fun updateKanjiBasicLocal(lesson: Int, data: List<KanjiBasic>) {
        kanjiRepository.updateKanjiBasicLocal(
            data,
            object : OnDataLoadedCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    splashView.showProgressDownloadData(
                        Constants.LOAD_KANJI_BASIC_DATA,
                        lesson,
                        lesson * 100 / Constants.KANJI_BASIC_MAX_LESSON
                    )
                }

                override fun onFailed(exception: Exception) {
                    splashView.showError(exception.message.toString())
                }
            })
    }

    private fun updateKanjiAdvanceLocal(lesson: Int, data: List<KanjiAdvance>) {
        kanjiRepository.updateKanjiAdvanceLocal(
            data,
            object : OnDataLoadedCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    splashView.showProgressDownloadData(
                        Constants.LOAD_KANJI_ADVANCE_DATA,
                        lesson,
                        lesson * 100 / Constants.KANJI_ADVANCE_MAX_LESSON
                    )
                }

                override fun onFailed(exception: Exception) {
                    splashView.showError(exception.message.toString())
                }
            })
    }

    private fun updateJLPTTestLocal(category: Int, data: List<JLPTTest>) {
        jlptTestRepository.updateJLPTTestLocal(
            data,
            object : OnDataLoadedCallback<Boolean> {
                override fun onSuccess(data: Boolean) {
                    splashView.showProgressDownloadData(
                        Constants.LOAD_JLPT_TEST_DATA,
                        category,
                        category * 100 / Constants.JLPT_TEST_COUNT
                    )
                }

                override fun onFailed(exception: Exception) {
                    splashView.showError(exception.message.toString())
                }
            })
    }
}
