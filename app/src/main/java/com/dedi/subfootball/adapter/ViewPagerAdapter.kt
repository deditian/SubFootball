package com.dedi.subfootball.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class ViewPagerAdapter : FragmentPagerAdapter {

    private val fragments : MutableList<Fragment> = mutableListOf()
    private val titles : MutableList<String> = mutableListOf()

    constructor(manager: FragmentManager):super (manager)
    {}

    fun addFragment(fragment: Fragment,title: String) {
        fragments.add(fragment)
        titles.add(title)
    }


    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles.get(position)
    }
}