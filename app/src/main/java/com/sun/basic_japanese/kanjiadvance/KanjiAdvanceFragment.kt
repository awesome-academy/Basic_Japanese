package com.sun.basic_japanese.kanjiadvance

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.FragmentInteractionListener
import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.model.KanjiAdvanceMessage
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.KanjiLocalDataSource
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource
import com.sun.basic_japanese.kanjidetail.KanjiAdvanceDetailFragment
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.showToast
import com.sun.basic_japanese.widget.SelectLessonDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_kanji.*

class KanjiAdvanceFragment @SuppressLint("ValidFragment") private constructor() : BaseFragment(),
    View.OnClickListener,
    KanjiAdvanceContract.View,
    KanjiAdvanceRecyclerAdapter.OnKanjiAdvanceItemClickListener,
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
        kanjiRepository?.let { KanjiAdvancePresenter(this, it) }
    }
    private val preferences by lazy {
        context?.getSharedPreferences(
            "${context?.packageName}${Constants.SETTING}",
            Context.MODE_PRIVATE
        )
    }
    private val kanjiBasicRecyclerAdapter = KanjiAdvanceRecyclerAdapter(this)
    private val selectDialog = SelectLessonDialogFragment.newInstance(Constants.KANJI_ADVANCE)

    private var listener: OnKanjiAdvanceFragmentInteractionListener? = null
    private var kanjiAdvanceList = listOf<KanjiAdvance>()
    private var currentLesson = 1

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnKanjiAdvanceFragmentInteractionListener) {
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
        currentLesson = preferences?.getInt(Constants.KANJI_ADVANCE, 1) ?: 1
        showKanjiAdvanceLesson(currentLesson)
    }

    override fun showKanjiAdvanceData(kanjiAdvanceList: List<KanjiAdvance>) {
        kanjiBasicRecyclerAdapter.updateData(kanjiAdvanceList)
        this.kanjiAdvanceList = kanjiAdvanceList
    }

    override fun showError(message: String) {
        context?.showToast(message)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonLessonSelect -> {
                selectDialog.show(childFragmentManager, selectDialog.toString())
            }

            R.id.buttonLessonPrevious -> showKanjiAdvanceLesson(currentLesson - 1)

            R.id.buttonLessonNext -> showKanjiAdvanceLesson(currentLesson + 1)
        }
    }

    override fun showKanjiAdvanceDetail(currentPosition: Int) {
        getNavigationManager().open(
            KanjiAdvanceDetailFragment.newInstance(
                KanjiAdvanceMessage(kanjiAdvanceList, currentPosition)
            )
        )
    }

    override fun showKanjiLesson(lesson: Int) {
        showKanjiAdvanceLesson(lesson)
        selectDialog.dismiss()
    }

    private fun initView() {
        recyclerKanji?.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = kanjiBasicRecyclerAdapter
        }

        buttonLessonSelect.setOnClickListener(this)
        buttonLessonPrevious.setOnClickListener(this)
        buttonLessonNext.setOnClickListener(this)

        selectDialog.setListener(this)
    }

    private fun showKanjiAdvanceLesson(lesson: Int) {
        val to = lesson * 100
        val from = to - 99
        if (lesson == 0)
            kanjiBasicPresenter?.getFavoriteKanjiAdvance()
        else
            kanjiBasicPresenter?.getKanjiAdvanceData(from, to)
        setTitleLesson(lesson)
        preferences?.edit()?.apply {
            putInt(
                Constants.KANJI_ADVANCE,
                lesson
            )
            apply()
        }
        currentLesson = lesson
    }

    private fun setTitleLesson(lesson: Int) {
        buttonLessonSelect.text = if (lesson == Constants.KANJI_ADVANCE_MIN_LESSON)
            resources.getString(R.string.title_favorite)
        else "${resources.getString(R.string.title_lesson)} $lesson"
        buttonLessonPrevious.visibility =
            if (lesson > Constants.KANJI_ADVANCE_MIN_LESSON) View.VISIBLE else View.INVISIBLE
        buttonLessonNext.visibility =
            if (lesson < Constants.KANJI_ADVANCE_MAX_LESSON) View.VISIBLE else View.INVISIBLE
    }

    interface OnKanjiAdvanceFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnKanjiBasicFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = KanjiAdvanceFragment()
    }
}
