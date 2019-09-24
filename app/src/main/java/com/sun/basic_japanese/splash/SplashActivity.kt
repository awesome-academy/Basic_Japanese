package com.sun.basic_japanese.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.sun.basic_japanese.MainActivity
import com.sun.basic_japanese.R
import com.sun.basic_japanese.data.repository.JLPTTestRepository
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.JLPTTestLocalDataSource
import com.sun.basic_japanese.data.source.local.KanjiLocalDataSource
import com.sun.basic_japanese.data.source.remote.JLPTTestRemoteDataSource
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.showToast
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(),
    SplashContract.View {

    private val kanjiLocal by lazy {
        KanjiLocalDataSource.getInstance(AppDatabase.getInstance(this))
    }
    private val kanjiRemote by lazy {
        KanjiRemoteDataSource.getInstance()
    }
    private val kanjiRepository by lazy {
        KanjiRepository.getInstance(kanjiLocal, kanjiRemote)
    }
    private val jlptTestLocal by lazy {
        JLPTTestLocalDataSource.getInstance(AppDatabase.getInstance(this))
    }
    private val jlptTestRemote by lazy {
        JLPTTestRemoteDataSource.getInstance()
    }
    private val jlptTestRepository by lazy {
        JLPTTestRepository.getInstance(jlptTestLocal, jlptTestRemote)
    }
    private val splashPresenter by lazy {
        SplashPresenter(this, kanjiRepository, jlptTestRepository)
    }
    private val preferences by lazy {
        getSharedPreferences("${packageName}${Constants.SETTING}", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val isDownloadData = preferences.getBoolean(Constants.LOAD_DATA, false)
        if (!isDownloadData) {
            titleDownload.text = resources.getString(R.string.title_progress_load_kanji_basic_data)
            splashPresenter.downloadKanjiBasicData(1)
        } else {
            Handler().postDelayed({
                showHomeScreen()
            }, 500)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showProgressDownloadData(message: String, position: Int, progress: Int) {
        when (message) {
            Constants.LOAD_KANJI_BASIC_DATA -> {
                textProgressDownload.text = "$progress%"
                if (position == Constants.KANJI_BASIC_MAX_LESSON) {
                    titleDownload.text =
                        resources.getString(R.string.title_progress_load_kanji_advance_data)
                    splashPresenter.downloadKanjiAdvanceData(1)
                } else {
                    splashPresenter.downloadKanjiBasicData(position + 1)
                }
            }

            Constants.LOAD_KANJI_ADVANCE_DATA -> {
                textProgressDownload.text = "$progress%"
                if (position == Constants.KANJI_ADVANCE_MAX_LESSON) {
                    titleDownload.text =
                        resources.getString(R.string.title_progress_load_jlpt_test_data)
                    splashPresenter.downloadJlptTestData(1)
                } else {
                    splashPresenter.downloadKanjiAdvanceData(position + 1)
                }
            }

            Constants.LOAD_JLPT_TEST_DATA -> {
                textProgressDownload.text = "$progress%"
                if (position == Constants.JLPT_TEST_COUNT) {
                    preferences?.edit()?.apply {
                        putBoolean(
                            Constants.LOAD_DATA,
                            true
                        )
                        apply()
                    }
                    showHomeScreen()
                } else {
                    splashPresenter.downloadJlptTestData(position + 1)
                }
            }
        }
    }


    override fun showError(message: String) {
        showToast(message)
    }

    private fun showHomeScreen() {
        startActivity(getMainIntent(this))
        finish()
    }

    companion object {
        fun  getMainIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
