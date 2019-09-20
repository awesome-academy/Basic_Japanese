package com.sun.basic_japanese.widget

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.util.Constants
import kotlinx.android.synthetic.main.fragment_select_lesson_dialog.*

class SelectLessonDialogFragment @SuppressLint("ValidFragment") private constructor() :
    DialogFragment(),
    KanjiLessonRecyclerAdapter.OnKanjiLessonItemClickListener {

    private lateinit var listener: OnDialogItemClickListener
    private val lessonType by lazy {
        arguments?.getString(KANJI_LESSON_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_select_lesson_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = createKanjiLessonList()
        val selectDialogRecyclerAdapter = KanjiLessonRecyclerAdapter(this, list)
        textTitleDialog?.text = resources.getString(R.string.title_select_dialog)
        recyclerLessonDialog?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = selectDialogRecyclerAdapter
        }
    }

    override fun showKanjiLesson(lesson: Int) {
        listener.showKanjiLesson(lesson)
    }

    fun setListener(listener: OnDialogItemClickListener) {
        this.listener = listener
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onStart() {
        super.onStart()
        val with = resources.getDimension(R.dimen.dp_240).toInt()
        val height = resources.getDimension(R.dimen.dp_348).toInt()
        dialog.window.apply {
            setLayout(with, height)
            setGravity(Gravity.CENTER)
            setBackgroundDrawableResource(R.drawable.fragment_background)
        }
    }

    private fun createKanjiLessonList(): List<String> {
        val list = mutableListOf<String>()
        val favorite = resources.getString(R.string.title_favorite)
        val lesson = resources.getString(R.string.title_lesson)
        list.add(favorite)
        when (lessonType) {
            Constants.KANJI_BASIC -> {
                for (i in Constants.KANJI_BASIC_MIN_LESSON + 1 until Constants.KANJI_BASIC_MAX_LESSON + 1) {
                    list.add("$lesson $i")
                }
            }

            Constants.KANJI_ADVANCE -> {
                for (i in Constants.KANJI_ADVANCE_MIN_LESSON + 1 until Constants.KANJI_ADVANCE_MAX_LESSON + 1) {
                    list.add("$lesson $i")
                }
            }
        }

        return list
    }

    companion object {
        private const val KANJI_LESSON_TYPE = "Kanji lesson type"

        @JvmStatic
        fun newInstance(kanjiLessonType: String) = SelectLessonDialogFragment().apply {
            arguments = Bundle().apply {
                putString(KANJI_LESSON_TYPE, kanjiLessonType)
            }
        }
    }

    interface OnDialogItemClickListener {
        fun showKanjiLesson(lesson: Int)
    }
}
