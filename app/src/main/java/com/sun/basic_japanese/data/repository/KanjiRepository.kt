package com.sun.basic_japanese.data.repository

import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.model.KanjiBasicResponse
import com.sun.basic_japanese.data.source.KanjiDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.data.source.local.KanjiLocalDataSource
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource

class KanjiRepository private constructor(
    private val localDataSource: KanjiLocalDataSource,
    private val remoteDataSource: KanjiRemoteDataSource

) : KanjiDataSource.Local,
    KanjiDataSource.Remote {

    override fun getKanjiBasicLocal(
        lesson: String,
        callback: OnDataLoadedCallback<KanjiBasicResponse>
    ) {
        localDataSource.getKanjiBasicLocal(lesson, callback)
    }

    override fun getKanjiAdvanceLocal(
        from: String,
        to: String,
        callback: OnDataLoadedCallback<KanjiAdvanceResponse>
    ) {
        localDataSource.getKanjiAdvanceLocal(from, to, callback)
    }

    override fun getFavoriteKanjiBasic(callback: OnDataLoadedCallback<KanjiBasicResponse>) {
        localDataSource.getFavoriteKanjiBasic(callback)
    }

    override fun getFavoriteKanjiAdvance(callback: OnDataLoadedCallback<KanjiAdvanceResponse>) {
        localDataSource.getFavoriteKanjiAdvance(callback)
    }

    override fun updateKanjiBasicLocal(
        kanjiList: List<KanjiBasic>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateKanjiBasicLocal(kanjiList, callback)
    }

    override fun updateKanjiAdvanceLocal(
        kanjiList: List<KanjiAdvance>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateKanjiAdvanceLocal(kanjiList, callback)
    }

    override fun updateFavoriteKanjiBasic(
        kanjiBasic: KanjiBasic,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateFavoriteKanjiBasic(kanjiBasic, callback)
    }

    override fun updateFavoriteKanjiAdvance(
        kanjiAdvance: KanjiAdvance,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateFavoriteKanjiAdvance(kanjiAdvance, callback)
    }

    override fun updateTagKanjiBasic(
        kanjiBasic: KanjiBasic,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateTagKanjiBasic(kanjiBasic, callback)
    }

    override fun updateTagKanjiAdvance(
        kanjiAdvance: KanjiAdvance,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        localDataSource.updateTagKanjiAdvance(kanjiAdvance, callback)
    }

    override fun getStrokeOrder(input: String, callback: OnDataLoadedCallback<String>) {
        localDataSource.getStrokeOrder(input, callback)
    }

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

        fun getInstance(
            local: KanjiLocalDataSource,
            remote: KanjiRemoteDataSource
        ) = instance ?: synchronized(this) {
            instance ?: KanjiRepository(local, remote).also { instance = it }
        }
    }
}
