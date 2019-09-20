package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.AlphabetAudioResponse
import com.sun.basic_japanese.data.model.AlphabetsResponse
import com.sun.basic_japanese.data.source.AlphabetDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.util.Constants

class AlphabetLocalDataSource(private val database: AppDatabase) : AlphabetDataSource.Local {

    override fun getAlphabetAudio(audioFilename: String, callback: OnDataLoadedCallback<AlphabetAudioResponse>) {
        LoadDataAsync(callback).execute(AlphabetAudioResponse(database.getAlphabetAudio(audioFilename)))
    }

    override fun getAllAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, AlphabetsResponse> {
            override fun execute(vararg params: String): AlphabetsResponse =
                AlphabetsResponse(database.getAlphabets())
        }, callback).execute(Constants.EMPTY_STRING)
    }

    override fun getRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, AlphabetsResponse> {
            override fun execute(vararg params: String): AlphabetsResponse =
                AlphabetsResponse(database.getAlphabetsByRemember(REMEMBER))
        }, callback).execute(Constants.EMPTY_STRING)
    }

    override fun getNotRememberAlphabets(callback: OnDataLoadedCallback<AlphabetsResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, AlphabetsResponse> {
            override fun execute(vararg params: String): AlphabetsResponse =
                AlphabetsResponse(database.getAlphabetsByRemember(NOT_REMEMBER))
        }, callback).execute(Constants.EMPTY_STRING)
    }

    override fun updateRememberAlphabet(
        alphabet: Alphabet,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<Alphabet, Boolean> {
            override fun execute(vararg params: Alphabet): Boolean =
                database.updateRememberAlphabet(params[0])
        }, callback).execute(alphabet)
    }

    companion object {
        private const val REMEMBER = 1
        private const val NOT_REMEMBER = 0

        @Volatile
        private var instance: AlphabetLocalDataSource? = null

        fun getInstance(database: AppDatabase) = instance ?: synchronized(this) {
            instance ?: AlphabetLocalDataSource(database).also { instance = it }
        }
    }
}
