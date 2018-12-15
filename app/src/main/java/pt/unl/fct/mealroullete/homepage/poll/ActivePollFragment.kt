package pt.unl.fct.mealroullete.homepage.poll

import android.content.Intent
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
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard
import pt.unl.fct.mealroullete.persistance.MockDatabase
import pt.unl.fct.mealroullete.persistance.Poll
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal

class ActivePollFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_active_poll, container, false)
        val ingredientTable = view.findViewById<TableLayout>(R.id.ingredient_table)

        val polls = MockDatabase.polls
        var counter = polls.size - 1
        while(counter >= 0){
            val p = polls[counter]
            if(p.owner == MockDatabase.loggedInUser!!.username || p.users.containsKey(MockDatabase.loggedInUser!!.email)){
                val allMinutes = Duration.between(LocalDateTime.now(), p.endTimestamp).toMinutes()
                val hours = Math.floor((allMinutes/60).toDouble()).toInt()
                val minutes = allMinutes - hours*60
                if(p.active && minutes > 0){
                    val childParent = inflater.inflate(R.layout.table_item_pollactive, container, false) as LinearLayout
                    val child = childParent.findViewById<LinearLayout>(R.id.table_item_pollactive_child)
                    child.findViewById<TextView>(R.id.pollAuthor).text = p.owner

                    child.setOnClickListener {
                        val intent = Intent(context, PollVoteActivity::class.java)
                        val b = Bundle()
                        b.putInt("name", p.id) //Your id
                        intent.putExtras(b)
                        startActivity(intent)
                    }

                    child.findViewById<TextView>(R.id.pollexpiration).text = hours.toString() + "h" + minutes + "m"
                    child.findViewById<TextView>(R.id.pollName).text = p.name
                    ingredientTable.addView(childParent)
                }
                else{
                   p.active = false
                }
            }
            counter--;
        }

        return view
    }
}
