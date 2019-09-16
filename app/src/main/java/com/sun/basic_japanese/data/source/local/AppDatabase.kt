package com.sun.basic_japanese.data.source.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun.basic_japanese.constants.BasicJapaneseConstants.EMPTY_STRING
import com.sun.basic_japanese.data.model.*
import com.sun.basic_japanese.util.Constants
import java.io.File
import java.io.FileOutputStream

class AppDatabase private constructor(
    private val context: Context
) : SQLiteOpenHelper(
    context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    private val preferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getWritableDatabase()
    }

    fun getAlphabets(): MutableList<Alphabet> {
        val alphabets = mutableListOf<Alphabet>()
        val db = readableDatabase
        val cursor = db.query(DATABASE_TABLE_ALPHABET, null, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val alphabet = Alphabet(cursor)
                alphabets.add(alphabet)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return alphabets
    }

    fun getAlphabetsByRemember(remember: Int): MutableList<Alphabet> {
        val alphabets = mutableListOf<Alphabet>()
        val db = readableDatabase
        val cursor = db.query(
            DATABASE_TABLE_ALPHABET,
            null,
            "${Alphabet.DATABASE_TABLE_ALPHABET_COLUMN_REMEMBER} = ?",
            arrayOf(remember.toString()),
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val alphabet = Alphabet(cursor)
                alphabets.add(alphabet)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return alphabets
    }

    @Synchronized
    fun updateRememberAlphabet(alphabet: Alphabet): Boolean {
        val values = ContentValues().apply {
            put(Alphabet.DATABASE_TABLE_ALPHABET_COLUMN_REMEMBER, alphabet.remember)
        }
        val db = writableDatabase

        val result = db.update(
            DATABASE_TABLE_ALPHABET,
            values,
            "${Alphabet.DATABASE_TABLE_ALPHABET_COLUMN_ID} = ?",
            arrayOf(alphabet.id.toString())
        )

        db.close()
        return result != -1
    }

    fun getNHKLessons(): MutableList<NHKLesson> {
        val nhkLessons = mutableListOf<NHKLesson>()
        val db = readableDatabase
        val cursor = db.query(DATABASE_TABLE_NHK_LESSON, null, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val nhkLesson = NHKLesson(cursor)
                nhkLessons.add(nhkLesson)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return nhkLessons
    }

    fun getKanjiBasicLocal(lesson: String): List<KanjiBasic> {
        val kanjiList = mutableListOf<KanjiBasic>()

        val db = readableDatabase
        val cursor = db.query(
            DATABASE_TABLE_KANJI_BASIC,
            null,
            "${KanjiBasic.JSON_KEY_LESSON} = ?",
            arrayOf(lesson),
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val kanjiBasic = KanjiBasic(cursor)
                kanjiList.add(kanjiBasic)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return kanjiList
    }

    fun getKanjiAdvanceLocal(from: String, to: String): List<KanjiAdvance> {
        val kanjiList = mutableListOf<KanjiAdvance>()

        val db = readableDatabase
        val cursor = db.query(
            DATABASE_TABLE_KANJI_ADVANCE,
            null,
            "${KanjiAdvance.JSON_KEY_ID} >= ? AND ${KanjiAdvance.JSON_KEY_ID} <= ?",
            arrayOf(from, to),
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val kanjiAdvance = KanjiAdvance(cursor)
                kanjiList.add(kanjiAdvance)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return kanjiList
    }

    fun getFavoriteKanjiBasic(): List<KanjiBasic> {
        val kanjiList = mutableListOf<KanjiBasic>()

        val db = readableDatabase
        val cursor = db.query(
            DATABASE_TABLE_KANJI_BASIC,
            null,
            "${KanjiBasic.JSON_KEY_FAVORITE} = ?",
            arrayOf(Constants.TRUE),
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val kanjiBasic = KanjiBasic(cursor)
                kanjiList.add(kanjiBasic)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return kanjiList
    }

    fun getFavoriteKanjiAdvance(): List<KanjiAdvance> {
        val kanjiList = mutableListOf<KanjiAdvance>()

        val db = readableDatabase
        val cursor = db.query(
            DATABASE_TABLE_KANJI_ADVANCE,
            null,
            "${KanjiAdvance.JSON_KEY_FAVORITE} = ?",
            arrayOf(Constants.TRUE),
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val kanjiAdvance = KanjiAdvance(cursor)
                kanjiList.add(kanjiAdvance)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return kanjiList
    }

    @Synchronized
    fun updateKanjiBasicLocal(kanjiList: List<KanjiBasic>): Boolean {
        val db = writableDatabase
        val cursor = db.query(
            DATABASE_TABLE_KANJI_BASIC,
            null,
            "${KanjiBasic.JSON_KEY_ID} = ?",
            arrayOf(kanjiList[0].id.toString()),
            null,
            null,
            null
        )
        val values = ContentValues()

        if (cursor != null && !cursor.moveToFirst()) {
            for (index in kanjiList.indices) {
                values.apply {
                    put(KanjiBasic.JSON_KEY_ID, kanjiList[index].id)
                    put(KanjiBasic.JSON_KEY_WORD, kanjiList[index].word)
                    put(KanjiBasic.JSON_KEY_VIET_MEAN, kanjiList[index].vietMean)
                    put(KanjiBasic.JSON_KEY_CHINA_MEAN, kanjiList[index].chinaMean)
                    put(KanjiBasic.JSON_KEY_ONJOMI, kanjiList[index].id)
                    put(KanjiBasic.JSON_KEY_ROMAJI_ONJOMI, kanjiList[index].romajiOnjomi)
                    put(KanjiBasic.JSON_KEY_KUNJOMI, kanjiList[index].kunjomi)
                    put(KanjiBasic.JSON_KEY_ROMAJI_KUNJOMI, kanjiList[index].romajiKunjomi)
                    put(KanjiBasic.JSON_KEY_STROKE_COUNT, kanjiList[index].strokeCount)
                    put(KanjiBasic.JSON_KEY_EXAMPLE, kanjiList[index].example)
                    put(KanjiBasic.JSON_KEY_LESSON, kanjiList[index].lesson)
                    put(KanjiBasic.JSON_KEY_REMEMBER, kanjiList[index].remember)
                    put(KanjiBasic.JSON_KEY_IMAGE, kanjiList[index].image)
                    put(KanjiBasic.JSON_KEY_FAVORITE, kanjiList[index].favorite)
                    put(KanjiBasic.JSON_KEY_TAG, kanjiList[index].tag)
                }
                if (db.insert(DATABASE_TABLE_KANJI_BASIC, null, values) == -1L) {
                    return false
                }
            }
        }

        cursor.close()
        db.close()
        return true
    }

    @Synchronized
    fun updateKanjiAdvanceLocal(kanjiList: List<KanjiAdvance>): Boolean {
        val db = writableDatabase
        val cursor = db.query(DATABASE_TABLE_KANJI_ADVANCE, null, null, null, null, null, null)
        val values = ContentValues()

        if (cursor != null && !cursor.moveToFirst()) {
            for (index in kanjiList.indices) {
                values.apply {
                    put(KanjiAdvance.JSON_KEY_ID, kanjiList[index].id)
                    put(KanjiAdvance.JSON_KEY_WORD, kanjiList[index].word)
                    put(KanjiAdvance.JSON_KEY_VIET_MEAN, kanjiList[index].vietMean)
                    put(KanjiAdvance.JSON_KEY_CHINA_MEAN, kanjiList[index].chinaMean)
                    put(KanjiAdvance.JSON_KEY_ONJOMI, kanjiList[index].onjomi)
                    put(KanjiAdvance.JSON_KEY_ROMAJI_ONJOMI, kanjiList[index].romajiOnjomi)
                    put(KanjiAdvance.JSON_KEY_KUNJOMI, kanjiList[index].kunjomi)
                    put(KanjiAdvance.JSON_KEY_ROMAJI_KUNJOMI, kanjiList[index].romajiKunjomi)
                    put(KanjiAdvance.JSON_KEY_STROKE_COUNT, kanjiList[index].strokeCount)
                    put(KanjiAdvance.JSON_KEY_EXAMPLE, kanjiList[index].example)
                    put(KanjiAdvance.JSON_KEY_FAVORITE, kanjiList[index].favorite)
                    put(KanjiAdvance.JSON_KEY_TAG, kanjiList[index].tag)
                }
                if (db.insert(DATABASE_TABLE_KANJI_ADVANCE, null, values) == -1L) {
                    return false
                }
            }
        }

        cursor.close()
        db.close()
        return true
    }

    @Synchronized
    fun updateFavoriteKanjiBasic(kanjiBasic: KanjiBasic): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KanjiBasic.JSON_KEY_FAVORITE, kanjiBasic.favorite)
        }

        val result = db.update(
            DATABASE_TABLE_KANJI_BASIC,
            values,
            "${KanjiBasic.JSON_KEY_ID} = ?",
            arrayOf(kanjiBasic.id.toString())
        )

        db.close()
        return result != -1
    }

    @Synchronized
    fun updateFavoriteKanjiAdvance(kanjiAdvance: KanjiAdvance): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KanjiAdvance.JSON_KEY_FAVORITE, kanjiAdvance.favorite)
        }

        val result = db.update(
            DATABASE_TABLE_KANJI_ADVANCE,
            values,
            "${KanjiAdvance.JSON_KEY_ID} = ?",
            arrayOf(kanjiAdvance.id.toString())
        )

        db.close()
        return result != -1
    }

    @Synchronized
    fun updateTagKanjiBasic(kanjiBasic: KanjiBasic): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KanjiBasic.JSON_KEY_TAG, kanjiBasic.tag)
        }

        val result = db.update(
            DATABASE_TABLE_KANJI_BASIC,
            values,
            "${KanjiBasic.JSON_KEY_ID} = ?",
            arrayOf(kanjiBasic.id.toString())
        )

        db.close()
        return result != -1
    }

    @Synchronized
    fun updateTagKanjiAdvance(kanjiAdvance: KanjiAdvance): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(KanjiAdvance.JSON_KEY_TAG, kanjiAdvance.tag)
        }

        val result = db.update(
            DATABASE_TABLE_KANJI_ADVANCE,
            values,
            "${KanjiBasic.JSON_KEY_ID} = ?",
            arrayOf(kanjiAdvance.id.toString())
        )

        db.close()
        return result != -1
    }

    fun getJLPTTestLocal(category: String): List<JLPTTest> {
        val jlptTests = mutableListOf<JLPTTest>()

        val db = readableDatabase
        val cursor = db.query(
            DATABASE_TABLE_JLPT_TEST,
            null,
            "${JLPTTest.JSON_KEY_CATEGORY} = ?",
            arrayOf(category),
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val jlptTest = JLPTTest(cursor)
                jlptTests.add(jlptTest)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return jlptTests
    }

    @Synchronized
    fun updateJLPTTestLocal(jlptTest:  List<JLPTTest>): Boolean {
        val db = writableDatabase
        val cursor = db.query(
            DATABASE_TABLE_JLPT_TEST,
            null,
            "${JLPTTest.JSON_KEY_CATEGORY} = ?",
            arrayOf(jlptTest[0].category),
            null,
            null,
            null
        )
        val values = ContentValues()

        if (cursor != null && !cursor.moveToFirst()) {
            for (index in jlptTest.indices) {
                values.apply {
                    put(JLPTTest.JSON_KEY_CATEGORY, jlptTest[index].category)
                    put(JLPTTest.JSON_KEY_CORRECT, jlptTest[index].correct)
                    put(JLPTTest.JSON_KEY_QUESTION, jlptTest[index].question)
                    put(JLPTTest.JSON_KEY_ANSWER, jlptTest[index].answer)
                }
                if (db.insert(DATABASE_TABLE_JLPT_TEST, null, values) == -1L) {
                    return false
                }
            }
        }

        cursor.close()
        db.close()
        return true
    }

    fun getStrokeOrder(input: String): String? {
        val id =
            STROKE_ORDER_DEFAULT_ID + input.codePointAt(FRIST_CHAR_INDEX).toString(HEXA_DECIMAL)
        var result: String? = null
        val db = readableDatabase
        val cursor = db.query(
            DATABASE_TABLE_STROKE_ORDER,
            null,
            "$DATABASE_TABLE_STROKE_ORDER_COLUMN_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            LIMIT_1
        )

        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(DATABASE_TABLE_STROKE_ORDER_COLUMN_XML))
        }

        cursor.close()
        db.close()

        return result ?: EMPTY_STRING
    }

    private fun installDatabaseFromAssets() {
        val inputStream = context.assets.open("$ASSETS_DB_PATH/$DATABASE_NAME")

        try {
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            if (!outputFile.exists()) {
                val checkDatabase =
                    context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null)
                checkDatabase?.close()
            }
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        } catch (exception: Throwable) {
            throw RuntimeException(ERROR_MESSAGE, exception)
        }
    }

    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) != DATABASE_VERSION
    }

    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(
                DATABASE_NAME,
                DATABASE_VERSION
            )
            apply()
        }
    }

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    companion object {
        private const val ASSETS_DB_PATH = "databases"
        private const val DATABASE_NAME = "AppDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val ERROR_MESSAGE = "The $DATABASE_NAME database could't be installed"
        private const val DATABASE_TABLE_ALPHABET = "Alphabet"
        private const val DATABASE_TABLE_NHK_LESSON = "NHKLesson"
        private const val DATABASE_TABLE_KANJI_BASIC = "KanjiBasic"
        private const val DATABASE_TABLE_KANJI_ADVANCE = "KanjiAdvance"
        private const val DATABASE_TABLE_JLPT_TEST = "JLPTTest"
        private const val DATABASE_TABLE_STROKE_ORDER = "StrokeOrder"
        private const val DATABASE_TABLE_STROKE_ORDER_COLUMN_ID = "id"
        private const val DATABASE_TABLE_STROKE_ORDER_COLUMN_XML = "xml"
        private const val STROKE_ORDER_DEFAULT_ID = "kvg:kanji_0"
        private const val HEXA_DECIMAL = 16
        private const val FRIST_CHAR_INDEX = 0
        private const val LIMIT_1 = "1"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: AppDatabase(context).also { instance = it }
        }
    }
}
