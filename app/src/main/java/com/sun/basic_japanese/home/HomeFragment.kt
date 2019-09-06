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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        setView()
    }

    private fun setView() {
        alphabet.icon.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_sort_by_alpha_black_24dp, null
            )
        )
        alphabet.label.text = resources.getText(R.string.bang_chu_cai)
        audio.icon.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_headset_black_24dp, null
            )
        )
        audio.label.text = resources.getText(R.string.hoc_tieng_nhat_nkh)
        kanjiBasic.icon.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_text_fields_black_24dp, null
            )
        )
        kanjiBasic.label.text = resources.getText(R.string.kanji)
        kanjiWriting.icon.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_mode_edit_black_24dp, null
            )
        )
        kanjiWriting.label.text = resources.getText(R.string.tap_viet_kanji)
        test.icon.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_school_black_24dp, null
            )
        )
        test.label.text = resources.getText(R.string.thi_jlpt)
        translate.icon.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_translate_black_24dp, null
            )
        )
        translate.label.text = resources.getText(R.string.dich_tieng_nhat)
    }

    interface OnHomeFragmentInteractionListener : FragmentInteractionListener

    companion object {
        private const val LISTENER_NULL_EXCEPTION = "must implement OnHomeFragmentInteractionListener"
    }
}
