package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.LessonThumbnailResponse
import com.sun.basic_japanese.data.model.NHKAudioResponse
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class LessonLocalAsset(private val assetManager: AssetManager) {

    fun getLessonThumbnail(imageFileName: String, callback: OnDataLoadedCallback<LessonThumbnailResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, LessonThumbnailResponse> {
            override fun execute(vararg params: String): LessonThumbnailResponse =
                LessonThumbnailResponse(assetManager.getLessonThumbnail(params[0]))
        }, callback).execute(imageFileName)
    }

    fun getLessonAudio(audioFileName: String, callback: OnDataLoadedCallback<NHKAudioResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, NHKAudioResponse> {
            override fun execute(vararg params: String): NHKAudioResponse =
                NHKAudioResponse(assetManager.getLessonAudio(params[0]))
        }, callback).execute(audioFileName)
    }

    companion object {
        @Volatile
        private var instance: LessonLocalAsset? = null

        fun getInstance(assetManager: AssetManager) = instance ?: synchronized(this) {
            instance ?: LessonLocalAsset(assetManager).also { instance = it }
        }
    }
}
