package com.sun.basic_japanese.audiolessons.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sun.basic_japanese.R
import com.sun.basic_japanese.data.model.NHKLesson
import kotlinx.android.synthetic.main.item_audio_lesson.view.*

class AudioLessonsRecyclerAdapter(
    private val lessonItems: List<NHKLesson>
) : RecyclerView.Adapter<AudioLessonsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_audio_lesson, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(lessonItems[position])
    }

    override fun getItemCount() = lessonItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textLessonTitle: TextView? = itemView.textLessonTitle

        fun bindData(audioLesson: NHKLesson) {
            textLessonTitle?.text = audioLesson.title
        }
    }
}
