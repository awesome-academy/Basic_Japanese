package com.sun.basic_japanese.data.repository

import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetsResponse
import com.sun.basic_japanese.data.source.AlphabetDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.data.source.local.AlphabetLocalDataSource

class AlphabetRepository(private val localDataSource: AlphabetLocalDataSource) : AlphabetDataSource.Local {

    override fun getAllAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        localDataSource.getAllAlphabets(callback)
    }

    override fun getRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        localDataSource.getRememberAlphabets(callback)
    }

    override fun getNotRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        localDataSource.getNotRememberAlphabets(callback)
    }

    override fun updateRememberAlphabet(
        alphabet: Alphabet,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateRememberAlphabet(alphabet, callback)
    }

    companion object {
        @Volatile
        private var instance: AlphabetRepository? = null

        fun getInstance(local: AlphabetLocalDataSource) = instance ?: synchronized(this) {
            instance ?: AlphabetRepository(local).also { instance = it }
        }
    }
}
