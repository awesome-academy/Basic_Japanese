package com.sun.basic_japanese.data.source.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun.basic_japanese.data.model.Alphabet
import com.sun.basic_japanese.data.model.NHKLesson
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
        val db = readableDatabase

        return db.update(
            DATABASE_TABLE_ALPHABET,
            values,
            "${Alphabet.DATABASE_TABLE_ALPHABET_COLUMN_ID} = ?",
            arrayOf(alphabet.id.toString())
        ) > 0
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

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: AppDatabase(context).also { instance = it }
        }
    }
}
