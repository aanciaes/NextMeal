package pt.unl.fct.mealroullete.mealgenerator.customize

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.mealgenerator.GeneratorHome
import pt.unl.fct.mealroullete.persistance.MockDatabase
import android.support.design.widget.TabLayout




class RecipePresentation : FragmentActivity() {

    private lateinit var mPager: ViewPager

    val generatedRecipes = MockDatabase.recipesList.shuffled().take(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_presentation)

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.recipe_viewer)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter

        val tabLayout = findViewById<TabLayout>(R.id.slider_indicator)
        tabLayout.setupWithViewPager(mPager, true)

        //Listeners
        setBackToGeneratorListener()
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    private fun setBackToGeneratorListener() {
        val back = findViewById<ImageButton>(R.id.backToStep2)
        back.setOnClickListener {
            startActivity(Intent(this, GeneratorHome::class.java))
        }
    }

    inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int = generatedRecipes.size

        override fun getItem(position: Int): Fragment = screenSlidePageFragment(position)

        private fun screenSlidePageFragment(position: Int): Fragment {
            val frag: Fragment = Card()
            val bundle = Bundle()
            bundle.putInt("currentItem", position)

            frag.arguments = bundle

            return frag
        }
    }
}

class Card : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.card_default_layout, container, false)

        val currentPos = arguments?.getInt("currentItem")!!
        val cardFront = CardFrontFragment()
        val bundle = Bundle()
        bundle.putInt("currentItem", currentPos)

        cardFront.arguments = bundle

        this.childFragmentManager.beginTransaction()
                .add(R.id.card_container, cardFront)
                .commit()

        return view
    }
}

class CardFrontFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val act = activity as RecipePresentation

        val recipes = act.generatedRecipes
        val currentPos = arguments?.getInt("currentItem")!!

        val view = inflater.inflate(R.layout.card_front, container, false)
        val recipeName = view.findViewById<TextView>(R.id.recipeName)
        recipeName.text = recipes[currentPos].name

        setFlipCardListener(view, currentPos)
        return view
    }

    private fun setFlipCardListener(view: View, currentPos: Int) {
        val container = view.findViewById<ConstraintLayout>(R.id.recipe_layout_container)

        container.setOnClickListener {
            val cardBack = CardBackFragment()
            val bundle = Bundle()
            bundle.putInt("currentItem", currentPos)

            cardBack.arguments = bundle

            this.parentFragment!!.childFragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out
                    ).replace(R.id.card_container, cardBack).addToBackStack(null).commit()
        }
    }
}

class CardBackFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val currentPos = arguments?.getInt("currentItem")!!

        val view = inflater.inflate(R.layout.card_back, container, false)
        val fav = view.findViewById<ImageButton>(R.id.favorite_vote)
        fav.setOnClickListener {
            if (view.findViewById<TextView>(R.id.check_fav).text.equals("full")) {
                fav.setBackgroundResource(R.drawable.heart_favorite_empty)
                view.findViewById<TextView>(R.id.check_fav).setText("empty")
            } else {
                fav.setBackgroundResource(R.drawable.heart_favorite_full)
                view.findViewById<TextView>(R.id.check_fav).setText("full")
            }
        }

        val upVote = view.findViewById<ImageButton>(R.id.upVote)
        val downVote = view.findViewById<ImageButton>(R.id.downVote)
        upVote.setOnClickListener {
            if (view.findViewById<TextView>(R.id.upVoteState).text.equals("full")) {
                upVote.setBackgroundResource(R.drawable.vote_up_empty)
                view.findViewById<TextView>(R.id.upVoteState).setText("empty")
            } else {
                if (view.findViewById<TextView>(R.id.downVoteState).text.equals("full")) {
                    downVote.setBackgroundResource(R.drawable.vote_down_empty)
                    view.findViewById<TextView>(R.id.downVoteState).setText("empty")
                }
                upVote.setBackgroundResource(R.drawable.vote_up_full)
                view.findViewById<TextView>(R.id.upVoteState).setText("full")
            }
        }

        downVote.setOnClickListener {
            if (view.findViewById<TextView>(R.id.downVoteState).text.equals("full")) {
                downVote.setBackgroundResource(R.drawable.vote_down_empty)
                view.findViewById<TextView>(R.id.downVoteState).setText("empty")
            } else {
                if (view.findViewById<TextView>(R.id.upVoteState).text.equals("full")) {
                    upVote.setBackgroundResource(R.drawable.vote_up_empty)
                    view.findViewById<TextView>(R.id.upVoteState).setText("empty")
                }
                downVote.setBackgroundResource(R.drawable.vote_down_full)
                view.findViewById<TextView>(R.id.downVoteState).setText("full")
            }
        }

        setFlipCardListener(view, currentPos)

        return view
    }

    private fun setFlipCardListener(view: View, currentPos: Int) {
        val textView = view.findViewById<TextView>(R.id.textView)

        textView.setOnClickListener {

            val cardFront = CardFrontFragment()
            val bundle = Bundle()
            bundle.putInt("currentItem", currentPos)
            cardFront.arguments = bundle


            this.parentFragment!!.childFragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.animator.card_flip_left_in,
                            R.animator.card_flip_left_out,
                            R.animator.card_flip_right_in,
                            R.animator.card_flip_right_out
                    ).replace(R.id.card_container, cardFront).addToBackStack(null).commit()
        }
    }
}