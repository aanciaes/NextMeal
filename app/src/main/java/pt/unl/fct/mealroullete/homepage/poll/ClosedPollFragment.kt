package pt.unl.fct.mealroullete.homepage.poll

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard
import pt.unl.fct.mealroullete.persistance.MockDatabase

class ClosedPollFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_closed_poll, container, false)
        val ingredientTableLayout = view.findViewById<TableLayout>(R.id.ingredient_table)

        val polls = MockDatabase.polls

        for (p in polls) {
            if (p.owner == MockDatabase.loggedInUser!!.username) {
                if (!p.active) {
                    val child = inflater.inflate(R.layout.table_item_pollclosed, container, false) as LinearLayout
                    child.findViewById<TextView>(R.id.pollAuthor).text = p.owner

                    child.findViewById<TextView>(R.id.winnerName).text = p.winner!!.name
                    child.findViewById<ImageView>(R.id.pollImage).setImageResource((p.winner.image as Int))

                    child.setOnClickListener {
                        val intent = Intent(this.context, RecipeCard::class.java)
                        val b = Bundle()
                        b.putString("name", p.winner.name) //Your id
                        intent.putExtras(b)
                        startActivity(intent)
                    }
                    ingredientTableLayout.addView(child)
                }
            }
        }


        return view
    }
}
