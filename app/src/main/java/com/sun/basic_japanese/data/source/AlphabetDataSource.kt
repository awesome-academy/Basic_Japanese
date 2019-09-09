package com.sun.basic_japanese.data.source

import android.content.Context
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetsResponse

interface AlphabetDataSource {
    interface Local: AlphabetDataSource {
        fun getAllAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>)

        fun getRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>)

        fun getNotRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>)

        fun updateRememberAlphabet(alphabet: Alphabet, callback: OnDataLoadedCallback<Boolean>)
    }
}
