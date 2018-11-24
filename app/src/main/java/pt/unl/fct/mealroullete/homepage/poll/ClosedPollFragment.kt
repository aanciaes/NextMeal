package pt.unl.fct.mealroullete.homepage.poll

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
                    child.findViewById<TextView>(R.id.winnerName).text = p.winner.name
                    child.findViewById<ImageView>(R.id.pollImage).setImageResource(p.winner.image)

                    container.addView(child)
                }
            }
        }
        return view
    }
}
