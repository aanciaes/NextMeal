package pt.unl.fct.mealroullete.homepage.history

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class HistoryPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                DailyHistoryFragment()
            }
            1 -> {
                WeeklyHistoryFragment()
            }
            else -> {
                MonthlyHistoryFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> {
                "Daily"
            }
            1 -> {
                "Weekly"
            }
            else -> {
                "Monthly"
            }
        }
    }

}

