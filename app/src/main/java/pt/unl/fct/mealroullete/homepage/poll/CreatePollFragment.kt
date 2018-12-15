package pt.unl.fct.mealroullete.homepage.poll

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
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
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class CreatePollFragment : Fragment() {

    val recipes = mutableListOf<Recipe>()

    val selectedRecipes = mutableListOf<Recipe>()
    var date: LocalDateTime? = null

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
        val tmp = recipes.map { it.name } as MutableList
        val list = mutableListOf<String>()
        list.add("Choose a recipe")
        tmp.sort()
        list.addAll(tmp)

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
            builder.setTitle("Invite Friend")

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
            datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                date = LocalDateTime.of(year, month+1, dayOfMonth, 23, 59, 59, 0)
                val dateOfPoll = year.toString() + "/" + (month+1).toString() + "/" + dayOfMonth.toString()
                activity?.findViewById<Button>(R.id.poll_date)?.text = dateOfPoll
            }
            datePickerDialog.show()
        }


        val addPoll = view.findViewById<Button>(R.id.createPoll)
        addPoll.setOnClickListener {
            val pollName = view.findViewById<EditText>(R.id.pollName)
            val users = mutableMapOf<String, String>()
            val wrapper = view.findViewById<TableLayout>(R.id.ingredient_table)

            for (i in (0 until wrapper.childCount)) {
                val child = wrapper.getChildAt(i)
                val email = child.findViewById<TextView>(R.id.userEmail)
                users.put(email.text.toString(), "")
            }

            if(pollName.text.toString() == "" || date == null || users.size < 1 || selectedRecipes.filterNotNull().size < 3){
                val builder = AlertDialog.Builder(this.context)
                builder.setTitle("Failure")
                builder.setMessage("A poll must have 3 recipes, one user, a name, and a end date")
                builder.apply {
                    setPositiveButton("OK") { _, _ ->
                        // nothing
                    }
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
            } else {

                if (Duration.between(LocalDateTime.now(), date).toNanos() < 0) {
                    val builder = AlertDialog.Builder(this.context)
                    builder.setTitle("Failure")
                    builder.setMessage("End date must be later than today")
                    builder.apply {
                        setPositiveButton("OK") { _, _ ->
                            // nothing
                        }
                    }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                } else {

                    val poll = Poll(MockDatabase.polls.size + 1, pollName.text.toString(), users, MockDatabase.loggedInUser?.username.toString(), selectedRecipes, null, date!!, true)

                    MockDatabase.polls.add(poll)

                    val builder = AlertDialog.Builder(this.context)
                    builder.setTitle("Success")
                    builder.setMessage("Your Poll has just started. Go ahead and start voting")
                    builder.apply {
                        setPositiveButton("OK") { _, _ ->
                            val intent = Intent(activity, PollActivity::class.java)
                            intent.putExtra("selectedPage", 1)

                            startActivity(intent)
                        }
                    }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
        }

        return view
    }

    @SuppressLint("NewApi")
    fun LocalDate.toDateTime(): LocalDateTime {
        return LocalDateTime.of(this.dayOfYear, this.month, this.dayOfMonth, 0, 0, 0)
    }


    inner class SpinnerListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (view != null && position > 0) {
                val recipeSelected = recipes.find { it.name == recipes[position - 1].name }
                selectedRecipes.add(0, recipeSelected!!)

                    val outerView = activity
                    val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper)
                    val recipeImage = container?.findViewById<Spinner>(R.id.spinner1)
                    val params = recipeImage?.getLayoutParams()

                    params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    (view as TextView).text = ""

                    recipeImage?.layoutParams = params

                    if (recipeSelected.image is Int) {
                        recipeImage!!.setBackgroundResource(recipeSelected.image as Int)
                    } else {
                        val uri = Uri.parse(recipeSelected.image as String)
                        val input = activity?.contentResolver?.openInputStream(uri)
                        val d = Drawable.createFromStream(input, uri.toString())
                        input?.close()
                        recipeImage?.background = d
                    }

                    val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll)
                    recipeName?.text = recipeSelected.name
            } else if (view != null) {
                (view as TextView).text = ""
            }
            }
        }

        inner class SpinnerListener2 : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (view != null && position > 0) {
                    val recipeSelected = recipes.find { it.name == recipes[position - 1].name }
                    selectedRecipes.add(1, recipeSelected!!)

                    val outerView = activity
                    val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper2)

                    val recipeImage = container?.findViewById<Spinner>(R.id.spinner2)
                    val params = recipeImage?.getLayoutParams()

                    params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    (view as TextView).text = ""

                    recipeImage?.layoutParams = params

                    if (recipeSelected.image is Int) {
                        recipeImage!!.setBackgroundResource(recipeSelected.image as Int)
                    } else {
                        val uri = Uri.parse(recipeSelected.image as String)
                        val input = activity?.contentResolver?.openInputStream(uri)
                        val d = Drawable.createFromStream(input, uri.toString())
                        input?.close()
                        recipeImage?.background = d
                    }

                    val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll2)
                    recipeName?.text = recipeSelected.name
                } else if (view != null) {
                    (view as TextView).text = ""
                }
            }
        }

        inner class SpinnerListener3 : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (view != null && position > 0) {
                    val recipeSelected = recipes.find { it.name == recipes[position - 1].name }
                    selectedRecipes.add(2, recipeSelected!!)

                    val outerView = activity
                    val container = outerView?.findViewById<RelativeLayout>(R.id.addRecipeWrappper3)
                    val recipeImage = container?.findViewById<Spinner>(R.id.spinner3)
                    val params = recipeImage?.getLayoutParams()

                    params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    (view as TextView).text = ""

                    recipeImage?.layoutParams = params

                    if (recipeSelected.image is Int) {
                        recipeImage!!.setBackgroundResource(recipeSelected.image as Int)
                    } else {
                        val uri = Uri.parse(recipeSelected.image as String)
                        val input = activity?.contentResolver?.openInputStream(uri)
                        val d = Drawable.createFromStream(input, uri.toString())
                        input?.close()
                        recipeImage?.background = d
                    }

                    val recipeName = container?.findViewById<TextView>(R.id.recipeNamePoll3)
                    recipeName?.text = recipeSelected.name
                } else if (view != null) {
                    (view as TextView).text = ""
                }
            }
        }
    }
