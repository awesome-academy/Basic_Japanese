package com.sun.basic_japanese.kanji_advance

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseAdapter
import com.sun.basic_japanese.base.BaseViewHolder
import com.sun.basic_japanese.data.model.KanjiAdvance
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.getStringBySplit
import kotlinx.android.synthetic.main.item_kanji_advance.view.*

class KanjiAdvanceRecyclerAdapter(
    private val listener: OnKanjiAdvanceItemClickListener,
    private val kanjiAdvanceList: MutableList<KanjiAdvance> = mutableListOf()
) : RecyclerView.Adapter<KanjiAdvanceRecyclerAdapter.ViewHolder>(),
    BaseAdapter<KanjiAdvance> {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kanji_advance, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = kanjiAdvanceList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBindData(kanjiAdvanceList[position])
    }

    override fun updateData(data: List<KanjiAdvance>) {
        val diffUtil =
            DiffUtil.calculateDiff(KanjiBasicUpdateCallback(kanjiAdvanceList, data))
        loadNewLesson(data)
        diffUtil.dispatchUpdatesTo(this)
    }

    private fun loadNewLesson(kanjiBasicList: List<KanjiAdvance>) {
        this.kanjiAdvanceList.run {
            clear()
            addAll(kanjiBasicList)
        }
    }

    class ViewHolder(
        itemView: View,
        private val listener: OnKanjiAdvanceItemClickListener
    ) : BaseViewHolder<KanjiAdvance>(itemView) {

        override fun onBindData(itemData: KanjiAdvance) {
            super.onBindData(itemData)
            itemView.apply {
                textKanjiAdvanceWord?.text = itemData.word
                textKanjiAdvanceChinaMean?.text =
                    itemData.chinaMean.getStringBySplit(Constants.CHARACTER_SPLIT_3)
            }
        }

        override fun onHandleItemClick(mainItem: KanjiAdvance) {
            super.onHandleItemClick(mainItem)
            listener.showKanjiAdvanceDetail(adapterPosition)
        }
    }

    class KanjiBasicUpdateCallback(
        private val oldKanjiAdvanceList: List<KanjiAdvance>,
        private val newKanjiAdvanceList: List<KanjiAdvance>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldKanjiAdvanceList.size

        override fun getNewListSize() = newKanjiAdvanceList.size

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
            oldKanjiAdvanceList[oldPosition].id == newKanjiAdvanceList[newPosition].id

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int) =
            areItemsTheSame(oldPosition, newPosition)
    }

    interface OnKanjiAdvanceItemClickListener {
        fun showKanjiAdvanceDetail(currentPosition: Int)
    }
}
