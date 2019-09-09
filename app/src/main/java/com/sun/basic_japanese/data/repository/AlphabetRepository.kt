package com.sun.basic_japanese.data.repository

import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetsResponse
import com.sun.basic_japanese.data.source.AlphabetDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.data.source.local.AlphabetLocalDataSource
import com.sun.basic_japanese.data.source.local.AppDatabase

class AlphabetRepository(private val local: AlphabetLocalDataSource) : AlphabetDataSource.Local {

    override fun getAllAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        local.getAllAlphabets(callback)
    }

    override fun getRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        local.getRememberAlphabets(callback)
    }

    override fun getNotRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        local.getNotRememberAlphabets(callback)
    }

    override fun updateRememberAlphabet(
        alphabet: Alphabet,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        local.updateRememberAlphabet(alphabet, callback)
    }

    companion object {
        private var instance: AlphabetRepository? = null

        fun getInstance(local: AlphabetLocalDataSource): AlphabetRepository =
            instance ?: AlphabetRepository(local).also { instance = it }
    }
}
