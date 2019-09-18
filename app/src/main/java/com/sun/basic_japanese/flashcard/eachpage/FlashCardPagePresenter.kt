package com.sun.basic_japanese.flashcard.eachpage

import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.repository.AlphabetRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class FlashCardPagePresenter(
    private val flashCardPageView: FlashCardPageContract.View,
    private val alphabetRepository: AlphabetRepository
) : FlashCardPageContract.Presenter {
    override fun playAudio() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateAlphabetRemember(alphabet: Alphabet) {
        alphabetRepository.updateRememberAlphabet(alphabet, object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean) {
                flashCardPageView.showAlphabetRememberChanged()
            }

            override fun onFailed(exception: Exception) {
                TODO()
            }
        })
    }
}
