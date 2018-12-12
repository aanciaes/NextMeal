package pt.unl.fct.mealroullete.homepage.poll

import android.annotation.SuppressLint
import android.app.AlertDialog
<<<<<<< HEAD
import android.app.DatePickerDialog
=======
import android.graphics.drawable.Drawable
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.Poll
import pt.unl.fct.mealroullete.persistance.Recipe
import java.time.LocalDate
import java.time.LocalDateTime


class CreatePollFragment : Fragment() {

    val recipes = mutableListOf<Recipe>()
    var firstInit = true
    var firstInit2 = true
    var firstInit3 = true

    val selectedRecipes = mutableListOf<Recipe?>(null, null,null)
    var date: LocalDate? = null

    init {
        recipes.addAll(MockDatabase.recipesList)
        recipes.sortBy { it.name }
    }

    @SuppressLint("NewApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_poll, container, false)

        //get the spinner from the xml.
        val dropdown = view.findViewById(R.id.spinner1) as Spinner
        val dropdown2 = view.findViewById(R.id.spinner2) as Spinner
        val dropdown3 = view.findViewById(R.id.spinner3) as Spinner
        val list = recipes.map { it.name } as MutableList
        list.sort()
        val adapter = ArrayAdapter<String>(this.context!!, R.layout.simple_spinner_dropdown_item, list)
        val adapter2 = ArrayAdapter<String>(this.context!!, R.layout.simple_spinner_dropdown_item, list)
        val adapter3 = ArrayAdapter<String>(this.context!!, R.layout.simple_spinner_dropdown_item, list)
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
            builder.setPositiveButton("Add") { _, _ ->
                userEmail = input.text.toString()
                if (userEmail != "") {
                    val ingredientTable = view.findViewById<LinearLayout>(R.id.ingredient_table)
                    val child = inflater.inflate(R.layout.table_item_polluser, container, false) as LinearLayout
                    child.findViewById<TextView>(R.id.userEmail).text = userEmail
                    val remove = child.findViewById<ImageButton>(R.id.removeUserFromPoll)
                    remove.setOnClickListener {
                        ingredientTable.removeView(child)
                    }
                    val index = ingredientTable.indexOfChild(inviteUser)
                    ingredientTable.addView(child, index)
                }

            }

            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

            builder.show()
        }

        val datepickerButton = view.findViewById<Button>(R.id.poll_date)

        datepickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.datepicker)
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                date = LocalDate.of(year, month, dayOfMonth)
                val dateOfPoll = year.toString() + "/" + month.toString() + "/" + dayOfMonth.toString()
                activity?.findViewById<Button>(R.id.poll_date)?.text = dateOfPoll
            }
            datePickerDialog.show()
        }


        val addpoll = view.findViewById<Button>(R.id.createPoll)
        addpoll.setOnClickListener {
            val pollName = view.findViewById<EditText>(R.id.pollName)
            val users = mutableListOf<String>()
            val wrapper = view.findViewById<TableLayout>(R.id.ingredient_table)

            for(i in ( 0..wrapper.childCount)){
                val child  = wrapper.getChildAt(i)
                val email = child.findViewById<TextView>(R.id.userEmail)
                users.add(i, email.text.toString())
            }
            val poll = Poll(5, pollName.text.toString(),users, MockDatabase.loggedInUser.toString(), recipes, null,date!!.toDateTime(), true)
        }

        return view
    }

    @SuppressLint("NewApi")
    fun LocalDate.toDateTime (): LocalDateTime {
        return LocalDateTime.of(this.dayOfYear, this.month, this.dayOfMonth, 0, 0, 0)
    }


    inner class SpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
<<<<<<< HEAD
            val recipeSelected = recipes.find { it.name == recipes.get(position).name }
            if (!firstInit && view != null) {
                selectedRecipes.add(0, recipeSelected!!)
=======
            val recipeSelected = recipes.find { it.name == recipes[position].name }
            if (!firstInit && view != null) {
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
                val outerView = activity
                val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper)
                val recipeImage = container?.findViewById<Spinner>(R.id.spinner1)
                val params = recipeImage?.getLayoutParams()
<<<<<<< HEAD
                params?.width= ViewGroup.LayoutParams.MATCH_PARENT
                params?.height= ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = ""
=======
                params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = null
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
                recipeImage?.layoutParams = params

                if (recipeSelected!!.image is Int) {
                    recipeImage!!.setBackgroundResource(recipeSelected.image as Int)
                } else {
                    val d = Drawable.createFromPath(recipeSelected.image as String)
                    recipeImage?.background = d
                }

                val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll)
                recipeName?.text = recipeSelected.name
            } else {
                firstInit = false
            }
        }
    }

    inner class SpinnerListener2 : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val recipeSelected = recipes.find { it.name == recipes.get(position).name }
            if (!firstInit2 && view != null) {
<<<<<<< HEAD

                selectedRecipes.add(1, recipeSelected!!)
=======
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
                val outerView = activity
                val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper2)

                val recipeImage = container?.findViewById<Spinner>(R.id.spinner2)
                val params = recipeImage?.getLayoutParams()
<<<<<<< HEAD
                params?.width= ViewGroup.LayoutParams.MATCH_PARENT
                params?.height= ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = ""
=======
                params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = null
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
                recipeImage?.layoutParams = params

                if (recipeSelected!!.image is Int) {
                    recipeImage!!.setBackgroundResource(recipeSelected.image as Int)
                } else {
                    val d = Drawable.createFromPath(recipeSelected.image as String)
                    recipeImage?.background = d
                }

                val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll2)
                recipeName?.text = recipeSelected.name
            } else {
                firstInit2 = false
            }
        }
    }

    inner class SpinnerListener3 : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val recipeSelected = recipes.find { it.name == recipes.get(position).name }
            if (!firstInit3 && view != null) {
<<<<<<< HEAD
                selectedRecipes.add(2, recipeSelected!!)
=======
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
                val outerView = activity
                val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper3)
                val recipeImage = container?.findViewById<Spinner>(R.id.spinner3)
                val params = recipeImage?.getLayoutParams()
<<<<<<< HEAD
                params?.width= ViewGroup.LayoutParams.MATCH_PARENT
                params?.height= ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = ""
=======
                params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                (view as TextView).text = null
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
                recipeImage?.layoutParams = params

                if (recipeSelected!!.image is Int) {
                    recipeImage!!.setBackgroundResource(recipeSelected.image as Int)
                } else {
                    val d = Drawable.createFromPath(recipeSelected.image as String)
                    recipeImage?.background = d
                }

                val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll3)
                recipeName?.text = recipeSelected.name
            } else {
                firstInit3 = false
            }
        }
    }
}
