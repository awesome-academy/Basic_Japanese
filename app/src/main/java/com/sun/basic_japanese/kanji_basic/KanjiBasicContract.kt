package com.sun.basic_japanese.kanji_basic

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.KanjiBasic

interface KanjiBasicContract {

    interface View: BaseView {
        fun showKanjiBasicData(kanjiBasicList: List<KanjiBasic>)

        fun showToast(message: String)
    }

    interface Presenter: BasePresenter {
        fun getKanjiBasicData(lesson: Int)
    }
}
