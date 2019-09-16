package com.sun.basic_japanese.data.source.remote

import com.sun.basic_japanese.data.model.DataRequest
import com.sun.basic_japanese.data.model.JLPTTestResponse
import com.sun.basic_japanese.data.source.JLPTTestDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.util.Constants

class JLPTTestRemoteDataSource private constructor() : JLPTTestDataSource.Remote {

    override fun getJLPTTestRemote(
        category: String,
        callback: OnDataLoadedCallback<JLPTTestResponse>
    ) {
        val requestURL = DataRequest(
            Constants.SCHEME_HTTP,
            Constants.AUTHORITY_MINNA_MAZIL,
            listOf(Constants.PATH_API, Constants.PATH_JLPT_TEST),
            mapOf(KEY_LESSON to category)
        ).toUrl()
        GetResponseAsync(JLPTTestResponseHandler(), callback).execute(requestURL)
    }

    companion object {
        private const val KEY_LESSON = "lessonid"

        @Volatile
        private var instance: JLPTTestRemoteDataSource? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: JLPTTestRemoteDataSource().also { instance = it }
        }
    }
}
