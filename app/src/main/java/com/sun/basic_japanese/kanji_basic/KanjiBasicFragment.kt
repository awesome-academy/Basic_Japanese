package com.sun.basic_japanese.kanji_basic

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.FragmentInteractionListener
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.KanjiLocalDataSource
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.showToast
import com.sun.basic_japanese.widget.SelectLessonDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_kanji.*

class KanjiBasicFragment @SuppressLint("ValidFragment") private constructor() : BaseFragment(),
    View.OnClickListener,
    KanjiBasicContract.View,
    KanjiBasicRecyclerAdapter.OnKanjiBasicItemClickListener,
    SelectLessonDialogFragment.OnDialogItemClickListener {

    private val local by lazy {
        context?.let { KanjiLocalDataSource.getInstance(AppDatabase.getInstance(it)) }
    }
    private val remote by lazy {
        KanjiRemoteDataSource.getInstance()
    }
    private val kanjiRepository by lazy {
        local?.let { KanjiRepository.getInstance(it, remote) }
    }
    private val kanjiBasicPresenter by lazy {
        kanjiRepository?.let { KanjiBasicPresenter(this, it) }
    }
    private val preferences by lazy {
        context?.getSharedPreferences(
            "${context?.packageName}${Constants.SETTING}",
            Context.MODE_PRIVATE
        )
    }
    private val kanjiBasicRecyclerAdapter = KanjiBasicRecyclerAdapter(this)
    private val selectDialog = SelectLessonDialogFragment.newInstance(Constants.KANJI_BASIC)

    private var listener: OnKanjiBasicFragmentInteractionListener? = null
    private var currentLesson = 1

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnKanjiBasicFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("${context.toString()} $ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_kanji, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        currentLesson = preferences?.getInt(Constants.KANJI_BASIC, 1) ?: 1
        showKanjiBasicLesson(currentLesson)
    }

    override fun showKanjiBasicData(kanjiBasicList: List<KanjiBasic>) {
        kanjiBasicRecyclerAdapter.updateData(kanjiBasicList)
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonLessonSelect -> {
                selectDialog.show(childFragmentManager, selectDialog.toString())
            }

            R.id.buttonLessonPrevious -> showKanjiBasicLesson(currentLesson - 1)

            R.id.buttonLessonNext -> showKanjiBasicLesson(currentLesson + 1)
        }
    }

    override fun showKanjiBasicDetail(currentPosition: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateFavoriteKanji(kanjiBasic: KanjiBasic) {
        kanjiBasicPresenter?.updateFavoriteKanjiBasic(kanjiBasic)
    }


    override fun showKanjiLesson(lesson: Int) {
        showKanjiBasicLesson(lesson)
        selectDialog.dismiss()
    }

    private fun initView() {
        recyclerKanji?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = kanjiBasicRecyclerAdapter
        }

        buttonLessonSelect.setOnClickListener(this)
        buttonLessonPrevious.setOnClickListener(this)
        buttonLessonNext.setOnClickListener(this)

        selectDialog.setListener(this)
    }

    private fun showKanjiBasicLesson(lesson: Int) {
        if (lesson == 0)
            kanjiBasicPresenter?.getFavoriteKanjiBasic()
        else
            kanjiBasicPresenter?.getKanjiBasicData(lesson)
        setTitleLesson(lesson)
        preferences?.edit()?.apply {
            putInt(
                Constants.KANJI_BASIC,
                lesson
            )
            apply()
        }
        currentLesson = lesson
    }

    private fun setTitleLesson(lesson: Int) {
        buttonLessonSelect.text = if (lesson == Constants.KANJI_BASIC_MIN_LESSON)
            resources.getString(R.string.title_favorite)
        else "${resources.getString(R.string.title_lesson)} $lesson"
        buttonLessonPrevious.visibility =
            if (lesson > Constants.KANJI_BASIC_MIN_LESSON) View.VISIBLE else View.GONE
        buttonLessonNext.visibility =
            if (lesson < Constants.KANJI_BASIC_MAX_LESSON) View.VISIBLE else View.GONE
    }

    interface OnKanjiBasicFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnKanjiBasicFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = KanjiBasicFragment()
    }
}
