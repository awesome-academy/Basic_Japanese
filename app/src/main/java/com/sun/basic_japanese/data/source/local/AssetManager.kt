package com.sun.basic_japanese.data.source.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.drawable.Drawable
import com.sun.basic_japanese.constants.BasicJapaneseConstants.ALPHABET_AUDIO_PATH
import com.sun.basic_japanese.constants.BasicJapaneseConstants.LESSON_AUDIO_PATH
import com.sun.basic_japanese.constants.BasicJapaneseConstants.THUMBNAILS_PATH

class AssetManager(private val context: Context) {
    fun getAlphabetAudio(audioFileName: String): AssetFileDescriptor {
        val assetsManager = context.assets
        return assetsManager.openFd(ALPHABET_AUDIO_PATH + audioFileName)
    }

    fun getLessonThumbnail(imageFileName: String): Drawable {
        val assetsManager = context.assets
        return Drawable.createFromStream(
            assetsManager.open(THUMBNAILS_PATH + imageFileName), null
        )
    }

    fun getLessonAudio(audioFileName: String): AssetFileDescriptor {
        val assetsManager = context.assets
        return assetsManager.openFd(LESSON_AUDIO_PATH + audioFileName)
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
