package com.sun.basic_japanese.data.source

import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.model.KanjiBasicResponse

interface KanjiDataSource {

    interface Local {
        fun getKanjiBasicLocal(lesson: String, callback: OnDataLoadedCallback<KanjiBasicResponse>)

        fun getKanjiAdvanceLocal(from: String, to: String, callback: OnDataLoadedCallback<KanjiAdvanceResponse>)

        fun getFavoriteKanjiBasic(callback: OnDataLoadedCallback<KanjiBasicResponse>)

        fun getFavoriteKanjiAdvance(callback: OnDataLoadedCallback<KanjiAdvanceResponse>)

        fun updateKanjiBasicLocal(kanjiList: List<KanjiBasic>, callback: OnDataLoadedCallback<Boolean>)

        fun updateKanjiAdvanceLocal(kanjiList: List<KanjiAdvance>, callback: OnDataLoadedCallback<Boolean>)

        fun updateFavoriteKanjiBasic(kanjiBasic: KanjiBasic, callback: OnDataLoadedCallback<Boolean>)

        fun updateFavoriteKanjiAdvance(kanjiAdvance: KanjiAdvance, callback: OnDataLoadedCallback<Boolean>)

        fun updateTagKanjiBasic(kanjiBasic: KanjiBasic, callback: OnDataLoadedCallback<Boolean>)

        fun updateTagKanjiAdvance(kanjiAdvance: KanjiAdvance, callback: OnDataLoadedCallback<Boolean>)

        fun getStrokeOrder(input: String, callback: OnDataLoadedCallback<String>)

        fun searchKanji(query: String, callback: OnDataLoadedCallback<KanjiAdvanceResponse>)
    }

    interface Remote {
        fun getKanjiBasicRemote(lesson: String, callback: OnDataLoadedCallback<KanjiBasicResponse>)

        fun getKanjiAdvanceRemote(from: String, to: String, callback: OnDataLoadedCallback<KanjiAdvanceResponse>)
    }
}
