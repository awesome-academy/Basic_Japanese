package com.sun.basic_japanese.flashcard.eachpage

import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetAudioResponse
import com.sun.basic_japanese.data.repository.AlphabetRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class FlashCardPagePresenter(
    private val flashCardPageView: FlashCardPageContract.View,
    private val alphabetRepository: AlphabetRepository
) : FlashCardPageContract.Presenter {

    override fun getAlphabetAudio(audioFileName: String) {
        alphabetRepository.getAlphabetAudio(audioFileName, object : OnDataLoadedCallback<AlphabetAudioResponse> {
            override fun onSuccess(data: AlphabetAudioResponse) {
                flashCardPageView.setupAudioPlayer(data)
            }

            override fun onFailed(exception: Exception) {
                TODO()
            }
        })
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
