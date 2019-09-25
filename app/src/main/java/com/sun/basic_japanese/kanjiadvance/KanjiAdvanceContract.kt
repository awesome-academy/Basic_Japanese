package com.sun.basic_japanese.kanjiadvance

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.KanjiAdvance

interface KanjiAdvanceContract {
    interface View: BaseView {
        fun showKanjiAdvanceData(kanjiAdvanceList: List<KanjiAdvance>)

        fun showError(message: String)
    }

    interface Presenter: BasePresenter {
        fun getKanjiAdvanceData(from: Int, to: Int)

        fun getFavoriteKanjiAdvance()
    }
}
