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
            startActivity(Intent(this, CustomizeGeneratorSides::class.java))
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
        ): View = inflater.inflate(R.layout.card_back, container, false)
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
                    Toast.makeText(this, "SELECTED", Toast.LENGTH_SHORT).show()
                } else if (Math.abs(deltaX) > MIN_DISTANCE && x2 < x1) {
                    Toast.makeText(this, "SKIP", Toast.LENGTH_SHORT).show()
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
