package pt.unl.fct.mealroullete.homepage.poll

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PollPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CreatePollFragment()
            }
            1 -> {
                ActivePollFragment()
            }
            else -> {
                ClosedPollFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> {
                "Create"
            }
            1 -> {
                "Active"
            }
            else -> {
                "Closed"
            }
        }
    }

}
