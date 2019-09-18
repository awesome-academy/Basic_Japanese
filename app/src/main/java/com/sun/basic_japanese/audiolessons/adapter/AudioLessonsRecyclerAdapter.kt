package com.sun.basic_japanese.audiolessons.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sun.basic_japanese.R
import com.sun.basic_japanese.data.model.NHKLessonWithThumbnail
import kotlinx.android.synthetic.main.item_audio_lesson.view.*

class AudioLessonsRecyclerAdapter(
    private val lessons: List<NHKLessonWithThumbnail>
) : RecyclerView.Adapter<AudioLessonsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_audio_lesson, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(lessons[position])
    }

    override fun getItemCount() = lessons.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textLessonTitle: TextView? = itemView.textLessonTitle
        private val imageLessonThumbnail: ImageView? = itemView.imageLessonThumbnail

        fun bindData(lesson: NHKLessonWithThumbnail) {
            textLessonTitle?.text = lesson.lesson.title
            imageLessonThumbnail?.setImageDrawable(lesson.thumbnail)
        }
    }
}
