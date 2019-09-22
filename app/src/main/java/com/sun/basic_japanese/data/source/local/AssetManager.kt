package com.sun.basic_japanese.data.source.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetFileDescriptor
import com.sun.basic_japanese.constants.BasicJapaneseConstants.ALPHABET_AUDIO_PATH

class AssetManager(private val context: Context) {
    fun getAlphabetAudio(audioFileName: String): AssetFileDescriptor {
        val assetsManager = context.assets
        return assetsManager.openFd(ALPHABET_AUDIO_PATH + audioFileName)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: AssetManager? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: AssetManager(context).also { instance = it }
        }
    }
}
