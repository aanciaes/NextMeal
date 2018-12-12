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
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ClosedPollFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_closed_poll, container, false)
        val container = view.findViewById<TableLayout>(R.id.ingredient_table)

        val polls = MockDatabase.polls

        for(p in polls) {
            if (p.owner == MockDatabase.loggedInUser!!.username) {
                if (!p.active) {
                    val child = inflater.inflate(R.layout.table_item_pollclosed, container, false) as LinearLayout
                    child.findViewById<TextView>(R.id.pollAuthor).text = p.owner
<<<<<<< HEAD
                    child.findViewById<TextView>(R.id.winnerName).text = p.winner?.name
                    child.findViewById<ImageView>(R.id.pollImage).setImageResource(p.winner!!.image)
=======
                    child.findViewById<TextView>(R.id.winnerName).text = p.winner.name
                    child.findViewById<ImageView>(R.id.pollImage).setImageResource((p.winner.image as Int))
>>>>>>> d874d567f8563173fcb81667952a00102a17b180
                    child.setOnClickListener {
                        val intent = Intent(this.context, RecipeCard::class.java)
                        val b = Bundle()
                        b.putString("name", p.winner.name) //Your id
                        intent.putExtras(b)
                        startActivity(intent)
                    }
                    container.addView(child)
                }
            }
        }


        return view
    }
}
