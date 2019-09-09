package com.sun.basic_japanese.data.source.local

import android.content.Context
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetsResponse
import com.sun.basic_japanese.data.source.AlphabetDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class AlphabetLocalDataSource(private val database: AppDatabase) : AlphabetDataSource.Local {

    override fun getAllAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        LoadDataAsync(callback).execute(AlphabetsResponse(database.getAlphabets()))
    }

    override fun getRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        LoadDataAsync(callback).execute(AlphabetsResponse(database.getAlphabetsByRemember(REMEMBER)))
    }

    override fun getNotRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        LoadDataAsync(callback).execute(
            AlphabetsResponse(
                database.getAlphabetsByRemember(
                    NOT_REMEMBER
                )
            )
        )
    }

    override fun updateRememberAlphabet(
        alphabet: Alphabet,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateRememberAlphabet(alphabet))
    }


    companion object {
        private const val REMEMBER = 1
        private const val NOT_REMEMBER = 0

        private var instance: AlphabetLocalDataSource? = null

        fun getInstance(database: AppDatabase): AlphabetLocalDataSource =
            instance ?: AlphabetLocalDataSource(database).also { instance = it }

    }
}
