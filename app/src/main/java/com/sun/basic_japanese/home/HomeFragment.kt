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
        alphabet?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_sort_by_alpha_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.alphabet)
        }
        audio?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_headset_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.audio_learning)
        }
    }

    private fun setKanjiView() {
        kanjiBasic?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_text_fields_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.kanji)
        }
        kanjiWriting?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_mode_edit_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.kanji_writing)
        }
    }

    private fun setTestView() {
        test?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_school_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.jlpt_test)
        }
        translate?.let {
            it.imageIcon?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_translate_black_24dp, null
                )
            )
            it.textLabel?.text = resources.getText(R.string.translate)
        }
    }

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val LISTENER_NULL_EXCEPTION = "must implement OnHomeFragmentInteractionListener"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
