package com.sun.basic_japanese.kanjidetail

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiBasic

interface KanjiDetailContract {
    interface View: BaseView {
        fun showStrokeOrderAnimation(input: String)

        fun showError(message: String)
    }

    interface Presenter: BasePresenter {
        fun getStrokeOrder(input: String)
        
        fun updateFavoriteKanjiAdvance(kanjiAdvance: KanjiAdvance)
    }
}
