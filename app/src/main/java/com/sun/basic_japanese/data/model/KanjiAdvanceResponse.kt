package com.sun.basic_japanese.data.model

import org.json.JSONArray

data class KanjiAdvanceResponse(val kanjiAdvanceList: List<KanjiAdvance>) {
    constructor(jsonArray: JSONArray) : this(
        mutableListOf<KanjiAdvance>().apply {
            for (index in 0 until jsonArray.length()) {
                add(KanjiAdvance(jsonArray.optJSONObject(index)))
            }
        }
    )
}
