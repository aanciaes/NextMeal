package pt.unl.fct.mealroullete.mealgenerator.customize

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.persistance.MockDatabase

class CustomizeGeneratorSides : AppCompatActivity() {

    val cachedItems = MockDatabase.sideItems
    private val selectedItems = ArrayList<String>()
    private val mainCourseSelectedItems = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customize_generator_sides)

        intent.getStringArrayListExtra("mainCourseSelected")?.forEach { mainCourseSelectedItems.add(it) }
        intent.getStringArrayListExtra("sidesSelected")?.forEach { selectedItems.add(it) }

        setListeners()
        buildList(cachedItems)
    }

    private fun buildList (items: List<String>) {
        val tl = findViewById<TableLayout>(R.id.ingredient_table)
        tl.removeAllViews()

        var index = 0
        while (index < items.size) {
            val row = TableRow(this)
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
            row.layoutParams = lp

            val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var aux = 0
            while (index < items.size && aux < 4) {
                val childLayout = inflater.inflate(R.layout.table_item_ingredient, row, false) as LinearLayout

                childLayout.id = View.generateViewId()

                val item = items[index]
                childLayout.findViewById<TextView>(R.id.ingredient_name).text = item

                if (selectedItems.contains(item)) {
                    childLayout.findViewById<CheckBox>(R.id.ingredient_check_box).isChecked = true
                }

                childLayout.findViewById<CheckBox>(R.id.ingredient_check_box)
                        .setOnCheckedChangeListener { buttonView, isChecked ->
                            val parent = buttonView.parent.parent as View
                            val name = parent.findViewById<TextView>(R.id.ingredient_name).text.toString()
                            if (isChecked) {
                                selectedItems.add(name)
                            } else {
                                selectedItems.remove(name)
                            }
                        }

                index++
                aux++
                row.addView(childLayout)
            }
            tl.addView(row)
        }
    }

    private fun setListeners () {
        val back = findViewById<ImageButton>(R.id.backToStep1)
        back.setOnClickListener {
            val i = Intent(this, CustomizeGeneratorMainCourse::class.java)
            i.putExtra("mainCourseSelected", mainCourseSelectedItems)
            i.putExtra("sidesSelected", selectedItems)
            startActivity(i)
        }

        val generate = findViewById<Button>(R.id.buttonGenerate)
        generate.setOnClickListener {
            startActivity(Intent(this, RecipePresentation::class.java))
        }

        val search = findViewById<EditText>(R.id.search_field)
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    val items = cachedItems.filter { it.contains(s, true) }
                    buildList(items)
                }else{
                    buildList(cachedItems)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}
