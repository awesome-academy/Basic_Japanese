package com.sun.basic_japanese.data.repository

import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.model.KanjiBasicResponse
import com.sun.basic_japanese.data.source.KanjiDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource

class KanjiRepository private constructor(
    private val remoteDataSource: KanjiRemoteDataSource
) : KanjiDataSource.Remote {

    override fun getKanjiBasicRemote(
        lesson: String,
        callback: OnDataLoadedCallback<KanjiBasicResponse>
    ) {
        remoteDataSource.getKanjiBasicRemote(lesson, callback)
    }

    override fun getKanjiAdvanceRemote(
        from: String,
        to: String,
        callback: OnDataLoadedCallback<KanjiAdvanceResponse>
    ) {
        remoteDataSource.getKanjiAdvanceRemote(from, to, callback)
    }

    companion object {
        @Volatile
        private var instance: KanjiRepository? = null

        fun getInstance(remote: KanjiRemoteDataSource) = instance ?: synchronized(this) {
            instance ?: KanjiRepository(remote).also { instance = it }
        }
    }
}
