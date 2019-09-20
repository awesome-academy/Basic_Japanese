package com.sun.basic_japanese.flashcard.eachpage

import com.sun.basic_japanese.base.BasePresenter
import com.sun.basic_japanese.base.BaseView
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetAudioResponse

interface FlashCardPageContract {

    interface View : BaseView {
        fun showAlphabetRememberChanged()
        fun setupAudioPlayer(alphabetAudio: AlphabetAudioResponse)
    }

    interface Presenter : BasePresenter {
        fun updateAlphabetRemember(alphabet: Alphabet)
        fun getAlphabetAudio(audioFileName: String)
    }
}
