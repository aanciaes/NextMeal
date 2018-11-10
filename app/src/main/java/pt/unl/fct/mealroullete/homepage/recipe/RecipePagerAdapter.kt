package pt.unl.fct.mealroullete.homepage.recipe

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class RecipePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                RandomRecipeFragment()
            }
            1 -> {
                CreateRecipeFragment()
            }
            else -> {
                FavoriteRecipeFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> {
                "Random"
            }
            1 -> {
                "Create"
            }
            else -> {
                "Favorite"
            }
        }
    }

}
