package pt.unl.fct.mealroullete.homepage.poll

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.persistance.MockDatabase
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal

class ActivePollFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_active_poll, container, false)
        val container = view.findViewById<TableLayout>(R.id.ingredient_table)

        val polls = MockDatabase.polls

        for(p in polls){
            if(p.owner == MockDatabase.loggedInUser!!.username){
                if(p.active){
                    val child = inflater.inflate(R.layout.table_item_pollactive, container, false) as LinearLayout
                    child.findViewById<TextView>(R.id.pollAuthor).text = p.owner

                    val allMinutes = Duration.between(LocalDateTime.now(), p.endTimestamp).toMinutes()
                    val hours = Math.floor((allMinutes/60).toDouble()).toInt()
                    val minutes = allMinutes - hours*60
                    
                    child.findViewById<TextView>(R.id.pollexpiration).text = hours.toString() + "h" + minutes + "m"
                    child.findViewById<TextView>(R.id.pollName).text = p.name
                    container.addView(child)
                }
            }

        }

        return view
    }
}
