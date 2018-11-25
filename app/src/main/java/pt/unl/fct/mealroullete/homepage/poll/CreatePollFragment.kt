package pt.unl.fct.mealroullete.homepage.poll

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import pt.unl.fct.mealroullete.R

class CreatePollFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_poll, container, false)

        val container = view.findViewById<TableLayout>(R.id.ingredient_table)
        var x = 1
        while (x < 8) {
            val child = inflater.inflate(R.layout.table_item_polluser, container, false) as LinearLayout
            child.findViewById<TextView>(R.id.userEmail).text = "email" + x.toString() + "@email.com"
            container.addView(child)
            x++
        }
        return view
    }
}
