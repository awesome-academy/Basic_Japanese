package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.model.KanjiBasicResponse
import com.sun.basic_japanese.data.source.KanjiDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback

class KanjiLocalDataSource private constructor(
    private val database: AppDatabase
) : KanjiDataSource.Local {

    override fun getKanjiBasicLocal(
        lesson: String,
        callback: OnDataLoadedCallback<KanjiBasicResponse>
    ) {
        LoadDataAsync(callback).execute(KanjiBasicResponse(database.getKanjiBasicLocal(lesson)))
    }

    override fun getKanjiAdvanceLocal(
        from: String, to: String,
        callback: OnDataLoadedCallback<KanjiAdvanceResponse>
    ) {
        LoadDataAsync(callback).execute(
            KanjiAdvanceResponse(database.getKanjiAdvanceLocal(from, to))
        )
    }

    override fun getFavoriteKanjiBasic(callback: OnDataLoadedCallback<KanjiBasicResponse>) {
        LoadDataAsync(callback).execute(KanjiBasicResponse(database.getFavoriteKanjiBasic()))
    }

    override fun getFavoriteKanjiAdvance(callback: OnDataLoadedCallback<KanjiAdvanceResponse>) {
        LoadDataAsync(callback).execute(KanjiAdvanceResponse(database.getFavoriteKanjiAdvance()))
    }

    override fun updateKanjiBasicLocal(
        kanjiList: List<KanjiBasic>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateKanjiBasicLocal(kanjiList))
    }

    override fun updateKanjiAdvanceLocal(
        kanjiList: List<KanjiAdvance>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateKanjiAdvanceLocal(kanjiList))
    }

    override fun updateFavoriteKanjiBasic(
        kanjiBasic: KanjiBasic,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateFavoriteKanjiBasic(kanjiBasic))
    }

    override fun updateFavoriteKanjiAdvance(
        kanjiAdvance: KanjiAdvance,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateFavoriteKanjiAdvance(kanjiAdvance))
    }

    override fun updateTagKanjiBasic(
        kanjiBasic: KanjiBasic,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateTagKanjiBasic(kanjiBasic))
    }

    override fun updateTagKanjiAdvance(
        kanjiAdvance: KanjiAdvance,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(callback).execute(database.updateTagKanjiAdvance(kanjiAdvance))
    }

    companion object {

        @Volatile
        private var instance: KanjiLocalDataSource? = null

        fun getInstance(database: AppDatabase) = instance ?: synchronized(this) {
            instance ?: KanjiLocalDataSource(database).also { instance = it }
        }
    }
}