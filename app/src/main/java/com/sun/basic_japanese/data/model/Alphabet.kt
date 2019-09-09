package com.sun.basic_japanese.data.model

import android.database.Cursor
import com.sun.basic_japanese.data.source.local.AppDatabase

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
        cursor.getInt(cursor.getColumnIndex(AppDatabase.DATABASE_COLUMN_ID)),
        cursor.getString(cursor.getColumnIndex(AppDatabase.DATABASE_COLUMN_ROMAJI)),
        cursor.getString(cursor.getColumnIndex(AppDatabase.DATABASE_COLUMN_HIRAGANA)),
        cursor.getString(cursor.getColumnIndex(AppDatabase.DATABASE_COLUMN_KATAKANA)),
        cursor.getString(cursor.getColumnIndex(AppDatabase.DATABASE_COLUMN_AUDIO)),
        cursor.getInt(cursor.getColumnIndex(AppDatabase.DATABASE_COLUMN_GROUPE)),
        cursor.getInt(cursor.getColumnIndex(AppDatabase.DATABASE_COLUMN_REMEMBER))
    )
}
