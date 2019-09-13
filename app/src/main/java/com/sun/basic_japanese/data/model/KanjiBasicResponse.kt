package com.sun.basic_japanese.data.model

import org.json.JSONArray

data class KanjiBasicResponse(val kanjiBasicList: List<KanjiBasic>) {
    constructor(jsonArray: JSONArray) : this(
        mutableListOf<KanjiBasic>().apply {
            for (index in 0 until jsonArray.length()) {
                add(KanjiBasic(jsonArray.optJSONObject(index)))
            }
        }
    )
}
