package com.sun.basic_japanese.audiolessons.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.`interface`.RecyclerViewItemClickListener
import com.sun.basic_japanese.data.model.Dialogue
import kotlinx.android.synthetic.main.item_lesson_dialog.view.*

class AudioLessonsDetailRecyclerAdapter(
    private val dialogues: List<Dialogue>,
    private val listener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<AudioLessonsDetailRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_lesson_dialog, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(dialogues[position])
    }

    override fun getItemCount() = dialogues.size

    class ViewHolder(
        itemView: View,
        private val listener: RecyclerViewItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            setClickEvent()
        }

        fun bindData(dialogue: Dialogue) {
            itemView.apply {
                textPersonNameJapanese?.text = dialogue.kanaName
                textPersonNameInternational?.text = dialogue.romajiName
                textDialogJapanese?.text = dialogue.kana
                textDialogInternational?.text = dialogue.romaji
                textDialogVietnamese?.text = dialogue.vn
            }
        }

        private fun setClickEvent() {
            itemView.setOnClickListener {
                listener.onRecyclerViewItemClick(adapterPosition)
            }
        }
    }
}
