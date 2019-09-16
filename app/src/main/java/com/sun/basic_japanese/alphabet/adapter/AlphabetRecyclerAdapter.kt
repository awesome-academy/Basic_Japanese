package com.sun.basic_japanese.alphabet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sun.basic_japanese.R
import com.sun.basic_japanese.constants.BasicJapaneseConstants.HIRAGANA
import com.sun.basic_japanese.data.model.Alphabet
import kotlinx.android.synthetic.main.item_word.view.*

class AlphabetRecyclerAdapter(
    private val alphabetItems: List<Alphabet?>,
    private val alphabetType: String?,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<AlphabetRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_word, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(alphabetItems[position], alphabetType)
    }

    override fun getItemCount() = alphabetItems.size
    class ViewHolder(
        itemView: View,
        private val listener: RecyclerViewClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val textWordLabel: TextView? = itemView.textAlphabetJapanese
        private val textWordDescription: TextView? = itemView.textAlphabetInternational

        init {
            setClickEvent()
        }

        fun bindData(alphabet: Alphabet?, alphabetType: String?) {
            if (alphabet != null) {
                if (alphabetType == HIRAGANA) textWordLabel?.text = alphabet.hiragana
                else textWordLabel?.text = alphabet.katakana
                textWordDescription?.text = alphabet.romaji
            } else {
                textWordLabel?.text = EMPTY_TITLE
                textWordDescription?.text = EMPTY_TITLE
            }
        }

        private fun setClickEvent() {
            itemView.setOnClickListener {
                listener.onRecyclerViewItemClick(adapterPosition)
            }
        }
    }

    interface RecyclerViewClickListener {
        fun onRecyclerViewItemClick(currentPosition: Int)
    }

    companion object {
        private const val EMPTY_TITLE = ""
    }
}
