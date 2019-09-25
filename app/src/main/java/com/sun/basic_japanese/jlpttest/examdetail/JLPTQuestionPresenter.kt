package com.sun.basic_japanese.jlpttest.examdetail

import com.sun.basic_japanese.data.model.JLPTTest
import com.sun.basic_japanese.data.model.JLPTTestResponse
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.data.source.remote.JLPTTestRemoteDataSource

class JLPTQuestionPresenter(
    private val jLPTQuestionView: JLPTQuestionContract.View,
    private val jLPTRemoteDataSource: JLPTTestRemoteDataSource
) : JLPTQuestionContract.Presenter {

    private var questions = listOf<JLPTTest>()

    override fun evaluate(currentQuestion: Int, pickedAnswer: Int) {
        jLPTQuestionView.showResult(
            pickedAnswer == questions[currentQuestion].correct.toInt()
        )
    }

    override fun getJLPTData(category: String) {
        jLPTRemoteDataSource.getJLPTTestRemote(category, object : OnDataLoadedCallback<JLPTTestResponse> {
            override fun onSuccess(data: JLPTTestResponse) {
                questions = data.jlptTestList
                jLPTQuestionView.showJLPTData(data.jlptTestList)
            }

            override fun onFailed(exception: Exception) {
                TODO()
            }
        })
    }
}
