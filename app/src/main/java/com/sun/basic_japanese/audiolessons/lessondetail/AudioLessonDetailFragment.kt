package com.sun.basic_japanese.audiolessons.lessondetail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.`interface`.RecyclerViewItemClickListener
import com.sun.basic_japanese.audiolessons.adapter.AudioLessonsDetailRecyclerAdapter
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.constants.BasicJapaneseConstants.LESSON
import com.sun.basic_japanese.data.model.DialogueWithAudio
import com.sun.basic_japanese.data.model.NHKLesson
import com.sun.basic_japanese.data.repository.NHKLessonRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.NHKLessonLocalDataSource
import kotlinx.android.synthetic.main.fragment_audio_lesson_detail.*
import kotlinx.android.synthetic.main.fragment_audio_lesson_detail.view.*

class AudioLessonDetailFragment :
    BaseFragment(),
    AudioLessonDetailContract.View,
    RecyclerViewItemClickListener {

    private val local by lazy {
        context?.let { NHKLessonLocalDataSource.getInstance(AppDatabase.getInstance(it)) }
    }

    private val nhkRepository by lazy {
        local?.let { NHKLessonRepository.getInstance(it) }
    }

    private val lessonDetailPresenter by lazy {
        nhkRepository?.let { AudioLessonDetailPresenter(this, it) }
    }

    private var audioLesson: NHKLesson? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run { audioLesson = getParcelable(LESSON) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_audio_lesson_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayLessonDescription(view)
        audioLesson?.dialogues?.let { lessonDetailPresenter?.setDialogue(it) }
    }

    override fun showDialogueData(dialogues: List<DialogueWithAudio>) {
        val linearLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val audioLessonDetailAdapter = AudioLessonsDetailRecyclerAdapter(dialogues, this)
        recyclerLessonDialogs?.apply {
            layoutManager = linearLayoutManager
            adapter = audioLessonDetailAdapter
        }
    }

    override fun onRecyclerViewItemClick(currentPosition: Int) {
        TODO()
    }

    private fun displayLessonDescription(view: View) {
        view.apply {
            textLessonTitle?.text = audioLesson?.title
            textLessonDescription?.text = audioLesson?.detail
            textLessonGrammar?.text = audioLesson?.grammar
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(nhkLesson: NHKLesson) =
            AudioLessonDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LESSON, nhkLesson)
                }
            }
    }
}
