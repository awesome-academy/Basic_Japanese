package com.sun.basic_japanese.kanji_advance

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.FragmentInteractionListener
import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.data.repository.KanjiRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.KanjiLocalDataSource
import com.sun.basic_japanese.data.source.remote.KanjiRemoteDataSource
import com.sun.basic_japanese.util.Extensions.showToast
import kotlinx.android.synthetic.main.bottom_navigation.*
import kotlinx.android.synthetic.main.fragment_kanji.*

class KanjiAdvanceFragment @SuppressLint("ValidFragment") private constructor() : BaseFragment(),
    View.OnClickListener,
    KanjiAdvanceContract.View,
    KanjiAdvanceRecyclerAdapter.OnKanjiAdvanceItemClickListener {

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
    private val kanjiBasicRecyclerAdapter = KanjiAdvanceRecyclerAdapter(this)

    private var listener: OnKanjiAdvanceFragmentInteractionListener? = null
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
        showKanjiAdvanceLesson(currentLesson)
    }

    override fun showKanjiAdvanceData(kanjiAdvanceList: List<KanjiAdvance>) {
        kanjiBasicRecyclerAdapter.updateData(kanjiAdvanceList)
    }

    override fun showToast(message: String) {
        context?.showToast(message)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonLessonSelect -> {

            }

            R.id.buttonLessonPrevious -> showKanjiAdvanceLesson(--currentLesson)

            R.id.buttonLessonNext -> showKanjiAdvanceLesson(++currentLesson)
        }
    }

    override fun showKanjiAdvanceDetail(kanjiAdvance: KanjiAdvance) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initView() {
        recyclerKanji?.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = kanjiBasicRecyclerAdapter
        }

        buttonLessonSelect.setOnClickListener(this)
        buttonLessonPrevious.setOnClickListener(this)
        buttonLessonNext.setOnClickListener(this)
    }

    private fun showKanjiAdvanceLesson(lesson: Int) {
        val to = lesson * 100
        val from = to - 99
        kanjiBasicPresenter?.getKanjiAdvanceData(from, to)

        buttonLessonSelect.text = if (currentLesson == MIN_LESSON)
            resources.getString(R.string.title_favorite)
        else "${resources.getString(R.string.title_favorite)} $currentLesson"
        buttonLessonPrevious.visibility =
            if (currentLesson > MIN_LESSON) View.VISIBLE else View.GONE
        buttonLessonNext.visibility = if (currentLesson < MAX_LESSON) View.VISIBLE else View.GONE
    }

    interface OnKanjiAdvanceFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val MIN_LESSON = 0
        private const val MAX_LESSON = 20
        private const val ERROR_IMPLEMENT_FRAGMENT_INTERACTION_LISTENER =
            "must implement OnKanjiBasicFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = KanjiAdvanceFragment()
    }
}
