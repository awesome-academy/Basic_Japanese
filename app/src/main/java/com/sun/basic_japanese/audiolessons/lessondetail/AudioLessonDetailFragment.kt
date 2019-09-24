package com.sun.basic_japanese.audiolessons.lessondetail

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
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
import com.sun.basic_japanese.data.model.Dialogue
import com.sun.basic_japanese.data.model.NHKAudioResponse
import com.sun.basic_japanese.data.model.NHKLesson
import com.sun.basic_japanese.data.source.local.AssetManager
import com.sun.basic_japanese.data.source.local.LessonLocalAsset
import kotlinx.android.synthetic.main.fragment_audio_lesson_detail.*
import kotlinx.android.synthetic.main.fragment_audio_lesson_detail.view.*

class AudioLessonDetailFragment :
    BaseFragment(),
    AudioLessonDetailContract.View,
    RecyclerViewItemClickListener {

    private val lessonLocalAsset by lazy {
        context?.let { LessonLocalAsset.getInstance(AssetManager.getInstance(it)) }
    }
    private val lessonDetailPresenter by lazy {
        lessonLocalAsset?.let { AudioLessonDetailPresenter(this, it) }
    }

    private val lessonAudioPlayer: MediaPlayer by lazy { MediaPlayer() }

    private var audioLesson: NHKLesson? = null

    private var dialogues: List<Dialogue>? = null

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
        audioLesson?.image?.let { lessonDetailPresenter?.getLessonThumbnail(it) }
        setEventClick()
    }

    override fun onBackPressed(): Boolean {
        if (lessonAudioPlayer.isPlaying) {
            lessonAudioPlayer.stop()
        }
        lessonAudioPlayer.reset()
        return super.onBackPressed()
    }

    override fun showDialogueData(dialogues: List<Dialogue>) {
        this.dialogues = dialogues
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

    override fun setLessonThumbnail(thumbnail: Drawable) {
        view?.imageLessonThumbnail?.setImageDrawable(thumbnail)
    }

    override fun setupAudioPlayer(lessonAudio: NHKAudioResponse) {
        lessonAudioPlayer.apply {
            if (isPlaying) stop()
            reset()
            setDataSource(
                lessonAudio.audioDescriptor.fileDescriptor,
                lessonAudio.audioDescriptor.startOffset,
                lessonAudio.audioDescriptor.length
            )
            prepare()
            start()
        }
    }

    override fun onRecyclerViewItemClick(currentPosition: Int) {
        dialogues?.get(currentPosition)?.audio?.let {
            lessonDetailPresenter?.getLessonAudio(it)
        }
    }

    private fun setEventClick() {
        buttonPlayLessonAudio?.setOnClickListener {
            audioLesson?.audio?.let {
                lessonDetailPresenter?.getLessonAudio(it)
            }
        }
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
