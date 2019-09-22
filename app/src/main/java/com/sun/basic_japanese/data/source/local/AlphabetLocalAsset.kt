package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.AlphabetAudioResponse
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class AlphabetLocalAsset(private val assetManager: AssetManager) {

    fun getAlphabetAudio(audioFileName: String, callback: OnDataLoadedCallback<AlphabetAudioResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, AlphabetAudioResponse> {
            override fun execute(vararg params: String): AlphabetAudioResponse =
                AlphabetAudioResponse(assetManager.getAlphabetAudio(params[0]))
        }, callback).execute(audioFileName)
    }

    companion object {
        @Volatile
        private var instance: AlphabetLocalAsset? = null

        fun getInstance(assetManager: AssetManager) = instance ?: synchronized(this) {
            instance ?: AlphabetLocalAsset(assetManager).also { instance = it }
        }
    }
}
