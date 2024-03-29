package com.sun.basic_japanese.data.model

import android.database.Cursor
import android.os.Parcelable
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.parseImage
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class KanjiBasic(
    override val id: Int,
    override val word: String,
    override val vietMean: String,
    override val chinaMean: String,
    override val onjomi: String,
    override val romajiOnjomi: String,
    override val kunjomi: String,
    override val romajiKunjomi: String,
    override val strokeCount: Int,
    override val example: String,
    val lesson: String,
    val remember: String,
    val image: String,
    override var favorite: String = Constants.FALSE,
    override var tag: String = Constants.FALSE

) : Kanji(), Parcelable {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.optInt(JSON_KEY_ID),
        jsonObject.optString(JSON_KEY_WORD),
        jsonObject.optString(JSON_KEY_VIET_MEAN),
        jsonObject.optString(JSON_KEY_CHINA_MEAN),
        jsonObject.optString(JSON_KEY_ONJOMI),
        jsonObject.optString(JSON_KEY_ROMAJI_ONJOMI),
        jsonObject.optString(JSON_KEY_KUNJOMI),
        jsonObject.optString(JSON_KEY_ROMAJI_KUNJOMI),
        jsonObject.optInt(JSON_KEY_STROKE_COUNT),
        jsonObject.optString(JSON_KEY_EXAMPLE),
        jsonObject.optString(JSON_KEY_LESSON),
        jsonObject.optString(JSON_KEY_REMEMBER),
        jsonObject.optString(JSON_KEY_IMAGE).parseImage()
    )

    constructor(cursor: Cursor) : this(
        cursor.getInt(cursor.getColumnIndex(JSON_KEY_ID)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_WORD)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_VIET_MEAN)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_CHINA_MEAN)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_ONJOMI)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_ROMAJI_ONJOMI)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_KUNJOMI)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_ROMAJI_KUNJOMI)),
        cursor.getInt(cursor.getColumnIndex(JSON_KEY_STROKE_COUNT)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_EXAMPLE)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_LESSON)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_REMEMBER)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_IMAGE)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_FAVORITE)),
        cursor.getString(cursor.getColumnIndex(JSON_KEY_TAG))
    )

    companion object {
        const val JSON_KEY_ID = "id"
        const val JSON_KEY_WORD = "word"
        const val JSON_KEY_VIET_MEAN = "vi_mean"
        const val JSON_KEY_CHINA_MEAN = "cn_mean"
        const val JSON_KEY_ONJOMI = "onjomi"
        const val JSON_KEY_ROMAJI_ONJOMI = "ronjomi"
        const val JSON_KEY_KUNJOMI = "kunjomi"
        const val JSON_KEY_ROMAJI_KUNJOMI = "rkunjomi"
        const val JSON_KEY_STROKE_COUNT = "numstroke"
        const val JSON_KEY_EXAMPLE = "note"
        const val JSON_KEY_LESSON = "lesson"
        const val JSON_KEY_REMEMBER = "remember"
        const val JSON_KEY_IMAGE = "image"
        const val JSON_KEY_FAVORITE = "favorite"
        const val JSON_KEY_TAG = "tag"
    }
}
