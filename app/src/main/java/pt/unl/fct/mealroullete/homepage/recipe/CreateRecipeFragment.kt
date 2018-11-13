package pt.unl.fct.mealroullete.homepage.recipe

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.mealgenerator.customize.AdvancedGeneratorStep1

class CreateRecipeFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_recipe, container, false)
    }
}
