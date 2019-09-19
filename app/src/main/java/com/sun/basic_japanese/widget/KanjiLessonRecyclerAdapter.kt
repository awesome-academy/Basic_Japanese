package com.sun.basic_japanese.widget

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_lesson_dialog.view.*

class KanjiLessonRecyclerAdapter(
    private val listener: OnKanjiLessonItemClickListener,
    private val lessonList: List<String>
) : RecyclerView.Adapter<KanjiLessonRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lesson_dialog, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = lessonList.size

    override fun onBindViewHolder(viewHoler: ViewHolder, position: Int) {
        viewHoler.onBindData(lessonList[position])
    }

    class ViewHolder(
        itemView: View,
        private val listener: OnKanjiLessonItemClickListener
    ) : BaseViewHolder<String>(itemView) {
        override fun onBindData(itemData: String) {
            super.onBindData(itemData)
            itemView.textTitleLesson.text = itemData
        }

        override fun onHandleItemClick(mainItem: String) {
            super.onHandleItemClick(mainItem)
            listener.showKanjiLesson(adapterPosition)
        }
    }

    interface OnKanjiLessonItemClickListener {
        fun showKanjiLesson(lesson: Int)
    }
}
