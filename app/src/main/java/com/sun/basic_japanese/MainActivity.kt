package com.sun.basic_japanese

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sun.basic_japanese.base.BaseFragment
import com.sun.basic_japanese.base.NavigationManager
import com.sun.basic_japanese.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeFragment.OnHomeFragmentInteractionListener {

    private lateinit var navigationManager: NavigationManager
    private lateinit var currentFragment: BaseFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        navigationManager = NavigationManager(supportFragmentManager, R.id.mainContainer)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigationManager.openAsRoot(HomeFragment())
        }
    }

    override fun setToolbarTitle(title: String) {
        collapsingToolbar.title = title
    }

    override fun setToolbarVisibility(show: Boolean) {
    }

    override fun setCurrentFragment(fragment: BaseFragment) {
        currentFragment = fragment
    }
}
