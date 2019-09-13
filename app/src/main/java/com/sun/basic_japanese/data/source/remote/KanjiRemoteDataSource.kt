package com.sun.basic_japanese.data.source.remote

import com.sun.basic_japanese.data.model.DataRequest
import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.model.KanjiBasicResponse
import com.sun.basic_japanese.data.source.KanjiDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.util.Constants

class KanjiRemoteDataSource private constructor() : KanjiDataSource.Remote {

    override fun getKanjiBasicRemote(
        lesson: String,
        callback: OnDataLoadedCallback<KanjiBasicResponse>
    ) {
        val requestURL = DataRequest(
            Constants.SCHEME_HTTP,
            Constants.AUTHORITY_MINNA_MAZIL,
            listOf(Constants.PATH_API, Constants.PATH_KANJI_BASIC),
            mapOf(KEY_LESSON to lesson)
        ).toUrl()
        GetResponseAsync(KanjiBasicResponseHandler(), callback).execute(requestURL)
    }

    override fun getKanjiAdvanceRemote(
        from: String, to: String,
        callback: OnDataLoadedCallback<KanjiAdvanceResponse>
    ) {
        val requestURL = DataRequest(
            Constants.SCHEME_HTTP,
            Constants.AUTHORITY_MINNA_MAZIL,
            listOf(Constants.PATH_API, Constants.PATH_KANJI_ADVANCE),
            mapOf(KEY_FROM to from, KEY_TO to to)
        ).toUrl()
        GetResponseAsync(KanjiAdvanceResponseHandler(), callback).execute(requestURL)
    }

    companion object {
        private const val KEY_LESSON = "lessonid"
        private const val KEY_FROM = "from"
        private const val KEY_TO = "to"

        @Volatile
        private var instance: KanjiRemoteDataSource? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: KanjiRemoteDataSource().also { instance = it }
        }
    }
}
