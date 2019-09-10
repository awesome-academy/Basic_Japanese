package com.sun.basic_japanese.data.model

import org.json.JSONObject

data class Dialogue(
    val kanaName: String,
    val romajiName: String,
    val kana: String,
    val romaji: String,
    val vn: String,
    val audio: String
) {
    constructor(jsonObject: JSONObject) : this(
        jsonObject.optString(JSON_KEY_KANA_NAME),
        jsonObject.optString(JSON_KEY_ROMAJI_NAME),
        jsonObject.optString(JSON_KEY_KANA),
        jsonObject.optString(JSON_KEY_ROMAJI),
        jsonObject.optString(JSON_KEY_VN),
        jsonObject.optString(JSON_KEY_AUDIO)
    )

    companion object {
        const val JSON_KEY_KANA_NAME = "kana_name"
        const val JSON_KEY_ROMAJI_NAME = "romaji_name"
        const val JSON_KEY_KANA = "kana"
        const val JSON_KEY_ROMAJI = "romaji"
        const val JSON_KEY_VN = "vn"
        const val JSON_KEY_AUDIO = "audio"
    }
}
