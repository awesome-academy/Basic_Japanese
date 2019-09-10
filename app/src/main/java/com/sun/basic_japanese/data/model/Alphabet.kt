package com.sun.basic_japanese.data.model

import android.database.Cursor

data class Alphabet(
    val id: Int,
    val romaji: String,
    val hiragana: String,
    val katakana: String,
    val audio: String,
    val group: Int,
    var remember: Int
) {
    constructor(cursor: Cursor) : this(
        cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ALPHABET_COLUMN_ID)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_ALPHABET_COLUMN_ROMAJI)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_ALPHABET_COLUMN_HIRAGANA)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_ALPHABET_COLUMN_KATAKANA)),
        cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_ALPHABET_COLUMN_AUDIO)),
        cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ALPHABET_COLUMN_GROUPE)),
        cursor.getInt(cursor.getColumnIndex(DATABASE_TABLE_ALPHABET_COLUMN_REMEMBER))
    )

    companion object {
        const val DATABASE_TABLE_ALPHABET_COLUMN_ID = "id"
        const val DATABASE_TABLE_ALPHABET_COLUMN_ROMAJI = "romaji"
        const val DATABASE_TABLE_ALPHABET_COLUMN_HIRAGANA = "hiragana"
        const val DATABASE_TABLE_ALPHABET_COLUMN_KATAKANA = "katakana"
        const val DATABASE_TABLE_ALPHABET_COLUMN_AUDIO = "audio"
        const val DATABASE_TABLE_ALPHABET_COLUMN_GROUPE = "groupe"
        const val DATABASE_TABLE_ALPHABET_COLUMN_REMEMBER = "remember"
    }
}
