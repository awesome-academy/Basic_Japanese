package com.sun.basic_japanese.search

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.KanjiAdvance

interface SearchContract {
    interface View : BaseView {
        fun showKanjiAdvanceData(kanjiAdvanceList: List<KanjiAdvance>)

        fun showError(message: String)
    }

    interface Presenter : BasePresenter {
        fun getKanjiAdvanceData(query: String)
    }
}
