package com.graphtech.giserap.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.graphtech.giserap.R
import com.graphtech.giserap.fragment.FollowerFragment
import com.graphtech.giserap.fragment.FollowingFragment

class DetailPagerAdapter(private val context: Context, fm: FragmentManager, private val username: String) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.follower, R.string.following)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowerFragment().newInstance(username)
            }

            1 -> {
                fragment = FollowingFragment().newInstance(username)
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}