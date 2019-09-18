package com.sun.basic_japanese.audiolessons

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.audiolessons.adapter.AudioLessonsRecyclerAdapter
import com.sun.basic_japanese.constants.BasicJapaneseConstants.AUDIO_LESSONS_COLUMN_NUMBER
import com.sun.basic_japanese.data.model.NHKLesson
import com.sun.basic_japanese.data.model.NHKLessonWithThumbnail
import com.sun.basic_japanese.data.repository.NHKLessonRepository
import com.sun.basic_japanese.data.source.local.AppDatabase
import com.sun.basic_japanese.data.source.local.NHKLessonLocalDataSource
import kotlinx.android.synthetic.main.fragment_audio_lessons.*

class AudioLessonsFragment : Fragment(), AudioLessonsContract.View {

    private var audioLessonsPresenter: AudioLessonsContract.Presenter? = null
    private var audioLessons = listOf<NHKLesson>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_audio_lessons, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        audioLessonsPresenter?.getLessonsData()
    }

    override fun setLessonsData(lessonItems: List<NHKLesson>) {
        audioLessons = lessonItems
    }

    override fun showLessonsDataWithThumbnails(lessonsThumbnails: List<Drawable>) {
        val gridLayoutManager = GridLayoutManager(
            context,
            AUDIO_LESSONS_COLUMN_NUMBER,
            LinearLayoutManager.VERTICAL,
            false
        )
        val lessonsWithThumbnail = audioLessons.mapIndexed { index, nhkLesson ->
            NHKLessonWithThumbnail(nhkLesson, lessonsThumbnails[index])
        }
        val audioLessonsAdapter = AudioLessonsRecyclerAdapter(lessonsWithThumbnail)
        recyclerAudioLessons.apply {
            layoutManager = gridLayoutManager
            adapter = audioLessonsAdapter
        }
    }

    private fun initPresenter() {
        context?.let {
            val nhkLessonRepository =
                NHKLessonRepository.getInstance(NHKLessonLocalDataSource(AppDatabase.getInstance(it)))
            audioLessonsPresenter = AudioLessonsPresenter(this, nhkLessonRepository)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = AudioLessonsFragment()
    }
}
