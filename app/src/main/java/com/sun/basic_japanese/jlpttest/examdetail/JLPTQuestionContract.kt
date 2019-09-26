package com.sun.basic_japanese.jlpttest.examdetail

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.JLPTTest

interface JLPTQuestionContract {
    interface View : BaseView {
        fun showJLPTData(jLPTExam: List<JLPTTest>)
        fun showResult(status: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getJLPTData(category: String)
        fun evaluate(currentQuestion: Int, pickedAnswer: Int)
    }
}
