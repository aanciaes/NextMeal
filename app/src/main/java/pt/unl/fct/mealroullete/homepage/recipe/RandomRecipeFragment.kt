package pt.unl.fct.mealroullete.homepage.recipe

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.mealgenerator.GeneratorHome
import android.widget.EditText
import android.widget.ImageButton


class RandomRecipeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.lunch, R.id.brunch, R.id.breakfast, R.id.dinner -> {
                startActivity(Intent(this.context, GeneratorHome::class.java))
            }
            else -> {
                println(R.id.lunch)
                println(v?.id)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_random_recipe, container,false)
        val lunch = mView.findViewById(R.id.lunch) as ImageButton
        lunch.setOnClickListener(this)
        val breakfast = mView.findViewById(R.id.breakfast) as ImageButton
        breakfast.setOnClickListener(this)
        val dinner = mView.findViewById(R.id.dinner) as ImageButton
        dinner.setOnClickListener(this)
        val brunch = mView.findViewById(R.id.brunch) as ImageButton
        brunch.setOnClickListener(this)
        return mView

    }

}
