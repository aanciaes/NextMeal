package pt.unl.fct.mealroullete.homepage.poll

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.w3c.dom.Text
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.persistance.Ingredient
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.Recipe

class CreatePollFragment : Fragment() {

    val recipes = mutableListOf<Recipe>()
    var firstInit = true
    var firstInit2 = true
    var firstInit3 = true

    init {
        recipes.addAll(MockDatabase.recipesList)
        recipes.sortBy{it.name}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_poll, container, false)

        //get the spinner from the xml.
        val dropdown = view.findViewById(R.id.spinner1) as Spinner
        val dropdown2 = view.findViewById(R.id.spinner2) as Spinner
        val dropdown3 = view.findViewById(R.id.spinner3) as Spinner
        val list = recipes.map{it.name} as MutableList
        list.sort()
        val adapter = ArrayAdapter<String>(this.context, R.layout.simple_spinner_dropdown_item, list)
        val adapter2 = ArrayAdapter<String>(this.context, R.layout.simple_spinner_dropdown_item, list)
        val adapter3 = ArrayAdapter<String>(this.context, R.layout.simple_spinner_dropdown_item, list)
        dropdown.adapter = adapter
        dropdown2.adapter = adapter2
        dropdown3.adapter = adapter3

        dropdown.onItemSelectedListener = SpinnerListener()
        dropdown2.onItemSelectedListener = SpinnerListener2()
        dropdown3.onItemSelectedListener = SpinnerListener3()

        val inviteUser = view.findViewById<ImageButton>(R.id.inviteUser)
        inviteUser.setOnClickListener {

            var userEmail = ""
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("INVITE FRIEND")

            val viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_input, null, false);

            // Set up the input
            val input = viewInflated.findViewById(R.id.input) as EditText
            builder.setView(viewInflated)

            // Set up the buttons
            builder.setPositiveButton("Add") { dialog, which ->
                userEmail = input.text.toString()
                if (userEmail != "") {
                    val container = view.findViewById<LinearLayout>(R.id.ingredient_table)
                    val child = inflater.inflate(R.layout.table_item_polluser, container, false) as LinearLayout
                    child.findViewById<TextView>(R.id.userEmail).text = userEmail
                    val remove = child.findViewById<ImageButton>(R.id.removeUserFromPoll)
                    remove.setOnClickListener {
                        container.removeView(child)
                    }
                    val index = container.indexOfChild(inviteUser)
                    container.addView(child, index)
                }

            }

            builder.setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })

            builder.show()
        }

        return view
    }


    inner class SpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val recipeSelected = recipes.find { it.name == recipes.get(position).name }
            if (!firstInit) {
                val outerView = activity
                val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper)
                val recipeImage = container?.findViewById<Spinner>(R.id.spinner1)
                val params = recipeImage?.getLayoutParams()
                params?.width= ViewGroup.LayoutParams.MATCH_PARENT
                params?.height= ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = null
                recipeImage?.layoutParams = params
                recipeImage?.setBackgroundResource(recipeSelected!!.image)
                val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll)
                recipeName?.text = recipeSelected?.name
            } else {
                firstInit = false
            }
        }
    }

    inner class SpinnerListener2 : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val recipeSelected = recipes.find { it.name == recipes.get(position).name }
            if (!firstInit2) {
                val outerView = activity
                val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper2)

                val recipeImage = container?.findViewById<Spinner>(R.id.spinner2)
                val params = recipeImage?.getLayoutParams()
                params?.width= ViewGroup.LayoutParams.MATCH_PARENT
                params?.height= ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = null
                recipeImage?.layoutParams = params
                recipeImage?.setBackgroundResource(recipeSelected!!.image)
                val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll2)
                recipeName?.text = recipeSelected?.name
            } else {
                firstInit2 = false
            }
        }
    }

    inner class SpinnerListener3 : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val recipeSelected = recipes.find { it.name == recipes.get(position).name }
            if (!firstInit3) {
                val outerView = activity
                val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper3)
                val recipeImage = container?.findViewById<Spinner>(R.id.spinner3)
                val params = recipeImage?.getLayoutParams()
                params?.width= ViewGroup.LayoutParams.MATCH_PARENT
                params?.height= ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = null
                recipeImage?.layoutParams = params
                recipeImage?.setBackgroundResource(recipeSelected!!.image)
                val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll3)
                recipeName?.text = recipeSelected?.name
            } else {
                firstInit3 = false
            }
        }
    }
}
