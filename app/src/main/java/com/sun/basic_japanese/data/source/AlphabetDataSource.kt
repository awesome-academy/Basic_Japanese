package com.sun.basic_japanese.data.source

import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetAudioResponse
import com.sun.basic_japanese.data.model.AlphabetsResponse

interface AlphabetDataSource {
    interface Local {
        fun getAllAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>)

        fun getAlphabetAudio(audioFilename: String, callback: OnDataLoadedCallback<AlphabetAudioResponse>)

        fun getRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>)

        fun getNotRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>)

        fun updateRememberAlphabet(alphabet: Alphabet, callback: OnDataLoadedCallback<Boolean>)
    }
}
