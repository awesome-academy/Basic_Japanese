package com.sun.basic_japanese.flashcard.eachpage

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.Alphabet

interface FlashCardPageContract {

    interface View : BaseView {
        fun showAlphabetRememberChanged()
    }

    interface Presenter : BasePresenter {
        fun playAudio()
        fun updateAlphabetRemember(alphabet: Alphabet)
    }
}
