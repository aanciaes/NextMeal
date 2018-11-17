package pt.unl.fct.mealroullete.mealgenerator.customize

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import pt.unl.fct.mealroullete.R
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import pt.unl.fct.mealroullete.mealgenerator.GeneratorHome


class RecipePresentation : AppCompatActivity() {
    private var x1: Float = 0.toFloat()
    private var x2: Float = 0.toFloat()
    val MIN_DISTANCE = 150
    private var mShowingBack:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_presentation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, CardFrontFragment())
                    .commit()
        }

        val back = findViewById<ImageButton>(R.id.backToStep2)
        back.setOnClickListener {
            startActivity(Intent(this, GeneratorHome::class.java))
        }

    }

    class CardFrontFragment : Fragment() {

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View = inflater.inflate(R.layout.card_front, container, false)
    }

    class CardBackFragment : Fragment() {
        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View {

            val view = inflater.inflate(R.layout.card_back, container, false)
            val fav = view.findViewById<ImageButton>(R.id.favorite_vote)
            fav.setOnClickListener {
                if(view.findViewById<TextView>(R.id.check_fav).text.equals("full")){
                    fav.setBackgroundResource(R.drawable.heart_favorite_empty)
                    view.findViewById<TextView>(R.id.check_fav).setText("empty")
                }
                else{
                    fav.setBackgroundResource(R.drawable.heart_favorite_full)
                    view.findViewById<TextView>(R.id.check_fav).setText("full")
                }
            }

            val upVote = view.findViewById<ImageButton>(R.id.upVote)
            val downVote = view.findViewById<ImageButton>(R.id.downVote)
            upVote.setOnClickListener {
                if(view.findViewById<TextView>(R.id.upVoteState).text.equals("full")){
                    upVote.setBackgroundResource(R.drawable.vote_up_empty)
                    view.findViewById<TextView>(R.id.upVoteState).setText("empty")
                }
                else{
                    if(view.findViewById<TextView>(R.id.downVoteState).text.equals("full")){
                        downVote.setBackgroundResource(R.drawable.vote_down_empty)
                        view.findViewById<TextView>(R.id.downVoteState).setText("empty")
                    }
                    upVote.setBackgroundResource(R.drawable.vote_up_full)
                    view.findViewById<TextView>(R.id.upVoteState).setText("full")
                }
            }


            downVote.setOnClickListener {
                if(view.findViewById<TextView>(R.id.downVoteState).text.equals("full")){
                    downVote.setBackgroundResource(R.drawable.vote_down_empty)
                    view.findViewById<TextView>(R.id.downVoteState).setText("empty")
                }
                else{
                    if(view.findViewById<TextView>(R.id.upVoteState).text.equals("full")){
                        upVote.setBackgroundResource(R.drawable.vote_up_empty)
                        view.findViewById<TextView>(R.id.upVoteState).setText("empty")
                    }
                    downVote.setBackgroundResource(R.drawable.vote_down_full)
                    view.findViewById<TextView>(R.id.downVoteState).setText("full")
                }
            }
            return view
        }
    }


    fun flipCard(view: View) {

        if (mShowingBack) {
            supportFragmentManager.popBackStack()
            mShowingBack = false
            return
        }

        mShowingBack = true

        supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out
                ).replace(R.id.container, CardBackFragment()).addToBackStack(null).commit()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> x1 = event.x
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                val deltaX = x2 - x1
                if (Math.abs(deltaX) > MIN_DISTANCE && x1 < x2) {
                    //ON LOAD OF RECIPE PRESENTATION GO GET THE LIST OF RECIPES THAT MATCH CONSTRAINTS
                    //THEN SHOW 1 TO USER
                    //ON SWIPE LEFT DO NOTHING
                    //ON SWIPE RIGHT SHOW NEXT RECIPE AND SAVE CURRENT RECIPE
                    //ON SWIPE LEFT GET BACK TO PREVIOUS RECIPE
                    Toast.makeText(this, "SHOW LAST RECIPE", Toast.LENGTH_SHORT).show()
                } else if (Math.abs(deltaX) > MIN_DISTANCE && x2 < x1) {
                    Toast.makeText(this, "GIVE ME ANOTHER RECIPE", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (mShowingBack) {
                        supportFragmentManager.popBackStack()
                        mShowingBack = false
                    }else {

                        mShowingBack = true

                        supportFragmentManager.beginTransaction()
                                .setCustomAnimations(
                                        R.animator.card_flip_right_in,
                                        R.animator.card_flip_right_out,
                                        R.animator.card_flip_left_in,
                                        R.animator.card_flip_left_out
                                ).replace(R.id.container, CardBackFragment()).addToBackStack(null).commit()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

}
