package com.sun.basic_japanese.alphabet.eachpage

import com.sun.basic_japanese.constants.BasicJapaneseConstants.WORD_DEFAULT_GROUP
import com.sun.basic_japanese.constants.BasicJapaneseConstants.WORD_MAX_GROUP
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetsResponse
import com.sun.basic_japanese.data.repository.AlphabetRepository
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class AlphabetPagePresenter(
    private val pageView: AlphabetPageContract.View,
    private val alphabetRepository: AlphabetRepository
) : AlphabetPageContract.Presenter {

    override fun getAlphabetData() {

        alphabetRepository.getAllAlphabets(object : OnDataLoadedCallback<AlphabetsResponse> {
            override fun onSuccess(data: AlphabetsResponse) {
                pageView.showAlphabetData(getAlphabetRawData(data))
            }

            override fun onFailed(exception: Exception) {
                //To do
            }
        })
    }

    //Add empty item in specific index to make alphabet table order correct
    private fun getAlphabetRawData(data: AlphabetsResponse): List<Alphabet?> {
        val alphabetItems = mutableListOf<Alphabet?>()
        var wordGroup = WORD_DEFAULT_GROUP
        data.alphabets.forEach {
            if (it.group >= wordGroup) {
                var distance = it.group - wordGroup
                while (distance > 0) {
                    alphabetItems.add(null)
                    distance--
                }
                wordGroup = it.group
                if (wordGroup == WORD_MAX_GROUP) wordGroup =
                    WORD_DEFAULT_GROUP
                else wordGroup++
            } else {
                var distance = WORD_MAX_GROUP - it.group
                while (distance > 0) {
                    alphabetItems.add(null)
                    distance--
                }
                wordGroup = WORD_DEFAULT_GROUP + 1
            }
            alphabetItems.add(it)
        }
        return alphabetItems
    }

}
