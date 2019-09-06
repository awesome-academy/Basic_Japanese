package com.sun.basic_japanese.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.sun.basic_japanese.R

class NavigationManager(
    private val fragmentManager: FragmentManager,
    private val container: Int
) {

    val isRootFragmentVisible: Boolean
        get() = fragmentManager.backStackEntryCount <= 1

    private var navigationListener: (() -> Unit)? = null

    init {
        fragmentManager.addOnBackStackChangedListener {
            navigationListener?.invoke()
        }
    }

    fun open(fragment: Fragment) {
        openFragment(fragment, addToBackStack = true, isRoot = false)
    }

    fun openAsRoot(fragment: Fragment) {
        popEveryFragment()
        openFragment(fragment, addToBackStack = false, isRoot = true)
    }

    fun navigateBack(): Boolean {
        return if (fragmentManager.backStackEntryCount == 0)
            false
        else {
            fragmentManager.popBackStackImmediate()
            true
        }
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean, isRoot: Boolean) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (isRoot)
            fragmentTransaction.replace(container, fragment, "ROOT")
        else
            fragmentTransaction.replace(container, fragment)

        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_left,
            R.anim.slide_out_right,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )

        if (addToBackStack)
            fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.commit()
    }

    private fun popEveryFragment() {
        fragmentManager.popBackStackImmediate("ROOT", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
