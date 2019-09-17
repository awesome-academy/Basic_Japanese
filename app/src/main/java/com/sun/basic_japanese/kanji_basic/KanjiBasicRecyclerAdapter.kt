package com.sun.basic_japanese.kanji_basic

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.BaseAdapter
import com.sun.basic_japanese.base.BaseViewHolder
import com.sun.basic_japanese.data.model.KanjiBasic
import com.sun.basic_japanese.util.Constants
import com.sun.basic_japanese.util.Extensions.setHtmlText
import com.sun.basic_japanese.util.Extensions.setImage
import kotlinx.android.synthetic.main.item_kanji_basic.view.*

class KanjiBasicRecyclerAdapter(
    private val listener: OnKanjiBasicItemClickListener,
    private val kanjiBasicList: MutableList<KanjiBasic> = mutableListOf()
) : RecyclerView.Adapter<KanjiBasicRecyclerAdapter.ViewHolder>(),
    BaseAdapter<KanjiBasic> {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kanji_basic, parent, false)
        return ViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = kanjiBasicList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBindData(kanjiBasicList[position])
    }

    override fun updateData(data: List<KanjiBasic>) {
        val diffUtil =
            DiffUtil.calculateDiff(KanjiBasicUpdateCallback(kanjiBasicList, data))
        loadNewLesson(data)
        diffUtil.dispatchUpdatesTo(this)
    }

    private fun loadNewLesson(kanjiBasicList: List<KanjiBasic>) {
        this.kanjiBasicList.run {
            clear()
            addAll(kanjiBasicList)
        }
    }

    class ViewHolder(
        itemView: View,
        private val listener: OnKanjiBasicItemClickListener
    ) : BaseViewHolder<KanjiBasic>(itemView) {

        override fun onBindData(itemData: KanjiBasic) {
            super.onBindData(itemData)
            itemView.apply {
                textKanjiBasicWord?.text = itemData.word
                textKanjiBasicChinaMean?.text = itemData.chinaMean
                textKanjiBasicOnjomi?.text = itemData.onjomi
                textKanjiBasicKunjomi?.text = itemData.kunjomi
                textKanjiBasicRemember?.setHtmlText(itemData.remember)
                imageKanjiBasicRemember?.setImage(itemData.image)
                when (itemData.favorite) {
                    Constants.TRUE -> buttonKanjiBasicFavorite?.setImageResource(
                        R.drawable.ic_favorite_24dp
                    )

                    Constants.FALSE -> buttonKanjiBasicFavorite?.setImageResource(
                        R.drawable.ic_not_favorite_24dp
                    )
                }
            }
        }

        override fun onHandleItemClick(mainItem: KanjiBasic) {
            super.onHandleItemClick(mainItem)
            listener.showKanjiBasicDetail(mainItem)
        }
    }

    class KanjiBasicUpdateCallback(
        private val oldKanjiBasicList: List<KanjiBasic>,
        private val newKanjiBasicList: List<KanjiBasic>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldKanjiBasicList.size

        override fun getNewListSize() = newKanjiBasicList.size

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int) =
            oldKanjiBasicList[oldPosition].id == newKanjiBasicList[newPosition].id

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int) =
            areItemsTheSame(oldPosition, newPosition)
    }

    interface OnKanjiBasicItemClickListener {
        fun showKanjiBasicDetail(kanjiBasic: KanjiBasic)
    }
}
