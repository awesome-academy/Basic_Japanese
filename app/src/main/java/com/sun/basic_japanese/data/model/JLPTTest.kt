package com.sun.basic_japanese.data.model

import android.database.Cursor
import org.json.JSONObject

data class JLPTTest(
    val category: String,
    val correct: String,
    val question: String,
    val answer: String
) {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.optString(JSON_KEY_CATEGORY),
        jsonObject.optString(JSON_KEY_CORRECT),
        jsonObject.optString(JSON_KEY_QUESTION),
        jsonObject.optString(JSON_KEY_ANSWER)
    )

    constructor(cursor: Cursor) : this(
        cursor.getString(cursor.getColumnIndex(JSON_KEY_CATEGORY)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_CORRECT)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_QUESTION)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_ANSWER))
    )

    companion object {
        const val JSON_KEY_CATEGORY = "category"
        const val JSON_KEY_CORRECT = "correct"
        const val JSON_KEY_QUESTION = "question"
        const val JSON_KEY_ANSWER = "answer"
    }
}
