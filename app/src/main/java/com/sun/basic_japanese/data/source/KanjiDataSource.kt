package com.sun.basic_japanese.data.source

import com.sun.basic_japanese.data.model.KanjiAdvanceResponse
import com.sun.basic_japanese.data.model.KanjiBasicResponse

interface KanjiDataSource {

    interface Remote {
        fun getKanjiBasicRemote(lesson: String, callback: OnDataLoadedCallback<KanjiBasicResponse>)

        fun getKanjiAdvanceRemote(from: String, to: String, callback: OnDataLoadedCallback<KanjiAdvanceResponse>)
    }
}
