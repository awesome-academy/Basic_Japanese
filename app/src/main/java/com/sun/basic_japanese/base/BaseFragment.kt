package com.sun.basic_japanese.base

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {

    private lateinit var navigationManagerInner: NavigationManager
    private lateinit var fragmentInteractionInner: FragmentInteractionListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        navigationManagerInner =
            if (parentFragment != null && parentFragment is HasNavigationManager)
                (parentFragment as HasNavigationManager).provideNavigationManager()
            else if (context is HasNavigationManager)
                (context as HasNavigationManager).provideNavigationManager()
            else
                throw RuntimeException("Activity host must implement HasNavigationManager")

        if (context is Activity)
            fragmentInteractionInner = context as FragmentInteractionListener
        else
            throw RuntimeException("Activity host must implement FragmentInteractionListener")
    }

    override fun onStart() {
        super.onStart()
        fragmentInteractionInner.setCurrentFragment(this)
    }

    fun getNavigationManager(): NavigationManager = navigationManagerInner

    open fun onBackPressed(): Boolean = false

}
