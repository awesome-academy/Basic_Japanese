package com.sun.basic_japanese.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.basic_japanese.R
import com.sun.basic_japanese.base.FragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.one_line_element.view.*
import java.lang.RuntimeException

class HomeFragment : Fragment() {

    private var listener: OnHomeFragmentInteractionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnHomeFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context $LISTENER_NULL_EXCEPTION")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        setBasicView()
        setKanjiView()
        setTestView()
    }

    private fun setBasicView() {
        layoutAlphabet?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_sort_by_alpha_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.title_alphabet)
        }
        layoutAudio?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_headset_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.title_audio_learning)
        }
    }

    private fun setKanjiView() {
        layoutKanjiBasic?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_text_fields_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.title_kanji)
        }
        layoutKanjiWriting?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_mode_edit_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.title_kanji_writing)
        }
    }

    private fun setTestView() {
        layoutTest?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_school_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.title_jlpt_test)
        }
        layoutTranslate?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_translate_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.title_translate)
        }
    }

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val LISTENER_NULL_EXCEPTION =
            "must implement OnHomeFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
