package com.sun.basic_japanese.kanjidetail

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseAdapter
import com.sun.basic_japanese.base.BaseViewHolder
import com.sun.basic_japanese.data.model.Example
import kotlinx.android.synthetic.main.item_kanji_example.view.*

class KanjiExampleRecyclerAdapter(
    private val kanjiExampleList: MutableList<Example> = mutableListOf()
) : RecyclerView.Adapter<KanjiExampleRecyclerAdapter.ViewHolder>(),
    BaseAdapter<Example> {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kanji_example, parent, false)
        return ViewHolder(itemView)    }

    override fun getItemCount(): Int = kanjiExampleList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBindData(kanjiExampleList[position])
    }

    override fun updateData(data: List<Example>) {
        val diffUtil =
            DiffUtil.calculateDiff(KanjiExampleUpdateCallback(kanjiExampleList, data))
        loadNewExample(data)
        diffUtil.dispatchUpdatesTo(this)    }

    private fun loadNewExample(kanjiExampleList: List<Example>) {
        this.kanjiExampleList.run {
            clear()
            addAll(kanjiExampleList)
        }
    }

    class ViewHolder(itemView: View) : BaseViewHolder<Example>(itemView) {
        override fun onBindData(itemData: Example) {
            super.onBindData(itemData)
            itemView.apply {
                textExampleKanji?.text = itemData.kanji
                textExampleKana?.text = itemData.kana
                textExampleVn?.text = itemData.vn
            }
        }
    }

    class KanjiExampleUpdateCallback(
        private val oldKanjiExampleList: List<Example>,
        private val newKanjiExampleList: List<Example>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldKanjiExampleList.size

        override fun getNewListSize() = newKanjiExampleList.size

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
            oldKanjiExampleList[oldPosition].kanji == newKanjiExampleList[newPosition].kanji

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int) =
            areItemsTheSame(oldPosition, newPosition)
    }
}
