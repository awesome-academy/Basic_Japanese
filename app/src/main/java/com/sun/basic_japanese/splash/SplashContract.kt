package com.sun.basic_japanese.splash

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView

interface SplashContract {
    interface View : BaseView {
        fun showProgressDownloadData(message: String, position: Int, progress: Int)

        fun showError(message: String)
    }

    interface Presenter : BasePresenter {
        fun downloadKanjiBasicData(lesson: Int)

        fun downloadKanjiAdvanceData(lesson: Int)

        fun downloadJlptTestData(category: Int)
    }
}
