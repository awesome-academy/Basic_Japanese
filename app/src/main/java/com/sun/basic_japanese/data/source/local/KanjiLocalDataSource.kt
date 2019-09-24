package com.sun.basic_japanese.data.source.local

import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.model.KanjiBasicResponse
import com.sun.basic_japanese.data.source.KanjiDataSource
import com.sun.basic_japanese.data.source.OnDataLoadedCallback
import com.sun.basic_japanese.util.Constants

class KanjiLocalDataSource private constructor(
    private val database: AppDatabase
) : KanjiDataSource.Local {

    override fun getKanjiBasicLocal(
        lesson: String,
        callback: OnDataLoadedCallback<KanjiBasicResponse>
    ) {
        LoadDataAsync(object : LocalDataHandler<String, KanjiBasicResponse> {
            override fun execute(vararg params: String): KanjiBasicResponse =
                KanjiBasicResponse(database.getKanjiBasicLocal(params[0]))
        }, callback).execute(lesson)
    }

    override fun getKanjiAdvanceLocal(
        from: String, to: String,
        callback: OnDataLoadedCallback<KanjiAdvanceResponse>
    ) {
        LoadDataAsync(object : LocalDataHandler<String, KanjiAdvanceResponse> {
            override fun execute(vararg params: String): KanjiAdvanceResponse =
                KanjiAdvanceResponse(database.getKanjiAdvanceLocal(params[0], params[1]))
        }, callback).execute(from, to)
    }

    override fun getFavoriteKanjiBasic(callback: OnDataLoadedCallback<KanjiBasicResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, KanjiBasicResponse> {
            override fun execute(vararg params: String): KanjiBasicResponse =
                KanjiBasicResponse(database.getFavoriteKanjiBasic())
        }, callback).execute(Constants.EMPTY_STRING)
    }

    override fun getFavoriteKanjiAdvance(callback: OnDataLoadedCallback<KanjiAdvanceResponse>) {
        LoadDataAsync(object : LocalDataHandler<String, KanjiAdvanceResponse> {
            override fun execute(vararg params: String): KanjiAdvanceResponse =
                KanjiAdvanceResponse(database.getFavoriteKanjiAdvance())
        }, callback).execute(Constants.EMPTY_STRING)
    }

    override fun updateKanjiBasicLocal(
        kanjiList: List<KanjiBasic>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<List<KanjiBasic>, Boolean> {
            override fun execute(vararg params: List<KanjiBasic>): Boolean =
                database.updateKanjiBasicLocal(params[0])
        }, callback).execute(kanjiList)
    }

    override fun updateKanjiAdvanceLocal(
        kanjiList: List<KanjiAdvance>,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<List<KanjiAdvance>, Boolean> {
            override fun execute(vararg params: List<KanjiAdvance>): Boolean =
                database.updateKanjiAdvanceLocal(params[0])
        }, callback).execute(kanjiList)
    }

    override fun updateFavoriteKanjiBasic(
        kanjiBasic: KanjiBasic,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<KanjiBasic, Boolean> {
            override fun execute(vararg params: KanjiBasic): Boolean =
                database.updateFavoriteKanjiBasic(params[0])
        }, callback).execute(kanjiBasic)
    }

    override fun updateFavoriteKanjiAdvance(
        kanjiAdvance: KanjiAdvance,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<KanjiAdvance, Boolean> {
            override fun execute(vararg params: KanjiAdvance): Boolean =
                database.updateFavoriteKanjiAdvance(params[0])
        }, callback).execute(kanjiAdvance)
    }

    override fun updateTagKanjiBasic(
        kanjiBasic: KanjiBasic,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<KanjiBasic, Boolean> {
            override fun execute(vararg params: KanjiBasic): Boolean =
                database.updateTagKanjiBasic(params[0])
        }, callback).execute(kanjiBasic)
    }

    override fun updateTagKanjiAdvance(
        kanjiAdvance: KanjiAdvance,
        callback: OnDataLoadedCallback<Boolean>
    ) {
        LoadDataAsync(object : LocalDataHandler<KanjiAdvance, Boolean> {
            override fun execute(vararg params: KanjiAdvance): Boolean =
                database.updateTagKanjiAdvance(params[0])
        }, callback).execute(kanjiAdvance)
    }

    override fun getStrokeOrder(input: String, callback: OnDataLoadedCallback<String>) {
        LoadDataAsync(object : LocalDataHandler<String, String> {
            override fun execute(vararg params: String): String =
                database.getStrokeOrder(params[0])
        }, callback).execute(input)
    }

    companion object {

        @Volatile
        private var instance: KanjiLocalDataSource? = null

        fun getInstance(database: AppDatabase) = instance ?: synchronized(this) {
            instance ?: KanjiLocalDataSource(database).also { instance = it }
        }
    }
}