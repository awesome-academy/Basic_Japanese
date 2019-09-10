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
    private val alphabetType: String?
) : RecyclerView.Adapter<AlphabetRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_word, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindData(alphabetItems[position], alphabetType)
    }

    override fun getItemCount() = alphabetItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textWordLabel: TextView? = itemView.textAlphabetJapanese
        private val textWordDescription: TextView? = itemView.textAlphabetInternational

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
    }

    companion object {
        private const val EMPTY_TITLE = ""
    }
}
