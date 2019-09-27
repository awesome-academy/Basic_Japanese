package com.sun.basic_japanese.search

import android.annotation.SuppressLint
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseAdapter
import com.sun.basic_japanese.base.BaseViewHolder
import com.sun.basic_japanese.data.model.KanjiAdvance
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(
    private val listener: OnSearchItemClickListener,
    private val kanjiAdvanceList: MutableList<KanjiAdvance> = mutableListOf()
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>(),
    BaseAdapter<KanjiAdvance> {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = kanjiAdvanceList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBindData(kanjiAdvanceList[position])
    }

    override fun updateData(data: List<KanjiAdvance>) {
        val diffUtil =
            DiffUtil.calculateDiff(KanjiAdvanceUpdateCallback(kanjiAdvanceList, data))
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
        private val listener: OnSearchItemClickListener
    ) : BaseViewHolder<KanjiAdvance>(itemView) {

        @SuppressLint("SetTextI18n")
        override fun onBindData(itemData: KanjiAdvance) {
            super.onBindData(itemData)
            itemView.apply {
                textKanjiAdvanceWord?.text = itemData.word
                textKanjiAdvanceChinaMean?.text = itemData.chinaMean
                textKanjiAdvanceLesson.text =
                    "${resources.getString(R.string.title_lesson)} ${itemData.id / 100 + 1}"
            }
        }

        override fun onHandleItemClick(mainItem: KanjiAdvance) {
            super.onHandleItemClick(mainItem)
            listener.showItemDetail(adapterPosition)
        }
    }

    class KanjiAdvanceUpdateCallback(
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

    interface OnSearchItemClickListener {
        fun showItemDetail(currentPosition: Int)
    }
}
