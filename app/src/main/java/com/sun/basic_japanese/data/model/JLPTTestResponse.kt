package com.sun.basic_japanese.data.model

import org.json.JSONArray

data class JLPTTestResponse(val jlptTestList: List<JLPTTest>) {
    constructor(jsonArray: JSONArray) : this(
        mutableListOf<JLPTTest>().apply {
            for (index in 0 until jsonArray.length()) {
                add(JLPTTest(jsonArray.optJSONObject(index)))
            }
        }
    )
}
